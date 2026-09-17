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
public interface AllVersionsJar {
	public String getDir();
	public AllFiles getAllFiles();
	public void Compile(int javaversionnumber);
	public Preferences extractJars(StoreSelectedFile storeselectedfile);
	public String getMain(StoreSelectedFile storeselectedfile,Preferences preferences);
	public void WriteManifest(String main_class);
	public boolean isMatthewJavaEditor(String main_class);
	public void MakeJarUsingmsdos(int javaversionnumber,String main_class);
	public void Powershell(String main_class);
	public default void deleteExistingMainJar(Main main,String main_class) {
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
