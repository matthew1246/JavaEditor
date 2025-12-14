import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
public class AddDependency {
	public AddDependency() {
		setLayout();
		setListeners();
	}
	public JTextField textfield;
	public JPanel rows;
	public JButton search;
	public void setLayout() {
		JFrame frame = new JFrame();
		
		rows = new JPanel(new GridLayout(1,1));
		JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		textfield = new JTextField(15);
		row.add(textfield);
		
		search = new JButton("Search");
		row.add(search);
		
		rows.add(row);
		
		frame.add(rows);
		
		frame.pack();
		frame.setVisible(true);
	}
	public void setListeners() {
		search.addActionListener( (ev) -> {
			String input = textfield.getText();
			
		});		
	}
}