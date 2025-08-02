import javax.swing.*;
import java.awt.event.*;
public class MSDOS {
	public static void main(String[] args) {
		MSDOS msdos = new MSDOS("C:\\Users\\Owner\\Documents\\git\\notepad\\Main.java");
	}
	private String filename;
	private JTextField input;
	private JButton run;
	public MSDOS(String filename) {
		this.filename = filename;
		setLayout();
		setListeners();
	}
	public void setFileName(String filename) {
		this.filename = filename;
	}
	public void setLayout() {
		JFrame frame = new JFrame();
		frame.setTitle("Run MSDOS");
		
		JPanel panel = new JPanel();
		input =	new JTextField(17);
		panel.add(input);
		run = new JButton("run");
		panel.add(run);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setLocation(1225,100);
		frame.setVisible(true);
	}
	public void setListeners() {
		ActionListener actionlistener = new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				CommandLine commandline = new CommandLine();
				String dir=filename.replaceAll("[^\\\\]+\\.java","");
				commandline.runWithMSDOS(input.getText(),dir);
			}
		};
		run.addActionListener(actionlistener);
		input.addActionListener(actionlistener);
	}
}

			