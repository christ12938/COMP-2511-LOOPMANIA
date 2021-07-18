package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends OffensiveItems {
    private int attack;
    
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = 5;
    }    

    public ItemType getItemType(){
        return ItemType.SWORD;
    }

    public int getAttack() {
        return this.attack;
    }
}
