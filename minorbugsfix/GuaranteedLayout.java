import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuaranteedLayout extends GridBagLayout {

    public static void main(String[] args) {
        // Completely block OS scaling distortions
        // System.setProperty("sun.java2d.uiScale", "1.0");

        JFrame frame = new JFrame("Strict 1:2:1 Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.YELLOW);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.CYAN);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GREEN);

        // SECRET SAUCE: Force the components' preferred sizes to 0.
        // This tells Java to completely ignore internal content sizes
        // and rely strictly on the mathematical weights.
        panel1.setPreferredSize(new Dimension(0, 0));
        panel2.setPreferredSize(new Dimension(0, 0));
        panel3.setPreferredSize(new Dimension(0, 0));

        // Use GridBagLayout instead of a null layout
        JPanel mainContainer = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Force components to stretch completely to fill the space
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0; // Fill 100% of vertical height
        
        /*
        1:2 is maths ratio of two columns. How to make bug ratio for two columns?
        1 is first column is whole screen area.
        1*(1/2) is amount of padding that can go to second column to make first column
        1/2x=1/3 or 1/2x=2/3
        x=2/3   or x=4/3
        2:3 is the bug ratio of first and second column
        */
        
        /*
        1:2:1 is maths ratio of three columns. How to make bug ratio for three columns?
        2:3 is bug ratio for first two columns
        Third column is same bug ratio as first column
        Therefore third column is same weightx number as third column
        Thus no need to calculate weightx for third column
        Hence third column = 2 because first column = 2 so no calculation required.
        */
        
        /* Old values
        c.gridx = 0;
        c.weightx = 2.0;
        mainContainer.add(panel1, c);

        c.gridx = 1;
        c.weightx = 3.0;
        mainContainer.add(panel2, c);

        c.gridx = 2;
        c.weightx = 2.0;
        mainContainer.add(panel3, c);
        */
        
          /*
        6x=1/2
        x=1/12
        3-1/12
        3/1-1/12
        36/12-1/12
        35/12
        
        x=1/6
        3-1/6
        3/1-1/6
        18/6-1/6
        17/6
        */
        
        // Column 1: Left (Weight = 0.25)
        c.gridx = 0;
        c.weightx = 2.0;
        mainContainer.add(panel1, c);

        // Column 2: Middle (Weight = 0.50 - Exactly twice the sides)
        c.gridx = 1;
        c.weightx = 3.0;
        mainContainer.add(panel2, c);
        
        // 2/3 / 2 (2/6)x=1/2 x=6*1/(2*2) x = 3/2
        
        /*
        Calculate bug ratio for 1:2:3
        2:3 is bug ratio of first and second column
        2/3 is second column when full screen is only two columns
        (2/3) / 2 is amount of padding in second column that can go to new third column
        (2/3) / 2 = 2/6
        2/6x=1/2 equals half because third column is half the screen.
        1/3x=1/2
        x = 3/2
        x = 1.5 
        3 + 1.5 = 4.5 or 3*1.5=4.5
        */
        
        // Column 3: Right (Weight = 0.25)
        c.gridx = 2;
        c.weightx = 4.5; // 24.0/3.0; // (2/3)x =1/2 x=4/3 (4/3)*6=24/3
        mainContainer.add(panel3, c);

        mainContainer.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(mainContainer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        System.out.println(panel1.getWidth());
System.out.println(panel2.getWidth());
System.out.println(panel3.getWidth());
    }
    
    public GuaranteedLayout() {
    	super();
    }
    	private List<XYWidthHeight> xywidthheights = new ArrayList<XYWidthHeight>();	
    	private List<GridBagConstraints> gbcs = new ArrayList<GridBagConstraints>();
    	@Override
    	public void addLayoutComponent(Component panel,Object xywidthheight1) {
           		xywidthheights.add(((XYWidthHeight)xywidthheight1));
           		
           		panel.setPreferredSize(new Dimension(0, 0));
            	GridBagConstraints c = new GridBagConstraints();
        
        		// Force components to stretch completely to fill the space
  		c.fill = GridBagConstraints.BOTH;
            	XYWidthHeight xywidthheight=(XYWidthHeight)xywidthheight1;
            	c.gridx = xywidthheight.x;
            	c.gridy = xywidthheight.y;
            	
            	Last number:First ratio * middle number * Last ratio
            	1:2:3                                      1:2:1				
            	2:3:9/2  			2:3:2					
            	(1:2):(2:3)*(1:2:3)		(1:2):(2:1):(1:2:1)
            	(2/1)*(3/2)*(2:3)		(2/1)*(1/2)*(2/1)
            	ratio is 2:ratio is 3:		ratio is 2: ratio is 2			
            	2*3/2*(1:2:3)			2*1/2*2			
            	3*(3/2)                                   (2/2)*2
            	9/2				2				
            	
            	Second number when only two numbers? For eg) 1:2
            	
            	Formula for two digits: y=1/mean
            	1:2		                      	            1:2		
            	2:3             	                                   First digit=(second digit/first digit)
            	2:2*mean            	          	       	second digit=(1:2):(mean)	
            	2:2*((1+2)/2)    	                       second digit=(2):((1+2)/2)
            	2:2(3/2)				second digit=3
            	2:3
            	
            	1:2
            	3:2
            	y=1/mean
            	y=1/[(1+2)/2]
            	y=1/(3/2)
            	y=2/3				
            	
            	Formula for ratio of two numbers:mean				        	
            	2:1
            	Formula when second digit smaller than first digit: y=1/(1/mean) Note 1/(1/mean)=mean
            	y=1/(1/3/2)
            	y=1/(2/3)
            	y=3/2
            	if(xywidthheights.size() == 1) {
            		c.weightx=xywidthheight.width;
            	}
            	else if(xywidthheights.size() = 2) {
            		XYWidthHeight xywidthheight2=xywidthheights.get(0);
            		double two=xywidthheight2.width/xywidthheight.width;
            		Fraction mean=new Fraction();
            		mean.numerator=xywidthheight.width+xywidthheight2.width;
            		mean.denominator=2;
            		
            		double thirdrowpartone=xywidthheight.width*two;
            		double thirdrowparttwo=two*mean;
            	}
           }
           public void Sort(XYWidthHeight xywidthheight) {
           		// XYWidthHeight xywidthheight = (XYWidthHeight)object;
		if(xywidthheights.size() > 0) { // normal
			/*if(dimension.width < minimumWidth) {
				minimumWidth = dimension.width;
				minimumHeight = dimension.height;
			}*/
			for(int i = xywidthheights.size()-1; i >= 0; i--) {
				XYWidthHeight xywidthheight2 = xywidthheights.get(i);
				if(xywidthheight.y > xywidthheight2.y) {
					// components.add(i+1,component);
					xywidthheights.add(i+1,xywidthheight);					
					break;
				}
				else if(xywidthheight.y == xywidthheight2.y) {
					if(xywidthheight.x > xywidthheight2.x) {
						// components.add(i+1,component);
						xywidthheights.add(i+1,xywidthheight);
						break;
					}
					else if(i == 0) {
						// components.add(0,component);
						xywidthheights.add(0,xywidthheight);
					}
				}
				else if(i == 0) {
					// components.add(0,component);
					xywidthheights.add(0,xywidthheight);
				}
			}
		}
		else {
			/*minimumWidth = dimension.width;
			minimumHeight = dimension.height;*/
			// components.add(component);
			xywidthheights.add(xywidthheight);
		}		
           }
}