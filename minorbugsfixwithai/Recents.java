import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.*;

/*
** Recent files list of the "Recent Files" menu stored in recents.txt.
** The newest entry is always first and only the latest 10 entries are kept.
** Entries are never duplicated, paths are compared ignoring case
** because the same file can be reached with a different spelling.
*/
public class Recents {
	public static final int max = 10;
	private String getRecentsDir() {
		String userDir = System.getProperty("user.dir");
		if(userDir.replace("\\","").matches("[a-zA-Z]:")) {
			return System.getProperty("user.home");
		}
		return userDir;
	}
	private File getRecentsFile() {
		return new File(getRecentsDir() + File.separator + "recents.txt");
	}
	private boolean contains(List<String> recents,String entry) {
		for(String saved:recents) {
			if(saved.equalsIgnoreCase(entry))
				return true;
		}
		return false;
	}
	public List<String> get() {
		List<String> recents = new ArrayList<String>();
		GsonBuilder gsonbuilder=new GsonBuilder();
		gsonbuilder.setPrettyPrinting();
		Gson gson = gsonbuilder.create();
		File recentsfile = getRecentsFile();
		if(!recentsfile.exists())
			return recents;
		try {
			TypeToken<List<String>> typetoken = new TypeToken<List<String>>(){};
			FileReader filereader = new FileReader(recentsfile);
			List<String> list = gson.fromJson(filereader,typetoken.getType());
			filereader.close();
			if(list != null) {
				for(String entry:list) {
					if(entry == null)
						continue;
					entry = entry.trim();
					if(entry.equals(""))
						continue;
					if(contains(recents,entry))
						continue;
					recents.add(entry);
					if(recents.size() >= max)
						break;
				}
			}
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return recents;
	}
	public void set(List<String> recents) {
		try {
			GsonBuilder gsonbuilder = new GsonBuilder();
			gsonbuilder.setPrettyPrinting();
			Gson gson = gsonbuilder.create();
			String contents = gson.toJson(recents);
			PrintWriter printwriter=new PrintWriter(getRecentsFile());
			printwriter.print(contents);
			printwriter.close();
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	public void add(String entry) {
		if(entry == null)
			return;
		String recent = entry.trim();
		if(recent.equals(""))
			return;
		List<String> recents = get();
		recents.removeIf(saved -> saved.equalsIgnoreCase(recent));
		recents.add(0,recent);
		while(recents.size() > max)
			recents.remove(recents.size()-1);
		set(recents);
	}
}