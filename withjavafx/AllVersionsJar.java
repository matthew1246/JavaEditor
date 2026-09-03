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
	public void Compile(boolean isJavaFX,int javaversionnumber);
	public Preferences extractJars(StoreSelectedFile storeselectedfile);
	public String getMain(boolean isJavaFX,StoreSelectedFile storeselectedfile,Preferences preferences);
	public void WriteManifest(String main_class);
	public boolean isMatthewJavaEditor(String main_class);
	public void MakeJarUsingmsdos(int javaversionnumber,String main_class);
	public void Powershell(boolean isJavaFX,String main_class);
}
