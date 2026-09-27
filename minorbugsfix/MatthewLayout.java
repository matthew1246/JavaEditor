import java.awt.Toolkit;
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
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
public class MatthewLayout implements LayoutManager2 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		MatthewLayout matthewLayout = new MatthewLayout(true); // or MatthewLayout matthewLayout = new MatthewLayout(60,20);
		panel.setLayout(matthewLayout);
		frame.setSize(800,600);
		
		JPanel guaranteedlayout=new JPanel(new GuaranteedLayout());
		JPanel firstLabel = new JPanel(new GridBagLayout());
		firstLabel.add(new JLabel("Face:"));
		guaranteedlayout.add(firstLabel,new XYWidthHeight(0,0,1,1));
		JComboBox<String> combobox = new JComboBox<String>();
		combobox.addItem("Serif");
		combobox.addItem("2");
		guaranteedlayout.add(combobox,new XYWidthHeight(1,0,2,1));
		panel.add(guaranteedlayout,new XYWidthHeight(0,0,3,1));
		
		JTextArea textArea = new JTextArea("The quick brown fox jumps over the lazy dog");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		panel.add(textArea,new XYWidthHeight(1,0,3,6));
		
		JPanel guaranteedlayout2=new JPanel(new GuaranteedLayout());
		JPanel subzero = new JPanel(new GridBagLayout());
		subzero.add(new JLabel("Size:"));
		guaranteedlayout2.add(subzero,new XYWidthHeight(0,0,1,1));
		JComboBox<String> combobox2 = new JComboBox<String>();
		combobox2.addItem("8");
		combobox2.addItem("2");
		guaranteedlayout2.add(combobox2,new XYWidthHeight(1,0,2,1));
		panel.add(guaranteedlayout2,new XYWidthHeight(0,1,3,1));
		
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
	private Insets padding = new Insets(0,0,0,0);
	private int vGap = 0;
	private List<Component> components = new ArrayList<Component>();
	private List<XYWidthHeight> xywidthheights = new ArrayList<XYWidthHeight>();
	public MatthewLayout() {
		this(true);
	}
	public MatthewLayout(boolean isFill) {
		if(!isFill) {
			throw new RuntimeException("Need isFill to be true to use this constructor.");
		}
		this.isFill = isFill;
		padding = new Insets(3,3,3,3);
		vGap = 3;
	}
	public MatthewLayout(int minimumWidth, int minimumHeight) {
		this.minimumWidth = minimumWidth;
		this.minimumHeight = minimumHeight;
	}
	public void setShowBorders(boolean isBorders) {
		showBorders = isBorders;
	}
	public void setPadding(int top, int left, int bottom, int right) {
		padding = new Insets(top, left, bottom, right);
	}
	public void setGap(int vGap) {
		this.vGap = vGap;
	}
	public void addLayoutComponent(Component component, Object object) {
		XYWidthHeight xywidthheight;
		if(object instanceof XYWidthHeight) {
			xywidthheight = (XYWidthHeight)object;
		}
		else { // added without a constraint, so it gets its own row below everything else
			xywidthheight = new XYWidthHeight(0,getMaxRow(),1,1);
		}
		if(components.contains(component)) { // re-adding moves the component instead of duplicating it
			int oldindex = components.indexOf(component);
			components.remove(oldindex);
			xywidthheights.remove(oldindex);
		}
		int index = 0;
		while(index < xywidthheights.size()) {
			XYWidthHeight xywidthheight2 = xywidthheights.get(index);
			if(xywidthheight2.y > xywidthheight.y) {
				break;
			}
			if((xywidthheight2.y == xywidthheight.y) && (xywidthheight2.x > xywidthheight.x)) {
				break;
			}
			index++;
		}
		components.add(index,component);
		xywidthheights.add(index,xywidthheight);
	}
	public void removeLayoutComponent(Component component) {
		for(int i = 0; i < components.size(); i++) {
			if(components.get(i) == component) {
				components.remove(i);
				xywidthheights.remove(i);
				return;
			}
		}
	}
	public void addLayoutComponent(String name,Component component) {
		addLayoutComponent(component,null);
	}
	public void invalidateLayout(Container container) {
		// Nothing to do: the fractions of every component are already known.
	}
	public float getLayoutAlignmentX(Container container) {
		return 0.5f;
	}
	public float getLayoutAlignmentY(Container container) {
		return 0.5f;
	}
	/*
	** The preferred size is derived from the preferred sizes of the children
	** scaled by their XYWidthHeight fractions. It must never report the current
	** size of the container (or the size of the window), otherwise a parent
	** layout manager such as GridBagLayout would grow to fit this panel and
	** resize the whole row/frame.
	*/
	public Dimension preferredLayoutSize(Container container) {
		Insets insets = container.getInsets();
		int padL = insets.left + padding.left;
		int padR = insets.right + padding.right;
		int padT = insets.top + padding.top;
		int padB = insets.bottom + padding.bottom;
		int columns = getHighestXSumFraction();
		int rows = getHighestYSumFraction();
		if((components.size() == 0) || (columns <= 0) || (rows <= 0)) {
			return new Dimension(padL+padR,padT+padB);
		}
		int gaps = Math.max(0,getMaxRow()-1)*vGap;
		if(!isFill) {
			return new Dimension(padL+padR+columns*minimumWidth,padT+padB+rows*minimumHeight);
		}
		double xsize = 0;
		double ysize = 0;
		for(int i = 0; i < components.size(); i++) {
			XYWidthHeight xywidthheight = xywidthheights.get(i);
			Dimension preferred = components.get(i).getPreferredSize();
			if(preferred == null) {
				continue;
			}
			if(xywidthheight.width > 0) {
				xsize = Math.max(xsize,preferred.width/(double)xywidthheight.width);
			}
			if(xywidthheight.height > 0) {
				ysize = Math.max(ysize,preferred.height/(double)xywidthheight.height);
			}
		}
		int width = padL+padR+(int)Math.ceil(columns*xsize);
		int height = padT+padB+(int)Math.ceil(rows*ysize)+gaps;
		return new Dimension(width,height);
	}
	public Dimension minimumLayoutSize(Container container) {
		Insets insets = container.getInsets();
		return new Dimension(insets.left+insets.right,insets.top+insets.bottom);
	}
	public Dimension maximumLayoutSize(Container container) {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets insets = container.getInsets();
		return new Dimension(screensize.width,screensize.height-(insets.top+insets.bottom));
	}
	public void layoutContainer(Container container) 	{
		if(components.size() == 0) {
			return;
		}
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
				component.setBounds(insets.left+padding.left+xSum,insets.top+padding.top+ySum,minimumWidth*xywidthheight.width,minimumHeight*xywidthheight.height);
				showBorderIfNeeded(component);
			}
		}
		else { // isFill = true
			int highestXSumFraction = getHighestXSumFraction();
			int highestYSumFraction = getHighestYSumFraction();
			int maxRow = getMaxRow();
			Insets insets = container.getInsets();
			int padL = insets.left + padding.left;
			int padR = insets.right + padding.right;
			int padT = insets.top + padding.top;
			int padB = insets.bottom + padding.bottom;
			int gaps = Math.max(0,maxRow-1)*vGap;
			double xsize = Math.max(0,container.getWidth()-padL-padR) / ((double)highestXSumFraction);
			double ysize = Math.max(0,container.getHeight()-padT-padB-gaps) / ((double)highestYSumFraction);
			for(int i = 0; i < components.size(); i++) {
				XYWidthHeight xywidthheight = xywidthheights.get(i);
				Component component = components.get(i);
				int xSum = 0;
				int xcount = 0;
				for(int j = 0; j < components.size(); j++) {
					XYWidthHeight xywidthheight2 = xywidthheights.get(j);
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
					if(!xywidthheight2.equals(xywidthheight)) {
						if((getWeightx(xywidthheight2) == getWeightx(xywidthheight)) || isInclusiveY(xywidthheight,xywidthheight2)) {			
							ySum+= xywidthheight2.height;
						}
					}
					else break;
				}
				
				if(component instanceof JButton) {
					JButton button=(JButton) component;
					Insets insets2=button.getMargin();
					insets2.left = 0;
					insets2.right=0;
					button.setMargin(insets2);
				}
				
				/*
				** Only the bounds are set here. Changing the preferred/minimum/maximum
				** size of the children would make preferredLayoutSize() depend on the
				** current size of this panel, which is what made the parent layout grow.
				*/
				component.setBounds(padL+(int)(xSum*xsize),padT+(int)(ySum*ysize)+xywidthheight.y*vGap,(int)(xywidthheight.width*xsize),(int)(xywidthheight.height*ysize));
				showBorderIfNeeded(component);
			}
		}
	}
	private void showBorderIfNeeded(Component component) {
		if(showBorders) {
			JComponent jcomponent = (JComponent)component;
			jcomponent.setBorder(BorderFactory.createLineBorder(Color.black));
		}
	}
	private int getMaxRow() {
		int maxRow = 0;
		for(int i = 0; i < xywidthheights.size(); i++) {
			XYWidthHeight xywidthheight = xywidthheights.get(i);
			if(xywidthheight.y >= maxRow) {
				maxRow = xywidthheight.y + 1;
			}
		}
		return maxRow;
	}
	private int getHighestXSumFraction() {
		int highestXSumFraction = 0;
		for(int i = 0; i < xywidthheights.size(); i++) {
			XYWidthHeight xywidthheight = xywidthheights.get(i);
			int fractionXSum = 0;
			int count = 0;
			for(int j = 0; j < xywidthheights.size(); j++) {
				XYWidthHeight xywidthheight2 = xywidthheights.get(j);
				if(xywidthheight.y == xywidthheight2.y) {
					if(count != xywidthheight2.x) {
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
		return highestXSumFraction;
	}
	private int getHighestYSumFraction() {
		int highestYSumFraction = 0;
		for(int i = 0; i < xywidthheights.size(); i++) {
			XYWidthHeight xywidthheight = xywidthheights.get(i);
			int fractionYSum = 0;
			for(int j = 0; j < xywidthheights.size(); j++) {
				XYWidthHeight xywidthheight2 = xywidthheights.get(j);
				if(xywidthheight.x == xywidthheight2.x) {
					fractionYSum+= xywidthheight2.height;
				}					
			}
			if(fractionYSum > highestYSumFraction) {
				highestYSumFraction=fractionYSum;
			}
		}
		return highestYSumFraction;
	}
	public boolean isInclusiveY(XYWidthHeight xywidthheight,XYWidthHeight xywidthheight2) {
		int weightx2= getWeightx(xywidthheight2);
		int weightx = getWeightx(xywidthheight);
		return ((weightx2 < weightx) && ((weightx2+xywidthheight2.width) > weightx) );
	}
	public int getWeightx(XYWidthHeight xywidthheight) {
		int x = 0;
		for(XYWidthHeight xywidthheight2:xywidthheights) {
			if(xywidthheight.equals(xywidthheight2))
					return x;
			if(xywidthheight.y ==xywidthheight2.y) {
				x+=xywidthheight2.width;
			}		
		}
		return x;
	}
}
