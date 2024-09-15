package Scripts.Run;

import Scripts.View.LibraryCardViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application{
    public void start(Stage primaryStage) throws Exception{
        try {
            LibraryCardViewController VC = new LibraryCardViewController();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Resources/fxml/Library.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("My JavaFX App");
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
