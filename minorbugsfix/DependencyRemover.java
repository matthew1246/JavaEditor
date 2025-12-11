import javax.swing.JFrame;
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

import java.io.File;
import java.io.IOException;
/*
** This class shows all dependencies inside pom.xml
** This class also allows you to add and remove Dependencies.
*/
public class DependencyRemover {
	public String fileName;
	public DependencyRemover(String fileName) {
		this.fileName= fileName;
		setLayout();
	}
	public void setLayout() {
		JFrame frame = new JFrame();
	}
	public void showDependencies() {
		try {
			String pomxml=Main.getDirectory(fileName)+"pom.xml";
			
			DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc=builder.parse(new File(pomxml));
			doc.getDocumentElement().normalize();
			
			Element root=doc.getDocumentElement();
			NodeList nodelist=root.getElementsByTagName("dependency");
			for(int i = 0; i < nodelist.getLength(); i++) {
				Element element=(Element)nodelist.item(i);
				String groupId=element.getElementsByTagName("groupId").item(0).getTextContent();						String artifactId=element.getElementsByTagName("artifactId").item(0).getTextContent();						String version=element.getElementsByTagName("version").item(0).getTextContent();			
				System.out.print(groupId+" "+artifactId+" "+version);
				
NodeList scope=element.getElementsByTagName("scope");
				if(scope.getLength() > 0) {
					System.out.print(" "+scope.item(0).getTextContent());
				}												System.out.println();
			}
			System.out.println();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}								
	}
}