import java.io.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.regex.*;
public class Git {
	public boolean isVisible = false;
	public boolean isGitInstalled = false;
	public String root_directory;
	public String directory;
	public JFrame frame=new JFrame();
	public Git(String fileName) {
		if(isGitInstalled()) {
			isGitInstalled = true;
			if(isFileInsideGitRepository(fileName)) {
				isVisible = true;
				Change(fileName);
				setLayout();
			      	setListeners();
		      	}
	      	}
	}
	public boolean isFileInsideGitRepository(String fileName) {
		directory=fileName.replaceAll("[^\\\\]+\\.java","");
		CommandLine commandline = new CommandLine();
		Process process=commandline.run("git rev-parse --show-toplevel",directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String oneline=displayoutput.OneLine(process);
		return !(oneline.equals("line is null"));
	}
	public boolean isGitInstalled() {
		try {
			String[] command2 =new String[3];
			command2[0] = "cmd.exe";
			command2[1] = "/c";
			command2[2] = "git --version";
			Process process = Runtime.getRuntime().exec(command2,null);
		            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		            String output;
		            if ((output = reader.readLine()) != null) {
		            	return true;
		            } else {
		            	return false;
		            }
	            } catch(IOException ex) {
	            	ex.printStackTrace();
	            	return false;
            	}
            }

	public void Change(String fileName) {
		if(isGitInstalled) {
			if( new File(fileName).exists() ) {
				if(isFileInsideGitRepository(fileName)) {
					directory=fileName.replaceAll("[^\\\\]+\\.java","");
					CommandLine commandline = new CommandLine();
					Process process=commandline.run("git rev-parse --show-toplevel",directory);
					DisplayOutput displayoutput = new DisplayOutput();
					root_directory = displayoutput.OneLine(process);
				      	//JOptionPane.showMessageDialog(n,root_directory);
				      	if(!isVisible) {
						setLayout();
						setListeners();
			      		}
			      		frame.setTitle(whichBranchOpened());
			      	}	      			      		      			      	
		      	}
	      	}
      	}
      	
      	public JButton addtoall;
      	public JButton upload;
	public JTextField input = new JTextField();
	public JButton run;
	public JButton clear;
	public JButton switch2_branch;	
	public JButton reset;
	public void setLayout() {
		frame.setSize(500,100);
 // previously 400,100
		frame.setLocation(1000,0);
		frame.getContentPane().add(input,BorderLayout.CENTER);
		run = new JButton("run");
		frame.getContentPane().add(run,BorderLayout.SOUTH);
		
		JPanel east = new JPanel();
		
		switch2_branch = new JButton("switch branch");
		east.add(switch2_branch);
		reset = new JButton("reset");
		east.add(reset);
		upload = new JButton("upload");
		east.add(upload);
		addtoall = new JButton("add to all");
		east.add(addtoall);
		frame.getContentPane().add(east,BorderLayout.EAST);
		
		frame.setVisible(true);
	}
	public void setListeners() {
		addtoall.addActionListener( (ev) -> {
			String command = "eval $(git for-each-ref --shell --format=\"git switch %(refname:lstrip=3); git merge "+frame.getTitle()+"; git push;\" refs/remotes)";
			JOptionPane.showMessageDialog(null,command);
			git(command);
		});
		upload.addActionListener( (ev) -> {
			JFrame commit = new JFrame("Add commit message");
			JPanel panel = new JPanel();
			JTextField commitmessage = new JTextField(10);
			panel.add(commitmessage);
			JButton add = new JButton("add");
			ActionListener actionlistener = new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					commit.dispose();
					git("git add .; git commit -m \""+commitmessage.getText()+"\"; git push");
				}
			};
			add.addActionListener(actionlistener);
			commitmessage.addActionListener(actionlistener);
			panel.add(add);
			commit.getContentPane().add(panel);
			commit.pack();
			commit.setVisible(true);
		});
		reset.addActionListener((ev) -> {
			git("git reset --hard HEAD",root_directory);
		});
		run.addActionListener( (ev) -> {
			git(input.getText(),root_directory);
		});
		input.addActionListener((ev) -> {
			git(input.getText(),root_directory);
		});
		switch2_branch.addActionListener( (ev) -> {
			String mainbranch = getMainBranch();
			String whichbranch = whichBranchOpened();
			String whichfolderopened=whichFolderOpened();
			if(whichbranch.equals(whichfolderopened)) {
				//substring = "master";
				git("git switch "+mainbranch,root_directory);
				frame.setTitle(mainbranch);			
			}
			else {
				if(isBranch(whichfolderopened)) {
					git("git switch "+whichfolderopened,root_directory);
					frame.setTitle(whichfolderopened);
				}
				else
				{
					JFrame selectbranch = new JFrame();
					selectbranch.setSize(400,200);
					
					selectbranch.setTitle("Select Which Branch");
					JPanel selectpanel = new JPanel();
					JLabel selectlabel = new JLabel("Select branch:");
					selectpanel.add(selectlabel);
					JComboBox<String> combobox = new JComboBox<String>();
					for(String branch:getAllBranches()) {
						combobox.addItem(branch);
					}
					selectpanel.add(combobox);
					JButton openbranch = new JButton("open branch");
					openbranch.addActionListener( (ev2) -> {
						String selectedbranch = (String)combobox.getSelectedItem();
						
						git("git switch "+selectedbranch,root_directory);
						frame.setTitle(selectedbranch);
						selectbranch.dispose();
					});
					selectpanel.add(openbranch);

					selectbranch.add(selectpanel);
					selectbranch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					selectbranch.pack();
					
					selectbranch.setVisible(true);
				}
			}
		});
	}
	public String whichFolderOpened() {
		String substring=directory.replace(root_directory.replace("/","\\"),"");
		return substring.replace("\\","");
	}
	public boolean isBranch(String folder) {
		String[] branches=getAllBranches();
		for(int i = 0; i < branches.length; i++) {
			String branch = branches[i];
			if(folder.equals(branch)) {
				return true;
			}
		}
		return false;
	}
	public String[] getAllBranches() {
		CommandLine commandline = new CommandLine();
		Process process=commandline.run("git for-each-ref --format=\"%(refname:short)\" refs/heads/ refs/remotes/",root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String substring = displayoutput.Multiline(process);
		return substring.split("\\r?\\n|\\r");
	}
	public String getMainBranch() {
		CommandLine commandline = new CommandLine();
		Process process=commandline.run("git ls-remote --symref origin HEAD",root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String substring = displayoutput.Multiline(process);
		substring=substring.split("\\r?\\n|\\r")[0];
		substring=substring.replaceFirst("ref:","");
		substring=substring.trim();
		substring=substring.substring(0,substring.length()-4);
		String[] substrings=substring.split("/");
		substring=substrings[substrings.length-1];
		substring=substring.trim();
		return substring;
	}
	public void git(String command) {
		git(command,root_directory);
	}
	public void git(String command,String directory) {
		Thread thread = new Thread() {
			public void run() {
				CommandLine commandline = new CommandLine();
				// commandline.run("start C:\\\"Program Files\"\\Git\\bin\\bash.exe -i -c \'git status; exec bash\'",root_directory);
				commandline.run("\"C:\\Program Files\\Git\\git-bash.exe\" -c \'"+command+"; exec bash\'",directory);
			}
		};
		thread.start();
	}
	
	public void MSDOS() {
		CommandLine commandLine = new CommandLine();
		Process process=commandLine.run(input.getText(),root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		displayoutput.From(process);
	}
	
	public void gitwithoutthread(String command) {
		try {
			CommandLine commandline = new CommandLine();
			// commandline.run("start C:\\\"Program Files\"\\Git\\bin\\bash.exe -i -c \'git status; exec bash\'",root_directory);
			Process process=commandline.run("start C:\\\"Program Files\"\\Git\\bin\\bash.exe -i -c \'"+command+"; exec bash\'",directory);
			process.waitFor();
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	public String whichBranchOpened() {
		/*try {
			File file = new File(root_directory+"\\.git/HEAD");
			BufferedReader input = new BufferedReader(new FileReader(file));
			String line=input.readLine();
			String[] strings=line.split(" ");
			String[] strings2= strings[1].split("/");
			input.close();
			return strings2[strings2.length-1];
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return "FileNotFoundException";
		} catch(IOException ex) {
			ex.printStackTrace();
			return "IOException";
		}*/
		CommandLine commandline = new CommandLine();
		Process process=commandline.run("git rev-parse --abbrev-ref HEAD",root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String substring = displayoutput.OneLine(process);
		substring=substring.replaceAll("\\r?\\n|\\r","");
		substring=substring.trim();
		return substring;
	}
}