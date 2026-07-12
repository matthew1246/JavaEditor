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
			URL url=AI.class.getClassLoader().getResource("opencode.exe");	
			InputStream inputstream=url.openStream();
			String dir = "";
			try {
				java.net.URI jarUri = AI.class.getProtectionDomain().getCodeSource().getLocation().toURI();
				String jarPath = jarUri.getPath();
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
			Path outputpath=Paths.get(opencodeexe);
					
			Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException ex) {
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