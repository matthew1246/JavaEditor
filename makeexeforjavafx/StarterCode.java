import javax.swing.JOptionPane;
public class StarterCode {
	private int hasCode=0;
	public StarterCode() {
		String[] options2 = {"Yes","No"};
		hasCode = JOptionPane.showOptionDialog(null,"Do you want to add a main method to new tab?","You want starter code?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options2,options2[1]);
	}
	public String getNormalJava() {
		if(hasCode==JOptionPane.YES_OPTION) {
			String[] options2 = {"Normal Java","JavaFX"};
			int result = JOptionPane.showOptionDialog(null,null,"Do you default java starter code or JavaFX?",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options2,options2[0]);
			if(result == 0) {
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
				String mainFX=
				"""
				import javafx.application.Application;
				import javafx.stage.Stage;
				import javafx.scene.control.Label;
				import javafx.scene.Scene;
				public class Main extends Application {
					@Override
					public void start(Stage stage) {
						Scene scene = new Scene(new Label("Hello World!"),800,600);
						stage.setScene(scene);
						stage.show();
					}
				}
				""";
				return mainFX;
			}
		}
		else {
			return "";
		}
	}
}