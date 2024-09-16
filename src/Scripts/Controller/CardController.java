package Scripts.Controller;

import com.google.gson.Gson;
import Scripts.Module.CardModule;

import java.util.ArrayList;

public class CardController {
    private FileDataController fileDataController = new FileDataController();

    public CardModule creatCard(String word, String define)
    {
        CardModule card = new CardModule();
        card.setWord(word);
        card.setDefine(define);
        return card;
    }

    public void deleteCard(CardModule card)
    {
        ArrayList<CardModule> cardModules = jsonWordToCardModule();
        ArrayList<String> Line;
        CardModule cardModule = new CardModule();

        for(CardModule c : cardModules) {
            if(c.getWord().equals(card.getWord())) {
                cardModule = c;
            }
        }
        cardModules.remove(cardModule);
        Line = CardModuleToJsonWord(cardModules);
        fileDataController.writeFile(Line);
    }

    public void saveCard(CardModule card)
    {
        Gson gson = new Gson();
        String cardData = gson.toJson(card);
        ArrayList<String> Line = new ArrayList<String>();
        fileDataController.readFile(Line);

        Line.add(cardData);

        fileDataController.writeFile(Line);
    }

    public ArrayList<CardModule> jsonWordToCardModule() {// Chuyen doi json sang Object
        ArrayList<CardModule> CardList = new ArrayList<CardModule>();
        ArrayList<String> Line = new ArrayList<String>();
        Gson gson = new Gson();
        fileDataController.readFile(Line);
        for(String line : Line) {
            CardModule cardData = gson.fromJson(line, CardModule.class);
            CardList.add(cardData);
        }
        return CardList;
    }

    public ArrayList<String> CardModuleToJsonWord(ArrayList<CardModule> cardList) {
        ArrayList<String> Line = new ArrayList<String>();
        Gson gson = new Gson();
        for(CardModule c : cardList) {
            String cardData = gson.toJson(c);
            Line.add(cardData);
        }
        return Line;
    }
}
