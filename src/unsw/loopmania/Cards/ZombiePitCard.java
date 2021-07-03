package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.CardType;

public class ZombiePitCard extends Card{

    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public CardType getCardType(){
        return CardType.ZOMBIEPIT_CARD;
    }
}
