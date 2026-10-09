public class MainDotJars {
	protected Main main;
	public MainDotJars(Main main) {
		this.main=main;
	}
	public List<String> getAllMainJars() {
		List<String> maindotjars=new ArrayList<String>();
		Packager packager=new Packager(main);
		if(!packager.containsPackage()) {
			String filePath=getFilePath(main.fileName);
			if(isExists(filePath)) {
				maindotjars.add(filePath);
			}
			return maindotjars;
		}
		else { // Contains package
			String filePath=getFilePath(main.fileName);
			if(isExists(filePath)) {
				maindotjars.add(filePath);
			}
			
		}
	}
	private String getFilePath(String dir) {
		if(!dir.endsWith("\\"))
			dir=dir+"\\";
		String classname=Main.getClassName(main.fileName);
		String filepath=dir+classname+".jar";
		return filepath;
	}
	private boolean isExists(String filepath) {
		File maindir=new File(filepath);
		return maindir.exists();
	}
}