package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped armour in the backend world
 */
public class Armour extends DefensiveItems{

    private final int defense = 3;

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public ItemType getItemType(){
        return ItemType.ARMOUR;
    }

    public int getDefense() {
        return this.defense; 
     }

    public Item copyItem(int x, int y){
        return new Armour(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }

}
