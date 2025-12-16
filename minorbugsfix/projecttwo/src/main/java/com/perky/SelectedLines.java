package com.perky;

import javax.swing.JTextArea;
import javax.swing.JOptionPane;
public class SelectedLines {
    private Main main;
    public SelectedLines(Main main)
    {
	  this.main = main;
    }

    public void ShiftTab() {
	  	start=main.textarea.getSelectionStart();
		end= main.textarea.getSelectionEnd();
	  	// javax.swing.JOptionPane.showMessageDialog(selected,"shifttab");
		  String wholetext = main.textarea.getText();		  
	  
	  
		  
	  String selected = wholetext.substring(start-1,end);
	  String[] lines = selected.split("\n");
	  for(int i = 1; i < lines.length && lines.length != -1; i++)
	  {
		String line = lines[i];
		lines[i] = line.replaceFirst("\\t","");
	  }
	  selected = String.join("\n",lines);
	  String first = wholetext.substring(0,start-1);
	  String second =wholetext.substring(end,wholetext.length());
	  main.textarea.setText(first+selected+second);
	  lines_length = lines.length;
	  	  
	  MoveOneLineLeft(start);	  
	  	  
	  main.textarea.requestFocus();
	  main.textarea.setSelectionStart(start);
	main.textarea.setSelectionEnd(start);  
    }

    public void ShiftTabOutput() {
    	//javax.swing.JOptionPane.showMessageDialog(selected,"ShiftTab");
	if(start != end) {
		main.textarea.setSelectionStart(start-1);
		main.textarea.setSelectionEnd(end-lines_length);  
	}
	else {
		main.textarea.setSelectionStart(start-1);
		main.textarea.setSelectionEnd(end-1);
	}
    }
    public void MoveOneLineLeft( int start) {
	  main.textarea.setCaretPosition(start);
	  Middle middle = new Middle(main.textarea);
	  String line=middle.getCurrentLine();
	  line=line.replaceFirst("\t","");
	  middle.setCurrentLine(line);
    }
    private int lines_length;
    private int start;
    private int end;
    private String wholetext2;
    public void TabMultipleLines() {
    	  start=main.textarea.getSelectionStart();
	  end= main.textarea.getSelectionEnd();
	  if(start == end) {
		  Middle middle2 = new Middle(main.textarea);
		  middle2.addMiddle("\t");
		  return;
	  }	  
	  
	  String wholetext = main.textarea.getText();
	  String first = wholetext.substring(0,start);
	  String middle6=wholetext.substring(start,end);
	  String last = wholetext.substring(end,wholetext.length());
	  
	  String[] lines = middle6.split("\n");
	  for(int i = 1; i < lines.length; i++) {
	  	String line = lines[i];
	  	lines[i] = "\t"+line;
  	}
  	lines_length = lines.length;
  	String middle = String.join("\n",lines);
  	if(middle6.endsWith("\n")) {
		middle=middle+"\n";
	}
	//javax.swing.JOptionPane.showMessageDialog(selected,middle);
	  	
	wholetext2=first+middle+last;
	
	main.textarea.setText(wholetext2);
	
	main.textarea.setCaretPosition(start);
	Middle middle2 = new Middle(main.textarea);
	String line=middle2.getCurrentLineFromWholeIndex(start);
	line="\t"+line;
	middle2.setWholeCurrentLine(line);
	
	main.textarea.requestFocus();
	main.textarea.setSelectionStart(start);
	main.textarea.setSelectionEnd(start);
    }
    
    public void TabMultipleLinesOutput() {
   	  //javax.swing.JOptionPane.showMessageDialog(selected,"Tab");
   	  if(start != end) { 
		  main.textarea.setSelectionStart(start+1);
		  main.textarea.setSelectionEnd(end+lines_length);
	  }
	  else {
	  	main.textarea.setSelectionStart(start+1);
	  	main.textarea.setSelectionEnd(start+1);
  	}
  }
}
						