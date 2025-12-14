import javax.swing.*;
public class AddDependency {
	public AddDependency() {
		setLayout();
		setListeners();
	}
	public JButton search;
	public void setLayout() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		JTextField textfield = new JTextField();
		panel.add(textfield);
		
		search = new JButton();
		panel.add(search);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	public void setListeners() {
			
	}
}