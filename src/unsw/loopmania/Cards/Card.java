package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Types.CardType;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {

    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract CardType getCardType();
}
