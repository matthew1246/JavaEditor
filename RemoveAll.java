import java.util.regex.*;
import javax.swing.JOptionPane;
public class RemoveAll {
	public static void main(String[] args) {
		String comment = "//}";
		System.out.println(comment);
		comment=Comments(comment);
		System.out.println(comment);
		/*String wholetext = "\"jkjkjjkk\"";// "  kjjkjkjkjk jkkjkjkj"; {
		//menubar.setPreferredSize(new Dimension(0,50)); {
		System.out.println(wholetext);		
		wholetext=Strings(wholetext);
		System.out.println("1: "+wholetext);
		
		System.out.println();
		
		wholetext="\"jkjkj\\\"jkjkjjk\"";
		System.out.println(wholetext);
		wholetext=Strings(wholetext);
		System.out.println("2: "+wholetext);
		
		System.out.println();
		
		wholetext="\"jkjkj\\\"jkjkjjk\" jjkkjjk\"jjkkjkkjkj\"";
		System.out.println(wholetext);
		wholetext=Strings(wholetext);
		System.out.println("3: "+wholetext);

		System.out.println();
		
		wholetext="\"jkjkj\\\"jkjkjjk\\\" jjkkjjk\\\"jjkkjkkjkj\"";
		System.out.println(wholetext);
		wholetext=Strings(wholetext);
		System.out.println("4: "+wholetext);
		
		System.out.println();
		
		wholetext="\"\"\"";
		System.out.println(wholetext);
		wholetext=Strings(wholetext);
		System.out.println("5: "+wholetext);
		
		System.out.println();
		
		wholetext="\"\" \"";
		System.out.println(wholetext);
		wholetext=Strings(wholetext);
		System.out.println("6: "+wholetext);
		*/
	}
	/*
	** This remove all single line comments from a string.
	*/
	public static String Comments(String wholetext) {
		wholetext=wholetext.replaceAll("//","");
		wholetext=wholetext.replaceAll("//\\}","");
		return wholetext;
	}
	
	public static String LeftCurlyBraceInsideComments(String wholetext) {
		try {
			Pattern pattern=Pattern.compile("/\\*.+\\*/");
			Matcher matcher=pattern.matcher(wholetext);
			StringBuilder stringbuilder = new StringBuilder();
			while(matcher.find()) {
				// String change=matcher.group();
				String change=matcher.group();
				change=change.replaceAll("\\{|\\}"," ");
				matcher.appendReplacement(stringbuilder,matcher.quoteReplacement(change));
			}
			matcher.appendTail(stringbuilder);
			wholetext=stringbuilder.toString();
			//System.out.println(wholetext);
			wholetext=Comments(wholetext);
			//System.out.println(wholetext);
			wholetext=Strings(wholetext);
		}
		catch(StackOverflowError error) {
			JOptionPane.showMessageDialog(null,wholetext);
			error.printStackTrace();
		}
		return wholetext;
	}
	
	public static String Strings(String wholetext) {
		try {
			//wholetext= wholetext.replaceAll("(?<!\\\\)\"([^\"]|(\\\\\"))*?(?<!\\\\)\"","");
			
			Pattern pattern=Pattern.compile("(?<!\\\\)\".*?(\\{|\\})+?.*?(?<!\\\\)\"");
			Matcher matcher=pattern.matcher(wholetext);
			StringBuilder stringbuilder = new StringBuilder();
			while(matcher.find()) {
				String match=matcher.group();
				match=match.replaceAll("\\{|\\}"," ");
				matcher.appendReplacement(stringbuilder,matcher.quoteReplacement(match));
			}
			matcher.appendTail(stringbuilder);
			wholetext=stringbuilder.toString();
		}
		catch (StackOverflowError error) {
			JOptionPane.showMessageDialog(null,"RemoveAll.Strings() error.");
			error.printStackTrace();
		}
		return wholetext;
	}
}