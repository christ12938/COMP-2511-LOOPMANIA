package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;


/**
 * represents consumables in the backend world
 */
public abstract class Consumables extends Item {
    
    public Consumables(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

}
