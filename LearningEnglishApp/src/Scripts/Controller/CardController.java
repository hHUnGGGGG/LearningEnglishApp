package Scripts.Controller;

import com.google.gson.Gson;
import Scripts.Module.CardModule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardController {
    private ArrayList<CardModule> CardList = new ArrayList<CardModule>();

    public CardModule creatCard()
    {
        CardModule card = new CardModule();
        Scanner input = new Scanner(System.in);
        String word = input.nextLine();
        card.setWord(word);
        word = input.nextLine();
        card.setDefine(word);
        return card;
    }

    public void deleteCard()
    {

    }
    public void saveCard(CardModule card)
    {
        Gson gson = new Gson();
        String cardData = gson.toJson(card);
        String filePath = "src/Data/JsonWord.txt";
        ArrayList<String> Line = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Line.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Line.add(cardData);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(String word : Line) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jsonWordToCardModule() {// Chuyen doi json sang Object
        Gson gson = new Gson();
        String filePath = "src/Data/JsonWord.txt"; // Đường dẫn đến file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CardModule cardData = gson.fromJson(line,CardModule.class);
                CardList.add(cardData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CardModule> getCardList() {
        return CardList;
    }

    public void setCardList(ArrayList<CardModule> cardList) {
        CardList = cardList;
    }
}
