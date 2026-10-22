import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
** The parts of the editor that are different on Windows and on Linux are all
** put in this class, so that the rest of the code does not have to know which
** one it is running on.
**
** The class only uses java.io and java.util, so it compiles on every java
** version the editor can compile for when it makes a jar.
*/
public class Platform {
	private static final String osname = System.getProperty("os.name","").toLowerCase();
	private static Boolean iswindows = null;
	private static Boolean hasbash = null;

	public static boolean isWindows() {
		if(iswindows == null) {
			iswindows = Boolean.valueOf(osname.contains("win"));
		}
		return iswindows.booleanValue();
	}
	public static boolean isMac() {
		return !isWindows() && (osname.contains("mac") || osname.contains("darwin"));
	}
	public static boolean isLinux() {
		return !isWindows() && !isMac();
	}
	/*
	** The character that separates the parts of a path.
	*/
	public static String separator() {
		return isWindows() ? "\\" : "/";
	}
	/*
	** The directory part of a path, with the separator at the end of it.
	** A path that has no directory in it gives an empty string.
	*/
	public static String directoryOf(String path) {
		if(path == null) {
			return "";
		}
		int index = Math.max(path.lastIndexOf('/'),path.lastIndexOf('\\'));
		if(index < 0) {
			return "";
		}
		return path.substring(0,index+1);
	}
	/*
	** The file name part of a path.
	*/
	public static String fileNameOf(String path) {
		if(path == null) {
			return "";
		}
		int index = Math.max(path.lastIndexOf('/'),path.lastIndexOf('\\'));
		if(index < 0) {
			return path;
		}
		return path.substring(index+1);
	}
	/*
	** A package name like a.b.c becomes the folders a/b/c.
	*/
	public static String packageToPath(String packagename) {
		if(packagename == null) {
			return "";
		}
		return packagename.replace(".",separator());
	}
	/*
	** A path that is given to a shell has to have its backslashes turned into
	** forward slashes on Linux, because a backslash is the escape character
	** of the shell there.
	*/
	public static String toShellPath(String path) {
		if(path == null) {
			return "";
		}
		if(isWindows()) {
			return path;
		}
		return path.replace('\\','/');
	}
	/*
	** Puts a path in quotes so that a shell keeps it as one word.
	*/
	public static String quote(String path) {
		if(path == null) {
			return "\"\"";
		}
		return "\""+path.replace("\"","\\\"")+"\"";
	}
	/*
	** Puts a path in single quotes for a linux shell. A single quote inside
	** the path is closed, escaped and opened again so that it stays part of
	** the word.
	*/
	public static String singleQuote(String path) {
		if(path == null) {
			return "''";
		}
		return "'"+path.replace("'","'\\''")+"'";
	}
	/*
	** Checks whether a directory is the root of a drive on Windows or the
	** root of the file system on Linux. Only an administrator can write in
	** those folders.
	*/
	public static boolean isRootDirectory(String dir) {
		if(dir == null) {
			return false;
		}
		String withoutSeparators = dir.replace("\\","").replace("/","");
		if(withoutSeparators.matches("[a-zA-Z]:")) {
			return true;
		}
		return withoutSeparators.equals("");
	}
	/*
	** The classpath that means the folder of the program and every jar in
	** that folder. The star has to come after the separator on Linux, because
	** the shell would otherwise turn it into the names of the files in the
	** folder.
	*/
	public static String classpathWildcard() {
		return isWindows() ? ".*" : ".:*";
	}
	/*
	** The path of a tool that comes with the java installation, so javac,
	** jar and java.
	*/
	public static String javaTool(String name) {
		String home = System.getProperty("java.home");
		String exe = isWindows() ? ".exe" : "";
		return home + File.separator + "bin" + File.separator + name + exe;
	}
	/*
	** The shell that runs the commands of the editor. Windows has cmd.exe and
	** the other systems have a shell like bash.
	*/
	public static String[] shellCommand(String command) {
		if(isWindows()) {
			return new String[] {"cmd.exe","/c",command};
		}
		return new String[] {shellName(),"-c",command};
	}
	/*
	** The name of the shell that runs the commands of the editor.
	*/
	public static String shellName() {
		if(isWindows()) {
			return "cmd.exe";
		}
		if(hasbash == null) {
			hasbash = Boolean.valueOf(commandExists("bash"));
		}
		if(hasbash.booleanValue()) {
			return "bash";
		}
		return "sh";
	}
	/*
	** Checks whether a command can be run, by asking it for its version.
	** The command is run without a shell, so that the check does not need
	** the shell that is being looked for.
	*/
	public static boolean commandExists(String command) {
		try {
			ProcessBuilder processbuilder = new ProcessBuilder(splitCommand(command+" --version"));
			processbuilder.redirectErrorStream(true);
			Process process = processbuilder.start();
			java.io.BufferedReader reader = new java.io.BufferedReader(
				new java.io.InputStreamReader(process.getInputStream()));
			String line = reader.readLine();
			reader.close();
			return line != null;
		} catch(IOException ex) {
			return false;
		}
	}
	/*
	** Splits a command into the words of the command. A word that is in
	** quotes stays one word, even when it has a space in it.
	*/
	public static java.util.List<String> splitCommand(String command) {
		java.util.List<String> arguments = new java.util.ArrayList<String>();
		int index = 0;
		while(index < command.length()) {
			while(index < command.length() && command.charAt(index) == ' ') {
				index++;
			}
			if(index >= command.length()) {
				break;
			}
			StringBuilder word = new StringBuilder();
			boolean inQuotes = false;
			while(index < command.length()) {
				char character = command.charAt(index);
				if(character == '"') {
					inQuotes = !inQuotes;
					index++;
					continue;
				}
				if(character == ' ' && !inQuotes) {
					break;
				}
				word.append(character);
				index++;
			}
			arguments.add(word.toString());
		}
		return arguments;
	}
	/*
	** Opens a window with a console in it that runs a command and stays open
	** after the command has finished.
	** Returns false when no console could be opened.
	*/
	public static boolean openConsole(String command,String dir) {
		if(isWindows()) {
			try {
				ProcessBuilder processbuilder = new ProcessBuilder("cmd.exe","/c","start","cmd","/k",command);
				if(dir != null) {
					processbuilder.directory(new File(dir));
				}
				processbuilder.start();
				return true;
			} catch(IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}
		String shell = shellName();
		String[][] attempts = {
			{"x-terminal-emulator","-e",shell,"-c",command+"; exec "+shell},
			{"gnome-terminal","--",shell,"-c",command+"; exec "+shell},
			{"konsole","-e",shell,"-c",command+"; exec "+shell},
			{"xterm","-e",shell,"-c",command+"; exec "+shell},
			{"xfce4-terminal","-x",shell,"-c",command+"; exec "+shell},
			{"mate-terminal","--",shell,"-c",command+"; exec "+shell},
			{"lxterminal","-e",shell,"-c",command+"; exec "+shell},
			{"tilix","-e",shell,"-c",command+"; exec "+shell},
			{"alacritty","-e",shell,"-c",command+"; exec "+shell},
			{"kitty",shell,"-c",command+"; exec "+shell},
			{"urxvt","-e",shell,"-c",command+"; exec "+shell},
			{"st","-e",shell,"-c",command+"; exec "+shell}
		};
		for(int i = 0; i < attempts.length; i++) {
			try {
				ProcessBuilder processbuilder = new ProcessBuilder(attempts[i]);
				if(dir != null) {
					processbuilder.directory(new File(dir));
				}
				processbuilder.start();
				return true;
			} catch(IOException ex) {
				// That terminal is not installed, so the next one is tried.
			}
		}
		return false;
	}
	/*
	** Opens a folder or a file in the file manager of the system.
	*/
	public static boolean openInFileManager(String path,String dir) {
		String command;
		if(isWindows()) {
			command = "explorer \""+path+"\"";
		}
		else if(isMac()) {
			command = "open \""+path+"\"";
		}
		else {
			command = "xdg-open \""+path+"\"";
		}
		try {
			ProcessBuilder processbuilder = new ProcessBuilder(shellCommand(command));
			processbuilder.redirectErrorStream(true);
			if(dir != null) {
				processbuilder.directory(new File(dir));
			}
			processbuilder.start();
			return true;
		} catch(IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	/*
	** The extension of a script of the system.
	*/
	public static String scriptExtension() {
		return isWindows() ? ".bat" : ".sh";
	}
	/*
	** The name of the script that closes the program that has a jar open and
	** then makes a new jar of it.
	*/
	public static String closeAndCreateJarScriptName() {
		return isWindows() ? "closeandcreatejar.bat" : "closeandcreatejar.sh";
	}
	/*
	** The name of the script that closes the program that has a jar open and
	** then gives the jar another name.
	*/
	public static String closeAndRenameJarScriptName() {
		return isWindows() ? "closeandrenamejar.bat" : "closeandrenamejar.sh";
	}
	/*
	** The lines that go at the top of the script that replaces a jar.
	** They change to the folder of the jar, stop the program that has the jar
	** open and delete the old jars.
	*/
	public static List<String> scriptHeader(String dir) {
		List<String> lines = new ArrayList<String>();
		if(isWindows()) {
			lines.add("cd "+dir);
			lines.add("START /B /WAIT taskkill /F /im java.exe");
			lines.add("START /B /WAIT taskkill /F /im javaw.exe");
		}
		else {
			lines.add("#!/bin/sh");
			lines.add("cd "+singleQuote(toShellPath(dir)));
			lines.add("pkill -f 'java -jar' 2>/dev/null");
			lines.add("true");
		}
		return lines;
	}
	/*
	** The command that runs a script that was written by scriptHeader.
	** On Windows the script has to be run with administrator rights, because
	** the jar can be in a folder that only an administrator can write to.
	** Linux has no administrator rights in the same way, so the script is
	** simply run.
	*/
	public static String scriptRunCommand(String scriptpath) {
		if(isWindows()) {
			return "powershell -Command \"Start-Process powershell -Verb runAs -ArgumentList '-Command cmd /c \""
				+scriptpath+"\"'\"";
		}
		return shellName()+" "+singleQuote(toShellPath(scriptpath));
	}
	/*
	** The command that runs a script and keeps the console open afterwards.
	*/
	public static String scriptConsoleCommand(String scriptpath) {
		if(isWindows()) {
			return scriptRunCommand(scriptpath);
		}
		return shellName()+" "+singleQuote(toShellPath(scriptpath))+"; exec "+shellName();
	}
	/*
	** The command that sets the java home and the path for maven and then
	** runs the rest of the command.
	*/
	public static String withMavenPath(String mavenfolder,String rest) {
		String javahome = System.getProperty("java.home");
		if(isWindows()) {
			return "set \"JAVA_HOME="+javahome+"\" && "
				+"set \"PATH="+javahome+"\\bin;"+mavenfolder+"\\bin;%PATH%\" && "
				+rest;
		}
		return "export JAVA_HOME="+singleQuote(javahome)+" && "
			+"export PATH="+singleQuote(javahome+"/bin:"+mavenfolder+"/bin")+":\"$PATH\" && "
			+rest;
	}
	/*
	** The name of the maven command. Windows has mvn.cmd and the other
	** systems have mvn.
	*/
	public static String mavenCommand() {
		return isWindows() ? "mvn.cmd" : "mvn";
	}
}
