package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped defensive items in the backend world
 */
public abstract class DefensiveItems extends Equipable {
    
    public DefensiveItems(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getDefense(){
        return getItemType().getDefense();
    }
    
    abstract public ItemType getItemType();

}
