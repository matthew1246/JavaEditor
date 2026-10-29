import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.InputStream;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.io.File;
import javax.swing.JButton;
public class AI {
	private Main main;
	private String opencodeexe;
	private static String aiFileName() {
		return Platform.isWindows() ? "opencode.exe" : "opencode";
	}
	public AI(Main main) {
		this.main=main;
		Extract();
		setLayout();
		setListeners();
	}
	private void Extract() {
		try {
			String dir = Platform.programFolder();
			if(dir == null || dir.equals(""))
				dir = System.getProperty("user.dir");
			opencodeexe = new File(dir,aiFileName()).getAbsolutePath();

			File exeFile = new File(opencodeexe);
			if(exeFile.exists()) {
				makeExecutable(exeFile);
				return;
			}

			String jarPath = "";
			try {
				java.net.URI jarUri = AI.class.getProtectionDomain().getCodeSource().getLocation().toURI();
				jarPath = new File(jarUri).getPath();
			} catch(Exception ex) {
				jarPath = "";
			}

			String fileName = aiFileName();
			String packagePath = "";
			Package pack = AI.class.getPackage();
			if(pack != null && pack.getName().length() > 0)
				packagePath = pack.getName().replace('.','/') + "/";
			String resPath = packagePath + fileName;

			if(jarPath.endsWith(".jar")) {
				try(java.util.jar.JarFile jar = new java.util.jar.JarFile(jarPath)) {
					java.util.jar.JarEntry entry = jar.getJarEntry(resPath);
					if(entry == null) {
						entry = jar.getJarEntry(fileName);
					}
					if(entry != null) {
						try(InputStream is = jar.getInputStream(entry)) {
							Path outputpath=Paths.get(opencodeexe);
							Files.copy(is,outputpath,StandardCopyOption.REPLACE_EXISTING);
						}
						makeExecutable(exeFile);
						return;
					}
				}
			}

			URL url=AI.class.getResource("/" + resPath);
			if(url == null) {
				url=AI.class.getClassLoader().getResource(resPath);
			}
			if(url == null && packagePath.length() > 0) {
				url = AI.class.getResource("/" + fileName);
			}
			if(url != null) {
				try(InputStream inputstream=url.openStream()) {
					Path outputpath=Paths.get(opencodeexe);
					Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
				}
				makeExecutable(exeFile);
				return;
			}
			System.err.println(fileName + " not found, skipping extraction.");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private static void makeExecutable(File file) {
		if(!Platform.isWindows()) {
			file.setExecutable(true,false);
		}
	}
	private JButton runAI;
	public void setLayout() {
		JFrame frame =new JFrame("AI");
		JPanel panel = new JPanel();
		runAI = new JButton("Launch AI");
		panel.add(runAI);
		panel.setPreferredSize(new java.awt.Dimension(200,100));
		frame.add(panel);
		frame.pack();
		frame.setMinimumSize(new java.awt.Dimension(200,100));
		frame.setLocation(980,675);
		frame.setVisible(true);
	}
	public void setListeners() {
		runAI.addActionListener((ev) -> {
			Launch();
		});
	}
	public void Launch() {
    		new Thread(() -> {
        			try {
            			File exe = new File(opencodeexe);

            			System.out.println("Launching: " + exe.getAbsolutePath());
		           		System.out.println("Exists: " + exe.exists());
		           		if(!exe.exists()) {
		           			System.err.println(aiFileName() + " not found.");
		           			return;
		           		}
		           		makeExecutable(exe);

		           		String command;
		           		if(Platform.isWindows()) {
		           			command = Platform.quote(exe.getAbsolutePath());
		           		}
		           		else {
		           			command = Platform.singleQuote(Platform.toShellPath(exe.getAbsolutePath()));
		           		}
				CommandLine commandLine = new CommandLine();
				commandLine.runWithMSDOS(command,main.getDirectory(main.fileName));
		        	} catch (Exception ex) {
		           		ex.printStackTrace();
		        	}
    		}).start();
	}
}