import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.io.IOException;
public class ExtractJUnit {
	public Main main;
	protected Packager packager;
	public ExtractJUnit(Main main) {
		this.main = main;
		this.packager=new Packager(main);
		ExtractJar("junit-4.13.2.jar");
		ExtractJar("hamcrest-core-1.3.jar");
	}
	public void ExtractJar(String jar) {
		try {
			URL url=ExtractJUnit.class.getClassLoader().getResource(jar);	
			InputStream inputstream=url.openStream();
			String dir = "";
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				dir=main.getDirectory(main.fileName);
			}
			else { // packager.isInRightFolders() == true
				dir=packager.classpath;
			}
			if(!dir.endsWith("\\"))
				dir=dir+"\\";
			Path outputpath=Paths.get(dir+jar);
					
			Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}		
}
