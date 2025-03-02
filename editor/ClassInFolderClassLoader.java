import java.io.*;
import javax.swing.JOptionPane;
public class ClassInFolderClassLoader extends ClassLoader {
	
	protected String folder;
	public ClassInFolderClassLoader(String folder) {
		super(ClassLoader.getSystemClassLoader());
		this.folder = folder;
	}
	
	protected Class<?> findClass(String classname) throws ClassNotFoundException {
		try {
		FileInputStream fileinputstream;
		if(classname.contains("\\")) {
			fileinputstream = new FileInputStream(classname+".class");
		}
		else {
			fileinputstream = new FileInputStream(folder+classname+".class");
		}
		byte[] bytes=new byte[fileinputstream.available()];
		fileinputstream.read(bytes);
		if(bytes == null) throw new ClassNotFoundException("Class "+classname+" not found.");
		
		if(classname.contains("\\")) {
			classname = classname.replace(folder,"");
		}
		Class<?> classquestionmark=defineClass(classname,bytes,0,bytes.length);
		return classquestionmark;
		} catch(FileNotFoundException ex) {
			//ex.printStackTrace();
			throw new ClassNotFoundException("File could not be found!");
		} catch(IOException ex) {
			//ex.printStackTrace();
			throw new ClassNotFoundException("IOException "+ex.getMessage());
		}
	}
}