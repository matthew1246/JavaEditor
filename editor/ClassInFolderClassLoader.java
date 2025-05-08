import java.io.*;
import javax.swing.JOptionPane;
public class ClassInFolderClassLoader extends ClassLoader {
	
	protected String folder;
	public ClassInFolderClassLoader(String folder) {
		super(ClassLoader.getSystemClassLoader());
		this.folder = folder;
	}
	@Override 
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(!name.contains("\\") && !name.contains(".")) {
			name=folder+name;
		}
		JOptionPane.showMessageDialog(null,"loadClass:"+name);
		return super.loadClass(name);
	}
	protected Class<?> findClass(String classname) throws ClassNotFoundException {
		try {
		FileInputStream fileinputstream;
		if(classname.contains("\\")) {
			fileinputstream = new FileInputStream(classname+".class");
		}
		else {
			/*if(classname.contains("$")) {
				String[] classes=classname.split("$");
				String class1 = classes[0];
				Class<?> parent=findClass(class1);
				Class<?>[] classes2=parent.getDeclaredClasses();
				for(Class<?> class2 : classes2) {
					if(classes[1].equals(class2.getName())) {
						parent
			}*/
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