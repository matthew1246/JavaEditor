import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
public class CommandLine {
 	public StarNorDot javac_star_nor_dot = new StarNorDot();
 	public StarNorDot java_star_nor_dot= new StarNorDot();
	private String junitmain_class = "";
	private String main_class;
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
	
	/*
	** This adds all jars in public static void main(String[] args) directory.
	*/
	public void addClasspathCheckboxFeature() {
		javac_star_nor_dot.setStarNorDot("*");
		javac_star_nor_dot.lock();
		
		java_star_nor_dot.setStarNorDot("*;.");
		java_star_nor_dot.lock();
	}
	
	public String getJars() {
		return String.join(";",jars);
	}
	
	public String getClasspath(StarNorDot isjavaorjavac) {
		if(isjavaorjavac.isEmpty() && jars.size() == 0) {
			return "";
		}
		String classpath = "-cp ";
		if(jars.size() > 0) { 
			classpath= classpath+jars.get(0);
			for(int i = 1; i < jars.size(); i++) {
				classpath=classpath+";"+jars.get(i);
			}
		}
		if(!isjavaorjavac.isEmpty() && jars.size() > 0) {
			classpath=classpath+";";
		}
		if(!isjavaorjavac.isEmpty()) {
			classpath=classpath+isjavaorjavac.getStarNorDot();
		}
		
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
		String str = "javac";
		if(isdeprecated) {
			str+=" -Xlint:deprecation";
		}
		if(isearlierversion) {
			str+=" --release "+javaversion;
		}
		str=str+Prettify(getClasspath(javac_star_nor_dot))+" "+main_class+".java";
		JOptionPane.showMessageDialog(null,str);
		return str;
	}
	
	public String java() {
		String command = "java"+Prettify(getClasspath(java_star_nor_dot))+Prettify(junitmain_class)+" "+main_class;
		JOptionPane.showMessageDialog(null,command);
		return command;
	}	
	
	/*
	** Run any command without showing msdos.
	** @classpath is current directory in MSDOS.
	*/
	public Process run(String command,String dir) {
		try {
			
String[] command2 =new String[3];
			command2[0] = "cmd.exe";
			command2[1] = "/c";
			command2[2] = command;
			Process process = Runtime.getRuntime().exec(command2,null,new File(dir));
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
	** Run any program without msdos
	*/
	public Process run(String[] arguments,String dir) {
		try {
			Process process = Runtime.getRuntime().exec(arguments,null,new File(dir));
			return process;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
																																