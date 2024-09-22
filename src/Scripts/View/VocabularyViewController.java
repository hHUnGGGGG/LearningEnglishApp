package Scripts.View;

import Scripts.Module.CardModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class VocabularyViewController {
    private int correctAnswers = 0;
    private CardModule card;
    private StackPane pane;
    private CardViewController cardViewController = new CardViewController();
    private ArrayList<String> defineList;
    private ArrayList<CardModule> cardList = new ArrayList<CardModule>();
    private ExitToMenuViewController exitToMenuViewController = new ExitToMenuViewController();

    @FXML
    private AnchorPane AP;

    @FXML
    private ArrayList<StackPane> stackPaneList;

    @FXML
    private Button exit;

    public void showLearnVocabulary() {
        cardViewController.LoadLibrary(0, 0);
        stackPaneList = cardViewController.getStackPaneList();
        defineList = cardViewController.getDefineList();
        cardList = cardViewController.getCardList();
        randomAnswer();
    }

    public void randomAnswer() {// tạo các đáp án A B C D ngẫu nhiên
        Random random = new Random();
        int i = random.nextInt(stackPaneList.size());
        pane = stackPaneList.get(i);
        pane.setLayoutX(376);
        pane.setLayoutY(0);
        pane.setDisable(true);
        card = cardList.get(i);

        Collections.shuffle(defineList);
        defineList.remove(card.getDefine());
        defineList.add(random.nextInt(4), card.getDefine());
        Button answerA = setButton(20, 359 , defineList.get(0));
        Button answerB = setButton(614, 359 , defineList.get(1));
        Button answerC = setButton(20, 492 , defineList.get(2));
        Button answerD = setButton(614, 492 , defineList.get(3));
        AP.getChildren().addAll(pane, answerA, answerB, answerC, answerD);
        stackPaneList.remove(pane);
        cardList.remove(card);
    }

    public Button setButton(int x , int y , String define) {
        Button button = new Button();
        button.setText(define);
        button.setOnAction(event -> CheckAnswer(event, button));
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(276);
        button.setPrefHeight(103);
        return button;
    }

    @FXML
    public void CheckAnswer(ActionEvent event, Button button) {// Kiểm tra câu trả lời của người dùng
        boolean a = card.getDefine().equals(button.getText());
        stackPaneList.remove(pane);
        cardList.remove(card);
        AP.getChildren().clear();

        if(stackPaneList.size() == 0) {
            if(a) correctAnswers++;
            PrintCorrectAnswer();
        }

        else {
            if (a) {
                correctAnswers++;
                randomAnswer();
            }

            if (!a) {
                randomAnswer();
            }
        }
    }

    @FXML
    public void PrintCorrectAnswer() {
        String s;
        int numberOfWords = defineList.size();
        if(numberOfWords == correctAnswers) s = " Đỉnh thật á";
        else s = " Sao gà vậy ! Có làm được không thế ?";

        Label label = new Label("Số câu trả lời đúng : " + correctAnswers + "/" + numberOfWords + s);
        label.setLayoutX(0);
        label.setLayoutY(0);
        label.setStyle("-fx-text-fill: red;-fx-font-size: 26px;");

        Button restart = new Button("Làm lại nàoooooo @@");
        restart.setOnAction(event -> {
            correctAnswers = 0;
            defineList.clear();
            AP.getChildren().clear();
            showLearnVocabulary();
        });
        restart.setLayoutX(450);
        restart.setLayoutY(300);

        Button learnAnother = new Button("Giỏi rồi học cái khác thui");
        learnAnother.setOnAction(event1 -> ExitToMenu(event1, learnAnother));
        learnAnother.setLayoutX(450);
        learnAnother.setLayoutY(350);

        AP.getChildren().addAll(label, restart, learnAnother);
    }

    @FXML
    void ExitToMenu(ActionEvent event, Button exit) {
        exitToMenuViewController.ReturnToMenu(exit);
    }
}
