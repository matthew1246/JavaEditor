import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.io.IOException;
public class ExtractJUnit {
	public boolean isDriveRoot(String dir) {
		return dir.matches("^[A-Za-z]:\\$");
	}
	public boolean canWriteToDriveRoot(String dir) {
		try {
			Path probe=Paths.get(dir+"extractjunit_probe.tmp");
			Files.createFile(probe);
			Files.delete(probe);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	public void relaunchAsAdmin(String dir) {
		try {
			String userdir=System.getProperty("user.dir");
			String command;
			String exePath=getAppExePath();
			if(exePath != null) {
				command="Start-Process '"+exePath+"' -Verb RunAs";
			}
			else {
				String javaBin=System.getProperty("java.home")+"\\bin\\java.exe";
				String classpath=System.getProperty("java.class.path");
				if(classpath == null || classpath.trim().isEmpty())
					classpath=getCodeSourcePath();
				String mainclass=getApplicationMainClass();
				command="Start-Process -WorkingDirectory '"+userdir+"' -FilePath '"+javaBin+"' -ArgumentList '-cp','"+classpath+"','-Dextractjfx.elevated=true','"+mainclass+"' -Verb RunAs";
			}
			ProcessBuilder pb=new ProcessBuilder("powershell.exe","-NoProfile","-Command",command);
			pb.redirectErrorStream(true);
			Process process=pb.start();
			int exitcode=process.waitFor();
			if(exitcode!=0) {
				javax.swing.JOptionPane.showMessageDialog(null,"Administrator privileges are required to extract jars to "+dir+".\nThe elevation request was cancelled or failed.");
				return;
			}
			System.exit(0);
		} catch(Exception ex) {
			ex.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null,"Could not relaunch the program as administrator: "+ex.getMessage());
		}
	}
	public String getApplicationMainClass() {
		try {
			java.net.URL location=ExtractJUnit.class.getProtectionDomain().getCodeSource().getLocation();
			if(location != null) {
				java.io.File file=new java.io.File(location.toURI());
				if(file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
					try(java.util.jar.JarFile jf=new java.util.jar.JarFile(file)) {
						java.util.jar.Manifest manifest=jf.getManifest();
						if(manifest != null) {
							String mainclass=manifest.getMainAttributes().getValue("Main-Class");
							if(mainclass != null && !mainclass.trim().isEmpty())
								return mainclass.trim();
						}
					}
				}
			}
		} catch(Exception ex) {}
		return "Main";
	}
	public String getCodeSourcePath() {
		try {
			java.net.URL location=ExtractJUnit.class.getProtectionDomain().getCodeSource().getLocation();
			if(location == null)
				return ".";
			Path path=Paths.get(location.toURI());
			return path.toAbsolutePath().toString();
		} catch(Exception ex) {
			return ".";
		}
	}
	public String getAppExePath() {
		try {
			String javaHome=System.getProperty("java.home");
			if(javaHome == null || javaHome.isEmpty())
				return null;
			boolean jpackageRuntime=javaHome.endsWith("runtime") || javaHome.endsWith("runtime\\") || javaHome.contains("\\runtime\\");
			if(!jpackageRuntime)
				return null;
			String classpath=System.getProperty("java.class.path");
			if(classpath == null || classpath.trim().isEmpty())
				return null;
			String first=classpath.split(";")[0];
			java.io.File cpfile=new java.io.File(first);
			java.io.File appdir=cpfile.isFile() ? cpfile.getParentFile() : null;
			if(appdir == null)
				return null;
			java.io.File imagedir=appdir.getParentFile();
			if(imagedir == null)
				return null;
			java.io.File[] exes=imagedir.listFiles((java.io.File dir,String name)->name.toLowerCase().endsWith(".exe"));
			if(exes != null && exes.length > 0)
				return exes[0].getAbsolutePath();
		} catch(Exception ex) {}
		return null;
	}
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
			if(isDriveRoot(dir) && !canWriteToDriveRoot(dir) && !Boolean.getBoolean("extractjfx.elevated")) {
				relaunchAsAdmin(dir);
				return;
			}

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