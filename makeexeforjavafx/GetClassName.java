import javax.swing.*;
import java.util.regex.*;
public class GetClassName {
	private Middle middle;
	private JTextArea textarea;
	private String wholetext;
	public GetClassName(JTextArea textarea) {
		this.textarea = textarea;
		middle = new Middle(textarea);
	}
	public GetClassName(String wholetext) {
		this.wholetext = wholetext;
		middle = new Middle(wholetext);
	}
	
	public String getClassName() {
		Pattern pattern = Pattern.compile("(\b(public)\b)?\s+class\s+[a-bA-Z]+");
		String wholetext;	
		if(textarea != null) {
			wholetext=textarea.getText();
		}
		else {
			wholetext =this.wholetext;
		}
		for(int i = 0; i < wholetext.length(); i++) {
			if( wholetext.substring(i,i+1).equals("{") ) {
				String classname = getClassName(i);
				return classname;
			}
		}
		return "Can't find class name.";
	}
	
	public String getClassName(int index) {
		String currentLine=middle.getLineFromWholeDocumentIndex(index);
		if( StringHasClassName(currentLine) ) {
			return parseClassName(currentLine);
		}
		else {
			String previousLine = middle.getPreviousLine(index);
			return parseClassName(previousLine);
		}
	}
	
	public boolean StringHasClassName(String line) {
		return line.contains("class");
	}
	
	public String parseClassName(String subline) {
		Pattern pattern = Pattern.compile("class\\s+([a-zA-Z]+)");
		Matcher matcher =pattern.matcher(subline);
		if(matcher.find()) {
			return matcher.group(1);
		}
		else {
			return "Couldn't parse class name line";
		}
	}
	
	public boolean isMainClass(int index) {
		String currentLine=middle.getLineFromWholeDocumentIndex(index);
		if(parseMainClass(currentLine)) {
			return true;
		}
		else {
			String previousLine = middle.getPreviousLine(index);
			return parseMainClass(previousLine);
		}
	}
	
	public boolean parseMainClass(String line) {
		Pattern pattern = Pattern.compile("public\\s+class\\s+[a-zA-Z]+");
		Matcher matcher = pattern.matcher(line);
		return matcher.find();
	}
}
