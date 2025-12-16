package com.perky;

import java.util.LinkedHashMap;
public class IsEqual {
	public boolean isEqual(LinkedHashMap<String,Preferences> linkedhashmap,LinkedHashMap<String,Preferences> linkedhashmap2) {
		boolean isresult = Compare(linkedhashmap,linkedhashmap2);
		if(isresult) {
			return Compare(linkedhashmap2,linkedhashmap);
		}
		return false;
	}	
	public boolean Compare(LinkedHashMap<String,Preferences> linkedhashmap,LinkedHashMap<String,Preferences> linkedhashmap2) {
		for(String key:linkedhashmap2.keySet()) {
			if(!linkedhashmap.containsKey(key))
				return false;
			Preferences preferences2=linkedhashmap2.get(key);
			Preferences preferences = linkedhashmap.get(key);
			if(!preferences2.starterclass.equals(preferences.starterclass))
				return false;
			for(String jar:preferences2.jars) {
				boolean containsjar = false;
				for(String jar0:preferences.jars) {
					if(jar.equals(jar0)) 
						containsjar = true;
				}
				if(!containsjar)
					return false;
			}
			for(String fileName:preferences2.fileNames) {
				boolean containsfileName=  false;
				for(String fileName0:preferences.fileNames) {
					if(fileName.equals(fileName0)) 
						containsfileName = true;
				}
				if(!containsfileName)
					return false;
			}

			if(preferences2.caretposition != preferences.caretposition)
				return false;
		}
		return true;
	}
}

				