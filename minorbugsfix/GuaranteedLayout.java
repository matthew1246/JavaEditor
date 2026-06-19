import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
		JPanel panel = new JPanel();
		GuaranteedLayout matthewLayout = new GuaranteedLayout(); // new MatthewLayout(true); // or MatthewLayout matthewLayout = new MatthewLayout(60,20);
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
    	
    	System.out.println(matthewLayout);
    }		
    public Contains contains = new Contains();
    public GuaranteedLayout() {
    	super();
    }
    private ArrayList<ArrayList<XYWidthHeight>> rowsextra;
    private ArrayList<ArrayList<GridBagConstraints>> rowsgbcextra;
    private ArrayList<ArrayList<Component>> rowscomponentextra;
    private ArrayList<ArrayList<XYWidthHeight>> columnsextra;
    private ArrayList<ArrayList<GridBagConstraints>> columnsgbcextra;
    private ArrayList<ArrayList<Component>> columnscomponentextra;
    
        	private List<XYWidthHeight> xywidthheightsY=  new ArrayList<XYWidthHeight>();
        	private List<Component> componentsY=  new ArrayList<Component>();
        	private List<GridBagConstraints> gridbagconstraintsY=new ArrayList<GridBagConstraints>();
        	
    	// This is for GuaranteedLayout.toString()
    	private List<XYWidthHeight> xywidthheightsX=  new ArrayList<XYWidthHeight>();
    	private List<Component> componentsX=  new ArrayList<Component>();
    	private List<GridBagConstraints> gridbagconstraintsX=new ArrayList<GridBagConstraints>();
    	@Override
    	public void addLayoutComponent(Component panel,Object constraint) {
           		XYWidthHeight xywidthheight=(XYWidthHeight)constraint;
           		
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
            	
          /*  	for(int i = 0; i < componentsX.size(); i++) {
            		super.removeLayoutComponent(componentsX.get(i));
            	}
            	*/

            	GridBagConstraints newgbc = new GridBagConstraints();
            	SortX(xywidthheight,panel,newgbc);
            	
                  	ArrayList<ArrayList<Component>> componentrows=new ArrayList<ArrayList<Component>>();
            	ArrayList<ArrayList<GridBagConstraints>> rowsgbc = new ArrayList<ArrayList<GridBagConstraints>>();
            	ArrayList<ArrayList<XYWidthHeight>> rows = new ArrayList<ArrayList<XYWidthHeight>>();
            	rowsextra=rows;
            	rowsgbcextra=rowsgbc;
            	rowscomponentextra=componentrows;
            	for(int i = 0; i < xywidthheightsX.size(); i++) {
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
            			i=j;
            		}
            		rows.add(row);
            		
            		rowsgbc.add(rowgbc);
            		componentrows.add(componentrow);
            	}
            	for(int i = 0; i < rows.size(); i++) {
            		List<XYWidthHeight> xywidthheightsX1=rows.get(i);

            		List<GridBagConstraints> gridbagconstraintsX1=rowsgbc.get(i);
            		List<Component> componentsX1=componentrows.get(i);
	            	if(xywidthheightsX1.size() == 1) {
	            		GridBagConstraints gbc = gridbagconstraintsX1.get(0);
	            		gbc.gridx=xywidthheightsX1.get(0).x;
	            		gbc.gridy=xywidthheightsX1.get(0).y;
	            		gbc.weightx=xywidthheightsX1.get(0).width;
	            		gbc.weighty=xywidthheightsX1.get(0).height;
	            		gbc.fill=GridBagConstraints.BOTH;	
	                       	panel.setPreferredSize(new Dimension(0, 0));
	                       	// super.setConstraints(panel,gbc);
	                       	// contains.addLayoutComponent(panel,gbc);
	                  		// gridbagconstraintsX.add(gbc);
	            	}
	            	else if (xywidthheightsX1.size() == 2) {
	            		Weight weight = new Weightx();
	            		addTwo(xywidthheightsX1,gridbagconstraintsX1,componentsX1,weight);
	            	}
	            	else if(xywidthheightsX1.size() == 3) {
	            		Weight weight = new Weightx();
	            		System.out.println("Before calculate and Add weightx: ");
	            		for(int k = 0; k < xywidthheightsX1.size(); k++) {
	            			System.out.println(xywidthheightsX1.get(k));
            			}
            			System.out.println();
	            		
	            		addThree(xywidthheightsX1,gridbagconstraintsX1,componentsX1,weight);
	           		}
           		}
           		
           		SortY(xywidthheight,panel,newgbc);
           		System.out.println("SortY:");
           		for(int i = 0; i < xywidthheightsY.size(); i++) {
           			System.out.println(xywidthheightsY.get(i));
           		}
           		System.out.println();
           		
                  	ArrayList<ArrayList<Component>> componentcolumns=new ArrayList<ArrayList<Component>>();
            	ArrayList<ArrayList<GridBagConstraints>> columnsgbc = new ArrayList<ArrayList<GridBagConstraints>>();
            	ArrayList<ArrayList<XYWidthHeight>> columns=  new ArrayList<ArrayList<XYWidthHeight>>();
            	columnsextra=columns;
            	columnsgbcextra=columnsgbc;
            	columnscomponentextra=componentcolumns;
            	for(int i = 0; i < xywidthheightsY.size(); i++) {
            		ArrayList<Component> componentcolumn= new ArrayList<Component>();
            		Component component1=componentsY.get(i);
            		componentcolumn.add(component1);
            		         
            		ArrayList<GridBagConstraints> columngbc =new ArrayList<GridBagConstraints>();
            		GridBagConstraints gridbagconstraints1=gridbagconstraintsY.get(i);
            		columngbc.add(gridbagconstraints1);
            		
            		ArrayList<XYWidthHeight> column=new ArrayList<XYWidthHeight>();
                        	XYWidthHeight xywidthheight1=xywidthheightsY.get(i);
            		column.add(xywidthheight1);
            		for(int j = i+1; j < xywidthheightsY.size(); j++) {
            			GridBagConstraints gridbagconstraints2=gridbagconstraintsY.get(j);
            			Component component2 = componentsY.get(j);
            			
            			XYWidthHeight xywidthheight2=xywidthheightsY.get(j);
            			if(xywidthheight1.x == xywidthheight2.x) {
            				column.add(xywidthheight2);
            				
            				columngbc.add(gridbagconstraints2);
            				componentcolumn.add(component2);
            			}
            			else {
            				i=j-1;
            				break;
            			}
            			i=j;
            		}
            		columns.add(column);
            		
            		columnsgbc.add(columngbc);
            		componentcolumns.add(componentcolumn);
            	}
            	for(int i = 0; i < columns.size(); i++) {
            		List<XYWidthHeight> xywidthheightsY1=columns.get(i);

            		List<GridBagConstraints> gridbagconstraintsY1=columnsgbc.get(i);
            		List<Component> componentsY1=componentcolumns.get(i);
	            	if(xywidthheightsY1.size() == 1) {
	            		GridBagConstraints gbc = gridbagconstraintsY1.get(0);
	            		gbc.gridx=xywidthheightsY1.get(0).x;
	            		gbc.gridy=xywidthheightsY1.get(0).y;
	            		gbc.weighty=xywidthheightsY1.get(0).height;
                        		// gbc.weighty=1.0;
	            		gbc.fill=GridBagConstraints.BOTH;	
	                       	panel.setPreferredSize(new Dimension(0, 0));
	                       	//super.setConstraints(panel,gbc);
	                  		// gridbagconstraintsX.add(gbc);
	            	}
	            	else if (xywidthheightsY1.size() == 2) {
	            		Weight weight = new Weighty();
	            		addTwo(xywidthheightsY1,gridbagconstraintsY1,componentsY1,weight);
	            	}
	            	else if(xywidthheightsY1.size() == 3) {
	            		Weight weight = new Weighty();
	            		addThree(xywidthheightsY1,gridbagconstraintsY1,componentsY1,weight);
	           		}
	           		else if(xywidthheightsY1.size() == 4) {
	           			for(int j = 0; j < 4; j++) {
	           				GridBagConstraints gbc = gridbagconstraintsY1.get(j);
	            			gbc.gridx=xywidthheightsY1.get(j).x;
	            			gbc.gridy=xywidthheightsY1.get(j).y;
	            			gbc.weighty=xywidthheightsY1.get(j).height;
                        			// gbc.weighty=1.0;
	            			gbc.fill=GridBagConstraints.BOTH;	
	                       		panel.setPreferredSize(new Dimension(0, 0));
                       		}
                       	}
           		}
           		
                       for(int i = 0; i < componentrows.size(); i++) {
            		List<GridBagConstraints> gridbagconstraintsX1=rowsgbc.get(i);
            		List<Component> componentsX1=componentrows.get(i);
           		
           			for(int j = 0; j < componentsX1.size(); j++) {
		       		if(gridbagconstraintsX1.get(j).weighty == 6.0)
		       			gridbagconstraintsX1.get(j).gridheight=4;	
		       		if(gridbagconstraintsX1.get(j).weighty == 2.0)
		       			gridbagconstraintsX1.get(j).gridwidth=2;		contains.addLayoutComponent(componentsX1.get(j),gridbagconstraintsX1.get(j));
	                                	// gridbagconstraintsX.add(gbc1);
		              	componentsX1.get(j).revalidate();
		              	componentsX1.get(j).repaint();
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
            	if(x != x2) {
	           		mean.numerator=x+x2;
	            	mean.denominator=2;
            	}
            	else { // x == 2
            		mean.numerator=x;
            		mean.denominator=x2;
            	}
            	
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
           	public void addTwo(List<XYWidthHeight> xywidthheightsY1,List<GridBagConstraints> gridbagconstraintsY1,List<Component> componentsY1,Weight weight) {
           		XYWidthHeight xywidthheight1=xywidthheightsY1.get(0);
            	XYWidthHeight xywidthheight2=xywidthheightsY1.get(1);
            	
            	//Fraction mean=getMean(xywidthheight1.width,xywidthheight2.width);
            	//Fraction mean2=getMean(xywidthheight1.height,xywidthheight2.height);
                        Fraction mean=getMean(weight.getWidthOrHeight(xywidthheight1),weight.getWidthOrHeight(xywidthheight2));
            	
            	GridBagConstraints gbc1 = gridbagconstraintsY1.get(0);
            	gbc1.gridx=xywidthheight1.x;
            	gbc1.gridy=xywidthheight1.y;
            	
            	//gbc1.weightx=mean.numerator;
            	//gbc1.weighty=mean2.numerator;
                       //gbc1.weighty=1.0;
            	weight.setWeightxorWeighty(gbc1,mean.numerator);
              	gbc1.fill = GridBagConstraints.BOTH;
                       componentsY1.get(0).setPreferredSize(new Dimension(0, 0));
              	// super.setConstraints(componentsY1.get(0),gbc1);
            
            	// Force componentsX to stretch completely to fill the space
  		GridBagConstraints gbc2=gridbagconstraintsY1.get(1);
  		gbc2.fill = GridBagConstraints.BOTH;
            	gbc2.gridx = xywidthheight2.x;
            	gbc2.gridy = xywidthheight2.y;
      		weight.setWeightxorWeighty(gbc2,mean.denominator);
            	//gbc2.weightx=mean.denominator;
                     	//gbc2.weighty=mean2.denominator;
                   	//gbc2.weighty=1.0;
                  	componentsY1.get(1).setPreferredSize(new Dimension(0, 0));
            	//super.setConstraints(componentsY1.get(1),gbc2);
            	//super.addLayoutComponent(componentsY1.get(1),gbc2);
                     	// contains.addLayoutComponent(componentsY1.get(1),gbc2);
                     	// gridbagconstraintsY1.add(gbc2);
           }
           public void addThree(List<XYWidthHeight> xywidthheightsY1,List<GridBagConstraints> gridbagconstraintsY1,List<Component> componentsY1,Weight weight) {
           		XYWidthHeight xywidthheight1=xywidthheightsY1.get(0);
            	XYWidthHeight xywidthheight2=xywidthheightsY1.get(1);
            	
            	Fraction mean=getMean(weight.getWidthOrHeight(xywidthheight1),weight.getWidthOrHeight(xywidthheight2));
            	
            	GridBagConstraints gbc1 = gridbagconstraintsY1.get(0);
            	gbc1.gridx=xywidthheight1.x;
            	gbc1.gridy=xywidthheight1.y;
            	weight.setWeightxorWeighty(gbc1,mean.numerator);
            	System.out.println("addThree: gbc1.gridx="+gbc1.gridx + " gridy=" + gbc1.gridy
            			+ " weightx=" + gbc1.weightx + " weighty=" + gbc1.weighty
            			+ " fill=" + gbc1.fill + " mean.numerator: "+mean.numerator+"\n");
            	//gbc1.weighty=mean2.numerator;
                     	// gbc1.weighty=1.0;
              	gbc1.fill = GridBagConstraints.BOTH;
                	componentsY1.get(0).setPreferredSize(new Dimension(0, 0));
            
            	// Force componentsX to stretch completely to fill the space
  		GridBagConstraints gbc2=gridbagconstraintsY1.get(1);
  		gbc2.fill = GridBagConstraints.BOTH;
            	gbc2.gridx = xywidthheight2.x;
            	gbc2.gridy = xywidthheight2.y;
            	weight.setWeightxorWeighty(gbc2,mean.denominator);
            	System.out.println("addThree: gbc2.gridx="+gbc2.gridx + " gridy=" + gbc2.gridy
            			+ " weightx=" + gbc2.weightx + " weighty=" + gbc2.weighty
            			+ " fill=" + gbc2.fill + " mean.denominator: "+mean.denominator+"\n");
                  	//gbc2.weighty=mean2.denominator;
                   	// gbc2.weighty=1.0;
                   	componentsY1.get(1).setPreferredSize(new Dimension(0, 0));
            	
            	/*1:2:3                                      1:2:1				
            	2:3:9/2  			2:3:2					
            	(1:2):(2:3)*(1:2:3)		(1:2):(2:1):(1:2:1)
            	(2/1)*(3/2)*(2:3)		(2/1)*(1/2)*(2/1)
            	ratio is 2:ratio is 3:		ratio is 2: ratio is 2			
            	2*3/2*(1:2:3)			2*1/2*2			
            	3*(3/2)                                   (2/2)*2
            	9/2				2
            	*/
            	
            	XYWidthHeight xywidthheight3=xywidthheightsY1.get(2);
            	// Find 9/2 from 1:2:3 for 2:3:4.5
            	double thirdnumber=getThirdNumber(weight.getWidthOrHeight(xywidthheight1),weight.getWidthOrHeight(xywidthheight2),
            		weight.getWidthOrHeight(xywidthheight3));
           		
           		GridBagConstraints gbc3=gridbagconstraintsY1.get(2);
  		gbc3.fill = GridBagConstraints.BOTH;
            	gbc3.gridx = xywidthheight3.x;
            	gbc3.gridy = xywidthheight3.y;
            	//gbc3.weightx=thirdnumber;
            	weight.setWeightxorWeighty(gbc3,thirdnumber);
            	System.out.println("addThree: gbc3.gridx="+gbc3.gridx + " gridy=" + gbc3.gridy
            			+ " weightx=" + gbc3.weightx + " weighty=" + gbc3.weighty
            			+ " fill=" + gbc3.fill + " thirdnumber: "+thirdnumber+"\n");
            	//gbc3.weighty=thirdnumber2;
                	// gbc3.weighty=1.0;
               	componentsY1.get(2).setPreferredSize(new Dimension(0, 0));
           }
           	public interface Weight {
           		public void setWeightxorWeighty(GridBagConstraints gbc,double weightxorweighty);
           		public double getWidthOrHeight(XYWidthHeight xywidthheight);
           	}
           	public class Weightx implements Weight {
           		public void setWeightxorWeighty(GridBagConstraints gbc,double weightx) {
           			gbc.weightx = weightx;
           		}
           		public double getWidthOrHeight(XYWidthHeight xywidthheight) {
           			return xywidthheight.width;
           		}
           	}												
           	public class Weighty implements Weight {
           		public void setWeightxorWeighty(GridBagConstraints gbc,double weighty) {
           			gbc.weighty = weighty;
           		}
           		public double getWidthOrHeight(XYWidthHeight xywidthheight) {
           			return xywidthheight.height;
           		}	
           	}	
           	public void SortY(XYWidthHeight xywidthheight,Component component,GridBagConstraints gbc) {
           		// XYWidthHeight xywidthheight = (XYWidthHeight)object;
		if(xywidthheightsY.size() > 0) { // normal
			/*if(dimension.width < minimumWidth) {
				minimumWidth = dimension.width;
				minimumHeight = dimension.height;
			}*/
			for(int i = xywidthheightsY.size()-1; i >= 0; i--) {
				XYWidthHeight xywidthheight2 = xywidthheightsY.get(i);
				if(xywidthheight.x > xywidthheight2.x) {
					componentsY.add(i+1,component);
					xywidthheightsY.add(i+1,xywidthheight);
					gridbagconstraintsY.add(i+1,gbc);			
					break;
				}
				else if(xywidthheight.x == xywidthheight2.x) {
					if(xywidthheight.y > xywidthheight2.y) {
						componentsY.add(i+1,component);
						xywidthheightsY.add(i+1,xywidthheight);
						gridbagconstraintsY.add(i+1,gbc);
						break;
					}
					else if(i == 0) {
						componentsY.add(0,component);
						xywidthheightsY.add(0,xywidthheight);
						gridbagconstraintsY.add(0,gbc);
					}
				}
				else if(i == 0) {
					componentsY.add(0,component);
					xywidthheightsY.add(0,xywidthheight);
					gridbagconstraintsY.add(0,gbc);
				}
			}
		}
		else {
			/*minimumWidth = dimension.width;
			minimumHeight = dimension.height;*/
			componentsY.add(component);
			xywidthheightsY.add(xywidthheight);
			gridbagconstraintsY.add(gbc);
		}		
           }	
           public class Contains {	
           		public List<Component> components=new ArrayList<Component>();
           		public void addLayoutComponent(Component component,GridBagConstraints gbc) {
           			System.out.println("Contains: gridx=" + gbc.gridx + " gridy=" + gbc.gridy
            			+ " weightx=" + gbc.weightx + " weighty=" + gbc.weighty
            			+ " fill=" + gbc.fill + "\n");
           			if(!Contains(component)) {
           				GuaranteedLayout.super.addLayoutComponent(component,gbc);
           				components.add(component);
           			}
           			else {
           				GuaranteedLayout.super.setConstraints(component,gbc);
           			}
           		}
           		public boolean Contains(Component component) {
           			for(Component component2:components) { 	           			
           				if (component.equals(component2)) {
           					return true;
           				}
           			}
           			return false;
           		}
           }
           @Override
           public String toString() {
           		String debug = "";
           		for(int i = 0; i < contains.components.size(); i++) {
     			GridBagConstraints gbc=(GridBagConstraints)super.getConstraints(contains.components.get(i));
     			debug+="final gbc: gbc.gridx="+gbc.gridx + " gridy=" + gbc.gridy
            			+" gridwidth="+gbc.gridwidth+ " weightx=" + gbc.weightx +" gridheight="+gbc.gridheight+ " weighty=" + gbc.weighty
            		+ " fill=" + gbc.fill + "\n";			   	 	 	 		}
            	return debug;
           	}						
}