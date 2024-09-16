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
    private int a = 0;// tọa độ x ở màn hình hiển thị của thẻ
    private int b = 0;// tọa độ y ở màn hình hiển thị của thẻ
    private static CardController cardController = new CardController();
    private static ArrayList<CardModule> cardList = new ArrayList<CardModule>();// mảng lưu trữ các đối tượng đc lấy từ file dữ liệu
    private static ArrayList<String> wordList = new ArrayList<String>();// mảng lưu trữ word của các đối tượng trên

    @FXML
    private ArrayList<StackPane> stackPaneList = new ArrayList<StackPane>();// mảng lưu trữ các stackPane hiển thị trên màn hình
    @FXML
    private AnchorPane AP;
    @FXML
    private Button library;// nút để show Library
    @FXML
    private Button creareACard;// nút tạo thẻ mới
    @FXML
    private Button selectCardButton;// nút lựa chọn thẻ

    static {
        cardList = cardController.jsonWordToCardModule();//chuyển dữ liệu thành đối tượng rồi lưu vào mảng
        for(CardModule c : cardList) {
            wordList.add(c.getWord());
        }
    }
    @FXML
    public void libraryCard(ActionEvent event) {// Bam nut de show library card
        AP.getChildren().remove(library);// xoa nut button
        LoadLibrary();
    }

    public void LoadLibrary() {
        for(CardModule c : cardList) {
            StackPane pane = addCard(c.getWord(), c.getDefine());
            AP.getChildren().add(pane);
            stackPaneList.add(pane);
        }
    }

    public Canvas createCanvas (String word, String define,ImageView card) { // tao canvas de ghi word va define
        Canvas canvas = new Canvas(148, 168);
        setTextInCanvas(canvas,word);
        canvas.setOnMouseClicked(mouseEvent -> flip(mouseEvent, card, canvas, word, define));
        return canvas;
    }

    public StackPane createPane() {// tao Pane chua card va word
        StackPane pane = new StackPane();
        pane.setPrefWidth(148);
        pane.setPrefHeight(168);
        pane.setLayoutX(a);
        pane.setLayoutY(b);
        return pane;
    }

    public ImageView createImageCard() {// tao image card
        ImageView card = new ImageView("Resources/Image/CardImage.png");
        card.setFitWidth(148);
        card.setFitHeight(168);
        card.setX(a);
        card.setY(b);
        return card;
    }

    public StackPane addCard(String word, String define) {// tao ra mot Card
        StackPane pane = createPane();

        ImageView card = createImageCard();

        Canvas canvas = createCanvas(word, define, card);

        CheckBox checkBox = new CheckBox();

        pane.getChildren().addAll(card, canvas);// dua image card va text vao pane(canvas sẽ nằm đè lên card)

        a += 148;//vi tri hien thi cua card tiep theo duoc tao ra
        if(a >= 1480) {
            a = 0;
            b += 168;
        }

        return pane;
    }

    @FXML
    public void flip(MouseEvent mouseEvent, ImageView card, Canvas canvas, String word, String define) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.15),card);// quay the trong 0,15s
        if(card.getRotate() ==0 ) { // quay card 180 do
            rotate.setFromAngle(0);
            rotate.setToAngle(180);
            setTextInCanvas(canvas, define);// nếu thẻ đang quay 180 độ thì ghi định nghĩa
        }
        else  {
            rotate.setFromAngle(180);// quay card lai vi tri ban dau
            rotate.setToAngle(0);
            setTextInCanvas(canvas, word);// nếu thẻ đang quay lại vị trí ban đầu thì ghi từ
        }
        rotate.setAxis(javafx.geometry.Point3D.ZERO.add(0, 1, 0)); // Quay quanh trục Y
        rotate.play();
    }

    public void setTextInCanvas(Canvas canvas, String text) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.clearRect(0, 0, 148, 168); // Xóa nội dung cũ trước khi ghi nội dung mới

        // Tính toán vị trí để căn giữa
        double wordWidth = gc.getFont().getSize() * text.length() * 0.51; // Ước tính chiều rộng văn bản
        double x = (canvas.getWidth() - wordWidth) / 2; // Tính tọa độ x
        double y = canvas.getHeight() / 2; // Tọa độ y

        // Vẽ văn bản lên Canvas
        gc.fillText(text, x, y);
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
        if(!wordAvailable) {// nếu thẻ đã tồn tại thì in ra thông báo thẻ đã tồn tại
            CardModule newCard = cardController.creatCard(word, define);// tạo một card và lưu trữ nó
            cardController.saveCard(newCard);//
            cardList.add(newCard);//
            StackPane pane = addCard(word, define);
            stackPaneList.add(pane);
            AP.getChildren().add(pane);//
            createCard.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thẻ đã có rồi mà !!!");
            alert.setHeaderText(null); // Không tiêu đề
            alert.setContentText("Word available");

            // Hiển thị hộp thoại
            alert.showAndWait();
        }
    }

    @FXML
    public void selectCard(ActionEvent event) {
        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        Button deleteCard = new Button("Delete Card");
        deleteCard.setLayoutX(182);
        deleteCard.setLayoutY(290);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(374);
        cancel.setLayoutY(290);

        for (StackPane pane : stackPaneList) {
            CheckBox checkBox = new CheckBox();
            checkBox.setTranslateX(30); // Đẩy CheckBox sang phải 30 pixel
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
        AP.getChildren().add(cancel);//thêm vào màn hình nút cancel
        cancel.setOnAction(event1 -> {//các hành động khi bấm cancel
            creareACard.setDisable(false);// cho phép người dùng sử dụng nút creat A Card
            selectCardButton.setDisable(false);// cho phép người dùng sử dụng nút select
            AP.getChildren().remove(cancel);// xóa nút cancel khỏi màn hình
            AP.getChildren().remove(deleteCard);
            for(int i = 0; i < stackPaneList.size(); i++)
                stackPaneList.get(i).getChildren().removeAll(rectangles.get(i), checkBoxes.get(i));
        });

        // Tạo nút Delete Card để xóa đi những card được chọn
        AP.getChildren().add(deleteCard);
        deleteCard.setOnAction(event2 -> {//các hành động khi bấm Delete Card
            ArrayList<Integer> cardModuleIndex = new ArrayList<Integer>();// mảng để lưu lại các chỉ số của các thẻ được chọn
            for(int i = 0; i < stackPaneList.size(); i++) {
                AP.getChildren().remove(stackPaneList.get(i));
                if (checkBoxes.get(i).isSelected()) {// nếu thẻ đã được chọn thì khi bấm xóa thẻ sẽ biến mất
                    cardModuleIndex.add(0,i);
                    cardController.deleteCard(cardList.get(i));// xóa dữ liệu của thẻ
                }
                stackPaneList.get(i).getChildren().removeAll(rectangles.get(i), checkBoxes.get(i));// xóa checkBox và tấm chắn làm mờ
            }
            System.out.println(cardList.size());
            for(int i : cardModuleIndex) {
                cardList.remove(i);// xóa các phần tử đã xóa khỏi cardList

            }
            stackPaneList.clear();// xóa toàn bộ stackPaneList
            a = 0;// reset vị trí x của thẻ về 0;
            LoadLibrary();// hiện lại Library

            creareACard.setDisable(false);// cho phép người dùng sử dụng nút creat A Card
            selectCardButton.setDisable(false);// cho phép người dùng sử dụng nút select
            AP.getChildren().remove(cancel);// xóa nút cancel khỏi màn hình
            AP.getChildren().remove(deleteCard);
        });

        creareACard.setDisable(true);
        selectCardButton.setDisable(true);
    }
}
