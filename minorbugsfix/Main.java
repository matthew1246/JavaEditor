import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.reflect.Method;
import java.awt.Rectangle;
import javax.swing.event.ChangeEvent;
import java.awt.Point;
import javax.swing.text.BadLocationException;
import javax.swing.tree.TreePath;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.text.BadLocationException;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.event.ItemEvent;
import java.util.regex.*;
import javax.swing.Box;
import javax.swing.event.CaretListener;
import java.nio.CharBuffer;
import javax.swing.event.CaretEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.awt.FlowLayout;	
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import javax.swing.SwingConstants;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.lang.model.SourceVersion;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
public class Main {
	public JMenuItem opennewtab = new JMenuItem("Open New Tab");
	public JTabbedPane tabbedpane = new JTabbedPane();
	public JPanel pluspanel = new JPanel();
	public JMenuItem generatejar;
	public JButton deprecated;	
	public static Muck muck = new Muck();
	public Expandable expandable;
	public JComboBox<String> filenamescombobox = new JComboBox<String>();	
	public JComboBox<String> classnamescombobox = new JComboBox<String>();
	public JComboBox<String> combobox;
	public JComboBox<String> startupcombobox = new JComboBox<String>();
	public JCheckBox lock = new JCheckBox();
	public GetClassName getclassname;
	public GetClassMethods getclassmethods;
	JMenu edit = new JMenu("Edit");
	JMenuItem control_f = new JMenuItem("Find");
	public JTextField line_number = new JTextField();
	public JButton go_to_line_number = new JButton("Go to line");
	public JLabel line = new JLabel("line number: 1");
	public JMenuItem newopenwindow = new JMenuItem("New Open Window");
	public JMenuItem exitItem = new JMenuItem("Exit");
	public JMenuItem fontmenuitem = new JMenuItem("Font");
	public JMenuItem saveItem = new JMenuItem("Save");
	public JMenuItem saveasitem = new JMenuItem("Save As");
	public JMenuItem newitem = new JMenuItem("New");
	public JMenuItem tabSizeMenuItem = new JMenuItem("Tab");
	
	public JMenuItem opennewwindow = new JMenuItem("Open New Window");
	public SaveActionListener sal = new SaveActionListener(this);
	public JButton compile = new JButton("compile");
	public JButton compile_all = new JButton("compile all");		
	public JButton run = new JButton("run");
	public JCheckBox checkbox = new JCheckBox();
	public JLabel jarlabel = new JLabel("JUnit");
	public JCheckBox jarcheckbox = new JCheckBox();
	public JButton reload;
	public JButton addjar=new JButton("jar");
	public OpenActionListener oal = new OpenActionListener(this);	
	public JFrame frame;
	public JTextArea textarea;
	public String fileName = "";
	public static String value = System.getProperty("user.home")+"\\load_program.ser";
	//public String value="load_program.ser";
	public static void main(String[] args) 	{ 
		Main main = new Main(new OpenDefaultContent());
	}
	/*
	** If have no default content for window
	
	*/
	public Main() {
		setLayout();
		setListeners();
		expandable = new Expandable(this);
	}
	public FileListModifier filelistmodifier = new FileListModifier();
	public Git git;
	/*
	** If have default content for window
	*/
	public Main(OpenDefaultContent odc) {
		fileName = odc.getFileName();
		if(fileName != null && !fileName.equals("")) {
			git = new Git(fileName);
		}
		setLayout();
		expandable = new Expandable(this);	
		if(!fileName.equals("")) {
			String lines = odc.getString();
			open(getFileName(fileName));
		}		
		setListeners();	
	}
	public String getFileName(String directoryandfilename) {
		return directoryandfilename.replaceAll(".+\\\\","");
	}
	
	public void setNoMargin(JComboBox button) {
		Border border = button.getBorder();
		Border margin = new EmptyBorder(0,0,0,0);
		button.setBorder(new CompoundBorder(border, margin));
	}
	
	
	public void setNoMargin(JButton button) {
		Insets insets2=button.getMargin();
		insets2.left = 0;
		insets2.right=0;
		button.setMargin(insets2);	
	}
	
	public void setNoMargin(JLabel label) {
		Border border = label.getBorder();
		Border margin = new EmptyBorder(0,0,0,0);
		label.setBorder(new CompoundBorder(border, margin));
	}
	public void setNoMargin(JTextField textfield) {
		Border newborder =  new EmptyBorder(0,0,0,0);
		textfield.setBorder(newborder);
	}
	public void setSize(JComponent component) {
		component.setMinimumSize(component.getPreferredSize());
	}
	public List<String> fileNames = new LinkedList<String>();
	public JScrollPane scrollpane;
	public void setLayout() {
		JMenuBar menubar = new InnerGridBagLayout();


		frame = new JFrame();
		
		/*JFrame frame2 = new JFrame();
		JButton button = new JButton("Output");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				JOptionPane.showMessageDialog(null,tabSizeMenuItem.getSize().getWidth()+" "+run.getSize().getWidth());
			}
		});
		frame2.add(button);
		frame2.pack();
		frame2.setVisible(true);*/
		
		if(fileName != null && !fileName.equals("")) {
			frame.setTitle(fileName.replaceAll(".+\\\\",""));
		}
		
		else {
			frame.setTitle("");
		}
		frame.setSize(800,600);
		
		textarea = new JTextArea();
		combobox = new JComboBox<String>();
		
		Font originalFont = textarea.getFont();
		textarea.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));
		scrollpane = new JScrollPane(textarea);
		tabbedpane.add(getFileName(fileName),scrollpane);

		tabbedpane.addTab("+",pluspanel);
		fileNames.add(fileName);
				
		frame.getContentPane().add(tabbedpane);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JMenuBar menubar = new JMenuBar();
		//menubar.setPreferredSize(new Dimension(0,50));
		
		/*frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent ev) {
				Dimension dimension = ev.getComponent().getSize();
				double height = dimension.getHeight();
				double y = height/12.0;
				//menubar.setSize(new Dimension(0,50));
				menubar.setPreferredSize(new Dimension((int)dimension.getWidth(),(int)y));
				menubar.revalidate();
			}
		});*/
		
		//MatthewLayout matthewlayout = new MatthewLayout(true);
		
		/*menubar.invalidate();
		menubar.repaint();
		*/
		
		menubar.setLayout(new GridBagLayout());
		
		JMenu menu = new JMenu("File");		
		JMenuItem menuitem = new JMenuItem("Open");
		generatejar = new JMenuItem("Make Jar");			
		opennewwindow = new JMenuItem("Open New Window");
		KeyStroke keystroke = KeyStroke.getKeyStroke("control O");
		menuitem.setAccelerator(keystroke);
		KeyStroke tab_keystroke =KeyStroke.getKeyStroke(KeyEvent.VK_TAB,0);
		textarea.getInputMap().put(tab_keystroke,"none");
		
		control_f.setAccelerator(KeyStroke.getKeyStroke("control F"));

		KeyStroke save_Key_Stroke = KeyStroke.getKeyStroke("control S");
		saveItem.setAccelerator(save_Key_Stroke);
		
		KeyStroke control_t=KeyStroke.getKeyStroke("control T");
		opennewtab.setAccelerator(control_t);
		
		menuitem.addActionListener(oal);
		menu.add(newitem);
		menu.add(newopenwindow);
		menu.add(menuitem);
		menu.add(opennewwindow);
		menu.add(opennewtab);
		menu.add(saveItem);
		menu.add(saveasitem);
		menu.add(generatejar);
		menu.add(exitItem);
		edit.add(control_f);
		edit.add(fontmenuitem);
		edit.add(tabSizeMenuItem);
		
		
		
		menubar.invalidate();
		menubar.repaint();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.insets =new Insets(1,0,0,0);
		menubar.add(menu,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=1;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(edit,gbc);

		menubar.validate();
		menubar.repaint();

		line.setText("line number:");
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=3.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=3;
		gbc.gridheight=1;
		menubar.add(line,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=5;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=6.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=6;
		gbc.gridheight=1;
		menubar.add(line_number,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=11;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=3.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=3;
		gbc.gridheight=1;
		menubar.add(go_to_line_number,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=14;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=2.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		menubar.add(compile,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=16;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=3.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=3;
		gbc.gridheight=1;
		menubar.add(compile_all,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=19;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(run,gbc);

		menubar.validate();
		menubar.repaint();

		JLabel custom2 = new JLabel();
		custom2.setText("classpath:");
		custom2.setHorizontalAlignment(SwingConstants.RIGHT);		
		gbc.gridx=20;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=2.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		menubar.add(custom2,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=22;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(checkbox,gbc);

		menubar.validate();
		menubar.repaint();

		JLabel jarlabel = new JLabel();
		jarlabel.setText("JUnit");
		gbc.gridx=23;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(jarlabel,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=24;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(jarcheckbox,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=25;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(addjar,gbc);

		menubar.validate();
		menubar.repaint();

		// gbc = new GridBagConstraints(); // reset insets object to default value
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=5.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=5;
		gbc.gridheight=1;
		filenamescombobox.setPrototypeDisplayValue("Filename");
		menubar.add(filenamescombobox,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=5;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=9.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=9;
		gbc.gridheight=1;
		classnamescombobox.setPrototypeDisplayValue("Class");
		menubar.add(classnamescombobox,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=14;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=5.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=5;
		gbc.gridheight=1;
		combobox.setPrototypeDisplayValue("Method");
		menubar.add(combobox,gbc);

		menubar.validate();
		menubar.repaint();

		JLabel label = new JLabel();
		label.setText("Starter:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx=19;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(label,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=20;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=2.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		startupcombobox.setPrototypeDisplayValue("Start");
		menubar.add(startupcombobox,gbc);

		menubar.validate();
		menubar.repaint();

		gbc.gridx=22;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(lock,gbc);

		menubar.validate();
		menubar.repaint();

		JLabel label4 = new JLabel();
		label4.setText("lock");
		gbc.gridx=23;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		menubar.add(label4,gbc);

		menubar.validate();
		menubar.repaint();

		reload = new JButton("reload");
		gbc.gridx=24;
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=2.0;
		gbc.weighty=1.0;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		menubar.add(reload,gbc);

		menubar.validate();
		menubar.repaint();
		
		frame.setJMenuBar(menubar);
		
		menubar.revalidate();
		menubar.repaint();
	
		//frame.setJMenuBar(menubar);
				
		frame.setLocation(190,0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		textarea.requestFocus();
		textarea.setTabSize(4);		
	}
	public void updateMethodComboBox(ActionEvent ie) {
		if(classnamescombobox.hasFocus()) {
		final String classname = (String)classnamescombobox.getSelectedItem();
		combobox.removeAllItems();
		LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();
		LinkedHashMapInterface<String,LinkedHashMap<String,Integer>> Lhmi = new LinkedHashMapInterface<String,LinkedHashMap<String,Integer>>(classnamesandmethodnames) {
		
			public void KeyAndValue(String key,LinkedHashMap<String,Integer> value) {
				if(classname.equals(key)) {			
					for(String method_name:value.keySet()) {					
						combobox.addItem(method_name);
					}
				}	
			}
		};
		Lhmi.iterate();
		}
	}
			
	public void updateMethodComboBox(ItemEvent ie) {
		final String classname = (String)classnamescombobox.getSelectedItem();						
		if( ie.getStateChange() == ItemEvent.SELECTED ) {
			combobox.removeAllItems();
			LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();
			LinkedHashMapInterface<String,LinkedHashMap<String,Integer>> lhmpi= new LinkedHashMapInterface<String,LinkedHashMap<String,Integer>>(classnamesandmethodnames) {
				public void KeyAndValue(String key,LinkedHashMap<String,Integer> value) {
					if(classname.equals(key)) {
						for(String methodname:value.keySet()) {
							combobox.addItem(methodname);
						}
					}
				}
			};
			lhmpi.iterate();
		}
	}	
	public void scrollToCaretPosition(int wholedocumentindex) {
		SwingUtilities.invokeLater(new Runnable() {
		        public void run(){
		            try {
		            	if(wholedocumentindex <= (textarea.getText().length()) ) {
					Rectangle2D viewposition=textarea.modelToView2D(wholedocumentindex);
					Point caretposition=new Point(0,(int)viewposition.getY());
					scrollpane.getViewport().setViewPosition(caretposition);
					textarea.grabFocus();
					textarea.setCaretPosition(wholedocumentindex);
					line.setText("line number: "+getLineNumber(textarea.getText().substring(0,wholedocumentindex))+" ");
				}
		            }
			catch (BadLocationException exception) {
				exception.printStackTrace();
			}

		        }
		    });
	}

	public void selectCode(ActionEvent ev) {
		if(combobox.hasFocus() ) {
			String classname = (String)classnamescombobox.getSelectedItem();
			String methodname = (String)combobox.getSelectedItem();
			LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();
			LinkedHashMap<String,Integer> classandmethods = classnamesandmethodnames.get(classname);
			int wholedocumentindex = classandmethods.get(methodname);
			
			
			JScrollBar verticalscrollbar=scrollpane.getVerticalScrollBar();
			
			/*verticalscrollbar.setValue(textarea.getText().length()-1);
			textarea.setCaretPosition(textarea.getText().length()-1);*/
			verticalscrollbar.setValue(0);
			textarea.setCaretPosition(0);
			textarea.requestFocus();
			
			verticalscrollbar.setValue(wholedocumentindex);
			textarea.setCaretPosition(wholedocumentindex);
			//JOptionPane.showMessageDialog(null,"Opened new file.");
			
			verticalscrollbar.setValue(wholedocumentindex);
			
			
			scrollToCaretPosition(wholedocumentindex);
		}
																
	}
	public void selectCode(ItemEvent ev) {
		if(ev.getStateChange() == ItemEvent.SELECTED) {
			String classname = (String)classnamescombobox.getSelectedItem();
			String methodname = (String)combobox.getSelectedItem();
			LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();			
			LinkedHashMap<String,Integer> classandmethods = classnamesandmethodnames.get(classname);
			int wholedocumentindex = classandmethods.get(methodname);
			
			
			JScrollBar verticalscrollbar=scrollpane.getVerticalScrollBar();
			
			/*verticalscrollbar.setValue(textarea.getText().length()-1);
			textarea.setCaretPosition(textarea.getText().length()-1);*/
			verticalscrollbar.setValue(0);
			textarea.setCaretPosition(0);
			textarea.requestFocus();
			
			verticalscrollbar.setValue(wholedocumentindex);
			textarea.setCaretPosition(wholedocumentindex);
			//JOptionPane.showMessageDialog(null,"Opened new file.");
			
			verticalscrollbar.setValue(wholedocumentindex);
			
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			if(storeselectedfile.getCaretPosition(fileName) != 0) scrollToCaretPosition(wholedocumentindex);
		}
	}
	public void setStarterClassBoxes() {
		if(!filelistmodifier.isEmpty()) {
			// System.out.println(filelistmodifier);
			List<String> mains=getclassmethods.getMains(filelistmodifier);
			if(mains.size() == 1)
				lock.setSelected(true);
			for(String string:mains) {
				startupcombobox.addItem(string);
			}
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			String starterclass = storeselectedfile.getStarterClass();
			/*if(starterclass.contains("/") || starterclass.contains("\\")) {
				starterclass=fileName.replaceAll(".+\\\\","");
				starterclass=starterclass.replace(".java","");
			}*/
			if(!starterclass.equals("")) {
				startupcombobox.setSelectedItem(starterclass);
				lock.setSelected(true);
			}
			else if(mains.contains("Main")) {
				startupcombobox.setSelectedItem("Main");
				lock.setSelected(true);
			}
		}
	}
	public MSDOS msdos;
	public void open(String selected2) {
		try {
			// JOptionPane.showMessageDialog(null,deselected);
			String dir = fileName;
			if(dir == null) JOptionPane.showMessageDialog(null,"dir is null");
			
			dir = dir.replaceAll("[^\\\\]+\\.java","");
			if(dir == null) JOptionPane.showMessageDialog(null,"2 dir is null");
				
	
			if(selected2 == null) JOptionPane.showMessageDialog(null,"filename combox value is null");
			
			if(msdos == null)
				msdos = new MSDOS(dir);
			else
				msdos.setFileName(dir);
			
			dir=dir+selected2;
				
			fileName = dir; // Might need uncomment to make Main.java work again.
			//fileName = dir+".java";
			Path path = Paths.get(dir);
			String lines = Files.readString(path,StandardCharsets.UTF_8);
			/*DefaultCaret defaultcaret = (DefaultCaret)textarea.getCaret();
			defaultcaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
			textarea.setCaret(defaultcaret);*/
			textarea.setText(lines);
			// textarea.setCaretPosition(0);
				
			if(filelistmodifier.isEmpty()) {
				filelistmodifier.fillList(fileName);
			}			
			else if(!filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","").equals(fileName.replaceAll("[^\\\\]+\\.java",""))) {
				filelistmodifier = new FileListModifier();
				filelistmodifier.fillList(fileName);
			}
			git.Change(fileName);
			expandable.open();
			
			getclassmethods = new GetClassMethods(textarea);		
			getclassname = new GetClassName(textarea);
			
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			storeselectedfile.set(fileName);
			setStarterClassBoxes();
			if(fileName != null && !fileName.equals("")) {
				frame.setTitle(fileName.replaceAll(".+\\\\",""));
			}
			//filelistmodifier.setSelected(selected2);
			/*if(!deselected.equals("")) {
				filelistmodifier.setToMostRecentAfterSelected(deselected);	
			}*/
			dir = filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","");
			String dir2 = fileName.replaceAll("[^\\\\]+\\.java","");
			if(!dir.equals(dir2)) {
				filelistmodifier.fillList(fileName);
			}
			loadComboboxes(filelistmodifier);
			filenamescombobox.setSelectedItem(selected2);
			StoreSelectedFile storeselectedfile3 = new StoreSelectedFile();
			int caretposition=storeselectedfile3.getCaretPosition(fileName);
			if(caretposition != 0)
			scrollToCaretPosition(caretposition);
			//deselected="";
			int selectedtab = tabbedpane.getSelectedIndex();
			fileNames.set(selectedtab,fileName);
			tabbedpane.setTitleAt(selectedtab,getFileName(fileName));
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public CurlyBraceKeyListener curlybracekeylistener;
	public boolean go_to_line_is_executed = false;
	String deselected = "";
	public void setListeners() {
		opennewtab.addActionListener((ev) -> {
			addOrUpdateTab(ev);
		});
		tabbedpane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeevent) {
				Main.this.addOrUpdateTab(changeevent);	
			}
		});	
		setApiClasses();
		setKeywords();
		generatejar.addActionListener((ev) -> {
			String[] options={"Yes","No"};
			int option=JOptionPane.showOptionDialog(null,"Compile for previous versions of Java?","Deprecated versions of Java",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
			switch(option) {
				case JOptionPane.YES_OPTION:
					JFrame getjavaversion = new JFrame();
					JPanel panelversion = new JPanel();
					JLabel label = new JLabel("Version of Java:");
					panelversion.add(label);
					JComboBox<Integer> combobox = new JComboBox<Integer>();
					for(int i = 0; i < 23; i++) {
						combobox.addItem(i+1);
					}					
					combobox.setSelectedItem(23);
					panelversion.add(combobox);
					JButton compiley = new JButton("compile");
					panelversion.add(compiley);
					getjavaversion.add(panelversion);
					getjavaversion.pack();
					getjavaversion.setVisible(true);
				
					compiley.addActionListener((ev4) -> {
						try {
							int javaversionnumber=(Integer)combobox.getSelectedItem();
							getjavaversion.dispose();
							Compile compile = new Compile();
							compile.compileall(fileName,javaversionnumber,sal,ev4);
							CommandLine commandline = new CommandLine();
							StoreSelectedFile storeselectedfile = new StoreSelectedFile();
							Preferences preferences=storeselectedfile.get(fileName);
							String main=preferences.starterclass;
							String dir = fileName.replaceAll("[^\\\\]+\\.java","");
							if(!fileName.equals("")) {
								List<String> jars = preferences.jars;
								for(String jar:jars) {
									jar = getFileName(jar);
									Process process=commandline.run("\""+System.getProperty("java.home")+"\\bin\\jar.exe\" xf "+jar,dir);
									process.waitFor();
									//output.write(" "+jar);
								}
							}
							if(!fileName.equals("")) {
								if(main.equals("")) {
									main=fileName.replaceAll(".+\\\\","");
									main = main.replaceAll("\\.java","");
								}
								storeselectedfile.set(fileName);
								LinkedHashMap<String,Preferences> linkedhashmap=storeselectedfile.getBackup();
								linkedhashmap.get(fileName).starterclass=main;
								storeselectedfile.setBackup(linkedhashmap);
							}
							FileWriter filewriter = new FileWriter( dir+"mf.txt",StandardCharsets.UTF_8);
							BufferedWriter output = new BufferedWriter(filewriter);
							output.write("Manifest-Version: 1.0");
							output.write("\n");
							output.write("Main-Class: ");
							output.write(main);
							output.write("\n");
							//output.write("Class-Path:");
							//output.write(" *");
							//output.write("\n");
							output.close();
							
							AllFiles allfiles = new AllFiles(main,dir);
							if(allfiles.exists() && !allfiles.delete()) {
								commandline = new CommandLine();
								JOptionPane.showMessageDialog(null,dir+"ForJava"+javaversionnumber+"_"+main+".jar is already open. Run script to close "+main+".jar");
								FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
								BufferedWriter output2 = new BufferedWriter(filewriter2);
								output2.write("START /B /WAIT taskkill /F /im java.exe");
								output2.write("\n");
								output2.write("START /B /WAIT taskkill /F /im javaw.exe");
								output2.write("\n");
								for(int i = 0; i < allfiles.files.length; i++) {
									File file2 = new File(allfiles.files[i]);
									if(file2.exists()) {
										output2.write("del "+allfiles.files[i]);
										output2.write("\n");
									}
								}
								// START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm Main.jar mf.txt .
								output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm ForJava"+javaversionnumber+"_"+main+".jar mf.txt .");
								output2.write("\n");
								
								commandline = new CommandLine();
								output2.write("java -jar ForJava"+javaversionnumber+"_"+main+".jar");
								output2.write("\n");
								output2.write("\n");
								output2.close();
								commandline = new CommandLine();
								commandline.runWithMSDOS("closeandcreatejar.bat",dir);
							}
							else {
								String input = "\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+"ForJava"+javaversionnumber+"_"+main+".jar mf.txt .";
								JOptionPane.showMessageDialog(null,input);
								Process process=commandline.run(input,dir);
								
								InputStream inputstream = process.getErrorStream();
								InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
								BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
								String line = bufferedreader.readLine();
								if(line == null) {
									JOptionPane.showMessageDialog(null,"jar created");
								}
								else {
									String lines = line;
									while(true) {
										line = bufferedreader.readLine();
										if(line == null)
											break;
										lines = lines+"\n"+line;
									}
									JOptionPane.showMessageDialog(null,lines);
								}
							}
						} catch(InterruptedException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null,ex.getMessage());
						} catch(IOException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null,ex.getMessage());
						}
					});
				break;
				case JOptionPane.NO_OPTION:
					try {
						Compile compile = new Compile();
						compile.compileall(fileName,sal,ev);
						CommandLine commandline = new CommandLine();
						StoreSelectedFile storeselectedfile = new StoreSelectedFile();
						Preferences preferences=storeselectedfile.get(fileName);
						String main=preferences.starterclass;
						String dir = fileName.replaceAll("[^\\\\]+\\.java","");
						if(!fileName.equals("")) {
							List<String> jars = preferences.jars;
							for(String jar:jars) {
								jar = getFileName(jar);
								Process process=commandline.run("\""+System.getProperty("java.home")+"\\bin\\jar.exe\" xf "+jar,dir);
								process.waitFor();
								//output.write(" "+jar);
							}
						}
						if(!fileName.equals("")) {
							if(main.equals("")) {
								main=fileName.replaceAll(".+\\\\","");
								main = main.replaceAll("\\.java","");
							}
							storeselectedfile.set(fileName);
							LinkedHashMap<String,Preferences> linkedhashmap=storeselectedfile.getBackup();
							linkedhashmap.get(fileName).starterclass=main;
							storeselectedfile.setBackup(linkedhashmap);
						}
						
						FileWriter filewriter = new FileWriter( dir+"mf.txt",StandardCharsets.UTF_8);
						BufferedWriter output = new BufferedWriter(filewriter);
						output.write("Manifest-Version: 1.0");
						output.write("\n");
						output.write("Main-Class: ");
						output.write(main);
						output.write("\n");
						//output.write("Class-Path: ");
						//output.write("javafx/lib/");
						
						//output.write(" *");
						//output.write("\n");
						output.close();
						
						//File file = new File(dir+main+".jar");
						AllFiles allfiles = new AllFiles(main,dir);
						if(allfiles.exists() && !allfiles.delete()) {
							commandline = new CommandLine();
							JOptionPane.showMessageDialog(null,dir+main+".jar is already open. Run script to close "+main+".jar");
							FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
							BufferedWriter output2 = new BufferedWriter(filewriter2);
							output2.write("START /B /WAIT taskkill /F /im java.exe");
							output2.write("\n");
							output2.write("START /B /WAIT taskkill /F /im javaw.exe");
							output2.write("\n");
							for(int i = 0; i < allfiles.files.length; i++) {
								File file2 = new File(allfiles.files[i]);
								if(file2.exists()) {
									output2.write("del "+allfiles.files[i]);
									output2.write("\n");
								}
							}
							// START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm Main.jar mf.txt .
							output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+main+".jar mf.txt .");
							output2.write("\n");
							
							commandline = new CommandLine();
							output2.write("java -jar "+main+".jar");
							output2.write("\n");
							output2.write("\n");
							output2.close();
							commandline.runWithMSDOS("closeandcreatejar.bat",dir);
						}
						else { 
							String input = "\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+main+".jar mf.txt .";
							JOptionPane.showMessageDialog(null,input);
							Process process=commandline.run(input,dir);
							
							InputStream inputstream = process.getErrorStream();
							InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
							BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
							String line = bufferedreader.readLine();
							if(line == null) {
								JOptionPane.showMessageDialog(null,"jar created");
							}
							else {
								String lines = line;
								while(true) {
									line = bufferedreader.readLine();
									if(line == null)
										break;
									lines = lines+"\n"+line;
								}
								JOptionPane.showMessageDialog(null,lines);
							}
						}
					} catch(InterruptedException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null,ex.getMessage());
					} catch(IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null,ex.getMessage());
					}
				break;
			}
		});
		/*deprecated.addActionListener((ev) -> {
			try {
				if(fileName.equals("")) {
					NoFileOpen nofileopen=new NoFileOpen(textarea);
					fileName=nofileopen.getFileName();
				}
				sal.actionPerformed(ev);
				String classpath = fileName.replaceAll("[^\\\\]+\\.java","");

				CommandLine commandline = new CommandLine();
				commandline.deprecated();
				StoreSelectedFile storeselectedfile = new StoreSelectedFile();
				Preferences preferences=storeselectedfile.get(fileName);
				for(String jar:preferences.jars) {
					commandline.addExternalJar(jar);
				}
				if(jarcheckbox.isSelected()) {
					commandline.addJunit();
				}
				String main_class = fileName.replaceAll(".+\\\\","");						
				main_class =main_class.replaceAll("\\.java","");
				commandline.setMainClass(main_class);
				if(checkbox.isSelected()) {
					commandline.addClasspathCheckboxFeature();
				}
				// JOptionPane.showMessageDialog(null,commandline.javac());
				// runtime.exec(new String[]{"cmd.exe","/c","javac -cp *;. "+fileName.replaceAll(".+\\\\","")},null,new File(classpath));
				String[] command = new String[3];
				command[0] = "cmd.exe";
				command[1] = "/c";				
				command[2] = commandline.javac();
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(command,null,new File(classpath));
				// process = compileFromMSDOS(fileName,classpath);
				
				InputStream inputstream = process.getErrorStream();
				InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
				BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
				String line = bufferedreader.readLine();
				if(line == null) {
					JOptionPane.showMessageDialog(null,"compiled");
				}
				else {
					String lines = line;
					while(true) {
						line = bufferedreader.readLine();
						if(line == null)
							break;
						lines = lines+"\n"+line;
					}
					JOptionPane.showMessageDialog(null,lines);
				}
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		});
		*/
		reload.addActionListener( (ev) -> {
			try {
				String lines = Files.readString(Paths.get(fileName),StandardCharsets.UTF_8);
				int caretposition = textarea.getCaretPosition();
				textarea.setText(lines);
				textarea.setCaretPosition(caretposition);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		);
		addjar.addActionListener((ev) -> {
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			/*if(preferences == null || preferences.jars.size() == 0) {
				String directory = fileName.replaceAll("[^\\\\]+\\.java","");
				JFileChooser filechooser = new JFileChooser(new File(directory));
				FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Open .jar","jar");
				fileChooser.setFileFilter(filenameextensionfilter);
				int result = filechooser.showOpenDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = filechooser.getSelectedFile();
					storeselectedfile.addJar(fileName,selectedFile.getAbsolutePath());
				}
			}
			else {
			*/
				JFrame jarframe = new JFrame();
				JPanel jarpanel = new JPanel();
				GridLayout gridlayout = new GridLayout(preferences.jars.size(),1);
				jarpanel.setLayout(gridlayout);
				for(String jar:preferences.jars) {
					JPanel actualjar = new JPanel();
					actualjar.add(new JLabel(jar));
					JButton removejar = new JButton("remove");
					removejar.addActionListener( (ev2) -> {
						StoreSelectedFile storeselectedfile2=new StoreSelectedFile();
						storeselectedfile2.removeJar(fileName,jar);
						gridlayout.setRows(gridlayout.getRows()-1);
						jarpanel.remove(actualjar);
						jarpanel.validate();
						jarpanel.repaint();
						jarframe.pack();
					});					
					actualjar.add(removejar);
					jarpanel.add(actualjar);
				}
				jarframe.add(jarpanel,BorderLayout.CENTER);
				JPanel south = new JPanel();
				JButton add = new JButton("add");
				south.add(add);
				jarframe.add(south,BorderLayout.SOUTH);
				add.addActionListener( (ev1) -> {
					String directory = fileName.replaceAll("[^\\\\]+\\.java","");
					JFileChooser filechooser = new JFileChooser(new File(directory));
					FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Open .jar","jar");
					filechooser.setFileFilter(filenameextensionfilter);
					int result = filechooser.showOpenDialog(frame);
					if(result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = filechooser.getSelectedFile();
						storeselectedfile.addJar(fileName,selectedFile.getAbsolutePath());
						gridlayout.setRows(gridlayout.getRows()+1);
						JPanel row = new JPanel();
						row.add(new JLabel(selectedFile.getAbsolutePath()));
						
						JButton removejar = new JButton("remove");
						removejar.addActionListener( (ev2) -> {
							StoreSelectedFile storeselectedfile2=new StoreSelectedFile();
							storeselectedfile2.removeJar(fileName,selectedFile.getAbsolutePath());
							gridlayout.setRows(gridlayout.getRows()-1);
							jarpanel.remove(row);
							jarpanel.validate();
							jarpanel.repaint();
							jarframe.pack();
						});	
						row.add(removejar);
						
						jarpanel.add(row);
						jarpanel.validate();
						jarpanel.repaint();
						jarframe.pack();
					}
				});

				jarframe.pack();
				jarframe.setVisible(true);
			//}
		});
		filenamescombobox.addItemListener(ev-> {
			if(ev.getStateChange() == ItemEvent.DESELECTED) {
				deselected = (String)ev.getItem(); 
			}
		});
		// setStarterClassBoxes(); Might need uncomment this in future.
		filenamescombobox.addActionListener((ev) -> {
			if(filenamescombobox.hasFocus()) {
				StoreSelectedFile storeselectedfile = new StoreSelectedFile();
				int caretposition = textarea.getCaretPosition();
				String maindirectory=fileName.replaceAll("[^\\\\]+\\.java","");
				storeselectedfile.setCaretPosition(maindirectory+deselected,caretposition);
				open((String)filenamescombobox.getSelectedItem());
			}
		});

		startupcombobox.addActionListener( (ev) -> {
			if(startupcombobox.hasFocus()) {
				String selected=(String)startupcombobox.getSelectedItem();
				// filelistmodifier.setSelected(selected);
			}
		}
		);
		
		/*classnamescombobox.addItemListener((ev) -> {
			updateMethodComboBox(ev);
		});
		*/
		classnamescombobox.addActionListener((ev) -> {
			if(classnamescombobox.hasFocus()) {
				updateMethodComboBox(ev);
			}
		});
		combobox.addActionListener((ev) -> {
			if(combobox.hasFocus()) {
				selectCode(ev);
			}
		});
		combobox.addItemListener((ev) -> {
			selectCode(ev);
		});
		curlybracekeylistener = new CurlyBraceKeyListener(this);				
		positiontrackers.add(curlybracekeylistener.positiontracker);
 		textarea.addKeyListener(curlybracekeylistener); 		
		control_f.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JFrame frame2 = new JFrame();

				JPanel panel0 = new JPanel();
				frame2.getContentPane().add(panel0);
				panel0.setLayout(new GridBagLayout());
				JTextField input = new JTextField(20);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx=0;
				gbc.gridy=0;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx=8.0;
				gbc.weighty=1.0;
				gbc.anchor=gbc.CENTER;
				gbc.gridwidth=8;
				gbc.gridheight=1;
				gbc.insets=new Insets(4,0,0,0);
				panel0.add(input,gbc);
				
				panel0.validate();
				panel0.repaint();
				
				JButton click = new JButton("Find");
				gbc.gridx=8;
				gbc.gridy=0;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx=2.0;
				gbc.weighty=1.0;
				gbc.anchor=gbc.CENTER;
				gbc.gridwidth=2;
				gbc.gridheight=1;
				panel0.add(click,gbc);

				panel0.validate();
				panel0.repaint();
		
				JCheckBox searchall = new JCheckBox("all");
				gbc.gridx=0;
				gbc.gridy=1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx=2.0;
				gbc.weighty=1.0;
				gbc.anchor=gbc.CENTER;
				gbc.gridwidth=2;
				gbc.gridheight=1;
				gbc.insets = new Insets(0,0,0,0);
				panel0.add(searchall,gbc);
				
				panel0.validate();
				panel0.repaint();
		
				JCheckBox casey=new JCheckBox("case");
				gbc.gridx=2;
				gbc.gridy=1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx=2.0;
				gbc.weighty=1.0;
				gbc.anchor=gbc.CENTER;
				gbc.gridwidth=2;
				gbc.gridheight=1;
				gbc.insets = new Insets(0,0,0,0);
				panel0.add(casey,gbc);
		
				panel0.validate();
				panel0.repaint();
		
				JTextField replaceinput = new JTextField(5);
				replaceinput.setEditable(false);
				gbc.gridx=4;
				gbc.gridy=1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx=2.0;
				gbc.weighty=1.0;
				gbc.anchor=gbc.CENTER;
				gbc.gridwidth=2;
				gbc.gridheight=1;
				panel0.add(replaceinput,gbc);
				
				panel0.validate();
				panel0.repaint();
				
				JCheckBox replace = new JCheckBox("replace");
				gbc.gridx=6;
				gbc.gridy=1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx=2.0;
				gbc.weighty=1.0;
				gbc.anchor=gbc.CENTER;
				gbc.gridwidth=2;
				gbc.gridheight=1;
				panel0.add(replace,gbc);
				replace.addActionListener( (ev3) -> {
					replaceinput.setEditable(true);
				});
				
				panel0.validate();
				panel0.repaint();
		
				JCheckBox selection = new JCheckBox("Select");
				gbc.gridx=8;
				gbc.gridy=1;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx=2.0;
				gbc.weighty=1.0;
				gbc.anchor=gbc.CENTER;
				gbc.gridwidth=2;
				gbc.gridheight=1;
				gbc.insets = new Insets(0,0,0,0);
				panel0.add(selection,gbc);
					
				panel0.validate();
				panel0.repaint();

				frame2.pack();
				frame2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
				Control_F control_f = new Control_F(Main.this,searchall,textarea,replace,selection,replaceinput,casey);
				ActionListener clicky=new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						control_f.Find(input.getText());
					}
				};
				click.addActionListener(clicky);
				input.addActionListener(clicky);
				
				frame2.setVisible(true);
				//replaceinput.setVisible(false);
			}
		});		
		textarea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if(!curlybracekeylistener.is_content_update) {
					String text = textarea.getText();
					int position = textarea.getCaretPosition();
					if(text.length() >= (position+1))
						line.setText("line number: "+getLineNumber(text.substring(0,position+1))+" ");
					else
						line.setText("line number: "+getLineNumber(text.substring(0,position))+" ");
				}
			}
		});
		newopenwindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Main main = new Main();
				FileListModifier filelistmodifier2 = (FileListModifier)filelistmodifier.clone();
				filelistmodifier2.addNew();		
				main.loadComboboxes(filelistmodifier2);
			}
		});
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});
		saveItem.addActionListener(sal);
		fontmenuitem.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent ev) {
				JFrame fontFrame = new JFrame();
				fontFrame.setLayout(new FlowLayout());
				JLabel label = new JLabel("Font size: ");
				Font font = textarea.getFont();
				JTextField textfield = new JTextField(font.getSize()+"");
				JButton submitButton = new JButton("submit");
				submitButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						try {
							Font font2 = new Font(font.getName(),font.getStyle(),Integer.parseInt(textfield.getText()));
							textarea.setFont(font2);
							textarea.requestFocus();
						} catch (NumberFormatException ex) {
							System.out.println(ex);
						}
					}
				});
				fontFrame.getContentPane().add(label);
				fontFrame.getContentPane().add(textfield);
				fontFrame.getContentPane().add(submitButton);
				fontFrame.pack();
				fontFrame.setVisible(true);				
			}
		});
		saveasitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String text = textarea.getText();
					
					String dir = fileName;
					if(!dir.equals("")) {
						dir = fileName.replaceAll("[^\\\\]+\\.java","");
					}
					else {
						dir = System.getProperty("user.home");
					}
					System.out.println("dir is: "+dir);
					
					JFileChooser fileChooser = new JFileChooser(new File(dir));
					FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Save as .java","java");
					fileChooser.setFileFilter(filenameextensionfilter);
					int status =fileChooser.showSaveDialog(frame);
					if(status == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						
						fileName = file.getPath();
						fileName=addDotJava(fileName);
						PrintWriter output = new PrintWriter(fileName);
						output.print(text);
						output.close();
						frame.setTitle(fileName.replaceAll(".+\\\\",""));
					}
				}catch (FileNotFoundException ex) {
					System.out.println(ex);
				}
			}
		});
		newitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				fileName = "";
				textarea.setText("");
				frame.setTitle("");
			}
		});
		tabSizeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JFrame tabFrame = new JFrame();
				tabFrame.setLayout(new FlowLayout());
				JLabel tabLabel = new JLabel("Tab size: ");
				JTextField numberTextField = new JTextField(textarea.getTabSize()+"");
				JButton tabsubmit = new JButton("submit");
				tabsubmit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						/*String text = textarea.getText();
						text = text.replaceAll("\stopSe\stopSe\stopSe\stopSe\stopSe\stopSe","\t");*/
						textarea.setTabSize(Integer.parseInt(numberTextField.getText()));
						//textarea.setText(text);
					}
				});
				tabFrame.getContentPane().add(tabLabel);
				tabFrame.getContentPane().add(numberTextField);
				tabFrame.getContentPane().add(tabsubmit);
				tabFrame.pack();
				tabFrame.setVisible(true);
			}
		});
		opennewwindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Thread t = new Thread() {
					public void run() {
						String dir = fileName;
						System.out.println("dir before is: "+dir);
						if(!dir.equals("")) {
							dir = dir.replaceAll("[^\\\\]+\\.java","");
						}
						else {
							dir = System.getProperty("user.home");
						}
						
						JFileChooser filechooser = new JFileChooser(new File(dir));
						FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Open .java","java");
						filechooser.setFileFilter(filenameextensionfilter);
						int result = filechooser.showOpenDialog(frame);
						if(result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = filechooser.getSelectedFile();
							String fileName3= selectedFile.getPath();

							Main main2 = new Main(new OpenDefaultContent(fileName3));
						}
					}
				};
				t.start();
			}
		});
		compile_all.addActionListener((ev) -> {		
			try {
				if(fileName.equals("")) {
					NoFileOpen nofileopen=new NoFileOpen(textarea);
					fileName=nofileopen.getFileName();
				}
				sal.actionPerformed(ev);
				if(!fileName.equals("")) {
					String classpath = fileName.replaceAll("[^\\\\]+\\.java","");
					CommandLine commandline = new CommandLine();
					commandline.compileAll();
					StoreSelectedFile storeselectedfile = new StoreSelectedFile();
					Preferences preferences=storeselectedfile.get(fileName);
					
					for(String jar:preferences.jars) {
						commandline.addExternalJar(jar);
					}
					if(checkbox.isSelected()) {
						commandline.addClasspathCheckboxFeature();
					}
					if(jarcheckbox.isSelected()) {
						commandline.addJunit();
					}
					//Process process = compileFromMSDOS("*.java",classpath);
					String[] command = new String[3];
					command[0] = "cmd.exe";
					command[1] = "/c";
					command[2] = commandline.javac();
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(command,null,new File(classpath));
					
					InputStream inputstream = process.getErrorStream();
					InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
					BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
					String line = bufferedreader.readLine();
					if(line == null) {
						JOptionPane.showMessageDialog(null,"compiled");
					}
					else {
						String lines = line;
						while(true) {
							line = bufferedreader.readLine();
							if(line == null)
								break;
							lines = lines+"\n"+line;
						}
						JOptionPane.showMessageDialog(null,lines);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"No file name given to save file.");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}		
		});
			
		compile.addActionListener(new ActionListener() {								
			public void actionPerformed(ActionEvent e) {
				try {
					if(fileName.equals("")) {
						NoFileOpen nofileopen=new NoFileOpen(textarea);
						fileName=nofileopen.getFileName();
					}
					sal.actionPerformed(e);
					if(!fileName.equals("")) {
						String classpath = fileName.replaceAll("[^\\\\]+\\.java","");
	
						CommandLine commandline = new CommandLine();
						StoreSelectedFile storeselectedfile = new StoreSelectedFile();
						Preferences preferences=storeselectedfile.get(fileName);
						for(String jar:preferences.jars) {
							commandline.addExternalJar(jar);
						}
						if(jarcheckbox.isSelected()) {
							commandline.addJunit();
						}
						String main_class = fileName.replaceAll(".+\\\\","");						
						main_class =main_class.replaceAll("\\.java","");
						commandline.setMainClass(main_class);
						if(checkbox.isSelected()) {
							commandline.addClasspathCheckboxFeature();
						}
						// JOptionPane.showMessageDialog(null,commandline.javac());
						// runtime.exec(new String[]{"cmd.exe","/c","javac -cp *;. "+fileName.replaceAll(".+\\\\","")},null,new File(classpath));
						String[] command = new String[3];
						command[0] = "cmd.exe";
						command[1] = "/c";
						command[2] = commandline.javac();
						Runtime runtime = Runtime.getRuntime();
						Process process = runtime.exec(command,null,new File(classpath));
						// process = compileFromMSDOS(fileName,classpath);
						
						InputStream inputstream = process.getErrorStream();
						InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
						BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
						String line = bufferedreader.readLine();
						if(line == null) {
							JOptionPane.showMessageDialog(null,"compiled");
						}
						else {
							String lines = line;
							while(true) {
								line = bufferedreader.readLine();
								if(line == null)
									break;
								lines = lines+"\n"+line;
							}
							JOptionPane.showMessageDialog(null,lines);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"No filename saved.");
					}
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread() {
					public void run() {
						try {	
							StoreSelectedFile storeselectedfile10=new StoreSelectedFile();
							int caretposition = textarea.getCaretPosition();
							storeselectedfile10.setCaretPosition(fileName,caretposition);
						
							String selected = (String)startupcombobox.getSelectedItem();
							Runtime runtime = Runtime.getRuntime();

							String string = textarea.getText();
							String lines2 = "";
							
							boolean isCompiled = true;
							if( !fileName.equals("") ) {
								// Check if already saved
								File file2 = new File(fileName);
								Path path = Paths.get(file2.getPath());
								String lines = Files.readString(path,StandardCharsets.UTF_8);
								char[] characters = lines.toCharArray();
					
								for(int i = 0; i < characters.length-1; i++) {
									if((int)characters[i] == 13) {
										if(characters[i+1] != 10) {
											characters[i] = (char)10;
										}
									}
								}
								for(int i = 0; i < characters.length; i++) {
									lines2+=characters[i];
								}
							}
							else {
								NoFileOpen nofileopen=new NoFileOpen(textarea);
								fileName=nofileopen.getFileName();
								isCompiled = false;
							
}
							String classpath1 = fileName.replaceAll("[^\\\\]+\\.java","");
							String replaceAll = fileName.replaceAll("[^\\\\]+\\.java","");
							String fileNameWithoutDotJava = fileName.replaceAll(".+\\\\","").replace(".java","");
							if(isCompiled && string.equals(lines2)) { // End check if already saved
								System.out.println("Is equal.");
								
								CommandLine commandline = new CommandLine();
								if(jarcheckbox.isSelected()) {
									commandline.addJunit();
								}
								if(lock.isSelected()) {
									String save = selected.replace(".java","");
									StoreSelectedFile storeselectedfile = new StoreSelectedFile();
									storeselectedfile.set(fileName);
									storeselectedfile.setStarterClass(save);
									commandline.setMainClass(save);
									
									Preferences preferences=storeselectedfile.get(classpath1+selected+".java");
									for(String jar:preferences.jars) {
										commandline.addExternalJar(jar);
									}
								}
								else {
									commandline.setMainClass(fileNameWithoutDotJava);
									
									StoreSelectedFile storeselectedfile = new StoreSelectedFile();
									Preferences preferences=storeselectedfile.get(fileName);
									for(String jar:preferences.jars) {																		
										commandline.addExternalJar(jar);
									}
								}																												
								if(checkbox.isSelected()) {
									commandline.addClasspathCheckboxFeature();									
								}
								String[] command = new String[6];								
								command[0] = "cmd";
								command[1] = "/c";
								command[2] = "start";
								command[3] = "cmd";
								command[4]= "/k";
								command[5] = commandline.java();
								Process process=runtime.exec(command,null,new File(classpath1));
								// process = runJavaProgramFromMSDOS(fileNameWithoutDotJava,classpath1);
							}
 							else { // compile because not latest code.
								System.out.println("save new code first.");
								sal.actionPerformed(e);
								
										
								CommandLine commandline = new CommandLine();
								StoreSelectedFile storeselectedfile = new StoreSelectedFile();
								Preferences preferences=storeselectedfile.get(fileName);
								for(String jar:preferences.jars) {
									commandline.addExternalJar(jar);
								}
								if(jarcheckbox.isSelected())
									commandline.addJunit();
								String main_class = fileName.replaceAll(".+\\\\","");									
								main_class =main_class.replaceAll("\\.java","");
								commandline.setMainClass(main_class);
								if(checkbox.isSelected()) {
									commandline.addClasspathCheckboxFeature();
								}	
						
								// runtime.exec(new String[]{"cmd.exe","/c","javac -cp *;. "+fileName.replaceAll(".+\\\\","")},null,new File(classpath));
								String[] command = new String[3];
								command[0] = "cmd.exe";
								command[1] = "/c";
								command[2] = commandline.javac();
								runtime = Runtime.getRuntime();
								classpath1=fileName.replaceAll("[^\\\\]+\\.java","");
								Process process = runtime.exec(command,null,new File(classpath1));
								// process=Main.this.compileFromMSDOS(fileName,classpath1);
								
								InputStream inputstream = process.getErrorStream();
								InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
								BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
								String line = bufferedreader.readLine();
								if(line == null) {
									JOptionPane.showMessageDialog(null,"compiled");
									// run
	 								String classpath = fileName.replaceAll("[^\\\\]+\\.java","");

									commandline = new CommandLine();
									if(lock.isSelected()) {
										String save = selected.replace(".java","");
										storeselectedfile = new StoreSelectedFile();
										storeselectedfile.set(fileName);
										storeselectedfile.setStarterClass(save);
										
										storeselectedfile = new StoreSelectedFile();
										preferences=storeselectedfile.get(classpath1+selected+".java");
										for(String jar:preferences.jars) {
											commandline.addExternalJar(jar);
										}
										commandline.setMainClass(save);
									}
									else {
										fileNameWithoutDotJava = fileName.replaceAll(".+\\\\","").replace(".java","");
										commandline.setMainClass(fileNameWithoutDotJava);
										
										storeselectedfile = new StoreSelectedFile();
										preferences=storeselectedfile.get(fileName);
										for(String jar:preferences.jars) {										
											commandline.addExternalJar(jar);
										}
									}
									if(checkbox.isSelected()) {																		
										commandline.addClasspathCheckboxFeature();																				
									}
									if(jarcheckbox.isSelected()) {
										commandline.addJunit();
									}
									// JOptionPane.showMessageDialog(null,commandline.java());
									command = new String[6];
									command[0] = "cmd";
									command[1] = "/c";
									command[2] = "start";
									command[3] = "cmd";									
									command[4]= "/k";
									command[5] = commandline.java();
									process=runtime.exec(command,null,new File(classpath1));
									// process = runJavaProgramFromMSDOS(fileNameWithoutDotJava,classpath);																
								}
								else {
									String lines = line;
									while(true) {
										line = bufferedreader.readLine();
										if(line == null)
											break;
										lines = lines+"\n"+line;
									}
									JOptionPane.showMessageDialog(null,lines);
								}
							}
						} catch(IOException ex) {
							ex.printStackTrace();
						}
					}
				};
				thread.start();
			}
		});
		go_to_line_number.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if(ae.getModifiers() == 0) {
						go_to_line_is_executed = true;
					}
					String wholetext=textarea.getText();
					String number =line_number.getText();
					int line_number = Integer.parseInt(number);
					String line = "";
					LineNumberReader linenumberreader=new LineNumberReader(new StringReader(wholetext));
					while((line = linenumberreader.readLine()) != null) {
						int linenumber2=linenumberreader.getLineNumber();
						if(line_number == linenumber2) break;
					}
					int startOfLine = -1;
					while((startOfLine = wholetext.indexOf(line,++startOfLine)) != -1) {
						//int startOfLine=wholetext.indexOf(line);
						String firsthalf=wholetext.substring(0,startOfLine+1);
						if(getLineNumber(firsthalf) == line_number) {
							textarea.grabFocus();
							textarea.setCaretPosition(startOfLine);
							break;
						}
						/*else {
							JOptionPane.showMessageDialog(null,getLineNumber(firsthalf)+" "+line_number);
						}
						*/
					}
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		});		
		frame.getRootPane().setDefaultButton(go_to_line_number);		
	}
	public String addDotJava(String filename) {	
		if(!(filename.endsWith(".java"))) {
			filename+=".java";
		}
		return filename;
	}
	public List<PositionTracker> positiontrackers = new ArrayList<PositionTracker>();	
	public void addOrUpdateTab(EventObject eventobject) {
		try {
			int index=tabbedpane.getSelectedIndex();
			String title=tabbedpane.getTitleAt(index);
			if(opennewtab.hashCode()==eventobject.getSource().hashCode()) {
				title="+";
			}
			if(title.equals("+")) {
				//Component plustab = tabbedpane.getComponentAt(index);
				String dir = Main.this.fileName;
				if(!dir.equals("")) {
					dir = dir.replaceAll("[^\\\\]+\\.java","");
				}
				else {
					dir = System.getProperty("user.home");
				}
				JFileChooser filechooser = new JFileChooser(new File(dir));
				FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Open .java","java");
				filechooser.setFileFilter(filenameextensionfilter);
				int result = filechooser.showOpenDialog(Main.this.frame);
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = filechooser.getSelectedFile();
					tabbedpane.remove(pluspanel);
					JTextArea textarea2 = new JTextArea();
					Font originalFont = textarea.getFont();
					textarea2.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));

					JScrollPane scrollpane2 = new JScrollPane(textarea2);
					String directoryandfilename = selectedFile.getPath();
					fileNames.add(directoryandfilename);
					String filename = Main.this.getFileName(directoryandfilename);
					
					Path path = Paths.get(directoryandfilename);
					String lines = Files.readString(path,StandardCharsets.UTF_8);
					textarea2.setTabSize(4);
					textarea2.setText(lines);
					
					textarea2.addKeyListener(curlybracekeylistener);
					positiontrackers.add(new PositionTracker(textarea2));
					
					tabbedpane.addTab(filename,scrollpane2);
					tabbedpane.addTab("+",pluspanel);
					tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-2);
				}
			}
			fileName=fileNames.get(tabbedpane.getSelectedIndex());
			curlybracekeylistener.positiontracker=positiontrackers.get(tabbedpane.getSelectedIndex());
			JScrollPane jscrollpane5=((JScrollPane)tabbedpane.getSelectedComponent());
			Main.this.textarea=(JTextArea)jscrollpane5.getViewport().getView();
			
			if(filelistmodifier.isEmpty()) {
				filelistmodifier.fillList(fileName);
			}			
			else if(!filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","").equals(fileName.replaceAll("[^\\\\]+\\.java",""))) {
				filelistmodifier = new FileListModifier();
				filelistmodifier.fillList(fileName);
			}
			git.Change(fileName);
			expandable.open();
			
			getclassmethods = new GetClassMethods(textarea);		
			getclassname = new GetClassName(textarea);
			
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			storeselectedfile.set(fileName);
			setStarterClassBoxes();
			if(fileName != null && !fileName.equals("")) {
				frame.setTitle(fileName.replaceAll(".+\\\\",""));
			}
			//filelistmodifier.setSelected(selected2);
			/*if(!deselected.equals("")) {
				filelistmodifier.setToMostRecentAfterSelected(deselected);	
			}*/
			String dir = filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","");
			String dir2 = fileName.replaceAll("[^\\\\]+\\.java","");
			if(!dir.equals(dir2)) {
				filelistmodifier.fillList(fileName);
				
			}
			
			this.fileName=fileName;
			loadComboboxes(filelistmodifier);
			filenamescombobox.setSelectedItem(getFileName(fileName));
			StoreSelectedFile storeselectedfile3 = new StoreSelectedFile();
			int caretposition=storeselectedfile3.getCaretPosition(fileName);
			if(caretposition != 0)
			scrollToCaretPosition(caretposition);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public int getLineNumber(String stringuptocaretposition) {
		int linenumber2=0;
		try {
			LineNumberReader linenumberreader=new LineNumberReader(new StringReader(stringuptocaretposition));
			while(linenumberreader.readLine() != null) {
				linenumber2=linenumberreader.getLineNumber();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return linenumber2;
	}
	public Process compileFromMSDOS(String fileName,String classpath) {
		if(checkbox.isSelected()) {
			return compileSourceCodeWithClasspath(fileName,classpath);
		}
		else {			
			return compileSourceCodeWithoutClasspath(fileName,classpath);
		}
	}
	public Process compileSourceCodeWithClasspath(String fileName,String classpath) {
		try {
			Runtime runtime = Runtime.getRuntime();
			return runtime.exec(new String[]{"cmd.exe","/c","javac -cp *;. "+fileName.replaceAll(".+\\\\","")},null,new File(classpath));
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}	
	public Process compileSourceCodeWithoutClasspath(String fileName,String classpath) {
		try {
			Runtime runtime = Runtime.getRuntime();
			return runtime.exec(new String[]{"cmd.exe","/c","javac "+fileName.replaceAll(".+\\\\","")},null,new File(classpath));
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public Process runJavaProgramFromMSDOS(String fileNameWithoutDotJava,String classpath) {
		try {
			Runtime runtime = Runtime.getRuntime();
			if(checkbox.isSelected()) {		
				return runtime.exec(new String[]{"cmd","/c","start","cmd","/k","java -cp *;. "+fileNameWithoutDotJava},null,new File(classpath));
			}
			else {
				return runtime.exec(new String[]{"cmd","/c","start","cmd","/k","java "+fileNameWithoutDotJava},null,new File(classpath));
			}
		} catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	String mainclass = ""; // Need here because anonymous class calls this code.
	public void loadComboboxes(FileListModifier openfileslist) {
		if(!openfileslist.isEmpty()) {
			try {
				mainclass = "";
				if(filenamescombobox.getItemCount() > 0) {
					filenamescombobox.removeAllItems();
				}
				if(classnamescombobox.getItemCount() > 0) {
					classnamescombobox.removeAllItems();
				}
				if(combobox.getItemCount() > 0) {
					combobox.removeAllItems();
					
				}
				List<String> filenames=openfileslist.getFileList();
				for(String filename:filenames) {
					filenamescombobox.addItem(filename);
				}
				if( (new GetClassName(textarea)).getClassName().equals("Can't find class name.") ) 
					return;	
				LinkedHashMap<String,Integer> classnames = getclassmethods.getClasses();
				if(classnames == null) JOptionPane.showMessageDialog(null,"classnames 1 is null.");
				if(classnames.keySet().size() == 0) JOptionPane.showMessageDialog(null,"Classes is empty.");
				LinkedHashMapInterface<String,Integer> iterator2=new LinkedHashMapInterface<String,Integer>(classnames) {
					public void KeyAndValue(String key,Integer integer) {
						classnamescombobox.addItem(key);
						if(getclassname.isMainClass(integer)) {
							mainclass=key;
							// textarea.setCaretPosition(integer);
						}	
					}
				};						
				iterator2.iterate();
				if(mainclass.equals("")) {
					mainclass = getclassname.getClassName();
				}
				// JOptionPane.showMessageDialog(null,mainclass);		
				
				// final String classname = mainclass;
				//classnamescombobox.addItem(classname);
				// JOptionPane.showMessageDialog(iterator,classname);
				LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();
				if(classnamesandmethodnames == null) JOptionPane.showMessageDialog(null,"classnamesandmethods is null.");
				
				LinkedHashMap<String,Integer> linkedhashmap2 = classnamesandmethodnames.get(mainclass);
				if(linkedhashmap2 == null) {
					JOptionPane.showMessageDialog(null,"linkedhashmap2 is null");
				}
				else {
					LinkedHashMapInterface<String,Integer> iterator4=new LinkedHashMapInterface<String,Integer>(linkedhashmap2);
					Integer integer2 =iterator4.getFirstValue();
					if(integer2 != null) {
						JScrollBar verticalscrollbar=scrollpane.getVerticalScrollBar();
						
						/*
						verticalscrollbar.setValue(textarea.getText().length()-1);
						textarea.setCaretPosition(textarea.getText().length()-1);
						verticalscrollbar.setValue(0);
						textarea.setCaretPosition(0);
						textarea.requestFocus();
						
						verticalscrollbar.setValue(integer2);
						*/
						//DefaultCaret defaultcaret = (DefaultCaret)textarea.getCaret();
						//defaultcaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);										
						/*textarea.setCaretPosition(textarea.getText().length());
						textarea.setCaretPosition(integer2);
						*/
						
						/*textarea.setCaretPosition(textarea.getText().length());
						scrollToCaretPosition(textarea.getText().length());*/
						//textarea.setCaretPosition(integer2);
						// JOptionPane.showMessageDialog(null,integer2+"");
						scrollToCaretPosition(integer2);
						
						/*
						JOptionPane.showMessageDialog(null,"Opened new file.");
						
						verticalscrollbar.setValue(integer2);
						
						
						scrollToCaretPosition(integer2);
											
						JOptionPane.showMessageDialog(null,verticalscrollbar.getValue()+" "+integer2);
						*/
					}
				}
	
				LinkedHashMapInterface<String,LinkedHashMap<String,Integer>> iterator=new LinkedHashMapInterface<String,LinkedHashMap<String,Integer>>(classnamesandmethodnames) {		
					public void KeyAndValue(String key,LinkedHashMap<String,Integer> value) {
						if(mainclass.equals(key)) {
							Set<String> method_names=value.keySet();
							for(String method_name:method_names) {
								combobox.addItem(method_name);
							}
						}
					}
				};		
				iterator.iterate();
				/*if(filenamescombobox.getItemCount() > 0) {
					openfileslist.
				}
				*/
				if(classnamescombobox.getItemCount() > 0)
				classnamescombobox.setSelectedItem(mainclass);
				if(combobox.getItemCount() > 0)
				combobox.setSelectedItem(0);
			}
			catch(NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}		
	public List<String> apiclasses;
	public void setApiClasses() {
		if(apiclasses == null) {	
			apiclasses=new ArrayList<String>();
			for(HashMap<String,String> innerhashmap:Main.muck.links.hashmap.values()) {
				for(String classname:innerhashmap.keySet()) { // Only one value
					apiclasses.add(classname);
				}
			}
		}
	}
	public List<String> keywords;
	public void setKeywords() {
		if(keywords ==null) {
			/*keywords=new ArrayList<String>();
			SourceVersion sourceversion=SourceVersion.latest();
			Pattern pattern = Pattern.compile("([a-zA-Z0-9]+)");
			Matcher matcher=pattern.matcher(textarea.getText());
			while(matcher.find()) {
				if(sourceversion.isKeyword(matcher.group(1))) {
					keywords.add(matcher.group(1));
				}
			}
			*/
			keywords=new ArrayList<>(Arrays.asList("enum","_","public","protected","private","abstract","static",
			"final","transient","volatile","synchronized","native","class",
			"interface","extends","package","throws","implements",
			"boolean","byte","char","short","int","long","float","double",
			"void","if","else","try","catch","finally","do","while","for","continue",
			"switch","case","default","break","throw","return",
			"this","instanceof","goto","const",
			"null","super","new","import","true","false"));
		}
	}
}
class Expandable {
	public Main main;
	public JScrollPane jscrollpane;
	public JTree jtree = new JTree();
	Expandable(Main main) {
		this.main = main;
		JFrame frame = new JFrame();
		frame.setSize(200,main.frame.getHeight());
		frame.setLocation(0,0);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jscrollpane = new JScrollPane();
		if(!main.filelistmodifier.isEmpty()) {
			open();
		}
		frame.add(jscrollpane);
		frame.setVisible(true);
	}
	public void mySingleClick(int selRow,TreePath selPath) {
		main.open(main.filelistmodifier.original.get(selRow));	
	}
	public void myDoubleClick(int selRow,TreePath selPath) {
		main.open(main.filelistmodifier.original.get(selRow));	
	}
	public void open() {
		jtree = new JTree(main.filelistmodifier.original.toArray(new Object[main.filelistmodifier.original.size()]));
		jscrollpane.setViewportView(jtree);
		setListener();
	}
	public void setListener() {
		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
		         		int selRow = jtree.getRowForLocation(e.getX(), e.getY());
		        		TreePath selPath = jtree.getPathForLocation(e.getX(), e.getY());
		        		if(selRow != -1) {
		             		if(e.getClickCount() == 1) {
		                			mySingleClick(selRow, selPath);
		             		}
		             		else if(e.getClickCount() == 2) {
		                 			myDoubleClick(selRow, selPath);
		             		}
		         		}
		    	 }
 		};
 		jtree.addMouseListener(ml);
 	}
}
class OpenDefaultContent {
	private String fileName = "";
	private String lines = "";
	OpenDefaultContent() {
		try {
		StoreSelectedFile storeselectedfile = new StoreSelectedFile();
		this.fileName = storeselectedfile.get();
		File file2 = new File(fileName); 
		if(!file2.exists()) {
			JOptionPane.showMessageDialog(null,"No previous file found.");
			this.fileName = "";
			return;
		}
		
		FileReader filereader = new FileReader(file2);
		CharBuffer chars = CharBuffer.allocate((int)file2.length());
		filereader.read(chars);
		filereader.close();
		chars.flip();
		lines=chars.toString();
		
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	OpenDefaultContent(String fileName3) {
		try {
		File file2 = new File(fileName3);
		FileReader filereader = new FileReader(file2);
		char[] chars = new char[(int)file2.length()];
		filereader.read(chars);
		filereader.close();
		lines=new String(chars);
		fileName = fileName3;
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public String getFileName() {
		return fileName;
	}
	public String getString() {
		return lines;
	}
}
class SaveActionListener implements ActionListener {
	private Main main;
	public SaveActionListener(Main main) {
		this.main = main;
		
	}
	public void actionPerformed(ActionEvent ev) {
		try {
			String text = main.textarea.getText();
			if(!main.fileName.equals("")) {						
				PrintWriter output = new PrintWriter(main.fileName);
				output.print(text);
				output.close();
				main.frame.setTitle(main.fileName.replaceAll(".+\\\\",""));
			}
			else {
				JFileChooser fileChooser = new JFileChooser(".");
				FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Save as .java","java");
				fileChooser.setFileFilter(filenameextensionfilter);
				fileChooser.setDialogTitle("Choose a file name for your typed code.");
				int status =fileChooser.showSaveDialog(main.frame);
				if(status == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					main.fileName = file.getPath();
					main.fileName=main.addDotJava(main.fileName);
					//JOptionPane.showMessageDialog(null,main.fileName);
					PrintWriter output = new PrintWriter(main.fileName);
					output.print(text);
					output.close();
					main.frame.setTitle(main.fileName.replaceAll(".+\\\\",""));
				}
			}
		}catch (FileNotFoundException ex) {
			System.out.println(ex);
		}
	}
}
class OpenActionListener implements ActionListener {
	private Main main;
	public OpenActionListener(Main main) {
		this.main = main;
	}
	public void actionPerformed(ActionEvent ev) {
		String dir = main.fileName;
		if(!dir.equals("")) {
			dir = dir.replaceAll("[^\\\\]+\\.java","");
		}
		else {
			dir = System.getProperty("user.home");
		}
		JFileChooser filechooser = new JFileChooser(new File(dir));
		FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Open .java","java");
		filechooser.setFileFilter(filenameextensionfilter);
		int result = filechooser.showOpenDialog(main.frame);
		if(result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = filechooser.getSelectedFile();
			String original = main.fileName;
			main.fileName = selectedFile.getPath();
			int selectedtab = main.tabbedpane.getSelectedIndex();
			main.fileNames.set(selectedtab,main.fileName);
			main.tabbedpane.setTitleAt(selectedtab,main.getFileName(main.fileName));
			if(original.equals(""))
				main.git = new Git(main.fileName);
			main.open(main.getFileName(main.fileName));
		}
	}
}

class CurlyBraceKeyListener implements KeyListener {
	public Main main;
	protected Tracker tracker;
	public RenameVariable renamevariable;
	public CurlyBraceKeyListener(Main main) {
		this.main = main;
		selectedlines = new SelectedLines(main);
		tracker = new Tracker(main);
		renamevariable=new RenameVariable(main);
		positiontracker= new PositionTracker(main.textarea);
		autokeylistener = new AutoKeyListener(main);
	}
	public boolean isEndOfFile() {
		return main.textarea.getCaretPosition() == main.textarea.getText().length();
	}
	private boolean isShift = false;
	private boolean isControlDown = false;
	private boolean isTab = false;
	private SelectedLines selectedlines;
	public PositionTracker positiontracker;
	public static VariableSuggestionBoxSelected variablesuggestionboxselected= new VariableSuggestionBoxSelected();	
	public AutoKeyListener autokeylistener;	
	public void keyPressed(KeyEvent ev) {
		positiontracker.startTracking();		
		switch(ev.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				isShift = true;
			break;
			case KeyEvent.VK_TAB:
				isTab = true;
				if(isShift) {
					selectedlines.ShiftTab();
				}
				else {
					selectedlines.TabMultipleLines();
				}
			break;
			default:
				renamevariable.track();
			break;	
		}		
		if(ev.getKeyChar() =='.') {
			String text = main.textarea.getText();
			Middle middle = new Middle(main.textarea);
			int caretposition = main.textarea.getCaretPosition();
			//String currentline=middle.getWholeLine2(caretposition);
			String currentline = middle.getCurrentLine();
			Pattern pattern3=Pattern.compile("\\s*([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)$");
			Matcher matcher=pattern3.matcher(currentline);
			if(matcher.find()) {
				String editedline = matcher.group(1);
				String[] properties = editedline.split("\\.");
				String first = properties[0];
				String classname=getClassName(first,text);
				Class<?> property = getClassQuestionMark(classname,text);
				for(int i = 1; i < properties.length; i++) {
					//Member[] methodsandproperties=getAllPropertyAndMethods(property);
					Object[] methodsandproperties=getAllPropertyAndMethodsAndEnums(property);
					String last=properties[i];
					escapey:for(Object member:methodsandproperties) {
						if(member instanceof Method) {
							String name = ((Method)member).getName();
							if(name.contains("$")) {
								name=name.replaceAll(".+\\$","");
							}
							if(last.equals(name)) {
								property=(Class<?>)member;
								break escapey;
							}
						}
						else if(member instanceof Field) {						
							String name=((Member)member).getName();
							if(name.contains("$")) {
								name=name.replaceAll(".+\\$","");
							}
							if(last.equals(name)) {
								property=((Field)member).getType();
								break escapey;
							}
						}
						else if(member instanceof Class<?> && ((Class<?>)member).isEnum()) {						
							String name=((Class<?>)member).getName();
							if(name.contains("$")) {
								name=name.replaceAll(".+\\$","");
							}
							if(last.equals(name)) {
								property=(Class<?>)member;
								break escapey;
							}
						}
						else if(member instanceof Class<?> && ((Class<?>)member).isInterface() ) { // Is a Enum						
							String name=((Class<?>)member).getName();
							if(name.contains("$")) {
								name=name.replaceAll(".+\\$","");
							}
							if(last.equals(name)) {
								property=(Class<?>)member;
								break escapey;
							}
						}
						else { // if(member instanceof Class<?> && ((Class<?>)member).isLocalClass()) {
							String name=((Class<?>)member).getName();
							if(name.contains("$")) {
								name=name.replaceAll(".+\\$","");
							}
							if(last.equals(name)) {
								property=(Class<?>)member;
								break escapey;
							}
						}
					}
				}
				Popup(property,caretposition);
			}
		}
		/**
		** This is a variable suggestion box not a method
		** suggestionbox.
		*/
		int caretposition = main.textarea.getCaretPosition();
		//JOptionPane.showMessageDialog(null,caretposition+"");
		
		Middle middle = new Middle(main.textarea);
		String line=middle.getCurrentLine();
		line=line+ev.getKeyChar();
		
		Pattern pattern=Pattern.compile("([a-z0-9A-Z]+)\\z");
		Matcher matcher=pattern.matcher(line);
		if(matcher.find()) {
			String variablename = matcher.group(1);
			if(autokeylistener.search(variablename)) { // if Variable name exists in this opened file
				autokeylistener.open(variablename,caretposition);
			}
		}
		if(ev.isControlDown()) {
			isControlDown = true;
		}
	}
	public boolean is_content_update = false;
	public void keyReleased(KeyEvent ev) {
		if(!ev.isControlDown() && ( ev.getKeyCode() != KeyEvent.VK_Z || ev.getKeyCode() != KeyEvent.VK_Y) ) {
			positiontracker.add();
		}
		int value = ev.getKeyCode();
		switch(value) {
			case KeyEvent.VK_Z:
				if(ev.isControlDown()) {
					Selection selection=positiontracker.previous();
					main.textarea.setText(selection.wholetext);
					main.textarea.setCaretPosition(selection.cursor);
				}
				break;
			case KeyEvent.VK_Y:
				if(ev.isControlDown()) {
					Selection selection=positiontracker.next();
					main.textarea.setText(selection.wholetext);
					main.textarea.setCaretPosition(selection.cursor);
				}
			break;
			case KeyEvent.VK_SHIFT:
				isShift = false;
				break;
			case KeyEvent.VK_TAB:
				isTab = false;
				if(!isShift)
					selectedlines.TabMultipleLinesOutput();
				else selectedlines.ShiftTabOutput();
			break;
			default:
				renamevariable.replace();
			break;
		}
		
		if(!main.go_to_line_is_executed) {
			is_content_update = true;
			Content content = new Content(main.textarea);
			char character = ev.getKeyChar();
			if(character == '\n') {
				content.NewLineCharacter();
			}
			else if(character == ';') {
				content.Semicolon();
			}			
			else if(character == '{') {
				content.LeftCurlyBrace();
			}
	 
			else if(character == '}') {
				content.RightCurlyBrace();
			}
			is_content_update = false;
		}
		else {
			main.go_to_line_is_executed = false;
		}
		if(isControlDown && ev.getKeyCode() == KeyEvent.VK_D) {
			int oldcaretposition = main.textarea.getCaretPosition();
			Middle middle = new Middle(main.textarea);
			
			String[] first_half_lines = middle.first_half_lines;
			String[] first_half = new String[first_half_lines.length-1];
			for(int i = 0; i < first_half.length; i++) {
				first_half[i] = first_half_lines[i];
			}
			String first_half_string=String.join("\n",first_half);
			
			String secondhalf = middle.second;
			String[] secondhalflines = secondhalf.split("\n");
			String[] array = new String[secondhalflines.length-1];
			for(int i = 0; i < array.length; i++) {
				array[i] = secondhalflines[i+1];
			}
			String secondhalfstring = String.join("\n",array);
			
			main.textarea.setText(first_half_string+"\n"+secondhalfstring);
			main.textarea.setCaretPosition(oldcaretposition);
		}
		else if(isControlDown && ev.getKeyCode() == KeyEvent.VK_R) {
			try {
				String lines = Files.readString(Paths.get(main.fileName),StandardCharsets.UTF_8);
				int caretposition = main.textarea.getCaretPosition();
				main.textarea.setText(lines);
				main.textarea.setCaretPosition(caretposition);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if(isControlDown) {
			isControlDown = false;
		}
	}
	public String getClassName(String variablenameorclassname,String text) {
		// Below if variable name
		Pattern pattern4= Pattern.compile("(\\s*(\\b(public|protected|private)\\b)?\\s*(\\b(static)\\b)?\\s*([a-zA-Z<>0-9,?]+)\\s+("+variablenameorclassname+")(?=\\s*=|;|:|\\)))");	
		Matcher matcher2=pattern4.matcher(text);
		if(matcher2.find()) {
			String classname=matcher2.group(6);
			return classname;
		}
		else { // If static class name
			return variablenameorclassname;
		}
	}
	public Class<?> getClassQuestionMark(String classname,String text) {
		try {
			if(classname.contains("<") && classname.contains(">")) {
				classname = classname.replaceAll("<.+>","");
			}
			String dir=main.fileName.replaceAll("[^\\\\]+\\.java","");
			ClassInFolderClassLoader classloader = new ClassInFolderClassLoader(dir);			
			Class<?> classquestionmark=classloader.loadClass(dir+classname);
			// Class<?> classquestionmark=Class.forName();
			return classquestionmark;
		} catch(ClassNotFoundException ex3) {
			String[] lines = text.split("\n");
			try {
				for(int i = 0; i < lines.length; i++) {
					String line = lines[i];
					String output = "import (.+)\\."+classname+";";
					Pattern findimport=Pattern.compile(output);
					Matcher matcher12=findimport.matcher(line);
					if(matcher12.find()) {
						return Class.forName(matcher12.group(1)+"."+classname);
					}	
				}
				List<String> imports =Main.muck.links.getImport(classname);
				for(String importy:imports) {
					String select="import "+importy.replaceAll(classname+"$","")+"*;";
					if(text.contains(select))
						return Class.forName(importy);
				}
				return Class.forName(imports.get(0));
			}
			catch(ClassNotFoundException ex4) {
				ex4.printStackTrace();
				try {
					return Class.forName("java.lang.Object");
				}
				catch(ClassNotFoundException ex5) {
					ex5.printStackTrace();
					return null;
				}
			}
		}
	}
	public static SuggestionBoxSelected suggestionboxselected = new SuggestionBoxSelected();
	public static int red = 94;
	public static int green = 167;
	public static int blue = 236;		
	public void Popup(Class<?> classquestionmark,int caretposition) {
		try {
			JFrame suggestionbox = new JFrame();			
			suggestionbox.setTitle(classquestionmark.getName());
			suggestionbox.setSize(100,500);
			JPanel panelgridlayout = new JPanel();
			//Member[] unorderedmethods=getAllPropertyAndMethods(classquestionmark);
			Object[] unorderedmethods = getAllPropertyAndMethodsAndEnums(classquestionmark);
			for(Object object:unorderedmethods) {
				if(object instanceof Enum) {
					System.out.println(((Enum)object).name());
				}
			}
			System.out.println();
			System.out.println();
			final Object[] methods = suggestionboxselected.Reordered(unorderedmethods,classquestionmark);
			for(Object object:methods) {
				if(object instanceof Enum) {
					System.out.println(((Enum)object).name());
				}
			}
			GridLayout gridlayout=new GridLayout(methods.length+1,1);
			panelgridlayout.setLayout(gridlayout);
			JTextField search_textfield=new JTextField();
			panelgridlayout.add(search_textfield);
			JLabel[] labels = new JLabel[methods.length];
			for(int i = 0; i < methods.length; i++) {
				if(methods[i] instanceof Method) {
					String name=((Method)methods[i]).getName();
					if(name.contains("$")) {
						name=name.replaceAll(".+\\$","");
					}
					labels[i] = new JLabel(name);
					panelgridlayout.add(labels[i]);
				}
				else if(methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isEnum()) {
					String name=((Class<?>)methods[i]).getName();
					//String name=((Enum)methods[i]).name();
					if(name.contains("$")) {
						name=name.replaceAll(".+\\$","");
					}
					labels[i] = new JLabel(name);
					panelgridlayout.add(labels[i]);
				}
				else if( methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isInterface() ) {
					String name=((Class<?>)methods[i]).getName();
					if(name.contains("$")) {
						name=name.replaceAll(".+\\$","");
					}
					labels[i] = new JLabel(name);
					panelgridlayout.add(labels[i]);
				}
				else if(methods[i] instanceof Member) {
					String name=((Member)methods[i]).getName();
					if(name.contains("$")) {
						name=name.replaceAll(".+\\$","");
					}
					labels[i] = new JLabel(name);
					panelgridlayout.add(labels[i]);
				}
				else { // if(methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isLocalClass()) {
					String name= methods[i].toString();
					if(name.contains("$")) {
						name=name.replaceAll(".+\\$","");
					}
					labels[i] = new JLabel(name);
					panelgridlayout.add(labels[i]);
				}
			}
			JScrollPane scrollpane = new JScrollPane(panelgridlayout);
			
			labels[0].setOpaque(true);
			labels[0].setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
			KeyListener keylistener = new KeyListener() {
				LiveIterator<JLabel> liveiterator = new LiveIterator<JLabel>(labels);
				int selected_index = 0;
				@Override
				public void keyPressed(KeyEvent keyevent) {
					if(keyevent.getKeyCode() == KeyEvent.VK_ESCAPE) {
						suggestionbox.dispose();
					}
					else if(keyevent.getKeyCode() == KeyEvent.VK_DOWN) {
						labels[selected_index].setOpaque(false);
						labels[selected_index].setBackground(new JLabel().getBackground());
						panelgridlayout.validate();
						panelgridlayout.repaint();
						int live_index = liveiterator.indexOf(labels[selected_index]);						
						if( live_index < (liveiterator.list.size()-1) ) {
							live_index++;
							JLabel selected_label=liveiterator.list.get(live_index);
							selected_label.setOpaque(true);
							selected_label.setBackground(new Color(red,green,blue));
							panelgridlayout.validate();
							panelgridlayout.repaint();
							
							label3:for(int i = 0; i < labels.length; i++) {
								if(selected_label.equals(labels[i])) {
									selected_index = i;
									break label3;
								}
							}
						}
					}
					else if(keyevent.getKeyCode() == KeyEvent.VK_UP) {
						labels[selected_index].setOpaque(false);
						labels[selected_index].setBackground(new JLabel().getBackground());
						panelgridlayout.validate();
						panelgridlayout.repaint();
						int live_index = liveiterator.indexOf(labels[selected_index]);
						if(live_index > 0) {
							live_index--;
							JLabel selected_label=liveiterator.list.get(live_index);
							selected_label.setOpaque(true);
							selected_label.setBackground(new Color(red,green,blue));
							panelgridlayout.validate();
							panelgridlayout.repaint();
							
							label4:for(int i = 0; i < labels.length; i++) {
								if(selected_label.equals(labels[i])) {
									selected_index = i;
									break label4;
								}
							}
						}
					}
				}
				@Override
				public void keyReleased(KeyEvent keyevent) {
					if(keyevent.getKeyCode() == KeyEvent.VK_ENTER) {			
						suggestionbox.dispose();
						String text = main.textarea.getText();
						// String selected = search_textfield.getText().trim();
						JLabel selected_label2 =labels[selected_index];
						String selected = selected_label2.getText();
						suggestionboxselected.Save(classquestionmark.getSimpleName(),selected);
						String methodorproperty = "";
						breaky:for(int i = 0; i < labels.length; i++) {
							if(labels[i] == null) {
								JOptionPane.showMessageDialog(null,"labels[i] is null");
							}
							if(selected_label2 == null) {
								JOptionPane.showMessageDialog(null,"selected_label2 is null");
							}
							if(labels[i].equals(selected_label2)) {
								if(methods[i] instanceof Method) {
									methodorproperty = "(";
									if(((Method)methods[i]).getParameterCount() > 0) {
										Parameter[] parametertypes=((Method)methods[i]).getParameters();
										String[] variabletypes= new String[parametertypes.length];
										for(int j = 0; j < parametertypes.length; j++) {
											variabletypes[j]= parametertypes[j].getType()+" "+parametertypes[j].getName();
										}
										methodorproperty+=String.join(",",variabletypes);
									}
									methodorproperty+=")";
									break breaky;
								}
							}
						}
						String firsthalf=text.substring(0,caretposition)+"."+selected+methodorproperty;
						String second =text.substring(caretposition+1,text.length());
						main.textarea.setText(firsthalf+second);
						main.textarea.setCaretPosition(caretposition+1+selected.length()+methodorproperty.length());
					}
					else if(keyevent.getKeyCode() != KeyEvent.VK_ENTER && keyevent.getKeyCode() != KeyEvent.VK_DOWN && keyevent.getKeyCode() != KeyEvent.VK_UP) {
						liveiterator.reset();
						while(liveiterator.hasNext()) {
							JLabel label = liveiterator.next();
							panelgridlayout.remove(label);
						}
						liveiterator = new LiveIterator<JLabel>(labels);	
						String methodname=search_textfield.getText();	
						for(JLabel label:labels) {
							if( ! (label.getText().toLowerCase().startsWith(methodname.toLowerCase())) ) {
								liveiterator.remove(label);
							}
						}
						
						gridlayout.setRows(liveiterator.list.size()+1);
						liveiterator.reset();
						while(liveiterator.hasNext()) {
							JLabel label = liveiterator.next();
							panelgridlayout.add(label);
						}
						panelgridlayout.validate();
						panelgridlayout.repaint();
						suggestionbox.pack();	
						JLabel selected_label = labels[selected_index];
						if(!liveiterator.contains(selected_label)) { // Selected JLabel no longer in list.
							selected_label.setOpaque(false);
							selected_label.setBackground(new JLabel().getBackground());
							
							if(liveiterator.list.size() != 0) {
								JLabel label=liveiterator.list.get(0);
								labelly2:for(int i = 0; i < labels.length; i++) {
									if(label.equals(labels[i])) {
										selected_index=i;
										break labelly2;
									}
								}
								label.setOpaque(true);
								label.setBackground(new Color(red,green,blue));
							}
						}
					}
				}
				@Override
				public void keyTyped(KeyEvent ev) { }
			};
			search_textfield.addKeyListener(keylistener);
			//methodscombobox.getEditor().getEditorComponent().addKeyListener(keylistener);
			suggestionbox.add(scrollpane);
			Rectangle2D rectanglecoords=main.textarea.modelToView2D(caretposition);
			Point screencoordinates= new Point((int)rectanglecoords.getX(),(int)rectanglecoords.getY());
			SwingUtilities.convertPointToScreen(screencoordinates,main.textarea);
			suggestionbox.setLocation(screencoordinates);
			//suggestionbox.pack();
			suggestionbox.setVisible(true);
		}
		catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	public Member[] getAllPropertyAndMethods(String classname,String text) {
		return getAllPropertyAndMethods(getClassQuestionMark(classname,text));
	}
	public Member[] getAllPropertyAndMethods(Class<?> classquestionmark) {
		Member[] methods4=classquestionmark.getDeclaredMethods();
		List<Class<?>> ancestors=getAncestorsForClassQuestionMark(classquestionmark);
		methods4=addAncestorsToMethods(methods4,ancestors);
		Member[] properties = classquestionmark.getDeclaredFields();
		Member[] properties2= classquestionmark.getFields();
		Member[] members= addMembersToMembers(methods4,properties);
		return addMembersToMembers(members,properties2);
	}
	public Object[] getAllPropertyAndMethodsAndEnums(Class<?> classquestionmark) {
		if(classquestionmark.isEnum()) {
			List<Object> list = new ArrayList<Object>();
			Field[] fields= classquestionmark.getDeclaredFields();
			for(Field field : fields) {
				if(field.isEnumConstant()) {
					field.setAccessible(true);
					list.add(field);
				}
			}
			return list.toArray(new Object[list.size()]);
		}
		else {		
			Member[] propertiesandmethods=getAllPropertyAndMethods(classquestionmark);
			Class<?>[] enums=classquestionmark.getDeclaredClasses();
			for(Class<?> enum1:enums) {
				if(enum1.isEnum()) {
					Field[] fields= enum1.getDeclaredFields();
					for(Field field : fields) {
						if(field.isEnumConstant()) {
							field.setAccessible(true);
						}
					}
				}
			}
			//JOptionPane.showMessageDialog(null,(enums==null)+" is length");
			int amount_of_enums = 0;
			if(enums != null) {
				amount_of_enums = enums.length;
			}
			Object[] propertiesandmethodsandenums=new Object[propertiesandmethods.length+amount_of_enums];
			for(int i = 0; i < propertiesandmethods.length; i++) {
				propertiesandmethodsandenums[i] = propertiesandmethods[i];
			}
			for(int i = 0; i < amount_of_enums; i++) {
				propertiesandmethodsandenums[propertiesandmethods.length+i]=enums[i];
			}
			return propertiesandmethodsandenums;
		}
	}
	public List<Class<?>> getAncestorsForClassQuestionMark(Class<?> classquestionmark) {
		List<Class<?>> list = new ArrayList<Class<?>>();
		Class<?> superclass=classquestionmark.getSuperclass();
		while(superclass != null) {
			list.add(superclass);
			superclass = superclass.getSuperclass();
		}
		return list;
	}
	public Member[] addAncestorsToMethods(Member[] methods,List<Class<?>> ancestors)
	{
		List<Member> extendmethods = new ArrayList<Member>();
		for(Member method:methods) {
			extendmethods.add(method);
		}
		for(Class<?> classquestionmark:ancestors) {
			Member[] methods2=classquestionmark.getDeclaredMethods();
			for(Member method:methods2) {
				extendmethods.add(method);
			}
		}
		Member[] methods3 = new Member[extendmethods.size()];
		for(int i = 0; i < methods3.length; i++) {
			methods3[i] = extendmethods.get(i);
		}
		return methods3;
	}
	public Member[] addMembersToMembers(Member[] members,Member[] methods2) {
		List<Member> methodsandproperties=  new ArrayList<Member>();
		for(Member method:members) {
			methodsandproperties.add(method);
		}
		for(Member method2:methods2) {
			methodsandproperties.add(method2);
		}
		Member[] methods3 = new Member[methodsandproperties.size()];
		for(int i = 0; i < methods3.length; i++) {
			methods3[i] = methodsandproperties.get(i);
		}
		return methods3;
	}
	public void keyTyped(KeyEvent ev) {
		/*switch(evetKeyCode()) {
			case KeyEvent.VK_TAB:
				if(!isShift)
					selectedlines.TabMultipleLinesOutput();
			break;
		}*/
	}	
}
/*
** This is a Variable Automatic Suggestion Box.
*/
class AutoKeyListener {
	public List<String> data = new ArrayList<String>();
	private JPanel panelgridlayout;
	private Main main;
	private JFrame suggestionbox;
	private JTextField search_textfield;
	private GridLayout gridlayout;
	public AutoKeyListener(Main main) {
		this.main = main;
	}
	private String variablename;
	public int caretposition;
	public void open(String variablename,int caretposition) {
		run(variablename,caretposition);
	}
	public void run(String variablename,int caretposition) {
		this.variablename = variablename;
		this.caretposition = caretposition;
		setLayout();
		setListeners();
		fillComboBox();
		suggestionbox.setVisible(true);
	}
	public void setLayout() {
		suggestionbox = new JFrame();			
		suggestionbox.setTitle("Variable name suggestion box");
		suggestionbox.setSize(100,500);
		panelgridlayout = new JPanel();
		
		//GridLayout gridlayout=new GridLayout(variablenames2.size()+1,1);
		gridlayout=new GridLayout(1,1);
		panelgridlayout.setLayout(gridlayout);
		search_textfield=new JTextField();
		search_textfield.setText(variablename.trim());
		panelgridlayout.add(search_textfield);
		JScrollPane scrollpane = new JScrollPane(panelgridlayout);
		
		//methodscombobox.getEditor().getEditorComponent().addKeyListener(keylistener);
		suggestionbox.add(scrollpane);
		try {
			Rectangle2D rectanglecoords=main.textarea.modelToView2D(caretposition);
			Point screencoordinates= new Point((int)rectanglecoords.getX(),(int)rectanglecoords.getY());
			SwingUtilities.convertPointToScreen(screencoordinates,main.textarea);
			suggestionbox.setLocation(screencoordinates);
			//suggestionbox.pack();
			//suggestionbox.setVisible(true);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	public void setListeners() {
		suggestionbox.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				EnterText();
			}
		});
		search_textfield.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent keyevent) {
				if(keyevent.getKeyCode() == KeyEvent.VK_DOWN) {
					List<JLabel> labels=getLabels();
					JLabel selected_label=getSelected();
					int selected_index = 0;
					for(int i = 0; i < labels.size(); i++) {
						if(selected_label.hashCode() == labels.get(i).hashCode()) {
							selected_index = i;
							break;
						}
					}
					selected_index++;
					if(selected_index < labels.size()) {
						// Turn off highlighted
						selected_label.setOpaque(false);
						selected_label.setBackground(new JLabel().getBackground());
						selected_label=labels.get(selected_index);
						// Make highlighted
						selected_label.setOpaque(true);
						selected_label.setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
					}
				}
				else if(keyevent.getKeyCode() == KeyEvent.VK_UP) {
					List<JLabel> labels=getLabels();
					JLabel selected_label=getSelected();
					int selected_index = 0;
					for(int i = 0; i < labels.size(); i++) {
						if(selected_label.hashCode() == labels.get(i).hashCode()) {
							selected_index = i;
							break;
						}
					}
					selected_index--;
					if(selected_index > -1) {
						// Turn off highlighted
						selected_label.setOpaque(false);
						selected_label.setBackground(new JLabel().getBackground());
						selected_label=labels.get(selected_index);
						// Make highlighted
						selected_label.setOpaque(true);
						selected_label.setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
					}
				}
				else if(keyevent.getKeyCode() == KeyEvent.VK_ESCAPE) {
					EnterText();
					suggestionbox.dispose();
				}
			}
			@Override
			public void keyReleased(KeyEvent keyevent) {
				if(keyevent.getKeyCode() == KeyEvent.VK_ENTER) {			
					String text = main.textarea.getText();	
					String selected = search_textfield.getText().trim();
					JLabel selected_label2 =getSelected();
					if(selected_label2.getText().startsWith(selected)) {
						CurlyBraceKeyListener.variablesuggestionboxselected.Save(selected,selected_label2.getText());
						EnterText(selected_label2.getText());
					}
					else {
						EnterText();
					}
				}
				else if(keyevent.getKeyCode() != KeyEvent.VK_ENTER && keyevent.getKeyCode() != KeyEvent.VK_DOWN && keyevent.getKeyCode() != KeyEvent.VK_UP) {
					String input = search_textfield.getText();
					if(search(input)) {
						fillComboBox();
					}
					else {
						EnterText(input);
						if(keyevent.getKeyChar()=='.') {
							main.textarea.setCaretPosition(main.textarea.getCaretPosition()-1);
							//main.curlybracekeylistener.keyPressed(keyevent);
							KeyEvent keyevent2 = new KeyEvent(main.textarea,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,keyevent.getKeyCode(),'.');
							main.textarea.dispatchEvent(keyevent2);
						}
					}
				}
			}
			@Override
			public void keyTyped(KeyEvent ke) {
				
			}
		});
		panelgridlayout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				Component label=panelgridlayout.getComponentAt(me.getPoint());
				if(label instanceof JLabel) {
					AutoKeyListener.this.EnterText(((JLabel)label).getText());
				}
			}
		});
	}
	public String getInput() {
		return search_textfield.getText().trim();
	}
	public void fillData() {
		List<String> variablenames = new ArrayList<String>();
		String text = main.textarea.getText();
		Pattern pattern2=Pattern.compile("((\\s+\\b(public|protected|private)\\b)?\\s+[a-zA-Z<>]+\\s+([a-zA-Z0-9_]+)(?=\\s*=|;))");
		Matcher matcher2=pattern2.matcher(text.substring(0,caretposition));
		while(matcher2.find()) {
			variablenames.add(0,matcher2.group(4));			
		}
		Matcher matcher3=pattern2.matcher(text.substring(caretposition,text.length()));
		while(matcher3.find()) {
			variablenames.add(matcher3.group(4));
		}
		
		data=variablenames;
	}
	public void fillComboBox() {
		List<String> variablenames2;
		String input=search_textfield.getText().trim();
		if(data.size() > 0) {
			variablenames2=data;
			
			removeData();
			if(variablenames2.size() > 0) {
				variablenames2=CurlyBraceKeyListener.variablesuggestionboxselected.ReorderedStrings(variablenames2,input);
				gridlayout.setRows(1+variablenames2.size());
				for(int i = 0; i < variablenames2.size(); i++) {
					JLabel label = new JLabel(variablenames2.get(i));
					if(i == 0) {
						label.setOpaque(true);
						label.setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
					}
					panelgridlayout.add(label);
				}
				panelgridlayout.validate();
				panelgridlayout.repaint();
				suggestionbox.pack();	
			}
		}
	}

	public void removeData() {
		Component[] components=panelgridlayout.getComponents();
		for(int i = 0; i < components.length; i++) {
			if(components[i] instanceof JLabel) {
				JLabel jlabel=(JLabel)components[i];
				panelgridlayout.remove(jlabel);
			}
		}
		gridlayout.setRows(1);
	}
	public void EnterText() {
		String input=search_textfield.getText().trim();
		EnterText(input);
	}
	public void EnterText(String input) {
			String text = main.textarea.getText();
			//JOptionPane.showMessageDialog(null,caretposition+"");
	
			String first=text.substring(0,caretposition);
			//variablename=variablename.substring(variablename.length()-1,variablename.length());
			first=first.substring(0,first.length()-variablename.length()+1);
			String second = text.substring(caretposition+1,text.length());
			/*System.out.println("Start");
			System.out.println(second);
			System.out.println("End");
			*/
			main.textarea.setText(first+input+second);
			main.textarea.setCaretPosition(first.length()+input.length());
			suggestionbox.dispose();
	}
	public JLabel getSelected() {
		for(Component component:panelgridlayout.getComponents()) {
			if(component instanceof JLabel) {
				if(component.isOpaque()) {
					return (JLabel)component;
				}
			}
		}
		return new JLabel("select label not found");
	}
	public List<JLabel> getLabels() {
		List<JLabel> labels = new ArrayList<JLabel>();
		for(Component component:panelgridlayout.getComponents()) {
			if(component instanceof JLabel) {
				labels.add((JLabel)component);
			}
		}
		return labels;
	}
	public List<String> getAPI(String variablename) {
		// Add Java API classes
		Pattern pattern6=Pattern.compile("^[A-Z]");
		Matcher capitolpattern=pattern6.matcher(variablename);
		if(capitolpattern.find()) {
			return main.apiclasses;
		}
		else if(variablename.equals("")) {
			return main.apiclasses;
		}
		else {
			return new ArrayList<String>();
		}
	}
	public boolean search(String input) {
		
		fillData();
		List<String> variablenames2 = new ArrayList<String>();
		TreeSet<String> treeset = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String one,String two) {
				if(one.length() < two.length()) {
					return -1;
				}
				else if(one.length() > two.length()) {
					return 1;
				}
				else { // if(one.length==two.length())
					if(one.equals(two)) {
						return 0;
					}
					return one.compareTo(two);
				}
			}
		});
		if(!input.equals("")) {
			for(String variablename2:data) {
				if(variablename2.startsWith(input))
					treeset.add(variablename2);
			}
			//System.out.println("start");
			for(String apiclass:getAPI(input)) {
				//System.out.println(apiclass);
				if(apiclass.startsWith(input))
					treeset.add(apiclass);
			}
			for(String keyword:main.keywords) {
				if(keyword.startsWith(input)) {
					treeset.add(keyword);
				}
			}
			//System.out.println("end");
			variablenames2=new ArrayList<String>(treeset);
		}
		else { // if(input.equals(""))
			for(String variablename2:data) {
				treeset.add(variablename2);
			}
			for(String apiclass:getAPI(input)) {
				treeset.add(apiclass);
			}
			for(String keyword:main.keywords) {
				treeset.add(keyword);
			}
			variablenames2=new ArrayList<String>(treeset);
		}
		data = variablenames2;
		return variablenames2.size() > 0;
	}
	}