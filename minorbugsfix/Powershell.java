import javax.swing.JOptionPane;
public class Powershell {
	public Powershell(String dir) {
		JOptionPane.showMessageDialog(null,dir+"ForJava"+javaversionnumber+"_"+main_class+".jar is already open. Run script to close "+main_class+".jar");
		FileWriter filewriter2 = new FileWriter(dir+"closeandcreatejar.bat",StandardCharsets.UTF_8);
		BufferedWriter output2 = new BufferedWriter(filewriter2);
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
	}
}
