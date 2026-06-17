import javax.swing.JOptionPane;
import javax.swing.JButton;
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
    	JFrame frame = new JFrame();
    	frame.setSize(800,600);
    	JPanel panel = new JPanel();
    	GuaranteedLayout guaranteedlayout = new GuaranteedLayout();
    	panel.setLayout(guaranteedlayout);
    	
    	XYWidthHeight xywidthheight=new XYWidthHeight();
    	xywidthheight.x=0;
    	xywidthheight.y=0;
    	xywidthheight.width=1;
    	xywidthheight.height=1;
    	panel.add(new JButton("Hello World!"),xywidthheight);
    
    	XYWidthHeight xywidthheight2=new XYWidthHeight();
    	xywidthheight2.x=1;
    	xywidthheight2.y =0;
    	xywidthheight2.width=2;
    	xywidthheight2.height=1;
    	panel.add(new JButton("Hello World!"),xywidthheight2);
    	
    	XYWidthHeight xywidthheight3=new XYWidthHeight();
    	xywidthheight3.x=2;
    	xywidthheight3.y =0;
    	xywidthheight3.width=3;
    	xywidthheight3.height=1;
    	panel.add(new JButton("Hello World!"),xywidthheight3);
    	
    	frame.add(panel);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);	
    	
    	System.out.println(guaranteedlayout);
    }						
    public GuaranteedLayout() {
    	super();
    }
    	private List<XYWidthHeight> xywidthheights = new ArrayList<XYWidthHeight>();
    	private List<Component> components=  new ArrayList<Component>();
    	@Override
    	public void addLayoutComponent(Component panel,Object constraint) {
           		XYWidthHeight xywidthheight=(XYWidthHeight)constraint;
           	
           		boolean contains = false;
           		for(XYWidthHeight xywidthheight2:xywidthheights) {
           			if((xywidthheight.x==xywidthheight2.x) && (xywidthheight.y == xywidthheight2.y) && 
           			(xywidthheight.width == xywidthheight2.width) && (xywidthheight.height == xywidthheight2.height)) {
           				contains=true;
           			}
           		}
           		/*
           		if(!contains) {
           			xywidthheights.add(xywidthheight);
           			components.add(panel);
           		}
           		*/
           		
           		panel.setPreferredSize(new Dimension(0, 0));
        
            	/*
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
            	Formula when second digit smaller than first digit: y=1/(1/mean)                                       Note 1/(1/mean)=mean
            	y=1/(1/3/2)
            	y=1/(2/3)
            	y=3/2
            	
            	*/
            	
            	for(int i = 0; i < components.size(); i++) {
            		super.removeLayoutComponent(components.get(i));
            	}
            	
            	Sort(xywidthheight,panel);
            	
            	if(xywidthheights.size() == 1) {
            		GridBagConstraints gbc = new GridBagConstraints();
            		gbc.gridx=xywidthheight.x;
            		gbc.gridy=xywidthheight.y;
            		gbc.weightx=xywidthheight.width;
            		gbc.weighty=xywidthheight.height;
            		gbc.fill=GridBagConstraints.BOTH;	
                       	panel.setPreferredSize(new Dimension(0, 0));
                       	super.setConstraints(panel,gbc);
                       	panel.revalidate();
                       	panel.repaint();
            	}
            	else if (xywidthheights.size() == 2) {
            		XYWidthHeight xywidthheight1=xywidthheights.get(0);
            		XYWidthHeight xywidthheight2=xywidthheights.get(1);
            		
            		Fraction mean=new Fraction();
            		mean.numerator=xywidthheight1.width+xywidthheight2.width;
            		mean.denominator=xywidthheights.size();
            		
            		mean.Flip(); // coz ratio=1/(mean)
            		if(xywidthheight1.width > xywidthheight2.width) { 
            			mean.Flip();
            		}
            		
            		GridBagConstraints gbc1 = new GridBagConstraints();
            		gbc1.gridx=xywidthheight1.x;
            		gbc1.gridy=xywidthheight1.y;
            		gbc1.weightx=mean.numerator;
            		gbc1.weighty=1.0;
              		gbc1.fill = GridBagConstraints.BOTH;
                         	components.get(0).setPreferredSize(new Dimension(0, 0));
              		// super.setConstraints(components.get(0),gbc1);
              		super.addLayoutComponent(components.get(0),gbc1);
              		components.get(0).revalidate();
              		components.get(0).repaint();
            	
            		// Force components to stretch completely to fill the space
	  		GridBagConstraints gbc2=new GridBagConstraints();
	  		gbc2.fill = GridBagConstraints.BOTH;
	            	gbc2.gridx = xywidthheight2.x;
	            	gbc2.gridy = xywidthheight2.y;
            		gbc2.weightx=mean.denominator;
                        	gbc2.weighty=1.0;
                                	components.get(1).setPreferredSize(new Dimension(0, 0));
            		//super.setConstraints(components.get(1),gbc2);
            		super.addLayoutComponent(components.get(1),gbc2);
            		components.get(1).revalidate();
              		components.get(1).repaint();
            	}
            	else if(components.size() == 3) {
            		XYWidthHeight xywidthheight1=xywidthheights.get(0);
            		XYWidthHeight xywidthheight2=xywidthheights.get(1);
            		
            		Fraction mean=new Fraction();
            		mean.numerator=xywidthheight1.width+xywidthheight2.width;
            		mean.denominator=2; // for first two ratios
            		
            		mean.Flip(); // coz ratio=1/(mean)
            		if(xywidthheight1.width > xywidthheight2.width) { 
            			mean.Flip();
            		}
            		
            		GridBagConstraints gbc1 = new GridBagConstraints();
            		gbc1.gridx=xywidthheight1.x;
            		gbc1.gridy=xywidthheight1.y;
            		gbc1.weightx=mean.numerator;
            		gbc1.weighty=1.0;
              		gbc1.fill = GridBagConstraints.BOTH;
                         	components.get(0).setPreferredSize(new Dimension(0, 0));
              		// super.setConstraints(components.get(0),gbc1);
              		super.addLayoutComponent(components.get(0),gbc1);
              		components.get(0).revalidate();
              		components.get(0).repaint();
            	
            		// Force components to stretch completely to fill the space
	  		GridBagConstraints gbc2=new GridBagConstraints();
	  		gbc2.fill = GridBagConstraints.BOTH;
	            	gbc2.gridx = xywidthheight2.x;
	            	gbc2.gridy = xywidthheight2.y;
            		gbc2.weightx=mean.denominator;
                        	gbc2.weighty=1.0;
                                	components.get(1).setPreferredSize(new Dimension(0, 0));
            		//super.setConstraints(components.get(1),gbc2);
            		super.addLayoutComponent(components.get(1),gbc2);
            		components.get(1).revalidate();
              		components.get(1).repaint();
            		
            		/*1:2:3                                      1:2:1				
	            	2:3:9/2  			2:3:2					
	            	(1:2):(2:3)*(1:2:3)		(1:2):(2:1):(1:2:1)
	            	(2/1)*(3/2)*(2:3)		(2/1)*(1/2)*(2/1)
	            	ratio is 2:ratio is 3:		ratio is 2: ratio is 2			
	            	2*3/2*(1:2:3)			2*1/2*2			
	            	3*(3/2)                                   (2/2)*2
	            	9/2				2
	            	*/
	            	
	            	// Find 9/2 from 1:2:3 for 2:3:4.5
	            	Fraction firstratio= new Fraction();
	            	firstratio.numerator=xywidthheight2.width;
	            	firstratio.denominator=xywidthheight1.width;
	            	
	            	Fraction secondratio=new Fraction();
	            	XYWidthHeight xywidthheight3=xywidthheights.get(2);
	            	secondratio.numerator=xywidthheight3.width;
	            	secondratio.denominator=xywidthheight2.width;
	            	
	            	Fraction thirdratio=new Fraction();
	            	if(xywidthheight1.width == xywidthheight3.width) { // For 1:2:1
	            		if(xywidthheight1.width < xywidthheight2.width) {
	            			thirdratio.numerator=xywidthheight2.width;
	            			thirdratio.denominator=xywidthheight1.width;
            			}
            			else { // xywidthheight1.width > xywidthheight2.width
            				thirdratio.numerator=xywidthheight1.width;
	            			thirdratio.denominator=xywidthheight2.width;
            			}
            		}
	            	else { // For 1:2:3 to get third digit
		            	thirdratio.numerator=xywidthheight2.width;
		            	thirdratio.denominator=xywidthheight1.width;
		            	
		           		double temporary = thirdratio.numerator;
		           		thirdratio.numerator=xywidthheight3.width*xywidthheight1.width;
		           		thirdratio.denominator=temporary;
	           		}
	           		double firstrow=firstratio.numerator*secondratio.numerator*thirdratio.numerator;
	           		double secondrow=firstratio.denominator*secondratio.denominator*thirdratio.denominator;
	           		double thirdnumber=firstrow/secondrow;
	           		
	           		GridBagConstraints gbc3=new GridBagConstraints();
	  		gbc3.fill = GridBagConstraints.BOTH;
	            	gbc3.gridx = xywidthheight3.x;
	            	gbc3.gridy = xywidthheight3.y;
            		gbc3.weightx=thirdnumber;
                        	gbc3.weighty=1.0;
                                	components.get(2).setPreferredSize(new Dimension(0, 0));
            		//super.setConstraints(components.get(2),gbc3);
            		super.addLayoutComponent(components.get(2),gbc3);
            		components.get(2).revalidate();
              		components.get(2).repaint();
           		}
           }
           public void Sort(XYWidthHeight xywidthheight,Component component) {
           		// XYWidthHeight xywidthheight = (XYWidthHeight)object;
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
           @Override
           public String toString() {
           		String debug = "";
           		for(XYWidthHeight xywidthheight:xywidthheights) {
           			debug+=xywidthheight.toString()+"\n";
           		}
           		return debug;
           	}				
}