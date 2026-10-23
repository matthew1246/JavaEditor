import java.io.File;
public class IsMoreThanOneJar {
	public boolean isMoreThanOneJar;
	public IsMoreThanOneJar(boolean isMoreThanOneJar) {
		this.isMoreThanOneJar=isMoreThanOneJar;
	}
	public String getCreateJarFolderLocation(String dir) {
		if(!isMoreThanOneJar) { // Create only One Jar
			if(!dir.endsWith(Platform.separator()))
				dir=dir+Platform.separator();
			return dir;
		}	
		else { // When creating multiple jars
			File folder = new File(dir);
			dir= folder.getParentFile().getAbsolutePath();
			if(!dir.endsWith(Platform.separator()))
				dir=dir+Platform.separator();
			return dir;
		}
	}	
}
