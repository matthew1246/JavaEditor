package javaeditor.minorbugsfixwithai;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class TestBraceCount2 {
	public static void main(String[] args) throws Exception {
		String wholetext = Files.readString(Path.of(args[0]));
		String original = wholetext;
		wholetext = RemoveAll.LeftCurlyBraceInsideComments(wholetext);
		
		Stack<Integer> stack = new Stack<Integer>();
		String classname = "unknown";
		
		boolean pastAutoKeyListener = false;
		
		for(int i = 0; i < wholetext.length(); i++) {
			String str = wholetext.substring(i,i+1);
			switch(str) {
				case "{":
					stack.push(i);
					if(stack.size() == 1) {
						classname = getClassNameAt(wholetext, i);
						if(classname.equals("MethodSuggestionBox")) {
							System.out.println(">>> FOUND MethodSuggestionBox { at pos=" + i + " (line " + getLine(wholetext, i) + ")");
						}
					}
				break;
				case "}":
					if(!stack.isEmpty()) {
						int leftcurlybrace = (Integer)stack.pop();
						if(stack.size() == 0) {
							System.out.println("CLASS CLOSED: " + classname + " at pos=" + leftcurlybrace);
							if(classname.equals("AutoKeyListener")) {
								pastAutoKeyListener = true;
								System.out.println("  *** Stack is now empty after AutoKeyListener. Next { should start a new class.");
								System.out.println("  Stack empty? " + stack.isEmpty());
							}
						}
					}
				break;
			}
		}
		
		System.out.println("\nTotal stack size at end: " + stack.size());
		
		// Now check: does the preprocessed text around MethodSuggestionBox line look correct?
		int msbPos = original.indexOf("class MethodSuggestionBox");
		int msbPosProcessed = wholetext.indexOf("class MethodSuggestionBox");
		System.out.println("\n=== MethodSuggestionBox in ORIGINAL text ===");
		System.out.println("Position: " + msbPos);
		System.out.println("Line: " + getLine(original, msbPos));
		System.out.println("Context: " + original.substring(msbPos, msbPos + 50));
		
		System.out.println("\n=== MethodSuggestionBox in PROCESSED text ===");
		System.out.println("Position: " + msbPosProcessed);
		System.out.println("Line: " + getLine(wholetext, msbPosProcessed));
		if(msbPosProcessed >= 0) {
			System.out.println("Context: " + wholetext.substring(msbPosProcessed, msbPosProcessed + 50));
		} else {
			System.out.println("NOT FOUND IN PROCESSED TEXT!");
			// Search for what's at that area
			int autoClose = wholetext.indexOf("class AutoKeyListener");
			if(autoClose >= 0) {
				// Find end of AutoKeyListener
				int autoBrace = wholetext.indexOf("{", autoClose);
				int depth = 1;
				for(int j = autoBrace + 1; j < wholetext.length(); j++) {
					char c = wholetext.charAt(j);
					if(c == '{') depth++;
					else if(c == '}') {
						depth--;
						if(depth == 0) {
							System.out.println("AutoKeyListener ends at pos=" + j + " (line " + getLine(wholetext, j) + ")");
							System.out.println("Next 200 chars after AutoKeyListener close:");
							String after = wholetext.substring(j, Math.min(wholetext.length(), j + 200));
							System.out.println(after.replace("\n", "\\n"));
							break;
						}
					}
				}
			}
		}
	}
	
	static int getLine(String text, int pos) {
		int line = 1;
		for(int i = 0; i < pos && i < text.length(); i++) {
			if(text.charAt(i) == '\n') line++;
		}
		return line;
	}
	
	static String getClassNameAt(String text, int bracePos) {
		int lineStart = text.lastIndexOf('\n', bracePos - 1) + 1;
		String currentLine = text.substring(lineStart, bracePos + 1);
		
		if(currentLine.contains("class")) {
			java.util.regex.Pattern p = java.util.regex.Pattern.compile("class\\s+([a-zA-Z0-9_]+)");
			java.util.regex.Matcher m = p.matcher(currentLine);
			if(m.find()) return m.group(1);
		}
		
		int prevLineStart = text.lastIndexOf('\n', lineStart - 1) + 1;
		String prevLine = text.substring(prevLineStart, lineStart);
		
		if(prevLine.contains("class")) {
			java.util.regex.Pattern p = java.util.regex.Pattern.compile("class\\s+([a-zA-Z0-9_]+)");
			java.util.regex.Matcher m = p.matcher(prevLine);
			if(m.find()) return m.group(1);
		}
		
		return "UNRESOLVED(pos=" + bracePos + ")";
	}
}
