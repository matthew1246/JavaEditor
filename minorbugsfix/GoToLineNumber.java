import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.IOException;
public class GoToLineNumber {		
	private Main main;
	public GoToLineNumber(Main main,String lines) {
		this.main = main;
				
		String[] options=new String[2];
		options[0] = "Yes";
		options[1] = "No";
		int option2=JOptionPane.showOptionDialog(null,"Go to line number of error?","Which you like to go to line number?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
		if(option2 == JOptionPane.YES_OPTION) {
			if(!ContainsLineError(lines)) {
				JOptionPane.showMessageDialog(null,"Could not find line number.");
				return;	
			}
			String[] lines2 = lines.split("\\n");
			for(int i = 0; i < lines2.length; i++) {
				String line = lines2[i];
				if(ContainsLineError(line)) {
					Pattern pattern=Pattern.compile("([a-zA-Z0-9]+):([0-9]+):");
					Matcher matcher=pattern.matcher(line);
					if(matcher.find()) {
						String classname = matcher.group(1);
						int line_number=Integer.parseInt(matcher.group(2));
					
						try {
							String wholetext=main.textarea.getText();
							LineNumberReader linenumberreader=new LineNumberReader(new StringReader(wholetext));
							while((line = linenumberreader.readLine()) != null) {
								int linenumber2=linenumberreader.getLineNumber();
								if(line_number == linenumber2) break;
							}
							int startOfLine = -1;
							while((startOfLine = wholetext.indexOf(line,++startOfLine)) != -1) {
								//int startOfLine=wholetext.tindexOf(line);
								String firsthalf=wholetext.substring(0,startOfLine+1);
								if(getLineNumber(firsthalf) == line_number) {
									main.textarea.grabFocus();
									main.textarea.setCaretPosition(startOfLine);
									break;
								}
							}
						} catch(IOException ex) {
							ex.printStackTrace();
						}
					}
				}
				else {
					addLine(line);
				}
			}
		}	
	}
	public boolean ContainsLineError(String line) {
		Pattern pattern=Pattern.compile("([a-zA-Z0-9]+):([0-9]+):");
		Matcher matcher=pattern.matcher(line);
		return matcher.find();
	}
	public void addLine(String line) {
			
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

}
