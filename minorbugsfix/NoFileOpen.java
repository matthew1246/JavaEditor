import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
public class NoFileOpen {
	public JTabbedPane tabbedpane;
	public JTextArea textarea;
	public NoFileOpen(JTextArea textarea,JTabbedPane tabbedpane) {
		this.textarea = textarea;
		this.tabbedpane = tabbedpane;
	}		
	public String getFileName() {
		StoreSelectedFile storeselectedfile = new StoreSelectedFile();
		String previousfile=storeselectedfile.get();
		if(previousfile.equals("")) {
			return CreateFile();
		}
		String output =previousfile.replaceAll("[^\\\\]+\\.java","");
		
		GetClassName getclassname = new GetClassName(textarea);
		String classname=getclassname.getClassName();
		
		String fileName = output+classname+".java";
		File file = new File(fileName);
		if(!file.exists()) {
			saveTabs(fileName);
			return fileName;
		}
		else { // If file already exists.
			int yesorno=JOptionPane.showConfirmDialog(null,"Overwrite existing file?","Do you want overwrite the existing file with this code?",JOptionPane.YES_NO_OPTION);
			switch(yesorno) {
				case JOptionPane.YES_OPTION:
					saveTabs(fileName);
					return fileName;
				case JOptionPane.NO_OPTION:
					return CreateFile();
			}
		}
		return "";
	}
	public void saveTabs(String fileName) {
		StoreSelectedFile storeselectedfile12 = new StoreSelectedFile();
		List<String> tabs = storeselectedfile12.getTabs();
		int tabsize = tabbedpane.getTabCount();
		int selectedIndex = tabbedpane.getSelectedIndex();
		// Ensure tabs list matches tab count
		while (tabs.size() < tabsize) {
			tabs.add("");
		}
		// Set fileName for the selected tab
		tabs.set(selectedIndex, fileName);
		storeselectedfile12.setTabs(tabs);
	}
	public String CreateFile() {
		while(true) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filenameextensionfilter= new FileNameExtensionFilter("Save as .java","java");
			fileChooser.setFileFilter(filenameextensionfilter);
			int status =fileChooser.showSaveDialog(textarea);
			if(status == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				
				String fileName= file.getPath();
				fileName=Main.addDotJava(fileName);
				
				saveTabs(fileName);
				
				return fileName;
			}
		}
	}
}

