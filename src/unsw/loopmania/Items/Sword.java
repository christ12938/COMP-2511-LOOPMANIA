package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends OffensiveItems {

    private final int attack = 5;
    
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    public ItemType getItemType(){
        return ItemType.SWORD;
    }

    public int getAttack() {
        return this.attack;
    }

    public Item copyItem(int x, int y){
        return new Sword(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}
