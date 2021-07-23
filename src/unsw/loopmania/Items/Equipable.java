package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents equipable items in the backend world
 */
public abstract class Equipable extends Item {
    
    public Equipable(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
}
