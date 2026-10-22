import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.io.BufferedWriter;
import java.io.IOException;
public class AllVersionsJarNoPackage extends AllVersionsJar {
	private FileName filename;
	private Packager packager;
	private String dir;
	private Main main;
	private String fileName;
	private SaveActionListener sal;
	private ActionEvent ev4;
	private IsMoreThanOneJar isMoreThanOneJar;
	public AllVersionsJarNoPackage(Main main,String fileName,SaveActionListener sal,ActionEvent ev4,boolean _isMoreThanOneJar,FileName filename) {
		this(main,fileName,sal,ev4,_isMoreThanOneJar);
		this.filename=filename;
	}
	public AllVersionsJarNoPackage(Main main,String fileName,SaveActionListener sal,ActionEvent ev4,boolean _isMoreThanOneJar) {
		isMoreThanOneJar=new IsMoreThanOneJar(_isMoreThanOneJar);
		this.main = main;
		this.fileName = fileName;
		this.sal = sal;
		this.ev4 = ev4;
		packager=new Packager(main);
		if(!packager.containsPackage() || !packager.isInRightFolders()) {
			dir = Platform.directoryOf(fileName);
		}
		else {
			dir=packager.classpath;
		}
		if(!dir.endsWith(Platform.separator()))
			dir=dir+Platform.separator();
	}
	public String getDir() {
		return dir;
	}
	public AllFiles getAllFiles() {
		return allfiles;
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
					// jar = main.getFileName(jar);
					Process process=commandline.run(Platform.quote(Platform.javaTool("jar"))+" xf "+jar,dir);
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
				main_string=Platform.fileNameOf(fileName);
				main_string = main_string.replaceAll("\\.java","");
			}
			storeselectedfile.set(fileName);
			java.util.LinkedHashMap<String,Preferences> linkedhashmap=storeselectedfile.getBackup();
			linkedhashmap.get(fileName).starterclass=main_string;
			storeselectedfile.setBackup(linkedhashmap);
			
			if(packager.containsPackage())
				main_string=packager.getPackageName()+"."+main_string;
		}
		return main_string;
	}
	public void WriteManifest(String main_class) {
		try {
			while(dir.endsWith(Platform.separator()) && dir.length() > 1) {
				dir=dir.substring(0,dir.length()-1);
			}
			if(!dir.endsWith(Platform.separator()))
				dir=dir+Platform.separator();
			if(Platform.isRootDirectory(dir)) {
				System.out.println("dir3:"+dir);
				CommandLine commandline = new CommandLine();
				java.io.File tempfile = new java.io.File(System.getProperty("java.io.tmpdir"),"writemf"+Platform.scriptExtension());
				java.io.FileWriter batwriter = new java.io.FileWriter(tempfile,java.nio.charset.StandardCharsets.UTF_8);
				java.io.BufferedWriter batoutput = new java.io.BufferedWriter(batwriter);
				if(Platform.isWindows()) {
					batoutput.write("echo Manifest-Version: 1.0 > \""+dir+"mf.txt\"");
					batoutput.write("\n");
					batoutput.write("echo Main-Class: "+main_class+" >> \""+dir+"mf.txt\"");
				}
				else {
					batoutput.write("#!/bin/sh");
					batoutput.write("\n");
					batoutput.write("echo Manifest-Version: 1.0 > \""+dir+"mf.txt\"");
					batoutput.write("\n");
					batoutput.write("echo Main-Class: "+main_class+" >> \""+dir+"mf.txt\"");
				}
				batoutput.close();
				String liney = Platform.scriptRunCommand(tempfile.getAbsolutePath());
				System.out.println(liney);
				commandline.run(liney, dir);
			}
			else {
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
			}
		} catch(java.io.IOException ex) {
			ex.printStackTrace();
		}
	}
	private AllFiles allfiles;
	public boolean isMatthewJavaEditor(String main_class) {
		allfiles = new AllFiles(main_class,dir);
		return (allfiles.isSameDirectory(main) || (allfiles.exists() && !allfiles.delete()));
	}
	public void MakeJarUsingmsdos(int javaversionnumber,String main_class) {
		try {
			String[] splited=  main_class.split("\\.");
			String main_class2 = splited[splited.length-1];

			String input = Platform.quote(Platform.javaTool("jar"))+" cfm "+isMoreThanOneJar.getCreateJarFolderLocation(dir)+filename.getJarFileName(javaversionnumber)+".jar mf.txt .";

			JOptionPane.showMessageDialog(null,input);
			Process process;
			CommandLine commandline =new CommandLine();
			if(Platform.isRootDirectory(dir)) {
				process=commandline.runAsAdmin(input,dir);
			}
			else {
				process=commandline.run(input,dir);
			}

			String output = new DisplayOutput().All(process);
			if(output.equals("")) {
				JOptionPane.showMessageDialog(null,"jar created");
			}
			else {
				JOptionPane.showMessageDialog(null,output);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public Powershell getPowershell(Main main,String main_class,String dir,AllFiles allfiles) {
		return new PowershellNoPackage(main,main_class,dir,allfiles,isMoreThanOneJar.isMoreThanOneJar,filename);
	}
}
