import javax.swing.*;
import java.util.regex.*;
public class GetVariable {
	private JTextArea textarea;
	public GetVariable(JTextArea textarea) {
		this.textarea = textarea;
	}
	public int start;
	public String getVariable() {
		Middle middle = new Middle(textarea);
		String line = middle.getCurrentWholeLine();
		//?! means exclude matches
		//?= means look forward for matches
		String regex = "^((\\s+\\b(public|protected|private)\\b)?\\s+[a-zA-Z]+\\s+([a-zA-Z0-9]+)(?=\\s*=|;))";
		// JOptionPane.showMessageDialog(null,"if compiles");
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher= pattern.matcher(line);
		if(matcher.find()) {
			start = matcher.start(4);
			
			return matcher.group(4);
		}
		else {
			return "could not find variable";
		}
	}
}
		