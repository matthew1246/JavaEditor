public class MainDotJars {
	protected Main main;
	public MainDotJars(Main main) {
		this.main=main;
	}
	public List<String> getAllMainJars() {
		List<String> maindotjars=new ArrayList<String>();
		Packager packager=new Packager(main);
		if(!packager.containsPackage()) {
			String dir=Main.getDirectory(main.fileName);
			if(!dir.endsWith("\\"))
				dir=dir+"\\";
			String classname=Main.getClassName(main.fileName);
			String filepath=dir+classname+".jar";
			File maindir=new File(filepath);
			if(maindir.exists()) {
				maindotjars.add(filepath);
			}
		}
		else { // Contains package
			