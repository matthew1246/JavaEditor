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
    	xywidthheight3.width=1;
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
    	// This is for GuaranteedLayout.toString()
    	private List<XYWidthHeight> xywidthheightsX=  new ArrayList<XYWidthHeight>();
    	private List<Component> componentsX=  new ArrayList<Component>();
    	private List<GridBagConstraints> gridbagconstraintsX=new ArrayList<GridBagConstraints>();
    	@Override
    	public void addLayoutComponent(Component panel,Object constraint) {
           		XYWidthHeight xywidthheight=(XYWidthHeight)constraint;
           	
           		/*boolean contains = false;
           		for(XYWidthHeight xywidthheight2:xywidthheightsX) {
           			if((xywidthheight.x==xywidthheight2.x) && (xywidthheight.y == xywidthheight2.y) && 
           			(xywidthheight.width == xywidthheight2.width) && (xywidthheight.height == xywidthheight2.height)) {
           				contains=true;
           			}
           		}
           		if(!contains) {
           			xywidthheights.add(xywidthheight);
           			componentsX.add(panel);
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
            	
            	for(int i = 0; i < componentsX.size(); i++) {
            		super.removeLayoutComponent(componentsX.get(i));
            	}
            	
            	SortX(xywidthheight,panel,new GridBagConstraints());
            	
                  	ArrayList<ArrayList<Component>> componentrows=new ArrayList<ArrayList<Component>>();
            	ArrayList<ArrayList<GridBagConstraints>> rowsgbc = new ArrayList<ArrayList<GridBagConstraints>>();
            	ArrayList<ArrayList<XYWidthHeight>> rows = new ArrayList<ArrayList<XYWidthHeight>>();
            	for(int i = 0; i < (xywidthheightsX.size()-1); i++) {
            		ArrayList<Component> componentrow = new ArrayList<Component>();
            		Component component1=componentsX.get(i);
            		componentrow.add(component1);
            		         
            		ArrayList<GridBagConstraints> rowgbc = new ArrayList<GridBagConstraints>();
            		GridBagConstraints gridbagconstraints1=gridbagconstraintsX.get(i);
            		rowgbc.add(gridbagconstraints1);
            		
            		ArrayList<XYWidthHeight> row = new ArrayList<XYWidthHeight>();
                        	XYWidthHeight xywidthheight1=xywidthheightsX.get(i);
            		row.add(xywidthheight1);
            		for(int j = i+1; j < xywidthheightsX.size(); j++) {
            			GridBagConstraints gridbagconstraints2=gridbagconstraintsX.get(j);
            			Component component2 = componentsX.get(j);
            			
            			XYWidthHeight xywidthheight2=xywidthheightsX.get(j);
            			if(xywidthheight1.y == xywidthheight2.y) {
            				row.add(xywidthheight2);
            				
            				rowgbc.add(gridbagconstraints2);
            				componentrow.add(component2);
            			}
            			else {
            				i=j-1;
            				break;
            			}
            		}
            		rows.add(row);
            		
            		rowsgbc.add(rowgbc);
            		componentrows.add(componentrow);
            	}
            	for(int i = 0; i < rows.size(); i++) {
            		List<XYWidthHeight> xywidthheightsX1=rows.get(i);

            		List<GridBagConstraints> gridbagconstraintsX1=rowsgbc.get(i);
	            	if(xywidthheightsX1.size() == 1) {
	            		GridBagConstraints gbc = gridbagconstraintsX1.get(0);
	            		gbc.gridx=xywidthheight.x;
	            		gbc.gridy=xywidthheight.y;
	            		gbc.weightx=xywidthheight.width;
	            		// gbc.weighty=xywidthheight.height;
	            		gbc.fill=GridBagConstraints.BOTH;	
	                       	panel.setPreferredSize(new Dimension(0, 0));
	                       	super.setConstraints(panel,gbc);
	                  		// gridbagconstraintsX.add(gbc);
	                       	panel.revalidate();
	                       	panel.repaint();
	            	}
	            	else if (xywidthheightsX1.size() == 2) {
	            		XYWidthHeight xywidthheight1=xywidthheightsX1.get(0);
	            		XYWidthHeight xywidthheight2=xywidthheightsX1.get(1);
	            		
	            		Fraction mean=getMean(xywidthheight1.width,xywidthheight2.width);
	            		Fraction mean2=getMean(xywidthheight1.height,xywidthheight2.height);
	            		
	            		GridBagConstraints gbc1 = gridbagconstraintsX1.get(0);
	            		gbc1.gridx=xywidthheight1.x;
	            		gbc1.gridy=xywidthheight1.y;
	            		gbc1.weightx=mean.numerator;
	            		gbc1.weighty=mean2.numerator;
	              		gbc1.fill = GridBagConstraints.BOTH;
	                         	componentsX.get(0).setPreferredSize(new Dimension(0, 0));
	              		// super.setConstraints(componentsX.get(0),gbc1);
	              		super.addLayoutComponent(componentsX.get(0),gbc1);
	                                	// gridbagconstraintsX.add(gbc1);
	              		componentsX.get(0).revalidate();
	              		componentsX.get(0).repaint();
	            	
	            		// Force componentsX to stretch completely to fill the space
		  		GridBagConstraints gbc2=gridbagconstraintsX1.get(1);
		  		gbc2.fill = GridBagConstraints.BOTH;
		            	gbc2.gridx = xywidthheight2.x;
		            	gbc2.gridy = xywidthheight2.y;
	            		gbc2.weightx=mean.denominator;
	                        	gbc2.weighty=mean2.denominator;
	                                	componentsX.get(1).setPreferredSize(new Dimension(0, 0));
	            		//super.setConstraints(componentsX.get(1),gbc2);
	            		super.addLayoutComponent(componentsX.get(1),gbc2);
	                              	// gridbagconstraintsX.add(gbc2);
	            		componentsX.get(1).revalidate();
	              		componentsX.get(1).repaint();
	            	}
	            	else if(componentsX.size() == 3) {
	            		XYWidthHeight xywidthheight1=xywidthheightsX1.get(0);
	            		XYWidthHeight xywidthheight2=xywidthheightsX1.get(1);
	            		
	            		Fraction mean=getMean(xywidthheight1.width,xywidthheight2.width);
	            		Fraction mean2=getMean(xywidthheight1.height,xywidthheight2.height);
	            		
	            		GridBagConstraints gbc1 = gridbagconstraintsX1.get(0);
	            		gbc1.gridx=xywidthheight1.x;
	            		gbc1.gridy=xywidthheight1.y;
	            		gbc1.weightx=mean.numerator;
	            		gbc1.weighty=mean2.numerator;
	              		gbc1.fill = GridBagConstraints.BOTH;
	                         	componentsX.get(0).setPreferredSize(new Dimension(0, 0));
	              		// super.setConstraints(componentsX.get(0),gbc1);
	              		super.addLayoutComponent(componentsX.get(0),gbc1);
	                                	// gridbagconstraintsX.add(gbc1);
	              		componentsX.get(0).revalidate();
	              		componentsX.get(0).repaint();
	            	
	            		// Force componentsX to stretch completely to fill the space
		  		GridBagConstraints gbc2=gridbagconstraintsX1.get(1);
		  		gbc2.fill = GridBagConstraints.BOTH;
		            	gbc2.gridx = xywidthheight2.x;
		            	gbc2.gridy = xywidthheight2.y;
	            		gbc2.weightx=mean.denominator;
	                        	gbc2.weighty=mean2.denominator;
	                                	componentsX.get(1).setPreferredSize(new Dimension(0, 0));
	            		//super.setConstraints(componentsX.get(1),gbc2);
	            		super.addLayoutComponent(componentsX.get(1),gbc2);
	                              	// gridbagconstraintsX.add(gbc2);
	            		componentsX.get(1).revalidate();
	              		componentsX.get(1).repaint();
	            		
	            		/*1:2:3                                      1:2:1				
		            	2:3:9/2  			2:3:2					
		            	(1:2):(2:3)*(1:2:3)		(1:2):(2:1):(1:2:1)
		            	(2/1)*(3/2)*(2:3)		(2/1)*(1/2)*(2/1)
		            	ratio is 2:ratio is 3:		ratio is 2: ratio is 2			
		            	2*3/2*(1:2:3)			2*1/2*2			
		            	3*(3/2)                                   (2/2)*2
		            	9/2				2
		            	*/
		            	
		            	XYWidthHeight xywidthheight3=xywidthheightsX1.get(2);
		            	// Find 9/2 from 1:2:3 for 2:3:4.5
		            	double thirdnumber=getThirdNumber(xywidthheight1.width,xywidthheight2.width,xywidthheight3.width);
		           		double thirdnumber2=getThirdNumber(xywidthheight1.height,xywidthheight2.height,xywidthheight3.height);
		           		
		           		GridBagConstraints gbc3=gridbagconstraintsX1.get(2);
		  		gbc3.fill = GridBagConstraints.BOTH;
		            	gbc3.gridx = xywidthheight3.x;
		            	gbc3.gridy = xywidthheight3.y;
	            		gbc3.weightx=thirdnumber;
	                        	gbc3.weighty=thirdnumber2;
	                                	componentsX.get(2).setPreferredSize(new Dimension(0, 0));
	            		//super.setConstraints(componentsX.get(2),gbc3);
	            		super.addLayoutComponent(componentsX.get(2),gbc3);
	                              	// gridbagconstraintsX.add(gbc3);
	            		componentsX.get(2).revalidate();
	              		componentsX.get(2).repaint();
	           		}
           		}
           }
           public void SortX(XYWidthHeight xywidthheight,Component component,GridBagConstraints gbc) {
           		// XYWidthHeight xywidthheight = (XYWidthHeight)object;
		if(xywidthheightsX.size() > 0) { // normal
			/*if(dimension.width < minimumWidth) {
				minimumWidth = dimension.width;
				minimumHeight = dimension.height;
			}*/
			for(int i = xywidthheightsX.size()-1; i >= 0; i--) {
				XYWidthHeight xywidthheight2 = xywidthheightsX.get(i);
				if(xywidthheight.y > xywidthheight2.y) {
					componentsX.add(i+1,component);
					xywidthheightsX.add(i+1,xywidthheight);
					gridbagconstraintsX.add(i+1,gbc);			
					break;
				}
				else if(xywidthheight.y == xywidthheight2.y) {
					if(xywidthheight.x > xywidthheight2.x) {
						componentsX.add(i+1,component);
						xywidthheightsX.add(i+1,xywidthheight);
						gridbagconstraintsX.add(i+1,gbc);
						break;
					}
					else if(i == 0) {
						componentsX.add(0,component);
						xywidthheightsX.add(0,xywidthheight);
						gridbagconstraintsX.add(0,gbc);
					}
				}
				else if(i == 0) {
					componentsX.add(0,component);
					xywidthheightsX.add(0,xywidthheight);
					gridbagconstraintsX.add(0,gbc);
				}
			}
		}
		else {
			/*minimumWidth = dimension.width;
			minimumHeight = dimension.height;*/
			componentsX.add(component);
			xywidthheightsX.add(xywidthheight);
			gridbagconstraintsX.add(gbc);
		}		
           }
           public Fraction getMean(double x,double x2) {
           		Fraction mean=new Fraction();
            	mean.numerator=x+x2;
            	mean.denominator=2;
            	
            	mean.Flip(); // coz ratio=1/(mean)
            	if(x > x2) { 
            		mean.Flip();
            	}
            	return mean;
            }
            public double getThirdNumber(double width1,double width2,double width3) {
            	Fraction firstratio= new Fraction();
            	firstratio.numerator=width2;
            	firstratio.denominator=width1;
            	
            	Fraction secondratio=new Fraction();
            	secondratio.numerator=width3;
            	secondratio.denominator=width2;
            	
            	double thirdnumber = 0;
            	Fraction thirdratio=new Fraction();
            	if(width1 == width3) { // For 1:2:1
            		Fraction mean=getMean(width1,width2);	
            		thirdnumber=mean.numerator;
            	}
            	else { // For 1:2:3 to get third digit
	            	thirdratio.numerator=width2;
	            	thirdratio.denominator=width1;
	            	
	           		double temporary = thirdratio.numerator;
	           		thirdratio.numerator=width3*width1;
	           		thirdratio.denominator=temporary;
	           		
	           		double firstrow=firstratio.numerator*secondratio.numerator*thirdratio.numerator;
	           		double secondrow=firstratio.denominator*secondratio.denominator*thirdratio.denominator;
	           		thirdnumber=firstrow/secondrow;
           		}
                      	return thirdnumber;
           	}
           	public void testGetThirdNumber() {
           		// Assert.assertEquals(2.0,getThirdNumber(1,2,1),0.1);
           	}
           @Override
            public String toString() {
            	String debug = "";
            	for(int i = 0; i < gridbagconstraintsX.size(); i++) {
            		GridBagConstraints gbc = gridbagconstraintsX.get(i);
            		/*debug += "Component: " + componentsX.get(i).getClass().getSimpleName()
            			+ " gridx=" + gbc.gridx + " gridy=" + gbc.gridy
            			+ " weightx=" + gbc.weightx + " weighty=" + gbc.weighty
            			+ " fill=" + gbc.fill + "\n";
            		*/
            		debug+=" gridx=" + gbc.gridx + " gridy=" + gbc.gridy
            			+ " weightx=" + gbc.weightx + " weighty=" + gbc.weighty
            			+ " fill=" + gbc.fill + "\n";
            	}
            	return debug;
           	}				
}