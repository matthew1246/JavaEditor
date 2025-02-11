import java.util.*;
public class OpenNewWindowOpenFilesList extends OpenFilesList {
	private String filenameanddirectory;
	public OpenNewWindowOpenFilesList(String previousfilenameanddirectory) {
		this.filenameanddirectory = previousfilenameanddirectory;
	}
	public List<String> getFileList() {
		List<String> list =getFileList(filenameanddirectory);
		list.add(0,"<Click here for file>");
		return list;
	}
}
