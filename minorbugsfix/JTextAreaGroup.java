import javax.swing.JTextArea;
import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.geom.Rectangle2D;
import javax.swing.text.BadLocationException;
import java.awt.Font;
import java.awt.Color;
public class JTextAreaGroup extends JTextArea {
	public JTextAreaGroup() {
		super();
	}
	List<Group> groups = new LinkedList<Group>();
	public String text1 = "";
	@Override
	public void paintComponent(Graphics graphics) {
		Stack<Integer> leftcurlybraces= new Stack<Integer>();
			
		super.paintComponent(graphics);
		
		graphics.setColor(java.awt.Color.blue);
		//graphics.setFont(new Font("Arial",Font.BOLD,25));
		//graphics.drawString("-",(int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()));
		//graphics.drawString("-",10,20);
		String text = super.getText();
		//if(!text1.equals(text)) {
			for(int i = 0; i < (text.length()-4); i++) {
				String character = text.substring(i,i+1);
				if(character.equals("{")) {
					try {
						Rectangle2D		 rectanglecoords=super.modelToView2D(i+1);
graphics.drawString("-",(int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()+20));
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