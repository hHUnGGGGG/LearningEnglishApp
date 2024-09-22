package Scripts.View;

import Scripts.Run.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ExitToMenuViewController {
    public void ReturnToMenu(Button exit) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Resources/fxml/Menu.fxml"));
            Parent root = loader.load();

            MenuViewController menu = loader.getController();
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/Resources/CSS/Button.css").toExternalForm());

            // Thay đổi scene
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.setTitle("Learning English App");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
