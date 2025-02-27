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
		JOptionPane.showMessageDialog(null,"supery "+folder+classname+".class");
		//FileInputStream fileinputstream = new FileInputStream(folder+classname+".class");
		FileInputStream fileinputstream = new FileInputStream(classname+".class");
		byte[] bytes=new byte[fileinputstream.available()];
		fileinputstream.read(bytes);
		if(bytes == null) throw new ClassNotFoundException("Class "+classname+" not found.");
		
		classname = classname.replace(folder,"");
		Class<?> classquestionmark=defineClass(classname,bytes,0,bytes.length);
		return classquestionmark;
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,"FileNotFoundException in ClassInFolderClassLoader");
			throw new ClassNotFoundException("File could not be found!");
		} catch(IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,"IOException in ClassInFolderClassLoader");
			throw new ClassNotFoundException("IOException "+ex.getMessage());
		}
	}
}