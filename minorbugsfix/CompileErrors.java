import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JButton;
public class CompileErrors {			
	private Main main;
	private List<Data> datas = new ArrayList<Data>();
	public CompileErrors(Main main,String ls) {
		this.main = main;
				
		String[] options=new String[2];
		options[0] = "Yes";
		options[1] = "No";
		int option2=JOptionPane.showOptionDialog(null,"Go to l number of error(s)?","Which you like to go to l number of error(s)?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
		if(option2 == JOptionPane.YES_OPTION) {
			if(!ContainsLineError(ls)) {
				JOptionPane.showMessageDialog(null,"Could not find l number.");
				return;	
			}
			Pattern pattern2= Pattern.compile("(([a-zA-Z0-9.]+):(\\d+):.*?)(?=\\n[a-zA-Z0-9.]+:\\d+:|\\z)",Pattern.DOTALL);
			Matcher matcher=pattern2.matcher(ls);
			while(matcher.find()) {
				Data data = new Data();
				
				String wholematch = matcher.group(1);
				//JOptionPane.showMessageDialog(null,wholematch);
				data.classname=matcher.group(2);
				data.l_number=Integer.parseInt(matcher.group(3));
					
				if(wholematch.contains("cannot find symbol")) {
					Pattern pattern3=Pattern.compile("symbol:\\s*class\\s*([a-zA-Z0-9.]+)",Pattern.DOTALL);
					Matcher matcher3=pattern3.matcher(wholematch);
					if(matcher3.find()) {
						data.apiclass=matcher3.group(1);
					}
				}
				datas.add(data);					
			}
			
			Generate();
		}	
	}
	public void Generate() {
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(main.tabbedpane.getSelectedComponent());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(datas.size(),1));
		for(int i = 0; i < datas.size(); i++) {	
			JPanel row = new JPanel();
			Data data=datas.get(i);
			JLabel label = new JLabel("Class of error: "+data.classname);
			row.add(label);
			JLabel label2 = new JLabel("Line number: "+data.l_number);
			row.add(label2);
			JButton button = new JButton("Show Error Line");
			row.add(button);
			button.addActionListener(new GoToLineCompileError(this,i));
			if(data.hasAPIClass()) {
				JLabel label3 = new JLabel("import missing:");
				row.add(label3);
				JButton importbutton = new JButton("import class");
				data.button=importbutton;
				row.add(importbutton);
				importbutton.addActionListener(new ImportAPIClassActionListener(this,i));
			}
			panel.add(row);
		}
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}		
	class ImportAPIClassActionListener implements ActionListener {
		int i;
		CompileErrors ce;
		ImportAPIClassActionListener(CompileErrors ce,int i) {
			this.ce = ce;
			this.i = i;
		}
		@Override
		public void actionPerformed(ActionEvent actionevent) {
			String apiclass = ce.datas.get(i).apiclass;
			List<String> imports=ce.main.muck.links.getImport(apiclass);
			String text=main.textarea.getText();
			if(imports.size() == 1) {
				String importtwo = imports.get(0);
				main.textarea.setText("import "+importtwo+";\n"+text);
				RemoveClass(apiclass);
			}
			else if(imports.size() > 1) {
				JFrame frame2 = new JFrame();
				JPanel panel2 = new JPanel();
				panel2.setLayout(new GridLayout(1,imports.size()));
				for(int i = 0; i < imports.size(); i++) {
					JPanel row = new JPanel();
					String importtwo = imports.get(i);
					JButton addimport2 = new JButton("Add importtwo");
					row.add(addimport2);
					addimport2.addActionListener((ev) -> {
						main.textarea.setText("import "+importtwo+";\n"+text);
						frame2.dispose();
						RemoveClass(apiclass);
					});
					panel2.add(row);
				}
				frame2.add(panel2);
				frame2.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null,"datas.size() is "+datas.size());	
			}						
			main.scrollToCaretPositionWithoutFocus(0);
		}				
	}
	public void RemoveClass(String classname) {
		for(int i = 0, j = 0; i < datas.size(); i++) {
			Data data = datas.get(i);
			if(classname.equals(data.apiclass)) {
				data.apiclass = "";
				data.button.setEnabled(false);
				j++;
			}
		}
	}
	class GoToLineCompileError implements ActionListener {
		int i;
		CompileErrors ce;
		GoToLineCompileError(CompileErrors ce,int i) {
			this.ce = ce;
			this.i = i;
		}		
		@Override
		public void actionPerformed(ActionEvent ae) {
			Data data = datas.get(i);
			String fileName = data.classname;
			int l_number=ce.datas.get(i).l_number;
			if(fileName.equals(main.getFileName(main.fileName))) {	
				ce.showError(ce.getCaretPosition(l_number));	
			}
			else {
				for(String fileName2:main.filelistmodifier.getFileList()) {
					if(fileName.equals(ce.main.getFileName(fileName2))) {
						ce.main.open(fileName2);
						ce.main.scrollToCaretPositionWithoutFocus(ce.getCaretPosition(l_number));
						return;
					}
				}
			}				
		}
	}
	public boolean ContainsLineError(String l) {
		Pattern pattern=Pattern.compile("([a-zA-Z0-9]+):([0-9]+):");
		Matcher matcher=pattern.matcher(l);
		return matcher.find();
	}
	public boolean ContainsSymbol(String l) {
		return l.contains("cannot find symbol");
	}
	public int getLineNumber(String stringuptocaretposition) {
		int lnumber2=0;
		try {
			LineNumberReader lnumberreader=new LineNumberReader(new StringReader(stringuptocaretposition));
			while(lnumberreader.readLine() != null) {
				lnumber2=lnumberreader.getLineNumber();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return lnumber2;
	}
	public int getCaretPosition(int l_number) {
		try {
			String wholetext=main.textarea.getText();
			LineNumberReader lnumberreader=new LineNumberReader(new StringReader(wholetext));
			String l = "";
			while((l = lnumberreader.readLine()) != null) {
				int lnumber2=lnumberreader.getLineNumber();
				if(l_number == lnumber2) break;
			}
			int startOfLine = -1;
			while((startOfLine = wholetext.indexOf(l,++startOfLine)) != -1) {
				//int startOfLine=wholetext.tindexOf(l);
				String firsthalf=wholetext.substring(0,startOfLine+1);
				if(getLineNumber(firsthalf) == l_number) {
					return startOfLine;
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	public void showError(int caretposition) {
		main.scrollToCaretPositionWithoutFocus(caretposition);
	}
}
class Data {
	public JButton button;
	public String classname = "";
	public int l_number = 0;
	public String apiclass = "";
	public boolean hasAPIClass() {
		return !apiclass.equals("");
	}
}