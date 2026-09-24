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
** The user selects which folders from packager.classpath to exclude.
** The folders that were not selected to exclude are added to the jar with jar.exe -C folder .
*/
public class AllVersionsJarMoreThanOnePackageExcludePackages extends AllVersionsJarMoreThanOnePackage {
	private IsMoreThanOneJar isMoreThanOneJar;
	private List<String> includedFolders;
	public AllVersionsJarMoreThanOnePackageExcludePackages(Main main,String fileName,SaveActionListener sal,ActionEvent ev4,boolean _isMoreThanOneJar) {
		super(main,fileName,sal,ev4,_isMoreThanOneJar);
		isMoreThanOneJar=new IsMoreThanOneJar(_isMoreThanOneJar);
		includedFolders=selectIncludedFolders();
	}
	public List<String> getIncludedFolders() {
		return includedFolders;
	}
	private List<String> selectIncludedFolders() {
		String classpath = packager.classpath;
		if(classpath == null || classpath.equals(""))
			classpath = getDir();
		File classpathDir = new File(classpath);
		File[] subfolders = classpathDir.listFiles(File::isDirectory);
		List<String> included = new ArrayList<String>();
		if(subfolders == null || subfolders.length == 0) {
			included.add(classpath);
			return included;
		}
		String[] names = new String[subfolders.length];
		for(int i = 0; i < subfolders.length; i++) {
			names[i] = subfolders[i].getName();
		}
		javax.swing.JList<String> list = new javax.swing.JList<String>(names);
		list.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		javax.swing.JScrollPane scrollpane = new javax.swing.JScrollPane(list);
		javax.swing.JPanel panel = new javax.swing.JPanel();
		panel.add(scrollpane);
		String[] options = {"OK","Cancel"};
		int result = JOptionPane.showOptionDialog(null,panel,"Select folders to EXCLUDE from jar:",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		if(result != 0) {
			included.add(classpath);
			return included;
		}
		List<String> selected = list.getSelectedValuesList();
		Set<String> excluded = new HashSet<String>(selected);
		for(int i = 0; i < subfolders.length; i++) {
			if(!excluded.contains(names[i])) {
				included.add(subfolders[i].getAbsolutePath());
			}
		}
		if(included.size() == 0)
			included.add(classpath);
		return included;
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
			for(String folder:includedFolders) {
				input = input+" -C \""+folder+"\" .";
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
		return new PowershellMoreThanOnePackageExcludePackages(main,main_class,dir,allfiles,isMoreThanOneJar.isMoreThanOneJar,includedFolders);
	}
}