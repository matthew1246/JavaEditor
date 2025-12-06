import java.io.Console;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
public class Maven {
	public static void main(String[] args) {
		Maven maven = new Maven();
		maven.run();	
	}
	public void run() {
		Console console=System.console();
		String input=console.readLine("What you like to search? ");
		System.out.println("*"+input+"*");
		String rowsa=console.readLine("How many search results do you want?");
		int rows=Integer.parseInt(rowsa);
		int start_index = 0;
		String url = "https://search.maven.org/solrsearch/select?q="+input+"&rows="+rows+"&start="+start_index+"&wt=json";
		System.out.println(get(url));
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