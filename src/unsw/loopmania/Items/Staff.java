package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends OffensiveItems{

    private final int attack = 1;
    public static final double tranceChance = 0.2;
    public static final int tranceTurn = 3;

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public ItemType getItemType(){
        return ItemType.STAFF;
    }

    public int getAttack() {
        return this.attack;
    }

    public Item copyItem(int x, int y){
        return new Staff(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
    
}
