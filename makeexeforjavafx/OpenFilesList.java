import java.util.*;
import java.io.*;
public abstract class OpenFilesList {
	
	public abstract List<String> getFileList();
	
	public List<String> getFileList(String filenameanddirectory) {
		String current_editorfilename =filenameanddirectory.replaceAll(".+\\\\","");		
		File currentdirectory = new File(filenameanddirectory.replaceAll("[^\\\\]+\\.java",""));
		
		File[] files=currentdirectory.listFiles();
		List<String> stringlist = new ArrayList<String>();
		stringlist.add(current_editorfilename);
		for(File file:files) {
			String filename=file.getName();
			if( !current_editorfilename.equals(filename) && filename.contains(".java") ) {
				stringlist.add(filename);
			}				
		}
		return stringlist;
	}
	
	public List<String> getEmptyList() 	{
		List<String> filenames= new ArrayList<String>();
		filenames.add("");
		return filenames;
	}
}
		
		