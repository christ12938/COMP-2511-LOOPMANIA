package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents gold in the backend world
 */
public class Gold extends Item{

    public Gold(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public ItemType getItemType(){
        return ItemType.GOLD;
    }
    
}
