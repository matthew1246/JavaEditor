import java.awt.event.InputEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.reflect.Method;
import java.awt.Rectangle;
import javax.swing.event.ChangeEvent;
import java.awt.Point;
import java.lang.reflect.InaccessibleObjectException;
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
	public JMenuItem closetab = new JMenuItem("Close Tab");	
	public JMenuItem opennewtab = new JMenuItem("Open New Tab");
	public JMenuItem openemptynewtab = new JMenuItem("Open Empty Tab");
	public JTabbedPane tabbedpane = new JTabbedPane();
	public JPanel pluspanel = new JPanel();
	public JMenuItem generatejar;
	public JButton deprecated;	
	public static Muck muck;
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
	public MouseAdapter rightclick = new RightClick();
	public static void main(String[] args) 	{  
		Main main = new Main(new OpenDefaultContent());
	}
	/*
	** If have no default content for window
	
	*/
	public Main() {			
		setLayout();
		this.textarea = new JTextAreaGroup();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		
		Font originalFont = textarea.getFont();
		textarea.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));

		JScrollPane scrollpane2 = new JScrollPane(textarea);
		
		textarea.setTabSize(4);
		
		CurlyBraceKeyListener curlybracekeylistener = new CurlyBraceKeyListener(this);
		textarea.addKeyListener(curlybracekeylistener);
		
		addCaretListener(textarea);
		scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
			try {
				if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
					int caretposition = curlybracekeylistener.autokeylistener.position;
					Rectangle2D rectanglecoords=textarea.modelToView2D(caretposition);
					Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
					SwingUtilities.convertPointToScreen(screencoordinates,textarea);
					curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		});
		scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
			try {
				if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
					int caretposition = curlybracekeylistener.autokeylistener.position;
					Rectangle2D rectanglecoords=textarea.modelToView2D(caretposition);
					Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
					SwingUtilities.convertPointToScreen(screencoordinates,textarea);
					curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		});
		scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
			try {
				if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
					int caretposition = curlybracekeylistener.methodsuggestionbox.position;
					Rectangle2D rectanglecoords=textarea.modelToView2D(caretposition);
					Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
					SwingUtilities.convertPointToScreen(screencoordinates,textarea);
					curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		});
		scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
			try {
				if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
					int caretposition = curlybracekeylistener.methodsuggestionbox.position;
					Rectangle2D rectanglecoords=textarea.modelToView2D(caretposition);
					Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
					SwingUtilities.convertPointToScreen(screencoordinates,textarea);
					curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		});
		textarea.addMouseListener(rightclick);
		
		tabbedpane.addTab("",scrollpane2);
		tabbedpane.addTab("+",pluspanel);
		
		setListeners();
		expandable = new Expandable(this);
	}
	public FileListModifier filelistmodifier = new FileListModifier();
	public Git git;
	/*
	** If have default content for window
	*/
	public Main(OpenDefaultContent odc) {
		try {
		fileName = odc.getFileName();
		if(fileName != null && !fileName.equals("")) {
			git = new Git(fileName);
			String dir = getDirectory(fileName);
			if(msdos == null)
				msdos = new MSDOS(dir);
			else
				msdos.setFileName(dir);
		}
		setLayout();
		expandable = new Expandable(this);	
		if(fileName.equals("")) {
			JTextArea textarea2 = new JTextAreaGroup();
			textarea2.setLineWrap(true);
			textarea2.setWrapStyleWord(true);
			Font originalFont = textarea.getFont();
			textarea2.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));

			JScrollPane scrollpane2 = new JScrollPane(textarea2);
			textarea2.setTabSize(4);
			
			this.textarea=textarea2;
			
			CurlyBraceKeyListener curlybracekeylistener =new CurlyBraceKeyListener(this);
			
			textarea2.addKeyListener(curlybracekeylistener);
			
			addCaretListener(textarea2);
			scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
					try {
					if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.autokeylistener.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
				try {
					if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.autokeylistener.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
				try {
					if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.methodsuggestionbox.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
				try {
					if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.methodsuggestionbox.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			textarea2.addMouseListener(rightclick);
			
			tabbedpane.addTab(fileName,scrollpane2);
			tabbedpane.addTab("+",pluspanel);
			tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-2);
			fileNames.add("");
		}
		else { //if(!fileName.equals("")) {
			String lines = odc.getString();
			
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();	
			List<String> tabs=storeselectedfile.getTabs();
			if(tabs.size() <= 1) {
				fileNames.add(fileName);
				
				JTextArea textarea2 = new JTextAreaGroup();
				textarea2.setLineWrap(true);
				textarea2.setWrapStyleWord(true);
				Font originalFont = textarea.getFont();
				textarea2.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));
	
				JScrollPane scrollpane2 = new JScrollPane(textarea2);
				textarea2.setTabSize(4);
				
				this.textarea=textarea2;
				
				CurlyBraceKeyListener curlybracekeylistener=new CurlyBraceKeyListener(this);
				textarea2.addKeyListener(curlybracekeylistener);
				//positiontrackers.add(new PositionTracker(textarea2));
				
				addCaretListener(textarea2);
				
				scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
				try {
						if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.autokeylistener.position;
							Rectangle2D rectanglecoords=textarea.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea);
							curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
				try {
						if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.autokeylistener.position;
							Rectangle2D rectanglecoords=textarea.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea);
							curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
					try {
						if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.methodsuggestionbox.position;
							Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
							curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
					try {
						if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.methodsuggestionbox.position;
							Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
							curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				textarea2.addMouseListener(rightclick);
				
				tabbedpane.addTab(fileName,scrollpane2);
				tabbedpane.addTab("+",pluspanel);
				tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-2);
				open(getFileName(fileName));		
			}
			else { // if(tabs.size() > 1) {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {	
						for(String directoryandfilename:tabs) {
						try {
							JTextArea textarea2 = new JTextAreaGroup();
							textarea2.setLineWrap(true);
							textarea2.setWrapStyleWord(true);
							Main.this.textarea = textarea2;
							Font originalFont = textarea.getFont();
							textarea2.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));
		
							JScrollPane scrollpane2 = new JScrollPane(textarea2);
							String filename = Main.this.getFileName(directoryandfilename);
							
							if(!filename.equals("")) {
								Path path = Paths.get(directoryandfilename);
								String lines2= Files.readString(path,StandardCharsets.UTF_8);
								textarea2.setText(lines2);
							}
							textarea2.setTabSize(4);
							
							CurlyBraceKeyListener curlybracekeylistener = new CurlyBraceKeyListener(Main.this);
							textarea2.addKeyListener(curlybracekeylistener);
							//positiontrackers.add(new PositionTracker(textarea2));
							
							addCaretListener(textarea2);
							scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
							try {
									if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
										int caretposition = curlybracekeylistener.autokeylistener.position;
										Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
										Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
										SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
										curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
									}
								} catch (BadLocationException ex) {
									ex.printStackTrace();
								}
							});
							scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
							try {
									if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
										int caretposition = curlybracekeylistener.autokeylistener.position;
										Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
										Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
										SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
										curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
									}
								} catch (BadLocationException ex) {
									ex.printStackTrace();
								}
							});
							scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
								try {
									if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
										int caretposition = curlybracekeylistener.methodsuggestionbox.position;
										Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
										Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
										SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
										curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
									}
								} catch (BadLocationException ex) {
									ex.printStackTrace();
								}
							});
							scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
								try {
									if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
										int caretposition = curlybracekeylistener.methodsuggestionbox.position;
										Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
										Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
										SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
										curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
									}
								} catch (BadLocationException ex) {
									ex.printStackTrace();
								}
							});
							textarea2.addMouseListener(rightclick);
							
							tabbedpane.addTab(filename,scrollpane2);
							openLastSelectedLine(textarea2,directoryandfilename);
						}
						catch (IOException ex) {
							ex.printStackTrace();
						}
						}
					}
				});
				fileNames = tabs;
				tabbedpane.addTab("+",pluspanel);
				
				filelistmodifier = new FileListModifier();
				filelistmodifier.fillList(fileName);
				JScrollPane jscrollpane5=((JScrollPane)tabbedpane.getSelectedComponent());
				textarea=(JTextArea)jscrollpane5.getViewport().getView();
				getclassmethods = new GetClassMethods(textarea);		
				getclassname = new GetClassName(textarea);
				loadComboboxes(filelistmodifier);
				filenamescombobox.setSelectedItem(getFileName(fileName));
			}
		}
		setListeners();	
		} catch(InvocationTargetException ex) {
			ex.printStackTrace();
		} catch(InterruptedException ex) {
			ex.printStackTrace();								
		}catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		Thread thread = new Thread(new Runnable() {
			@Override	
			public void run() {
				Main.muck = new Muck();
				setFullPackageNames();		
				setSubpackages();
				setPackages();
				setApiClasses();				
				setKeywords();
				setAllClassesInFile();
				setAllClassesInFolder();
			}
		});
		thread.start();
		//openLastSelectedLine();
	}
	public void openLastSelectedLine(JTextArea textarea3,String filename) {
		StoreSelectedFile storeselectedfile = new StoreSelectedFile();
		if(filename != null && !filename.equals("")) {
			int caretposition=storeselectedfile.getCaretPosition(filename);
			scrollToCaretPosition(textarea3,caretposition);
		}
	}
	public void openLastSelectedLine() {
		StoreSelectedFile storeselectedfile = new StoreSelectedFile();
		if(fileName != null && !fileName.equals("")) {
			int caretposition=storeselectedfile.getCaretPosition(fileName);
			scrollToCaretPosition(caretposition);
		}
	}
	public String getDirectory(String filename) {
		if(filename.endsWith(".java")) {
			return filename.replaceAll("[^\\\\]+\\.java","");
		}
		else if(filename.endsWith(".jar")) {
			return filename.replaceAll("[^\\\\]+\\.jar","");
		}
		return filename;
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
	//public JScrollPane scrollpane;
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
		
		textarea = new JTextAreaGroup();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		combobox = new JComboBox<String>();
		
		Font originalFont = textarea.getFont();
		textarea.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));
		//scrollpane = new JScrollPane(textarea);
		//tabbedpane.add(getFileName(fileName),scrollpane);

		//tabbedpane.addTab("+",pluspanel);
				
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
		openemptynewtab.setAccelerator(control_t);
		
		menuitem.addActionListener(oal);
		menu.add(newitem);
		menu.add(newopenwindow);
		menu.add(menuitem);
		menu.add(opennewwindow);
		menu.add(opennewtab);
		menu.add(openemptynewtab);
		menu.add(closetab);
		JMenu recent = new JMenu("Recent Files");
		StoreSelectedFile storeselectedfile2 =new StoreSelectedFile();
		LinkedHashMap<String,Preferences> hashmap=storeselectedfile2.getBackup();
		ActionListener opennewtab = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String filename=((JMenuItem)ev.getSource()).getText();
				if(filename.startsWith("lastopened: "))
					filename=filename.replaceFirst("lastopened: ","");
				Main.this.OpenNewTab(filename);
			}
		};
		for(String filename:hashmap.keySet()) {
			if(filename.equals("lastopened"))
				filename="lastopened: "+fileName;
			JMenuItem recentmenuitem = new JMenuItem(filename);
			recentmenuitem.addActionListener(opennewtab);
			recent.add(recentmenuitem);
		}  
		menu.add(recent);
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
			if(classname != null && !classname.equals("")) {
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
	}
			
	public void updateMethodComboBox(ItemEvent ie) {
		final String classname = (String)classnamescombobox.getSelectedItem();						
		if(classname != null && !classname.equals("")) {
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
	}	
	public void scrollToCaretPosition(JTextArea textarea3,int wholedocumentindex) {
		SwingUtilities.invokeLater(new Runnable() {
		        public void run(){
		            try {
		            	if(wholedocumentindex <= (textarea3.getText().length()) ) {
					Rectangle2D viewposition=textarea3.modelToView2D(wholedocumentindex);
					Point caretposition=new Point(0,(int)viewposition.getY());
					
					JScrollPane scrollpane=((JScrollPane)tabbedpane.getSelectedComponent());
					
					scrollpane.getViewport().setViewPosition(caretposition);
					textarea3.grabFocus();
					textarea3.setCaretPosition(wholedocumentindex);
					line.setText("line number: "+getLineNumber(textarea3.getText().substring(0,wholedocumentindex))+" ");
				}
		            }
			catch (BadLocationException exception) {
				exception.printStackTrace();
			}

		        }
		    });

	}

	public void scrollToCaretPosition(int wholedocumentindex) {
		SwingUtilities.invokeLater(new Runnable() {
		        public void run(){
		            try {
		            	if(wholedocumentindex <= (textarea.getText().length()) ) {
					Rectangle2D viewposition=textarea.modelToView2D(wholedocumentindex);
					Point caretposition=new Point(0,(int)viewposition.getY());
					
					JScrollPane scrollpane=((JScrollPane)tabbedpane.getSelectedComponent());
					
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
			if(classname != null && !classname.equals("")) {
				String methodname = (String)combobox.getSelectedItem();
				if(methodname != null && !methodname.equals("")) {
					LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();
					LinkedHashMap<String,Integer> classandmethods = classnamesandmethodnames.get(classname);
					int wholedocumentindex = classandmethods.get(methodname);
					
					JScrollPane scrollpane=((JScrollPane)tabbedpane.getSelectedComponent());
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
		}
																
	}
	public void selectCode(ItemEvent ev) {
		if(ev.getStateChange() == ItemEvent.SELECTED) {
			String classname = (String)classnamescombobox.getSelectedItem();
			if(classname != null && !classname.equals("")) {
				String methodname = (String)combobox.getSelectedItem();
				if(methodname != null && !methodname.equals("")) {
					LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();			
					LinkedHashMap<String,Integer> classandmethods = classnamesandmethodnames.get(classname);
					int wholedocumentindex = classandmethods.get(methodname);
					
					JScrollPane scrollpane=((JScrollPane)tabbedpane.getSelectedComponent());
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
			
			dir=dir+selected2;
			
			if(msdos == null)
				msdos = new MSDOS(dir);
			else
				msdos.setFileName(dir);
				
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
			if(git !=null)
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
			
			if(selectedtab >= 0 && selectedtab < fileNames.size()) {
				fileNames.set(selectedtab,fileName);
			}
			else if(selectedtab == fileNames.size()) {
				fileNames.add(fileName);
			}
				
			tabbedpane.setTitleAt(selectedtab,getFileName(fileName));
			tabbedpane.repaint();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	//public CurlyBraceKeyListener curlybracekeylistener;
	public boolean go_to_line_is_executed = false;
	String deselected = "";
	public void setListeners() {	
		frame.addWindowStateListener(new java.awt.event.WindowStateListener() {
	          		public void windowStateChanged(WindowEvent e) {
	                		int state=e.getNewState();
	                		if ((state & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
	           				int caretposition=textarea.getCaretPosition();
	           				textarea.setCaretPosition(caretposition);
	           				Main.this.scrollToCaretPosition(caretposition);
	                		}
	                		else if ((state & JFrame.ICONIFIED) == JFrame.ICONIFIED) {
      	 				int caretposition=textarea.getCaretPosition();
	           				textarea.setCaretPosition(caretposition);
	           				Main.this.scrollToCaretPosition(caretposition);
   				 } else if ((state & JFrame.NORMAL) == JFrame.NORMAL) {
        					int caretposition=textarea.getCaretPosition();
	           				textarea.setCaretPosition(caretposition);
	           				Main.this.scrollToCaretPosition(caretposition);
    				}
	            	}
	        	});
		
		openemptynewtab.addActionListener( (ev2) -> {
			tabbedpane.remove(pluspanel);
			JTextArea textarea2 = new JTextAreaGroup();
			textarea2.setLineWrap(true);
			textarea2.setWrapStyleWord(true);
			Main.this.textarea = textarea2;
			Font originalFont = textarea.getFont();
			textarea2.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));

			JScrollPane scrollpane2 = new JScrollPane(textarea2);
			String filename = "";
			fileNames.add(filename);
			StoreSelectedFile storeselectedfile=  new StoreSelectedFile();
			storeselectedfile.setTabs(fileNames);
			
			textarea2.setTabSize(4);
			
			CurlyBraceKeyListener curlybracekeylistener=new CurlyBraceKeyListener(this);
			textarea2.addKeyListener(curlybracekeylistener);
			//positiontrackers.add(new PositionTracker(textarea2));
			
			addCaretListener(textarea2);
			scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
				try {
					if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.autokeylistener.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
				try {
					if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.autokeylistener.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
				try {
					if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.methodsuggestionbox.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
				try {
					if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
						int caretposition = curlybracekeylistener.methodsuggestionbox.position;
						Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
						Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
						SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
						curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			});
			textarea2.addMouseListener(rightclick);
			
			tabbedpane.addTab("",scrollpane2);
			tabbedpane.addTab("+",pluspanel);
			tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-2);
			
			fileName=filename;
			//curlybracekeylistener.positiontracker=positiontrackers.get(tabbedpane.getSelectedIndex());
			JScrollPane jscrollpane5=((JScrollPane)tabbedpane.getSelectedComponent());
			Main.this.textarea=(JTextArea)jscrollpane5.getViewport().getView();
			
			StoreSelectedFile storeselectedfile3 = new StoreSelectedFile();
			storeselectedfile3.set(fileName);
			
			this.fileName=fileName;
		});		
		closetab.addActionListener((ev) -> {	
			int tabindex=tabbedpane.getSelectedIndex();
			fileNames.remove(tabindex);
			if(fileNames.size() != 0 && tabindex != 0)
				tabbedpane.setSelectedIndex((tabindex-1));
			else if(fileNames.size() == 1 && tabindex == 0)
				tabbedpane.setSelectedIndex(0);
			tabbedpane.remove(tabindex);
			StoreSelectedFile storeselectedfile=new StoreSelectedFile();
			storeselectedfile.setTabs(fileNames);
			
		});
				
				
		opennewtab.addActionListener((ev) -> {
			addOrUpdateTab(ev);
		});
		tabbedpane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeevent) {
				Main.this.addOrUpdateTab(changeevent);	
				
			}
		});	
		
		generatejar.addActionListener((ev) -> {
			JTextAreaGroup textarea3=(JTextAreaGroup)textarea;
			textarea3.ExpandAll();	
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
							compile.compileall(this,fileName,javaversionnumber,sal,ev4);
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
							if(allfiles.isSameDirectory() || (allfiles.exists() && !allfiles.delete())) {
								commandline = new CommandLine();
								JOptionPane.showMessageDialog(null,dir+"ForJava"+javaversionnumber+"_"+main+".jar is already open. Run script to close "+main+".jar");
								FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
								BufferedWriter output2 = new BufferedWriter(filewriter2);
								output2.write("cd "+dir);
								output2.write("\n");
								output2.write("START /B /WAIT taskkill /F /im java.exe");
								output2.write("\n");
								output2.write("START /B /WAIT taskkill /F /im javaw.exe");
								output2.write("\n");
								for(int i = 0; i < allfiles.files.size(); i++) {
									File file2 = new File(allfiles.files.get(i));
									if(file2.exists()) {
										output2.write("del "+allfiles.files.get(i));
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
								String liney = "powershell -Command \"Start-Process powershell -Verb runAs -ArgumentList '-Command cmd /c \""+dir+"closeandcreatejar.bat\"'\"";
							
								commandline.runWithMSDOS(liney,dir);
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
						compile.compileall(this,fileName,sal,ev);
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
						if(allfiles.isSameDirectory() || (allfiles.exists() && !allfiles.delete())) {
							commandline = new CommandLine();
							JOptionPane.showMessageDialog(null,dir+main+".jar is already open. Run script to close "+main+".jar");
							FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
							BufferedWriter output2 = new BufferedWriter(filewriter2);
							output2.write("cd "+dir);
							output2.write("\n");
							output2.write("START /B /WAIT taskkill /F /im java.exe");
							output2.write("\n");
							output2.write("START /B /WAIT taskkill /F /im javaw.exe");
							output2.write("\n");
							for(int i = 0; i < allfiles.files.size(); i++) {
								File file2 = new File(allfiles.files.get(i));
								if(file2.exists()) {
									output2.write("del "+allfiles.files.get(i));
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
							String liney = "powershell -Command \"Start-Process powershell -Verb runAs -ArgumentList '-Command cmd /c \""+dir+"closeandcreatejar.bat\"'\"";
							
							commandline.runWithMSDOS(liney,dir);
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
					tabbedpane.setTitleAt(tabbedpane.getSelectedIndex(),getFileName(fileName));
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
				if(!fileName.equals("")) {
					String lines = Files.readString(Paths.get(fileName),StandardCharsets.UTF_8);
					int caretposition = textarea.getCaretPosition();
					textarea.setText(lines);
					textarea.setCaretPosition(caretposition);
					
					((JTextAreaGroup)textarea).codes = new LinkedList<Code>();
				}
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
					if(!selectedFile.getName().endsWith(".jar"))
						selectedFile = new File(selectedFile.getAbsolutePath()+".jar");
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
						if(!selectedFile.getName().endsWith(".jar"))
							selectedFile=new File(selectedFile.getAbsolutePath()+".jar");
						String absolutepath = selectedFile.getAbsolutePath();
			
						storeselectedfile.addJar(Main.this.fileName,absolutepath);
						gridlayout.setRows(gridlayout.getRows()+1);
						JPanel row = new JPanel();
						row.add(new JLabel(absolutepath));
						
						JButton removejar = new JButton("remove");
						removejar.addActionListener( (ev2) -> {
							StoreSelectedFile storeselectedfile2=new StoreSelectedFile();
							storeselectedfile2.removeJar(Main.this.fileName,absolutepath);
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
				String liney = (String)filenamescombobox.getSelectedItem();
				if(liney != null && !liney.equals("")) {
					open(liney);
				}  
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
		/*combobox.addItemListener((ev) -> {
			selectCode(ev);
		});*/
		//curlybracekeylistener = new CurlyBraceKeyListener(this);				
		//positiontrackers.add(curlybracekeylistener.positiontracker);
 		//textarea.addKeyListener(curlybracekeylistener); 		
		control_f.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JFrame frame2 = new JFrame();
				frame2.setSize(300,140);
				
				JPanel panel0 = new JPanel();
				panel0.setLayout(new FlowLayout(FlowLayout.CENTER,2,10));
				
				 panel0.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 10, 0));
				
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
				panel0.add(input);
				
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
				panel0.add(click);

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
				panel0.add(searchall);
				
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
				panel0.add(casey);
		
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
				panel0.add(replaceinput);
				
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
				panel0.add(replace);
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
				panel0.add(selection);
					
				panel0.validate();
				panel0.repaint();

				frame2.getContentPane().add(panel0);
				
				//frame2.pack();
				//frame2.setResizable(false);
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
		addCaretListener(textarea);
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
					
					JFileChooser fileChooser = new JFileChooser(new File(dir));
					FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Save as .java","java");
					fileChooser.setFileFilter(filenameextensionfilter);
					int status =fileChooser.showSaveDialog(frame);
					if(status == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						
						Main.this.fileName = file.getPath();
						Main.this.fileName=addDotJava(fileName);
						PrintWriter output = new PrintWriter(fileName);
						output.print(text);
						output.close();
						frame.setTitle(fileName.replaceAll(".+\\\\",""));
						String selected=Main.this.getFileName(fileName);
						
						Main.this.open(selected);
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
							fileName3=addDotJava(fileName3);

							Main main2 = new Main();
							main2.fileName = fileName3;
							main2.open(getFileName(fileName3));
						}
					}
				};
				t.start();
			}
		});
		compile_all.addActionListener((ev) -> {		
			JTextAreaGroup textarea3=(JTextAreaGroup)textarea;
			textarea3.ExpandAll();	
			try {
				if(fileName.equals("")) {
					NoFileOpen nofileopen=new NoFileOpen(textarea);
					fileName=nofileopen.getFileName();
					tabbedpane.setTitleAt(tabbedpane.getSelectedIndex(),getFileName(fileName));
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
				JTextAreaGroup textarea3=(JTextAreaGroup)textarea;
				textarea3.ExpandAll();	
				try {
					if(fileName.equals("")) {
						NoFileOpen nofileopen=new NoFileOpen(textarea);
						fileName=nofileopen.getFileName();
						tabbedpane.setTitleAt(tabbedpane.getSelectedIndex(),getFileName(fileName));
					}
					sal.actionPerformed(e);
					if(!fileName.equals("")) {
						String classpath = fileName.replaceAll("[^\\\\]+\\.java","");
	
						CommandLine commandline = new CommandLine();
						StoreSelectedFile storeselectedfile = new StoreSelectedFile();
						storeselectedfile.setCaretPosition(fileName,textarea.getCaretPosition());
						
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
							String[] options=new String[2];
							options[0] = "Yes";
							options[1] = "No";
							int option2=JOptionPane.showOptionDialog(null,"Go to line number of error?","Which you like to go to line number?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
							if(option2 == JOptionPane.YES_OPTION) {
								Pattern pattern=Pattern.compile(getFileName(fileName)+":([0-9]+):");
								Matcher matcher=pattern.matcher(lines);
								if(matcher.find()) {
									int line_number=Integer.parseInt(matcher.group(1));
									
									try {
										String wholetext=textarea.getText();
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
										}
									} catch(IOException ex) {
										ex.printStackTrace();
									}
								}
								else {
									JOptionPane.showMessageDialog(null,"Could not find line number.");
								}
							}		
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
				JTextAreaGroup textarea3=(JTextAreaGroup)textarea;
				textarea3.ExpandAll();	
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
								tabbedpane.setTitleAt(tabbedpane.getSelectedIndex(),getFileName(fileName));
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
	public void OpenNewTab(String filename) {
		int index=tabbedpane.getSelectedIndex();
		if(index != -1) {
			try {			
				//Component plustab = tabbedpane.getComponentAt(index);
				String dir = filename;
				if(!dir.equals("")) {
					dir = dir.replaceAll("[^\\\\]+\\.java","");
				}
				else {
					dir = System.getProperty("user.home");
				}
										
				tabbedpane.remove(pluspanel);
				JTextArea textarea2 = new JTextAreaGroup();
				textarea2.setLineWrap(true);
				textarea2.setWrapStyleWord(true);
				Main.this.textarea = textarea2;
				Font originalFont = textarea.getFont();
				textarea2.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));

				JScrollPane scrollpane2 = new JScrollPane(textarea2);
				fileNames.add(filename);
				StoreSelectedFile storeselectedfile=  new StoreSelectedFile();
				storeselectedfile.setTabs(fileNames);
				
				Path path = Paths.get(filename);
				String lines = Files.readString(path,StandardCharsets.UTF_8);
				textarea2.setTabSize(4);
				textarea2.setText(lines);
				
				CurlyBraceKeyListener curlybracekeylistener=new CurlyBraceKeyListener(this);
				textarea2.addKeyListener(curlybracekeylistener);
				//positiontrackers.add(new PositionTracker(textarea2));
				
				addCaretListener(textarea2);
				scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
					try {
						if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.autokeylistener.position;
							Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
							curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
					try {
						if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.autokeylistener.position;
							Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
							curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
					try {
						if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.methodsuggestionbox.position;
							Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
							curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
					try {
						if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
							int caretposition = curlybracekeylistener.methodsuggestionbox.position;
							Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
							Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
							SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
							curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
						}
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				});
				textarea2.addMouseListener(rightclick);
				
				tabbedpane.addTab(getFileName(filename),scrollpane2);
				tabbedpane.addTab("+",pluspanel);
				tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-2);
				
				fileName=filename;
				//curlybracekeylistener.positiontracker=positiontrackers.get(tabbedpane.getSelectedIndex());
				JScrollPane jscrollpane5=((JScrollPane)tabbedpane.getSelectedComponent());
				Main.this.textarea=(JTextArea)jscrollpane5.getViewport().getView();
				
				if(filelistmodifier.isEmpty()) {
					filelistmodifier.fillList(fileName);
				}			
				else if(!filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","").equals(fileName.replaceAll("[^\\\\]+\\.java",""))) {
					filelistmodifier = new FileListModifier();
					filelistmodifier.fillList(fileName);
				}
				else if(!filelistmodifier.directoryandfilename.equals(fileName)) {
					filelistmodifier = new FileListModifier();
					filelistmodifier.fillList(fileName);
				}
				git.Change(fileName);
				expandable.open();
				
				getclassmethods = new GetClassMethods(textarea);		
				getclassname = new GetClassName(textarea);
				
				StoreSelectedFile storeselectedfile3 = new StoreSelectedFile();
				storeselectedfile3.set(fileName);
				setStarterClassBoxes();
				if(fileName != null && !fileName.equals("")) {
					frame.setTitle(fileName.replaceAll(".+\\\\",""));
				}
				filelistmodifier.setSelected(getFileName(fileName));
				/*if(!deselected.equals("")) {
					filelistmodifier.setToMostRecentAfterSelected(deselected);	
				}*/
				String dir1= filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","");
				String dir2 = fileName.replaceAll("[^\\\\]+\\.java","");
				/*if(!dir.equals(dir2)) {
					filelistmodifier.fillList(fileName);
					
				}*/
				
				this.fileName=fileName;
					
				loadComboboxes(filelistmodifier);
				filenamescombobox.setSelectedItem(getFileName(fileName));
				
				//filenamescombobox.setSelectedItem(getFileName(fileName));
				/*StoreSelectedFile storeselectedfile3 = new StoreSelectedFile();
				int caretposition=storeselectedfile3.getCaretPosition(fileName);
				if(caretposition != 0)
				scrollToCaretPosition(caretposition);
				*/
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	//public List<PositionTracker> positiontrackers = new ArrayList<PositionTracker>();	
	public void addOrUpdateTab(EventObject eventobject) {
		int index=tabbedpane.getSelectedIndex();
		if(index != -1) {
			try {
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
						if(!selectedFile.getName().endsWith(".java")) {
							selectedFile=new File(addDotJava(selectedFile.getAbsolutePath()));
						}						
						tabbedpane.remove(pluspanel);
						JTextArea textarea2 = new JTextAreaGroup();
						textarea2.setLineWrap(true);
						textarea2.setWrapStyleWord(true);
						Main.this.textarea = textarea2;
						Font originalFont = textarea.getFont();
						textarea2.setFont(new Font(originalFont.getName(),originalFont.getStyle(),19));
	
						JScrollPane scrollpane2 = new JScrollPane(textarea2);
						String directoryandfilename = selectedFile.getPath();
						fileNames.add(directoryandfilename);
						StoreSelectedFile storeselectedfile=  new StoreSelectedFile();
						storeselectedfile.setTabs(fileNames);
						String filename = Main.this.getFileName(directoryandfilename);
						
						Path path = Paths.get(directoryandfilename);
						String lines = Files.readString(path,StandardCharsets.UTF_8);
						textarea2.setTabSize(4);
						textarea2.setText(lines);
						
						CurlyBraceKeyListener curlybracekeylistener=new CurlyBraceKeyListener(this);
						textarea2.addKeyListener(curlybracekeylistener);
						//positiontrackers.add(new PositionTracker(textarea2));
						
						addCaretListener(textarea2);
						scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
							try {
								if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
									int caretposition = curlybracekeylistener.autokeylistener.position;
									Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
									Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
									SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
									curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
								}
							} catch (BadLocationException ex) {
								ex.printStackTrace();
							}
						});
						scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
							try {
								if(curlybracekeylistener.autokeylistener.suggestionbox != null && curlybracekeylistener.autokeylistener.suggestionbox.isVisible()) {
									int caretposition = curlybracekeylistener.autokeylistener.position;
									Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
									Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
									SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
									curlybracekeylistener.autokeylistener.suggestionbox.setLocation(screencoordinates);
								}
							} catch (BadLocationException ex) {
								ex.printStackTrace();
							}
						});
						scrollpane2.getVerticalScrollBar().addAdjustmentListener((ev) -> {
							try {
								if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
									int caretposition = curlybracekeylistener.methodsuggestionbox.position;
									Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
									Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
									SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
									curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
								}
							} catch (BadLocationException ex) {
								ex.printStackTrace();
							}
						});
						scrollpane2.getHorizontalScrollBar().addAdjustmentListener((ev) -> {
							try {
								if(curlybracekeylistener.methodsuggestionbox != null && curlybracekeylistener.methodsuggestionbox.isVisible()) {
									int caretposition = curlybracekeylistener.methodsuggestionbox.position;
									Rectangle2D rectanglecoords=textarea2.modelToView2D(caretposition);
									Point screencoordinates= new Point((int)(Math.round(rectanglecoords.getX())),(int)(Math.round(rectanglecoords.getY())));
									SwingUtilities.convertPointToScreen(screencoordinates,textarea2);
									curlybracekeylistener.methodsuggestionbox.suggestionbox.setLocation(screencoordinates);
								}
							} catch (BadLocationException ex) {
								ex.printStackTrace();
							}
						});
						textarea2.addMouseListener(rightclick);
						
						tabbedpane.addTab(filename,scrollpane2);
						tabbedpane.addTab("+",pluspanel);
						tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-2);
					}
				}
				fileName=fileNames.get(tabbedpane.getSelectedIndex());
				//curlybracekeylistener.positiontracker=positiontrackers.get(tabbedpane.getSelectedIndex());
				JScrollPane jscrollpane5=((JScrollPane)tabbedpane.getSelectedComponent());
				Main.this.textarea=(JTextArea)jscrollpane5.getViewport().getView();
				
				if(filelistmodifier.isEmpty()) {
					filelistmodifier.fillList(fileName);
				}			
				else if(!filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","").equals(fileName.replaceAll("[^\\\\]+\\.java",""))) {
					filelistmodifier = new FileListModifier();
					filelistmodifier.fillList(fileName);
				}
				else if(!filelistmodifier.directoryandfilename.equals(fileName)) {
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
				filelistmodifier.setSelected(getFileName(fileName));
				/*if(!deselected.equals("")) {
					filelistmodifier.setToMostRecentAfterSelected(deselected);	
				}*/
				String dir = filelistmodifier.directoryandfilename.replaceAll("[^\\\\]+\\.java","");
				String dir2 = fileName.replaceAll("[^\\\\]+\\.java","");
				/*if(!dir.equals(dir2)) {
					filelistmodifier.fillList(fileName);
					
				}*/
				
				this.fileName=fileName;
					
				loadComboboxes(filelistmodifier);
				filenamescombobox.setSelectedItem(getFileName(fileName));
				
				//filenamescombobox.setSelectedItem(getFileName(fileName));
				/*StoreSelectedFile storeselectedfile3 = new StoreSelectedFile();
				int caretposition=storeselectedfile3.getCaretPosition(fileName);
				if(caretposition != 0)
				scrollToCaretPosition(caretposition);
				*/
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		allclassesinfile.ChangeFile(textarea,fileName);
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
						//scrollToCaretPosition(integer2);
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
				
				/*if(classnamescombobox.getItemCount() > 0)
				classnamescombobox.setSelectedItem(mainclass);
				if(combobox.getItemCount() > 0)
				combobox.setSelectedItem(0);
				*/
			}
			catch(NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}		
	public List<String> apiclasses = new ArrayList<String>();
	public void setApiClasses() {
		if(Main.muck != null && apiclasses.size() == 0) {	
			apiclasses=muck.links.getAPIClasses();
		}
	}
	public List<String> keywords= new ArrayList<String>();
	public void setKeywords() {
		if(Main.muck != null && keywords.size() == 0) {
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
	public List<String> packages = new ArrayList<String>();
	public void setPackages() {
		if(Main.muck != null && packages.size() == 0) {
			packages = muck.links.getPackages();
		}
	}
	public List<String> subpackages = new ArrayList<String>();
	public void setSubpackages() {
		if(Main.muck != null && subpackages.size() == 0) {
			subpackages = muck.links.getSubpackages();
		}
	}
	public List<String> fullpackagenames = new ArrayList<String>();
	public void setFullPackageNames() {
		if(Main.muck != null && fullpackagenames.size() == 0) {
			fullpackagenames = muck.links.getFullPackageNames();
		}
	}
	public AllClassesInFile allclassesinfile;
	public void setAllClassesInFile() {
		if(allclassesinfile == null) {
			allclassesinfile = new AllClassesInFile(textarea,fileName);
		}
	}
	List<String> allclassesinfolder = new ArrayList<String>();
	public void setAllClassesInFolder() {
		//if(allclassesinfolder == null) {
			allclassesinfolder=new ArrayList<String>();
			FileListModifier filelistmodifier = new FileListModifier();
			filelistmodifier.fillList(fileName);
			for(String filename:filelistmodifier.filelist) {
				allclassesinfolder.add(filename.replace(".java",""));
			}				
		//}
	}
	public void addCaretListener(JTextArea textarea) {
		textarea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				CurlyBraceKeyListener curlybracekeylistener = null;
				KeyListener[] keylisteners=textarea.getKeyListeners();
				for(KeyListener keylistener:keylisteners) {
					if(keylistener instanceof CurlyBraceKeyListener) {
						if(curlybracekeylistener != null) {
							JOptionPane.showMessageDialog(null,"Found more than one CurlyBraceKeyListener!");				
							break;
						}	
						curlybracekeylistener = (CurlyBraceKeyListener)keylistener;
					}
				}				
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
	/*
	** First time open program.
	*/
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
	/*
	** Second time open Window
	*/
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
			main.fileName = main.addDotJava(main.fileName);
			
			if(main.fileNames.size() == 0) {
				main.fileNames.add(main.fileName);
			}
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
	public MethodSuggestionBox methodsuggestionbox;
	private boolean isShift = false;
	private boolean isTab = false;
	private SelectedLines selectedlines;
	public PositionTracker positiontracker;
	public static VariableSuggestionBoxSelected variablesuggestionboxselected= new VariableSuggestionBoxSelected();	
	public AutoKeyListener autokeylistener;	
	public void keyPressed(KeyEvent ev)  {
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
		if((ev.getKeyChar() =='.' && !ev.isControlDown() ) || (methodsuggestionbox != null && methodsuggestionbox.isVisible()) ) {
			if(methodsuggestionbox != null && methodsuggestionbox.isVisible()) {
				//JOptionPane.showMessageDialog(null,"two characters");
							
				String oldplusnew = methodsuggestionbox.search_textfield.getText()+ev.getKeyChar();
				methodsuggestionbox.replacelength = methodsuggestionbox.replacelength+1;
				methodsuggestionbox.position = methodsuggestionbox.position+1;
				methodsuggestionbox.setLocation(methodsuggestionbox.position);
				methodsuggestionbox.search_textfield.setText(oldplusnew);
			}
			else {
				methodsuggestionbox= new MethodSuggestionBox(main);
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
		
		
		
		if(line.length() > 1 && !ev.isControlDown() && (methodsuggestionbox == null || !methodsuggestionbox.isVisible()) ) {
			if(autokeylistener.suggestionbox != null && autokeylistener.suggestionbox.isVisible()) {
				String oldplusnew = autokeylistener.search_textfield.getText()+ev.getKeyChar();
				autokeylistener.variablename = oldplusnew;
				autokeylistener.position = autokeylistener.position+1;
				autokeylistener.setLocation(autokeylistener.position);
				autokeylistener.search_textfield.setText(oldplusnew);
			}
			else {
				Pattern pattern=Pattern.compile("([a-z0-9A-Z]+)\\z");
				Matcher matcher=pattern.matcher(line);
				if(matcher.find()) {
					String variablename = matcher.group(1);
					if(autokeylistener.search(variablename)) { // if Variable name exists in this opened file
						autokeylistener.open(variablename,caretposition);
					}
				}
			}
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
		if(ev.isControlDown() && ev.getKeyCode() == KeyEvent.VK_D) {
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
		else if(ev.isControlDown() && ev.getKeyCode() == KeyEvent.VK_R) {
			try {
				String lines = Files.readString(Paths.get(main.fileName),StandardCharsets.UTF_8);
				int caretposition = main.textarea.getCaretPosition();
				main.textarea.setText(lines);
				main.textarea.setCaretPosition(caretposition);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public static SuggestionBoxSelected suggestionboxselected = new SuggestionBoxSelected();
	public static int red = 94;
	public static int green = 167;
	public static int blue = 236;		
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
	public JButton search_unique;
	public List<String> data = new ArrayList<String>();
	private JPanel panelgridlayout;
	private Main main;
	public JFrame suggestionbox;
	public JTextField search_textfield;
	private GridLayout gridlayout;
	public AutoKeyListener(Main main) {
		this.main = main;
	}
	public String variablename;
	public int caretposition;
	public void open(String variablename,int caretposition) {
		int selectionstart = main.textarea.getSelectionStart();
		if(selectionstart != caretposition)
			caretposition = selectionstart;
		run(variablename,caretposition);
	}
	public void run(String variablename,int caretposition) {
		position = caretposition;		
		caretposition-=variablename.length()-1;
		this.variablename = variablename;
		this.caretposition = caretposition;
		setLayout();
		setListeners();
		fillComboBox();
		suggestionbox.setVisible(true);
	}
	public int position;
	public void setLayout() {
		suggestionbox = new JFrame();	
		suggestionbox.setAlwaysOnTop(true);
				
		suggestionbox.setTitle("Variable name suggestion box");
		suggestionbox.setSize(100,500);
		panelgridlayout = new JPanel();
		
		//GridLayout gridlayout=new GridLayout(variablenames2.size()+1,1);
		gridlayout=new GridLayout(1,1);
		panelgridlayout.setLayout(gridlayout);
		/*
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		*/
		search_textfield=new JTextField();
		search_textfield.setText(variablename.trim());
		/*
		top_panel.add(search_textfield,BorderLayout.CENTER);
		search_unique = new JButton("\uD83D\uDD0D");
		top_panel.add(search_unique,BorderLayout.EAST);
		
		panelgridlayout.add(top_panel);
		*/
		panelgridlayout.add(search_textfield);	
		JScrollPane scrollpane = new JScrollPane(panelgridlayout);
		
		//methodscombobox.getEditor().getEditorComponent().addKeyListener(keylistener);
		suggestionbox.add(scrollpane);
		try {
			Rectangle2D rectanglecoords=main.textarea.modelToView2D(position);
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
		/*search_unique.addActionListener(ev -> {
			if(searchOnlyAPI(input)) {
				fillComboBox();
			}
		});*/
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
					else if(selected_label2.getText().contains(".")) {
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
		Pattern pattern2=Pattern.compile("((\\s+\\b(public|protected|private)\\b)?\\s+[0-9a-zA-Z<>]+\\s+([a-zA-Z0-9_]+)(?=\\s*=|;))");
		Matcher matcher2=pattern2.matcher(text.substring(0,caretposition));
		while(matcher2.find()) {
			variablenames.add(0,matcher2.group(4));			
		}
		Matcher matcher3=pattern2.matcher(text.substring(caretposition,text.length()));
		while(matcher3.find()) {
			variablenames.add(matcher3.group(4));
		}
		
		Pattern pattern = Pattern.compile("\\((.*?)\\)");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
		    String params = matcher.group(1); // everything inside ()
		    String[] paramList = params.split(",");
		    for (String param : paramList) {
		        param = param.trim();
		        String type = param.replaceAll("\\s+\\w+$", ""); // remove variable name
		        variablenames.add(type);
		    }
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
				variablenames2=CurlyBraceKeyListener.variablesuggestionboxselected.ReorderedStrings(variablenames2,input,data);
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
	
			if(caretposition > text.length()) {
				suggestionbox.dispose();
				return;
			}	
			String first=text.substring(0,caretposition);
			// JOptionPane.showMessageDialog(null,text.substring(caretposition,caretposition+variablename.length()));
			
			if((caretposition+variablename.length()) > text.length()) {
				suggestionbox.dispose();
				return;
			}
			String second = text.substring(caretposition+variablename.length(),text.length());
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
	public List<String> getMethods() {
		List<String> methods = new ArrayList<String>();
		try {
			if(!main.fileName.equals("")) {
				GetClassMethods getclassmethods3 = new GetClassMethods(main.textarea);	
				LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods3.getMethods();
				if(classnamesandmethodnames == null) JOptionPane.showMessageDialog(null,"classnamesandmethods is null.");
				
				LinkedHashMapInterface<String,LinkedHashMap<String,Integer>> iterator=new LinkedHashMapInterface<String,LinkedHashMap<String,Integer>>(classnamesandmethodnames) {		
					public void KeyAndValue(String key,LinkedHashMap<String,Integer> value) {
						Set<String> method_names=value.keySet();
						for(String method_name:method_names) {
							methods.add(method_name+"()");
						}
					}
				};	
				iterator.iterate();
			}		
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return methods;
	}				
	public boolean search(String input) {
		fillData();
		List<String> methods=getMethods();
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
			for(String filename:main.allclassesinfolder) {
				if(filename.contains(input)) {
					treeset.add(filename);
				}
			}				
			for(String class1:main.allclassesinfile.classes) {
				if(class1.contains(input)) {
					treeset.add(class1);
				}
			}				
			for(String api:main.fullpackagenames) {
				if(api.contains(input)) {
					treeset.add(api);
				}
			}				
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
			for(String package0:main.packages) {
				if(package0.startsWith(input)) {
					treeset.add(package0);
				}
			}
			for(String subpackage:main.subpackages) {
				if(subpackage.startsWith(input)) {
					treeset.add(subpackage);
				}
			}
			for(String fullpackagename:main.fullpackagenames) {
				if(fullpackagename.startsWith(input)) {
					treeset.add(fullpackagename);
				}
			}
			for(String method:methods) {
				if(method.startsWith(input)) {
					treeset.add(method);
				}
			}
			//System.out.println("end");
			variablenames2=new ArrayList<String>(treeset);
		}
		else { // if(input.equals(""))
			for(String filename:main.allclassesinfolder) {
				treeset.add(filename);
			}				
			for(String class1:main.allclassesinfile.classes) {
				treeset.add(class1);
			}				
			for(String variablename2:data) {
				treeset.add(variablename2);
			}
			for(String apiclass:getAPI(input)) {
				treeset.add(apiclass);
			}
			for(String keyword:main.keywords) {
				treeset.add(keyword);
			}
			for(String package0:main.packages) {
				treeset.add(package0);
			}
			for(String subpackage:main.subpackages) {
				treeset.add(subpackage);
			}
			for(String fullpackagename:main.fullpackagenames) {
				treeset.add(fullpackagename);
			}
			for(String method:methods) {
				treeset.add(method);
			}
			variablenames2=new ArrayList<String>(treeset);
		}
		data = variablenames2;
		return variablenames2.size() > 0;
	}
	public void setLocation(int caretposition3) {
		try {
			Rectangle2D rectanglecoords=main.textarea.modelToView2D(caretposition3);
			Point screencoordinates= new Point((int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()));
			SwingUtilities.convertPointToScreen(screencoordinates,main.textarea);
			suggestionbox.setLocation(screencoordinates);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}
}
class MethodSuggestionBox {
	public int replacelength = 1;
	public int position;
	public JTextField search_textfield;	
	public JFrame suggestionbox;	
	public String text;
	public String currentline;	
	public Main main;	
	public MethodSuggestionBox(Main main) {
		this.main = main;	
		text = main.textarea.getText();
		Middle middle = new Middle(main.textarea);
		int caretposition = main.textarea.getCaretPosition();
		position = caretposition;
		//String currentline=middle.getWholeLine2(caretposition);
		String currentline2= middle.getCurrentLine();
		if(currentline2.length() > 0) {
			Pattern pattern = Pattern.compile("(import)?\\s*([a-zA-Z0-9\\.]+)\\z");
			Matcher matcher0=pattern.matcher(currentline2);	
			//List<String> classesfrompackage=null;	
			if(matcher0.find()) {
				currentline = matcher0.group(2);
				//JOptionPane.showMessageDialog(null,currentline);
									
				Object[] allobjects=search(currentline);
				if(allobjects.length > 0)
					show(allobjects,caretposition,currentline);	
			}
		}
	}
	public Object[] search(String input) {
		Object[] innerpackages=getInnerPackages(input);
		Object[] classes = getClassesFromPackage(input);
		Object[] allobjects=addMembersToMembers(innerpackages,classes);
		classes=getClassesFromPackage(input);
		allobjects=addMembersToMembers(allobjects,classes);
		Object[] classforname=getFromPackageAndClass(input);
		allobjects=addMembersToMembers(allobjects,classforname);
		Object[] not_api_normal_classes=getNotJavaAPIPackages(text,input);
		allobjects=addMembersToMembers(allobjects,not_api_normal_classes);
		return allobjects;
	}
	public String getClassName(String variablenameorclassname,String text) {
		// Below if variable name
		Pattern pattern4= Pattern.compile("(\\s*(\\b(public|protected|private)\\b)?\\s*(\\b(static)\\b)?\\s*([a-zA-Z<>0-9,?]+)\\s+("+variablenameorclassname+")(?=\\s*=|;|:))");	
		Matcher matcher2=pattern4.matcher(text);
		if(matcher2.find()) {		
			String classname=matcher2.group(6);
			return classname;
		}
		else { 
			// Get parameter variable type from method	
			Pattern pattern5=Pattern.compile("(?<!\\w)(\\w+)\\s+"+variablenameorclassname+"(?!\\w)");
			Matcher matcher3=pattern5.matcher(text);
			if(!matcher3.find()) { // If static class name
				return variablenameorclassname;
			}
			else {
				return matcher3.group(1);
			}
		}
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
				try {	
					if(field.isEnumConstant()) {
						field.setAccessible(true);
						list.add(field);
					}
				} catch (InaccessibleObjectException ex) {
					ex.printStackTrace();
				}
			}
			return list.toArray(new Object[list.size()]);
		}
		else {		
			Member[] propertiesandmethods=getAllPropertyAndMethods(classquestionmark);
			Class<?>[] enums=classquestionmark.getDeclaredClasses();
			List<Class<?>> enums2 = Arrays.asList(enums);
			LiveIterator<Class<?>> liveiterator=new LiveIterator<Class<?>>(enums2,true);
			while(liveiterator.hasNext()) {
				Class<?> enum1 = liveiterator.next();				
				try {
					if(enum1.isEnum()) {
						Field[] fields= enum1.getDeclaredFields();
						for(Field field : fields) {
							if(field.isEnumConstant()) {
								field.setAccessible(true);
							}
						}
					}
				} catch (InaccessibleObjectException ex) {
					ex.printStackTrace();
					liveiterator.remove(enum1);		
				}	
			}
			enums=liveiterator.list.toArray(new Class<?>[liveiterator.list.size()]);
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
	public Class<?> getClassQuestionMark(String classname) {
		try {	
			return Class.forName(classname);
		} catch (ClassNotFoundException ex) {
			return null;
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
				//JOptionPane.showMessageDialog(null,classname);
				
				List<String> imports =Main.muck.links.getImport(classname);
				//JOptionPane.showMessageDialog(null,imports.size()+"");
				if(imports == null)
					return Class.forName("java.lang.Object");
				for(String importy:imports) {
					String select="import "+importy.replaceAll(classname+"$","")+"*;";
					if(text.contains(select))
						return Class.forName(importy);
				}
				return Class.forName(imports.get(0));
			}
			catch(ClassNotFoundException ex4) {
				ex4.printStackTrace();
				return null;
			}
		}
	}
	public Member[] getAllPropertyAndMethods(String classname,String text) {
		Class<?> members = 	getClassQuestionMark(classname,text);
		if(members != null) {
			return getAllPropertyAndMethods(members);
		}
		else {
			return new Member[0];
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
	public Object[] addMembersToMembers(Object[] members,Object[] methods2) {
		List<Object> methodsandproperties=  new ArrayList<Object>();
		for(Object method:members) {
			methodsandproperties.add(method);
		}
		for(Object method2:methods2) {
			methodsandproperties.add(method2);
		}
		Object[] methods3 = new Object[methodsandproperties.size()];
		for(int i = 0; i < methods3.length; i++) {
			methods3[i] = methodsandproperties.get(i);
		}
		return methods3;
	}
	
	public Object[] getInnerPackages(String substring) {
		List<String> subpackages=main.muck.links.getInnerPackages(substring);	
		if(subpackages != null && subpackages.size() > 0) {
			Object[] methodboxvalues2 = new Object[subpackages.size()];
			for(int i = 0; i < methodboxvalues2.length; i++) {
				methodboxvalues2[i] = subpackages.get(i);
			}
			
			return methodboxvalues2;
		}	
		return new Member[0];
	}
	
	public Object[] getNotJavaAPIPackages(String text,String currentline) {
		Pattern pattern3=Pattern.compile("\\s*([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)$");
		Matcher matcher=pattern3.matcher(currentline);
		if(matcher.find()) {
			String editedline = matcher.group(1);	
			Class<?> property = null;	
			String[] properties = editedline.split("\\.");
			String first = properties[0];
			
			String classname = null;
			// Is Capitol
			if(Character.isUpperCase(editedline.charAt(0))) {
				classname=editedline;
			}
			else { // Is first character not a capitol
				classname=getClassName(first,text);
			}
			
			property = getClassQuestionMark(classname,text);
			if(property == null)
				return new Object[0];	
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
			Object[] unorderedmethods = getAllPropertyAndMethodsAndEnums(property);
			return unorderedmethods;
		}
		return new Object[0];
	}
	public Object[] getClassesFromPackage(String substring) {
		List<String> classesfrompackage=main.muck.links.getClassFrom(substring);
		if(classesfrompackage == null)
			return new Object[0];	
		Object[] listings = new Object[classesfrompackage.size()];
		for(int i = 0; i < listings.length; i++) {
			listings[i] = classesfrompackage.get(i);
		}
		return listings;
	}
	public Object[] getFromPackageAndClass(String substring) {
		try {
			Class<?> property=getClassQuestionMark(substring);
			if(property == null)
				return new Object[0];
			return getAllPropertyAndMethodsAndEnums(property);
		} 
		catch(NoClassDefFoundError ex) {
			//ex.printStackTrace();
			return new Object[0];
		}
	}
	public String[] getStrings(Object[] methods) {
		String[] strings = new String[methods.length];	
		for(int i = 0; i < methods.length; i++) {
			if(methods[i] instanceof String) {
				strings[i] = (String)methods[i];
			}		
			else if(methods[i] instanceof Method) {
				String name=((Method)methods[i]).getName();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				
				name+=getParanthesesAndParameters(methods[i]);
				
				strings[i] = name;
			}
			else if(methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isEnum()) {
				String name=((Class<?>)methods[i]).getName();
				//String name=((Enum)methods[i]).name();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				strings[i] =name;
			}
			else if( methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isInterface() ) {
				String name=((Class<?>)methods[i]).getName();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				strings[i] =name;
			}
			else if(methods[i] instanceof Member) {
				String name=((Member)methods[i]).getName();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				strings[i] =name;
			}
			else { // if(methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isLocalClass()) {
				String name= methods[i].toString();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				strings[i] =name;
			}
		}	
		return strings;
	}																																																																																																																																								
	/*
	** Old method signature for show() was:
	** public void Popup(Class<?> classquestionmark,int caretposition) {
	*/
	public void show(Object[] unorderedmethods,int caretposition,String search) {
		try {	
			suggestionbox = new JFrame();	
			// suggestionbox.setAlwaysOnTop(true);		
			suggestionbox.setTitle(search);
			suggestionbox.setSize(100,500);
			JPanel panelgridlayout = new JPanel();
			
			String[] unorderedmethods2 = getStrings(unorderedmethods);
			final String[] methods = CurlyBraceKeyListener.suggestionboxselected.Reordered(unorderedmethods2,search);
			
			GridLayout gridlayout=new GridLayout(methods.length+1,1);
			panelgridlayout.setLayout(gridlayout);
			search_textfield=new JTextField();
			panelgridlayout.add(search_textfield);
			JLabel[] labels = new JLabel[methods.length];
			for(int i = 0; i < methods.length; i++) {
				labels[i] = new JLabel((String)methods[i]);
				panelgridlayout.add(labels[i]);
			}
			JScrollPane scrollpane = new JScrollPane(panelgridlayout);
			
			labels[0].setOpaque(true);
			labels[0].setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
			KeyListener keylistener = new KeyListener() {
				boolean justStarted = true;	
				Object[] methods2=methods;	
				String ifdotbefore = "";
				JLabel[] labels2=labels;	
				LiveIterator<JLabel> liveiterator = new LiveIterator<JLabel>(labels2);
				int selected_index = 0;
				@Override
				public void keyPressed(KeyEvent keyevent) {
					if(keyevent.getKeyCode() == KeyEvent.VK_ESCAPE) {
						suggestionbox.dispose();
					}
					else if(keyevent.getKeyCode() == KeyEvent.VK_DOWN) {
						labels2[selected_index].setOpaque(false);
						labels2[selected_index].setBackground(new JLabel().getBackground());
						panelgridlayout.validate();
						panelgridlayout.repaint();
						int live_index = liveiterator.indexOf(labels2[selected_index]);						
						if( live_index < (liveiterator.list.size()-1) ) {
							live_index++;
							JLabel selected_label=liveiterator.list.get(live_index);
							selected_label.setOpaque(true);
							selected_label.setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
							panelgridlayout.validate();
							panelgridlayout.repaint();
							
							label3:for(int i = 0; i < labels2.length; i++) {
								if(selected_label.equals(labels2[i])) {
									selected_index = i;
									break label3;
								}
							}
						}
					}
					else if(keyevent.getKeyCode() == KeyEvent.VK_UP) {
						labels2[selected_index].setOpaque(false);
						labels2[selected_index].setBackground(new JLabel().getBackground());
						panelgridlayout.validate();
						panelgridlayout.repaint();
						int live_index = liveiterator.indexOf(labels2[selected_index]);
						if(live_index > 0) {
							live_index--;
							JLabel selected_label=liveiterator.list.get(live_index);
							selected_label.setOpaque(true);
							selected_label.setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
							panelgridlayout.validate();
							panelgridlayout.repaint();
							
							label4:for(int i = 0; i < labels2.length; i++) {
								if(selected_label.equals(labels2[i])) {
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
						JLabel selected_label2 =labels2[selected_index];
						String selected = selected_label2.getText();
						CurlyBraceKeyListener.suggestionboxselected.Save(search,selected);
						
						if(!ifdotbefore.equals(""))
							selected=ifdotbefore+"."+selected;
						String firsthalf=text.substring(0,caretposition)+"."+selected;
						//String firsthalf=text.substring(0,caretposition)+ifdotbefore+"."+selected;
						///String second =text.substring(caretposition+1,text.length());
						String second =text.substring(caretposition+replacelength,text.length());
						main.textarea.setText(firsthalf+second);
						main.textarea.setCaretPosition(caretposition+1+selected.length());
					}
					else if(keyevent.getKeyCode() != KeyEvent.VK_ENTER && keyevent.getKeyCode() != KeyEvent.VK_DOWN && keyevent.getKeyCode() != KeyEvent.VK_UP) {
						liveiterator.reset();
						while(liveiterator.hasNext()) {
							JLabel label = liveiterator.next();
							panelgridlayout.remove(label);
						}
						
						String methodname=search_textfield.getText();
						if(methodname.length() > 0 && (methodname.substring(methodname.length()-1,methodname.length())).equals(".")) {
							String output=currentline+".";
							String output2=methodname;
							if(methodname.endsWith("."))
								output2=methodname.substring(0,(methodname.length()-1));	
							ifdotbefore=output2;
							
							output=output+output2;	
							//currentline=output;
							Object[] allobjects2=MethodSuggestionBox.this.search(output);
							if(allobjects2.length == 0) {
								suggestionbox.dispose();
								main.textarea.setCaretPosition((caretposition+1));
								return;
							}
								
							methods2=allobjects2;
							labels2=getLabels(allobjects2);
							selected_index = 0;
						}
				
						liveiterator = new LiveIterator<JLabel>(labels2);	
						
						if(keyevent.getKeyCode() != KeyEvent.VK_PERIOD) {
							String searchy = methodname.toLowerCase();
							if(methodname.contains(".")) {
								String[] properties=searchy.split("\\.");
								searchy = properties[(properties.length-1)];
							}
							for(JLabel label:labels2) {	
								if( ! (label.getText().toLowerCase().startsWith(searchy)) ) {
									liveiterator.remove(label);
								}
							}
							labels2=liveiterator.list.toArray(new JLabel[liveiterator.list.size()]);
							selected_index = 0;
						}
						if(labels2.length == 0) {
							/*suggestionbox.dispose();
							main.textarea.setCaretPosition((caretposition+1));	
							*/
							/*
							String selected = selected_label2.getText();
							if(!ifdotbefore.equals(""))
							*/
							String selected = methodname;
							if(!ifdotbefore.equals(""))
								selected=ifdotbefore+"."+selected;
							String firsthalf=text.substring(0,caretposition)+"."+selected;
							//String firsthalf=text.substring(0,caretposition)+ifdotbefore+"."+selected;
							String second =text.substring(caretposition,text.length());
							main.textarea.setText(firsthalf+second);
							main.textarea.setCaretPosition(caretposition+1+selected.length());	
							suggestionbox.dispose();
							return;
						}
						
						gridlayout.setRows(liveiterator.list.size()+1);
						liveiterator.reset();
						while(liveiterator.hasNext()) {
							JLabel label = liveiterator.next();
							panelgridlayout.add(label);
						}
						if(!isSelected()) {
							selected_index = 0;
							JLabel label5 = labels2[selected_index];	
							label5.setOpaque(true);
							label5.setBackground(new Color(CurlyBraceKeyListener.red,CurlyBraceKeyListener.green,CurlyBraceKeyListener.blue));
						}
						
						panelgridlayout.validate();
						panelgridlayout.repaint();
						suggestionbox.pack();	
					}
				}
				public boolean isSelected() {
					if(selected_index > labels2.length) {
						return false;
					}
					JLabel selected_label=labels2[selected_index];	
					return !selected_label.getBackground().equals(new JLabel().getBackground());
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
	public String getParanthesesAndParameters(Object method) {
		String methodorproperty = "(";
		if(method instanceof Method) {
			if(((Method)method).getParameterCount() > 0) {
				Parameter[] parametertypes=((Method)method).getParameters();
				String[] variabletypes= new String[parametertypes.length];
				for(int j = 0; j < parametertypes.length; j++) {
					variabletypes[j]= parametertypes[j].getType()+" "+parametertypes[j].getName();
				}
				methodorproperty+=String.join(",",variabletypes);
			}
		}
		methodorproperty+=")";
		return methodorproperty;
	}
	public JLabel[] getLabels(Object[] methods) {
		JLabel[] labels = new JLabel[methods.length];
		for(int i = 0; i < methods.length; i++) {
			if(methods[i] instanceof String) {
				labels[i] = new JLabel((String)methods[i]);
			}		
			else if(methods[i] instanceof Method) {
				String name=((Method)methods[i]).getName();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				labels[i] = new JLabel(name);
			}
			else if(methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isEnum()) {
				String name=((Class<?>)methods[i]).getName();
				//String name=((Enum)methods[i]).name();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				labels[i] = new JLabel(name);
			}
			else if( methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isInterface() ) {
				String name=((Class<?>)methods[i]).getName();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				labels[i] = new JLabel(name);
			}
			else if(methods[i] instanceof Member) {
				String name=((Member)methods[i]).getName();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				labels[i] = new JLabel(name);
			}
			else { // if(methods[i] instanceof Class<?> && ((Class<?>)methods[i]).isLocalClass()) {
				String name= methods[i].toString();
				if(name.contains("$")) {
					name=name.replaceAll(".+\\$","");
				}
				labels[i] = new JLabel(name);
			}
		}
		return labels;
	}	
	public void setLocation(int caretposition3) {
		try {
			Rectangle2D rectanglecoords=main.textarea.modelToView2D(caretposition3);
			Point screencoordinates= new Point((int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()));
			SwingUtilities.convertPointToScreen(screencoordinates,main.textarea);
			suggestionbox.setLocation(screencoordinates);
			suggestionbox.validate();
			suggestionbox.repaint();
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	public boolean isVisible() {
		if(suggestionbox != null && suggestionbox.isVisible()) {
			return true;
		}
		else {
			return false;
		}
	}
}
class RightClick extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent me) {
		if(SwingUtilities.isRightMouseButton(me)) {
			RightClickJFrame rightclickjframe = new RightClickJFrame(me);
		}				
	}
	
}
class RightClickJFrame {
	public int caretposition;	
	public JButton cut;
	public JButton copy;
	public JButton paste;	
	public JTextArea textarea5;
	public JFrame frame;
	public RightClickJFrame(MouseEvent me) {
		textarea5=(JTextArea)me.getSource();
		this.caretposition = textarea5.getCaretPosition()-1;
		setLayout();
		setListeners();
	}
	public void setLayout() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		JPanel panel = new JPanel();
		GridLayout gridlayout = new GridLayout(3,1);
		panel.setLayout(gridlayout);
		
		copy = new JButton("copy");
		panel.add(copy);
		
		paste = new JButton("paste");
		panel.add(paste);
		
		cut = new JButton("cut");
		panel.add(cut);
		
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation();
		frame.setVisible(true);
	}
	public void setListeners() {
		cut.addActionListener( ev -> {
			textarea5.dispatchEvent(new KeyEvent(textarea5,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),InputEvent.CTRL_DOWN_MASK,KeyEvent.VK_X,'X'));
			frame.dispose();
		});
		copy.addActionListener((ev) -> {
			textarea5.dispatchEvent(new KeyEvent(textarea5,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),InputEvent.CTRL_DOWN_MASK,KeyEvent.VK_C,'C'));
			frame.dispose();	
		});
		paste.addActionListener((ev) -> {
			textarea5.dispatchEvent(new KeyEvent(textarea5,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),InputEvent.CTRL_DOWN_MASK,KeyEvent.VK_V,'V'));
			frame.dispose();	
		});
	}
	public void setLocation() {
		try {
			Rectangle2D rectanglecoords=textarea5.modelToView2D(caretposition);
			Point screencoordinates= new Point((int)Math.round(rectanglecoords.getX()),(int)Math.round(rectanglecoords.getY()));
			SwingUtilities.convertPointToScreen(screencoordinates,textarea5);
			frame.setLocation(screencoordinates);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}
}

		
		








			