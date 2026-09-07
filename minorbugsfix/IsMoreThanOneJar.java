import java.io.File;
import org.junit.*;
public class IsMoreThanOneJar {
	private boolean isMoreThanOneJar;
	public IsMoreThanOneJar(boolean isMoreThanOneJar) {
		this.isMoreThanOneJar=isMoreThanOneJar;
	}
	public String getCreateJarFolderLocation(String dir) {
		if(!isMoreThanOneJar) { // Create only One Jar
			return dir;
		}	
		else { // When creating multiple jars
			File folder = new File(dir);
			return folder.getParentFile().getAbsolutePath();
		}
	}	
}
