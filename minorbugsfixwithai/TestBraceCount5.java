package javaeditor.minorbugsfixwithai;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class TestBraceCount5 {
    public static void main(String[] args) throws Exception {
        String original = Files.readString(Path.of(args[0]));
        String processed = RemoveAll.LeftCurlyBraceInsideComments(original);
        
        for(int i = 0; i < Math.min(original.length(), processed.length()); i++) {
            if(original.charAt(i) != processed.charAt(i)) {
                int line = getLine(original, i);
                System.out.println("DIFF at pos=" + i + " line=" + line);
                System.out.println("  original char: '" + original.charAt(i) + "'");
                System.out.println("  processed char: '" + processed.charAt(i) + "'");
                int start = Math.max(0, i-60);
                int end = Math.min(original.length(), i+20);
                System.out.println("  ORIGINAL context: " + original.substring(start, end).replace("\n","\\n"));
                start = Math.max(0, i-60);
                end = Math.min(processed.length(), i+20);
                System.out.println("  PROCESSED context: " + processed.substring(start, end).replace("\n","\\n"));
                System.out.println();
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
