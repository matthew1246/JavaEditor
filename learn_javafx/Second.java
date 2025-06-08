import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
public class Second extends Application {
	@Override
	public void start(Stage stage) {
		GridPane gridpane=new GridPane();
		gridpane.setAlignment(Pos.CENTER);
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		gridpane.setPadding(new Insets(10,10,10,10));
		
		Text scenetitle=new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
		gridpane.add(scenetitle,0,0,2,1);
		
		Label username = new Label("User name:");
		gridpane.add(username,0,1);
		
		TextField textfield=new TextField();
		gridpane.add(textfield,1,1);
		
		gridpane.add(new Label("Password:"),0,2);
		
		gridpane.add(new PasswordField(),1,2);
		
		Button button=new Button("Sign in");
		HBox horizontalbox=new HBox(10);
		horizontalbox.setAlignment(Pos.BOTTOM_RIGHT);
		horizontalbox.getChildren().add(button);
		gridpane.add(horizontalbox,1,3);
		
		Text output = new Text();
		gridpane.add(output,1,4);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionevent) {
				output.setFill(Color.FIREBRICK);
				output.setText("Button pressed!");
			}
		});
		
		gridpane.setGridLinesVisible(true);
		
		Scene scene = new Scene(gridpane,800,600);
		stage.setScene(scene);
		
		stage.show();
	}
}

		
		