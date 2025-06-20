import java.util.List;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.io.InputStream;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
public class ExtractJavaFXJars {
	public Main main;
	public ExtractJavaFXJars(Main main) {	
		this.main = main;
		
		if(!isAlreadyExtracted()) {
			extractJars();
			unzipJars();
		}
		if(!strangeFilesExtracted()) {
			extractStrangeFiles();
		}
		if(!dllFilesExtracted()) {
			extractDLLFiles();
		}
		createStarter();
	}
	public String starter;
	public void createStarter() {
		try {
			String dir = main.getDirectory(main.fileName);	
			String normalmain=main.getFileName(main.fileName).replace(".java","");
			this.starter = normalmain+"two";
			PrintWriter printwriter = new PrintWriter(dir+this.starter+".java");
			printwriter.println("public class "+starter+" {");
			printwriter.println("\tpublic static void main(String[] args) {");
			printwriter.println("\t\t"+normalmain+".launch("+normalmain+".class,args);");
			printwriter.println("\t}");
			printwriter.println("}");
			printwriter.println();
			printwriter.close();
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public void unzipJars() {
		try {
			CommandLine commandline = new CommandLine();	
			String jarExe = System.getProperty("java.home")+"\\bin\\jar.exe";
			String dir = main.getDirectory(main.fileName);
			
			for(String jar:commandline.getJavaFX()) {
				JFrame extractframe = new JFrame();
				extractframe.setSize(800,600);
				JTextArea textarea = new JTextArea();
				JScrollPane jscrollpane = new JScrollPane(textarea);
				
				extractframe.add(jscrollpane);
				extractframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				extractframe.setVisible(true);
					
			        	// Command: jar -xvf myfile.jar
			            ProcessBuilder pb = new ProcessBuilder(
			                jarExe, "-xvf", jar
			            );
			
			            // Set working directory (where files will be extracted)
			            pb.directory(new File(dir));
			
			            // Merge error stream with output
			            pb.redirectErrorStream(true);
			
			            // Start the process
			            Process process = pb.start();
			
			            // Read output
			            BufferedReader reader = new BufferedReader(
			                new InputStreamReader(process.getInputStream())
			            );
			            String line;
			            while ((line = reader.readLine()) != null) {
			                textarea.append(line+"\n");
			            }
			
			            // Wait for the process to complete
			            int exitCode = process.waitFor();

  				if(exitCode == 0) {
  				            JOptionPane.showMessageDialog(null,"Extraction of "+jar+" was a success.");
  			            	extractframe.dispose();
  			            }
		            	else
		            		JOptionPane.showMessageDialog(null,"Could not extract "+jar+"!");
		            }
	            } catch (InterruptedException ex) {
	            	ex.printStackTrace();
            	} catch(IOException ex) {
            		ex.printStackTrace();
            	}
            }
	public void extractDLLFiles() {
		try {
			String dir=main.getDirectory(main.fileName);	
			for(String dll:getDLLFiles()) {
				URL url=ExtractJavaFXJars.class.getClassLoader().getResource(dll);	
				InputStream inputstream=url.openStream();
				Path outputpath=Paths.get(dir+dll);
						
				Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public List<String> getDLLFiles() {
		List<String> dlls = new ArrayList<String>();
		
		dlls.add("api-ms-win-core-console-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-console-l1-2-0.dll");
		
		dlls.add("api-ms-win-core-datetime-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-debug-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-errorhandling-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-file-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-file-l1-2-0.dll");
		
		dlls.add("api-ms-win-core-file-l2-1-0.dll");
		
		dlls.add("api-ms-win-core-handle-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-heap-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-interlocked-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-libraryloader-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-localization-l1-2-0.dll");
		
		dlls.add("api-ms-win-core-memory-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-namedpipe-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-processenvironment-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-processthreads-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-processthreads-l1-1-1.dll");
		
		dlls.add("api-ms-win-core-profile-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-rtlsupport-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-string-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-synch-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-synch-l1-2-0.dll");
		
		dlls.add("api-ms-win-core-sysinfo-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-timezone-l1-1-0.dll");
		
		dlls.add("api-ms-win-core-util-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-conio-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-convert-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-environment-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-filesystem-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-heap-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-locale-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-math-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-multibyte-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-private-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-process-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-runtime-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-stdio-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-string-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-time-l1-1-0.dll");
		
		dlls.add("api-ms-win-crt-utility-l1-1-0.dll");
		
		dlls.add("decora_sse.dll");
		
		dlls.add("fxplugins.dll");
		
		dlls.add("glass.dll");
		
		dlls.add("glib-lite.dll");
		
		dlls.add("gstreamer-lite.dll");
		
		dlls.add("javafx_font.dll");
		
		dlls.add("javafx_iio.dll");
		
		dlls.add("jfxmedia.dll");
		
		dlls.add("jfxwebkit.dll");
		
		dlls.add("msvcp140.dll");
		
		dlls.add("msvcp140_1.dll");
		
		dlls.add("msvcp140_2.dll");
		
		dlls.add("prism_common.dll");
		
		dlls.add("prism_d3d.dll");
		
		dlls.add("prism_sw.dll");
		
		dlls.add("ucrtbase.dll");
		
		dlls.add("vcruntime140.dll");
		
		dlls.add("vcruntime140_1.dll");
		
		
		return dlls;
	}
	public boolean dllFilesExtracted() {
		if(!fileExists("api-ms-win-core-console-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-console-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-datetime-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-debug-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-errorhandling-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-file-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-file-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-file-l2-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-handle-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-heap-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-interlocked-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-libraryloader-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-localization-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-memory-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-namedpipe-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-processenvironment-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-processthreads-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-processthreads-l1-1-1.dll"))
			return false;
		if(!fileExists("api-ms-win-core-profile-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-rtlsupport-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-string-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-synch-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-synch-l1-2-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-sysinfo-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-timezone-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-core-util-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-conio-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-convert-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-environment-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-filesystem-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-heap-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-locale-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-math-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-multibyte-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-private-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-process-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-runtime-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-stdio-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-string-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-time-l1-1-0.dll"))
			return false;
		if(!fileExists("api-ms-win-crt-utility-l1-1-0.dll"))
			return false;
		if(!fileExists("decora_sse.dll"))
			return false;
		if(!fileExists("fxplugins.dll"))
			return false;
		if(!fileExists("glass.dll"))
			return false;
		if(!fileExists("glib-lite.dll"))
			return false;
		if(!fileExists("gstreamer-lite.dll"))
			return false;
		if(!fileExists("javafx_font.dll"))
			return false;
		if(!fileExists("javafx_iio.dll"))
			return false;
		if(!fileExists("jfxmedia.dll"))
			return false;
		if(!fileExists("jfxwebkit.dll"))
			return false;
		if(!fileExists("msvcp140.dll"))
			return false;
		if(!fileExists("msvcp140_1.dll"))
			return false;
		if(!fileExists("msvcp140_2.dll"))
			return false;
		if(!fileExists("prism_common.dll"))
			return false;
		if(!fileExists("prism_d3d.dll"))
			return false;
		if(!fileExists("prism_sw.dll"))
			return false;
		if(!fileExists("ucrtbase.dll"))
			return false;
		if(!fileExists("vcruntime140.dll"))
			return false;
		if(!fileExists("vcruntime140_1.dll"))
			return false;
		return true;
	}
	public boolean fileExists(String dll) {
		String dir=main.getDirectory(main.fileName);		
		File file = new File(dir+dll);
		return file.exists();
	}
	public void extractStrangeFiles() {
		try {	
			String dir=main.getDirectory(main.fileName);	
			URL url=ExtractJavaFXJars.class.getClassLoader().getResource("javafx.properties");	
			InputStream inputstream=url.openStream();
			Path outputpath=Paths.get(dir+"javafx.properties");
			
			Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public boolean strangeFilesExtracted() {
		String dir=main.getDirectory(main.fileName);		
		File file = new File(dir+"javafx.properties");
		return file.exists();
	}
	public void extractJars() {	
		try {
			CommandLine commandline = new CommandLine();
			List<String> jars=commandline.getJavaFX();
			String dir=main.getDirectory(main.fileName);	
			for(String jar:jars) {
				URL url=ExtractJavaFXJars.class.getClassLoader().getResource(jar);	
				InputStream inputstream=url.openStream();
				Path outputpath=Paths.get(dir+jar);
						
				Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}		
	public boolean isAlreadyExtracted() {
		CommandLine commandline = new CommandLine();
		List<String> jars=commandline.getJavaFX();
		String dir=main.getDirectory(main.fileName);	
		for(String jar:jars) {
			File file = new File(dir+jar);
			if(!file.exists())
				return false;
		}
		return true;
	}
}