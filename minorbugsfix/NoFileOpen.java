import java.util.regex.*;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.io.File;
public class NoFileOpen {
	public JTextArea textarea;
	public NoFileOpen(JTextArea textarea) {
		this.textarea = textarea;
	}		
	public String getFileName() {
		StoreSelectedFile storeselectedfile = new StoreSelectedFile();
		String previousfile=storeselectedfile.get();
		if(previousfile.equals("")) {
			return "";
		}
		String output =previousfile.replaceAll("[^\\\\]+\\.java","");
		
		GetClassName getclassname = new GetClassName(textarea);
		String classname=getclassname.getClassName();
		
		String fileName = output+classname+".java";
		File file = new File(fileName);
		if(!file.exists()) {
			storeselectedfile.set(fileName);
			return fileName;
		}
		else { // If file already exists.
			int yesorno=JOptionPane.showConfirmDialog(null,"Overwrite existing file?","Do you want overwrite the existing file with this code?",JOptionPane.YES_NO_OPTION);
			switch(yesorno) {
				case JOptionPane.YES_OPTION:
					storeselectedfile.set(fileName);
					return fileName;
				case JOptionPane.NO_OPTION:
					return "";
			}
		}
		return "";
	}
}

		