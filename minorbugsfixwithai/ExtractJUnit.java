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
			if(Files.exists(outputpath))
				return;

			String resPath = ExtractJUnit.class.getPackage().getName().replace('.','/') + "/" + jar;
			String jarPath = "";
			try {
				java.net.URI jarUri = ExtractJUnit.class.getProtectionDomain().getCodeSource().getLocation().toURI();
				jarPath = jarUri.getPath();
				if(jarPath.startsWith("/"))
					jarPath=jarPath.substring(1,jarPath.length());
			} catch(Exception ex) {}

			if(!jarPath.isEmpty()) {
				try(java.util.jar.JarFile jf = new java.util.jar.JarFile(jarPath)) {
					java.util.jar.JarEntry entry = jf.getJarEntry(resPath);
					if(entry == null)
						entry = jf.getJarEntry(jar);
					if(entry != null) {
						try(InputStream is = jf.getInputStream(entry)) {
							Files.copy(is,outputpath,StandardCopyOption.REPLACE_EXISTING);
						}
						return;
					}
				}
			}

			URL url=ExtractJUnit.class.getResource("/" + resPath);
			if(url == null)
				url=ExtractJUnit.class.getClassLoader().getResource(resPath);
			if(url == null)
				url=ExtractJUnit.class.getClassLoader().getResource(jar);
			if(url == null)
				url = ExtractJUnit.class.getResource("/" + jar);
			if(url != null) {
				try(InputStream inputstream=url.openStream()) {
					Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
				}
				return;
			}
			System.err.println(jar + " not found, skipping extraction.");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}		
}
