package com.perky;

import javax.swing.*;
import java.util.regex.*;
public class IsVariable {
	protected JTextArea textarea;
	public IsVariable(JTextArea textarea) {
		this.textarea = textarea;
	}
	public boolean isVariable() {
		int cursor=textarea.getCaretPosition();
		Middle middle = new Middle(textarea);
		
		String regex = "^((\\s+\\b(public|protected|private)\\b)?\\s+((?!return)([a-zA-Z]+))\\s+[a-zA-Z0-9]+(?=\\s*=|;))";
		
		String line = middle.getCurrentWholeLine();
		
		
		
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		return matcher.find();
	}
}
		