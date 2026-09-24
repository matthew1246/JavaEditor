import javax.swing.*;
import java.awt.event.*;
public class Muck {
	public Links links;
	public JFrame frame;
	public JButton button;
	public JTextField textfield;
	public static void main(String[] args) 	{
		Links links = new Links();
		Muck main = new Muck(links);
	}
	public Muck(Links links) {
		this.links = links;
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
		textfield.setText("JButton");
		panel.add(textfield);
		button=new JButton("run");
		panel.add(button);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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