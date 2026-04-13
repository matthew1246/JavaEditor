import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;
import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
public class Compile {
	public void compileall(Main main,String fileName,SaveActionListener sal,ActionEvent ev) {
		try {
			sal.actionPerformed(ev);
			if(fileName.equals(""))
				fileName = main.fileName;
			String classpath = fileName.replaceAll("[^\\\\]+\\.java","");
			CommandLine commandline = new CommandLine();
			commandline.compileAll();
			
			Packager packager = new Packager(main);					
			if(packager.containsPackage()) {		
				if(packager.isInRightFolders()) {
					commandline.addPackage(packager.getPackageName());
					classpath=packager.classpath;
					String packagename=packager.getPackageName();
					labely: for(String file:main.filelistmodifier.filelist) {
						Packager packagerCustomFile=new Packager(file);
						if(!packagename.equals(packagerCustomFile.getPackageName())) {
							String[] options={"Yes","No"};
							int option=JOptionPane.showOptionDialog(null,"Make all classes in same folder have same package name?","All same package?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
							switch(option) {
								case JOptionPane.YES_OPTION:
									Path targetDir = Path.of(Main.getDirectory(main.fileName));
								   	try {
							        			//Files.createDirectories(targetDir);
								
									       	try (DirectoryStream<Path> stream = Files.newDirectoryStream(targetDir, "*.java")) {
										          	for (Path entry : stream) {
										
												// Read file
												String content = Files.readString(entry);
											
												// Remove existing package if present
												content = content.replaceFirst("(?s)^\\s*package\\s+[^;]+;\\s*", "");
											
												// Prepend correct package
												content = "package "+packagename + ";\n\n" + content;
											
												// Write to target
												Path targetFile = targetDir.resolve(entry.getFileName());
												Files.writeString(targetFile, content);
										            }
										}
										JOptionPane.showMessageDialog(null,"Code Updated");
									} catch (Exception ex) {
								        		ex.printStackTrace();
								    	}
									break;
								case JOptionPane.NO_OPTION:
									break;
							}
							break labely;				
						}
					}
				}
				else { // package name is not in right folder
					commandline.addPackageWithMinusD();
					main.filelistmodifier=new FileListModifier(fileName);
					String packagename=packager.getPackageName();
					
					// Make all classes in same folder have same package name
					labely: for(String file:main.filelistmodifier.filelist) {
						Packager packagerCustomFile=new Packager(file);
						if(!packagename.equals(packagerCustomFile.getPackageName())) {
							String[] options={"Yes","No"};
							int option=JOptionPane.showOptionDialog(null,"Make all classes in same folder have same package name?","All same package?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
							switch(option) {
								case JOptionPane.YES_OPTION:
									Path targetDir = Path.of(Main.getDirectory(fileName));
								   	try {
							        			//Files.createDirectories(targetDir);
								
									       	try (DirectoryStream<Path> stream = Files.newDirectoryStream(targetDir, "*.java")) {
										          	for (Path entry : stream) {
										
												// Read file
												String content = Files.readString(entry);
											
												// Remove existing package if present
												content = content.replaceFirst("(?s)^\\s*package\\s+[^;]+;\\s*", "");
											
												// Prepend correct package
												content = "package "+packagename + ";\n\n" + content;
											
												// Write to target
												Path targetFile = targetDir.resolve(entry.getFileName());
												Files.writeString(targetFile, content);
										            }
										}
										JOptionPane.showMessageDialog(null,"Code Updated");
									} catch (Exception ex) {
								        		ex.printStackTrace();
								    	}
									break;
								case JOptionPane.NO_OPTION:
									break;
							}
							break labely;				
						}
					}
					
					// Copy all classes with the same package name to new folder
					File targetDir = new File(classpath+packagename.replace(".","\\"));   
					// destination folder
					targetDir.mkdirs();
					for(String file:main.filelistmodifier.filelist) {
						Packager packagerCustomFile=new Packager(file);
						if(packagerCustomFile.containsPackage()) {
							if(packagename.equals(packagerCustomFile.getPackageName())) {
								File selectedFile=new File(file);	
							            File targetFile = new File(targetDir, selectedFile.getName());
							            try {
							                Files.copy(
							                        selectedFile.toPath(),
							                        targetFile.toPath(),
							                        StandardCopyOption.REPLACE_EXISTING
							                );
							                System.out.println("File copied successfully!");
							            } catch (IOException ex) {
							                JOptionPane.showMessageDialog(null, "Copy failed: " + ex.getMessage());
							            }

							}
						}
					}
				}
			}
			JOptionPane.showMessageDialog(null,"Output location of Jar: "+classpath);
			
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			
			for(String jar:preferences.jars) {
				commandline.addExternalJar(jar);
			}
			
			//Process process = compileFromMSDOS("*.java",classpath);
			String[] command = new String[3];
			command[0] = "cmd.exe";
			command[1] = "/c";
			command[2] = commandline.javac();
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command,null,new File(classpath));
			
			InputStream inputstream = process.getErrorStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String line = bufferedreader.readLine();
			if(line == null) {
				JOptionPane.showMessageDialog(null,"compiled");
			}
			else {
				String lines = line;
				while(true) {
					line = bufferedreader.readLine();
					if(line == null)
						break;
					lines = lines+"\n"+line;
				}
				JOptionPane.showMessageDialog(null,lines);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
	}
	public void compileall(Main main,String fileName,int javaversionnumber,SaveActionListener sal,ActionEvent ev) {
		try {
			sal.actionPerformed(ev);
			if(fileName.equals(""))
				fileName = main.fileName;
			String classpath = fileName.replaceAll("[^\\\\]+\\.java","");
			CommandLine commandline = new CommandLine();
			commandline.compileAll();
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			
			for(String jar:preferences.jars) {
				commandline.addExternalJar(jar);
			}
			
			commandline.earlierjavaversion(javaversionnumber);
			
			//Process process = compileFromMSDOS("*.java",classpath);
			String[] command = new String[3];
			command[0] = "cmd.exe";
			command[1] = "/c";
			command[2] = commandline.javac();
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command,null,new File(classpath));
			
			InputStream inputstream = process.getErrorStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String line = bufferedreader.readLine();
			if(line == null) {
				JOptionPane.showMessageDialog(null,"compiled");
			}
			else {
				String lines = line;
				while(true) {
					line = bufferedreader.readLine();
					if(line == null)
						break;
					lines = lines+"\n"+line;
				}
				JOptionPane.showMessageDialog(null,lines);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
	}
}