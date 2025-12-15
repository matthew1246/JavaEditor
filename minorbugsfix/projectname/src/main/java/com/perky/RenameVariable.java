package com.perky;

import javax.swing.JTextArea;
import java.util.regex.*;
import javax.swing.JOptionPane;
import java.io.*;
public class RenameVariable {
	public Main main;
	public RenameVariable(Main main) {
		this.main = main;
	}
	public String firstSelected;
	public int firstSelectedIndex;
	public boolean startTracking;
	public String wholetext;
	public int linenumber = -1;
	public int matcherend2;
	public int endSelectedIndex;
	public void track() {
		int caretposition = main.textarea.getCaretPosition();
		if(startTracking) {
			if(linenumber != getLineNumber(main.textarea.getText().substring(0,caretposition))) {
				startTracking = false;
			}
		}
		Middle middle = new Middle(main.textarea);
		// "^((\\s+\\b(public|protected|private)\\b)?\\s+[a-zA-Z<>]+\\s+([a-zA-Z0-9]+)(?=\\s*=|;))"
		// "^((\\s+\\b(public|protected|private)\\b)?\\s+\\b(?!return\\b)[a-zA-Z<>]+\\b\\s+([a-zA-Z0-9]+)(?=\\s*=|;))"
		Pattern pattern = Pattern.compile("^((\\s+\\b(public|protected|private)\\b)?\\s+(?!return)[a-zA-Z<>\\[\\]]+\\s+([a-zA-Z0-9]+)(?=\\s*=|;))");
		String line = middle.getCurrentWholeLine();
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			if(!startTracking) {
				//JOptionPane.showMessageDialog(null,"Started");
					
				int index = middle.getCurrentLineCaretIndex();
				int matcherend1=matcher.end(4);
				startTracking= index >= matcher.start(4) && index <= matcherend1;
				if(startTracking) {
					firstSelected = matcher.group(4);
					firstSelectedIndex = caretposition;
					matcherend2 = matcherend1;
					
					endSelectedIndex = caretposition+matcherend1-index;
					
					wholetext=main.textarea.getText();
					linenumber = getLineNumber(main.textarea.getText().substring(0,caretposition));
				}
			}
		} else {
			startTracking= false;
		}
	}
	public int getLineNumber(String stringuptocaretposition) {
		int linenumber2=0;
		try {
			LineNumberReader linenumberreader=new LineNumberReader(new StringReader(stringuptocaretposition));
			while(linenumberreader.readLine() != null) {
				linenumber2=linenumberreader.getLineNumber();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return linenumber2;
	}
	public void replace() {
		int caretposition = main.textarea.getCaretPosition();
		if(linenumber == getLineNumber(main.textarea.getText().substring(0,caretposition))) {
			Middle middle = new Middle(main.textarea);
			// "^((\\s+\\b(public|protected|private)\\b)?\\s+[a-zA-Z<>]+\\s+([a-zA-Z0-9]+)(?=\\s*=|;))"
			// "^((\\s+\\b(public|protected|private)\\b)?\\s+\\b(?!return\\b)[a-zA-Z<>]+\\b\\s+([a-zA-Z0-9]+)(?=\\s*=|;))"
			Pattern pattern = Pattern.compile("^((\\s+\\b(public|protected|private)\\b)?\\s+(?!return)[a-zA-Z<>\\[\\]]+\\s+([a-zA-Z0-9]+)(?=\\s*=|;))");
			String line = middle.getCurrentWholeLine();
			Matcher matcher = pattern.matcher(line);
			if(matcher.find()) {
				if(startTracking) {
					int index = middle.getCurrentLineCaretIndex();
					int matcherend = matcher.end(4);
					boolean result= index >= matcher.start(4) && index <= matcherend;
					if(result) {
						String selected = matcher.group(4);
						
						String originalfirsthalf=wholetext.substring(0,endSelectedIndex);
						String replacedfirsthalf=originalfirsthalf.replace(firstSelected,selected);
						int difference =replacedfirsthalf.length()-originalfirsthalf.length();
						//difference=difference+matcherend-matcherend2;
						//difference=difference+selected.length()-firstSelected.length();
						difference=difference+index-matcherend;
						
						String replacement=wholetext.replace(firstSelected,selected);
						main.textarea.setText(replacement);
						main.textarea.setCaretPosition(endSelectedIndex+difference);
						/*main.textarea.setSelectionStart(caretposition+(caretposition+difference);
						main.textarea.setSelectionEnd(caretposition+difference);*/
					}
				}
			}
			else {
				startTracking = false;
			}    
		}
		else {
			startTracking= false;
		}
	}	
	/*public int getCaretPosition(int linenumber,int index,String afterreplace) {
		/*int wholedocumentindex= getStartOfLineIndex(linenumber,afterreplace);
		//return wholedocumentindex+index;
		return wholedocumentindex;
	}*/
	/*public int getStartOfLineIndex(int linenumber,String afterreplace) {
		String uptoline = "";
		int linenumber2=0;
		try {
			LineNumberReader linenumberreader=new LineNumberReader(new StringReader(afterreplace));
			while(true) {
				uptoline+=linenumberreader.readLine();
				linenumber2=linenumberreader.getLineNumber();
				if(linenumber2 == linenumber) break;
				uptoline+="\n";
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("*"+uptoline+"*");
		return uptoline.length();
	}*/
}
