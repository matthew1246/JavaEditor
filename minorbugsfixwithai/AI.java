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
	public AI(Main main) {
		this.main=main;
		Extract();
		setLayout();
		setListeners();
	}
	private void Extract() {
		try {
			String dir = "";
			String jarPath = "";
			try {
				java.net.URI jarUri = AI.class.getProtectionDomain().getCodeSource().getLocation().toURI();
				jarPath = jarUri.getPath();
				if(jarPath.startsWith("/"))
					jarPath=jarPath.substring(1,jarPath.length());
				File jarFile = new File(jarPath);
				dir = jarFile.getParent();
			} catch(Exception ex) {
				dir = System.getProperty("user.dir");
			}
			if(dir == null)
				dir = System.getProperty("user.dir");
			if(!dir.endsWith("\\"))
				dir=dir+"\\";
			opencodeexe=dir+"opencode.exe";

			File exeFile = new File(opencodeexe);
			if(exeFile.exists())
				return;

			String resPath = AI.class.getPackage().getName().replace('.','/') + "/opencode.exe";

			try(java.util.jar.JarFile jar = new java.util.jar.JarFile(jarPath)) {
				java.util.jar.JarEntry entry = jar.getJarEntry(resPath);
				if(entry == null) {
					entry = jar.getJarEntry("opencode.exe");
				}
				if(entry != null) {
					try(InputStream is = jar.getInputStream(entry)) {
						Path outputpath=Paths.get(opencodeexe);
						Files.copy(is,outputpath,StandardCopyOption.REPLACE_EXISTING);
					}
					return;
				}
			}

			URL url=AI.class.getResource("/" + resPath);
			if(url == null) {
				url=AI.class.getClassLoader().getResource(resPath);
			}
			if(url == null) {
				url = AI.class.getResource("/opencode.exe");
			}
			if(url != null) {
				try(InputStream inputstream=url.openStream()) {
					Path outputpath=Paths.get(opencodeexe);
					Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
				}
				return;
			}
			System.err.println("opencode.exe not found, skipping extraction.");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private JButton runAI;
	public void setLayout() {
		JFrame frame =new JFrame("AI");
		frame.setSize(200,100);
		frame.setLocation(980,675);
		JPanel panel = new JPanel();
		runAI = new JButton("Launch AI");
		panel.add(runAI);
		frame.add(panel);
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
		
		            	/*ProcessBuilder pb = new ProcessBuilder(exe.getAbsolutePath());
		           	 	pb.directory(new File(main.getDirectory(main.fileName)));
		            	pb.redirectErrorStream(true);
		
		            	Process process = pb.start();
		
		            	try (BufferedReader reader = new BufferedReader(
		                    		new InputStreamReader(process.getInputStream()))) {
		
		                		String line;
		                		while ((line = reader.readLine()) != null) {
		                    			System.out.println(line);
		                		}
		            	}
				*/
				CommandLine commandLine = new CommandLine();
				commandLine.runWithMSDOS(exe.getAbsolutePath(),main.getDirectory(main.fileName));
		        	} catch (Exception ex) {
		           		ex.printStackTrace();
		        	}
    		}).start();
	}
}