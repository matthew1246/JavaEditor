package com.customyjavafx;

import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.io.IOException;
public class ExtractJUnit {
	public Main main;
	protected Packager packager;
	public ExtractJUnit(Main main) {
		this.main = main;
		this.packager=new Packager(main);
		ExtractJar("junit-4.13.2.jar");
		ExtractJar("hamcrest-core-1.3.jar");
	}
	public void ExtractJar(String jar) {
		try {
			URL url=ExtractJUnit.class.getClassLoader().getResource(jar);	
			InputStream inputstream=url.openStream();
			String dir = "";
			if(!packager.containsPackage() || !packager.isInRightFolders()) {
				dir=main.getDirectory(main.fileName);
			}
			else { // packager.isInRightFolders() == true
				dir=packager.classpath;
			}
			if(!dir.endsWith("\\"))
				dir=dir+"\\";
			Path outputpath=Paths.get(dir+jar);
			boolean needsAdmin = isDriveRoot(dir) || !canWriteToDriveRoot(dir);
			copyJar(inputstream,outputpath,needsAdmin);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}		
	public boolean isDriveRoot(String dir) {
		if(dir == null)
			return false;
		return dir.matches("^[A-Za-z]:\\\\+$");
	}
	public boolean canWriteToDriveRoot(String dir) {
		if(dir == null)
			return false;
		try {
			Path probe=Paths.get(dir+"extractjunit_probe.tmp");
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
			Path tempfile=Files.createTempFile("extractjar_", ".tmp");
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
}
