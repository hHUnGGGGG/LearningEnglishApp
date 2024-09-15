package Scripts.View;

import Scripts.Controller.CardController;
import Scripts.Module.CardModule;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class LibraryCardViewController {
    private int a = 0;
    private int b = 0;
    private CardController cardController = new CardController();

    @FXML
    private AnchorPane AP;
    @FXML
    private Button library;
    @FXML
    private Button creareACard;

    @FXML
    public void libraryCard(ActionEvent event) {// Bam nut de show library card
        cardController.jsonWordToCardModule();
        AP.getChildren().remove(library);// xoa nut button
        for(CardModule c : cardController.getCardList()) {
            AP.getChildren().add(addCard(c.getWord(), c.getDefine()));
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

        okButton.setOnAction(e -> {
            String word = textWord.getText();
            String define = textDefine.getText();

            CardModule newCard = new CardModule();// tạo một card và lưu trữ nó
            newCard.setWord(word);//
            newCard.setDefine(define);//
            cardController.saveCard(newCard);//
            cardController.getCardList().add(newCard);//

            boolean haveButton = AP.getChildren().contains(library);
            if(!haveButton)// Kiểm tra xem nút Library đã biến mất chưa nếu Library đã hiển thị thì thêm thẻ mới này vào màn hình
                AP.getChildren().add(addCard(word, define));//
            createCard.close();
        });

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
        ImageView card = new ImageView("Resources/Image/Test.png");
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

        pane.getChildren().addAll(card,canvas);// dua image card va text vao pane(canvas sẽ nằm đè lên card)

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

}
