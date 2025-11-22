import javax.swing.JComboBox;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class StarterJComboBox {
	protected Main main;
	protected String fileName;
	public StarterJComboBox(String fileName,Main main) {
		this.fileName = fileName;
		this.main = main;
		if(!fileName.equals("")) {
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Remove();
			List<String> starterclasses= storeselectedfile.getStartupComboBox(fileName);
			for(String starterclass2:starterclasses) {
				main.startupcombobox.addItem(starterclass2);
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
				Remove();
				if( !Main.getDirectory(filename).equals(Main.getDirectory(fileName)) ) { // Not same folder
					getCacheAndAddToComboBox(filename);
				}
				else { // same folder
					if(main.lock.isSelected()) {
						String  selected=main.startupcombobox.getSelectedItem();
						
				}
				this.fileName = filename;	
			}
		}								
	}
	public List<String> getCache() {
		
	public void getCacheAndAddToComboBox(String filename) {
		StoreSelectedFile storeselectedfile = new StoreSelectedFile();				List<String> starterclasses= storeselectedfile.getStartupComboBox(filename);
		for(String starterclass2:starterclasses) {
			main.startupcombobox.addItem(starterclass2);
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
		main.startupcombobox.validate();			main.startupcombobox.repaint();
	}
	public void Add(String starterclass) {
		main.startupcombobox.addItem(starterclass);
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
			if(item.equals(fileName))
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