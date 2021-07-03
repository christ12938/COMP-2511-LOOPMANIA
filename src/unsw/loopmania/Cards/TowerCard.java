package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.CardType;

public class TowerCard extends Card{

    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public CardType getCardType(){
        return CardType.TOWER_CARD;
    }
}
