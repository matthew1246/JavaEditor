import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
public class SimpleBrowser extends Application {
    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        webView.getEngine().load("https://www.chatgpt.com");

        Scene scene = new Scene(webView, 800, 600);
        stage.setTitle("My Java Browser");
        stage.setScene(scene);
        stage.show();
    }

    /*
    ** Don't run from here. Run from Main.class instead of SimpleBrowser.class
    ** Running from SimpleBrowser bypasses having to add --module-path and --add-modules
    ** to java.exe
    */ 
    public static void main(String[] args) {
        launch();
    }
}
