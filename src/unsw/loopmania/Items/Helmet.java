package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped helmet in the backend world
 */
public class Helmet extends DefensiveItems{
    
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public ItemType getItemType(){
        return ItemType.HELMET;
    }
    
    public Item copyItem(int x, int y) {
        return new Helmet(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}
