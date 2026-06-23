import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
	public JTextArea fileChanges;
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
		fileChanges = new JTextArea();
		fileChanges.setEditable(false);
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
			Process process = commandline.run("git show " + hash,directory);
			DisplayOutput displayoutput = new DisplayOutput();
			String files = displayoutput.Multiline(process);
			if(files.equals("No output.")) {
				fileChanges.setText("");
			} else {
				fileChanges.setText(files);
			}
		});
	}
}