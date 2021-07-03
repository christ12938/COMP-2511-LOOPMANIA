package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.CardType;

public class VillageCard extends Card{

    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public CardType getCardType(){
        return CardType.VILLAGE_CARD;
    }
}
