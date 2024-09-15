package Scripts.Run;

import Scripts.Controller.CardController;
import Scripts.Module.CardModule;

public class Data {
    public static void main(String[] args)
    {
        CardController card = new CardController();
        CardModule word = card.creatCard();
        card.saveCard(word);
    }
}
