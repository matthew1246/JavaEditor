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
** The file is kept in the user home directory so the same list is shared
** no matter which folder the editor is started from or edits, because
** user.dir changes with the working directory of the launched program.
** The newest entry is always first and only the latest 10 entries are kept.
** Entries are never duplicated, paths are compared ignoring case
** because the same file can be reached with a different spelling.
**
** The list is held in memory and recents.txt is only read once, when the
** editor starts. Every add changes the list in memory first and then saves
** it, so a new entry can never throw away the entries that were already
** there. Reading recents.txt again on every add used to lose the whole
** list whenever that read did not work, and the next add then saved a
** recents.txt holding only the one new file.
**
** Only java.io and java.util are used here, no java.nio.file and no
** lambdas, so this file compiles on every java version the editor can
** compile for when it makes a jar.
*/
public class Recents {
	public static final int max = 10;
	/*
	** The list in memory. It is null until recents.txt has been read once.
	** Every method that touches it is synchronized on lock, and the lock is
	** static because each caller uses its own new Recents() object.
	*/
	private static List<String> saved = null;
	private static final Object lock = new Object();
	private String getRecentsDir() {
		return System.getProperty("user.home");
	}
	private File getRecentsFile() {
		return new File(getRecentsDir() + File.separator + "recents.txt");
	}
	private File getBackupFile() {
		return new File(getRecentsDir() + File.separator + "recents.txt.bak");
	}
	private File getTempFile() {
		return new File(getRecentsDir() + File.separator + "recents.txt.tmp");
	}
	/*
	** Waits a short time before trying a file again.
	** Returns false when the wait was interrupted and the caller should stop.
	*/
	private boolean waitBeforeRetry() {
		try {
			Thread.sleep(25);
			return true;
		}
		catch(InterruptedException interrupted) {
			Thread.currentThread().interrupt();
			return false;
		}
	}
	private boolean contains(List<String> recents,String entry) {
		for(int i = 0; i < recents.size(); i++) {
			if(recents.get(i).equalsIgnoreCase(entry))
				return true;
		}
		return false;
	}
	private void removeEntry(List<String> recents,String entry) {
		for(int i = recents.size() - 1; i >= 0; i--) {
			if(recents.get(i).equalsIgnoreCase(entry))
				recents.remove(i);
		}
	}
	/*
	** Reads a whole file as text. Returns null when it cannot be read.
	*/
	private String readText(File file) {
		if(!file.exists())
			return null;
		FileReader filereader = null;
		try {
			filereader = new FileReader(file);
			StringBuilder stringbuilder = new StringBuilder();
			char[] buffer = new char[512];
			int read = filereader.read(buffer);
			while(read != -1) {
				stringbuilder.append(buffer,0,read);
				read = filereader.read(buffer);
			}
			filereader.close();
			filereader = null;
			return stringbuilder.toString();
		}
		catch(IOException ex) {
			return null;
		}
		finally {
			if(filereader != null) {
				try {
					filereader.close();
				}
				catch(IOException ex) {
				}
			}
		}
	}
	private void writeText(File file,String contents) throws IOException {
		PrintWriter printwriter = new PrintWriter(file);
		printwriter.print(contents);
		printwriter.close();
	}
	/*
	** Turns saved text into the list shown by the menu, dropping blanks and
	** duplicates and keeping only the newest max entries.
	*/
	private List<String> parse(String contents) {
		List<String> recents = new ArrayList<String>();
		if(contents == null)
			return recents;
		GsonBuilder gsonbuilder=new GsonBuilder();
		gsonbuilder.setPrettyPrinting();
		Gson gson = gsonbuilder.create();
		List<String> list = null;
		try {
			TypeToken<List<String>> typetoken = new TypeToken<List<String>>(){};
			list = gson.fromJson(contents,typetoken.getType());
		}
		catch(RuntimeException ex) {
			return recents;
		}
		if(list == null)
			return recents;
		for(int i = 0; i < list.size(); i++) {
			String entry = list.get(i);
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
		return recents;
	}
	/*
	** Reads recents.txt the first time it is needed. The last good copy in
	** recents.txt.bak is used when recents.txt cannot be read, so the list
	** is not empty just because that one read did not work.
	*/
	private List<String> load() {
		for(int attempt = 0; attempt < 40; attempt++) {
			String contents = readText(getRecentsFile());
			if(contents != null && !contents.trim().equals("")) {
				return parse(contents);
			}
			if(attempt < 39) {
				if(!waitBeforeRetry())
					break;
			}
		}
		return parse(readText(getBackupFile()));
	}
	/*
	** Returns the list in a copy, so a caller cannot change it by accident.
	*/
	private List<String> list() {
		if(saved == null)
			saved = load();
		return new ArrayList<String>(saved);
	}
	/*
	** Saves the list in memory to recents.txt.
	** The new text goes to a temp file and is then renamed over recents.txt,
	** so a reader can never see a half written file. Windows has no rename
	** over an existing file, so recents.txt is briefly missing during a save.
	** recents.txt.bak keeps the last good copy for load to fall back on.
	*/
	private void save() {
		try {
			GsonBuilder gsonbuilder = new GsonBuilder();
			gsonbuilder.setPrettyPrinting();
			Gson gson = gsonbuilder.create();
			String contents = gson.toJson(saved);
			File recentsfile = getRecentsFile();
			File tempfile = getTempFile();
			writeText(tempfile,contents);
			boolean written = false;
			for(int attempt = 0; attempt < 40 && !written; attempt++) {
				if(recentsfile.exists() && !recentsfile.delete()) {
					if(!waitBeforeRetry())
						break;
					continue;
				}
				if(tempfile.renameTo(recentsfile)) {
					written = true;
					break;
				}
				if(!waitBeforeRetry())
					break;
			}
			if(!written) {
				writeText(recentsfile,contents);
				tempfile.delete();
			}
			writeText(getBackupFile(),contents);
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	/*
	** The recent files, newest first.
	*/
	public List<String> get() {
		synchronized(lock) {
			return list();
		}
	}
	/*
	** Replaces the whole list.
	*/
	public void set(List<String> recents) {
		if(recents == null)
			return;
		synchronized(lock) {
			if(saved == null)
				saved = load();
			saved = new ArrayList<String>();
			for(int i = 0; i < recents.size(); i++) {
				String entry = recents.get(i);
				if(entry == null)
					continue;
				entry = entry.trim();
				if(entry.equals(""))
					continue;
				if(contains(saved,entry))
					continue;
				saved.add(entry);
			}
			while(saved.size() > max)
				saved.remove(saved.size()-1);
			save();
		}
	}
	/*
	** Puts one file at the front of the list and keeps the newest max files.
	*/
	public void add(String entry) {
		if(entry == null)
			return;
		String recent = entry.trim();
		if(recent.equals(""))
			return;
		synchronized(lock) {
			if(saved == null)
				saved = load();
			removeEntry(saved,recent);
			saved.add(0,recent);
			while(saved.size() > max)
				saved.remove(saved.size()-1);
			save();
		}
	}
}
