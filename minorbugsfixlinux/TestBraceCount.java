import java.util.*;
import java.io.*;
import java.nio.file.*;

public class TestBraceCount {
	public static void main(String[] args) throws Exception {
		String wholetext = Files.readString(Path.of(args[0]));
		String original = wholetext;
		wholetext = RemoveAll.LeftCurlyBraceInsideComments(wholetext);
		
		Stack<Integer> stack = new Stack<Integer>();
		LinkedList<String> classNames = new LinkedList<String>();
		LinkedList<String> foundClasses = new LinkedList<String>();
		String classname = "unknown";
		int lastPop = -1;
		
		for(int i = 0; i < wholetext.length(); i++) {
			String str = wholetext.substring(i,i+1);
			switch(str) {
				case "{":
					stack.push(i);
					if(stack.size() == 1) {
						classname = getClassNameAt(wholetext, i);
					}
				break;
				case "}":
					if(!stack.isEmpty()) {
						lastPop = (Integer)stack.pop();
						if(stack.size() == 0) {
							foundClasses.add(classname + " (pos=" + lastPop + ")");
							System.out.println("FOUND CLASS: " + classname + " at position " + lastPop);
						}
					} else {
						int lineNum = getLine(wholetext, i);
						int origLineNum = getLine(original, i);
						System.out.println("EXTRA } at pos=" + i + " (line " + lineNum + ", orig line " + origLineNum + ") stack was empty!");
						String context = wholetext.substring(Math.max(0, i-40), Math.min(wholetext.length(), i+40));
						System.out.println("  context: ..." + context.replace("\n","\\n") + "...");
					}
				break;
			}
		}
		
		System.out.println("\n=== ALL FOUND CLASSES ===");
		for(String c : foundClasses) {
			System.out.println("  " + c);
		}
		
		System.out.println("\n=== CHECKING SPECIFIC CLASSES ===");
		String[] checkClasses = {"Main", "Expandable", "OpenDefaultContent", "SaveActionListener", 
			"OpenActionListener", "CurlyBraceKeyListener", "AutoKeyListener", 
			"MethodSuggestionBox", "RightClick", "RightClickJFrame", "MyCaretListener", "Caret_Tracker"};
		for(String name : checkClasses) {
			boolean found = false;
			for(String c : foundClasses) {
				if(c.startsWith(name + " ")) {
					found = true;
					break;
				}
			}
			System.out.println("  " + name + ": " + (found ? "FOUND" : "MISSING"));
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
		
		return "UNRESOLVED(pos=" + bracePos + ", line='" + currentLine.trim() + "')";
	}
}
