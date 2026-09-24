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
** The user selects which folders to exclude from the jar.
** The user can select the direct subfolders of the classpath AND all subfolders
** of the package name folders (for example package javaeditor.minorbugsfixwithai
** lets the user select all javaeditor subfolders).
** The folders that were not selected to exclude are added to the jar with:
** jar.exe ... -C "<classpath>" <relative folder path> ...
*/
public class AllVersionsJarMoreThanOnePackageExcludePackages extends AllVersionsJarMoreThanOnePackage {
	private IsMoreThanOneJar isMoreThanOneJar;
	private String classpath;
	private List<String> includedFolders;
	public AllVersionsJarMoreThanOnePackageExcludePackages(Main main,String fileName,SaveActionListener sal,ActionEvent ev4,boolean _isMoreThanOneJar) {
		super(main,fileName,sal,ev4,_isMoreThanOneJar);
		isMoreThanOneJar=new IsMoreThanOneJar(_isMoreThanOneJar);
		classpath = packager.classpath;
		if(classpath == null || classpath.equals(""))
			classpath = getDir();
		if(!classpath.endsWith("\\"))
			classpath = classpath+"\\";
		includedFolders = selectIncludedFolders();
	}
	public List<String> getIncludedFolders() {
		return includedFolders;
	}
	public String getClasspath() {
		return classpath;
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
		javax.swing.JList<String> list = new javax.swing.JList<String>(names);
		list.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		javax.swing.JScrollPane scrollpane = new javax.swing.JScrollPane(list);
		javax.swing.JPanel panel = new javax.swing.JPanel();
		panel.add(scrollpane);
		String[] options = {"OK","Cancel"};
		int result = JOptionPane.showOptionDialog(null,panel,"Select folders to EXCLUDE from jar:",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		if(result != 0) {
			included.add(".");
			return included;
		}
		List<String> selected = list.getSelectedValuesList();
		Set<String> excluded = new HashSet<String>(selected);
		addIncludedFolders(new File(classpath),excluded,included);
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
	private void addIncludedFolders(File folder,Set<String> excluded,List<String> included) {
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
				addIncludedFolders(subfolder,excluded,included);
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
			addIncludedFolders(subfolder,excluded,included);
		}
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
			JOptionPane.showMessageDialog(null,"Output jar location is:"+isMoreThanOneJar.getCreateJarFolderLocation(getDir()));
			
			String input = "\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+isMoreThanOneJar.getCreateJarFolderLocation(getDir())+"\\ForJava"+javaversionnumber+"_"+main_class2+".jar mf.txt";
			if(javaversionnumber == 23 || javaversionnumber == -2) {
				input = "\""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+isMoreThanOneJar.getCreateJarFolderLocation(getDir())+"\\"+main_class2+".jar mf.txt";
			}
			for(String relative:includedFolders) {
				input = input+" -C \""+classpath+"\" "+relative;
			}
		
			JOptionPane.showMessageDialog(null,input);
			CommandLine commandline = new CommandLine();
			
			String classnameJar = getDir() + packager.getPackageName().replace(".", "\\") + "\\" + main_class2 + ".jar";
			File existingJar = new File(classnameJar);
			if(existingJar.exists()) {
				existingJar.delete();
			}
			
			classnameJar = getDir()+main_class2+".jar";
			existingJar = new File(classnameJar);
			if(existingJar.exists()) {
				existingJar.delete();
			}
			
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