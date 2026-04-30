import javax.swing.SwingWorker;
import java.util.List;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.io.InputStream;
import java.nio.file.Path;
import java.io.IOException;
import javax.swing.SwingUtilities;
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
	public boolean makejarwithpackagesandjavafx = false;
	public String dir;
	public Main main;
	public Packager packager;
	public ExtractJavaFXJars(Main main) {	
		this.main = main;
		packager = new Packager(main);
		if(!packager.containsPackage() || !packager.isInRightFolders()) {
			dir=main.getDirectory(main.fileName);
		}
		else { // packager.isInRightFolders() == true
			dir=packager.classpath;
		}
		if(!dir.endsWith("\\"))
			dir=dir+"\\";
		process();
	}
	/**
	** This is for making a Jar for making packages!
	*/
	public ExtractJavaFXJars(Main main,boolean makejarwithpackagesandjavafx) {
		this.makejarwithpackagesandjavafx = makejarwithpackagesandjavafx;
		this.main = main;
		this.packager = new Packager(main);
		if(!makejarwithpackagesandjavafx) {
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				dir=main.getDirectory(main.fileName);
			}
			else { // packager.isInRightFolders() == true
				dir=packager.classpath;
			}
		}
		else { // makejarwithpackagesandjavafx == true
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				dir=main.getDirectory(main.fileName);
				makejarwithpackagesandjavafx=false;
			}
			else { // packager.isInRightFolders() == true
				dir=packager.classpath;
				if(!dir.endsWith("\\"))
					dir=dir+"\\";
				dir=dir+"jars";	
			}	
		}
		if(!dir.endsWith("\\"))
			dir=dir+"\\";
		
		if(!dir.endsWith("\\"))
			dir=dir+"\\";
		this.dir = dir;
		process();
	}
	public void process() {
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
		delete_moduleinfo();
		createStarter();
		
	}
	public void delete_moduleinfo() {
		File file = new File(dir+"module-info.class");
		System.out.println("ExtractJavaFXJars.delete_moduleinfo() is being executed!");
		if(file.exists()) {
			System.out.println("module-info.class exists");
			file.delete();
			System.out.println("module-info.class deleted!");
		}
	}
	public String starter;
	public void createStarter() {
		try {
			String normalmain=main.getFileName(main.fileName).replace(".java","");
			this.starter = normalmain+"two";
			String dir3 = "";
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				dir3=dir;
			}
			else { // packager.isInRightFolders() == true
				dir3=dir+packager.getPackageName().replace(".","\\")+"\\";
			}
			File ifexists=new File(dir3);
			if(!ifexists.exists())
				ifexists.mkdirs();	
			PrintWriter printwriter = new PrintWriter(dir3+this.starter+".java");
			if(packager.containsPackage())
				printwriter.println("package "+packager.getPackageName()+";");
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
		CommandLine commandline = new CommandLine();	
		String jarExe = System.getProperty("java.home")+"\\bin\\jar.exe";
		
		for(String jar:commandline.getJavaFX()) {
			           		/*ProcessBuilder pb = new ProcessBuilder(
			                    	System.getProperty("java.home") + "\\bin\\jar.exe",
			                    	"-xvf", jar
			                	);*/
			                	/*
			                	"cmd.exe", "/c",
			                    	System.getProperty("java.home") + "\\bin\\jar.exe",
			                    	"-xvf", jar
			                    	*/
			                    	JFrame extractframe = new JFrame();
					final JTextArea textarea = new JTextArea();
					JScrollPane jscrollpane = new JScrollPane(textarea);
					
					extractframe.add(jscrollpane);
					extractframe.setSize(800,600);
					extractframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					extractframe.setVisible(true);		
			                    	
			                    	if(!makejarwithpackagesandjavafx) {
			                    		jar=dir+jar;
		                    		}
		                    		else { // makejarwithpackagesandjavafx == true
			                    		jar=dir.substring(0,dir.length()-5)+jar;
		                    		}
		                    		File file = new File(dir);
		                    		if(!file.exists())
		                    			file.mkdirs();	
					final String dir2 = dir;
					final String jar2 = jar;
			                    	SwingWorker<Void, String> worker = new SwingWorker<>() {
						   @Override
						    protected Void doInBackground() throws Exception {
						    	
						        ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c",System.getProperty("java.home") + "\\bin\\jar.exe", "-xvf", jar2);
						        pb.directory(new File(dir2));
						        pb.redirectErrorStream(true); // Merge stderr with stdout
						        Process process = pb.start();
						
						        try (BufferedReader reader = new BufferedReader(
						                new InputStreamReader(process.getInputStream()))) {
						            String line;
						            while ((line = reader.readLine()) != null) {
						                final String line2 = line;
                					        	    SwingUtilities.invokeLater( new Runnable() {
                					        	    	@Override
                					        	    	public void run() {
                					        	    		textarea.append(line2+"\n");
                				        	    		}		
                				        	    	   });
                					        	    
						                //publish(line); //Send line to process() on EDT
						            }
						        }
						
						        process.waitFor(); // Optional: wait for completion
						        return null;
						    }
						
						    @Override
						    protected void process(java.util.List<String> chunks) {
						        for (String line : chunks) {
						            textarea.append(line + "\n");
						            textarea.setCaretPosition(textarea.getDocument().getLength()); // Auto-scroll
						        }
						    }
						};
						
				 worker.execute();
				 try {
				 	worker.get();
			 	} catch (InterruptedException | java.util.concurrent.ExecutionException ex) {
			 		JOptionPane.showMessageDialog(null,"InterruptedException");
			 		ex.printStackTrace();
		 		}
	        	        	  // break;
	            }
	            delete_moduleinfo();
	}
	public void extractDLLFiles() {
		try {
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
		File file = new File(dir+dll);
		return file.exists();
	}
	public void extractStrangeFiles() {
		try {	
			URL url=ExtractJavaFXJars.class.getClassLoader().getResource("javafx.properties");	
			InputStream inputstream=url.openStream();
			Path outputpath=Paths.get(dir+"javafx.properties");
			Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public boolean strangeFilesExtracted() {
		File file = new File(dir+"javafx.properties");
		return file.exists();
	}
	public void extractJars() {	
		try {
			CommandLine commandline = new CommandLine();
			List<String> jars=commandline.getJavaFX();
			for(String jar:jars) {
				URL url=ExtractJavaFXJars.class.getClassLoader().getResource(jar);	
				InputStream inputstream=url.openStream();
				Path outputpath;
				if(!makejarwithpackagesandjavafx) {
					outputpath=Paths.get(dir+jar);
				}
				else { // makejarwithpackagesandjavafx == true
					outputpath=Paths.get(dir.substring(0,dir.length()-5)+jar);
				}								
				Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}		
	public boolean isAlreadyExtracted() {
		CommandLine commandline = new CommandLine();
		List<String> jars=commandline.getJavaFX();
		for(String jar:jars) {
			File file;
			if(!makejarwithpackagesandjavafx) {
				file=new File(dir+jar);
			}				
			else {
				file=new File(dir.substring(0,dir.length()-5)+jar); // remove "jars/ from C:\\documents\jars
			}		
			if(!file.exists())
				return false;
		}
		return true;
	}
}