package javaeditor.minorbugsfix;

import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
public interface Powershell {
	public void Compile(int javaversionnumber,String fileName);
	public void makeJar(int javaversionnumber);
	public void Finish();
}
