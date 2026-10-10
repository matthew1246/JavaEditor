package javaeditor.minorbugsfix;


import javax.swing.JOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class MainDotJars {
	public Main main;
	public MainDotJars(Main main) {
		this.main=main;
	}
	public MainDotJars() {
	}
	public void testgetallMainJars() {
		MainDotJars maindotjars =new MainDotJars();
		maindotjars.main=new Main(new OpenDefaultContent("C:\\Users\\Owner\\Documents\\javaeditor\\minorbugsfix\\Main.java"));
		List<String> mainjars=maindotjars.getAllMainJars();
		String mainjar=mainjars.get(0);	
		// Assert.assertEquals("C:\\Users\\Owner\\Documents\\javaeditor\\minorbugsfix\\Main.jar",mainjar);
		Packager packager=new Packager(main);
		if(packager.containsPackage()) { //Contains package
			String mainjar2=mainjars.get(1);
			// Assert.assertEquals("C:\\Users\\Owner\\Documents\\javaeditor\\Main.jar",mainjar2);
		}
		for(String maindotjar:mainjars) {
			System.out.println(maindotjar);
		}
	}
	public List<String> getAllMainJars() {
		List<String> maindotjars=new ArrayList<String>();
		Packager packager=new Packager(main);
		if(!packager.containsPackage()) { // !packager.containsPackage()
			JOptionPane.showMessageDialog(null,"No package");
			String filePath=getFilePath(Main.getDirectory(main.fileName));
			if(isExists(filePath)) {
				maindotjars.add(filePath);
			}
			return maindotjars;
		}
		else { // Contains package
			if(packager.isInRightFolders()) { // packager.isInRightFolders()
				// JOptionPane.showMessageDialog(null,"packages");
				String filePath=getFilePath(Main.getDirectory(main.fileName));
				if(isExists(filePath)) {
					maindotjars.add(filePath);
				}
				
				List<String> maindotjars2=getMiddleFolders(packager.classpath);
				//List<String> maindotjars2=getMiddleFolders("C:\\Users\\Owner\\Documents");
				for(String maindotjar:maindotjars2) {
					if(isExists(filePath)) {
						maindotjars.add(maindotjar);
					}
				}
					
				// Get Documents\Main.jar if package is javaeditor.minorbugsfix
				String filePath2=getFilePath(packager.classpath);
				//String filePath2=getFilePath("C:\\Users\\Owner\\Documents");
				if(isExists(filePath2)) {
					maindotjars.add(filePath2);
				}
			}
			else { // !packager.isInRightFolders()
				
				// If javac -d then this is generated: javaeditor/minorbugsfix/javaeditor/minorbugsfix/Main.jar
				String dir=Main.getDirectory(main.fileName);
				if(!dir.endsWith("\\"))
					dir=dir+"\\";
				String filePath=getFilePath(dir+packager.getPackageName());
				if(isExists(filePath))
					maindotjars.add(filePath);
				
				List<String> maindotjars2=getMiddleFolders(dir);
				for(String maindotjar:maindotjars2) {
					maindotjars.add(maindotjar);
				}	
				
				String filepath2=getFilePath(dir);
				if(isExists(filepath2))
					maindotjars.add(filepath2);	
			}
			return maindotjars;
		}
	}
	private String getFilePath(String dir) {
		if(!dir.endsWith("\\"))
			dir=dir+"\\";
		String classname=Main.getClassName(main.fileName);
		String filepath=dir+classname+".jar";
		return filepath;
	}
	private boolean isExists(String filepath) {
		File maindir=new File(filepath);
		return maindir.exists();
	}
	private List<String> getMiddleFolders(String classpath) {
		List<String> maindotjars=new ArrayList<String>();
		
		// Get javaeditor/Main.jar from javaeditor.minorbugsfix folders
		Packager packager=new Packager(main);
		String[] packagefolders=packager.getPackageName().split("\\.");
		//String[] packagefolders="javaeditor.minorbugsfix".split("\\.");
		String fileName2=classpath;
		if(!fileName2.endsWith("\\"))
			fileName2=fileName2+"\\";
		for(int i = 0; i < packagefolders.length-1; i++) {
			String packageFolder=packagefolders[i];
			fileName2=fileName2+packageFolder;
			String filePath5=getFilePath(fileName2);
			if(isExists(filePath5))
				maindotjars.add(filePath5);
		}	
		return maindotjars;
	}
}