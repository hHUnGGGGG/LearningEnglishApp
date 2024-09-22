package Scripts.View;

import Scripts.Controller.CardController;
import Scripts.Module.CardModule;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;

public class CardViewController {
    private CardController cardController = new CardController();
    private ArrayList<CardModule> cardList;// mảng lưu trữ các đối tượng đc lấy từ file dữ liệu
    private ArrayList<String> wordList = new ArrayList<String>();// mảng lưu trữ word của các đối tượng trên
    private ArrayList<String> defineList = new ArrayList<String>();
    @FXML
    private ArrayList<StackPane> stackPaneList = new ArrayList<StackPane>();// mảng lưu trữ các stackPane hiển thị trên màn hình

    public void LoadLibrary(int a, int b) {
        cardList = cardController.jsonWordToCardModule();
        for(CardModule c : cardList) {
            wordList.add(c.getWord());
            defineList.add(c.getDefine());
            StackPane pane = addCard(c.getWord(), c.getDefine(), a, b);
            stackPaneList.add(pane);
            a += 168;//vi tri hien thi cua card tiep theo duoc tao ra
            if(a >= 1176) {
                a = 0;
                b += 188;
            }
        }
    }

    public Canvas createCanvas (String word, String define, ImageView card) { // tao canvas de ghi word va define
        Canvas canvas = new Canvas(148, 168);
        setTextInCanvas(canvas,word);
        canvas.setOnMouseClicked(mouseEvent -> flip(card, canvas, word, define));
        return canvas;
    }

    public StackPane createPane(int a, int b) {// tao Pane chua card va word
        StackPane pane = new StackPane();
        pane.setPrefWidth(148);
        pane.setPrefHeight(168);
        pane.setLayoutX(a);
        pane.setLayoutY(b);
        return pane;
    }

    public ImageView createImageCard(int a, int b) {// tao image card
        ImageView card = new ImageView("Resources/Image/CardImage.png");
        card.setFitWidth(148);
        card.setFitHeight(168);
        card.setX(a);
        card.setY(b);
        return card;
    }

    public StackPane addCard(String word, String define, int a, int b) {// tao ra mot Card

        StackPane pane = createPane(a, b);

        ImageView card = createImageCard(a, b);

        Canvas canvas = createCanvas(word, define, card);

        CheckBox checkBox = new CheckBox();

        pane.getChildren().addAll(card, canvas);// dua image card va text vao pane(canvas sẽ nằm đè lên card)

        return pane;
    }

    @FXML
    public void flip(ImageView card, Canvas canvas, String word, String define) {
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
        Font font = Font.font("Arial", FontWeight.BOLD, 15); // Cỡ chữ 15 in đậm
        gc.setFont(font);

        gc.clearRect(0, 0, 148, 168); // Xóa nội dung cũ trước khi ghi nội dung mới

        // Tính toán vị trí để căn giữa
        double wordWidth = gc.getFont().getSize() * text.length() * 0.51; // Ước tính chiều rộng văn bản
        double x = (canvas.getWidth() - wordWidth) / 2; // Tính tọa độ x
        double y = canvas.getHeight() / 2; // Tọa độ y

        // Vẽ văn bản lên Canvas
        gc.fillText(text, x, y);
    }

    public ArrayList<CardModule> getCardList() {
        return cardList;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    public ArrayList<StackPane> getStackPaneList() {
        return stackPaneList;
    }

    public ArrayList<String> getDefineList() {
        return defineList;
    }
}
