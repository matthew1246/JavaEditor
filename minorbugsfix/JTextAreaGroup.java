import javax.swing.JTextArea;
import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.geom.Rectangle2D;
import javax.swing.text.BadLocationException;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JOptionPane;
public class JTextAreaGroup extends JTextArea {
	public JTextAreaGroup() {
		super();
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				int caretposition=viewToModel2D(me.getPoint());
				if(groups.get(caretposition) != null)
					JOptionPane.showMessageDialog(null,"you clicked");
			}
		});
	}
	HashMap<Integer,Group> groups;
	public String text1 = "";
	@Override
	public void paintComponent(Graphics graphics) {
		groups = new HashMap<Integer,Group>();		
		Stack<Integer> leftcurlybraces= new Stack<Integer>();
			
		super.paintComponent(graphics);
		
		graphics.setColor(java.awt.Color.blue);
	
		//graphics.setFont(new Font("Arial",Font.BOLD,25));
		//graphics.drawString("-",(int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()));
		//graphics.drawString("-",10,19);
		String text = super.getText();
		//if(!text1.equals(text)) {
			for(int i = 0; i < (text.length()-4); i++) {
				String character = text.substring(i,i+1);
				if(character.equals("{")) {
					try {
						Rectangle2D		 rectanglecoords=super.modelToView2D(i+1);
graphics.drawString("-",(int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()+20));
					groups.put(i+2,new Group());
					}												catch(BadLocationException ex) {
						ex.printStackTrace();
					}						
				}
				/*else if(character.equals("}")) {
					int leftcurlybrace=leftcurlybraces.pop();
					Group group = new Group();
					group.start = leftcurlybrace;
					
				}*/
			}
		//}
	}
}