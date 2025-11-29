import java.util.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.*;
public class GetClassMethods {
	private FindMethodName findmethodname;
	private GetClassName getclassname;
	private JTextArea textarea;
	public GetClassMethods(JTextArea textarea) {
		this.textarea = textarea;
		findmethodname = new FindMethodName(textarea);
		getclassname = new GetClassName(textarea);
	}
	
	public LinkedHashMap<String,Integer> getClasses() {
		LinkedHashMap<String,Integer> classnames2 = new LinkedHashMap<String,Integer>();
		try {
			Stack<Integer> stackleftcurlybrace2 = new Stack<Integer>();
			String wholetext=textarea.getText();
			wholetext=RemoveAll.LeftCurlyBraceInsideComments(wholetext);
			//wholetext=RemoveAll.Comments(wholetext);
			//wholetext=RemoveAll.Strings(wholetext);
			//System.out.println(wholetext);
			FindMethodName findmethodname2 = new FindMethodName(wholetext); 
			GetClassName getclassname2 = new GetClassName(wholetext);
			String classname="Didn't find class name before method name.";
			for(int i = 0; i < wholetext.length(); i++) {
				String str = wholetext.substring(i,i+1);
				switch(str) {
					case "{":
						stackleftcurlybrace2.push(i);
						if(stackleftcurlybrace2.size() == 1) {
							classname = getclassname2.getClassName(i);
						}
					break;
					case "}":
						int leftcurlybrace=(Integer)stackleftcurlybrace2.pop();
						if(stackleftcurlybrace2.size() == 0) {
							classnames2.put(classname,leftcurlybrace);
						}
					break;
				}
			}
		} catch (EmptyStackException ex) {
			ex.printStackTrace();
			return classnames2;
		}
		return classnames2;
	}
	
	public LinkedHashMap<String,LinkedHashMap<String,Integer>> getMethods() {
		LinkedHashMap<String,LinkedHashMap<String,Integer>> classnamesandmethods = new LinkedHashMap<String,LinkedHashMap<String,Integer>>();
		try {
				Stack<Integer> stackleftcurlybrace = new Stack<Integer>();
	
			String wholetext=textarea.getText();
			wholetext=RemoveAll.LeftCurlyBraceInsideComments(wholetext);
			//wholetext=RemoveAll.Comments(wholetext);
			FindMethodName findmethodname2=new FindMethodName(wholetext);
			GetClassName getclassname2 = new GetClassName(wholetext);
			String classname="Didn't find class name before method name.";
			for(int i = 0; i < wholetext.length(); i++) {
				String str = wholetext.substring(i,i+1);
				switch(str) {
					case "{":
						stackleftcurlybrace.push(i);
						if(stackleftcurlybrace.size() == 1) {
							classname = getclassname2.getClassName(i);
							classnamesandmethods.put(classname,new LinkedHashMap<String,Integer>());
						}
					break;
					case "}":
						int leftcurlybrace=(Integer)stackleftcurlybrace.pop();
						if(stackleftcurlybrace.size() == 1) {
							String methodname=findmethodname2.getMethodName(leftcurlybrace);
							classnamesandmethods.get(classname).put(methodname,leftcurlybrace);
						}
					break;
				}
			}
			/*JOptionPane.showMessageDialog(null,"start");
			for(String key : classnamesandmethods.keySet()) {
				JOptionPane.showMessageDialog(null,key);
			}
			JOptionPane.showMessageDialog(null,"end");
			*/
		} catch (EmptyStackException ex) {
			ex.printStackTrace();
			return classnamesandmethods;
		}				
		return classnamesandmethods;
	}
	
	public List<String> getMains(FileListModifier filelistmodifier) {
		TreeSet<String> mainclasses = new TreeSet<String>();
		try {
			List<String> filelist = filelistmodifier.getFileList();
			String directoryandfilename = filelistmodifier.directoryandfilename;
			String directory = directoryandfilename.replaceAll("[^\\\\]+\\.java","");
			
			for(String filename:filelist) {
				Stack<Integer> stackleftcurlybrace = new Stack<Integer>();
				String classname = "empty";
				String wholetext=Files.readString(Path.of(directory+filename));
				wholetext=RemoveAll.LeftCurlyBraceInsideComments(wholetext);
				//wholetext=RemoveAll.Comments(wholetext);
				GetMainClasses getmainclasses = new GetMainClasses(wholetext);
				FindMethodNameString findmethodnamestring = new FindMethodNameString(wholetext);
				
				for(int i = 0; i < wholetext.length(); i++) {
					String str = wholetext.substring(i,i+1);
				
					switch(str) {
						case "{":
							stackleftcurlybrace.push(i);
							if(stackleftcurlybrace.size() == 1) {
								classname = getmainclasses.getClassName(i);
							}
						break;
						case "}":
							if(stackleftcurlybrace.size() > 0) {
								int leftcurlybrace=(Integer)stackleftcurlybrace.pop();
							
								if(stackleftcurlybrace.size() == 1) {
									String methodname=findmethodnamestring.getMethodName(leftcurlybrace);
									if(methodname.equals("main"))
										if(getmainclasses.isMainClass(leftcurlybrace)) 
											if(!classname.equals("empty"))
												mainclasses.add(classname);			
								}
							}
						break;
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch(EmptyStackException ex) {
			ex.printStackTrace();
			return new ArrayList<String>();
		}
		return new ArrayList<String>(mainclasses);
	}
}
