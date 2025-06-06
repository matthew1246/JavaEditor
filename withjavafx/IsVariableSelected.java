import java.util.regex.*;
import javax.swing.*;
public class IsVariableSelected {

	public JTextArea textarea	;
	public IsVariableSelected(JTextArea textarea) {
		this.textarea = textarea;
	}
	
	public boolean isVariableSelected() {
		Middle middle = new Middle(textarea);
		Pattern pattern = Pattern.compile("^((\\s+\\b(public|protected|private)\\b)?\\s+[a-zA-Z]+\\s+([a-zA-Z0-9]+)(?=\\s*=|;))");
		String line = middle.getCurrentWholeLine();
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			String match2 = matcher.group(4);
			int index = middle.getCurrentLineCaretIndex();
			
			return index >= matcher.start(4) && index <= matcher.end(4);
		} else {
			return false;
		}
	}
}
			