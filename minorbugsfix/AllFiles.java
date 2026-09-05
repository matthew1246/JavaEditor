import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
/*
** This is Main.jar and for all previous versions of java. 
*/
public class AllFiles {
	public String main;	
	public String dir;	
	public List<String> files = new ArrayList<String>();
	public AllFiles(String main,String dir) {
		this.main = main;	
		this.dir = dir;	
		for(int i = 0; i < 23; i++) {
			files.add(dir+"ForJava"+(i+1)+"_"+main+".jar");
		}
		files.add(dir+main+".jar");
	}
	public boolean isSameDirectory(Main main2) {
		try {	
			String filename=AllFiles.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			String dir2 = filename.replaceAll("[^/]+\\.jar","");
			if(dir2.startsWith("/"))
				dir2=dir2.substring(1,dir2.length());
			dir2=dir2.replace("/","\\");
		
			String filename2 =filename.replaceAll(".+/","");
			if(dir.equals(dir2)) {
				filename2=dir+filename2;	
				if(filename2.endsWith(".jar") && !files.contains(filename2))
					files.add(filename2);
			}
			
			if(dir.equals(dir2)) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null,dir+" "+dir2);
				dir=Main.getDirectory(main2.fileName);
				if(filename.startsWith("/"))
					filename=filename.substring(1,filename.length());
				filename=filename.replace("/","\\");
				dir2=Main.getDirectory(filename);
				if(dir.equals(dir2)) {
					filename2=dir+filename2;	
					if(filename2.endsWith(".jar") && !files.contains(filename2))
						files.add(filename2);
						
					return true;
				}
				else {
					JOptionPane.showMessageDialog(null,main2.fileName+":"+dir+" "+filename+":"+dir2);
					return false;	
				}
			}				
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public boolean exists() {
		List<String> files2 = new ArrayList<String>();
		for(String filename:files) {
			File file = new File(filename);	
			if(file.exists()) {
				files2.add(filename);
			}
		}
		files = files2;
		return files.size() > 0;
	}
	public boolean delete() {
		for(String filename:files) {
			File file = new File(filename);	
			if(!file.delete())
				return false;
		}
		return true;
	}
}