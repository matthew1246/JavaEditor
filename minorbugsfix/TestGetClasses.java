import java.util.*;
import java.io.*;
import java.nio.file.*;

public class TestGetClasses {
	public static void main(String[] args) throws Exception {
		String text = Files.readString(Path.of(args[0]));
		String preprocessed = RemoveAll.LeftCurlyBraceInsideComments(text);
		
		ArrayList<String> classes = GetClassMethods.getClasses(preprocessed);
		
		System.out.println("Classes found: " + classes.size());
		for(int i = 0; i < classes.size(); i++) {
			System.out.println("  " + (i+1) + ". " + classes.get(i));
		}
		
		// Check specifically for classes that were previously missing
		String[] expected = {"Main", "MethodSuggestionBox", "CurlyBraceKeyListener", "Caret_Tracker"};
		System.out.println("\n=== Checking specific classes ===");
		for(String name : expected) {
			boolean found = classes.contains(name);
			System.out.println(name + ": " + (found ? "FOUND" : "MISSING"));
		}
	}
}
