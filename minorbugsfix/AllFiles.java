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
	public boolean isSameDirectory() {
		try {	
			String filename=AllFiles.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			String dir2 = filename.replaceAll("[^/]+\\.jar","");
			if(dir2.startsWith("/"))
				dir2=dir2.substring(1,dir2.length());
			dir2=dir2.replace("/","\\");
		
			filename=filename.replaceAll(".+/","");
			if(dir.equals(dir2)) {
				filename=dir+filename;	
				files.add(filename);
			}
			return dir.equals(dir2);
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public boolean exists() {
		List<String> files2 = new ArrayList<String>();
		for(String filename:files) {
			File file = new File(filename);	
			if(file.exists()) {
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