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
	public List<String> codes = new LinkedList<String>();
	public JTextAreaGroup() {
		super();
		
		this.addMouseListener(new MouseAdapter() { 
			@Override
			public void mousePressed(MouseEvent me) {
				int caretposition=viewToModel2D(me.getPoint());
				Group group = groups.get(caretposition);
				if(group != null) {
					String text = getText();

					String first=text.substring(0,group.start+1);
					String last = text.substring(group.end-1,text.length());
					Codes codes2 = new Codes(JTextAreaGroup.this);
					List<Integer> codesindex=codes2.getCodes();
					int index=codes2.getIndex(codesindex,first.length());
						codes.add(index,text.substring(group.start,group.end));
					
					setText(first+"+"+last);
					setCaretPosition(group.start+1);
				}
			}
		});
	}
	HashMap<Integer,Group> groups;
	public String text = "";
	@Override
	public void paintComponent(Graphics graphics) {
		groups = new HashMap<Integer,Group>();		
		Stack<Integer> leftcurlybraces= new Stack<Integer>();
			
		super.paintComponent(graphics);
		
		graphics.setColor(java.awt.Color.blue);
	
		//graphics.setFont(new Font("Arial",Font.BOLD,25));
		//graphics.drawString("-",(int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()));
		//graphics.drawString("-",10,19);
		text = super.getText();
		//if(!text1.equals(text)) {
			for(int i = 0; i < (text.length()-4); i++) {
				String character = text.substring(i,i+1);
				if(character.equals("{") && !isPlus(i+1) && !isRightCurlyBrace(i+2)) {
					try {
						Rectangle2D		 rectanglecoords=super.modelToView2D(i+1);
graphics.drawString("-",(int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()+20));
						groups.put(i+2,new Group());
						Stack<Integer> stack = new Stack<Integer>();
						stack.push(i);
						int j = i+1;
						while(true && j < text.length()) {
							String character2 = text.substring(j,j+1);
							if(character2.equals("{")) {
								stack.push(j);
							}
							else if(character2.equals("}")) {
								int rightcurlybrace=stack.pop();
								if(stack.size() == 0) {
									Group group=groups.get(i+2);
									group.start = i;
									group.end =j+1;
									groups.put(i+2,group);
									break;
								}
							}
							j++;
						}
					} catch(BadLocationException ex) {
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
	public boolean isPlus(int caretposition) {
		if((caretposition+1) > text.length())
			return false;	
		return text.substring(caretposition,caretposition+1).equals("+");
	}
	public boolean isRightCurlyBrace(int caretposition) {
		if((caretposition+1) > text.length())
			return false;
		return text.substring(caretposition,caretposition+1).equals("}");
	}
			
}