import javax.swing.JTextArea;
import javax.swing.JOptionPane;
public class SelectedLines {
    private JTextArea textarea;
    public SelectedLines(JTextArea textarea)
    {
	  this.textarea = textarea;
    }

    public void ShiftTab() {
	  	start=textarea.getSelectionStart();
		end= textarea.getSelectionEnd();
	  	// javax.swing.JOptionPane.showMessageDialog(selected,"shifttab");
		  String wholetext = textarea.getText();		  
	  
	  
		  
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
	  textarea.setText(first+selected+second);
	  lines_length = lines.length;
	  	  
	  MoveOneLineLeft(start);	  
	  	  
	  textarea.requestFocus();
	  textarea.setSelectionStart(start);
	textarea.setSelectionEnd(start);  
    }

    public void ShiftTabOutput() {
    	//javax.swing.JOptionPane.showMessageDialog(selected,"ShiftTab");
	if(start != end) {
		textarea.setSelectionStart(start-1);
		textarea.setSelectionEnd(end-lines_length);  
	}
	else {
		textarea.setSelectionStart(start-1);
		textarea.setSelectionEnd(end-1);
	}
    }
    public void MoveOneLineLeft( int start) {
	  textarea.setCaretPosition(start);
	  Middle middle = new Middle(textarea);
	  String line=middle.getCurrentLine();
	  line=line.replaceFirst("\t","");
	  middle.setCurrentLine(line);
    }
    private int lines_length;
    private int start;
    private int end;
    private String wholetext2;
    public void TabMultipleLines() {
    	  start=textarea.getSelectionStart();
	  end= textarea.getSelectionEnd();
	  if(start == end) {
		  Middle middle2 = new Middle(textarea);
		  middle2.addMiddle("\t");
		  return;
	  }	  
	  
	  String wholetext = textarea.getText();
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
	
	textarea.setText(wholetext2);
	
	textarea.setCaretPosition(start);
	Middle middle2 = new Middle(textarea);
	String line=middle2.getCurrentLineFromWholeIndex(start);
	line="\t"+line;
	middle2.setWholeCurrentLine(line);
	
	textarea.requestFocus();
	textarea.setSelectionStart(start);
	textarea.setSelectionEnd(start);
    }
    
    public void TabMultipleLinesOutput() {
   	  //javax.swing.JOptionPane.showMessageDialog(selected,"Tab");
   	  if(start != end) { 
		  textarea.setSelectionStart(start+1);
		  textarea.setSelectionEnd(end+lines_length);
	  }
	  else {
	  	textarea.setSelectionStart(start+1);
	  	textarea.setSelectionEnd(start+1);
  	}
  }
}
						