import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Packager {
	private Main main;
	public Packager(Main main) {
		this.main = main;
	}
	public String regex = "\\s*package\\s+([\\w.]+)\\s*\\;";
	public boolean containsPackage() {
		Pattern pattern=Pattern.compile(regex,Pattern.MULTILINE);	
		Matcher matcher=pattern.matcher(main.textarea.getText());
		return matcher.find();
	}
	public String getPackageName() {
		Pattern pattern=Pattern.compile(regex,Pattern.MULTILINE);	
		Matcher matcher=pattern.matcher(main.textarea.getText());
		if(matcher.find()) {
			return matcher.group(1);
		}
		else {
			return "Could not find package name.";
		}									
	}
}

