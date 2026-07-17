import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
public class Powershell {
	protected Packager packager;
	protected String main_class;
	protected String dir;
	protected BufferedWriter output2;
	public Powershell(Main main,String main_class,String dir,AllFiles allfiles) {
		this.dir = dir;
		this.main_class = main_class;
		try {
			packager=new Packager(main);
			if(packager.containsPackage()) {		
				if(packager.isInRightFolders()) {							
					dir=packager.classpath;
					String packagename=packager.getPackageName();
					labely: for(String file:main.filelistmodifier.fullpath) {
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
					main.filelistmodifier=new FileListModifier(main.fileName);
					String packagename=packager.getPackageName();
					
					// Make all classes in same folder have same package name
					labely: for(String file:main.filelistmodifier.fullpath) {
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
					
					// Copy all classes with the same package name to new folder
					File targetDir = new File(dir+packagename.replace(".","\\"));   
					// destination folder
					targetDir.mkdirs();
					for(String file:main.filelistmodifier.fullpath) {
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
			
			
			String filename=Powershell.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			filename = main.getFileName(filename);
			if(filename.startsWith("/"))
				filename=filename.substring(1,filename.length());
			JOptionPane.showMessageDialog(null,filename+" is already open. Run script to close "+filename);
			if(dir.contains("\\")) {
				if(!dir.endsWith("\\"))
					dir=dir+"\\";
			}
			else if(dir.contains("/")) {
				if(!dir.endsWith("/"))
					dir=dir+"/";
			}
			FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
			output2 = new BufferedWriter(filewriter2);
			output2.write("cd "+dir);
			output2.write("\n");
			output2.write("START /B /WAIT taskkill /F /im java.exe");
			output2.write("\n");
			output2.write("START /B /WAIT taskkill /F /im javaw.exe");
			output2.write("\n");
			for(int i = 0; i < allfiles.files.size(); i++) {
				File file2 = new File(allfiles.files.get(i));
				if(file2.exists()) {
					output2.write("del "+allfiles.files.get(i));
					output2.write("\n");
				}
			}
			// output2.close();
		} catch (java.net.URISyntaxException ex) {
			ex.printStackTrace();
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
	}
	public void Compile(int javaversionnumber,String fileName) {
		try {
			CommandLine commandline = new CommandLine();
			commandline.compileAll();
			StoreSelectedFile storeselectedfile = new StoreSelectedFile();
			Preferences preferences=storeselectedfile.get(fileName);
			
			if(packager.containsPackage()) {
				if(packager.isInRightFolders()) {
					commandline.addPackage(packager.getPackageName());
				}
				else {
					commandline.addPackageWithMinusD();
				}
			}
			
			for(String jar:preferences.jars) {
				commandline.addExternalJar(jar);
			}
		
			commandline.earlierjavaversion(javaversionnumber);
			
			output2.write("START /B /WAIT cmd.exe /c "+commandline.javac());
			output2.write("\n");
			
			//output2.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void makeJar(int javaversionnumber) {
		try {
			String main_class2 = main_class;
			if(packager.containsPackage()) {
				String[] splited=  main_class.split("\\.");
				main_class2 = splited[splited.length-1];
			}
			File file = new File(dir);
			File parentdirectory=file.getParentFile();
			if(packager.containsPackage() && packager.isInRightFolders()) {	
				parentdirectory=file;	
			}
			JOptionPane.showMessageDialog(null,"parentdirectory is:"+parentdirectory.getAbsolutePath());
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				// START /B /WAIT cmd.exe /c "C:\Program Files\Java\jdk-23\bin\jar.exe" cfm Main.jar mf.txt .
				if(javaversionnumber != 23) {
					output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+parentdirectory.getAbsolutePath()+"\\ForJava"+javaversionnumber+"_"+main_class2+".jar mf.txt .");
				}
				else {
					output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+parentdirectory.getAbsolutePath()+"\\"+main_class2+".jar mf.txt .");
					output2.write("\n");
					output2.write("java -jar "+parentdirectory.getAbsolutePath()+"\\"+main_class2+".jar");
				}
			}
			else { // Code is a package and package.isInRightFolder() == true
				if(javaversionnumber != 23) {
					// output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+parentdirectory.getAbsolutePath()+"\\ForJava"+javaversionnumber+"_"+main_class2+".jar mf.txt -C jars . "+packager.getPackageName().replace(".","\\"));
					output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+parentdirectory.getAbsolutePath()+"\\ForJava"+javaversionnumber+"_"+main_class2+".jar mf.txt .");
				}
				else {
					output2.write("START /B /WAIT cmd.exe /c \""+System.getProperty("java.home")+"\\bin\\jar.exe\" cfm "+parentdirectory.getAbsolutePath()+"\\"+main_class2+".jar mf.txt .");
					output2.write("\n");
					output2.write("java -jar "+parentdirectory.getAbsolutePath()+"\\"+main_class2+".jar");
				}
			}						
			output2.write("\n");
			// output2.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void Finish() {
		try {
			output2.close();
			CommandLine commandline = new CommandLine();
			String liney = "powershell -Command \"Start-Process powershell -Verb runAs -ArgumentList '-Command cmd /c \""+dir+"closeandcreatejar.bat\"'\"";
		
			commandline.runWithMSDOS(liney,dir);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
