import java.util.LinkedHashMap;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.nio.file.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
public class NoDuplicate {
	IsEqual isequal = new IsEqual();
	public boolean IsDuplicate(LinkedHashMap<String,Preferences> linkedhashmap) 	{
		GsonBuilder gsonbuilder=new GsonBuilder();
		gsonbuilder.setPrettyPrinting();
		Gson gson = gsonbuilder.create();
		File backup = new File("original.txt");
		try {
			TypeToken<LinkedHashMap<String,Preferences>> typetoken = new TypeToken<LinkedHashMap<String,Preferences>>(){};
			FileReader filereader = new FileReader(backup);
			LinkedHashMap<String,Preferences> linkedhashmap2= gson.fromJson(filereader,typetoken.getType());
			filereader.close();
			return isequal.isEqual(linkedhashmap,linkedhashmap2);
		}			
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
			return false;
		} catch(IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	/*
	** This is to stop git recognising backup.txt as
	** a different file even though the file is the same
	** because line endings changes the original file.
	*/
	public void ReplaceWithOriginal() {
		try {
			File file = new File("original.txt");
			if(file.exists()) {
				String path = file.getPath();
				Path originalpath=Paths.get(path);
				Path copypath=Paths.get("backup.txt");
				Files.copy(originalpath,copypath,StandardCopyOption.REPLACE_EXISTING);
				file.delete();
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public void CreateOriginal() {
		try {
			File file = new File("backup.txt");
			if(file.exists()) {
				String path = file.getPath();
				Path originalpath=Paths.get(path);
				Path copypath=Paths.get("original.txt");
				Files.copy(originalpath,copypath,StandardCopyOption.REPLACE_EXISTING);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public void Delete() {
		File file = new File("original.txt");
		if(file.exists())
		file.delete();
	}
}
