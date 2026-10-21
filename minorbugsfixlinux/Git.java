// import org.junit.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.regex.*;
public class Git {
	public static void main(String[] args) {
		SwingUtilities.invokeLater( () -> {
			Git git=new Git();
			System.out.println("Git is installed? "+git.isGitInstalled());
			git.ChangeBackgroundThread("/home/owner/javaeditor/minorbugsfixlinux/Git.java");
			git.ChangeEDT("/home/owner/javaeditor/minorbugsfixlinux/Git.java");
			
			/*git.ChangeBackgroundThread("C:\\Users\\Owner\\Documents\\javaeditor\\minorbugsfixlinux\\Git.java");
			git.ChangeEDT("C:\\Users\\Owner\\Documents\\javaeditor\\minorbugsfixlinux\\Git.java");
			*/
			//git.setLayout();
		});
	}
	public String root_directory;
	public String directory;
	public JFrame frame;
	public String gitbashdotexe = "";
	public boolean setWhereIsGitBashDotExe(String fileName) {
		String[] commonPaths = {
		    "C:\\Program Files\\Git\\git-bash.exe",
		    "C:\\Program Files (x86)\\Git\\git-bash.exe"
		};
		
		for(String path : commonPaths) {
			File file = new File(path);
		    	if (file.exists()) {
		        		gitbashdotexe=file.getAbsolutePath();
		    		return true;		
		    	}
	    	}
		JOptionPane.showMessageDialog(null,"Can't show Git features for Matthew Java Editor.");
		return false;		
	}
	public boolean setWhereIsGitBashDotExeBackgroundThread() {
		CommandLine commandline=new CommandLine();
		return commandline.doesCommandExist("bash");
    	}
    	public boolean setWhereIsGitBashDotExeEDT() {
		JOptionPane.showMessageDialog(null,"Can't show Git features for Matthew Java Editor.");
		return false;	
	}
	public boolean isFileInsideGitRepository(String fileName) {
		if(fileName.equals(""))
			return false;	
		directory=fileName.replaceAll("[^/]+\\.java","");
		CommandLine commandline = new CommandLine();
		Process process=commandline.run("git rev-parse --show-toplevel",directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String oneline=displayoutput.OneLine(process);
		return !(oneline.equals("line is null"));
	}
	public boolean isGitInstalled() {
		/*String[] command2 =new String[3];
		command2[0] = "cmd.exe";
		command2[1] = "/c";
		command2[2] = "git --version";
		Process process = Runtime.getRuntime().exec(command2,null);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            
	            String output;
	            return reader.readLine() != null;
	            */
	            String[] command2 =new String[2];
	            command2[0]="git";
	            command2[1]="--version";
	            
	            CommandLine commandline=new CommandLine();
	            return commandline.doesCommandExist("git --version");
            }

	public void Change(String fileName) {
		if(isGitInstalled()) {
			if(isGitBashDotExeFoundByBackgroundThread && isFileInsideGitRepository(fileName)) {
				setDirectoryAndRootDirectory(fileName);
				if(frame == null) {
					setLayout();
			      		setListeners();
		      		}
			      	frame.setTitle(whichBranchOpened());
		      	}
	      	}
      	}
      	public boolean isFileInsideGitRepository = false;
      	public boolean isGitBashDotExeFoundByBackgroundThread = false;
      	public String whichbranchopened="";
      	public void ChangeBackgroundThread(String fileName) {
     		if(isGitInstalled()) {
     			if(isFileInsideGitRepository(fileName)) {
     				isFileInsideGitRepository = true;
     				isGitBashDotExeFoundByBackgroundThread=setWhereIsGitBashDotExeBackgroundThread();
     				if(isGitBashDotExeFoundByBackgroundThread) {
	     				setDirectoryAndRootDirectory(fileName);
	     				whichbranchopened=whichBranchOpened();
     				}
     			}
     		}
     	}
      	public void ChangeEDT(String fileName) {
      		if(!isFileInsideGitRepository) return;
      		if(isGitBashDotExeFoundByBackgroundThread) {
      			if(frame == null) {
				setLayout();
		      		setListeners();
	      		}
		      	frame.setTitle(whichbranchopened);
	      	}
      	}
      	public void testsetDirectoryAndRootDirectory() {
      		setDirectoryAndRootDirectory("/home/owner/javaeditor/minorbugsfixlinux/Git.java");
      		/*org.junit.Assert.assertEquals("/home/owner/javaeditor",root_directory);
      		org.junit.Assert.assertEquals("/home/owner/javaeditor/minorbugsfixlinux/",directory);
      		*/
      	}
      	public void setDirectoryAndRootDirectory(String fileName) {
		directory=Main.getDirectory(fileName);
		CommandLine commandline = new CommandLine();
		Process process=commandline.run("git rev-parse --show-toplevel",directory);
		DisplayOutput displayoutput = new DisplayOutput();
		root_directory = displayoutput.OneLine(process);
      	}
      	
      	public JButton seeallchanges;
	public JButton everythingbutthekitchensink;
      	public JButton addtoall;
      	public JButton upload;
	public JTextField input = new JTextField();
	public JButton run;
	public JButton clear;
	public JButton switch2_branch;
	
	public JButton reset;
public void setLayout() {
		frame=new JFrame();
		JPanel panel=new JPanel();
		panel.setLayout(new MatthewLayout());
		input.setText("git status");
		panel.add(input,new XYWidthHeight(0,0,2,1));
		
		switch2_branch = new JButton("switch branch");
		panel.add(switch2_branch,new XYWidthHeight(1,0,4,1));
		reset = new JButton("reset");
		panel.add(reset,new XYWidthHeight(2,0,1,1));
		upload = new JButton("upload");
		panel.add(upload,new XYWidthHeight(3,0,2,1));
		addtoall = new JButton("add to all");
		panel.add(addtoall,new XYWidthHeight(4,0,4,1));
		everythingbutthekitchensink = new JButton("*");
		panel.add(everythingbutthekitchensink,new XYWidthHeight(5,0,1,1));
		seeallchanges=new JButton("\u2315");
		panel.add(seeallchanges,new XYWidthHeight(6,0,1,1));
		
		run = new JButton("run");
		panel.add(run,new XYWidthHeight(0,1,15,1));
		
		/*
		** The panel is put inside a holder instead of being added straight to
		** the content pane. A panel that sits straight in the content pane is
		** given the top inset of the window as extra padding, so that its
		** first row does not sit flush against the title bar. The window
		** manager only tells Java how big that inset is after the window is
		** on the screen, so the padding is there for the first window of the
		** program and missing for every window after that, and the same
		** window comes out a different height depending on which one it is.
		** The holder is not the content pane, so no padding is added and the
		** height is the same every time. The frame already keeps the border
		** of the window away from the panel, so nothing is lost by it.
		*/
		JPanel holder = new JPanel(new BorderLayout());
		holder.add(panel,BorderLayout.CENTER);
		frame.getContentPane().add(holder,BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(980,0);
		frame.pack();
		frame.setVisible(true);
		/*
		** The Linux window manager moves the window when it first appears and
		** drops the position that was asked for before that. Setting it again
		** a moment later, on the event dispatch thread, is what sticks: two
		** goes through the same move it makes itself, so the second one ends
		** up being the position that stays. On Windows the frame is already
		** where it was asked to be, and setting the same point again does
		** nothing.
		**
		** The size is put right as well, because the window manager can also
		** change that, and the size of the border of the window is only known
		** once the window is on the screen.
		*/
		javax.swing.Timer fixlocation = new javax.swing.Timer(500, (ev) -> {
			frame.setLocation(980,0);
			fixFrameSize();
		});
		fixlocation.setRepeats(false);
		fixlocation.start();
		javax.swing.Timer fixlocation2 = new javax.swing.Timer(900, (ev) -> {
			frame.setLocation(980,0);
			fixFrameSize();
		});
		fixlocation2.setRepeats(false);
		fixlocation2.start();
	}
	/*
	** Puts the window to the size that the panel inside it asks for, plus the
	** border of the window. The border is only known once the window is on the
	** screen, so the window is packed before that and put right afterwards.
	*/
	public void fixFrameSize() {
		if(frame == null) return;
		java.awt.Insets insets = frame.getInsets();
		Dimension preferred = frame.getContentPane().getPreferredSize();
		if((preferred.width <= 0) || (preferred.height <= 0)) return;
		Dimension wanted = new Dimension(preferred.width+insets.left+insets.right,
											preferred.height+insets.top+insets.bottom);
		if(!frame.getSize().equals(wanted)) {
			frame.setSize(wanted);
		}
	}
	public void setListeners() {
		seeallchanges.addActionListener((ev) -> {
			SeeAllGitChanges seeallgitchanges=new SeeAllGitChanges(root_directory);
		});
		everythingbutthekitchensink.addActionListener(ev -> {
			String command = "eval $(";
			// git for-each-ref --shell --format=\"git switch %(refname:lstrip=3); git merge "+frame.getTitle()+"; git push;\" refs/remotes)";
			for(String branch:getAllBranches()) {
				command=command+"git for-each-ref --shell --format='git switch %(refname:lstrip=3); git merge "+branch+"; git push;' refs/remotes; ";
			}
			command+=")";
			
			JOptionPane.showMessageDialog(null,command);
			git(command);
		});
		addtoall.addActionListener( (ev) -> {
			String branch = frame.getTitle();
			String command = "eval $(git for-each-ref --shell --format='git checkout %(refname:lstrip=3); git rebase "+branch+"; git push;' refs/remotes); git checkout "+branch;
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
					String msg = commitmessage.getText()
						.replace("\\", "\\\\")
						.replace("\"", "\\\"")
						.replace("$", "\\$");
					git("git add .; git commit -m \""+msg+"\"; git push");
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
			git("git reset --hard HEAD; git clean -fd",root_directory);
		});
		run.addActionListener( (ev) -> {
			git(input.getText(),root_directory);
		});
		input.addActionListener((ev) -> {
			git(input.getText(),root_directory);
		});
		switch2_branch.addActionListener( (ev) -> {
			SwingUtilities.invokeLater( () -> {
				String mainbranch = getMainBranch();
				String whichbranch = whichBranchOpened();
				String whichfolderopened=whichFolderOpened();
				if(whichbranch.equals(whichfolderopened)) {
					gitWaitUntilFinish("git switch "+mainbranch,root_directory);
				}
				else {
					if(isBranch(whichfolderopened)) {
						gitWaitUntilFinish("git switch "+whichfolderopened,root_directory);
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
							
							gitWaitUntilFinish("git switch "+selectedbranch,root_directory);
							selectbranch.dispose();
						});
						selectpanel.add(openbranch);
	
						selectbranch.add(selectpanel);
						selectbranch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						selectbranch.setLocationRelativeTo(frame);
						selectbranch.pack();
						selectbranch.setVisible(true);
						javax.swing.Timer fixSelectSize = new javax.swing.Timer(500, (ev3) -> {
							java.awt.Insets insets = selectbranch.getInsets();
							Dimension preferred = selectbranch.getContentPane().getPreferredSize();
							if((preferred.width > 0) && (preferred.height > 0)) {
								selectbranch.setSize(new Dimension(preferred.width+insets.left+insets.right,
																preferred.height+insets.top+insets.bottom));
							}
						});
						fixSelectSize.setRepeats(false);
						fixSelectSize.start();
					}
				}
			});
		});
	}
	public void gitWaitUntilFinish(String command) {
		git(command,root_directory);
	}
	public void gitWaitUntilFinish(String command,String directory) {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(gitbashdotexe);
			processBuilder.environment().put("PROMPT_COMMAND", command + "; unset PROMPT_COMMAND");
			processBuilder.directory(new File(directory));
			Process process = processBuilder.start();
			process.waitFor();
			getBranchAndSetTitle();
		} catch (InterruptedException ex) {
			ex.printStackTrace();				
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}	
	public void getBranchAndSetTitle() {
		//try {
			//thread.wait();
			//JOptionPane.showMessageDialog(null,"hello");
			//SwingUtilities.invokeLater( () -> {
				String branch=whichBranchOpened();
				
				frame.setTitle(branch);
			//});
		/*}
		catch(InterruptedException ex) {
			ex.printStackTrace();
		}*/
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
		String[] allbranches= substring.split("\\r?\\n|\\r");
		java.util.List<String> branches2=new java.util.ArrayList<String>();
		for(String branch:allbranches) {
			if(!branch.contains("/") && !branch.equals("origin"))
				branches2.add(branch);
		}
		return branches2.toArray(new String[branches2.size()]);
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
				try {
					/*File script = writeGitScript(command,directory);
					String os = System.getProperty("os.name").toLowerCase();
					if(os.contains("win")) {
						openWindowsBashWindow(script);
					}
					else if(isWSL()) {
						openWSLBashWindow(script);
					}
					else {
						openLinuxBashWindow(script);
					}
					*/
					ProcessBuilder processbuilder=new ProcessBuilder("bash","-c",command);
					processbuilder.redirectErrorStream(true);
					if(directory != null)
						processbuilder.directory(new File(directory));
					
					Process process=processbuilder.start();
					DisplayOutput displayoutput = new DisplayOutput();
					String substring = displayoutput.Multiline(process);
					System.out.println(substring);
				} catch(IOException ex) {
					ex.printStackTrace();
					showGitWindowError("Couldn't open a bash window: "+ex.getMessage());
				}
			}
		};
		thread.start();
	}
	public File writeGitScript(String command,String directory) throws IOException {
		File script = File.createTempFile("gitcmd",".sh");
		script.deleteOnExit();
		PrintWriter out = new PrintWriter(new FileWriter(script));
		out.print("cd '"+directory.replace('\\','/').replace("'","'\\''")+"'\n");
		out.print(command+"\n");
		out.print("exec bash\n");
		out.close();
		return script;
	}
	public String findWindowsBash() {
		java.util.List<String> candidates = new java.util.ArrayList<String>();
		if(gitbashdotexe.length() > 0) {
			File parent = new File(gitbashdotexe).getParentFile();
			if(parent != null)
				candidates.add(new File(parent,"bin\\bash.exe").getAbsolutePath());
		}
		candidates.add("C:\\Program Files\\Git\\bin\\bash.exe");
		candidates.add("C:\\Program Files (x86)\\Git\\bin\\bash.exe");
		for(String path:candidates) {
			if(new File(path).exists())
				return path;
		}
		return "bash";
	}
	public void openWindowsBashWindow(File script) throws IOException {
		String bash = findWindowsBash();
		String scriptpath = script.getAbsolutePath().replace('\\','/');
		String[] argv = new String[] {
			"cmd.exe","/c","start","\"Git Bash\"","\""+bash+"\"","\""+scriptpath+"\""
		};
		ProcessBuilder processBuilder = new ProcessBuilder(argv);
		processBuilder.start();
	}
	public boolean isWSL() {
		String os = System.getProperty("os.name").toLowerCase();
		if(!os.contains("linux")) return false;
		try {
			java.io.File proc = new java.io.File("/proc/version");
			if(proc.exists()) {
				java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(proc));
				String line;
				while((line = br.readLine()) != null) {
					String l = line.toLowerCase();
					if(l.contains("microsoft") || l.contains("wsl")) {
						br.close();
						return true;
					}
				}
				br.close();
			}
			String uname = System.getProperty("os.version","").toLowerCase();
			if(uname.contains("microsoft")) return true;
			try {
				byte[] b = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("/proc/version"));
				String s = new String(b).toLowerCase();
				if(s.contains("microsoft")||s.contains("wsl")) return true;
			} catch(Exception ex2) {}
		} catch(Exception ex) {}
		return false;
	}
	public void openWSLBashWindow(File script) throws IOException {
		String scriptpath = script.getAbsolutePath().replace('\\','/');
		String wslScript = "/mnt/"+scriptpath.replaceFirst("^([A-Za-z]):","$1".toLowerCase());
		// fallback if drive form weird
		if(scriptpath.startsWith("/")) wslScript = scriptpath;
		ProcessBuilder pb = new ProcessBuilder(
			"cmd.exe","/c","start","\"WSL Bash\"","wsl.exe","bash",scriptpath.replace('\\','/')
		);
		try { pb.start(); return; } catch(IOException ex) {}
		// try without spaces in title
		new ProcessBuilder("cmd.exe","/c","start","wsl.exe","bash",scriptpath.replace('\\','/')).start();
	}
	public void openLinuxBashWindow(File script) {
		String scriptpath = script.getAbsolutePath();
		String[][] attempts = {
			{"x-terminal-emulator","-e","bash",scriptpath},
			{"gnome-terminal","--","bash",scriptpath},
			{"konsole","-e","bash",scriptpath},
			{"xterm","-e","bash",scriptpath},
			{"xfce4-terminal","-x","bash",scriptpath},
			{"mate-terminal","--","bash",scriptpath},
			{"lxterminal","-e","bash",scriptpath},
			{"tilix","-e","bash",scriptpath},
			{"alacritty","-e","bash",scriptpath},
			{"kitty","bash",scriptpath},
			{"urxvt","-e","bash",scriptpath},
			{"st","-e","bash",scriptpath}
		};
		String errors = "";
		for(String[] argv : attempts) {
			try {
				Process process = new ProcessBuilder(argv).start();
				try {
					if(!process.waitFor(700,java.util.concurrent.TimeUnit.MILLISECONDS)) {
						System.err.println("bash window opened by "+argv[0]);
						return;
					}
					if(process.exitValue() == 0) {
						System.err.println("bash window opened by "+argv[0]);
						return;
					}
					errors = errors + argv[0]+" exited with "+process.exitValue()+"\n";
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
					return;
				}
			} catch(IOException ex) {
				errors = errors + argv[0]+": "+ex.getMessage()+"\n";
			}
		}
		showGitWindowError("Couldn't open a bash window. Tried every terminal emulator:\n"+errors);
	}
	public void showGitWindowError(String message) {
		System.err.println(message);
		if(!java.awt.GraphicsEnvironment.isHeadless()) {
			JOptionPane.showMessageDialog(null,message);
		}
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
	public void testwhichBranchOpened() {
		testsetDirectoryAndRootDirectory();
		// Assert.assertEquals("main",whichBranchOpened());
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
		}
*/
		CommandLine commandline = new CommandLine();
		Process process=commandline.run("git rev-parse --abbrev-ref HEAD",root_directory);
		DisplayOutput displayoutput = new DisplayOutput();
		String substring = displayoutput.OneLine(process);
		substring=substring.replaceAll("\\r?\\n|\\r","");
		substring=substring.trim();
		return substring;
	}
}