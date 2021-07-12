package unsw.loopmania.Items;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Equipable extends Item {

    private boolean isEquipped = false;
    
    public Equipable(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public boolean isEquipped(){
        return this.isEquipped;
    }

    public void setIsEquipped(boolean isEquipped){
        this.isEquipped = isEquipped;
    }
}
