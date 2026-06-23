import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
public class SeeAllGitChanges {
	public String directory;
	public SeeAllGitChanges(String directory) {
		this.directory = directory;
		setLayout();
		setListeners();
	}
	public JFrame frame;
	public JComboBox<String> combobox;
	public void setLayout() {
		frame = new JFrame();
		frame.setSize(800,600);
		JPanel panel = new JPanel();
		combobox=new JComboBox<String>();
		CommandLine commandline = new CommandLine();
		Process process = commandline.run("git log --format=%s",directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String multiline = displayoutput.Multiline(process);
		String[] lines = multiline.split("\\r?\\n|\\r");
		for(String line : lines) {
			combobox.addItem(line);
		}
		panel.add(combobox);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); 
	}
	public void setListeners() {
		combobox.addActionListener((ev) -> {
			JOptionPane.showMessageDialog(null,"Hello World!");
		});
	}
}