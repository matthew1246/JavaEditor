import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
public interface Powershell {
	public void Compile(int javaversionnumber,String fileName);
	public void makeJar(int javaversionnumber);
	public void Finish();
}
