import java.util.*;
public class CurrentEditorFilenameOpenFilesList extends OpenFilesList {
	public List<String> filelist = new ArrayList<String>();
	// public String current_editorfilename;
	public CurrentEditorFilenameOpenFilesList(String savedfilename) {
		filelist = getFileList(savedfilename);
	}
	@Override
	public List<String> getFileList() {
		return filelist;
	}
}
		