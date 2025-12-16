package com.perky;

import java.util.regex.*;
public class TestIsVariable {
	public static void main(String[] args) {
		isMatch("public static void main(String[] args)");
		isMatch("String main;");	
		isMatch("public String main;");
		isMatch("String main=\"Hello World!\";");
		isMatch("String main = \"Hello World!\";");			
	}
	public static void isMatch(String input) {
		String regex = "^((\\s*\\b(public|protected|private)\\b)?\\s*[a-zA-Z]+\\s+[a-zA-Z]+(?=\\s*=|;))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		System.out.println(input+ " is "+matcher.find());
	}
}
			