import javax.swing.JOptionPane;
import java.util.List;
import java.io.File;
import java.io.IOException;
/*
** This generates all versions of Java for Jars when Main.jar is open.
** The user selected folders to exclude or include for the jar.
** The included folders are added to the jar with:
** jar.exe ... -C <classpath> <relative folder path> ...
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
			String main_class3 = main_class;
			if(main_class3.contains(".")) {
				String[] split=main_class3.split("\\.");
				if(split != null && split.length > 0)
					main_class3=split[split.length-1];
			}		
			if(hasJavaFX) {
				if(main_class3.endsWith("two"))
					main_class3=main_class3.substring(0,main_class3.length()-3);
			}
			String createJarFolder=isMoreThanOneJar.getCreateJarFolderLocation(dir);
			if(!createJarFolder.endsWith("\\"))
				createJarFolder=createJarFolder+"\\";
			JOptionPane.showMessageDialog(null,"Create jar location is:"+createJarFolder);
			
			if(javaversionnumber != -2) {
				if(hasJavaFX) {
					output2.write("START /B /WAIT cmd.exe /c "+"\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+createJarFolder+"HasJavaFX_ForJava"+javaversionnumber+"_Windows11x64.jar mf.txt");
					folderPlusFileName=createJarFolder+"HasJavaFX_ForJava"+javaversionnumber+"_Windows11x64.jar";
				}
				else {
					output2.write("START /B /WAIT cmd.exe /c "+"\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+createJarFolder+"ForJava"+javaversionnumber+"_"+main_class3+".jar mf.txt");
					folderPlusFileName=createJarFolder+"ForJava"+javaversionnumber+"_"+main_class3+".jar";
				}
			}
			else {
				output2.write("START /B /WAIT cmd.exe /c "+"\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+createJarFolder+main_class3+".jar mf.txt");
				folderPlusFileName=createJarFolder+main_class3+".jar";
			}
			for(String relative:includedFolders) {
				output2.write(" -C "+classpath+" "+relative);
			}
			if(new File(classpath+"\\jars").exists()) {
				output2.write(" -C jars .");
			}
			output2.write("\n");
			
			// output2.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}