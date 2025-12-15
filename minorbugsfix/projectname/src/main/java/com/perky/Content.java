package com.perky;

import javax.swing.*;
import java.util.regex.*;
class Content {
	public static void main(String[] args) {
		System.out.println("Hello World 2!");
	}
	public JTextArea textArea;
	public Content(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	/*
	** This gets all the tabs from start of string
	** until first character.
	*/
	public String PreTabs(String line)
	{
		Pattern pattern = Pattern.compile("^([\s\t]+)");
		Matcher matcher =pattern.matcher(line);
		if(matcher.find()) {
			String middletabs=matcher.group(1);
			return middletabs;
		}
		else {
			// JOptionPane.showMessageDialog(null,"Can't find start tabs space.");
			return "";
		}
	}
		
	public void NewLineCharacter() {
		Middle middle = new Middle(textArea);
		String line =middle.getWholePreviousLine();
	
		//JOptionPane.showMessageDialog(null,"*"+line+"*");
		String pretabs = PreTabs(line);
		
		middle.addMiddle(pretabs);		
	}
	public void Semicolon() {
		Middle middle = new Middle(textArea);
		String line = middle.getCurrentWholeLine();
		middle.addMiddle("\n");
		String pretabs =PreTabs(line);
		middle.addMiddle(pretabs);
	}
	public void LeftCurlyBrace() {
		Middle middle = new Middle(textArea);
		String line = middle.getCurrentWholeLine();
		//JOptionPane.showMessageDialog(null,line);
		
		middle.addMiddle("\n");
		/*
		Pattern pattern=Pattern.compile("\\p{C}");
		Matcher matcher=pattern.matcher(line);
		line=matcher.replaceAll("");
		middle.setCurrentLine(line);*/
		String pretabs =PreTabs(line);
		middle.addMiddle(pretabs+"\t");
	}
	public void RightCurlyBrace() {
		int caretposition = textArea.getCaretPosition();
  		Middle middle = new Middle(textArea);
  		String previousLine = middle.getPreviousLine();
  		String pretabs=PreTabs(previousLine);
  		pretabs = pretabs.replaceFirst("\t","");
  		String current_line =middle.getCurrentWholeLine();
  		String current_line_pretabs=PreTabs(current_line);
  		String output = current_line.replaceFirst(current_line_pretabs,pretabs);
  		middle.setCurrentLine(output);
  		textArea.setCaretPosition(caretposition+(output.length()-current_line.length()));

	}
}		
		