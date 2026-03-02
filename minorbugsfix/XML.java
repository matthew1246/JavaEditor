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
	private Element root;
	private File xmlFile;
	public XML(String xmlfile) {
		 xmlFile = new File(xmlfile);
	}
	public Node getRootNode() {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		
		return doc.getDocumentElement();	
	}
	private Node[] getNodeChildren(Node node) {
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
	public Node getNode(String tagName) {
		Node root = getRootNode();
		return getNodeChildren(root)[0];
	}
}
