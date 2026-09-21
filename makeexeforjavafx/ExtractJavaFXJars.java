import java.util.Set;
import java.util.jar.JarFile;
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
	public boolean makejar = false;
	public String dir;
	public Main main;
	public Packager packager;
	public ExtractJavaFXJars(Main main) {	
		this.main = main;
		this.packager = new Packager(main);	
		if(!packager.containsPackage() || !packager.isInRightFolders()) {
			dir=main.getDirectory(main.fileName);
			makejar=false;
		}
		else { // packager.isInRightFolders() == true
			dir=packager.classpath;
			makejar = false;
		}	
		if(!dir.endsWith("\\"))
			dir=dir+"\\";
		this.dir = dir;
		process();
	}
	public ExtractJavaFXJars(Main main,boolean makejar) {
		this.main = main;
		this.packager = new Packager(main);	
		if(makejar) {
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				dir=main.getDirectory(main.fileName);
				this.makejar=false;
			}
			else { // packager.isInRightFolders() == true
				dir=packager.classpath;
				if(!dir.endsWith("\\"))
					dir=dir+"\\";
				dir=dir+"jars";	
				this.makejar = true;
			}	
		}
		else { // makejar == false
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				dir=main.getDirectory(main.fileName);
				this.makejar = false;
			}
			else { // packager.isInRightFolders() == true
				dir=packager.classpath;
				this.makejar = false;
			}
		}		
		if(!dir.endsWith("\\"))
			dir=dir+"\\";
		this.dir = dir;
		process();
	}
	public void process() {
		String normalmain=main.getFileName(main.fileName).replace(".java","");
		this.starter = normalmain+"two";
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
			String dir3 = "";
			if(makejar) {
				if(!packager.containsPackage() || !packager.isInRightFolders()) {
					dir3=dir;
				}
				else { // packager.isInRightFolders() == true
					dir3=dir.substring(0,dir.length()-5)+packager.getPackageName().replace(".","\\")+"\\";
				}
			}
			else {
				 if(!packager.containsPackage() || !packager.isInRightFolders()) {
					dir3=dir;
				}
				else {
					dir3=dir+packager.getPackageName().replace(".","\\")+"\\";
				}
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
			                    	
			                    	if(!makejar) {
			                    		jar=dir+jar;
		                    		}
		                    		else { // makejar == true
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
		boolean needsAdmin = isDriveRoot(dir) || !canWriteToDriveRoot(dir);
	
		try {
			CommandLine commandline = new CommandLine();
			List<String> jars=commandline.getJavaFX();
			for(String jar:jars) {
				URL url=ExtractJavaFXJars.class.getClassLoader().getResource(jar);	
				InputStream inputstream=url.openStream();
				Path outputpath;
				if(!makejar) {
					outputpath=Paths.get(dir+jar);
				}
				else { //makejar == true
					outputpath=Paths.get(dir.substring(0,dir.length()-5)+jar);
				}
				copyJar(inputstream,outputpath,needsAdmin);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}		
	public boolean isDriveRoot(String dir) {
		return dir.matches("^[A-Za-z]:\\\\+$");
	}
	public boolean canWriteToDriveRoot(String dir) {
		try {
			Path probe=Paths.get(dir+"extractjavafxjars_probe.tmp");
			Files.createFile(probe);
			Files.delete(probe);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	public void copyJar(InputStream inputstream, Path outputpath, boolean needsAdmin) {
		try {
			if(needsAdmin)
				copyJarAsAdmin(inputstream,outputpath);
			else
				Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public void copyJarAsAdmin(InputStream inputstream, Path outputpath) {
		try {
			Path tempfile=Files.createTempFile("extractjavafx_", ".tmp");
			try {
				Files.copy(inputstream,tempfile,StandardCopyOption.REPLACE_EXISTING);
				if(!elevatedCopy(tempfile,outputpath))
					javax.swing.JOptionPane.showMessageDialog(null,"Could not copy "+outputpath+" with administrator privileges.\nThe elevation request was cancelled or failed.");
			} finally {
				Files.deleteIfExists(tempfile);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public boolean elevatedCopy(Path source, Path outputpath) {
		try {
			String copyCommand = "Copy-Item -LiteralPath '"+singleQuote(source.toString())+"' -Destination '"+singleQuote(outputpath.toAbsolutePath().toString())+"' -Force\r\nif ($?) { exit 0 } else { Write-Error 'Copy failed'; exit 1 }";
			String encoded = java.util.Base64.getEncoder().encodeToString(copyCommand.getBytes("UTF-16LE"));
			Path script=Files.createTempFile("elevatedcopy_", ".ps1");
			try {
				String orchestrator = "$p = Start-Process -FilePath 'powershell.exe' -Verb RunAs -Wait -PassThru -WindowStyle Hidden -ArgumentList '-NoProfile','-WindowStyle','Hidden','-EncodedCommand','"+encoded+"'\r\nWrite-Output $p.ExitCode";
				Files.write(script, orchestrator.getBytes("US-ASCII"));
				ProcessBuilder pb=new ProcessBuilder("powershell.exe","-NoProfile","-ExecutionPolicy","Bypass","-File",script.toAbsolutePath().toString());
				pb.redirectErrorStream(true);
				Process process=pb.start();
				String output=readProcessOutput(process.getInputStream());
				int exitcode=process.waitFor();
				return exitcode==0 && output.trim().equals("0");
			} finally {
				Files.deleteIfExists(script);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public String singleQuote(String s) {
		return s.replace("'","''");
	}
	public String readProcessOutput(java.io.InputStream in) {
		try {
			java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
			byte[] buf=new byte[4096];
			int n;
			while((n=in.read(buf))!=-1)
				baos.write(buf,0,n);
			return new String(baos.toByteArray(),"UTF-8");
		} catch(Exception ex) {
			return "";
		}
	}		
	public boolean isAlreadyExtracted() {
		CommandLine commandline = new CommandLine();
		List<String> jars=commandline.getJavaFX();
		for(String jar:jars) {
			File file;
			if(!makejar) {
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
	public boolean isUnzippedAgain() {
		CommandLine commandline = new CommandLine();
		List<String> jars = commandline.getJavaFX();
		for(String jar : jars) {
			String jarPath;
			if(!makejar) {
				jarPath = dir + jar;
			}
			else {
				jarPath = dir.substring(0, dir.length()-5) + jar;
			}
			try {
				JarFile jarFile = new JarFile(jarPath);
				Set<String> rootFolders = new java.util.HashSet<>();
				jarFile.stream().forEach(entry -> {
					String name = entry.getName();
					int slash = name.indexOf('/');
					if(slash != -1) {
						rootFolders.add(name.substring(0, slash));
					}
				});
				jarFile.close();
				for(String root : rootFolders) {
					File file;
					/*if(!makejar) {
						file = new File(dir + root);
					}
					else {
						file = new File(dir.substring(0, dir.length()-5) + root);
					}
					*/
					file=new File(dir+root);
					if(!file.exists())
						return false;
				}
			} catch(IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
