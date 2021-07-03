package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.CardType;

public class CampfireCard extends Card{

    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public CardType getCardType(){
        return CardType.CAMPFIRE_CARD;
    }
}
