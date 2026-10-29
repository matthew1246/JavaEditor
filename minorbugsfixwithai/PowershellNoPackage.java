import java.util.List;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
public class PowershellNoPackage implements Powershell {
	protected FileName filename;
	protected Packager packager;
	protected String main_class;
	protected String dir;
	protected BufferedWriter output2;
	private IsMoreThanOneJar isMoreThanOneJar;
	public PowershellNoPackage(Main main,String main_class,String dir,AllFiles allfiles,boolean _isMoreThanOneJar,FileName filename) {
		this(main,main_class,dir,allfiles,_isMoreThanOneJar);
		this.filename=filename;
	}
	public PowershellNoPackage(Main main,String main_class,String dir,AllFiles allfiles,boolean _isMoreThanOneJar) {
		this.isMoreThanOneJar = new IsMoreThanOneJar(_isMoreThanOneJar);
		try {
			this.dir = dir;
			this.main_class = main_class;
			String filename=Powershell.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			filename = main.getFileName(filename);
			if(Platform.isWindows() && filename.startsWith("/"))
				filename=filename.substring(1,filename.length());
			if(!dir.endsWith(Platform.separator()))
				dir=dir+Platform.separator();
			JOptionPane.showMessageDialog(null,filename+" is already open. Run script to close "+filename);
			FileWriter filewriter2 = new FileWriter(dir+Platform.closeAndCreateJarScriptName(),StandardCharsets.UTF_8);
			output2 = new BufferedWriter(filewriter2);
			for(String line:Platform.scriptHeader(dir)) {
				output2.write(line);
				output2.write("\n");
			}
			/*for(int i = 0; i < allfiles.files.size(); i++) {
				File file2 = new File(allfiles.files.get(i));
				if(file2.exists()) {
					output2.write("del "+allfiles.files.get(i));
					output2.write("\n");
				}
			}
			*/
			MainDotJars maindotjars=new MainDotJars(main);
			List<String> mainjars=maindotjars.getAllMainJars();
			for(String maindotjar:mainjars) {

				if(Platform.isWindows()) {
					output2.write("del "+maindotjar);
				}
				else {
					output2.write("rm -f "+Platform.quote(Platform.toShellPath(maindotjar)));
				}
				output2.write("\n");
			}
		} catch(URISyntaxException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}			
	public void Compile(int javaversionnumber,String fileName) {
		try {
			CommandLine commandline = new CommandLine();
			commandline.compileAll();
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			
			for(String jar:preferences.jars) {
				commandline.addExternalJar(jar);
			}
		
			if(javaversionnumber != -2)	
				commandline.earlierjavaversion(javaversionnumber);
			
			if(Platform.isWindows()) {
				output2.write("START /B /WAIT cmd.exe /c "+commandline.javac());
			}
			else {
				output2.write(commandline.javac());
			}
			output2.write("\n");
			
			//output2.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}				
	private String main_class2;						
	public void makeJar(int javaversionnumber) {
		try {
			String createJarLocationFolder=isMoreThanOneJar.getCreateJarFolderLocation(dir);
			if(!createJarLocationFolder.endsWith(Platform.separator()))
				createJarLocationFolder=createJarLocationFolder+Platform.separator();
			main_class2 = main_class;
			JOptionPane.showMessageDialog(null,"jar will be created on:"+createJarLocationFolder);
			// START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm Main.jar mf.txt .
			String jarname=filename.getJarFileName(javaversionnumber);
			if(Platform.isWindows()) {
				output2.write("START /B /WAIT cmd.exe /c "+Platform.quote(Platform.javaTool("jar"))+" cfm "+createJarLocationFolder+jarname+".jar mf.txt .");
			}
			else {
				output2.write(Platform.quote(Platform.javaTool("jar"))+" cfm "+createJarLocationFolder+jarname+".jar mf.txt .");
			}
			main_class2=createJarLocationFolder+jarname;
			output2.write("\n");
			// output2.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}		
	public void Finish() {
		try {
			output2.write("\n");
			output2.write(Platform.quote(Platform.javaTool("java"))+" -jar "+main_class2+".jar");

			output2.close();
			CommandLine commandline = new CommandLine();
			String liney = Platform.scriptRunCommand(dir+Platform.closeAndCreateJarScriptName());
		
			commandline.runWithMSDOS(liney,dir);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
