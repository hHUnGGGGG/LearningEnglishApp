package Scripts.Run;

import Scripts.View.LibraryCardViewController;
import Scripts.View.MenuViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application{
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Resources/fxml/Menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);
            primaryStage.setTitle("Learning English App");
            primaryStage.setScene(scene);
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
