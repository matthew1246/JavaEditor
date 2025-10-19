import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.io.BufferedWriter;
import java.io.IOException;
/*
** This generates all versions of Java for Jars
** This class is only if Main.jar is not running.
*/
public class AllVersionsJar {
	private String dir;
	private Main main;
	private String fileName;
	private SaveActionListener sal;
	private ActionEvent ev4;
	public AllVersionsJar(Main main,String fileName,SaveActionListener sal,ActionEvent ev4) {
		this.main = main;
		this.fileName = fileName;
		this.sal = sal;
		this.ev4 = ev4;
		dir = fileName.replaceAll("[^\\\\]+\\.java","");
	}
	public void Compile(int javaversionnumber) {
		Compile compile = new Compile();
		compile.compileall(main,fileName,javaversionnumber,sal,ev4);
	}
	public Preferences extractJars(StoreSelectedFile storeselectedfile) {		
		CommandLine commandline = new CommandLine();
		Preferences preferences=storeselectedfile.get(fileName);
		if(!fileName.equals("")) {
			java.util.List<String> jars = preferences.jars;
			for(String jar:jars) {
				try {
					jar = main.getFileName(jar);
					Process process=commandline.run("\""+System.getProperty("java.home")+"\\bin\\jar.exe\" xf "+jar,dir);
					process.waitFor();
					//output.write(" "+jar);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}				
			}
		}
		return preferences;
	}		
	public String getMain(StoreSelectedFile storeselectedfile,Preferences preferences) {
		String main_string=preferences.starterclass;
		if(!fileName.equals("")) {
			if(main_string.equals("")) {
				main_string=fileName.replaceAll(".+\\\\","");
				main_string = main_string.replaceAll("\\.java","");
			}
			storeselectedfile.set(fileName);
			java.util.LinkedHashMap<String,Preferences> linkedhashmap=storeselectedfile.getBackup();
			linkedhashmap.get(fileName).starterclass=main_string;
			storeselectedfile.setBackup(linkedhashmap);
		}
		return main_string;
	}
	public void WriteManifest(String main_class) {
		try {
			java.io.FileWriter filewriter = new java.io.FileWriter( dir+"mf.txt",java.nio.charset.StandardCharsets.UTF_8);
			java.io.BufferedWriter output = new java.io.BufferedWriter(filewriter);
			output.write("Manifest-Version: 1.0");
			output.write("\n");
			output.write("Main-Class: ");
			output.write(main_class);
			output.write("\n");
			//output.write("Class-Path:");
			//output.write(" *");
			//output.write("\n");
			output.close();
		} catch(java.io.IOException ex) {
			ex.printStackTrace();
		}
	}
	private AllFiles allfiles;
	public boolean isMatthewJavaEditor(String main_class) {
		allfiles = new AllFiles(main_class,dir);
		return (allfiles.isSameDirectory() || (allfiles.exists() && !allfiles.delete()));
	}
	public void MakeJarUsingmsdos(int javaversionnumber,String main_class) {
		try {
			File file = new File(dir);
			File parentdirectory=file.getParentFile();
			String input = "\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+parentdirectory.getAbsolutePath()+"\\ForJava"+javaversionnumber+"_"+main_class+".jar mf.txt .";
			JOptionPane.showMessageDialog(null,input);
			CommandLine commandline = new CommandLine();
			Process process=commandline.run(input,dir);
			
			java.io.InputStream inputstream = process.getErrorStream();
			java.io.InputStreamReader inputstreamreader = new java.io.InputStreamReader(inputstream);
			java.io.BufferedReader bufferedreader = new java.io.BufferedReader(inputstreamreader);
			String line = bufferedreader.readLine();
			if(line == null) {
				JOptionPane.showMessageDialog(null,"jar created");
			}
			else {
				String lines = line;
				while(true) {
					line = bufferedreader.readLine();
					if(line == null)
						break;
					lines = lines+"\n"+line;
				}
				JOptionPane.showMessageDialog(null,lines);
			}
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
	}
	public void Powershell(int javaversionnumber,String main_class) {
		try {
			JOptionPane.showMessageDialog(null,dir+"ForJava"+javaversionnumber+"_"+main_class+".jar is already open. Run script to close "+main_class+".jar");
			FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
			BufferedWriter output2 = new BufferedWriter(filewriter2);
			output2.write("cd "+dir);
			output2.write("\n");
			output2.write("START /B /WAIT taskkill /F /im java.exe");
			output2.write("\n");
			output2.write("START /B /WAIT taskkill /F /im javaw.exe");
			output2.write("\n");
			for(int i = 0; i < allfiles.files.size(); i++) {
				File file2 = new File(allfiles.files.get(i));
				if(file2.exists()) {
					output2.write("del "+allfiles.files.get(i));
					output2.write("\n");
				}
			}
			
			CommandLine commandline = new CommandLine();
			commandline.compileAll();
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			
			for(String jar:preferences.jars) {
				commandline.addExternalJar(jar);
			}
			
			commandline.earlierjavaversion(javaversionnumber);
			
			output2.write("START /B /WAIT cmd.exe /c "+commandline.javac());
			
			File file = new File(dir);
			File parentdirectory=file.getParentFile();
			// START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm Main.jar mf.txt .
			output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+parentdirectory.getAbsolutePath()+"\\ForJava"+javaversionnumber+"_"+main_class+".jar mf.txt .");
			output2.write("\n");
			
			commandline = new CommandLine();
			output2.write("java -jar ForJava"+javaversionnumber+"_"+main_class+".jar");
			output2.write("\n");
			output2.write("\n");
			output2.close();
			commandline = new CommandLine();
			String liney = "powershell -Command \"Start-Process powershell -Verb runAs -ArgumentList '-Command cmd /c \""+dir+"closeandcreatejar.bat\"'\"";
		
			commandline.runWithMSDOS(liney,dir);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}