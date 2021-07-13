package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class TheOneRing extends RareItem {

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    public ItemType getItemType(){
        return ItemType.THE_ONE_RING;
    }
}