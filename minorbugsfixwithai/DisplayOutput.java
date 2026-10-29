import java.io.*;
import javax.swing.JOptionPane;
public class DisplayOutput {
	/*
	** This gets all the output of a command as one string. The error output
	** of the command is mixed into the output of the command, because the
	** commands are started with the error output redirected into the output.
	** An empty string is given when the command had no output at all.
	*/
	public String All(Process process) {
		if(process == null) {
			return "";
		}
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder lines = new StringBuilder();
			String line = input.readLine();
			while(line != null) {
				if(lines.length() > 0) {
					lines.append("\n");
				}
				lines.append(line);
				line = input.readLine();
			}
			return lines.toString();
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return "";
		}
	}
	/*
	** This gets all multiline output from MSDOS command
	*/
	public void From(Process process) {		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String lines = input.readLine();
			if(lines != null) {
				while(true) {
					String line = input.readLine();
					if(line == null)
						break;
					lines = lines+"\n"+line;
				}
				JOptionPane.showMessageDialog(null,lines);
			}
			else {
				JOptionPane.showMessageDialog(null,"No output");
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	/*
	** This displays all multiline output from MSDOS command.
	*/
	public String Multiline(Process process) {
		if(process == null) {
			return "No output.";
		}
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String lines = input.readLine();
			if(lines != null) {
				while(true) {
					String line = input.readLine();
					if(line == null)
						break;
					lines = lines+"\n"+line;
				}
				return lines;
			}
			else {
				BufferedReader err =
    new BufferedReader(new InputStreamReader(process.getErrorStream()));

String error;
while ((error = err.readLine()) != null) {
    System.out.println("ERROR: " + error);
}
				return "No output."; // No output
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return "IOException";
		}
	}
	public String OneLine(Process process) {
		if(process == null) {
			return "line is null";
		}
try {
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = input.readLine();
			if(line == null) 
				return "line is null";
			return line;
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return "line is null";
		}
	}
}
