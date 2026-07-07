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
		File javahome=new File(System.getProperty("java.home"));
		File base=new File(javahome.getParent());
		File app = new File(base,"app");
		File extrafiles = new File(app,"extra-files");
		File fileopencodefolder = new File(extrafiles,"OpenCode");
		this.opencodeexe=  fileopencodefolder.getAbsolutePath()+"\\opencode.exe";
		setLayout();
		setListeners();
	}
	private JButton runAI;
	public void setLayout() {
		JFrame frame =new JFrame("AI");
		frame.setSize(200,200);
		JPanel panel = new JPanel();
		runAI = new JButton("AI");
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
		try {
			ProcessBuilder pb = new ProcessBuilder(opencodeexe);
			pb.directory(new File(main.getDirectory(main.fileName)));
			pb.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}