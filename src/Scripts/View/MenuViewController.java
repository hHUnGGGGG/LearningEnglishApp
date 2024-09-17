package Scripts.View;

import Scripts.Run.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuViewController {
    @FXML
    private Button library;

    @FXML
    void showLibrary(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Resources/fxml/Library.fxml"));
            Parent root = loader.load();

            LibraryCardViewController libraryView = loader.getController();
            libraryView.LoadLibrary();
            Scene scene = new Scene(root, 900, 600);

            // Thay đổi scene
            Stage stage = (Stage) library.getScene().getWindow();
            stage.setTitle("Library Card");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}