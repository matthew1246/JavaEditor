import java.io.Console;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.io.File;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitResult;
import java.nio.file.Paths;
import javax.swing.JFileChooser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
public class Maven {
	public String fileName;
	public Maven() {
		setLayout();
		setListeners();
		showAllNotInitialised();
	}
	public void Change(String fileName) {
		if(fileName != null && !fileName.equals("")) {
			this.fileName = fileName;
			if(pomxmlExists()) {
				showMavenAlreadyInitialised();
			}
			else {
				showNotInitialised();
			}	
		}
		else if(fileName != null && fileName.equals("")) {
			showAllNotInitialised();
		}
	}
	public boolean pomxmlExists() {
		File dir = new File(Main.getDirectory(fileName));
		File[] folders = dir.listFiles(File::isDirectory);
		if (folders != null) {
	    		for (File f : folders) {
	        			File[] files=f.listFiles(File::isFile);
    				if(files != null) {
    					for(File file:files) {
    						if(file.getName().equals("pom.xml")) {
    							return true;
    						}
    					}	
    				}
		    	}
		    	return false;
		}
		else {
			return false;
		}
	}
	public JFrame frame;
	public JButton initialise;
	public JButton pomxml;
	public JButton add_dependency;
	public JButton addplugin;	
	public JButton add_dependencyorplugin;	
	public JButton removedependency;
	public JButton code;
	public JButton updatecode;
	public JButton deletecode;
	public JButton makeJarButton;
	public JButton makeJarsForAllVersionsOfJava;
	public JButton addHTML;
	public void setLayout() {
		frame = new JFrame();
		frame.setTitle("Maven");
		frame.setSize(500,500);
		frame.setLocation(980,175);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		initialise = new JButton("initialise");
		panel.add(initialise);
		
		pomxml=new JButton("pom.xml");
		panel.add(pomxml);
		
		add_dependency=new JButton("Add Dependency");
		panel.add(add_dependency);
		
		addplugin=new JButton("Add Plugin");
		panel.add(addplugin);
		
		add_dependencyorplugin=new JButton("Add Dependency/Plugin");
		panel.add(add_dependencyorplugin);
		
		removedependency = new JButton("Remove Dependency");
		panel.add(removedependency);
		
		code = new JButton("Code");
		panel.add(code);
		
		updatecode=new JButton("Update Code");
		panel.add(updatecode);
				
		deletecode=new JButton("Delete Code");
		panel.add(deletecode);

		makeJarButton=new JButton("update pom.xml with fat jar settings");
		panel.add(makeJarButton);
		
		makeJarsForAllVersionsOfJava = new JButton("Make fat Jars for all versions of java");
		panel.add(makeJarsForAllVersionsOfJava);
				
		addHTML = new JButton("Add HTML file");
		panel.add(addHTML);
				
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	public String recurseFolders(File folder) {
		for(File file:folder.listFiles()) {
			if(file.isFile()) {
				if(file.getName().equals("App.java")) {
					return "";
				}
			}
			else if(file.isDirectory()) {
				return file.getName()+"/"+recurseFolders(file);
			}
		}
		return "";
	}																																
	public String getPackageName() {
		try {
			String pomxml=getPOMXMLs();
				
			DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc=builder.parse(new File(pomxml));
			doc.getDocumentElement().normalize();
			
			Element root=doc.getDocumentElement();;
			String package_com_whatever=root.getElementsByTagName("groupId").item(0).getTextContent();
			package_com_whatever=package_com_whatever.replace(".","/");
			File folder=new File(Main.getDirectory(pomxml)+"src/main/java/"+package_com_whatever);
			if(folder.exists() && folder.isDirectory()) {
				return package_com_whatever;
			}
			else {
				folder=new File(Main.getDirectory(pomxml)+"src/main/java/");
				package_com_whatever=recurseFolders(folder);
				if(!package_com_whatever.equals(""))
					return package_com_whatever;
				else {
					JOptionPane.showMessageDialog(null,"Can't find package name that stores App.java");
					JOptionPane.showMessageDialog(null,"Select folder for where code goes.");
					while(true) {
						JFileChooser filechooser = new JFileChooser();
						filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						filechooser.setCurrentDirectory(folder);
						int returnValue = filechooser.showOpenDialog(null);
						if(returnValue == JFileChooser.APPROVE_OPTION) {
							package_com_whatever=filechooser.getSelectedFile().getAbsolutePath();
							package_com_whatever= package_com_whatever.replace(folder.getAbsolutePath(),"");	
							JOptionPane.showMessageDialog(null,package_com_whatever);
							return package_com_whatever;
						}
					}										
				}
			}				
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}										
	}
	public static void deleteFolder(Path folderPath) throws IOException {
		Files.walkFileTree(folderPath,new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult postVisitDirectory(Path dir,IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}												
				
	public void setListeners() {
		addHTML.addActionListener((ev) -> {
			JFileChooser chooser = new JFileChooser();
        			chooser.setDialogTitle("Select a file to copy");

			int result = chooser.showOpenDialog(null);
			
			if (result == JFileChooser.APPROVE_OPTION) {
			            File selectedFile = chooser.getSelectedFile();
			
			            File targetDir = new File(Main.getDirectory(getPOMXMLs())+"src/Main/resources");   // destination folder
				targetDir.mkdirs();	
			            File targetFile = new File(targetDir, selectedFile.getName());
			
			            try {
			                Files.copy(
			                        selectedFile.toPath(),
			                        targetFile.toPath(),
			                        StandardCopyOption.REPLACE_EXISTING
			                );
			                JOptionPane.showMessageDialog(null, "File copied successfully!");
			            } catch (IOException e) {
			                JOptionPane.showMessageDialog(null, "Copy failed: " + e.getMessage());
			            }
			}
		});
			
		makeJarsForAllVersionsOfJava.addActionListener(ev -> {
			CommandLine commandline = new CommandLine();
			String cmd =
    "(if exist jars rmdir /s /q jars || echo no jars folder) && " +
    "mkdir jars && " +
    "for %v in (17 18 19 20 21 22 23) do (" +
    "  mvn clean package -Pjava%v && " +
    "  copy /Y target\\projecttwo-1.0-SNAPSHOT-java%v.jar jars\\ " +
    ") && " +
    "for %v in (17 18 19 20 21 22 23) do (" +
    "  copy /Y jars\\projecttwo-1.0-SNAPSHOT-java%v.jar target\\ " +
    ")";
			commandline.runWithMSDOS(cmd,Main.getDirectory(getPOMXMLs()));
		});

		makeJarButton.addActionListener( ev -> {
			makeFatJar();
			JOptionPane.showMessageDialog(null,"updated pom.xml with fat jar settings");
		});		
		add_dependency.addActionListener((ev) -> {
			AddDependency add_dependency = new AddDependency(this);
		});
		deletecode.addActionListener((ev) -> {
			String pomxml=getPOMXMLs();
			String dir=Main.getDirectory(pomxml);
			Path folderPath=Paths.get(dir);
			
			try {
				deleteFolder(folderPath);
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
			showNotInitialised();
		});
		updatecode.addActionListener(ev -> {
			Path sourceDir = Path.of(Main.getDirectory(fileName));
			String pomxml = getPOMXMLs();
			
			String packagePath = getPackageName(); // e.g. com/perky
		    	String packageLine = "package " + packagePath.replace("/", ".") + ";";
			
	    		Path targetDir = Path.of(
		       	Main.getDirectory(pomxml) + "src/main/java/" + packagePath);
		
		   	try {
	        			//Files.createDirectories(targetDir);
		
			       	try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.java")) {
				          	for (Path entry : stream) {
				
						// Read file
						String content = Files.readString(entry);
					
						// Remove existing package if present
						//content = content.replaceFirst("(?s)^\\s*package\\s+[^;]+;\\s*", "");
					
						// Prepend correct package
						content = packageLine + "\n\n" + content;
					
						// Write to target
						Path targetFile = targetDir.resolve(entry.getFileName());
						Files.writeString(targetFile, content);
				            }
				}
				JOptionPane.showMessageDialog(null,"Code Updated");
			} catch (Exception ex) {
		        		ex.printStackTrace();
		    	}
		});
		code.addActionListener((ev) -> {
			CommandLine commandline = new CommandLine();
			commandline.run("explorer ." ,Main.getDirectory(getPOMXMLs())+"src/main/java/"+getPackageName());				
		});
		initialise.addActionListener( ev -> {
			showMavenAlreadyInitialised();
			// Generatepomxml();
			CommandLine commandline = new CommandLine();
			commandline.runWithMSDOS("echo mvn archetype:generate && mvn archetype:generate",Main.getDirectory(fileName));	
		});
		pomxml.addActionListener( ev -> {
			CommandLine commandline = new CommandLine();
			String pomxml = getPOMXMLs();
			if(!pomxml.equals("")) {
				commandline.run("explorer pom.xml",Main.getDirectory(pomxml));
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Could not find pom.xml");
			}
		});	
		removedependency.addActionListener( ev -> {
			DependencyRemover dependencyremover=new DependencyRemover(getPOMXMLs());
		});		
	}	
	public String getPOMXMLs() {
		File dir = new File(Main.getDirectory(fileName));
		File[] folders = dir.listFiles(File::isDirectory);
		if (folders != null) {
	    		for (File f : folders) {
	        			File[] files=f.listFiles(File::isFile);
    				if(files != null) {
    					for(File file:files) {
    						if(file.getName().equals("pom.xml")) {
    							return file.getAbsolutePath();
    						}
    					}
    				}
		    	}
		    	return "";
		}
		else {
			return "";
		}
	}
	public void showAllNotInitialised() {
		initialise.setEnabled(false);
		pomxml.setEnabled(false);
		add_dependency.setEnabled(false);
		addplugin.setEnabled(false);
		add_dependencyorplugin.setEnabled(false);
		removedependency.setEnabled(false);
		code.setEnabled(false);
		updatecode.setEnabled(false);
		deletecode.setEnabled(false);
		makeJarButton.setEnabled(false);
		makeJarsForAllVersionsOfJava.setEnabled(false);
		addHTML.setEnabled(false);
	}
	public void showNotInitialised() {
		initialise.setEnabled(true);
		
		pomxml.setEnabled(false);
		add_dependency.setEnabled(false);
		addplugin.setEnabled(false);
		add_dependencyorplugin.setEnabled(false);
		removedependency.setEnabled(false);
		code.setEnabled(false);
		updatecode.setEnabled(false);
		deletecode.setEnabled(false);
		makeJarButton.setEnabled(false);
		makeJarsForAllVersionsOfJava.setEnabled(false);
		addHTML.setEnabled(false);
	}
	public void showMavenAlreadyInitialised() {
		initialise.setEnabled(false);
		
		pomxml.setEnabled(true);
		add_dependency.setEnabled(true);
		addplugin.setEnabled(true);
		add_dependencyorplugin.setEnabled(true);
		removedependency.setEnabled(true);
		code.setEnabled(true);
		updatecode.setEnabled(true);
		deletecode.setEnabled(true);
		makeJarButton.setEnabled(true);
		makeJarsForAllVersionsOfJava.setEnabled(true);
		addHTML.setEnabled(true);
	}
	public void Generatepomxml() {
		String filestring = """
<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       		<modelVersion>4.0.0</modelVersion>
  		<groupId>com.jetbrains.marco</groupId>
  		<artifactId>maven-tutorial</artifactId>
  		<version>1.0-SNAPSHOT</version>

 		<properties>
      			<maven.compiler.source>11</maven.compiler.source>
      			<maven.compiler.target>11</maven.compiler.target>

      			<junit.version>5.8.2</junit.version>
  		</properties>

  		<build>
      			<plugins>
           			<plugin>
               				<artifactId>maven-surefire-plugin</artifactId>
               				<version>2.22.2</version>
           			</plugin>
           			<plugin>
               				<artifactId>maven-failsafe-plugin</artifactId>
               				<version>2.22.2</version>
           			</plugin>
      			</plugins>
  		</build>

  		<dependencies>
      			<dependency>
          			<groupId>com.mpatric</groupId>
          			<artifactId>mp3agic</artifactId>
          			<version>0.9.1</version>
      			</dependency>
      			<!-- Only needed to run tests in a version of IntelliJ IDEA that bundles older versions -->
      			<dependency>
          			<groupId>org.junit.platform</groupId>
          			<artifactId>junit-platform-launcher</artifactId>
          			<version>1.8.2</version>
          			<scope>test</scope>
      			</dependency>
      			<dependency>
           			<groupId>org.junit.jupiter</groupId>
           			<artifactId>junit-jupiter-engine</artifactId>
           			<version>${junit.version}</version>
           			<scope>test</scope>
      			</dependency>
      			<dependency>
          			<groupId>org.junit.vintage</groupId>
				<artifactId>junit-vintage-engine</artifactId>
			        <version>${junit.version}</version>
			        <scope>test</scope>
      			</dependency>
  		</dependencies>
	</project>
		""";
		try {
			PrintWriter printwriter = new PrintWriter(Main.getDirectory(fileName)+"pom.xml");
			printwriter.println(filestring);
			printwriter.close();
		} catch (java.io.FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}	
	public void makeFatJar() {
		String[] options = {"App.java","Main.java"};
		int choice=JOptionPane.showOptionDialog(null,"Do you want the starter class to be App.java or Main.java?","Confirm",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		if(choice == 0) {
			String fileString = """
<?xml version="1.0" encoding="UTF-8"?><project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.perky</groupId>
  <artifactId>projecttwo</artifactId>
  <version>1.0-SNAPSHOT</version>
<profiles>
  <profile>
    <id>default-java17</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
      <java.release>17</java.release>
      <jar.suffix>java17</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java17</id>
    <properties>
      <java.release>17</java.release>
      <jar.suffix>java17</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java18</id>
    <properties>
      <java.release>18</java.release>
      <jar.suffix>java18</jar.suffix>
    </properties>
  </profile>  
  <profile>
    <id>java19</id>
    <properties>
      <java.release>19</java.release>
      <jar.suffix>java19</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java20</id>
    <properties>
      <java.release>20</java.release>
      <jar.suffix>java20</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java21</id>
    <properties>
      <java.release>21</java.release>
      <jar.suffix>java21</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java22</id>
    <properties>
      <java.release>22</java.release>
      <jar.suffix>java22</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java23</id>
    <properties>
      <java.release>23</java.release>
      <jar.suffix>java23</jar.suffix>
    </properties>
  </profile>
</profiles>
  <name>projecttwo</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <java.release>17</java.release>
    <maven.compiler.release>${java.release}</maven.compiler.release>  
</properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.junit</groupId>
        <artifactId>junit-bom</artifactId>
        <version>5.11.0</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <scope>test</scope>
    </dependency>
    <!-- Optionally: parameterized tests support -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-params</artifactId>
      <scope>test</scope>
    </dependency>
  
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
  
    <!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp -->
   <dependency>
     <groupId>com.squareup.okhttp3</groupId>
     <artifactId>okhttp</artifactId>
     <version>3.0.0-RC1</version>
   </dependency>
  </dependencies>

  <build>
    <plugins>
	<plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.5.1</version>

      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>

          <configuration>
	    <finalName>${project.artifactId}-${project.version}-${jar.suffix}</finalName>
            <!-- Prevent dependency-reduced-pom.xml -->
            <createDependencyReducedPom>false</createDependencyReducedPom>

            <!-- Main class -->
            <transformers>
              <transformer
                implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                <mainClass>com.perky.App</mainClass>
              </transformer>
            </transformers>
          </configuration>
        </execution>
      </executions>
    </plugin>
        <!-- clean lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#clean_Lifecycle -->
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.4.0</version>
        </plugin>
        <!-- default lifecycle, jar packaging: see https://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_jar_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.3.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.13.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>3.3.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-jar-plugin</artifactId>
          <version>3.4.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>3.1.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>3.1.2</version>
        </plugin>
        <!-- site lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#site_Lifecycle -->
        <plugin>
          <artifactId>maven-site-plugin</artifactId>
          <version>3.12.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-project-info-reports-plugin</artifactId>
          <version>3.6.1</version>
        </plugin>
      </plugins>
  </build>
</project>
	""";	
			try {
				PrintWriter printwriter = new PrintWriter(getPOMXMLs());
				printwriter.println(fileString);
				printwriter.close();
			} catch (java.io.FileNotFoundException ex) {
				ex.printStackTrace();
			}	
		}
		else {
			String fileString = """
<?xml version="1.0" encoding="UTF-8"?><project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.perky</groupId>
  <artifactId>projecttwo</artifactId>
  <version>1.0-SNAPSHOT</version>
<profiles>
  <profile>
    <id>default-java17</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
      <java.release>17</java.release>
      <jar.suffix>java17</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java17</id>
    <properties>
      <java.release>17</java.release>
      <jar.suffix>java17</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java18</id>
    <properties>
      <java.release>18</java.release>
      <jar.suffix>java18</jar.suffix>
    </properties>
  </profile>  
  <profile>
    <id>java19</id>
    <properties>
      <java.release>19</java.release>
      <jar.suffix>java19</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java20</id>
    <properties>
      <java.release>20</java.release>
      <jar.suffix>java20</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java21</id>
    <properties>
      <java.release>21</java.release>
      <jar.suffix>java21</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java22</id>
    <properties>
      <java.release>22</java.release>
      <jar.suffix>java22</jar.suffix>
    </properties>
  </profile>
  <profile>
    <id>java23</id>
    <properties>
      <java.release>23</java.release>
      <jar.suffix>java23</jar.suffix>
    </properties>
  </profile>
</profiles>
  <name>projecttwo</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <java.release>17</java.release>
    <maven.compiler.release>${java.release}</maven.compiler.release>  
</properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.junit</groupId>
        <artifactId>junit-bom</artifactId>
        <version>5.11.0</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <scope>test</scope>
    </dependency>
    <!-- Optionally: parameterized tests support -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-params</artifactId>
      <scope>test</scope>
    </dependency>
  
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
  
    <!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp -->
   <dependency>
     <groupId>com.squareup.okhttp3</groupId>
     <artifactId>okhttp</artifactId>
     <version>3.0.0-RC1</version>
   </dependency>
  </dependencies>

  <build>
    <plugins>
	<plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.5.1</version>

      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>

          <configuration>
	    <finalName>${project.artifactId}-${project.version}-${jar.suffix}</finalName>
            <!-- Prevent dependency-reduced-pom.xml -->
            <createDependencyReducedPom>false</createDependencyReducedPom>

            <!-- Main class -->
            <transformers>
              <transformer
                implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                <mainClass>com.perky.Main</mainClass>
              </transformer>
            </transformers>
          </configuration>
        </execution>
      </executions>
    </plugin>
        <!-- clean lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#clean_Lifecycle -->
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.4.0</version>
        </plugin>
        <!-- default lifecycle, jar packaging: see https://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_jar_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.3.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.13.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>3.3.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-jar-plugin</artifactId>
          <version>3.4.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>3.1.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>3.1.2</version>
        </plugin>
        <!-- site lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#site_Lifecycle -->
        <plugin>
          <artifactId>maven-site-plugin</artifactId>
          <version>3.12.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-project-info-reports-plugin</artifactId>
          <version>3.6.1</version>
        </plugin>
      </plugins>
  </build>
</project>
	""";	
			try {
				PrintWriter printwriter = new PrintWriter(getPOMXMLs());
				printwriter.println(fileString);
				printwriter.close();
			} catch (java.io.FileNotFoundException ex) {
				ex.printStackTrace();
			}	
		}
	}				
	/*
	** This searches dependency and plugin for Maven.
	*/
	public void run() {
		Console console=System.console();
		String input=console.readLine("What you like to search? ");
		System.out.println("*"+input+"*");
		GetAll(input);
		/*
		String rowsa=console.readLine("How many search results do you want?");
		int rows=Integer.parseInt(rowsa);
		
		String responseJson=Search(rows,input);
	
		Parse(responseJson);
		*/
	}
		
	public OkHttpClient client = new OkHttpClient();
	public String Search(int how_many,String query) {
		return Search(how_many,query,0);
	}	
	public String Search(int how_many,String query,int start_index) {
		int rows = how_many;
		String input = query;
		String url = "https://search.maven.org/solrsearch/select?q="+input+"&rows="+rows+"&start="+start_index+"&wt=json";
		String responseJson=get(url);
		return responseJson;
	}		
	public String get(String url) {
		try {
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch(IOException ex) {
			ex.printStackTrace();
			return "IOException occured.";
		}
	}
	public void Parse(String responseJson) {
		System.out.println(responseJson);
		
		System.out.println();
		
		JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
		
		// {"responseHeader":{"status":0,"QTime":145,"params":{"q":"jar","core":"","defType":"dismax","qf":"text^20 g^5 a^10","indent":"off","spellcheck":"true","fl":"id,g,a,latestVersion,p,ec,repositoryId,text,timestamp,versionCount","start":"0","spellcheck.count":"5","sort":"score desc,timestamp desc,g asc,a asc","rows":"25","wt":"json","version":"2.2"}},"response":{"numFound":582830
		
		JsonObject response=jsonObject.getAsJsonObject("response");
		
		int totalnumber=response.get("numFound").getAsInt();
		System.out.println("Total number of search results is: "+totalnumber);
		
		System.out.println();
		
		System.out.println(response);
		
		
		System.out.println();
		
		JsonArray docs=response.getAsJsonArray("docs");
		
		for(int i = 0; i < docs.size(); i++) {
			JsonObject doc = docs.get(i).getAsJsonObject();
			System.out.println(doc);
			
			System.out.println();
			
			String groupId=doc.get("g").getAsString();
			String artifactId=doc.get("a").getAsString();
			String version=doc.get("latestVersion").getAsString();
			System.out.println("groupId: "+groupId+" artifactId: "+artifactId+" version: "+version);
			
			System.out.println();
			
			String pluginordependency= "jar";
			if(doc.get("p") != null)
				pluginordependency=	doc.get("p").getAsString();
			
			if(pluginordependency.contains("plugin")) {
				System.out.println(pluginordependency);
			}
			else {
				System.out.println("Is a dependency");
			}
			
			System.out.println();	
		}
	}
	public void GetAll(String query) {
		int totalnumber=GetHowMany(query);
		System.out.println("total number is: "+totalnumber);
	
		for(int i = 0; i < totalnumber; i+=20) {
			Parse(Search(20,query,i));
		}
	}
	/*
	** This function gets how many dependencies and plugins there
	** for a given String. The given String is the variable named query.
	*/
	public int GetHowMany(String query) {
		String responseJson=Search(1,query);
		JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
		// {"responseHeader":{"status":0,"QTime":145,"params":{"q":"jar","core":"","defType":"dismax","qf":"text^20 g^5 a^10","indent":"off","spellcheck":"true","fl":"id,g,a,latestVersion,p,ec,repositoryId,text,timestamp,versionCount","start":"0","spellcheck.count":"5","sort":"score desc,timestamp desc,g asc,a asc","rows":"25","wt":"json","version":"2.2"}},"response":{"numFound":582830
		
		JsonObject response=jsonObject.getAsJsonObject("response");
		int totalnumber=response.get("numFound").getAsInt();
		
		return totalnumber;
	}
}