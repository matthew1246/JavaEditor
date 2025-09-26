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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.TreeMap;		
public class JTextAreaGroup extends JTextArea {
	public List<Code> codes = new LinkedList<Code>();
	public JTextAreaGroup() {
		super();
		
		this.addMouseListener(new MouseAdapter() { 
			@Override
			public void mousePressed(MouseEvent me) {
				int caretposition=viewToModel2D(me.getPoint());
				Group group = groups.get(caretposition);
				// debug click {-
				/*
				System.out.println(caretposition);
				TreeMap<Integer,Group> treemap = new TreeMap<Integer,Group>();
				for(int b:groups.keySet()) {
					treemap.put(b,null);
				}
				for(int b:treemap.keySet()) {
					System.out.print(b+",");
				}
				System.out.println();
				*/
				if(group == null) {  // If expand code
					Pattern pattern=Pattern.compile("\\{\\+\\}");
					text = JTextAreaGroup.this.getText();
					Matcher matcher=pattern.matcher(text);
					while(matcher.find()) {
						if(caretposition >= matcher.start() && caretposition <= matcher.end()) {
								
							Codes codes2 = new Codes(JTextAreaGroup.this);
							List<Integer> codesindex=codes2.getCodes();
							int index=codes2.getIndex(codesindex,matcher.start());
							Code code2 = codes.get(index);
							for(int i = 0; i < code2.codes.size(); i++) {
								codes.add(index+i,code2.codes.get(i));
							}
							String code=code2.code;
							String first=text.substring(0,matcher.start());
							String second=text.substring(matcher.end(),text.length());
							JTextAreaGroup.this.setText(first+code+second);
							JTextAreaGroup.this.setCaretPosition(caretposition);
							codes.remove(index);
						}	
					}																		
				}
				else if(group != null) {  // will compress code
					String text = getText();
					String first=text.substring(0,group.start+1);
					String last = text.substring(group.end-1,text.length());
					Codes codes2 = new Codes(JTextAreaGroup.this);
					List<Integer> codesindex=codes2.getCodes();
					int index=codes2.getIndex(codesindex,first.length());
					String middle = text.substring(group.start,group.end);
					int sum=Count(middle);
					Code code = new Code(text.substring(group.start,group.end));
					for(int i = 0; i < sum; i++) {
						code.codes.add(codes.get(index+i));
					}
					for(int i = 0; i < sum; i++) {
						codes.remove(index+i);
					}
					codes.add(index,code);
					
					setText(first+"+"+last);
					setCaretPosition(group.start+1);
				}
			}
		});
	}
	public void ExpandAll() {
		if(codes.size() > 0) {
			int caretposition = getCaretPosition();	
			Pattern pattern=Pattern.compile("\\{\\+\\}");
			text = getText();
			Matcher matcher=pattern.matcher(text);
			int count = -1;
			
			StringBuilder stringbuilder = new StringBuilder();
			while(matcher.find()) {
				count++;
				String match=matcher.group();
				match=match.replaceAll("\\{\\+\\}",Matcher.quoteReplacement(codes.get(count).code));
				matcher.appendReplacement(stringbuilder,matcher.quoteReplacement(match));
			}
			matcher.appendTail(stringbuilder);
			setText(stringbuilder.toString());
	
			/*
			while(matcher.find()) {
				count++;
				String code=codes.get(count);
				JOptionPane.showMessageDialog(null,"*"+code+"*");
				
				String first=text.substring(0,matcher.start());
				String second=text.substring(matcher.end(),text.length());
				setText(first+code+second);
			}
			*/
			codes = new LinkedList<Code>();
			setCaretPosition(caretposition);
		}
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
		String text3 = RemoveAll.LeftCurlyBraceInsideComments(text);
		//if(!text31.equals(text3)) {
			for(int i = 0; i < (text3.length()-4); i++) {
				String character = text3.substring(i,i+1);
				if(character.equals("{") && !isPlus(i+1) && !isRightCurlyBrace(i+2)) {
					try {
						Rectangle2D		 rectanglecoords=super.modelToView2D(i+1);
graphics.drawString("-",(int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()+20));
						groups.put(i+1,new Group());
						Stack<Integer> stack = new Stack<Integer>();
						stack.push(i);
						int j = i+1;
						while(true && j < text3.length()) {
							String character2 = text3.substring(j,j+1);
							if(character2.equals("{")) {
								stack.push(j);
							}
							else if(character2.equals("}")) {
								int rightcurlybrace=stack.pop();
								if(stack.size() == 0) {
									Group group=groups.get(i+1);
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
	public int Count(String text) {
		 int index = 0;
		 int count = 0;
		 while ((index = text.indexOf("{+}", index)) != -1) {
            		count++;
            		index += ("{+}").length(); // Move past the last found substring
        		}
		return count;
	}
}