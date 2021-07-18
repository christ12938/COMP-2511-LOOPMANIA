package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Types.ItemType;

/**
 * represents an item in the backend world
 */
public abstract class Item extends StaticEntity{

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract ItemType getItemType();
    
}
