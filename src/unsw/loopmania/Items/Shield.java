package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped shield in the backend world
 */
public class Shield extends DefensiveItems{

    private final int defense = 3;

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public ItemType getItemType(){
        return ItemType.SHIELD;
    }

    public int getDefense() {
        return this.defense; 
     }

    public Item copyItem(int x, int y) {
        return new Shield(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}
