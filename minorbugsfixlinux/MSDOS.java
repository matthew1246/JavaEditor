import javax.swing.*;
import java.awt.event.*;
public class MSDOS {
	private JTextField input;
	private JButton run;
	private Main main;
	public MSDOS(Main main) {
		this.main =main;
	}
	public void EDT() {
		setLayout();
		setListeners();
	}
	private JButton exp=new JButton("explorer");
	public void setLayout() {
		JFrame frame = new JFrame();
		frame.setTitle("Run MSDOS");
		
		JPanel panel = new JPanel();
		input =	new JTextField(17);
		input.setText(Platform.isWindows() ? "del backup.txt" : "rm backup.txt");
		
		
		panel.add(input);
		run = new JButton("run");
		panel.add(run);
		panel.add(exp);
		frame.getContentPane().add(panel);
		frame.pack();
		SwingUtilities.invokeLater(() -> {
			frame.setLocation(1210,100);
		});
		frame.setVisible(true);
	}
	public void setListeners() {
		ActionListener actionlistener = new ActionListener() {
			public void actionPerformed
(ActionEvent ev) {
				CommandLine commandline = new CommandLine();
				String dir=Platform.directoryOf(main.fileName);
				commandline.runWithMSDOS(input.getText(),dir);
			}
		};
		run.addActionListener(actionlistener);
		input.addActionListener(actionlistener);
		exp.addActionListener((ev) -> {
			String dir=Platform.directoryOf(main.fileName);
			Platform.openInFileManager(".",dir);
		});
	}
}

			
