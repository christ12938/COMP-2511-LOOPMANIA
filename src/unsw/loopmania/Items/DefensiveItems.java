package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public abstract class DefensiveItems extends Equipable {
    
    public DefensiveItems(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    abstract public ItemType getItemType();
    abstract public int getDefense();
}
