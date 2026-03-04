import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Path target = Paths.get("C:\\somefolder2\\junit-4.13.2.jar");
		try (InputStream in = Main.class.getResourceAsStream("/junit-4.13.2.jar")) {
	 		if (in == null) {
                			throw new FileNotFoundException("junit.jar not found inside resources");
            		}

            		Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        		}
        		catch (IOException ex) {
        			ex.printStackTrace();	
        		}
		File file = new File("C:\\somefolder2\\junit-4.13.2.jar");
		boolean result = file.exists();
		String output = "";
		if(result) {
			output = "junit-4.13.2.jar exists";
		}
		else {
			output="doesn't exist";
		}				
		JButton button = new JButton(output);
		frame.add(button);
		frame.pack();
		frame.setVisible(true);	
	}
}
