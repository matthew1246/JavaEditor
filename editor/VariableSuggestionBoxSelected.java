import java.util.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import javax.swing.*;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Arrays;
public class VariableSuggestionBoxSelected {
	private LinkedHashMap<String,ArrayList<String>> linkedhashmap;
	public VariableSuggestionBoxSelected() {
		GsonBuilder gsonbuilder=new GsonBuilder();
		gsonbuilder.setPrettyPrinting();
		Gson gson = gsonbuilder.create();
		File backup = new File("autosuggestionbox.txt");
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
	
	public JLabel[] Reordered(JLabel[] labels0,String textfieldinput) {
		List<JLabel> list = Reordered(Arrays.asList(labels0),textfieldinput);
		return list.toArray(new JLabel[list.size()]);
	}	
	public List<JLabel> Reordered(List<JLabel> labels,String textfieldinput) {
		LiveIterator<JLabel> liveiterator = new LiveIterator<JLabel>(labels,true); // clone List
		try {
		if(linkedhashmap.get(textfieldinput) != null) {
			loopy: for(String variablename:linkedhashmap.get(textfieldinput)) {
				while(liveiterator.hasNext()) {
					JLabel label = (JLabel)liveiterator.next();
					if(label.getText().equals(variablename)) {
						liveiterator.remove(label);
					}
				}
			}
			for(String variablename:linkedhashmap.get(textfieldinput)) {
				liveiterator.list.add(0,new JLabel(variablename));
			}
			return liveiterator.list;
		}
		} catch (NoSuchMethodError error) {
			JOptionPane.showMessageDialog(null,textfieldinput);
		}	
		return labels;
	}	
	/*
	** This gets method or property last selected.
	*/
	public ArrayList<String> get(String textfieldinput) {
		return linkedhashmap.get(textfieldinput);
	}
	public void Save(String textfieldinput,String variablename) {
		ArrayList<String> list=linkedhashmap.get(textfieldinput);
		if(list == null) {
			list = new ArrayList<String>();
		}
		else {
			if(list.contains(variablename)) {
				list.remove(variablename);
			}
		}
		list.add(variablename);		
		linkedhashmap.put(textfieldinput,list);
		setBackup();
	}
	public void setBackup() {
		try {
			GsonBuilder gsonbuilder = new GsonBuilder();
			gsonbuilder.setPrettyPrinting();
			Gson gson = gsonbuilder.create();
			String contents = gson.toJson(linkedhashmap);
			PrintWriter printwriter=new PrintWriter(new File("autosuggestionbox.txt"));
			printwriter.print(contents);		
			printwriter.close();
		}
		catch(FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null,ex.getStackTrace());
		}
	}
}							