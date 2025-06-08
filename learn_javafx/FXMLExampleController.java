import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
 
public class FXMLExampleController {
    @FXML private Text actiontarget;
    public boolean isVisible = false;
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        if(!isVisible) {	
        		actiontarget.setText("Kiss my ass!");
        		isVisible = true;
        	}
        	else {
        		actiontarget.setText("");
        		isVisible = false;
        	}
    }

}
