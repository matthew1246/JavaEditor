import java.util.List;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.io.InputStream;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
public class ExtractJavaFXJars {
	public Main main;
	public ExtractJavaFXJars(Main main) {	
		this.main = main;
		if(!isAlreadyExtracted()) {
			extractJars();
		}
		if(!strangeFilesExtracted()) {
			extractStrangeFiles();
		}
		if(!dllFilesExtracted()) {
		}
	}
	public boolean dllFilesExtracted() {
		if(!fileExists("api-ms-win-core-console-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-console-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-datetime-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-debug-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-errorhandling-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-file-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-file-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-file-l2-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-handle-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-heap-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-interlocked-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-libraryloader-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-localization-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-memory-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-namedpipe-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-processenvironment-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-processthreads-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-processthreads-l1-1-1.dll"))
			return false;
		if(!fileExists("api-ms-win-core-profile-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-rtlsupport-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-string-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-synch-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-synch-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-sysinfo-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-timezone-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-util-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-conio-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-convert-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-environment-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-filesystem-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-heap-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-locale-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-math-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-multibyte-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-private-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-process-l1-1-0.dll"))
			return false;
		return true;
	}
	public boolean fileExists(String dll) {
		String dir=main.getDirectory(main.fileName);		
		File file = new File(dir+dll);
		return file.exists();
	}
	public void extractStrangeFiles() {
		try {	
			String dir=main.getDirectory(main.fileName);	
			URL url=ExtractJavaFXJars.class.getClassLoader().getResource("javafx.properties");	
			InputStream inputstream=url.openStream();
			Path outputpath=Paths.get(dir+"javafx.properties");
			
			Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public boolean strangeFilesExtracted() {
		String dir=main.getDirectory(main.fileName);		
		File file = new File(dir+"javafx.properties");
		return file.exists();
	}
	public void extractJars() {	
		try {
			CommandLine commandline = new CommandLine();
			List<String> jars=commandline.getJavaFX();
			String dir=main.getDirectory(main.fileName);	
			for(String jar:jars) {
				URL url=ExtractJavaFXJars.class.getClassLoader().getResource(jar);	
				InputStream inputstream=url.openStream();
				Path outputpath=Paths.get(dir+jar);
						
				Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}		
	public boolean isAlreadyExtracted() {
		CommandLine commandline = new CommandLine();
		List<String> jars=commandline.getJavaFX();
		String dir=main.getDirectory(main.fileName);	
		for(String jar:jars) {
			File file = new File(dir+jar);
			if(!file.exists())
				return false;
		}
		return true;
	}
}
