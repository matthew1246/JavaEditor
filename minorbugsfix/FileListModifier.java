import java.util.*;
import java.io.*;
public class FileListModifier implements Cloneable {
    public List<String> original = new ArrayList<String>();
    public boolean isEmpty = true;
    public List<String> filelist = new ArrayList<String>();
    public String directoryandfilename;
    public void setSelected(String selected) {
    	// filelist.remove(selected);
    	filelist.add(0,selected);
    }
    public FileListModifier(String fileName) {
    	fillList(fileName);
    }
    public FileListModifier() {
    }
    
    public boolean isEmpty() {
    	return isEmpty;
    }
    public List<String> getFileList() {
		return filelist;	
	}


    public void setToMostRecentAfterSelected(String previous) {
        // filelist.remove(previous);
        filelist.add(1,previous);
    }

    public void setAlphabetOrderAfterMostRecent() {
        String mostrecent=filelist.get(0);
        Collections.sort(filelist);
        int indexOfMostRecent=filelist.indexOf(mostrecent);
        for(int i = 0; i < indexOfMostRecent; i++) {
            String top=filelist.get(0);
            filelist.add(top);
        }
    }
    public void addNew() {
        filelist.add(0,"<Select Class>");
    }

    public void blank() {
        filelist = new ArrayList<String>();
    }
    
    public List<String> getEmptyList() 	{
		List<String> filenames= new ArrayList<String>();
		filenames.add("");
		return filenames;
	}
	public void addBlank() {
		filelist.add(0,"");
	}
	
	public void fillList(String filenameanddirectory) {
		directoryandfilename = filenameanddirectory;
		if(!filenameanddirectory.equals("")) {
			String current_editorfilename =filenameanddirectory.replaceAll(".+\\\\","");		
			File currentdirectory = new File(filenameanddirectory.replaceAll("[^\\\\]+\\.java",""));
			
			File[] files=currentdirectory.listFiles();
			Arrays.sort(files,new FileComparator());
			filelist = new ArrayList<String>();
			//filelist.add(current_editorfilename);
			for(File file:files) {
				String filename=file.getName();
				if(filename.contains(".java") ) {
					filelist.add(filename);
				}						
			}
			isEmpty = false;
		}
		
		if(!filenameanddirectory.equals("")) {
			String current_editorfilename =filenameanddirectory.replaceAll(".+\\\\","");		
			File currentdirectory = new File(filenameanddirectory.replaceAll("[^\\\\]+\\.java",""));
			
			File[] files=currentdirectory.listFiles();
			List<File> originalfiles = new ArrayList<File>();			

			for(File file:files) {
				String filename=file.getName();
				if(filename.contains(".java") ) {
					originalfiles.add(file);
				}						
			}
			original = new ArrayList<String>();
			//Collections.sort(originalfiles,new FileComparator());
			for(File file:originalfiles) {
				original.add(file.getName());
			}			
			isEmpty = false;
		}
	}
	
	@Override
	public String toString() {
		String lines="original:\n";
		for(String string:original) {
			lines+=string+"\n";
		}
		lines+="filelist:\n";
		for(String string:filelist) {
			lines+=string+"\n";
		}
		return lines;
	}
	@Override
	public Object clone() {
		List<String> newList = new ArrayList<String>();
		for(String string:filelist) {
			newList.add(string);
		}
		FileListModifier filelistmodifier = new FileListModifier();
		filelistmodifier.filelist = newList;
		filelistmodifier.isEmpty = false;
		return filelistmodifier;
	}
}
        