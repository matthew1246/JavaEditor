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
public abstract class AllVersionsJar {
	public abstract String getDir();
	public abstract AllFiles getAllFiles();
	public abstract void Compile(boolean isJavaFX,int javaversionnumber);
	public abstract Preferences extractJars(StoreSelectedFile storeselectedfile);
	public abstract String getMain(boolean isJavaFX,StoreSelectedFile storeselectedfile,Preferences preferences);
	public abstract void WriteManifest(String main_class);
	public abstract boolean isMatthewJavaEditor(String main_class);
	public abstract void MakeJarUsingmsdos(int javaversionnumber,String main_class);
	public void Powershell(boolean isJavaFX,String fileName,Main main,String main_class,String dir,AllFiles allfiles) {
		Powershell powershell = getPowershell(main,main_class,dir,allfiles);
		if(!isJavaFX) { // No JavaFX
			for(int i = 18; i <= 23; i++) {
				powershell.Compile(i,fileName,isJavaFX);
				powershell.makeJar(i);
			}
		}
		else { // Has JavaFX code.
			for(int i = 22; i <= 23; i++) {
				powershell.Compile(i,fileName,isJavaFX);
				powershell.makeJar(i);
			}
		}
		powershell.Finish();
	}
	public abstract Powershell getPowershell(Main main,String main_class,String dir,AllFiles allfiles);
	public void deleteExistingMainJar(Main main,String main_class) {
		String[] splited = main_class.split("\\.");
		String main_class2 = splited[splited.length-1];

		String dir2 = Main.getDirectory(main.fileName);
		if(!dir2.endsWith("\\"))
			dir2 = dir2+"\\";
		File mainJarFile = new File(dir2+main_class2+".jar");
		if(mainJarFile.exists())
			mainJarFile.delete();

		String dir3 = getDir();
		if(!dir3.endsWith("\\"))
			dir3 = dir3+"\\";
		File dirJarFile = new File(dir3+main_class2+".jar");
		if(dirJarFile.exists())
			dirJarFile.delete();
	}
}
