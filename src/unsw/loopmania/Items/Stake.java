package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped stake in the backend world
 */
public class Stake extends OffensiveItems{
   
    private final int attack = 3;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public ItemType getItemType(){
        return ItemType.STAKE;
    }

    public int getAttack() {
        return this.attack;
    }

    public Item copyItem(int x, int y){
        return new Stake(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}
