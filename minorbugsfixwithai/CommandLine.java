import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
public class CommandLine {
 	public static void main(String[] args) {
 		CommandLine commandline = new CommandLine();
 		if(commandline.doesCommandExist("cls")) {
		            System.out.println("Command does exist!");
	            } else {
		           	System.out.println("Command doesn't exist!");
            	}
 	}
 	public StarNorDot javac_star_nor_dot = new StarNorDot();
 	public StarNorDot java_star_nor_dot= new StarNorDot();
	private String junitmain_class = "";
	public String main_class;
	private List<String> jars = new ArrayList<String>();
	
	public boolean isdeprecated = false;
	public boolean isearlierversion = false;
	public int javaversion;
	public void earlierjavaversion(int javaversion) {
		isearlierversion = true;
		this.javaversion = javaversion;
	}
	public void addJavaFX() {
		for(String jar:getJavaFX()) {
			addExternalJar(jar);
		}
	}
	public List<String> getJavaFX() {
		List<String> javafx = new ArrayList<String>();
			
		javafx.add("javafx.base.jar");
		javafx.add("javafx.controls.jar");
		javafx.add("javafx.fxml.jar");
		javafx.add("javafx.graphics.jar");
		javafx.add("javafx.media.jar");
		javafx.add("javafx.swing.jar");
		javafx.add("javafx.web.jar");
		javafx.add("javafx-swt.jar");
		javafx.add("jdk.jsobject.jar");
		javafx.add("jfx.incubator.input.jar");
		javafx.add("jfx.incubator.richtext.jar");
		
		return javafx;
	}
	public void deprecated() {
		isdeprecated = true;
	}
	public void addExternalJar(String jar) {
		javac_star_nor_dot.setStarNorDot(".");
		java_star_nor_dot.setStarNorDot(".");
		jars.add(jar);
	}
	
	public void add(String jar) {
		jars.add(jar);
	}
	
	public void setMainClass(String class0) {
		main_class =class0;		
	}
	
	public void addJunit() {
		jars.add(0,"junit-4.13.2.jar");
		jars.add(0,"hamcrest-core-1.3.jar");
		junitmain_class=" org.junit.runner.JUnitCore";
		
		javac_star_nor_dot.setStarNorDot(".");
		
		java_star_nor_dot.setStarNorDot(".");
	}
	private String packagename;
	
private boolean isPackage = false;
	public void addPackage(String packagename) {
		isPackage = true;	
		this.packagename=packagename;
	}
	private boolean isPackageWithMinusD=false;
	public void addPackageWithMinusD(String packagename) {
		isPackageWithMinusD = true;	
		this.packagename=packagename;			
	}	
	public void addPackageWithMinusD() {
		isPackageWithMinusD = true;		
	}
	/*
	** This adds all jars in public static void main(String[] args) directory.
	*/
	public void addClasspathCheckboxFeature() {
		String default0 = Platform.isWindows() ? ".*" : ".:*";
		if(isPackage && !isPackageWithMinusD) {
			default0=Platform.isWindows() ? ".*" : ".:*";
		}
		else if(isPackageWithMinusD) {
			default0=Platform.isWindows() ? ".*" : ".:*";
		}
		javac_star_nor_dot.setStarNorDot(default0);
		javac_star_nor_dot.lock();

		if(isPackage && !isPackageWithMinusD) {
			default0=Platform.isWindows() ? ".*" : ".:*";
		}
		else if(isPackageWithMinusD) {
			default0=Platform.isWindows() ? ".*" : ".:*";
		}
		java_star_nor_dot.setStarNorDot(default0);
		java_star_nor_dot.lock();
	}

	public String getJars() {
		return String.join(File.pathSeparator,jars);
	}

	public String getClasspath(StarNorDot isjavaorjavac) {
		if(isjavaorjavac.isEmpty() && jars.size() == 0) {
			return "";
		}
		String classpath = "";
		if(jars.size() > 0) {
			classpath= classpath+jars.get(0);
			for(int i = 1; i < jars.size(); i++) {
				classpath=classpath+File.pathSeparator+jars.get(i);
			}
		}
		if(!isjavaorjavac.isEmpty() && jars.size() > 0) {
			classpath=File.pathSeparator+classpath;
		}
		if(!isjavaorjavac.isEmpty()) {
			classpath=isjavaorjavac.getStarNorDot()+classpath;
		}

classpath = "-cp "+classpath;
		return classpath;
	}
	
	public void compileAll() {
		main_class="*";
	}

	public String Prettify(String str) {
		if(str.equals("")) {
			return str;
		}
			else {
			return " "+str;
		}
	}
	
	public String javac() {
		String package1 = "";
		if(isPackage && !isPackageWithMinusD) {
			package1=packagename.replace(".","/")+"/";
		}						
		//String str = "\""+System.getProperty("java.home")+"\\bin\\javac.exe\"";
		String str="javac";
		if(isdeprecated) {
			str+=" -Xlint:deprecation";
		}
		if(isearlierversion) {
			str+=" --release "+javaversion;
		}
		if(isPackageWithMinusD) {
			str+=" -d . ";			
		}		
		str=str+Prettify(getClasspath(javac_star_nor_dot))+" "+package1+main_class+".java";
		if(!java.awt.GraphicsEnvironment.isHeadless()) {
			JOptionPane.showMessageDialog(null,str);
		}
		return str;
	}
	
	public String java() {
		String package2="";
		if(isPackage && !isPackageWithMinusD) {
			package2=packagename+".";
		}			
		else if(isPackageWithMinusD) {
			package2=packagename+".";
		}		
		//String command = "java"+Prettify(getClasspath(java_star_nor_dot))+Prettify(junitmain_class)+" "+package2+main_class;
		//String command=  "\""+System.getProperty("java.home")+"\\bin\\java.exe\""+Prettify(getClasspath(java_star_nor_dot))+Prettify(junitmain_class)+" "+package2+main_class;
		String command=  "java"+Prettify(getClasspath(java_star_nor_dot))+Prettify(junitmain_class)+" "+package2+main_class;
		if(!java.awt.GraphicsEnvironment.isHeadless()) {
			JOptionPane.showMessageDialog(null,command);
		}
		return command;
	}	
	
	/*
	** Run any command without showing msdos.
	** @classpath is current directory in MSDOS.
	*/
	public Process run(String command,String dir) {
		try {
			/*
			** The command is given to the shell of the system, so that the
			** quotes and the paths with spaces in them are kept as they are.
			** On Windows that is cmd.exe and on Linux it is bash.
			*/
			String[] commands=Platform.shellCommand(command);
			ProcessBuilder processbuilder=new ProcessBuilder(commands);
			processbuilder.redirectErrorStream(true);
			if(dir != null && !dir.isEmpty())
				processbuilder.directory(new File(dir));
			
			Process process=processbuilder.start();
			return process;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/*
	** Run any command as administrator
	*/
	public Process runAsAdmin(String command, String dir) 	{
		try {
			// command format: "C:\path\to\jar.exe" arg1 arg2 ...
			int endQuote = command.indexOf("\"", 1);
			if(endQuote < 0) {
				return run(command,dir);
			}
			String exePath = command.substring(1, endQuote);
			String args = command.substring(endQuote + 1).trim();
			if(!Platform.isWindows()) {
				/*
				** Linux has no administrator rights that work the same way as
				** they do on Windows, so the command is simply run.
				*/
				return run(command,dir);
			}
			String ps = "Start-Process -FilePath '" + exePath + "' -Verb RunAs -ArgumentList '" + args.replace(" ", "','") + "'";
			System.out.println("command: "+ps);
			ProcessBuilder pb = new ProcessBuilder(
				"powershell", "-NoProfile", "-Command", ps
			);
			pb.redirectErrorStream(true);
			if(dir != null && !dir.isEmpty())
				pb.directory(new File(dir));
			Process process = pb.start();
			return process;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/*
	** Run any command while showing MSDOS console
	*/
	public Process runWithMSDOS(String command,String dir) {
		if(Platform.isWindows()) {
			String[] command2 = new String[6];
			command2[0] = "cmd";
			command2[1] = "/c";
			command2[2] = "start";
			command2[3] = "cmd";
			command2[4]= "/k";
			command2[5] = command;
			return run(command2,dir);
		}
		/*
		** Linux has no console window of its own, so a terminal emulator is
		** used to show the command. When there is no terminal emulator on the
		** computer the command is run without a window.
		*/
		if(Platform.openConsole(command,dir)) {
			return null;
		}
		try {
			ProcessBuilder processbuilder=new ProcessBuilder(Platform.shellCommand(command));
			processbuilder.inheritIO();
			if(dir != null && !dir.isEmpty())
				processbuilder.directory(new File(dir));
			return processbuilder.start();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/*
	** Run any program without msdos
	*/
	public Process run(String[] arguments,String dir) {
		try {
			ProcessBuilder processbuilder=new ProcessBuilder(arguments);
			processbuilder.redirectErrorStream(true);
			if(dir != null && !dir.isEmpty())
				processbuilder.directory(new File(dir));
			
			Process process=processbuilder.start();
			return process;

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public boolean doesCommandExist(String command) {
		return Platform.commandExists(command);
    }
}
																																
