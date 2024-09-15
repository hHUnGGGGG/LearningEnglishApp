package Scripts.Run;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Tạo ListView
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");

        // Tạo CheckBox
        CheckBox checkBox = new CheckBox("Chọn tất cả");

        // Tạo lớp phủ để làm mờ
        Rectangle dimmingRectangle = new Rectangle();
        dimmingRectangle.setFill(Color.GRAY.deriveColor(0, 1, 1, 0.5)); // Màu xám với độ trong suốt 50%
        dimmingRectangle.widthProperty().bind(listView.widthProperty());
        dimmingRectangle.heightProperty().bind(listView.heightProperty());
        dimmingRectangle.setVisible(false); // Ẩn lớp phủ ban đầu
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(listView, dimmingRectangle, checkBox);

        checkBox.setOnAction(e -> {
            boolean selected = checkBox.isSelected();
            dimmingRectangle.setVisible(selected); // Hiển thị lớp phủ nếu CheckBox được chọn
            // Vô hiệu hóa StackPane nếu CheckBox được chọn
            stackPane.setDisable(selected);
        });

        // Chặn sự kiện chuột trên StackPane, ngoại trừ CheckBox
        stackPane.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (!checkBox.isPressed()) {
                event.consume(); // Ngăn chặn sự kiện nếu không phải là CheckBox
            }
        });

        // Tạo Scene
        Scene scene = new Scene(stackPane, 300, 200);

        primaryStage.setTitle("Disable StackPane Except CheckBox Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}