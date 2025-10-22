import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
public class Compile {
	public void compileall(String fileName,SaveActionListener sal,ActionEvent ev,boolean withJavaFX,Main main) {
		try {
			sal.actionPerformed(ev);
			if(fileName.equals(""))
				fileName = main.fileName;
			String classpath = fileName.replaceAll("[^\\\\]+\\.java","");
			CommandLine commandline = new CommandLine();
			if(withJavaFX) {
				ExtractJavaFXJars extractjavafxjars = new ExtractJavaFXJars(main);
				commandline.addJavaFX();						
			}
			commandline.compileAll();
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			
			for(String jar:preferences.jars) {
				commandline.addExternalJar(jar);
			}
			
			//Process process = compileFromMSDOS("*.java",classpath);
			String[] command = new String[3];
			command[0] = "cmd.exe";
			command[1] = "/c";
			command[2] = commandline.javac();
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command,null,new File(classpath));
			
			InputStream inputstream = process.getErrorStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String line = bufferedreader.readLine();
			if(line == null) {
				JOptionPane.showMessageDialog(null,"compiled");
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
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
	}
	public void compileall(String fileName,int javaversionnumber,SaveActionListener sal,ActionEvent ev,boolean withJavaFX,Main main) {
		try {
			sal.actionPerformed(ev);
			if(fileName.equals(""))
				fileName = main.fileName;
			String classpath = fileName.replaceAll("[^\\\\]+\\.java","");
			CommandLine commandline = new CommandLine();
			if(withJavaFX) {
				ExtractJavaFXJars extractjavafxjars = new ExtractJavaFXJars(main);						
			}
			commandline.compileAll();
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			
			for(String jar:preferences.jars) {
				commandline.addExternalJar(jar);
			}
			
			commandline.earlierjavaversion(javaversionnumber);
			
			//Process process = compileFromMSDOS("*.java",classpath);
			String[] command = new String[3];
			command[0] = "cmd.exe";
			command[1] = "/c";
			command[2] = commandline.javac();
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command,null,new File(classpath));
			
			InputStream inputstream = process.getErrorStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String line = bufferedreader.readLine();
			if(line == null) {
				JOptionPane.showMessageDialog(null,"compiled");
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
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
	}
}