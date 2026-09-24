import java.util.regex.*;
import javax.swing.JOptionPane;
public class RemoveAll {
	public static void main(String[] args) {
		String comment = "//}";
		System.out.println(comment);
		comment=Comments(comment);
		System.out.println(comment);
	}
	/*
	** This remove all single line comments from a string.
	*/
	public static String Comments(String wholetext) {
		Pattern pattern=Pattern.compile("//.*");
		Matcher matcher=pattern.matcher(wholetext);
		StringBuilder stringbuilder = new StringBuilder();
		while(matcher.find()) {
			String change=matcher.group();
			change=change.replaceAll("[{}]"," ");
			matcher.appendReplacement(stringbuilder,matcher.quoteReplacement(change));
		}
		matcher.appendTail(stringbuilder);
		wholetext=stringbuilder.toString();
		
		return wholetext;
	}
	
	public static String LeftCurlyBraceInsideComments(String wholetext) {
		try {
			wholetext = removeBracesFromCommentsAndStrings(wholetext);
		}
		catch(StackOverflowError error) {
			JOptionPane.showMessageDialog(null,wholetext);
			error.printStackTrace();
		}
		return wholetext;
	}

	private static String removeBracesFromCommentsAndStrings(String text) {
		int state = 0;
		final int NORMAL = 0;
		final int BLOCK_COMMENT = 1;
		final int LINE_COMMENT = 2;
		final int STRING = 3;
		final int CHAR_LITERAL = 4;

		StringBuilder result = new StringBuilder(text.length());
		int i = 0;
		int len = text.length();

		while (i < len) {
			char c = text.charAt(i);
			char next = (i + 1 < len) ? text.charAt(i + 1) : 0;

			switch (state) {
				case NORMAL:
					if (c == '/' && next == '*') {
						state = BLOCK_COMMENT;
						result.append(c);
						result.append(next);
						i += 2;
					} else if (c == '/' && next == '/') {
						state = LINE_COMMENT;
						result.append(c);
						result.append(next);
						i += 2;
					} else if (c == '"') {
						state = STRING;
						result.append(c);
						i++;
					} else if (c == '\'') {
						state = CHAR_LITERAL;
						result.append(c);
						i++;
					} else {
						result.append(c);
						i++;
					}
					break;

				case BLOCK_COMMENT:
					if (c == '*' && next == '/') {
						state = NORMAL;
						result.append(c);
						result.append(next);
						i += 2;
					} else if (c == '{' || c == '}') {
						result.append(' ');
						i++;
					} else {
						result.append(c);
						i++;
					}
					break;

				case LINE_COMMENT:
					if (c == '\n') {
						state = NORMAL;
						result.append(c);
						i++;
					} else if (c == '{' || c == '}') {
						result.append(' ');
						i++;
					} else {
						result.append(c);
						i++;
					}
					break;

				case STRING:
					if (c == '\\' && next != 0) {
						result.append(c);
						result.append(next);
						i += 2;
					} else if (c == '"') {
						state = NORMAL;
						result.append(c);
						i++;
					} else if (c == '{' || c == '}') {
						result.append(' ');
						i++;
					} else {
						result.append(c);
						i++;
					}
					break;

				case CHAR_LITERAL:
					if (c == '\\' && next != 0) {
						result.append(c);
						result.append(next);
						i += 2;
					} else if (c == '\'') {
						state = NORMAL;
						result.append(c);
						i++;
					} else if (c == '{' || c == '}') {
						result.append(' ');
						i++;
					} else {
						result.append(c);
						i++;
					}
					break;
			}
		}

		return result.toString();
	}
	
	public static String Strings(String wholetext) {
		return wholetext;
	}
}
