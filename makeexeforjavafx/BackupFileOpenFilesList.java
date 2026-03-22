import java.util.*;
import java.io.*;
public class BackupFileOpenFilesList extends OpenFilesList {
	@Override
	public List<String> getFileList() {
	 	StoreSelectedFile ssf = new StoreSelectedFile();
	 	String fileName=ssf.get();
	 	if(!fileName.equals("")) {
			return getFileList(fileName);
		}
		else {
			return getEmptyList();
		}
	}
}
