package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Equipable extends Item {
    
    public Equipable(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
