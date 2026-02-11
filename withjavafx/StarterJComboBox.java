import javax.swing.JComboBox;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Iterator;
public class StarterJComboBox {
	protected Main main;
	protected String fileName;
	public StarterJComboBox(Main main) {
		this.main = main;
	}
	public List<String> starterclasses = new ArrayList<String>();
	public List<String> displays = new ArrayList<String>();
	public String starterclass = "";
	public void BackgroundThread(String fileName) {
		this.fileName = fileName;
		if(!fileName.equals("")) {
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			starterclasses=storeselectedfile.getStartupComboBox(fileName);
				for(String starterclass2:starterclasses) {			
				String display = starterclass2;
				if(display != null && (display.contains("\\") || display.contains("/") || display.endsWith(".java"))) {
					display = main.getFileName(display);
					if(display.endsWith(".java")) {
						display = display.substring(0, display.length() - ".java".length());
					}
				}
				displays.add(display);
			}
			starterclass = storeselectedfile.getStarterClass(fileName);
			if(starterclass.equals(""))
				starterclass=Main.getClassName(fileName);	
		}
	}
	public void EDT() {
		Remove();
		RemoveNulls(starterclasses);	
		for(String display:displays) {
			main.startupcombobox.addItem(display);		
		}
		if(!Contains(starterclass)) {
			Add(starterclass);
		}
		main.startupcombobox.setSelectedItem(starterclass);
	}				
	public StarterJComboBox(String fileName,Main main) {
		this.fileName = fileName;
		this.main = main;
		if(!fileName.equals("")) {
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Remove();
			List<String> starterclasses= storeselectedfile.getStartupComboBox(fileName);
			RemoveNulls(starterclasses);
			for(String starterclass2:starterclasses) {
				String display = starterclass2;
				if(display != null && (display.contains("\\") || display.contains("/") || display.endsWith(".java"))) {
					display = main.getFileName(display);
					if(display.endsWith(".java")) {
						display = display.substring(0, display.length() - ".java".length());
					}
				}
				main.startupcombobox.addItem(display);
			}
			String starterclass = storeselectedfile.getStarterClass(fileName);
			if(starterclass.equals(""))
				starterclass=Main.getClassName(fileName);		
			if(!Contains(starterclass)) {
				Add(starterclass);
			}
			main.startupcombobox.setSelectedItem(starterclass);
		}
	}
	public void Change(String filename) {
		
if(!fileName.equals(filename)) {
			if(!filename.equals("")) {
				if( !Main.getDirectory(filename).equals(Main.getDirectory(fileName)) ) { // Not same folder
					main.lock.setSelected(false);	
					Remove();
					getCacheAndAddToComboBox(filename);
				}
				else { // same folder
					if(main.lock.isSelected()) {
						String selected=(String)main.startupcombobox.getSelectedItem();
						StoreSelectedFile storeselectedfile = new StoreSelectedFile();				List<String> starterclasses= storeselectedfile.getStartupComboBox(filename);
						System.out.println("starter:");
						for(String starter:starterclasses) {
							System.out.println(starter);
						}
						System.out.println();
						if(!starterclasses.contains(selected)) {
							Remove();
							starterclasses.add(0,selected);
							AddAll(starterclasses);
							main.startupcombobox.setSelectedItem(selected);
							storeselectedfile.setStartupComboBox(filename,starterclasses);
						}
						else {
							Remove();
							getCacheAndAddToComboBox(filename);
							main.startupcombobox.setSelectedItem(selected);	
						}
					}
					else {
						Remove();			
						getCacheAndAddToComboBox(filename);
					}
				}
				this.fileName = filename;	
			}
		}								
	}	
	public void getCacheAndAddToComboBox(String filename) {
		StoreSelectedFile storeselectedfile = new StoreSelectedFile();				List<String> starterclasses= storeselectedfile.getStartupComboBox(filename);
		RemoveNulls(starterclasses);		
			
		for(String starterclass2:starterclasses) {
			String display = starterclass2;
			if(display != null && (display.contains("\\") || display.contains("/") || display.endsWith(".java"))) {
				display = main.getFileName(display);
				if(display.endsWith(".java")) {
					display = display.substring(0, display.length() - ".java".length());
				}
			}
			main.startupcombobox.addItem(display);
		}
		String onlyfilename = main.getFileName(filename);
		if(onlyfilename.endsWith(".java")) {
			int length = ".java".length();
			onlyfilename = onlyfilename.substring(0,onlyfilename.length()-length);
		}
		if(!Contains(onlyfilename)) {
			main.startupcombobox.addItem(onlyfilename);
		}
		main.startupcombobox.setSelectedItem(onlyfilename);
		main.startupcombobox.validate();			
		main.startupcombobox.repaint();
		storeselectedfile.setStartupComboBox(filename,getItems());
	}
	public void RemoveNulls(List<String> starterclasses) {
		Iterator<String> starterclasses_it = starterclasses.iterator();
		while(starterclasses_it.hasNext()) {
			if(starterclasses_it.next() == null)
				starterclasses_it.remove();
		}
	}		
	public void Add(String starterclass) {
		String display = starterclass;
		if(display != null && (display.contains("\\") || display.contains("/") || display.endsWith(".java"))) {
			display = main.getFileName(display);
			if(display.endsWith(".java")) {
				display = display.substring(0, display.length() - ".java".length());
			}
		}
		main.startupcombobox.addItem(display);
	}
	public void AddAll(List<String> list) {
		for(String classname:list) {
			main.startupcombobox.addItem(classname);
		}
	}
	public List<String> getItems() {
		List<String> items = new ArrayList<String>();
		for(int i = 0; i < main.startupcombobox.getItemCount(); i++) {
			String item=main.startupcombobox.getItemAt(i);
			items.add(item);
		}
		return items;
	}
	public boolean Contains(String fileName) {
		for(int i = 0; i < main.startupcombobox.getItemCount(); i++) {
			String item=main.startupcombobox.getItemAt(i);
			if(item != null && item.equals(fileName))
				return true;
		}
		return false;			
	}
	public void Remove() {
		for(String item:getItems()) {
			main.startupcombobox.removeItem(item);	
		}
	
		main.startupcombobox.validate();
		main.startupcombobox.repaint();
	}
}
