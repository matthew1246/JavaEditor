package com.customyjavafx;

import java.util.ArrayList;
import java.util.List;
public class Preferences {
	public boolean isLocked = false;
	public String lockedStartupClass = "";
	public List<String> startupcombobox = new ArrayList<String>();
	public String starterclass="";
	public List<String> jars = new ArrayList<String>();
	
	// Below is tabs
	public List<String> fileNames = new ArrayList<String>();
	public int caretposition=0;
}
