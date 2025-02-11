import java.io.*;
import java.util.regex.*;
import javax.swing.*;
import java.util.*;
public class Links {
	public static void main(String[] args) {
		Links links = new Links();
		for(String importname:links.getImport("JButton")) {
			System.out.println(importname);
		}
	}
	private HashMap<String,List<String>> hashmap = new HashMap<String,List<String>>();
	private List<String> sublinks = new ArrayList<String>();
	public Links() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("links.html")));
			while(true) {
				String line=input.readLine();
				if(line == null) break;
				if(isSublink(line)) {
					String sublink = getSublink(line);
					sublinks.add(sublink);
					String class_one = getClass(sublink);
					hashmap.put(class_one,new ArrayList<String>());
				}
			}
			for(int i = 0; i < sublinks.size(); i++) {
				String sublink = sublinks.get(i);
				String class_one=getClass(sublink);
				List<String> list=hashmap.get(class_one);
				list.add(sublink);
			}
		} 
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public String getLink(String sublink) {
		 return "https://docs.oracle.com/javase/8/docs/api/index.html?"+sublink;
	}
	
	public boolean isSublink(String line) {
		Pattern pattern = Pattern.compile("<li><a href=\".+\"");
		Matcher matcher=pattern.matcher(line);
		return matcher.find();
	}
	
	public String getSublink(String line) {
		Pattern pattern = Pattern.compile("<li><a href=\"(.+?)\"");
		Matcher matcher=pattern.matcher(line);
		if(matcher.find()) {
			return matcher.group(1);
		}
		else {
			return "Could not find sublink.";
		}
	}
	
	public String getClass(String sublink) {
		Pattern pattern = Pattern.compile(".+/(.+)\\.html");
		Matcher matcher=pattern.matcher(sublink);
		if(matcher.find()) {
			return matcher.group(1).toLowerCase();
		}
		else {
			return "Could not find class name in sublink.";
		}
	}
	
	public void openLink(String one_class) {
		try {
			one_class=one_class.toLowerCase();
			List<String> classes=hashmap.get(one_class);
			for(int i = 0; i < classes.size(); i++) {
				String sublink = classes.get(i);
				String link = getLink(sublink);
				link = link.replace("https://docs.oracle.com","");
				link=link.substring(0,link.length()-5);
				link = link.replaceAll("\\.","/");
				link="https://docs.oracle.com"+link;
				link+=".html";
				// Runtime.getRuntime().exec("cmd /c start chrome "+link);
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(link));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getImport(String one_class) {
		one_class=one_class.toLowerCase();
		List<String> classes=hashmap.get(one_class);
		for(int i = 0; i < classes.size(); i++) {
			String oneclass=classes.get(i);
			classes.remove(oneclass);
			classes.add(i,oneclass.replace(".html","").replaceAll("/","\\."));
		}
		return classes;
	}
}
		
		