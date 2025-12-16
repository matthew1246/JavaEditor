package com.perky;

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
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;

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
	ActionListener remove_dependency_listener;
	public void setListeners() {
		remove_dependency_listener = (ev) -> {
			JButton remove_dependency_btn=(JButton)ev.getSource();
			JPanel row = (JPanel)remove_dependency_btn.getParent();
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
			try {
				removeDependency(groupId,artifactId);
			} catch(Exception ex) {
				ex.printStackTrace();	
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
				JButton remove_dependency_btn = new JButton("Remove Dependency");			
				remove_dependency_btn.addActionListener(remove_dependency_listener);
				row.add(remove_dependency_btn);
				
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
	public void removeDependency(String groupId,String artifactId) throws Exception {
		File pomFile= new File(Main.getDirectory(fileName)+"pom.xml");
        
        		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        		factory.setNamespaceAware(true);
	
	       	DocumentBuilder builder = factory.newDocumentBuilder();
	        	Document doc = builder.parse(pomFile);
	       	doc.getDocumentElement().normalize();
	
	        	NodeList dependencyNodes = doc.getElementsByTagName("dependency");
	
	        	for (int i = 0; i < dependencyNodes.getLength(); i++) {
	        		Element dependency = (Element) dependencyNodes.item(i);
		
		           	String groupId2= getChildText(dependency, "groupId");
		           	String artifactId2=getChildText(dependency, "artifactId");
		
		         	if (groupId.equals(groupId2) && artifactId.equals(artifactId2)) {
				dependency.getParentNode().removeChild(dependency);
		                	break; // remove only one
		          	}
	          	}				
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

        doc.setXmlStandalone(true);
        transformer.transform(
                new DOMSource(doc),
                new StreamResult(file)
        );
    }
}