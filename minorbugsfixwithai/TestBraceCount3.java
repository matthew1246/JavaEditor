import java.util.*;
import java.io.*;
import java.nio.file.*;

public class TestBraceCount3 {
	public static void main(String[] args) throws Exception {
		String original = Files.readString(Path.of(args[0]));
		String processed = RemoveAll.LeftCurlyBraceInsideComments(original);
		
		// Count braces in original vs processed
		int origOpen = 0, origClose = 0, procOpen = 0, procClose = 0;
		for(int i = 0; i < original.length(); i++) {
			if(original.charAt(i) == '{') origOpen++;
			else if(original.charAt(i) == '}') origClose++;
		}
		for(int i = 0; i < processed.length(); i++) {
			if(processed.charAt(i) == '{') procOpen++;
			else if(processed.charAt(i) == '}') procClose++;
		}
		
		System.out.println("ORIGINAL:  { = " + origOpen + "  } = " + origClose + "  diff = " + (origOpen - origClose));
		System.out.println("PROCESSED: { = " + procOpen + "  } = " + procClose + "  diff = " + (procOpen - procClose));
		System.out.println("Removed { = " + (origOpen - procOpen) + "  Removed } = " + (origClose - procClose));
		
		// Find where braces differ between original and processed
		System.out.println("\n=== FINDING WHERE } IS REMOVED BUT { IS NOT ===");
		int origDepth = 0;
		int procDepth = 0;
		int diffCount = 0;
		for(int i = 0; i < Math.min(original.length(), processed.length()); i++) {
			char origC = original.charAt(i);
			char procC = processed.charAt(i);
			
			if(origC == '{') origDepth++;
			else if(origC == '}') origDepth--;
			
			if(procC == '{') procDepth++;
			else if(procC == '}') procDepth--;
			
			if(origDepth != procDepth && diffCount < 30) {
				int line = getLine(original, i);
				String context = original.substring(Math.max(0, i-30), Math.min(original.length(), i+30)).replace("\n","\\n");
				System.out.println("DIFF at pos=" + i + " line=" + line + " origDepth=" + origDepth + " procDepth=" + procDepth);
				System.out.println("  context: ..." + context + "...");
				diffCount++;
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
}
