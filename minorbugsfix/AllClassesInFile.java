import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import java.util.LinkedHashMap;
public class AllClassesInFile {
	public String fileName;
	public List<String> classes = new ArrayList<String>();	
	public AllClassesInFile(JTextArea textarea,String fileName) {
		if(!fileName.equals("")) {
			this.fileName = fileName;
			try {
				GetClassMethods getclassmethods = new GetClassMethods(textarea);
				LinkedHashMap<String,Integer> classnames = getclassmethods.getClasses();
				LinkedHashMapInterface<String,Integer> iterator2=new LinkedHashMapInterface<String,Integer>(classnames) {
					public void KeyAndValue(String key,Integer integer) {
						classes.add(key);
					}
				};		
				iterator2.iterate();	
			} catch(Exception ex) {
				ex.printStackTrace();
			}											
		}		
	}
	public void ChangeFile(JTextArea textarea,String fileName) {
		if(!this.fileName.equals(fileName) && (!fileName.equals(""))) {
			this.fileName = fileName;		
			classes = new ArrayList<String>();
			try {
				GetClassMethods getclassmethods = new GetClassMethods(textarea);
				LinkedHashMap<String,Integer> classnames = getclassmethods.getClasses();
				LinkedHashMapInterface<String,Integer> iterator2=new LinkedHashMapInterface<String,Integer>(classnames) {
					public void KeyAndValue(String key,Integer integer) {
						classes.add(key);
					}
				};		
				iterator2.iterate();	
			} catch(Exception ex) {
				ex.printStackTrace();
			}	
		}
	}		
		
}