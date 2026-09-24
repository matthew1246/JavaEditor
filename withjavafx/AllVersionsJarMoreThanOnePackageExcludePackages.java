package javaeditor.withjavafx;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
** This generates all versions of Java for Jars
** This class is only if Main.jar is not running.
** The user is asked whether they want to select folders to EXCLUDE or folders
** to INCLUDE in the jar.
** The user can select the direct subfolders of the classpath AND all subfolders
** of the package name folders (for example package javaeditor.foo lets the user
** select all javaeditor subfolders).
** The included folders are added to the jar with:
** jar.exe ... -C "<classpath>" <relative folder path> ...
*/
public class AllVersionsJarMoreThanOnePackageExcludePackages extends AllVersionsJarMoreThanOnePackage {
	private String classpath;
	private List<String> includedFolders;
	public AllVersionsJarMoreThanOnePackageExcludePackages(Main main,String fileName,SaveActionListener sal,ActionEvent ev4,boolean _isMoreThanOneJar) {
		super(main,fileName,sal,ev4,_isMoreThanOneJar);
		classpath = packager.classpath;
		if(classpath == null || classpath.equals(""))
			classpath = getDir();
		if(classpath.endsWith("\\"))
			classpath = classpath.substring(0,classpath.length()-1);
		includedFolders = selectIncludedFolders();
	}
	public List<String> getIncludedFolders() {
		return includedFolders;
	}
	public String getClasspath() {
		return classpath;
	}
	@Override
	public Preferences extractJars(StoreSelectedFile storeselectedfile) {
		CommandLine commandline = new CommandLine();
		Preferences preferences=storeselectedfile.get(fileName);
		if(!fileName.equals("")) {
			java.util.List<String> jars = preferences.jars;
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				for(String jar:jars) {
					try {
						Process process=commandline.run("\""+System.getProperty("java.home")+"\\bin\\jar.exe\" xf "+jar,getDir());
						process.waitFor();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
			else { // package is in right folders: extract the classpath jars into <classpath>\jars
				File createdir = new File(classpath+"\\jars");
				if(!createdir.exists()) {
					createdir.mkdir();
				}
				for(String jar:jars) {
					try {
						Process process=commandline.run("\""+System.getProperty("java.home")+"\\bin\\jar.exe\" xf "+jar,classpath+"\\jars");
						process.waitFor();
					} catch(InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return preferences;
	}
	private boolean isPackageFolder(File folder) {
		String packagename = packager.getPackageName();
		String[] segments = packagename.split("\\.");
		File pkg = new File(classpath);
		for(String segment:segments) {
			pkg = new File(pkg,segment);
		}
		String pkgPath = pkg.getAbsolutePath();
		String folderPath = folder.getAbsolutePath();
		return pkgPath.equals(folderPath) || pkgPath.startsWith(folderPath+"\\") || pkgPath.startsWith(folderPath+"/");
	}
	private void addAllDescendantFolders(File dir,List<File> list) {
		File[] subfolders = dir.listFiles(File::isDirectory);
		if(subfolders == null)
			return;
		for(File subfolder:subfolders) {
			list.add(subfolder);
			addAllDescendantFolders(subfolder,list);
		}
	}
	private List<File> getCandidateFolders() {
		List<File> candidates = new ArrayList<File>();
		File classpathDir = new File(classpath);
		File[] topfolders = classpathDir.listFiles(File::isDirectory);
		if(topfolders != null) {
			for(File folder:topfolders) {
				candidates.add(folder);
				if(isPackageFolder(folder)) {
					addAllDescendantFolders(folder,candidates);
				}
			}
		}
		return candidates;
	}
	private List<String> selectIncludedFolders() {
		List<File> candidates = getCandidateFolders();
		List<String> included = new ArrayList<String>();
		if(candidates.size() == 0) {
			included.add(".");
			return included;
		}
		String[] names = new String[candidates.size()];
		for(int i = 0; i < candidates.size(); i++) {
			names[i] = candidates.get(i).getAbsolutePath();
		}
		String[] modeOptions = {"Select folders to EXCLUDE","Select folders to INCLUDE"};
		int mode = JOptionPane.showOptionDialog(null,"Do you want to select folders to exclude or include?","Exclude or Include",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,modeOptions,modeOptions[1]);
		if(mode < 0) {
			included.add(".");
			return included;
		}
		javax.swing.JList<String> list = new javax.swing.JList<String>(names);
		list.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		javax.swing.JScrollPane scrollpane = new javax.swing.JScrollPane(list);
		javax.swing.JPanel panel = new javax.swing.JPanel();
		panel.add(scrollpane);
		String[] options = {"OK","Cancel"};
		if(mode == 0) {
			int result = JOptionPane.showOptionDialog(null,panel,"Select folders to EXCLUDE from jar (folders not selected are included):",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
			if(result != 0) {
				included.add(".");
				return included;
			}
			List<String> selected = list.getSelectedValuesList();
			if(selected.size() == 0) {
				included.add(".");
				return included;
			}
			Set<String> excluded = new HashSet<String>(selected);
			addIncludedFoldersFromExcluded(new File(classpath),excluded,included);
		}
		else {
			int result = JOptionPane.showOptionDialog(null,panel,"Select folders to INCLUDE in jar (folders not selected are excluded):",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
			if(result != 0) {
				included.add(".");
				return included;
			}
			List<String> selected = list.getSelectedValuesList();
			if(selected.size() == 0) {
				included.add(".");
				return included;
			}
			Set<String> selectedSet = new HashSet<String>(selected);
			for(String path:selected) {
				File folder = new File(path);
				if(hasSelectedAncestor(folder,selectedSet))
					continue;
				included.add(getRelativePath(folder));
			}
		}
		if(included.size() == 0)
			included.add(".");
		return included;
	}
	private boolean hasExcludedDescendant(File folder,Set<String> excluded) {
		String path = folder.getAbsolutePath();
		if(excluded.contains(path))
			return true;
		File[] subfolders = folder.listFiles(File::isDirectory);
		if(subfolders == null)
			return false;
		for(File subfolder:subfolders) {
			if(hasExcludedDescendant(subfolder,excluded))
				return true;
		}
		return false;
	}
	private void addIncludedFoldersFromExcluded(File folder,Set<String> excluded,List<String> included) {
		if(excluded.contains(folder.getAbsolutePath()))
			return;
		if(folder.getAbsolutePath().equals(new File(classpath).getAbsolutePath())) {
			if(!hasExcludedDescendant(folder,excluded)) {
				included.add(".");
				return;
			}
			File[] subfolders = folder.listFiles(File::isDirectory);
			if(subfolders == null)
				return;
			for(File subfolder:subfolders) {
				addIncludedFoldersFromExcluded(subfolder,excluded,included);
			}
			return;
		}
		if(!hasExcludedDescendant(folder,excluded)) {
			included.add(getRelativePath(folder));
			return;
		}
		File[] subfolders = folder.listFiles(File::isDirectory);
		if(subfolders == null)
			return;
		for(File subfolder:subfolders) {
			addIncludedFoldersFromExcluded(subfolder,excluded,included);
		}
	}
	private boolean hasSelectedAncestor(File folder,Set<String> selected) {
		File parent = folder.getParentFile();
		while(parent != null) {
			String parentPath = parent.getAbsolutePath();
			if(parentPath.equals(new File(classpath).getAbsolutePath()))
				return false;
			if(selected.contains(parentPath))
				return true;
			parent = parent.getParentFile();
		}
		return false;
	}
	private String getRelativePath(File folder) {
		String folderPath = folder.getAbsolutePath();
		String classpathPath = new File(classpath).getAbsolutePath();
		if(folderPath.equals(classpathPath))
			return ".";
		String relative = folderPath.substring(classpathPath.length());
		if(relative.startsWith("\\") || relative.startsWith("/"))
			relative = relative.substring(1);
		return relative.replace("\\","/");
	}
	@Override
	public void MakeJarUsingmsdos(int javaversionnumber,String main_class) {
		try {
			String[] splited=  main_class.split("\\.");
			String main_class2 = splited[splited.length-1];
			String createdJarFolderCreation=isMoreThanOneJar.getCreateJarFolderLocation(getDir());
			if(!createdJarFolderCreation.endsWith("\\"))
				createdJarFolderCreation=createdJarFolderCreation+"\\";
			JOptionPane.showMessageDialog(null,"Jar folder location is:"+createdJarFolderCreation);
			
			String input = "";
			if(javaversionnumber == 23 || javaversionnumber == -2) {
				input = "\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+createdJarFolderCreation+main_class2+".jar mf.txt";
			}
			else {
				input = "\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+createdJarFolderCreation+"ForJava"+javaversionnumber+"_"+main_class2+".jar mf.txt";
			}
			for(String relative:includedFolders) {
				input = input+" -C "+classpath+" "+relative;
			}
			if(new File(classpath+"\\jars").exists()) {
				input = input+" -C jars .";
			}
		
			JOptionPane.showMessageDialog(null,input);
			CommandLine commandline = new CommandLine();
			Process process;
			if(getDir().replace("\\","").matches("[a-zA-Z]:")) {
				process=commandline.runAsAdmin(input,getDir());
			}
			else {
				process=commandline.run(input,getDir());
			}
			
			java.io.InputStream inputstream = process.getErrorStream();
			java.io.InputStreamReader inputstreamreader = new java.io.InputStreamReader(inputstream);
			java.io.BufferedReader bufferedreader = new java.io.BufferedReader(inputstreamreader);
			String line = bufferedreader.readLine();
			if(line == null) {
				JOptionPane.showMessageDialog(null,"jar created");
			}
			else {
				String lines = line;
				while(true) {
					line = bufferedreader.readLine();
					if(line == null)
						break;
					lines = lines+"\n"+line;
				}
				JOptionPane.showMessageDialog(null,lines);
			}
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
	}
	@Override
	public Powershell getPowershell(Main main,String main_class,String dir,AllFiles allfiles) {
		return new PowershellMoreThanOnePackageExcludePackages(main,main_class,dir,allfiles,isMoreThanOneJar.isMoreThanOneJar,classpath,includedFolders);
	}
}