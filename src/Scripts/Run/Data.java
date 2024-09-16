package Scripts.Run;

import Scripts.Controller.CardController;
import Scripts.Module.CardModule;

import java.util.ArrayList;

public class Data {
    public static void main(String[] args)
    {
        CardController card = new CardController();
        ArrayList<CardModule> cardModules = card.jsonWordToCardModule();
        System.out.println(cardModules.get(0).getWord());
        card.deleteCard(cardModules.get(0));
    }
}
