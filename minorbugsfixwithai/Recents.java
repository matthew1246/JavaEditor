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
** Saving keeps the last good copy in recents.txt.bak and reading falls back
** to it, so the list never shows up empty while recents.txt is being
** replaced. Windows has no rename over an existing file, so recents.txt is
** briefly missing during a save and a reader can land in that moment.
**
** Only java.io and java.util are used here, no java.nio.file and no
** lambdas, so this file compiles on every java version the editor can
** compile for when it makes a jar.
*/
public class Recents {
	public static final int max = 10;
	/*
	** Every add is a read modify write of recents.txt, so all of them are
	** done under one lock. The lock is static because each caller uses its
	** own new Recents() object, an instance lock would not be shared.
	*/
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
	** Waits a short time before trying recents.txt again.
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
	** Turns the saved text into the list shown by the menu, dropping blanks
	** and duplicates and keeping only the newest max entries.
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
	public List<String> get() {
		File recentsfile = getRecentsFile();
		/*
		** Replacing recents.txt can make the read fail or find the file missing
		** for a moment on Windows, so the read is retried instead of reporting
		** an empty Recent Files menu.
		*/
		for(int attempt = 0; attempt < 40; attempt++) {
			String contents = readText(recentsfile);
			if(contents != null && !contents.trim().equals("")) {
				List<String> recents = parse(contents);
				if(!recents.isEmpty())
					return recents;
			}
			if(attempt < 39) {
				if(!waitBeforeRetry())
					break;
			}
		}
		/*
		** recents.txt could not be read, the last good copy is used instead of
		** showing an empty Recent Files menu.
		*/
		List<String> recents = parse(readText(getBackupFile()));
		return recents;
	}
	public void set(List<String> recents) {
		synchronized(lock) {
			try {
				GsonBuilder gsonbuilder = new GsonBuilder();
				gsonbuilder.setPrettyPrinting();
				Gson gson = gsonbuilder.create();
				String contents = gson.toJson(recents);
				File recentsfile = getRecentsFile();
				/*
				** The new contents are written to a temp file and then renamed over
				** recents.txt so a reader can never see a half written file.
				** Writing recents.txt directly empties it first, and a reader
				** during that moment would lose every saved entry.
				*/
				File tempfile = getTempFile();
				writeText(tempfile,contents);
				/*
				** Windows refuses to rename over recents.txt while any program
				** has it open, so the rename is retried before giving up and
				** writing recents.txt directly.
				*/
				boolean saved = false;
				for(int attempt = 0; attempt < 40 && !saved; attempt++) {
					if(recentsfile.exists() && !recentsfile.delete()) {
						if(!waitBeforeRetry())
							break;
						continue;
					}
					if(tempfile.renameTo(recentsfile)) {
						saved = true;
						break;
					}
					if(!waitBeforeRetry())
						break;
				}
				if(!saved) {
					writeText(recentsfile,contents);
					tempfile.delete();
				}
				/*
				** The saved contents are now in recents.txt, so the copy that was
				** in recents.txt becomes the last good copy kept for reading.
				*/
				writeText(getBackupFile(),contents);
			}
			catch(FileNotFoundException ex) {
				ex.printStackTrace();
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public void add(String entry) {
		if(entry == null)
			return;
		String recent = entry.trim();
		if(recent.equals(""))
			return;
		synchronized(lock) {
			List<String> recents = get();
			removeEntry(recents,recent);
			recents.add(0,recent);
			while(recents.size() > max)
				recents.remove(recents.size()-1);
			set(recents);
		}
	}
}
