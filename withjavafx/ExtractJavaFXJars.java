import java.util.List;
import java.io.File;
public class ExtractJavaFXJars {
	public Main main;
	public ExtractJavaFXJars(Main main) {	
		this.main = main;
		if(!isAlreadyExtracted()) {
			extractJars();
		}
	}
	public void extractJars() {
		CommandLine commandline = new CommandLine();
		List<String> jars=commandline.getJavaFX();
		String dir=main.getDirectory(main.fileName);	
		for(String jar:jars) {
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