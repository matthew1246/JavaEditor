package com.the;

import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
public class XML {
	private File xmlFile;
	public XML(String xmlfile) {
		 xmlFile = new File(xmlfile);
	}
	public Node getRootNode() {
		try {
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			return doc.getDocumentElement();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} catch (SAXException ex) {
			ex.printStackTrace();
			return null;
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public Node[] getNodeChildren(Node node) {
		List<Node> childs = new ArrayList<Node>();
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node childnode=nodeList.item(i);
			if(childnode.getNodeType() == Node.ELEMENT_NODE) {
				childs.add(childnode);
			}
		}
		return childs.toArray(new Node[childs.size()]);
	}
	private Node getNode(Node node,String tagName) {
		Node[] nodechildren=getNodeChildren(node);
		for(Node childnode:nodechildren) {
			if(childnode == null)
				continue;	
			String value=childnode.getNodeName();
			if(value != null && value.equals(tagName)) {
				return childnode;
			}
			else if(value != null) {
				Node return_node= getNode(childnode,tagName);
				if(return_node != null)
					return return_node;	
			}
		}
		return null;
	}																
	public Node getNode(String tagName) {
		Node root = getRootNode();
		return getNode(root,tagName);
	}
}
