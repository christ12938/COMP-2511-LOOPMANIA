package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends OffensiveItems{
    private int attack;

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = 1;
    }

    public ItemType getItemType(){
        return ItemType.STAFF;
    }

    public int getAttack() {
        return this.attack;
    }
    
}
