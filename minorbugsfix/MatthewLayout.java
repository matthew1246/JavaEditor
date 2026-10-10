package javaeditor.minorbugsfix;

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
import java.awt.Window;
import javax.swing.JDialog;
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
		padding = new Insets(0,0,0,0);
		vGap = 0;
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
		removeButtonMargin(component);
	}
	/*
	** A JButton keeps a left and right margin of its own, which takes that much
	** off the width that is left for the label. The buttons here are stretched to
	** fill a cell of the grid, so that margin only ever clips the label, and it is
	** taken away here, once, when the component is added.
	**
	** This used to be done in layoutContainer(), which was the wrong place: the
	** margin feeds into the preferred size of the button, so every layout pass
	** quietly shrank the children and made the next preferredLayoutSize() answer
	** with a smaller size than the one before it. Laying out is not allowed to
	** change a child, and doing it here means the preferred size that is asked for
	** is the very same one before and after the first layout pass.
	*/
	private void removeButtonMargin(Component component) {
		if(!(component instanceof JButton)) {
			return;
		}
		JButton button = (JButton)component;
		Insets margin = button.getMargin();
		if((margin == null) || ((margin.left == 0) && (margin.right == 0))) {
			return;
		}
		button.setMargin(new Insets(margin.top,0,margin.bottom,0));
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
		int padT = insets.top + padding.top + getWindowTopInset(container);
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
		/*
		** The size that is asked for here is the size at which the fractions resolve
		** to exactly the preferred sizes of the children. The unit sizes are kept as
		** doubles and then rounded up once, so that laying this preferred size out
		** yields the very same unit sizes and no child is ever squeezed below its
		** preferred size.
		*/
		double xunit = getPreferredUnitX();
		double yunit = getPreferredUnitY();
		int width = padL+padR+(int)Math.ceil(columns*xunit);
		int height = padT+padB+(int)Math.ceil(rows*yunit)+gaps;
		return new Dimension(width,height);
	}
	/*
	** The number of pixels that one fraction of x has to be worth so that the
	** widest child is not narrower than its preferred size. Zero if nothing is
	** added yet.
	*/
	private double getPreferredUnitX() {
		double xunit = 0;
		for(int i = 0; i < components.size(); i++) {
			XYWidthHeight xywidthheight = xywidthheights.get(i);
			Dimension preferred = components.get(i).getPreferredSize();
			if((preferred == null) || (xywidthheight.width <= 0)) {
				continue;
			}
			xunit = Math.max(xunit,preferred.width/(double)xywidthheight.width);
		}
		return xunit;
	}
	/*
	** The number of pixels that one fraction of y has to be worth so that the
	** tallest child is not shorter than its preferred size. Zero if nothing is
	** added yet.
	*/
	private double getPreferredUnitY() {
		double yunit = 0;
		for(int i = 0; i < components.size(); i++) {
			XYWidthHeight xywidthheight = xywidthheights.get(i);
			Dimension preferred = components.get(i).getPreferredSize();
			if((preferred == null) || (xywidthheight.height <= 0)) {
				continue;
			}
			yunit = Math.max(yunit,preferred.height/(double)xywidthheight.height);
		}
		return yunit;
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
			Insets insets = container.getInsets();
			int padT = insets.top + padding.top + getWindowTopInset(container);
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
			
				component.setBounds(insets.left+padding.left+xSum,padT+ySum,minimumWidth*xywidthheight.width,minimumHeight*xywidthheight.height);
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
			int padT = insets.top + padding.top + getWindowTopInset(container);
			int padB = insets.bottom + padding.bottom;
			int gaps = Math.max(0,maxRow-1)*vGap;
			int availableWidth = Math.max(0,container.getWidth()-padL-padR);
			int availableHeight = Math.max(0,container.getHeight()-padT-padB-gaps);
			/*
			** The leftover pixels are spread over the rows instead of being dropped,
			** so the last row ends exactly on the bottom padding. The per row height
			** then alternates by one pixel, which no gap is ever made of, because
			** every component of a row takes its top and its bottom from the very
			** same two pixel lines.
			*/
			double xsize = (highestXSumFraction > 0) ? (availableWidth/((double)highestXSumFraction)) : 0;
			double ysize = (highestYSumFraction > 0) ? (availableHeight/((double)highestYSumFraction)) : 0;
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
				
				int left = padL + (int)Math.round(xSum*xsize);
				int right = padL + (int)Math.round((xSum+xywidthheight.width)*xsize);
				int top = rowTop(ySum, padT, availableHeight, highestYSumFraction, vGap);
				int bottom = rowBottom(ySum+xywidthheight.height, padT, availableHeight, highestYSumFraction, vGap);
				if (right < left) right = left;
				if (bottom < top) bottom = top;
				component.setBounds(left, top, right-left, bottom-top);
				showBorderIfNeeded(component);
			}
		}
	}
	/*
	** The pixel line where a row starts: the rows above it scaled over the space
	** that is left once the gaps are taken out, plus the gap above each of those
	** rows. The first row starts at the top padding, and the scaling is rounded
	** once per line instead of once per row so that the lines cannot drift apart
	** and leave a gap along a row boundary.
	*/
	private int rowTop(int row,int padT,int availableHeight,int rows,int vGap) {
		return rowPixel(row,padT,availableHeight,rows,vGap,true);
	}
	/*
	** The pixel line where a row ends: the rows down to it scaled over the space
	** that is left once the gaps are taken out, plus the gap between those rows
	** only, because the gap below the last row belongs to what comes next. The
	** bottom of the last row therefore lands on the bottom padding.
	*/
	private int rowBottom(int row,int padT,int availableHeight,int rows,int vGap) {
		return rowPixel(row,padT,availableHeight,rows,vGap,false);
	}
	/*
	** One shared pixel line for both edges, so that every component of a row is
	** given the very same top and the very same bottom and can never end up a
	** pixel away from its neighbour. belowGap decides whether the gap below the
	** line belongs to it or to the next row. The whole available height is scaled
	** from zero, so the line of the last row lands on the bottom padding and no
	** pixel is left over as a gap along the bottom. A row past the end is clamped
	** onto the last line, which keeps a badly written constraint inside the
	** container instead of hanging out of it.
	*/
	private int rowPixel(int row,int padT,int availableHeight,int rows,int vGap,boolean belowGap) {
		if(rows <= 0) {
			return padT;
		}
		if(row <= 0) {
			return padT;
		}
		if(row >= rows) {
			row = rows;
			belowGap = false;
		}
		int scaled = (int)Math.round((row*availableHeight)/(double)rows);
		return padT+scaled+((belowGap ? row : row-1)*vGap);
	}
	/*
	** The border of a window is what keeps the left, right and bottom edges of a
	** panel that is added straight to the content pane away from its components:
	** that inset is empty frame and reads as a margin. The top inset of a window
	** is not empty, it is the title bar, and the content pane starts on the very
	** last line of it, so a first row on pixel 0 is drawn flush against the title
	** bar. The width of the empty border is measured from the window and given to
	** the top as well, so that all four edges end up the same distance from the
	** window.
	**
	** Only a panel that is at the top of the content pane itself is given it, and
	** only when nothing sits above it there: neither another panel nor the menu
	** bar of the window (the content pane starts below the menu bar, so a panel
	** added straight to it is not on the title bar either). A panel further down
	** the window, and a panel inside another panel (the arrows of Control F, the
	** starter panel of the menubar), already have that margin and must not be
	** pushed down twice. A window that is not on screen yet has no insets at all,
	** so nothing is given before the decorations are known, and by the time they
	** are the panel has already been given its real place in the window.
	*/
	private int getWindowTopInset(Container container) {
		Window window = javax.swing.SwingUtilities.getWindowAncestor(container);
		if((window instanceof JFrame) || (window instanceof JDialog)) {
			Container contentpane = (window instanceof JFrame) ? ((JFrame)window).getContentPane() : ((JDialog)window).getContentPane();
			if((container.getParent() == contentpane) && (container.getY() <= 0) && (contentpane.getY() <= 0)) {
				Insets insets = window.getInsets();
				if(insets != null) {
					return Math.max(insets.left,Math.max(insets.right,insets.bottom));
				}
			}
		}
		return 0;
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
