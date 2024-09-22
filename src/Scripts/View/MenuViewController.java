package Scripts.View;

import Scripts.Run.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuViewController {
    @FXML
    private Button library;

    @FXML
    private Button vocabulary;

    @FXML
    void showLibrary(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Resources/fxml/Library.fxml"));
            Parent root = loader.load();

            LibraryCardViewController libraryView = loader.getController();
            libraryView.showLibrary();
            Scene scene = new Scene(root, 1920, 1080);

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

    public void showVocabularyView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Resources/fxml/VocabularyView.fxml"));
            Parent root = loader.load();
            root.setStyle("-fx-background-color: #FFC700;");

            VocabularyViewController vocabularyView = loader.getController();
            vocabularyView.showLearnVocabulary();
            Scene scene = new Scene(root, 1920, 1080);

            // Thay đổi scene
            Stage stage = (Stage) vocabulary.getScene().getWindow();
            stage.setTitle("Learn Vocabulary");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}