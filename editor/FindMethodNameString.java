import java.util.regex.*;
import javax.swing.*;
public class FindMethodNameString {
	private String wholetext;
	public FindMethodNameString(String wholetext) {
		this.wholetext = wholetext;
	}
	
	public String getMethodName(int index) {
		MiddleString middle = new MiddleString(wholetext);
		String currentLine=middle.getLineFromWholeDocumentIndex(index);
		if( StringHasMethodName(currentLine) ) {
			return parseMethodName(currentLine);
		}
		else {
			String previousLine = middle.getPreviousLine(index);
			return parseMethodName(previousLine);
		}
	}
	
	public boolean StringHasMethodName(String line) {
		
		return line.contains("(") && line.contains(")");
	}
	
	public String parseMethodName(String subline) {
		Pattern pattern = Pattern.compile("([a-zA-Z]+)\\(");
		Matcher matcher =pattern.matcher(subline);
		if(matcher.find()) {
			return matcher.group(1);
		}
		else {
			return "Couldn't parse method line";
		}
	}	
}
