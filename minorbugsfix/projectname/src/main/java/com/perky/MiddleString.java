package com.perky;

public class MiddleString {
	private String wholetext;
	public MiddleString(String wholetext) {
		this.wholetext = wholetext;
	}
	
	public String getLineFromWholeDocumentIndex(int wholedocumentindex) {
		 String firsthalf = wholetext.substring(0,wholedocumentindex);
		String[] lines = firsthalf.split("\n");
		return lines[lines.length-1];
	}
	
	public String getPreviousLine(int wholedocumentindex) {
		if(wholedocumentindex == -1)
			return "No previous line!";
		String firsthalf = wholetext.substring(0,wholedocumentindex);
		String[] lines = firsthalf.split("\n");
		if(lines.length == 1)
			return "No previous line 2!";
		return lines[lines.length-2];
	}
}
