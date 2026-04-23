package com.the;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Packager {
	private Main main;
	public Packager(Main main) {
		this.main = main;
	}
	private String fileName;
	public Packager(String fileName) {
		this.fileName = fileName;
	}
	public String regex = "\\s*package\\s+([\\w.]+)\\s*\\;";
	public boolean containsPackage() {
		Pattern pattern=Pattern.compile(regex,Pattern.MULTILINE);	
		Matcher matcher;
		if(main != null) {
			matcher=pattern.matcher(main.textarea.getText());
		}
		else {
			try {
				Path path = Paths.get(fileName);
				String lines2= Files.readString(path,StandardCharsets.UTF_8);
				matcher=pattern.matcher(lines2);
			} catch(IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}	

	

		return matcher.find();
	}
	public String getPackageName() {
		try {
			Pattern pattern=Pattern.compile(regex,Pattern.MULTILINE);	
			Matcher matcher;
			if(main != null) {
				matcher=pattern.matcher(main.textarea.getText());
			}
			else {
				Path path = Paths.get(fileName);
				String lines2= Files.readString(path,StandardCharsets.UTF_8);	
				matcher=pattern.matcher(lines2);
			}		
			if(matcher.find()) {
				return matcher.group(1);
			}
			else {
				return "Could not find package name.";
			}		
		} catch(IOException ex) {
			ex.printStackTrace();
			return "Not found!";
		}							
							
	}
	public String classpath = "";
	public boolean isInRightFolders() {
		if(!containsPackage())
			return false;	
		
		File file;
		if(main != null) {
			file = new File(main.fileName);
		}
		else {
			file=new File(fileName);
		}
		String[] folders = getPackageName().split("\\.");
		for(int i = folders.length-1; i >= 0; i--) {
			String foldername = folders[i];
			String folder = file.getParent();
			file = new File(folder);
			
			if(!foldername.equals(file.getName()))
				return false;		
		}
		classpath=file.getParentFile().getAbsolutePath();
		return true;
	}
}

