package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Stake extends OffensiveItems{

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public ItemType getItemType(){
        return ItemType.STAKE;
    }
    
}
