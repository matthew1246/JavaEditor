import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
public class SeeAllGitChanges {
	public SeeAllGitChanges() {
		setLayout();
		setListeners();
	}
	public JFrame frame;
	public JComboBox combobox;
	public void setLayout() {
		frame = new JFrame();
		frame.setSize(800,600);
		JPanel panel = new JPanel();
		combobox=new JComboBox();
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