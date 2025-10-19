import java.awt.event.ActionEvent;
/*
** This generates all versions of Java for Jars
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
			if(main.equals("")) {
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
}