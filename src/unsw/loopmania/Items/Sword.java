package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Sword extends OffensiveItems {

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    public ItemType getItemType(){
        return ItemType.SWORD;
    }
}
