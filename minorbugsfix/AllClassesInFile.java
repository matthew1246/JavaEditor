import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import java.util.LinkedHashMap;
public class AllClassesInFile {
	public List<String> classes = new ArrayList<String>();	
	public AllClassesInFile(JTextArea textarea) {
		GetClassMethods getclassmethods = new GetClassMethods(textarea);
		LinkedHashMap<String,Integer> classnames = getclassmethods.getClasses();
		LinkedHashMapInterface<String,Integer> iterator2=new LinkedHashMapInterface<String,Integer>(classnames) {
			public void KeyAndValue(String key,Integer integer) {
				classes.add(key);
			}
		};		
		iterator2.iterate();				
	}
}