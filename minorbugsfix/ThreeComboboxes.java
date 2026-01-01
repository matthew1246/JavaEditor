import java.util.EmptyStackException;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Set;
/*
** This is load all JComboBoxes fields, 
** so load all file names, class names and Method names.
** This object is used to load all data for three JComboBoxes.
** The three ComboBoxes are classnamescombobox, classnamescombobox
** and combobox.
*/
public class ThreeComboboxes {
	public Main main;
	public String fileName = "";
	public ThreeComboboxes(Main main) {
		this.fileName = "";
		this.main = main;
	}
	public ThreeComboboxes(Main main,String fileName) {
		this.main = main;
		load(fileName);	
	}
	/*
	** The root change for this is FileComboBox.
	*/
	public void load(String fileName) {
		if(fileName != null && !fileName.equals("") && !this.fileName.equals(fileName) && !Main.isSameDirectory(this.fileName,fileName)) { // Saved file
			FromScratch(fileName);
		}
		else if(fileName != null && Main.isSameDirectory(this.fileName,fileName)) {
			if(main.filelistmodifier == null) {
				FromScratch(fileName);
			}	
			else if( main.filelistmodifier.getFileList().contains(Main.getFileName(fileName)) )	{
				main.filenamescombobox.setSelectedItem(Main.getFileName(fileName));
			}
			else {
				String file = Main.getFileName(fileName);
				main.filelistmodifier.filelist.add(file);
				main.filelistmodifier.original.add(file);
				main.filenamescombobox.addItem(file);
			}
			setGetClassName();
			setGetClassMethods();
			setClassNames();
			classCombobox();
			setMainClass();
			methodCombobox(mainclass);
			Select(fileName,mainclass);				
		}
		else if(fileName != null && fileName.equals("")) { // Blank JTextArea
			main.filelistmodifier = new FileListModifier();
			RemoveAll();	
		}
		if(fileName != null)	
		this.fileName = fileName;
	}
	public void FromScratch(String fileName) {
		setGetClassName();
		setGetClassMethods();
		setClassNames();
		fileCombobox(fileName);
		classCombobox();
		setMainClass();
		methodCombobox(mainclass);
		Select(fileName,mainclass);	
	}
	protected GetClassName getclassname;
	public void setGetClassName() {
		getclassname = new GetClassName(main.textarea);
	}
	LinkedHashMap<String,Integer> classnames;
	protected void setClassNames() {
		classnames = getclassmethods.getClasses();
	}
	protected GetClassMethods getclassmethods;
	public void setGetClassMethods() {
		getclassmethods = new GetClassMethods(main.textarea);
	}
	public void fileCombobox(String fileName) {
		main.filelistmodifier = new FileListModifier(fileName);
		RemoveFilecombo();
		List<String> filenames=main.filelistmodifier.getFileList();
		for(String filename:filenames) {
			main.filenamescombobox.addItem(filename);
		}			
	}
	public void classCombobox() {
		try {
			RemoveClasscombo();			
			if(classnames == null) JOptionPane.showMessageDialog(null,"classnames 1 is null.");
			// if(classnames.keySet().size() == 0) JOptionPane.showMessageDialog(null,"Classes is empty.");
			LinkedHashMapInterface<String,Integer> iterator2=new LinkedHashMapInterface<String,Integer>(classnames) {
				public void KeyAndValue(String key,Integer integer) {
					main.classnamescombobox.addItem(key);
				}
			};						
			iterator2.iterate();
		} catch(NullPointerException ex) {
			ex.printStackTrace();
		}
	}
	protected String mainclass = "";
	public void setMainClass() {
		mainclass = getMainClass();
	}
	public String getMainClass() {
		if( getclassname.getClassName().equals("Can't find class name.") ) 
			return "Can't find class name.";
		try {
			LinkedHashMap<String,Integer> classnames = getclassmethods.getClasses();
			if(classnames == null) JOptionPane.showMessageDialog(null,"classnames 1 is null.");
			// if(classnames.keySet().size() == 0) JOptionPane.showMessageDialog(null,"Classes is empty.");
			LinkedHashMapInterface<String,Integer> iterator2=new LinkedHashMapInterface<String,Integer>(classnames) {
				public void KeyAndValue(String key,Integer integer) {
					if(getclassname.isMainClass(integer)) {
						mainclass=key;
					}	
				}
			};						
			iterator2.iterate();
			if(mainclass.equals("")) {
				mainclass = getclassname.getClassName();
			}
		} catch(EmptyStackException ex){
			ex.printStackTrace();
			mainclass = "";
		}
		catch (NullPointerException ex) {
			ex.printStackTrace();
			mainclass = "";
		}
		return mainclass;
	}
		
	public void methodCombobox(String selectedclass) {
		RemoveMethodcombo();	
		LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethodnames = getclassmethods.getMethods();
		if(classnamesandmethodnames == null) JOptionPane.showMessageDialog(null,"classnamesandmethods is null.");
		LinkedHashMapInterface<String,LinkedHashMap<String,Integer>> iterator=new LinkedHashMapInterface<String,LinkedHashMap<String,Integer>>(classnamesandmethodnames) {		
			public void KeyAndValue(String key,LinkedHashMap<String,Integer> value) {
				if(selectedclass.equals(key)) {
					Set<String> method_names=value.keySet();
					for(String method_name:method_names) {
						main.combobox.addItem(method_name);
					}
				}
			}
		};		
		iterator.iterate();
	}
	public void Select(String fileName,String mainclass) {
		main.filenamescombobox.setSelectedItem(Main.getFileName(fileName));
		main.classnamescombobox.setSelectedItem(mainclass);
		main.combobox.setSelectedItem(0);
	}
	public void RemoveAll() {
		RemoveFilecombo();
		RemoveClasscombo();
		RemoveMethodcombo();
	}
	public void RemoveFilecombo() {
		if(main.filenamescombobox.getItemCount() > 0) {
			main.filenamescombobox.removeAllItems();
		}	
	}
	public void RemoveClasscombo() {
		if(main.classnamescombobox.getItemCount() > 0) {
			main.classnamescombobox.removeAllItems();
		}
	}
	public void RemoveMethodcombo() {
		if(main.combobox.getItemCount() > 0) {
			main.combobox.removeAllItems();
		}
	}
}