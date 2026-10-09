import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class MainDotJars {
	protected Main main;
	public MainDotJars(Main main) {
		this.main=main;
	}
	public List<String> getAllMainJars() {
		List<String> maindotjars=new ArrayList<String>();
		Packager packager=new Packager(main);
		if(!packager.containsPackage()) {
			String filePath=getFilePath(main.fileName);
			if(isExists(filePath)) {
				maindotjars.add(filePath);
			}
			return maindotjars;
		}
		else { // Contains package
			if(packager.isInRightFolders()) {
				String filePath=getFilePath(main.fileName);
				if(isExists(filePath)) {
					maindotjars.add(filePath);
				}
				
				// Get Main.jar inside javaeditor folder for eg) package is javaeditor.minorbugsfix
				String[] packagefolders=packager.classpath.split("\\.");
				String fileName2=packager.classpath;
				if(!fileName2.endsWith("\\"))
					fileName2=fileName2+"\\";
				for(int i = 0; i < packagefolders.length-1; i++) {
					String packageFolder=packagefolders[i];
					fileName2=fileName2+packageFolder;
					String filePath5=getFilePath(fileName2);
					if(isExists(filePath5))
						maindotjars.add(filePath5);
				}	
					
				// Get Documents\Main.jar if package is javaeditor.minorbugsfix
				String filePath2=getFilePath(packager.classpath);
				if(isExists(filePath2)) {
					maindotjars.add(filePath2);
				}
			}
			else { // !packager.isInRightFolders()
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
}