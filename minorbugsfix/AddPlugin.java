import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
public class AddPlugin {
	public Maven maven;	
	public AddPlugin(Maven maven) {
		this.maven = maven;
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
	private ActionListener Add_Dependency_ActionListener;
	private JButton showAllButton = new JButton("Show All");
	private int showMoreIndex = 5;
	public void setListeners() {
		Add_Dependency_ActionListener = (ev) -> {
			JButton Add_button=(JButton)ev.getSource();
			
			JPanel row=(JPanel)Add_button.getParent();
			Component[] components=row.getComponents();
			String groupId=((JLabel)components[1]).getText().trim();
			String artifactId = ((JLabel)components[3]).getText().trim();
			String version = ((JLabel)components[5]).getText().trim();
			
			JOptionPane.showMessageDialog(null,groupId+" "+artifactId+" "+version);
			try {
				addDependency(groupId,artifactId,version);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		};
		showAllButton.addActionListener( (ev) -> {
			Thread thread = new Thread( () -> {
				rows.remove(showMoreRow);
				gridlayout.setRows(gridlayout.getRows()-1);
				frame.validate();
				frame.pack();
				frame.repaint();
				
				frame.remove(rows);
				JScrollPane jscrollpane = new JScrollPane(rows);
				jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				frame.add(jscrollpane);
				frame.validate();
				frame.pack();
				frame.repaint();
				 			
				String input = textfield.getText();
				GetAll(input);
			});
			thread.start();
		});
		showMore.addActionListener((ev) -> {
			Thread thread = new Thread( () -> {
				rows.remove(showMoreRow);
				gridlayout.setRows(gridlayout.getRows()-1);
				frame.validate();
				frame.pack();
				frame.repaint();
				
				String input = textfield.getText();
				String responseJson=Search(5,input,showMoreIndex);
				showMoreIndex+=5;
				Parse(responseJson);
				frame.validate();
				frame.pack();
				frame.repaint();
			});
			thread.start();
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
	public void addDependency(String groupId,String artifactId,String version) throws Exception {
		File pomFile=new File(maven.getPOMXMLs());
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc=builder.parse(pomFile);
		doc.getDocumentElement().normalize();
		
		Element root=doc.getDocumentElement();
		
		Element dependencies = null;
		NodeList secondlevel = root.getChildNodes();
		for(int i = 0; i < secondlevel.getLength(); i++) {
			Node secondnode=(Node)secondlevel.item(i);
			if(secondnode.getNodeType() == Node.ELEMENT_NODE) {
				if(secondnode.getLocalName().equals("dependencies")) {
					dependencies=(Element)secondnode;
					break;
				}
			}
		}
		if(dependencies == null) {
			dependencies = doc.createElement("dependencies");
			doc.getDocumentElement().appendChild(dependencies);
		}
		String indent = "\n    ";
		String indent2 = "\n        ";			
		dependencies.appendChild(doc.createTextNode(indent));
			
		Element dependency = doc.createElement("dependency");
		dependencies.appendChild(dependency);
		dependency.appendChild(doc.createTextNode(indent2));
		
		Element groupIdElement=doc.createElement("groupId");
		groupIdElement.setTextContent(groupId);
		dependency.appendChild(groupIdElement);
		dependency.appendChild(doc.createTextNode(indent2));
		
		Element artifactIdElement=doc.createElement("artifactId");
		artifactIdElement.setTextContent(artifactId);
		dependency.appendChild(artifactIdElement);
		dependency.appendChild(doc.createTextNode(indent2));
		
		Element versionElement=doc.createElement("version");
		versionElement.setTextContent(version);
		dependency.appendChild(versionElement);
		dependency.appendChild(doc.createTextNode(indent));
		
		dependencies.appendChild(doc.createTextNode("\n  "));
			
		writeBack(doc, pomFile);
	}

    private static String getChildText(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0) return null;
        return nodes.item(0).getTextContent().trim();
    }

    private static void writeBack(Document doc, File file) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
        
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");

        doc.setXmlStandalone(true);
        transformer.transform(
                new DOMSource(doc),
                new StreamResult(file)
        );
    }
	public OkHttpClient client = new OkHttpClient();
	public String Search(int how_many,String query) {
		return Search(how_many,query,0);
	}
	public String Search(int how_many,String query,int start_index) {
		//try {
			int rows = how_many;
			String input = query;
			// input = URLEncoder.encode(input,StandardCharsets.UTF_8.toString());
			String url = "https://search.maven.org/solrsearch/select?q="+input+"&rows="+rows+"&start="+start_index+"&wt=json";
			String responseJson=get(url);
			return responseJson;
		/*}
		catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
			return "UnsupportedEncodingException";
		}*/
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
			JButton Add_Dependency_Button = new JButton("Add Dependency");
			Add_Dependency_Button.addActionListener(Add_Dependency_ActionListener);
			row.add(Add_Dependency_Button);
			
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
		showMoreRow.add(showAllButton);
		rows.add(showMoreRow);
		
		frame.validate();
		frame.pack();
		frame.repaint();
	}
	public void GetAll(String query) {
		int totalnumber=GetHowMany(query);
		//System.out.println("total number is: "+totalnumber);
	
		int i = showMoreIndex;	
		for(; i < totalnumber; i+=20) {
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