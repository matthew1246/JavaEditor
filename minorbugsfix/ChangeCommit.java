import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
public class ChangeCommit {
	protected String root_directory;
	public ChangeCommit(String root_directory) {
		this.root_directory=root_directory;
		setLayout();
	}
	public void setLayout() {
		JFrame changecommit = new JFrame("Change Commit");
		changecommit.setSize(800,600);
		JComboBox<String> commits = new JComboBox<String>();
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
		JButton changeToSelectedCommit = new JButton("Change to Selected Commit");
		row.add(changeToSelectedCommit,BorderLayout.EAST);
		panel.add(row,BorderLayout.CENTER);
		
		changecommit.add(panel,BorderLayout.NORTH);
		changecommit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changecommit.setVisible(true);
	}
}
