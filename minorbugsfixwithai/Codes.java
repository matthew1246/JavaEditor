import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JTextArea;
public class Codes {
	public JTextArea textarea;
	public Codes(JTextArea textarea) {
		this.textarea = textarea;
	}
	public List<Group> codes = new ArrayList<Group>();	
	public void add(Group group) {
		if(codes.size() == 0) {
			codes.add(group);
		}
		else {
		}
	}
	public List<Integer> getCodes() {
		List<Integer> list = new ArrayList<Integer>();
				
		String text = textarea.getText();
		Pattern pattern=Pattern.compile("(?<!\")\\{\\+\\}(?!\")");
		Matcher matcher=pattern.matcher(text);
		while(matcher.find()) {
			list.add(matcher.start());
		}
		return list;
	}
	public int getIndex(List<Integer> codes,int caretposition) {
		for(int i = 0; i < codes.size(); i++) {
			int code = codes.get(i);
			if(code >= caretposition)
				return i;
		}
		return codes.size();
	}				
}
