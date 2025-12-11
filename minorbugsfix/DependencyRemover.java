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
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
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
	public JFrame frame;
	public JPanel rows;
	public GridLayout gridlayout;
	public void setLayout() {
		frame = new JFrame();
		gridlayout = new GridLayout(0,1);
		rows = new JPanel(gridlayout);
		frame.add(rows);
		frame.setVisible(true);
		showDependencies();
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
				gridlayout.setRows(gridlayout.getRows()+1);
				JPanel row = new JPanel();
				
				Element element=(Element)nodelist.item(i);
				String groupId=element.getElementsByTagName("groupId").item(0).getTextContent();						row.add(new JLabel(groupId));
					
				String artifactId=element.getElementsByTagName("artifactId").item(0).getTextContent();						row.add(new JLabel(artifactId));
				
				String version=element.getElementsByTagName("version").item(0).getTextContent();	
				row.add(new JLabel(version));
				
				System.out.print(groupId+" "+artifactId+" "+version);
				
				
NodeList scope=element.getElementsByTagName("scope");
				if(scope.getLength() > 0) {
					System.out.print(" "+scope.item(0).getTextContent());
					row.add(new JLabel(scope.item(0).getTextContent()));
				}		
				
				rows.add(row);
				rows.validate();
				rows.repaint();											System.out.println();
			}
			System.out.println();
			
			frame.pack();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}								
	}
}