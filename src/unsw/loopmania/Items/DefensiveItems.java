package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped defensive items in the backend world
 */
public abstract class DefensiveItems extends Equipable {
    
    public DefensiveItems(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    abstract public int getDefense();
}
