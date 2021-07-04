package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;


/**
 * represents an equipped or unequipped Helmet in the backend world
 */
public abstract class Consumables extends Item {
    // TODO = add more weapon/item types
    public Consumables(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

    }

}
