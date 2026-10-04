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
	boolean isPowershell = false;
	public void isPowershell() {
		isPowershell = true;
	}  
	public String getJarFileName(int javaversionnumber) {
		if(makeAllVersionsJar) {
			if(isPowershell) {
				if(javaversionnumber != 23 && javaversionnumber != -2) {
					return "ForJava"+javaversionnumber+"_"+main_class2+"Withai";
				}
				else {
					return main_class2+"Withai";
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
			if(javaversionnumber != -2) {
				return "ForJava"+javaversionnumber+"_"+main_class2;
			}
			else { // javaversionnumber == -2
				return "ForJava23_"+main_class2;
			}		
		}
		else {
			return main_class2;
		}
	}								
}