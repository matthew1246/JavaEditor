import java.util.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import javax.swing.*;
import java.lang.reflect.Member;
public class SuggestionBoxSelected {
	private LinkedHashMap<String,ArrayList<String>> linkedhashmap;
	public SuggestionBoxSelected() {
		GsonBuilder gsonbuilder=new GsonBuilder();
		gsonbuilder.setPrettyPrinting();
		Gson gson = gsonbuilder.create();
		File backup = new File("suggestionbox.txt");
		if(!backup.exists()) {
			this.linkedhashmap= new LinkedHashMap<String,ArrayList<String>>();
		}
		else {
			try {
				TypeToken<LinkedHashMap<String,ArrayList<String>>> typetoken = new TypeToken<LinkedHashMap<String,ArrayList<String>>>(){};
				FileReader filereader = new FileReader(backup);
				linkedhashmap= gson.fromJson(filereader,typetoken.getType());
				filereader.close();
				return;
			}			
			catch(FileNotFoundException ex) {
				JOptionPane.showMessageDialog(null,ex.getStackTrace());
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(null,ex.getStackTrace());
			}
		}
	}
	
	public Member[] Reordered(Member[] members,Class<?> classquestionmark) {
		String classname=classquestionmark.getSimpleName();
		if(linkedhashmap.get(classname) != null) {
			List<Member> nameslist = new LinkedList<Member>();
			for(int i = 0; i < members.length; i++) {
				Member member=members[i];
				nameslist.add(member);
			}
			
			for(String methodname:linkedhashmap.get(classname)) {
				for(int j = 0; j < nameslist.size(); j++) {
					Member member = nameslist.get(j);
					if(methodname.equals(member.getName())) {
						nameslist.remove(member);
						nameslist.addFirst(member);
						break;
					}
				}
			}
			return nameslist.toArray(new Member[nameslist.size()]);
		}
		else {
			return members;
		}

	}	
	
	/*
	** This gets method or property last selected.
	*/
	public ArrayList<String> get(String classy) {
		return linkedhashmap.get(classy);
	}
	public void Save(String classy,String methodorproperty) {
		ArrayList<String> list=linkedhashmap.get(classy);
		if(list == null) {
			list = new ArrayList<String>();
		}
		list.addFirst(methodorproperty);
		linkedhashmap.put(classy,list);
		setBackup();
	}
	public void setBackup() {
		try {
			GsonBuilder gsonbuilder = new GsonBuilder();
			gsonbuilder.setPrettyPrinting();
			Gson gson = gsonbuilder.create();
			String contents = gson.toJson(linkedhashmap);
			PrintWriter printwriter=new PrintWriter(new File("suggestionbox.txt"));
			printwriter.print(contents);		
			printwriter.close();
		}
		catch(FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null,ex.getStackTrace());
		}
	}
}							