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
	public HashMap<String,List<String>> packagesandclasses = new HashMap<String,List<String>>();
	public HashMap<String,HashMap<String,String>> hashmap = new HashMap<String,HashMap<String,String>>();
	//private List<String> sublinks = new ArrayList<String>();
	public Links() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("links.html")));
			while(true) {
				String line=input.readLine();
				if(line == null) break;
				if(isSublink(line)) {
					String sublink = getSublink(line);
					String package0= sublink.replaceAll("[^/]+\\.html","").replace("/",".");
					if(package0.endsWith("."))
						package0 = package0.substring(0,(package0.length()-1));
					JOptionPane.showMessageDialog(null,package0);
					System.exit(0);
					
					//sublinks.add(sublink);
					String with_capitols = getClassWithCapitols(sublink);
					String class_one = getClass(sublink);
					HashMap<String,String> hashmapwithcapitols =hashmap.get(class_one);
					if(hashmapwithcapitols == null) {
						hashmapwithcapitols = new HashMap<String,String>();
					}
					hashmapwithcapitols.put(with_capitols,sublink);
					hashmap.put(class_one,hashmapwithcapitols);
				}
			}
			/*for(int i = 0; i < sublinks.size(); i++) {
				String sublink = sublinks.get(i);
				String class_one=getClass(sublink);
				list.add(sublink);
			}
			*/
		} 
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public String getLink(String sublink) {
		 return "https://docs.oracle.com/javase/8/docs/api/?"+sublink;
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
	public String getClassWithCapitols(String sublink) {
		Pattern pattern = Pattern.compile(".+/(.+)\\.html");
		Matcher matcher=pattern.matcher(sublink);
		if(matcher.find()) {
			return matcher.group(1);
		}
		else {
			return "Could not find class name in sublink.";
		}
	}

	
	public void openLink(String one_class) {
		try {
			one_class=one_class.toLowerCase();
			HashMap<String,String> classes=hashmap.get(one_class);
			for(String sublink:classes.values()) {
				String link = getLink(sublink);
				// Runtime.getRuntime().exec("cmd /c start chrome "+link);
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(link));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getImport(String one_class) {
		List<String> classes2 = new ArrayList<String>();
		one_class=one_class.toLowerCase();
		HashMap<String,String> classes=hashmap.get(one_class);
		for(String oneclass:classes.values()) {
			classes2.add(oneclass.replace(".html","").replaceAll("/","\\."));
		}
		return classes2;
	}

}
		
		