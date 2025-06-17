import javax.swing.*;
import java.awt.event.*;
public class MuckFX {
	public LinksFX links = new LinksFX();
	public JFrame frame;
	public JButton button;
	public JTextField textfield;
	public static void main(String[] args) 	{
		MuckFX main = new MuckFX();
	}
	public MuckFX() {
		setLayout();
		setListeners();
	}
	public void setLayout() {
		frame= new JFrame();
		frame.setTitle("Class");
		frame.setLocation(980,100);
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		textfield = new JTextField(15);
		panel.add(textfield);
		button=new JButton("run");
		panel.add(button);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void setListeners() {
		textfield.addActionListener(new GooeyActionListener());
		button.addActionListener(new GooeyActionListener());
		//frame.getRootPane().setDefaultButton(button);
	}
	class GooeyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String class_one = textfield.getText();
			links.openLink(class_one);
		}
	}
}