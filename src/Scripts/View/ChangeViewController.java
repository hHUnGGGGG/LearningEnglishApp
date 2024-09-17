package Scripts.View;

import Scripts.Run.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ChangeViewController {
    public void ReturnToMenu(Button exit) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Resources/fxml/Menu.fxml"));
            Parent root = loader.load();

            MenuViewController menu = loader.getController();
            Scene scene = new Scene(root, 900, 600);

            // Thay đổi scene
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.setTitle("Library Card");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
