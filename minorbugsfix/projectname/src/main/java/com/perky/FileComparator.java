package com.perky;

import java.util.Comparator;
import java.io.File;
public class FileComparator implements Comparator<File> {
	public int compare(File file1,File file2) {
		 if(file1.lastModified() > file2.lastModified()) {
		 	return -1;
	 	}
	 	else if(file1.lastModified() < file2.lastModified()) {
	 		return 1;
 		}
 		else { // if file1.lastModified() == file2.lastModified()
 			return 0;
 		}
 	} 			
}