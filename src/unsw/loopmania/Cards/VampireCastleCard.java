package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.CardType;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {

    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }  
    
    public CardType getCardType(){
        return CardType.VAMPIRECASTLE_CARD;
    }
}
