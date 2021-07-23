package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents a TheOneRing in the backend world
 */
public class TheOneRing extends RareItem {

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    public ItemType getItemType(){
        return ItemType.THE_ONE_RING;
    }

    public Item copyItem(int x, int y){
        return new TheOneRing(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}