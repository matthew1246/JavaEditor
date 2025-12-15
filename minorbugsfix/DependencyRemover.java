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
import org.w3c.dom.Node;

import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.JButton;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
/*
** This class shows all dependencies inside pom.xml
** This class also allows you to add and remove Dependencies.
*/
public class DependencyRemover {
	public String fileName;
	public DependencyRemover(String fileName) {
		this.fileName= fileName;
		setLayout();
		setListeners();
		showDependencies();
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
	}
	ActionListener add_dependency_listener;
	public void setListeners() {
		add_dependency_listener = (ev) -> {
			JButton add_dependency_btn=(JButton)ev.getSource();
			JPanel row = (JPanel)add_dependency_btn.getParent();
			Component[] components = row.getComponents();
			String groupId=((JLabel)components[1]).getText();
			String artifactId=((JLabel)components[3]).getText();
			
			if(isJLabel(components[5])) {
				String scope = ((JLabel)components[5]).getText();
				JOptionPane.showMessageDialog(null,groupId+" "+artifactId+" "+scope);
			}
			else {
				JOptionPane.showMessageDialog(null,groupId+" "+artifactId);
			}
		};
	}
	public boolean isJLabel(Component component) {
		return component instanceof JLabel;
	}
	public void showDependencies() {
		try {
			String pomxml=Main.getDirectory(fileName)+"pom.xml";
			
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc=builder.parse(new File(pomxml));
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
			if(dependencies == null)
				return;	
			NodeList nodelist=dependencies.getElementsByTagName("dependency");
			for(int i = 0; i < nodelist.getLength(); i++) {
				gridlayout.setRows(gridlayout.getRows()+1);
				JPanel row = new JPanel();
				
				Element element=(Element)nodelist.item(i);
				row.add(new JLabel("groupId:"));
				String groupId=element.getElementsByTagName("groupId").item(0).getTextContent();						row.add(new JLabel(groupId));
					
				row.add(new JLabel("artifactId:"));
				String artifactId=element.getElementsByTagName("artifactId").item(0).getTextContent();						row.add(new JLabel(artifactId));
				
				/*String version=element.getElementsByTagName("version").item(0).getTextContent();	
				row.add(new JLabel(version));
				
				System.out.print(groupId+" "+artifactId+" "+scope);
				*/
				
				
				NodeList scope=element.getElementsByTagName("scope");
				if(scope.getLength() > 0) {
					// System.out.print(" "+scope.item(0).getTextContent());
					row.add(new JLabel("scope:"));
					row.add(new JLabel(scope.item(0).getTextContent()));
				}		
				JButton add_dependency_btn = new JButton("Add Dependency");			
				add_dependency_btn.addActionListener(add_dependency_listener);
				row.add(add_dependency_btn);
				
				rows.add(row);
				rows.validate();
				rows.repaint();											
				// System.out.println();
			}
			// System.out.println();
			
			frame.validate();
			frame.pack();
			frame.repaint();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}								
	}
}