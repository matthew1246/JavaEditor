import java.io.*;
import javax.swing.JOptionPane;
public class DisplayOutput {
	/*
	** This gets all multiline output from MSDOS command
	*/
	public void From(Process process) {
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
				return "No output."; // No output
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return "IOException";
		}
	}
	public String OneLine(Process process) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = input.readLine();
			if(line == null) 
				return "line is null";
			return line;
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return "exception has occuried";
		}
	}
}
