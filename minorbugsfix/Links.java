import java.io.*;
import java.util.regex.*;
import javax.swing.*;
import java.util.*;
public class Links {
	public static void main(String[] args) {
		Links links = new Links();
		for(String package0:links.getFullPackageNames()) {
			System.out.println(package0);
		}
	}
	public HashSet<String> fullpackagenames = new HashSet<String>();
	public HashSet<String> subpackage=  new HashSet<String>();
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
					String fullpackagename=sublink.replace(".html","").replace("/",".");
					fullpackagenames.add(fullpackagename);
					String package0= sublink.replaceAll("[^/]+\\.html","").replace("/",".");
					if(package0.endsWith("."))
						package0 = package0.substring(0,(package0.length()-1));
					String[] subpackages = package0.split("\\.");
					String packagewithdot = "";	
					for(int i = 0; i < subpackages.length; i++) {
						packagewithdot+=subpackages[i];	
						subpackage.add(packagewithdot);
						packagewithdot+=".";
					}
							
					List<String> package1=packagesandclasses.get(package0);
					if(package1 == null) {
						package1 = new ArrayList<String>();
					}		
					
					//sublinks.add(sublink);
					String with_capitols = getClassWithCapitols(sublink);
					package1.add(with_capitols);
					packagesandclasses.put(package0,package1);
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
	public List<String> getAPIClasses() {
		List<String> apiclasses=new ArrayList<String>();
		for(HashMap<String,String> innerhashmap:hashmap.values()) {
			for(String classname:innerhashmap.keySet()) { // Only one value
				apiclasses.add(classname);
			}
		}
		return apiclasses;
	}
	public List<String> getPackages() {
		List<String> packages = new ArrayList<String>();	
		for(String package0:packagesandclasses.keySet()) {
			packages.add(package0);
		}
		return packages;
	}
	public List<String> getClassFrom(String package0) {
		return packagesandclasses.get(package0);
	}
	public List<String> getSubpackages() {
		List<String> subpackages=new ArrayList<String>();
		for(String package0:subpackage) {
			subpackages.add(package0);
		}
		return subpackages;
	}
	public List<String> getFullPackageNames() {
		List<String> packages=new ArrayList<String>();
		for(String package0:fullpackagenames) {
			packages.add(package0);
		}
		return packages;
	}
}
		
		