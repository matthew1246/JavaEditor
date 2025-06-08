import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import java.io.IOException;
public class Third extends Application {
	@Override
	public void start(Stage stage) {			
		try {
			GridPane gridpane= FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
			
			Scene scene = new Scene(gridpane,800,600);
			
			stage.setTitle("Hello FXML!");
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}		
}