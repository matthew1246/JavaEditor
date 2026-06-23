import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
public class SeeAllGitChanges {
	public String directory;
	public SeeAllGitChanges(String directory) {
		this.directory = directory;
		setLayout();
		setListeners();
	}
	public JFrame frame;
	public JComboBox<String> combobox1;
	public JComboBox<String> combobox2;
	public JTextPane fileChanges;
	public Style defaultStyle,bold,red,green,yellow,cyan;
	public void setLayout() {
		frame = new JFrame();
		frame.setSize(800,600);
		combobox1=new JComboBox<String>();
		combobox2=new JComboBox<String>();
		CommandLine commandline = new CommandLine();
		Process process = commandline.run("git log --format=\"%h %s\"",directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String multiline = displayoutput.Multiline(process);
		String[] lines = multiline.split("\\r?\\n|\\r");
		for(String line : lines) {
			combobox1.addItem(line);
			combobox2.addItem(line);
		}
		JPanel north = new JPanel(new GridLayout(1,2));
		north.add(combobox1);
		north.add(combobox2);
		frame.add(north,BorderLayout.NORTH);
		fileChanges = new JTextPane();
		fileChanges.setEditable(false);
		fileChanges.setBackground(Color.BLACK);
		defaultStyle = fileChanges.addStyle("default",null);
		StyleConstants.setForeground(defaultStyle,Color.LIGHT_GRAY);
		bold = fileChanges.addStyle("bold",null);
		StyleConstants.setBold(bold,true);
		StyleConstants.setForeground(bold,Color.WHITE);
		red = fileChanges.addStyle("red",null);
		StyleConstants.setForeground(red,new Color(255,80,80));
		green = fileChanges.addStyle("green",null);
		StyleConstants.setForeground(green,new Color(80,255,80));
		yellow = fileChanges.addStyle("yellow",null);
		StyleConstants.setForeground(yellow,new Color(255,255,80));
		cyan = fileChanges.addStyle("cyan",null);
		StyleConstants.setForeground(cyan,new Color(80,255,255));
		frame.add(new JScrollPane(fileChanges),BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); 
	}
	public void setListeners() {
		combobox1.addActionListener((ev) -> {
			String selected = (String)combobox1.getSelectedItem();
			if(selected == null || selected.equals("No output.")) return;
			String hash = selected.split(" ")[0];
			CommandLine commandline = new CommandLine();
			Process process = commandline.run("git diff-tree --no-commit-id -r --name-only " + hash,directory);
			DisplayOutput displayoutput = new DisplayOutput();
			String files = displayoutput.Multiline(process);
			if(files.equals("No output.")) {
				fileChanges.setText("");
			} else {
				fileChanges.setText(files);
			}
		});
		combobox2.addActionListener((ev) -> {
			String selected = (String)combobox2.getSelectedItem();
			if(selected == null || selected.equals("No output.")) return;
			String hash = selected.split(" ")[0];
			CommandLine commandline = new CommandLine();
			Process process = commandline.run("git show --color=always " + hash,directory);
			DisplayOutput displayoutput = new DisplayOutput();
			String files = displayoutput.Multiline(process);
			if(files.equals("No output.")) {
				fileChanges.setText("");
			} else {
				appendAnsiColoredText(files);
			}
		});
	}
	public void appendAnsiColoredText(String ansiText) {
		StyledDocument doc = fileChanges.getStyledDocument();
		try {
			doc.remove(0,doc.getLength());
			Style current=defaultStyle;
			int i=0,len=ansiText.length();
			while(i<len) {
				if(ansiText.charAt(i)=='\u001B' && i+1<len && ansiText.charAt(i+1)=='[') {
					int j=i+2;
					while(j<len && ansiText.charAt(j)!='m') j++;
					if(j<len) {
						String code=ansiText.substring(i+2,j);
						if(code.equals("0")) current=defaultStyle;
						else if(code.equals("1")) current=bold;
						else if(code.equals("31")) current=red;
						else if(code.equals("32")) current=green;
						else if(code.equals("33")) current=yellow;
						else if(code.equals("36")) current=cyan;
						i=j+1;
					} else break;
				} else {
					int start=i;
					while(i<len && !(ansiText.charAt(i)=='\u001B' && i+1<len && ansiText.charAt(i+1)=='[')) i++;
					doc.insertString(doc.getLength(),ansiText.substring(start,i),current);
				}
			}
		} catch(Exception ex) {
			fileChanges.setText(ansiText.replaceAll("\u001B\\[[0-9;]*m",""));
		}
	}
}