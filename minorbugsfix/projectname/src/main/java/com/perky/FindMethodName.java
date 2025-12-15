package com.perky;

import java.util.regex.*;
import javax.swing.*;
public class FindMethodName {
	public Middle middle;
	public FindMethodName(JTextArea textarea) {
		middle = new Middle(textarea);
	}
	public FindMethodName(String wholetext) {
		middle = new Middle(wholetext);
	}
	
	public String getMethodName(int index) {
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
			