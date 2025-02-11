import java.io.File;
import java.util.List;
import java.util.ArrayList;
/*
** This is Main.jar and for all previous versions of java. 
*/
public class AllFiles {
	public String[] files = new String[24];
	public AllFiles(String main,String dir) {
		for(int i = 0; i < 23; i++) {
			files[i] = dir+"ForJava"+(i+1)+"_"+main+".jar";
		}
		files[23] = dir+main+".jar";
	}
	public boolean exists() {
		List<String> files2 = new ArrayList<String>();
		for(int i = 0; i < files.length; i++) {
			File file = new File(files[i]);
			if(file.exists()) {
				files2.add(files[i]);
			}
		}
		files = files2.toArray(new String[files2.size()]);
		return files.length > 0;
	}
	public boolean delete() {
		for(int i = 0; i < files.length; i++) {
			File file = new File(files[i]);
			if(!file.delete())
				return false;
		}
		return true;
	}
}