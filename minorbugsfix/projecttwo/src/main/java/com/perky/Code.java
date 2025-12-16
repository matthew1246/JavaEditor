package com.perky;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Code {
	public String code;
	public Code(String code) {
		this.code = code;
	}
	public List<Code> codes = new ArrayList<Code>();
	public String ExpandAll() {
		for(int i = 0; i < codes.size(); i++) {
			codes.set(i,new Code(codes.get(i).ExpandAll()));
		}
		if(codes.size() == 0) {
			return code;
		}
		else {
			Pattern pattern=Pattern.compile("(?<!\")\\{\\+\\}(?!\")");
			Matcher matcher=pattern.matcher(code);
			int count = -1;
			
			StringBuilder stringbuilder = new StringBuilder();
			while(matcher.find()) {
				count++;
				String match=matcher.group();
				match=match.replaceAll("\\{\\+\\}",Matcher.quoteReplacement(codes.get(count).code));
				matcher.appendReplacement(stringbuilder,matcher.quoteReplacement(match));
			}
			matcher.appendTail(stringbuilder);
			return stringbuilder.toString();	
		}
	}
}