import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
public class AddDependency {
	public AddDependency() {
		setLayout();
		setListeners();
	}
	public JFrame frame;
	public GridLayout gridlayout;
	public JTextField textfield;
	public JPanel rows;
	public JButton search;
	public void setLayout() {
		frame = new JFrame();
		
		gridlayout = new GridLayout(1,1);
		rows = new JPanel(gridlayout);
		JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		textfield = new JTextField(15);
		row.add(textfield);
		
		search = new JButton("Search");
		row.add(search);
		
		rows.add(row);
		
		frame.add(rows);
		
		frame.pack();
		frame.setVisible(true);
	}
	public void setListeners() {
		showMore.addActionListener((ev) -> {
			gridlayout.setRows(gridlayout.getRows()-1);
			gridlayout.removeLayoutComponent(showMoreRow);
			frame.validate();
			frame.pack();
			frame.repaint();
		});
		search.addActionListener( (ev) -> {
			Thread thread = new Thread(() -> {
				String input = textfield.getText();
				String responseJson=Search(5,input);
				Parse(responseJson);
			});
			thread.start();
		});		
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
	public JPanel showMoreRow;
	public JButton showMore = new JButton("Show More");;
	public void Parse(String responseJson) {
		//System.out.println(responseJson);
		
		//System.out.println();
		
		JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
		
		// {"responseHeader":{"status":0,"QTime":145,"params":{"q":"jar","core":"","defType":"dismax","qf":"text^20 g^5 a^10","indent":"off","spellcheck":"true","fl":"id,g,a,latestVersion,p,ec,repositoryId,text,timestamp,versionCount","start":"0","spellcheck.count":"5","sort":"score desc,timestamp desc,g asc,a asc","rows":"25","wt":"json","version":"2.2"}},"response":{"numFound":582830
		
		JsonObject response=jsonObject.getAsJsonObject("response");
		
		//int totalnumber=response.get("numFound").getAsInt();
		//System.out.println("Total number of search results is: "+totalnumber);
		
		//System.out.println();
		
		//System.out.println(response);
		
		
		//System.out.println();
		
		JsonArray docs=response.getAsJsonArray("docs");
		
		for(int i = 0; i < docs.size(); i++) {
			JsonObject doc = docs.get(i).getAsJsonObject();
			//System.out.println(doc);
			
			//System.out.println();
			
			String groupId=doc.get("g").getAsString();
			String artifactId=doc.get("a").getAsString();
			String version=doc.get("latestVersion").getAsString();
			//System.out.println("groupId: "+groupId+" artifactId: "+artifactId+" version: "+version);
			gridlayout.setRows(gridlayout.getRows()+1);
			
			JPanel row = new JPanel();
			row.add(new JLabel("groupId:"));
			row.add(new JLabel(groupId));
			row.add(new JLabel(" artifactId:"));
			row.add(new JLabel(artifactId+ " "));
			row.add(new JLabel("version:"));
			row.add(new JLabel(version));
			
			/*
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
			*/	
			
			rows.add(row);
			frame.validate();
			frame.pack();
			frame.repaint();
		}
		
		gridlayout.setRows(gridlayout.getRows()+1);
		
		showMoreRow = new JPanel();
		showMoreRow.add(showMore);
		rows.add(showMoreRow);
		
		frame.validate();
		frame.pack();
		frame.repaint();
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