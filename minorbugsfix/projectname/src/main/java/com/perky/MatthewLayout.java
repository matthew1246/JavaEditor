package com.perky;

import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.List;
import java.util.ArrayList;
import java.awt.LayoutManager2;
import javax.swing.JOptionPane;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Component;
import java.util.Random;
import java.awt.Container;
import java.awt.LayoutManager;
public class MatthewLayout implements LayoutManager2 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		MatthewLayout matthewLayout = new MatthewLayout(true); // or MatthewLayout matthewLayout = new MatthewLayout(60,20);
		panel.setLayout(matthewLayout);
		frame.setSize(800,600);
		
		JPanel firstLabel = new JPanel(new GridBagLayout());
		firstLabel.add(new JLabel("Face:"));
		panel.add(firstLabel,new XYWidthHeight(0,0,1,1));
		JComboBox<String> combobox = new JComboBox<String>();
		combobox.addItem("Serif");
		combobox.addItem("2");
		panel.add(combobox,new XYWidthHeight(1,0,2,1));
		
		JTextArea textArea = new JTextArea("The quick brown fox jumps over the lazy dog");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		panel.add(textArea,new XYWidthHeight(2,0,3,6));
		JPanel subzero = new JPanel(new GridBagLayout());
		subzero.add(new JLabel("Size:"));
		panel.add(subzero,new XYWidthHeight(0,1,1,1));
		JComboBox<String> combobox2 = new JComboBox<String>();
		combobox2.addItem("8");
		combobox2.addItem("2");
		panel.add(combobox2,new XYWidthHeight(1,1,2,1));
		JPanel panel_3 = new JPanel(new GridBagLayout());
		panel_3.add(new JCheckBox());
		panel_3.add(new JLabel("Bold"));
		panel.add(panel_3,new XYWidthHeight(0,2,3,2));
		JPanel panel_4 = new JPanel(new GridBagLayout());
		panel_4.add(new JCheckBox());
		panel_4.add(new JLabel("Italic"));
		panel.add(panel_4,new XYWidthHeight(0,3,3,2));
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private boolean isFill;
	public boolean showBorders = false;
	private int minimumWidth;
	private int minimumHeight;
	public MatthewLayout(boolean isFill) {
		if(!isFill) {
			throw new RuntimeException("Need isFill to be true to use this constructor.");
		}
		this.isFill = isFill;
	}
	public MatthewLayout(int minimumWidth, int minimumHeight) {
		this.minimumWidth = minimumWidth;
		this.minimumHeight = minimumHeight;
	}
	public void setShowBorders(boolean isBorders) {
		showBorders = isBorders;
	}
	private List<Component> components = new ArrayList<Component>();
	private List<XYWidthHeight> xywidthheights = new ArrayList<XYWidthHeight>();
	public void addLayoutComponent(Component component, Object object) {
		Dimension dimension = component.getPreferredSize();
		
		XYWidthHeight xywidthheight = (XYWidthHeight)object;
		if(xywidthheights.size() > 0) { // normal
			/*if(dimension.width < minimumWidth) {
				minimumWidth = dimension.width;
				minimumHeight = dimension.height;
			}*/
			for(int i = xywidthheights.size()-1; i >= 0; i--) {
				XYWidthHeight xywidthheight2 = xywidthheights.get(i);
				if(xywidthheight.y > xywidthheight2.y) {
					components.add(i+1,component);
					xywidthheights.add(i+1,xywidthheight);					
					break;
				}
				else if(xywidthheight.y == xywidthheight2.y) {
					if(xywidthheight.x > xywidthheight2.x) {
						components.add(i+1,component);
						xywidthheights.add(i+1,xywidthheight);
						break;
					}
					else if(i == 0) {
						components.add(0,component);
						xywidthheights.add(0,xywidthheight);
					}
				}
				else if(i == 0) {
					components.add(0,component);
					xywidthheights.add(0,xywidthheight);
				}
			}
		}
		else {
			/*minimumWidth = dimension.width;
			minimumHeight = dimension.height;*/
			components.add(component);
			xywidthheights.add(xywidthheight);
		}
	}
	
	public Dimension maximumLayoutSize(Container container) {
		JOptionPane.showMessageDialog(null,"maximumLayoutSize() called");
		return container.getPreferredSize();
	}
	
	
public boolean isOn = false;
	public int originalWidth;
	public int originalHeight;
	public void invalidateLayout(Container container) {
		if(container instanceof JPanel) {
			// System.out.println("container is jpanel");
			JPanel panel = (JPanel)container;				
			Container container2 = panel.getParent();
			JFrame frame=(JFrame)SwingUtilities.getWindowAncestor(container);
			Dimension realsize = frame.getSize();
			System.out.println("frame realsize is "+realsize);
			int width = (int)realsize.getWidth();
			int height = (int)realsize.getHeight();							
			if(width != 0 && height != 0) {
				originalWidth = width;
				originalHeight = height;
			}
		}
		if(container instanceof JFrame) {
			System.out.println("container is JFrame");
		}
		/*if(container instanceof JPanel) {
			JPanel panel = (JPanel)container;
			if(panel.getWidth() == 0 && panel.getHeight() == 0) {			
				Dimension preferredsize=panel.getSize();
				if(preferredsize.getWidth() == 0 && preferredsize.getHeight() == 0) {								
					Container container2 = container.getParent();
					if(container2 instanceof JFrame) {
						JFrame frame = (JFrame)container2;
						Dimension preferredsize2 = frame.getPreferredSize();
						if(preferredsize2.getWidth() == 0 && preferredsize2.getHeight() == 0) {
							Dimension realsize = frame.getSize();
							int width = (int)realsize.getWidth();
							int height = (int)realsize.getHeight();							
							if(width != 0 && height != 0) {
								originalWidth = width;
								originalHeight = height;
							}
						}
					}
				}
			}
		}*/
		// JOptionPane.showMessageDialog(null,"invalidateLayout called");
		/*SwingUtilities.invokeLater(new Runnable(){
		        public void run(){
		            	int width = container.getWidth();
		            	int height = container.getHeight();
				System.out.println("width is "+width);
					originalWidth = width;
					originalHeight = height;
					// debugger.Output(width,height);
					isOn = true;
			}
		});*/
	}
	public float getLayoutAlignmentX(Container container) {
		JOptionPane.showMessageDialog(null,"getLayoutAlignmentX() called");
		return 0.5f;
	}
	public float getLayoutAlignmentY(Container container) {
		JOptionPane.showMessageDialog(null,"getLayoutAlignmentY called.");
		return 0.5f;
	}
	public void removeLayoutComponent(Component component) {
		JOptionPane.showMessageDialog(null,"removeLayoutComponent() called");
	}
	public void addLayoutComponent(String name,Component component) {
		JOptionPane.showMessageDialog(null,"addLayoutComponent() called");
	}
	public Dimension preferredLayoutSize(Container container) {
		// layoutContainer(container);
		// JOptionPane.showMessageDialog(null,"preferredLayoutSize called.");
		/*if(isFill && (container.getWidth() == 0) && (container.getHeight() == 0)) {
			Dimension screensize= Toolkit.getDefaultToolkit().getScreenSize();
			container.setSize(screensize);
		}	
		layoutContainer(container);
		int xSum = 0;
		for(int j = 0; j < components.size(); j++) {
			XYWidthHeight xywidthheight2 = xywidthheights.get(j);
			Component component4 = components.get(j);
			if(xywidthheight2.y == 0) {
				xSum+= component4.getBounds().getWidth();
			}
		}
		int ySum = 0;
		for(int j = 0; j < components.size(); j++) {
			XYWidthHeight xywidthheight2 = xywidthheights.get(j);
			Component component4 = components.get(j);
			if(xywidthheight2.x == 0) {
				ySum+= component4.getBounds().getHeight();
			}
		}
		Dimension dimension = new Dimension(0,0);
		Insets insets = container.getInsets();
		dimension.width = insets.left+xSum+insets.right;
		dimension.height = insets.top+ySum+insets.bottom;
*/
		
		// System.out.println("originalWidth is "+originalWidth);				
		// System.out.println("originalHeight is "+originalHeight);
		// System.out.println("preferredLayout width "+container.getSize());
		Dimension dimension=container.getSize();
		if(dimension.getWidth() != 0.0 && dimension.getHeight() != 0.0) {
			layoutContainer(container);
			return dimension;
			
		}
		else {
			return new Dimension(originalWidth,originalHeight);
		}
	}
	public Dimension minimumLayoutSize(Container container) {
		JOptionPane.showMessageDialog(null,"minimumLayoutSize() called");
		Dimension dimension = new Dimension(0,0);
		Insets insets = container.getInsets();
		dimension.width = insets.left+insets.right;
		dimension.height = insets.top+insets.bottom;
		return dimension;
	}
	int x = 0;
	public void layoutContainer(Container container) 	{
		if(x == 0) 
		for(int i = 0; i < xywidthheights.size(); i++) {
			XYWidthHeight xywidthheight = xywidthheights.get(i);
		}
		x++;
		if(!isFill) {
			for(int i = 0; i < components.size(); i++) {
				int containerWidth = container.getWidth();
				Component component = components.get(i);
				XYWidthHeight xywidthheight = xywidthheights.get(i);
			
				int xSum = 0;
				for(int j = 0; j < components.size(); j++) {
					XYWidthHeight xywidthheight2 = xywidthheights.get(j);
					Component component4 = components.get(j);
					if(!xywidthheight2.equals(xywidthheight)) {
						if(xywidthheight2.y == xywidthheight.y) {
							xSum+= component4.getBounds().getWidth();
						}
					}
					else break;
				}
				int ySum = 0;
				for(int j = 0; j < components.size(); j++) {
					XYWidthHeight xywidthheight2 = xywidthheights.get(j);
					Component component4 = components.get(j);
					if(!xywidthheight2.equals(xywidthheight)) {
						if(xywidthheight2.x == xywidthheight.x) {
							ySum+= component4.getBounds().getHeight();
						}
					}
					else break;
				}
			
				Insets insets = container.getInsets();
				component.setBounds(insets.left+xSum+insets.right,insets.top+ySum+insets.bottom,minimumWidth*xywidthheight.width,minimumHeight*xywidthheight.height);
				if(showBorders) {
					JComponent jcomponent = (JComponent)component;
					jcomponent.setBorder(BorderFactory.createLineBorder(Color.black));
				}
			}
		}
		else { // isFill = true
			int highestXSumFraction = 0;
			for(int i = 0; i < components.size(); i++) {
				XYWidthHeight xywidthheight = xywidthheights.get(i);
				int fractionXSum =0;
				int count = 0;
				for(int j = 0; j < components.size(); j++) {
					XYWidthHeight xywidthheight2= xywidthheights.get(j);
					if(xywidthheight.y == xywidthheight2.y) {
						if(count != xywidthheight2.x) {
							// JOptionPane.showMessageDialog(null,""+xywidthheight2.x+" "+count);
							int z = xywidthheight2.x-count;
							fractionXSum+= z;
							count+= z;
						}
						fractionXSum+= xywidthheight2.width;
						count++;
					}					
				}
				if(fractionXSum > highestXSumFraction) {
					highestXSumFraction=fractionXSum;
				}
			}
			int highestYSumFraction = 0;
			for(int i = 0; i < components.size(); i++) {
				XYWidthHeight xywidthheight = xywidthheights.get(i);
				int fractionYSum =0;
				for(int j = 0; j < components.size(); j++) {
					XYWidthHeight xywidthheight2= xywidthheights.get(j);
					if(xywidthheight.x == xywidthheight2.x) {
						fractionYSum+= xywidthheight2.height;
					}					
				}
				if(fractionYSum > highestYSumFraction) {
					highestYSumFraction=fractionYSum;
				}
			}
			for(int i = 0; i < components.size(); i++) {
				XYWidthHeight xywidthheight = xywidthheights.get(i);
				Component component = components.get(i);
				int xSum = 0;
				int xcount = 0;
				for(int j = 0; j < components.size(); j++) {
					XYWidthHeight xywidthheight2 = xywidthheights.get(j);
					Component component4 = components.get(j);
					if(!xywidthheight2.equals(xywidthheight)) {
						if(xywidthheight2.y == xywidthheight.y) {
							if(xcount != xywidthheight2.x) {
								int z = xywidthheight2.x-xcount;
								xSum+= z;
								xcount+=z;
							}										
							xSum+= xywidthheight2.width;
							xcount++;
						}
					}
					else {
						if(xcount != xywidthheight2.x) {
							int z = xywidthheight2.x-xcount;
							xSum+= z;
							xcount+=z;
						}		
						break;
					}

				}
				int ySum = 0;
				for(int j = 0; j < components.size(); j++) {
					XYWidthHeight xywidthheight2 = xywidthheights.get(j);
					Component component4 = components.get(j);
					if(!xywidthheight2.equals(xywidthheight)) {
						if(xywidthheight2.x == xywidthheight.x) {							
							ySum+= xywidthheight2.height;
						}
					}
					else break;
				}
				Insets insets = container.getInsets();
				System.out.println("Insets "+insets);
				double xsize = ((double)container.getWidth()) / ((double)highestXSumFraction);
				// xsize=(int)(xsize+0.5);
				// JOptionPane.showMessageDialog(null,""+xsize);
				// System.out.println("xsize is "+xsize);
				double ysize = ((double)container.getHeight()) / ((double)highestYSumFraction);
				// System.out.println("ysize is " +  ysize);
				// container.setWidth(800);
				
				if(component instanceof JButton) {
					JButton button=(JButton) component;
					Insets insets2=button.getMargin();
					insets2.left = 0;
					insets2.right=0;
					button.setMargin(insets2);
				}
				
				System.out.println("sizes "+xywidthheight.x+" "+xywidthheight.y+" "+xSum+ " " + xsize);
				component.setLocation(insets.left+insets.right+(int)(xSum*xsize),insets.bottom+insets.top+(int)(ySum*ysize));
				// JOptionPane.showMessageDialog(null,(xywidthheight.width*((int)xsize))+"");
				component.setMinimumSize(new Dimension(xywidthheight.width*((int)xsize),xywidthheight.height*((int)ysize)));
				component.setMaximumSize(new Dimension(xywidthheight.width*((int)xsize),xywidthheight.height*((int)ysize)));
				component.setSize(new Dimension(xywidthheight.width*((int)xsize),xywidthheight.height*((int)ysize)));
				component.setPreferredSize(new Dimension(xywidthheight.width*((int)xsize),xywidthheight.height*((int)ysize)));
				// System.out.println(container.getWidth()+" "+(int)(((double)xywidthheight.x)*xsize)+" "+(xywidthheight.width*((int)xsize))+" "+(xywidthheight.height*((int)ysize))+" "+(int)xsize+" "+(int)ysize);
				component.validate();
				component.repaint();
				if(showBorders) {
					JComponent jcomponent = (JComponent)component;
					jcomponent.setBorder(BorderFactory.createLineBorder(Color.black));
				}
			}
		}
	}
}
