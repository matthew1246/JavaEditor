import javax.swing.JOptionPane;
import java.util.List;
/*
** This generates all versions of Java for Jars when Main.jar is open.
** The user excluded some folders from classpath and its package name folders.
** The folders that were not excluded are added to the jar with:
** jar.exe ... -C "<classpath>" <relative folder path> ...
*/
public class PowershellMoreThanOnePackageExcludePackages extends PowershellMoreThanOnePackage {
	private IsMoreThanOneJar isMoreThanOneJar;
	private String classpath;
	private List<String> includedFolders;
	public PowershellMoreThanOnePackageExcludePackages(Main main,String main_class,String dir,AllFiles allfiles,boolean _isMoreThanOneJar,String classpath,List<String> includedFolders) {
		super(main,main_class,dir,allfiles,_isMoreThanOneJar);
		this.isMoreThanOneJar=new IsMoreThanOneJar(_isMoreThanOneJar);
		this.classpath=classpath;
		this.includedFolders=includedFolders;
	}
	@Override
	public void makeJar(int javaversionnumber) {
		try {
			String main_class2 = main_class;
			if(packager.containsPackage()) {
				String[] splited=  main_class.split("\\.");
				main_class2 = splited[splited.length-1];
			}
			String createJarFolderLocation=isMoreThanOneJar.getCreateJarFolderLocation(dir);
			if(!createJarFolderLocation.endsWith("\\"))
				createJarFolderLocation=createJarFolderLocation+"\\";
			JOptionPane.showMessageDialog(null,"Output jar location is:"+createJarFolderLocation);
			if(javaversionnumber != 23 && javaversionnumber != -2) {
				output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+createJarFolderLocation+"ForJava"+javaversionnumber+"_"+main_class2+".jar mf.txt");
				main_class2=createJarFolderLocation+"ForJava"+javaversionnumber+"_"+main_class2;
			}
			else {
				output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+createJarFolderLocation+main_class2+".jar mf.txt");
				main_class2=createJarFolderLocation+main_class2;
			}
			for(String relative:includedFolders) {
				output2.write(" -C \""+classpath+"\" "+relative);
			}
			output2.write("\n");
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
	}
}