import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.regex.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Control_F {
	private LiveIterator<String> liveiterator;
	private Main main;
	private JCheckBox searchall;
	private JTextArea textarea;
	private JCheckBox replace;
	private JCheckBox selection;
	private JTextField replaceinput;
	private JCheckBox casey;
	public Control_F(Main main,JCheckBox searchall,JTextArea textarea,JCheckBox replace,JCheckBox selection,JTextField replaceinput,JCheckBox casey) {
		liveiterator= new LiveIterator<String>(main.filelistmodifier.original,true);
		this.main = main;	
		this.searchall = searchall;	
		this.textarea = textarea;
		this.replace = replace;
		this.selection=selection;
		this.replaceinput = replaceinput;
		this.casey = casey;
	}
	public String filename = "";
	public int z = 0;
	public void Find(String find) {
		if(!searchall.isSelected()) {
			String text=textarea.getText();
			String original = text;
			int selectionstart = 0;
			int selectionend = text.length();
			if(selection.isSelected()) {
				selectionstart=textarea.getSelectionStart();
				selectionend = textarea.getSelectionEnd();
				text=textarea.getText().substring(selectionstart,selectionend);
			}
			String find2 = find;
			if(!casey.isSelected())
				find = find.toLowerCase();
			int count = 0;
			z++;
			if(!casey.isSelected())
				text=text.toLowerCase();
			if(text.contains(find)) {
				String[] lines = text.split("\n");
				int x = 0;
				for(int i = 0; i < lines.length; i++) {
					String line = lines[i];
					char[] chars = line.toCharArray();
					for(int j = 0; j < chars.length; j++) {
						x++;
					}
					x++;
					if(line.contains(find)) {
						count++;
						if(z == count)
							break;
					}
				}
				x--;
				if(selection.isSelected()) {
					x=x+selectionstart;
				}
				if(replace.isSelected()) {
					String first = original.substring(0,selectionstart);
					String middle=original.substring(selectionstart,selectionend);
					Pattern pattern;
					if(!casey.isSelected()) {
						pattern=Pattern.compile(Pattern.quote(find2),Pattern.CASE_INSENSITIVE);
					} else {
						pattern=Pattern.compile(Pattern.quote(find2));
					}
					Matcher matcher=pattern.matcher(middle);
					String selected=matcher.replaceAll(replaceinput.getText());
					String end = original.substring(selectionend,original.length());
					textarea.setText(first+selected+end);
					
					String firsthalf = original.substring(0,x);
					Matcher matcher2= pattern.matcher(firsthalf);
					String changed=matcher2.replaceAll(replaceinput.getText());
					int y =changed.length()-firsthalf.length();
					x=x+y;
				}
				if(x < (textarea.getText().length()-1) ) {
					main.scrollToCaretPosition(x);
				}
				else {
					main.scrollToCaretPosition(textarea.getText().length()-1);
					z = 0;
				}
			}
		}
		else {
			String dir =main.fileName.replaceAll("[^\\\\]+\\.java","");
			while(liveiterator.hasNext()) {
				try {
					if(z == 0)
						filename = liveiterator.next();
					String newfilename = dir+filename;
					Path path = Paths.get(newfilename);
					String text = Files.readString(path,StandardCharsets.UTF_8);
		
					String original = text;
					String find2 = find;
					if(!casey.isSelected())
						find = find.toLowerCase();
					int count = 0;
					z++;
					if(!casey.isSelected())
						text=text.toLowerCase();
					if(text.contains(find)) {
						String[] lines = text.split("\n");
						int x = 0;
						for(int i = 0; i < lines.length; i++) {
							String line = lines[i];
							char[] chars = line.toCharArray();
							for(int j = 0; j < chars.length; j++) {
								x++;
							}
							x++;
							if(line.contains(find)) {
								//main.scrollToCaretPosition(x);
								if(!main.fileName.equals(newfilename)) {
									main.fileName = newfilename;
									main.open(filename);
								}
								count++;
								if(z == count) {
									if(i == (lines.length-1) ) {
										z = 0;
									}
									break;
								}
							}
							if(i == (lines.length-1) )
								z = 0;
						}
						x--;
						if(replace.isSelected()) {
							Pattern pattern;
							if(!casey.isSelected()) {
								pattern=Pattern.compile(Pattern.quote(find2),Pattern.CASE_INSENSITIVE);
							} else {
								pattern=Pattern.compile(Pattern.quote(find2));
							}
							Matcher matcher=pattern.matcher(original);
							String selected=matcher.replaceAll(replaceinput.getText());
							textarea.setText(selected);
							
							String firsthalf = original.substring(0,x);
							Matcher matcher2= pattern.matcher(firsthalf);
							String changed=matcher2.replaceAll(replaceinput.getText());
							int y =changed.length()-firsthalf.length();
							x=x+y;
						}
						if(z == 0)
							liveiterator.remove(filename);
						if(x < (text.length()-1) ) {
							main.scrollToCaretPosition(x);
							return;
						}
						else {
							main.scrollToCaretPosition(text.length()-1);
							z = 0;
							return;
						}
					}
					else {
						z = 0;
					}
				} catch(FileNotFoundException ex) {
					ex.printStackTrace();
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}