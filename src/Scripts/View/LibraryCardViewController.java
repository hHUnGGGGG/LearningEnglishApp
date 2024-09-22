package Scripts.View;

import Scripts.Controller.CardController;
import Scripts.Module.CardModule;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class LibraryCardViewController {
    private CardController cardController = new CardController();
    private CardViewController cardViewController = new CardViewController();
    private ArrayList<CardModule> cardList;// mảng lưu trữ các đối tượng đc lấy từ file dữ liệu
    private ArrayList<String> wordList;// mảng lưu trữ word của các đối tượng trên
    private ExitToMenuViewController exitToMenuViewController = new ExitToMenuViewController();

    @FXML
    private ArrayList<StackPane> stackPaneList;// mảng lưu trữ các stackPane hiển thị trên màn hình
    @FXML
    private AnchorPane AP;
    @FXML
    private AnchorPane AP1;
    @FXML
    private Button createACard;// nút tạo thẻ mới
    @FXML
    private Button selectCardButton;// nút lựa chọn thẻ
    @FXML
    private Button exit;

    public void showLibrary() {
        cardViewController.LoadLibrary(0, 0);
        cardList = cardViewController.getCardList();
        wordList = cardViewController.getWordList();
        stackPaneList = cardViewController.getStackPaneList();
        AP.setPrefHeight(stackPaneList.size() / 7 * 188 + 1000);
        for(StackPane pane : stackPaneList) {
            AP.getChildren().add(pane);
        }
    }

    @FXML
    public void creatACard(ActionEvent event) { //tao ra mot card moi
        Stage createCard = new Stage();
        createCard.initModality(Modality.APPLICATION_MODAL);// bắt người dùng phải bấm ok hoặc cancel để trở về
        createCard.setTitle("Input New Word");

        Label labelWord = new Label("Word:");
        Label labelDefine = new Label("Define:");

        TextField textWord = new TextField();// Tạo ra 2 textField để ghi word và define
        TextField textDefine = new TextField();//

        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");

        okButton.setOnAction(e -> inPutWord(textWord.getText(), textDefine.getText(), createCard));

        cancelButton.setOnAction(e -> createCard.close());

        GridPane grid = new GridPane();// sắp xếp các thành phần theo hàng và cột
        grid.add(labelWord, 0 , 0);
        grid.add(textWord, 1, 0);
        grid.add(labelDefine, 0, 1);
        grid.add(textDefine, 1, 1);
        grid.add(okButton, 0, 2);
        grid.add(cancelButton, 1, 2);

        Scene createCardScene = new Scene(grid, 300, 150);
        createCard.setScene(createCardScene);
        createCard.showAndWait();
    }

    public void inPutWord(String word, String define, Stage createCard) {
        boolean wordAvailable = false;
        for(String s : wordList) {
            if(s.equals(word)) {
                wordAvailable = true;
            }
        }
        if(!wordAvailable && word.matches("[A-Za-z]+")) {// nếu thẻ đã tồn tại thì in ra thông báo thẻ đã tồn tại
            CardModule newCard = cardController.creatCard(word, define);// tạo một card và lưu trữ nó
            cardController.saveCard(newCard);//
            cardList.add(newCard);//
            int l = stackPaneList.size();
            StackPane pane = cardViewController.addCard(word, define, (l % 7) * 168, (l/7) * 188);
            stackPaneList.add(pane);
            AP.setPrefHeight(stackPaneList.size() / 7 * 188 + 1000);
            AP.getChildren().add(pane);//
            createCard.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thẻ đã có rồi hoặc từ bạn nhập vào không phải tiếng anh :D");
            alert.setHeaderText(null); // Không tiêu đề
            alert.setContentText("Can't Create Card");

            // Hiển thị hộp thoại
            alert.showAndWait();
        }
    }

    @FXML
    public void selectCard(ActionEvent event) {
        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        Button deleteCard = new Button("Delete Card");
        deleteCard.setLayoutX(0);
        deleteCard.setLayoutY(620);
        deleteCard.setPrefWidth(100);
        deleteCard.setPrefHeight(50);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(180);
        cancel.setLayoutY(620);
        cancel.setPrefWidth(100);
        cancel.setPrefHeight(50);

        for (StackPane pane : stackPaneList) {
            CheckBox checkBox = new CheckBox();
            checkBox.setTranslateX(50); // Đẩy CheckBox sang phải 30 pixel
            checkBox.setTranslateY(-74);  // Đẩy CheckBox lên trên 74 pixel

            Rectangle dimmingRectangle = new Rectangle();//lớp phủ màu Gray
            dimmingRectangle.setFill(Color.GRAY.deriveColor(0, 1, 1, 0.5)); // Màu xám với độ trong suốt 50%
            dimmingRectangle.setWidth(148);
            dimmingRectangle.setHeight(168);
            dimmingRectangle.setVisible(false); // Ẩn lớp phủ ban đầu
            checkBox.setOnAction(e -> {
                boolean selected = checkBox.isSelected();
                dimmingRectangle.setVisible(selected); // Hiển thị lớp phủ nếu CheckBox được chọn
            });

            checkBoxes.add(checkBox);
            rectangles.add(dimmingRectangle);
            pane.getChildren().addAll(dimmingRectangle, checkBox);
            pane.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event2 -> {
                if (!checkBox.isPressed()) {
                    event2.consume(); // Ngăn chặn sự kiện nếu không phải là CheckBox
                }
            });
        }

        //tạo nút hủy thao tác select
        AP1.getChildren().add(cancel);//thêm vào màn hình nút cancel
        cancel.setOnAction(event1 -> {//các hành động khi bấm cancel
            AP.getChildren().clear();
            AP1.getChildren().removeAll(cancel, deleteCard);

            stackPaneList.clear();// xóa toàn bộ stackPaneList
            showLibrary();// hiện lại Library

            createACard.setDisable(false);// cho phép người dùng sử dụng nút creat A Card
            selectCardButton.setDisable(false);// cho phép người dùng sử dụng nút select
            exit.setDisable(false);
        });

        // Tạo nút Delete Card để xóa đi những card được chọn
        AP1.getChildren().add(deleteCard);
        deleteCard.setOnAction(event2 -> {//các hành động khi bấm Delete Card
            ArrayList<Integer> cardModuleIndex = new ArrayList<Integer>();// mảng để lưu lại các chỉ số của các thẻ được chọn
            AP1.getChildren().removeAll(cancel, deleteCard);
            for(int i = 0; i < stackPaneList.size(); i++) {
                AP.getChildren().clear();
                if (checkBoxes.get(i).isSelected()) {// nếu thẻ đã được chọn thì khi bấm xóa thẻ sẽ biến mất
                    cardModuleIndex.add(0,i);
                    cardController.deleteCard(cardList.get(i));// xóa dữ liệu của thẻ
                }
            }
            for(int i : cardModuleIndex) {
                cardList.remove(i);// xóa các phần tử đã xóa khỏi cardList
            }
            stackPaneList.clear();// xóa toàn bộ stackPaneList
            showLibrary();// hiện lại Library

            createACard.setDisable(false);// cho phép người dùng sử dụng nút creat A Card
            selectCardButton.setDisable(false);// cho phép người dùng sử dụng nút select
            exit.setDisable(false);
        });

        createACard.setDisable(true);
        selectCardButton.setDisable(true);
        exit.setDisable(true);
    }
    @FXML
    void ExitToMenu(ActionEvent event) {
        exitToMenuViewController.ReturnToMenu(exit);
    }
}
