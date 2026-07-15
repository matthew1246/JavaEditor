import java.util.ArrayList;
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
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.TreeMap;		
public class JTextAreaGroup extends JTextArea {
	public List<Code> codes = new LinkedList<Code>();
	private Main main;
	private int underlineStart = -1;
	private int underlineEnd = -1;
	private boolean ctrlDown = false;
	public JTextAreaGroup() {
		super();

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrlDown = true;
				}
			}
			@Override
			public void keyReleased(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrlDown = false;
					underlineStart = -1;
					underlineEnd = -1;
					JTextAreaGroup.this.repaint();
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent me) {
				if(ctrlDown) {
					int pos = viewToModel2D(me.getPoint());
					if(pos >= 0 && pos < getText().length()) {
						int[] bounds = getWordBounds(pos);
						if(bounds != null) {
							underlineStart = bounds[0];
							underlineEnd = bounds[1];
							setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						} else {
							underlineStart = -1;
							underlineEnd = -1;
							setCursor(Cursor.getDefaultCursor());
						}
					} else {
						underlineStart = -1;
						underlineEnd = -1;
						setCursor(Cursor.getDefaultCursor());
					}
					JTextAreaGroup.this.repaint();
				} else {
					if(underlineStart != -1 || underlineEnd != -1) {
						underlineStart = -1;
						underlineEnd = -1;
						setCursor(Cursor.getDefaultCursor());
						JTextAreaGroup.this.repaint();
					}
				}
			}
		});

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if(ctrlDown && underlineStart != -1 && underlineEnd != -1) {
					String word = getText().substring(underlineStart, underlineEnd);
					if(main != null) {
						main.openClassInNewTab(word);
					}
					return;
				}
				int caretposition=viewToModel2D(me.getPoint());
				Group group = groups.get(caretposition);
				if(group == null) {  // If expand code
					Pattern pattern=Pattern.compile("(?<!\")\\{\\+\\}(?!\")");
					text = JTextAreaGroup.this.getText();
					Matcher matcher=pattern.matcher(text);
					while(matcher.find()) {
						if(caretposition >= matcher.start() && caretposition <= matcher.end()) {
								
							Codes codes2 = new Codes(JTextAreaGroup.this);
							List<Integer> codesindex=codes2.getCodes();
							int index=codes2.getIndex(codesindex,matcher.start());
							Code code2 = codes.get(index);
							codes.remove(index);
							for(int i = 0; i < code2.codes.size(); i++) {
								codes.add(index+i,code2.codes.get(i));
							}
							String code=code2.code;
							String first=text.substring(0,matcher.start());
							String second=text.substring(matcher.end(),text.length());
							JTextAreaGroup.this.setText(first+code+second);
							JTextAreaGroup.this.setCaretPosition(caretposition);
						}	
					}																		
				}
				else if(group != null)
 {  // will compress code
					try {
						String text = getText();
						String first=text.substring(0,group.start+1);
						String last = text.substring(group.end-1,text.length());
						Codes codes2 = new Codes(JTextAreaGroup.this);
						List<Integer> codesindex=codes2.getCodes();
						int index=codes2.getIndex(codesindex,first.length());
						String m = text.substring(group.start,group.end);
						int sum=Count(m);
						Code code = new Code(text.substring(group.start,group.end));
						for(int i = 0; i < sum; i++) {
							code.codes.add(codes.get(index+i));
						}
						for(int i = 0; i < sum; i++) {
							codes.remove(index);
						}
						codes.add(index,code);
						
						setText(first+"+"+last);
						setCaretPosition(group.start+1);
					} catch(IndexOutOfBoundsException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}
	public int getAmountOfCodesBeforeCaretPosition(int caretposition) {
		Codes codes2 = new Codes(this);
		List<Integer> codesindex=codes2.getCodes();
		int index=codes2.getIndex(codesindex,caretposition);
		return index;
	}
	public void ExpandAll(Main main) {
		if(codes.size() > 0) {
			int caretposition = getCaretPosition();	
			int caretpositionrelativetocompressedcode=getAmountOfCodesBeforeCaretPosition(caretposition);

			Pattern pattern=Pattern.compile("(?<!\")\\{\\+\\}(?!\")");
			text = getText();
			Matcher matcher=pattern.matcher(text);
			int count = -1;
			
			StringBuilder stringbuilder = new StringBuilder();
			while(matcher.find()) {
				count++;
				String match=matcher.group();
				String insidecode=codes.get(count).ExpandAll();
				if((count+1) <= caretpositionrelativetocompressedcode) {
					caretposition=caretposition+insidecode.length();
				}
				match=match.replaceAll("\\{\\+\\}",Matcher.quoteReplacement(insidecode));
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
			super.setCaretPosition(caretposition);
			main.scrollToCaretPosition(caretposition);
		}
	}
	public boolean showLines = false;	
	public List<Integer> xaxisses = new ArrayList<Integer>();
	public List<Integer> yaxisses = new ArrayList<Integer>();
	public String previoustext="";
	HashMap<Integer,Group> groups = new HashMap<Integer,Group>();
	public String text = "";
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(java.awt.Color.blue);

		String text5 = super.getText();
		if(previoustext.equals(text5)) {
			for(int i = 0; i < xaxisses.size(); i++) {
				graphics.drawString("-",xaxisses.get(i),yaxisses.get(i));	
			}		
		}
		else {
			previoustext = text5;	
			xaxisses=new ArrayList<Integer>();
			yaxisses=new ArrayList<Integer>();
			
			groups = new HashMap<Integer,Group>();		
			Stack<Integer> leftcurlybraces= new Stack<Integer>();
				
			
			
			
		
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
							Rectangle2D rectanglecoords=super.modelToView2D(i+1);
							int a = (int)Math.round(rectanglecoords.getX());
							int b = (int)Math.round(rectanglecoords.getY()+20);			
							graphics.drawString("-",a,b);
							xaxisses.add(a);
							yaxisses.add(b);		
							
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
										
										if(showLines) {
											String text4 = text3.substring(0,i+1);
											String[] lines=text4.split("\\R");
											String line = lines[lines.length-1];
											//System.out.println("*"+line+"*");
											
											String firsttabs=getFirstTabs(line);
											//System.out.println("*"+firsttabs+"*");
											int z=line.length()-firsttabs.length();
											z=(i+1)-z;
											int x = (int)(super.modelToView2D(z).getX());
											int vertical_limit =(int)(super.modelToView2D(j+1).getY());
											for(int y = ((int)Math.round(rectanglecoords.getY()+20)); y <= vertical_limit; y+=20) {
												graphics.drawString("-",x,y);
												xaxisses.add(x);
												yaxisses.add(y);
											}
											
											/*
											String line= getLine(text3,(i+1));
											String firsttabs = getFirstTabs(line);
											String middle = text3.substring((i+1),(j+1));
											
											int q = i+2;
											*/
											/*for(int p = 0; p < lines.length; p++) {
												String line2= lines[p];
												q=q+line2.length();
												Rectangle2D rectanglecoords2=super.modelToView2D((q+firsttabs.length()));
		graphics.drawString("-",(int)Math.round(rectanglecoords2.getX()),(int)Math.round(rectanglecoords2.getY()+20));
											}
											*/
		
											
											
											//String line=getLine(text3,i+1);
											//String tabs=getFirstTabs(line);
											
											
											/*String line=getLine(text3,j+1);
											System.out.println("*"+line+"*");
											*/
										}		
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

		if(underlineStart != -1 && underlineEnd != -1) {
			try {
				Rectangle2D startRect = modelToView2D(underlineStart);
				Rectangle2D endRect = modelToView2D(underlineEnd - 1);
				if(startRect != null && endRect != null) {
					int x1 = (int)Math.round(startRect.getX());
					int x2 = (int)Math.round(endRect.getX() + endRect.getWidth());
					int y = (int)Math.round(endRect.getY() + endRect.getHeight()) + 2;
					graphics.setColor(Color.BLUE);
					graphics.drawLine(x1, y, x2, y);
				}
			} catch(BadLocationException ex) {
				// ignore
			}
		}
	}
	public String getLine(String text,int caretposition) {
		 text=text.substring(0,caretposition);
		 String[] lines=text.split("\\R");
		 return lines[lines.length-1];
	 }
	 public String getFirstTabs(String line) {
	 	Pattern pattern=Pattern.compile("^(\\s+)");
	 	Matcher matcher=pattern.matcher(line);
	 	if(matcher.find()) {
	 		return matcher.group(1);
 		}
 		return "";
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
		 Pattern pattern=Pattern.compile("(?<!\")\\{\\+\\}(?!\")");
		 Matcher matcher=pattern.matcher(text);
		 int count = 0;
		 while(matcher.find()) {
		 	count++;
	 	}
	 	return count;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public static CurlyBraceKeyListener findCurlyBraceKeyListener(javax.swing.JTextArea ta) {
		for(java.awt.event.KeyListener kl : ta.getKeyListeners()) {
			if(kl instanceof CurlyBraceKeyListener) return (CurlyBraceKeyListener)kl;
		}
		return null;
	}

	private int[] getWordBounds(int pos) {
		String text = getText();
		if(pos < 0 || pos >= text.length()) return null;
		char ch = text.charAt(pos);
		if(!Character.isLetterOrDigit(ch) && ch != '_') return null;
		int start = pos;
		int end = pos;
		while(start > 0 && (Character.isLetterOrDigit(text.charAt(start - 1)) || text.charAt(start - 1) == '_')) {
			start--;
		}
		while(end < text.length() && (Character.isLetterOrDigit(text.charAt(end)) || text.charAt(end) == '_')) {
			end++;
		}
		if(end - start < 2) return null;
		return new int[]{start, end};
	}
}