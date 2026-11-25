import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
public class ChangeCommit {
	public Main main;	
	protected String root_directory;
	public JComboBox<String> commits;
	public JButton changeToSelectedCommit;
	public JTextArea output;
	public ChangeCommit(Main main,String root_directory) {
		this.main=main;
		this.root_directory=root_directory;
		setLayout();
		setListeners();
	}
	public ChangeCommit(String root_directory) {
		this(null,root_directory);	
	}
	public boolean isDetachedHead() {
		CommandLine commandline = new CommandLine();
		Process process = commandline.run("git rev-parse --abbrev-ref HEAD",root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String branch = displayoutput.OneLine(process);
		return branch != null && branch.trim().equals("HEAD");
	}
	public void gitswitchdash() {
		CommandLine commandline = new CommandLine();
		Process process = commandline.run("git switch - 2>&1",root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		output.setText(displayoutput.Multiline(process));
		output.setCaretPosition(0);
	}
	public void setLayout() {
		JFrame changecommit = new JFrame("Change Commit");
		changecommit.setSize(800,600);
		commits = new JComboBox<String>();
		CommandLine commandline = new CommandLine();
		Process process = commandline.run("git log --format=\"%h %s\"",root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String multiline = displayoutput.Multiline(process);
		String[] lines = multiline.split("\\r?\\n|\\r");
		for(String line : lines) {
			commits.addItem(line);
		}
		JPanel panel = new JPanel(new BorderLayout());
		JPanel row = new JPanel(new BorderLayout());
		row.setBorder(new javax.swing.border.EmptyBorder(10,0,0,0));
		row.add(commits,BorderLayout.CENTER);
		changeToSelectedCommit = new JButton("Change to Selected Commit");
		row.add(changeToSelectedCommit,BorderLayout.EAST);
		panel.add(row,BorderLayout.CENTER);
		
		changecommit.add(panel,BorderLayout.NORTH);
		output = new JTextArea();
		output.setEditable(false);
		output.setLineWrap(true);
		changecommit.add(new JScrollPane(output),BorderLayout.CENTER);
		changecommit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changecommit.setVisible(true);
	}
	public void setListeners() {
		changeToSelectedCommit.addActionListener((ev) -> {
			if(isDetachedHead()) {	
				gitswitchdash();
			}
			String selected = (String)commits.getSelectedItem();
			if(selected == null || selected.equals("No output.") || selected.startsWith("<<")) return;
			String hash = selected.split(" ")[0];
			CommandLine commandline = new CommandLine();
			Process process = commandline.run("git checkout " + hash + " 2>&1",root_directory);
			DisplayOutput displayoutput = new DisplayOutput();
			output.setText(displayoutput.Multiline(process));
			output.setCaretPosition(0);
			if(main.isMainSet())
				main.updateAllTabs();
		});
	}
}