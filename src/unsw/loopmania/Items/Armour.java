package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Armour extends DefensiveItems{

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public ItemType getItemType(){
        return ItemType.ARMOUR;
    }
    
}
