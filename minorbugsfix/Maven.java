import java.io.Console;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
public class Maven {
	public static void main(String[] args) {
		Maven maven = new Maven();
		maven.run();	
	}
	/*
	** This searches dependencies for Maven.
	*/
	public void run() {
		Console console=System.console();
		String input=console.readLine("What you like to search? ");
		System.out.println("*"+input+"*");
		String rowsa=console.readLine("How many search results do you want?");
		int rows=Integer.parseInt(rowsa);
		int start_index = 0;
		String url = "https://search.maven.org/solrsearch/select?q="+input+"&rows="+rows+"&start="+start_index+"&wt=json";
		String responseJson=get(url);
		System.out.println(responseJson);
		
		System.out.println();
		
		JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
		JsonObject response=jsonObject.getAsJsonObject("response");
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
		}
		
	}
		
	public OkHttpClient client = new OkHttpClient();
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
}