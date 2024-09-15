package Scripts.Run;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Tạo Label
        Label label = new Label("Sau đó:");

        // Tạo TextField
        TextField textField = new TextField();

        // Sử dụng HBox để bố trí Label và TextField
        HBox hbox = new HBox(10, label, textField); // 10 là khoảng cách giữa các thành phần

        // Tạo Scene
        Scene scene = new Scene(hbox, 300, 100);
        primaryStage.setTitle("Label and TextField Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}