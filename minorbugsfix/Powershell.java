import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
public class Powershell {
	protected BufferedWriter output2;
	public Powershell(Main main,String dir,AllFiles allfiles) {
		try {
			String filename=Powershell.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			filename = main.getFileName(filename);
			JOptionPane.showMessageDialog(null,filename+" is already open. Run script to close "+filename+".jar");
			FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
			output2 = new BufferedWriter(filewriter2);
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
			// output2.close();
		} catch (java.net.URISyntaxException ex) {
			ex.printStackTrace();
		} catch (java.io.IOException ex) {
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
		
			commandline.earlierjavaversion(javaversionnumber);
			
			output2.write("START /B /WAIT cmd.exe /c "+commandline.javac());
			output2.write("\n");
			
			output2.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}