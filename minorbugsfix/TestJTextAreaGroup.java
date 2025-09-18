import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
public class TestJTextAreaGroup {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800,600);
		
		JPanel panel = new JPanel();
		JTextArea textarea =new JTextAreaGroup();
		frame.add(textarea);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}