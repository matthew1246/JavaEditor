import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
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
	public Style bold,red,green,yellow,cyan;
	public JButton gitStatus,gitDiff;
	/*
	** Runs a git command in the repository and gives the output of it back.
	** When the command could not be run at all, a message is given back
	** instead of the output.
	*/
	public String runGit(String[] arguments) {
		CommandLine commandline = new CommandLine();
		Process process = commandline.run(arguments,directory);
		return outputOf(process);
	}
	public String runGit(String command) {
		CommandLine commandline = new CommandLine();
		Process process = commandline.run(command,directory);
		return outputOf(process);
	}
	public String outputOf(Process process) {
		if(process == null) {
			return "Git could not be run.";
		}
		DisplayOutput displayoutput = new DisplayOutput();
		String output = displayoutput.Multiline(process);
		try {
			process.waitFor();
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		return output;
	}
	/*
	** A line that tells that git failed is not a commit, so it is not put
	** into the list of commits.
	*/
	public boolean isErrorLine(String line) {
		if(line == null) {
			return true;
		}
		String trimmed=line.trim();
		return trimmed.equals("") || trimmed.startsWith("fatal:") || trimmed.startsWith("error:");
	}
	public void setLayout() {
		frame = new JFrame();
		frame.setSize(800,600);
		combobox1=new JComboBox<String>();
		combobox2=new JComboBox<String>();
		combobox1.addItem("<<Select which git commit title for file changes>>");
		combobox2.addItem("<<Select which git commit title for content changes>>");
		String multiline = runGit(new String[]{"git","log","--format=%h %s"});
		String[] lines = multiline.split("\\r?\\n|\\r");
		for(String line : lines) {
			if(isErrorLine(line)) {
				continue;
			}
			combobox1.addItem(line);
			combobox2.addItem(line);
		}
		JPanel north = new JPanel(new BorderLayout());
		JLabel seeAllChanges = new JLabel("See All Changes",JLabel.CENTER);
		north.add(seeAllChanges,BorderLayout.NORTH);
		JPanel row = new JPanel(new BorderLayout());
		gitStatus = new JButton("git status");
		row.add(gitStatus,BorderLayout.WEST);
		JPanel comboPanel = new JPanel(new GridLayout(1,2));
		comboPanel.add(combobox1);
		comboPanel.add(combobox2);
		row.add(comboPanel,BorderLayout.CENTER);
		gitDiff = new JButton("git diff");
		row.add(gitDiff,BorderLayout.EAST);
		north.add(row,BorderLayout.CENTER);
		frame.add(north,BorderLayout.NORTH);
		fileChanges = new JTextPane();
		fileChanges.setEditable(false);
		fileChanges.setBackground(Color.BLACK);
		fileChanges.setForeground(Color.LIGHT_GRAY);
		fileChanges.setCaretColor(Color.LIGHT_GRAY);
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true); 
	}
	public void setListeners() {
		combobox1.addActionListener((ev) -> {
			String selected = (String)combobox1.getSelectedItem();
			if(selected == null || selected.equals("No output.") || selected.startsWith("<<")) return;
			String hash = selected.split(" ")[0];
			String files = runGit("git diff-tree --no-commit-id -r --name-only " + hash);
			if(files.equals("No output.") || isErrorLine(files)) {
				fileChanges.setText("");
			} else {
				fileChanges.setText(files);
			}
			fileChanges.setCaretPosition(0);
		});
		combobox2.addActionListener((ev) -> {
			String selected = (String)combobox2.getSelectedItem();
			if(selected == null || selected.equals("No output.") || selected.startsWith("<<")) return;
			String hash = selected.split(" ")[0];
			String files = runGit("git show --color=always " + hash);
			if(files.equals("No output.") || isErrorLine(files)) {
				fileChanges.setText("");
			} else {
				appendAnsiColoredText(files);
			}
			fileChanges.setCaretPosition(0);
		});
		gitStatus.addActionListener((ev) -> {
			String output = runGit("git status");
			if(output.equals("No output.") || isErrorLine(output)) {
				fileChanges.setText("");
			} else {
				fileChanges.setText(output);
			}
			fileChanges.setCaretPosition(0);
		});
		gitDiff.addActionListener((ev) -> {
			String output = runGit("git diff --color=always");
			if(output.equals("No output.") || isErrorLine(output)) {
				fileChanges.setText("");
			} else {
				appendAnsiColoredText(output);
			}
			fileChanges.setCaretPosition(0);
		});
	}
	public void appendAnsiColoredText(String ansiText) {
		StyledDocument doc = fileChanges.getStyledDocument();
		try {
			doc.remove(0,doc.getLength());
			Style current=null;
			int i=0,len=ansiText.length();
			while(i<len) {
				if(ansiText.charAt(i)=='\u001B' && i+1<len && ansiText.charAt(i+1)=='[') {
					int j=i+2;
					while(j<len && ansiText.charAt(j)!='m') j++;
					if(j<len) {
						String code=ansiText.substring(i+2,j);
						String[] codes=code.split(";");
						for(String c:codes) {
							if(c.equals("")||c.equals("0")) current=null;
							else if(c.equals("1")) current=bold;
							else if(c.equals("31")) current=red;
							else if(c.equals("32")) current=green;
							else if(c.equals("33")) current=yellow;
							else if(c.equals("36")) current=cyan;
						}
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
