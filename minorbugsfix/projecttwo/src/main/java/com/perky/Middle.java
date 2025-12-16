package com.perky;

import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.util.*;
class Middle {
	public String[] first_half_lines;
	private JTextArea textarea;
	private String current_line;
	private String first;
	public String second;
	private String wholetext;
	Middle(JTextArea textarea) {
		this.textarea = textarea;
	 	int index = textarea.getCaretPosition();
		String text = textarea.getText();
	 	first = text.substring(0,index);
	 	first_half_lines = first.split("\n");
	 	if(first_half_lines.length == 0) {
	 		current_line=first;	
 		}
 		else {
	 		current_line = first_half_lines[first_half_lines.length-1];
 		}
		second = text.substring(index,text.length());
	}
	
	Middle(String wholetext) {
		this.wholetext = wholetext;
	}
	
	public String getTopLine() {
		int start = textarea.getSelectionStart();
		return getWholeLine2(start);
	}
	
	public void setTopLine(String line) {
		int start = textarea.getSelectionStart();
		String text = textarea.getText();
		String first=text.substring(0,start);
		String second = text.substring(start,text.length());
		if(!first.endsWith("\n")) {
			String[] firstlines = first.split("\n");
			List<String> wholetext = new ArrayList<String>();
			for(int i = 0; i < (firstlines.length -1); i++) {
				wholetext.add(firstlines[i]);
			}
			wholetext.add(line);
			String[] secondlines = second.split("\n");
			for(int i = 1; i < firstlines.length; i++) {
				wholetext.add(secondlines[i]);
			}
			String lines=String.join("\n",wholetext);
			textarea.setText(lines);
		}
		else {
			JOptionPane.showMessageDialog(null,"Middle.setTopLine() needs change.");
		}
	}
	
	public String getWholeLine2(int index) {
		String text = textarea.getText();
		String first = text.substring(0,index);
		String second = text.substring(index,text.length());
		if(first.endsWith("\n")) {
			String[] lines = second.split("\n");
			return lines[0];
		}
		else { // !first.endsWith("\n")
			String[] lines = second.split("\n");
			String[] first_lines = first.split("\n");
			return first_lines[first_lines.length-1]+lines[0];
		}
	}
	
	public String getWholePreviousLine() {
		if(first_half_lines.length == 0) {
			return first;		
		}
		else { // Normal execution
			return first_half_lines[first_half_lines.length-1];
		}
	}
	
	public String getCurrentWholeLine() {
		String[] second_half_lines = second.split("\n");
		String first_half4 = "";
		if(first_half_lines.length == 0) {
			first_half4 = first;
		}
		else { // Normal execution.
			first_half4 = first_half_lines[first_half_lines.length-1];
		}
		String second_half4 = "";
		if(second_half_lines.length == 0) {
			second_half4=first_half4;
		}
		else { // Normal execution
			second_half4=second_half_lines[0];
		}
		return first_half4+second_half4;
	}
	
	public int getCurrentLineCaretIndex() {
		String firsthalfcurrentline = getCurrentLine();
		int index = firsthalfcurrentline.length();
		return index;
	}
	
	public int getWholeDocumentIndexFromCurrentLineIndex(int currentlineindex) {
		String[] lines = new String[first_half_lines.length-1];
		for(int i = 0; i < lines.length; i++) {
			lines[i] = first_half_lines[i];
		}
		String prelines=String.join("\n",lines);
		return prelines.length()+currentlineindex;
	}
	/*
	** This is opposite of getWholeDocumentIndexFromCurrentLineIndex()
	*/
	public int getLineIndexFromWholeDocumentIndex(int wholedocumentindex) {
		String line = getLineFromWholeDocumentIndex(wholedocumentindex);
		return line.length();
	}
	
	public String getLineFromWholeDocumentIndex(int wholedocumentindex) {
		String firsthalf;
		if(textarea != null) {
			firsthalf = textarea.getText().substring(0,wholedocumentindex);
		}
		else {
			firsthalf = wholetext.substring(0,wholedocumentindex);
		}
		String[] lines = firsthalf.split("\n");
		return lines[lines.length-1];
	}
	
	public String getPreviousLine() {
		return first_half_lines[first_half_lines.length-2];
	}
	
	/*
	** wholedocumentindex is current line. Returned String is previous line
	** from wholedocumentindex.
	*/
	public String getPreviousLine(int wholedocumentindex) {
		if(wholedocumentindex == -1) 
			return "There is no previous line for Middle object.";
		String firsthalf;
		if(textarea != null) {
			firsthalf = textarea.getText().substring(0,wholedocumentindex);
		}
		else {
			firsthalf=wholetext.substring(0,wholedocumentindex);
		}
		String[] lines = firsthalf.split("\n");
		if(lines.length == 1)
			return "No previous Middle object line 2!";
		return lines[lines.length-2];
	}
	/*
	** This only gets first half of current line.
	*/
	public String getCurrentLine() {
		return current_line;
	}
	public void setCurrentLine(String line) {
		first_half_lines[first_half_lines.length-1] = line;
		String first_half2 = "";
		for(int i = 0; i < (first_half_lines.length-1); i++) {
			first_half2 =first_half2+first_half_lines[i]+"\n";
		}
		first_half2+= first_half_lines[first_half_lines.length-1];
		textarea.setText(first_half2+second);
	}
	
	public void addMiddle(String middle) {
		textarea.setText(first+middle+second);
		first = first+middle;
		textarea.setCaretPosition(first.length());
	}
	
	public String getCurrentLineFromWholeIndex(int wholeindex) {
		if(first.endsWith("\n")) {
			String[] second_lines = second.split("\n");
			return second_lines[0];
		}
		else {
			return first_half_lines[first_half_lines.length-1];
		}
	}	
	
	public void setWholeCurrentLine(String line) {
		String[] second_lines = second.split("\n");
		if(!first.endsWith("\n")) {
			first_half_lines[first_half_lines.length-1] = line;
			String first_half2 = "";
			for(int i = 0; i < (first_half_lines.length-1); i++) {
				first_half2 =first_half2+first_half_lines[i]+"\n";
			}
			first_half2+= first_half_lines[first_half_lines.length-1];
			textarea.setText(first_half2+second);
		}
		else {
			second_lines[0] = line;
			second=String.join("\n",second_lines);
			textarea.setText(first+second);
		}
	}		
}