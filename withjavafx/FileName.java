public class FileName {
	protected String main_class2;
	public FileName(String fileName) {
		main_class2=Main.getClassName(fileName);
	}
	boolean makeAllVersionsJar = false;
	public void makeAllVersionsJar() {
		makeAllVersionsJar=true;
	}
	boolean makeCertainVersionNumber=false;
	public void makeCertainVersionNumber() {
		makeCertainVersionNumber=true;
	}
	public boolean makeForJavaFX = false;
	public void makeForJavaFX() {
		makeForJavaFX=true;
	}
	public boolean isPowershell = false;
	public void isPowershell() {
		isPowershell = true;
	}
	public String getJarFileName(int javaversionnumber) {
		if(makeAllVersionsJar) {
			if(isPowershell) {
				if(javaversionnumber == -2) {
					return "HasJavaFX_ForJava23_Windows11x64";
				}
				else {
					return "HasJavaFX_ForJava"+javaversionnumber+"_Windows11x64";
				}
			}
			else {
				if(javaversionnumber != 23 && javaversionnumber != -2) {
					return "ForJava"+javaversionnumber+"_"+main_class2;
				}
				else {
					return main_class2;
				}
			}
		}
		else if(makeCertainVersionNumber) {
			if(isPowershell) {
				if(javaversionnumber == -2) {
					return "HasJavaFX_ForJava23_Windows11x64";
				}
				else {
					return "HasJavaFX_ForJava"+javaversionnumber+"_Windows11x64";
				}
			}
			else
			{
				if(javaversionnumber != -2) {
					return "ForJava"+javaversionnumber+"_"+main_class2;
				}
				else { // javaversionnumber == -2
					return "ForJava23_"+main_class2;
				}
			}				
		}
		else {
			return main_class2;
		}
	}								
}