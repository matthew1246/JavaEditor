import javax.swing.SwingUtilities;
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
import java.io.*;
public class SeeAllGitChanges {
	public String directory;
	private boolean isGitInstalled;
	public SeeAllGitChanges(String directory) {
		this.directory = directory;
		isGitInstalled = isGitInstalled();
		setLayout();
		setListeners();
	}
	public JFrame frame;
	public JComboBox<String> combobox1;
	public JComboBox<String> combobox2;
	public JTextPane fileChanges;
	public Style bold,red,green,yellow,cyan;
	public JButton gitStatus,gitDiff;
	private boolean isGitInstalled() {
		try {
			String[] command2 = new String[3];
			command2[0] = "cmd.exe";
			command2[1] = "/c";
			command2[2] = "git --version";
			Process process = Runtime.getRuntime().exec(command2, null);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line= reader.readLine();
			reader.close();
			return line != null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	private String getGitDotExe() {
		File javahome = new File(System.getProperty("java.home"));
		File base = new File(javahome.getParent());
		File app = new File(base, "app");
		File extrafiles = new File(app, "extra-files");
		File gitfolder = new File(extrafiles, "PortableGit");
		String git = gitfolder.getAbsolutePath();
		if (!git.endsWith("\\"))
			git += "\\";
		git += "bin\\";
		git += "git.exe";
		return git;
	}
	private String getGitPrefix() {
		if (isGitInstalled) return "git";
		return "\"" + getGitDotExe() + "\"";
	}
	public void setLayout() {
		frame = new JFrame();
		frame.setSize(800,600);
		combobox1=new JComboBox<String>();
		combobox2=new JComboBox<String>();
		combobox1.addItem("<<Select which git commit title for file changes>>");
		combobox2.addItem("<<Select which git commit title for content changes>>");
		
		try {
			String[] command = new String[3];
			command[0] = getGitPrefix();
			command[1] = "log";
			command[2] =  "--format=\\\"%h %s\\\"";
			Process process=Runtime.getRuntime().exec(command,null,new File(directory));
			DisplayOutput displayoutput = new DisplayOutput();
			String multiline = displayoutput.Multiline(process);
			String[] lines = multiline.split("\\r?\\n|\\r");
			for(String line : lines) {
				combobox1.addItem(line);
				combobox2.addItem(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
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
			CommandLine commandline = new CommandLine();
			Process process = commandline.run(getGitPrefix() + " diff-tree --no-commit-id -r --name-only " + hash,directory);
			DisplayOutput displayoutput = new DisplayOutput();
			String files = displayoutput.Multiline(process);
			if(files.equals("No output.")) {
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
			CommandLine commandline = new CommandLine();
			Process process = commandline.run(getGitPrefix() + " show --color=always " + hash,directory);
			DisplayOutput displayoutput = new DisplayOutput();
			String files = displayoutput.Multiline(process);
			if(files.equals("No output.")) {
				fileChanges.setText("");
			} else {
				appendAnsiColoredText(files);
			}
			fileChanges.setCaretPosition(0);
		});
		gitStatus.addActionListener((ev) -> {
			CommandLine commandline = new CommandLine();
			Process process = commandline.run(getGitPrefix() + " status",directory);
			DisplayOutput displayoutput = new DisplayOutput();
			String output = displayoutput.Multiline(process);
			if(output.equals("No output.")) {
				fileChanges.setText("");
			} else {
				fileChanges.setText(output);
			}
			fileChanges.setCaretPosition(0);
		});
		gitDiff.addActionListener((ev) -> {
			CommandLine commandline = new CommandLine();
			Process process = commandline.run(getGitPrefix() + " diff --color=always",directory);
			DisplayOutput displayoutput = new DisplayOutput();
			String output = displayoutput.Multiline(process);
			if(output.equals("No output.")) {
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
