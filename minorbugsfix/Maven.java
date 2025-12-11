import java.io.Console;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.io.File;
import java.io.PrintWriter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
public class Maven {
	public String fileName;
	public Maven() {
		setLayout();
		setListeners();
	}
	public void Change(String fileName) {
		if(fileName != null && !fileName.equals("")) {
			this.fileName = fileName;
			String dir=Main.getDirectory(fileName);
			File file = new File(dir+"pom.xml");
			if(file.exists()) {
				showMavenAlreadyInitialised();
			}
			else {
				showNotInitialised();
			}
		}
	}
	public JFrame frame;
	public JButton initialise;
	public JButton pomxml;
	public JButton add_dependency;
	public JButton addplugin;	
	public JButton add_dependencyorplugin;	
	public JButton removedependency;
	public void setLayout() {
		frame = new JFrame();
		frame.setTitle("Maven");
		frame.setSize(500,500);
		frame.setLocation(980,175);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		initialise = new JButton("initialise");
		panel.add(initialise);
		
		pomxml=new JButton("pom.xml");
		panel.add(pomxml);
		
		add_dependency=new JButton("Add Dependency");
		panel.add(add_dependency);
		
		addplugin=new JButton("Add Plugin");
		panel.add(addplugin);
		
		add_dependencyorplugin=new JButton("Add Dependency/Plugin");
		panel.add(add_dependencyorplugin);
		
		removedependency = new JButton("Remove Dependency");
		panel.add(removedependency);
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	public void setListeners() {
		initialise.addActionListener( ev -> {
			showMavenAlreadyInitialised();
			Generatepomxml();	
		});
		pomxml.addActionListener( ev -> {
			CommandLine commandline = new CommandLine();
			commandline.run("explorer "+Main.getDirectory(fileName)+"pom.xml",Main.getDirectory(fileName));
		});	
		removedependency.addActionListener( ev -> {
			DependencyRemover dependencyremover=new DependencyRemover(fileName);
		});		
	}	
	public void showNotInitialised() {
		initialise.setEnabled(true);
		
		pomxml.setEnabled(false);
		add_dependency.setEnabled(false);
		addplugin.setEnabled(false);
	}
	public void showMavenAlreadyInitialised() {
		initialise.setEnabled(false);
		
		pomxml.setEnabled(true);
		add_dependency.setEnabled(true);
		addplugin.setEnabled(true);
	}
	public void Generatepomxml() {
		String filestring = """
<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       		<modelVersion>4.0.0</modelVersion>
  		<groupId>com.jetbrains.marco</groupId>
  		<artifactId>maven-tutorial</artifactId>
  		<version>1.0-SNAPSHOT</version>

 		<properties>
      			<maven.compiler.source>11</maven.compiler.source>
      			<maven.compiler.target>11</maven.compiler.target>

      			<junit.version>5.8.2</junit.version>
  		</properties>

  		<build>
      			<plugins>
           			<plugin>
               				<artifactId>maven-surefire-plugin</artifactId>
               				<version>2.22.2</version>
           			</plugin>
           			<plugin>
               				<artifactId>maven-failsafe-plugin</artifactId>
               				<version>2.22.2</version>
           			</plugin>
      			</plugins>
  		</build>

  		<dependencies>
      			<dependency>
          			<groupId>com.mpatric</groupId>
          			<artifactId>mp3agic</artifactId>
          			<version>0.9.1</version>
      			</dependency>
      			<!-- Only needed to run tests in a version of IntelliJ IDEA that bundles older versions -->
      			<dependency>
          			<groupId>org.junit.platform</groupId>
          			<artifactId>junit-platform-launcher</artifactId>
          			<version>1.8.2</version>
          			<scope>test</scope>
      			</dependency>
      			<dependency>
           			<groupId>org.junit.jupiter</groupId>
           			<artifactId>junit-jupiter-engine</artifactId>
           			<version>${junit.version}</version>
           			<scope>test</scope>
      			</dependency>
      			<dependency>
          			<groupId>org.junit.vintage</groupId>
				<artifactId>junit-vintage-engine</artifactId>
			        <version>${junit.version}</version>
			        <scope>test</scope>
      			</dependency>
  		</dependencies>
	</project>
		""";
		try {
			PrintWriter printwriter = new PrintWriter(Main.getDirectory(fileName)+"pom.xml");
			printwriter.println(filestring);
			printwriter.close();
		} catch (java.io.FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}		
	/*
	** This searches dependency and plugin for Maven.
	*/
	public void run() {
		Console console=System.console();
		String input=console.readLine("What you like to search? ");
		System.out.println("*"+input+"*");
		GetAll(input);
		/*
		String rowsa=console.readLine("How many search results do you want?");
		int rows=Integer.parseInt(rowsa);
		
		String responseJson=Search(rows,input);
	
		Parse(responseJson);
		*/
	}
		
	public OkHttpClient client = new OkHttpClient();
	public String Search(int how_many,String query) {
		return Search(how_many,query,0);
	}	
	public String Search(int how_many,String query,int start_index) {
		int rows = how_many;
		String input = query;
		String url = "https://search.maven.org/solrsearch/select?q="+input+"&rows="+rows+"&start="+start_index+"&wt=json";
		String responseJson=get(url);
		return responseJson;
	}		
	public String get(String url) {
		try {
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch(IOException ex) {
			ex.printStackTrace();
			return "IOException occured.";
		}
	}
	public void Parse(String responseJson) {
		System.out.println(responseJson);
		
		System.out.println();
		
		JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
		
		// {"responseHeader":{"status":0,"QTime":145,"params":{"q":"jar","core":"","defType":"dismax","qf":"text^20 g^5 a^10","indent":"off","spellcheck":"true","fl":"id,g,a,latestVersion,p,ec,repositoryId,text,timestamp,versionCount","start":"0","spellcheck.count":"5","sort":"score desc,timestamp desc,g asc,a asc","rows":"25","wt":"json","version":"2.2"}},"response":{"numFound":582830
		
		JsonObject response=jsonObject.getAsJsonObject("response");
		
		int totalnumber=response.get("numFound").getAsInt();
		System.out.println("Total number of search results is: "+totalnumber);
		
		System.out.println();
		
		System.out.println(response);
		
		
		System.out.println();
		
		JsonArray docs=response.getAsJsonArray("docs");
		
		for(int i = 0; i < docs.size(); i++) {
			JsonObject doc = docs.get(i).getAsJsonObject();
			System.out.println(doc);
			
			System.out.println();
			
			String groupId=doc.get("g").getAsString();
			String artifactId=doc.get("a").getAsString();
			String version=doc.get("latestVersion").getAsString();
			System.out.println("groupId: "+groupId+" artifactId: "+artifactId+" version: "+version);
			
			System.out.println();
			
			String pluginordependency= "jar";
			if(doc.get("p") != null)
				pluginordependency=	doc.get("p").getAsString();
			
			if(pluginordependency.contains("plugin")) {
				System.out.println(pluginordependency);
			}
			else {
				System.out.println("Is a dependency");
			}
			
			System.out.println();	
		}
	}
	public void GetAll(String query) {
		int totalnumber=GetHowMany(query);
		System.out.println("total number is: "+totalnumber);
	
		for(int i = 0; i < totalnumber; i+=20) {
			Parse(Search(20,query,i));
		}
	}
	/*
	** This function gets how many dependencies and plugins there
	** for a given String. The given String is the variable named query.
	*/
	public int GetHowMany(String query) {
		String responseJson=Search(1,query);
		JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
		// {"responseHeader":{"status":0,"QTime":145,"params":{"q":"jar","core":"","defType":"dismax","qf":"text^20 g^5 a^10","indent":"off","spellcheck":"true","fl":"id,g,a,latestVersion,p,ec,repositoryId,text,timestamp,versionCount","start":"0","spellcheck.count":"5","sort":"score desc,timestamp desc,g asc,a asc","rows":"25","wt":"json","version":"2.2"}},"response":{"numFound":582830
		
		JsonObject response=jsonObject.getAsJsonObject("response");
		int totalnumber=response.get("numFound").getAsInt();
		
		return totalnumber;
	}
}