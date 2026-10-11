import javax.swing.JOptionPane;
public class StarterCode {
	private int hasCode=0;
	public StarterCode() {
		String[] options2 = {"Yes","No"};
		hasCode = JOptionPane.showOptionDialog(null,"Do you want to add a main method to new tab?","You want starter code?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options2,options2[1]);
	}
	public String getNormalJava() {
		if(hasCode==JOptionPane.YES_OPTION) {
			String main=	
			"""
			public class Main {
				public static void main(String[] args) {
					System.out.println("Hello World!");
				}
			}
			""";
			return main;
		}
		else {
			return "";
		}
	}
}
