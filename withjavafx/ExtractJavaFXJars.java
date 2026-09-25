import java.util.jar.JarEntry;
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
import java.util.Set;
import java.util.stream.Collectors;
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
		String jarExe = "jar";
		
		JOptionPane.showMessageDialog(null,"JavaFX jars will be extracted to:"+dir);
		for(String jar:commandline.getJavaFX()) {
			           		/*ProcessBuilder pb = new ProcessBuilder(
			                    	"jar",
			                    	"-xvf", jar
			                	);*/
			                	/*
			                	"cmd.exe", "/c",
			                    	"jar",
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
						    	
						        ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","jar", "-xvf", jar2);
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
		for(String dll:getDLLFiles()) {
			try (InputStream inputstream=getResourceStream(dll)) {	
				if(inputstream == null) {
					System.err.println(dll + " not found, skipping extraction.");
					continue;
				}
				Path outputpath=Paths.get(dir+dll);
						
				Files.copy(inputstream,outputpath,StandardCopyOption.REPLACE_EXISTING);
			} catch(IOException ex) {
				ex.printStackTrace();
			}
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
		try (InputStream inputstream=getResourceStream("javafx.properties")) {	
			if(inputstream == null) {
				System.err.println("javafx.properties not found, skipping extraction.");
				return;
			}
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
		if(makejar)
			needsAdmin=	isDriveRoot(dir.substring(0,dir.length()-5)) || !canWriteToDriveRoot(dir.substring(0,dir.length()-5));
		try {
			CommandLine commandline = new CommandLine();
			List<String> jars=commandline.getJavaFX();
			for(String jar:jars) {
				try (InputStream inputstream=getResourceStream(jar)) {	
					if(inputstream == null) {
						System.err.println(jar + " not found, skipping extraction.");
						continue;
					}
					Path outputpath;
					if(!makejar) {
						outputpath=Paths.get(dir+jar);
					}
					else { //makejar == true
						outputpath=Paths.get(dir.substring(0,dir.length()-5)+jar);
					}
					copyJar(inputstream,outputpath,needsAdmin);
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}		
	public String getPackageFolder() {
		Package pkg = ExtractJavaFXJars.class.getPackage();
		if(pkg == null || pkg.getName() == null || pkg.getName().trim().isEmpty())
			return "";
		return pkg.getName().replace('.','/');
	}
	public String getPackageResourcePath(String resource) {
		String folder = getPackageFolder();
		if(folder.isEmpty())
			return resource;
		return folder + "/" + resource;
	}
	public String getCodeSourceLocation() {
		try {
			URL location=ExtractJavaFXJars.class.getProtectionDomain().getCodeSource().getLocation();
			if(location == null)
				return "";
			String path=location.toURI().getPath();
			if(path == null)
				return "";
			if(path.startsWith("/"))
				path=path.substring(1);
			return path;
		} catch(Exception ex) {
			return "";
		}
	}
	public byte[] readAll(InputStream in) {
		try {
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			byte[] buf = new byte[8192];
			int n;
			while((n=in.read(buf)) != -1)
				baos.write(buf,0,n);
			return baos.toByteArray();
		} catch(Exception ex) {
			return null;
		}
	}
	public InputStream getResourceStream(String resource) {
		try {
			String resPath = getPackageResourcePath(resource);
			String location = getCodeSourceLocation();
			if(!location.isEmpty()) {
				File file = new File(location);
				if(file.isFile()) {
					try(JarFile jf = new JarFile(file)) {
						JarEntry entry = jf.getJarEntry(resPath);
						if(entry == null && !resPath.equals(resource))
							entry = jf.getJarEntry(resource);
						if(entry != null) {
							byte[] bytes=readAll(jf.getInputStream(entry));
							if(bytes != null)
								return new java.io.ByteArrayInputStream(bytes);
						}
					}
				}
				else if(file.isDirectory()) {
					File resfile = new File(file,resPath);
					if(!resfile.exists() && !resPath.equals(resource))
						resfile = new File(file,resource);
					if(resfile.exists())
						return new java.io.FileInputStream(resfile);
				}
			}
			URL url = ExtractJavaFXJars.class.getResource("/" + resPath);
			if(url == null)
				url=ExtractJavaFXJars.class.getClassLoader().getResource(resPath);
			if(url == null && !resPath.equals(resource))
				url=ExtractJavaFXJars.class.getClassLoader().getResource(resource);
			if(url == null && !resPath.equals(resource))
				url = ExtractJavaFXJars.class.getResource("/" + resource);
			if(url != null)
				return url.openStream();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
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
	public void relaunchAsAdmin(String dir) {
		try {
			String userdir=System.getProperty("user.dir");
			String command;
			String exePath=getAppExePath();
			if(exePath != null) {
				command="Start-Process '"+exePath+"' -Verb RunAs";
			}
			else {
				String javaBin=System.getProperty("java.home")+"\\bin\\java.exe";
				String classpath=System.getProperty("java.class.path");
				if(classpath == null || classpath.trim().isEmpty())
					classpath=getCodeSourcePath();
				String mainclass=getApplicationMainClass();
				command="Start-Process -WorkingDirectory '"+userdir+"' -FilePath '"+javaBin+"' -ArgumentList '-cp','"+classpath+"','"+mainclass+"' -Verb RunAs";
			}
			ProcessBuilder pb=new ProcessBuilder("powershell.exe","-NoProfile","-Command",command);
			pb.redirectErrorStream(true);
			Process process=pb.start();
			int exitcode=process.waitFor();
			if(exitcode!=0) {
				JOptionPane.showMessageDialog(null,"Administrator privileges are required to extract jars to "+dir+".\nThe elevation request was cancelled or failed.");
				return;
			}
			System.exit(0);
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,"Could not relaunch the program as administrator: "+ex.getMessage());
		}
	}
	public String getApplicationMainClass() {
		try {
			URL location=ExtractJavaFXJars.class.getProtectionDomain().getCodeSource().getLocation();
			if(location != null) {
				File file=new File(location.toURI());
				if(file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
					try(JarFile jf=new JarFile(file)) {
						java.util.jar.Manifest manifest=jf.getManifest();
						if(manifest != null) {
							String mainclass=manifest.getMainAttributes().getValue("Main-Class");
							if(mainclass != null && !mainclass.trim().isEmpty())
								return mainclass.trim();
						}
					}
				}
			}
		} catch(Exception ex) {}
		return "Main";
	}
	public String getCodeSourcePath() {
		try {
			URL location=ExtractJavaFXJars.class.getProtectionDomain().getCodeSource().getLocation();
			if(location == null)
				return ".";
			Path path=Paths.get(location.toURI());
			return path.toAbsolutePath().toString();
		} catch(Exception ex) {
			return ".";
		}
	}
	public String getAppExePath() {
		try {
			String javaHome=System.getProperty("java.home");
			if(javaHome == null || javaHome.isEmpty())
				return null;
			boolean jpackageRuntime=javaHome.endsWith("runtime") || javaHome.endsWith("runtime\\") || javaHome.contains("\\runtime\\");
			if(!jpackageRuntime)
				return null;
			String classpath=System.getProperty("java.class.path");
			if(classpath == null || classpath.trim().isEmpty())
				return null;
			String first=classpath.split(";")[0];
			File cpfile=new File(first);
			File appdir=cpfile.isFile() ? cpfile.getParentFile() : null;
			if(appdir == null)
				return null;
			File imagedir=appdir.getParentFile();
			if(imagedir == null)
				return null;
			File[] exes=imagedir.listFiles((File dir,String name)->name.toLowerCase().endsWith(".exe"));
			if(exes != null && exes.length > 0)
				return exes[0].getAbsolutePath();
		} catch(Exception ex) {}
		return null;
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