import javax.swing.*;
import java.awt.event.*;
public class MSDOS {
	private JTextField input;
	private JButton run;
	private Main main;
	public MSDOS(Main main) {
		this.main =main;
		setLayout();
		setListeners();
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
		frame.setLocation(1210,100);
		frame.setVisible(true);
	}
	public void setListeners() {
		ActionListener actionlistener = new ActionListener() {
			public void actionPerformed
(ActionEvent ev) {
				CommandLine commandline = new CommandLine();
				String dir=main.fileName.replaceAll("[^\\\\]+\\.java","");
				commandline.runWithMSDOS(input.getText(),dir);
			}
		};
		run.addActionListener(actionlistener);
		input.addActionListener(actionlistener);
	}
}
