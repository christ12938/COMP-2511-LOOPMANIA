package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class OffensiveItems extends Equipable {
    
    public OffensiveItems(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
