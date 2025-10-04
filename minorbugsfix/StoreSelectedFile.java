import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import javax.swing.JOptionPane;
public class StoreSelectedFile {
	FileWriter filewriter;
	public static void main(String[] args) 	{
		Gson gson = new Gson();
		LinkedHashMap<String,Preferences> linkedhashmap = new LinkedHashMap<String,Preferences>();
		Preferences preferences=new Preferences();
		preferences.starterclass="Main";
		preferences.jars.add("C:\\gson.jar");
		linkedhashmap.put("test",preferences);
		String string=gson.toJson(linkedhashmap);
		System.out.println(string);
		TypeToken<LinkedHashMap<String,Preferences>> typetoken=new TypeToken<LinkedHashMap<String,Preferences>>(){};
		LinkedHashMap<String,Preferences> linkedhashmap2=gson.fromJson(string,typetoken.getType());
		for(String key:linkedhashmap2.keySet()) {
			System.out.println(key+" "+linkedhashmap2.get(key).starterclass+" "+linkedhashmap2.get(key).jars.get(0));
		}
	}
	public int getCaretPosition(String filename) {
		LinkedHashMap<String,Preferences> linkedhashmap=getBackup();
		if(linkedhashmap.containsKey(filename)) {
			return linkedhashmap.get(filename).caretposition;
		}
		else {
			noduplicate.Delete();			
			Preferences preference = new Preferences();
			linkedhashmap.put(filename,preference);
			setBackup(linkedhashmap);
			return 0;
		}
	}
	public void setCaretPosition(String filename,int position) {
		LinkedHashMap<String,Preferences> linkedhashmap=getBackup();
		if(linkedhashmap.containsKey(filename)) {
			Preferences preferences=linkedhashmap.get(filename);
			if(preferences.caretposition != position) {
				noduplicate.Delete();
				preferences.caretposition = position;
				setBackup(linkedhashmap);
			}
		}
		else {
			noduplicate.Delete();
			Preferences preferences = new Preferences();
			preferences.caretposition = position;
			linkedhashmap.put(filename,preferences);
			setBackup(linkedhashmap);
		}
	}
	private NoDuplicate noduplicate = new NoDuplicate();
	public void set(String filenameandpath) {
		LinkedHashMap<String,Preferences> linkedhashmap = getBackup();
		Preferences preferences = null; // always set in next if else statement
		if(linkedhashmap.containsKey("lastopened")) {
			preferences=linkedhashmap.get("lastopened");
			if(!preferences.starterclass.equals(filenameandpath)) {	
				if( !(new File("original.txt").exists()) ) {
					noduplicate.CreateOriginal();
				}		
			}	
			preferences.starterclass=filenameandpath;
			setBackup(linkedhashmap);
		}
		else {
			preferences = new Preferences();
			preferences.starterclass=filenameandpath;
			linkedhashmap.put("lastopened",preferences);
			setBackup(linkedhashmap);
		}
		if(!linkedhashmap.containsKey(filenameandpath)) {
			noduplicate.Delete();
			preferences = new Preferences();
			
			String directory = filenameandpath.replaceAll("[^\\\\]+\\.java","");
			for(String key:linkedhashmap.keySet()) {
				String directory2 = key.replaceAll("[^\\\\]+\\.java","");
				if(directory.equals(directory2)) {
					Preferences selectedpreferences=linkedhashmap.get(key);
					for(String jar:selectedpreferences.jars) {
						if(!preferences.jars.contains(jar))
							preferences.jars.add(jar);
					}
				}																
			}
			
			linkedhashmap.put(filenameandpath,preferences);
			setBackup(linkedhashmap);
		}
		else {
			return;
		}
	}

	public LinkedHashMap<String,Preferences> getBackup() {
		GsonBuilder gsonbuilder=new GsonBuilder();
		gsonbuilder.setPrettyPrinting();
		Gson gson = gsonbuilder.create();
		File backup = new File("backup.txt");
		if(!backup.exists()) {
			return new LinkedHashMap<String,Preferences>();
		}
		else {
			try {
				TypeToken<LinkedHashMap<String,Preferences>> typetoken = new TypeToken<LinkedHashMap<String,Preferences>>(){};
				FileReader filereader = new FileReader(backup);
				LinkedHashMap<String,Preferences> linkedhashmap= gson.fromJson(filereader,typetoken.getType());
				filereader.close();
				return linkedhashmap;
			}			
			catch(FileNotFoundException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,"Backup.txt file not found.");
				System.exit(0);
			} catch(IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,"IOException");
				System.exit(0);
			}
		}
		System.out.println("StoreSelectedFile.getBackup() this should never be reached.");
		System.exit(0);
		return null; // This line will never be reached.	
	}

	private IsEqual isequal = new IsEqual();
	public void setBackup(LinkedHashMap<String,Preferences> hashmap) {
		try {
			/*if( ((new File("original.txt")).exists() ) && noduplicate.IsDuplicate(hashmap)) {
				//JOptionPane.showMessageDialog(null,"Replace backup.txt with original.txt");
				noduplicate.ReplaceWithOriginal();
				return;
			}
			if(isequal.isEqual(hashmap,getBackup())) {
				return;
			}*/
			GsonBuilder gsonbuilder = new GsonBuilder();
			gsonbuilder.setPrettyPrinting();
			Gson gson = gsonbuilder.create();
			String contents = gson.toJson(hashmap);
			PrintWriter printwriter=new PrintWriter(new File("backup.txt"));
			printwriter.print(contents);		
			printwriter.close();
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,"StoreSelectedFile.setBackup() crashed.");
		}
	}
	
	public void setTabs(List<String> tabs) {
		LinkedHashMap<String,Preferences> hashmap= getBackup();
		Preferences lastopened=hashmap.get("lastopened");
		if(lastopened != null) {
			/*
			System.out.println("StoreSelectedFile:");
			for(String tab:tabs) {
				System.out.println(tab);
			}
			*/
			lastopened.fileNames = tabs;
			setBackup(hashmap);
		}
		else {
			JOptionPane.showMessageDialog(null,"lastopened is null");
		}
	}
	public List<String> getTabs() {
		LinkedHashMap<String,Preferences> hashmap= getBackup();
		Preferences lastopened=hashmap.get("lastopened");
		if(lastopened != null) {
			return lastopened.fileNames;
		}
		else {
			return new ArrayList<String>();
		}
	}
	public String get() {
		LinkedHashMap<String,Preferences> hashmap= getBackup();
		Preferences lastopened=hashmap.get("lastopened");
		if(lastopened != null) {
			return lastopened.starterclass;
		}
		else {
			return "";
		}
	}


	public void setStarterClass(String mainclass) {
		LinkedHashMap<String,Preferences> hashmap=getBackup();
		Preferences filenameanddirectory=hashmap.get("lastopened");
		filenameanddirectory=hashmap.get(filenameanddirectory.starterclass);
		if( ! filenameanddirectory.starterclass.equals(mainclass) ) {
			filenameanddirectory.starterclass=mainclass;		;
			setBackup(hashmap);
		}
	}

	public String getStarterClass() {
		LinkedHashMap<String,Preferences> hashmap=getBackup();
		Preferences filenameanddirectory=hashmap.get("lastopened");
		filenameanddirectory=hashmap.get(filenameanddirectory.starterclass);
		String starterclass= filenameanddirectory.starterclass;
		if(starterclass == null) {
			return "";
		}
		else {
			return starterclass;
		}
	}
	
	public void addJar(String directory,String jarpath) {
		LinkedHashMap<String,Preferences> linkedhashmap=getBackup();
		Preferences preferences=linkedhashmap.get(directory);
		if(preferences == null) {
			preferences = new Preferences();
			preferences.jars.add(jarpath);
			linkedhashmap.put(directory,preferences);
		}
		else {
			boolean haveJarAlready=false;
			for(String jar:preferences.jars) {
				if(jar.equals(jarpath)) {
					return;
				}
			}
			preferences.jars.add(jarpath);
		}
		noduplicate.Delete();
		setBackup(linkedhashmap);
		/*for(String key : linkedhashmap.keySet()) {
			if(!key.equals("lastopened")) {
				Preferences preferences=linkedhashmap.get(key);
				String directory2=key.replaceAll("[^\\\\]+\\.java","");
				if(directory.equals(directory2)) {
					boolean haveJarAlready=false;
					for(String jar:preferences.jars) {
						if(jar.equals(jarpath)) {
							haveJarAlready = true;
							break;
						}
					}
					if(!haveJarAlready) {
						preferences.jars.add(jarpath);
					}
				}
			}
		}*/
	}
	public void removeJar(String fileName,String jar) {
		LinkedHashMap<String,Preferences> linkedhashmap=getBackup();
		Preferences preferences=linkedhashmap.get(fileName);
		preferences.jars.remove(jar);
		setBackup(linkedhashmap);
		noduplicate.Delete();
	}
	public Preferences get(String filenameanddirectory) {
		LinkedHashMap<String,Preferences> linkedhashmap=getBackup();
		Preferences preferences= linkedhashmap.get(filenameanddirectory);
		if(preferences != null) {
			return preferences;
		}
		else { // preferences is null
			noduplicate.Delete();
			set(filenameanddirectory);
			linkedhashmap=getBackup();
			return linkedhashmap.get(filenameanddirectory);
		}
	}
}