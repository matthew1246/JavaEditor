package javaeditor.minorbugsfixwithai;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;

public class TestBraceCount4 {
	public static void main(String[] args) throws Exception {
		String original = Files.readString(Path.of(args[0]));
		
		// Count braces in original
		int origOpen = 0, origClose = 0;
		for(int i = 0; i < original.length(); i++) {
			if(original.charAt(i) == '{') origOpen++;
			else if(original.charAt(i) == '}') origClose++;
		}
		System.out.println("ORIGINAL: { = " + origOpen + "  } = " + origClose + "  diff = " + (origOpen - origClose));
		
		// Step 1: Block comment removal only
		String step1 = removeBlockComments(original);
		int s1Open = countChar(step1, '{'), s1Close = countChar(step1, '}');
		System.out.println("After block comments: { = " + s1Open + "  } = " + s1Close + "  removed { = " + (origOpen - s1Open) + "  removed } = " + (origClose - s1Close));
		
		// Step 2: Line comment removal
		String step2 = removeLineComments(step1);
		int s2Open = countChar(step2, '{'), s2Close = countChar(step2, '}');
		System.out.println("After line comments:  { = " + s2Open + "  } = " + s2Close + "  removed { = " + (s1Open - s2Open) + "  removed } = " + (s1Close - s2Close));
		
		// Step 3: String removal
		String step3 = removeStrings(step2);
		int s3Open = countChar(step3, '{'), s3Close = countChar(step3, '}');
		System.out.println("After strings:       { = " + s3Open + "  } = " + s3Close + "  removed { = " + (s2Open - s3Open) + "  removed } = " + (s2Close - s3Close));
		
		// Full pipeline via RemoveAll
		String full = RemoveAll.LeftCurlyBraceInsideComments(original);
		int fOpen = countChar(full, '{'), fClose = countChar(full, '}');
		System.out.println("Full pipeline:       { = " + fOpen + "  } = " + fClose);
		
		// Find differences between our step-by-step and full pipeline
		if(!step3.equals(full)) {
			System.out.println("\n*** step3 differs from full pipeline! ***");
			for(int i = 0; i < Math.min(step3.length(), full.length()); i++) {
				if(step3.charAt(i) != full.charAt(i)) {
					int line = getLine(original, i);
					System.out.println("First diff at pos=" + i + " line=" + line);
					System.out.println("  step3:  " + safeSub(step3, i));
					System.out.println("  full:   " + safeSub(full, i));
					break;
				}
			}
		}
		
		// Now find where structural braces are removed (not inside comments/strings)
		System.out.println("\n=== FINDING STRUCTURAL BRACE REMOVALS ===");
		findStructuralBraceRemoval(original, step1, "block comments");
		findStructuralBraceRemoval(original, step2, "line comments");
		findStructuralBraceRemoval(step2, step3, "strings");
	}
	
	static void findStructuralBraceRemoval(String before, String after, String phase) {
		int diffCount = 0;
		for(int i = 0; i < Math.min(before.length(), after.length()); i++) {
			char bc = before.charAt(i);
			char ac = after.charAt(i);
			if(bc != ac && (bc == '{' || bc == '}') && ac == ' ') {
				int line = getLine(before, i);
				if(diffCount < 20) {
					String ctx = before.substring(Math.max(0, i-40), Math.min(before.length(), i+40)).replace("\n","\\n");
					System.out.println("[" + phase + "] pos=" + i + " line=" + line + " removed '" + bc + "'  context: ..." + ctx + "...");
				}
				diffCount++;
			}
		}
		if(diffCount > 20) System.out.println("[" + phase + "] ... and " + (diffCount - 20) + " more");
		if(diffCount == 0) System.out.println("[" + phase + "] No structural braces removed");
	}
	
	static String removeBlockComments(String text) {
		Pattern p = Pattern.compile("(?s)/\\*.*?\\*/");
		Matcher m = p.matcher(text);
		StringBuilder sb = new StringBuilder();
		while(m.find()) {
			String c = m.group();
			c = c.replaceAll("\\{|\\}", " ");
			m.appendReplacement(sb, m.quoteReplacement(c));
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	static String removeLineComments(String text) {
		// Remove } from // comments
		Pattern p1 = Pattern.compile("\\s*//.*\\}.*");
		Matcher m1 = p1.matcher(text);
		StringBuilder sb1 = new StringBuilder();
		while(m1.find()) {
			String c = m1.group();
			c = c.replaceAll("\\}", " ");
			m1.appendReplacement(sb1, m1.quoteReplacement(c));
		}
		m1.appendTail(sb1);
		text = sb1.toString();
		
		// Remove { from // comments
		Pattern p2 = Pattern.compile("\\s*//.*\\{.*");
		Matcher m2 = p2.matcher(text);
		StringBuilder sb2 = new StringBuilder();
		while(m2.find()) {
			String c = m2.group();
			c = c.replaceAll("\\{", " ");
			m2.appendReplacement(sb2, m2.quoteReplacement(c));
		}
		m2.appendTail(sb2);
		return sb2.toString();
	}
	
	static String removeStrings(String text) {
		Pattern p = Pattern.compile("(?<!\\\\)\".*?(\\{|\\})+?.*?(?<!\\\\)\"");
		Matcher m = p.matcher(text);
		StringBuilder sb = new StringBuilder();
		while(m.find()) {
			String match = m.group();
			match = match.replaceAll("\\{|\\}", " ");
			m.appendReplacement(sb, m.quoteReplacement(match));
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	static int countChar(String text, char c) {
		int count = 0;
		for(int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == c) count++;
		}
		return count;
	}
	
	static int getLine(String text, int pos) {
		int line = 1;
		for(int i = 0; i < pos && i < text.length(); i++) {
			if(text.charAt(i) == '\n') line++;
		}
		return line;
	}
	
	static String safeSub(String text, int pos) {
		return text.substring(Math.max(0, pos-30), Math.min(text.length(), pos+30)).replace("\n","\\n");
	}
}
