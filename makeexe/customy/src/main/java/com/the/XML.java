package com.the;

import java.io.ByteArrayInputStream;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
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
import javax.xml.transform.OutputKeys;
import java.nio.charset.StandardCharsets;
public class XML {
	private File xmlFile;
	public XML(File xmlfile) {
		 xmlFile = xmlfile;
		 Setup();
	}
	public XML(String contents) {
		try {
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder=factory.newDocumentBuilder();
			ByteArrayInputStream inputstream=new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
			doc=builder.parse(inputstream);
			doc.getDocumentElement().normalize();
		} catch(ParserConfigurationException | SAXException | IOException ex) {
			ex.printStackTrace();
		}
	}	
	private Document doc;
	private void Setup() {
		try {	
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			
			DocumentBuilder builder=factory.newDocumentBuilder();
			doc=builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}
	public Node getRootNode() {
		return doc.getDocumentElement();
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
	private List<Node> nodes;
	public Node[] getNodes(String tagName) {
		nodes = new ArrayList<Node>();
		Node root=getRootNode();
		getNodes(root,tagName);
		return nodes.toArray(new Node[nodes.size()]);
	}
	private void getNodes(Node node,String tagName) {
		Node[] nodechildren=getNodeChildren(node);
		if(nodechildren == null)
			return;	
		for(Node childnode:nodechildren) {
			if(childnode == null)
				continue;	
			String value=childnode.getNodeName();
			if(value != null && value.equals(tagName)) {
				nodes.add(childnode);
			}
			getNodes(childnode,tagName);	
		}
	}	
	public void updateFile() {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
	       		Transformer transformer = tf.newTransformer();
	        		//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
	        
	        		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
	
	        		doc.setXmlStandalone(true);
	        		transformer.transform(
	                		new DOMSource(doc),
	                		new StreamResult(xmlFile)
	        		);
        		} catch (TransformerConfigurationException ex) {
        			ex.printStackTrace();
        		} catch(TransformerException ex) {
        			ex.printStackTrace();
        		}
        	}
        	public void updateFile(String xmlfile) {
        		xmlFile = new File(xmlfile);
        		updateFile();
        	}
}