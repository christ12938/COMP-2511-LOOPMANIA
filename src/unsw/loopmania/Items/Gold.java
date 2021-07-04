package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Gold extends Item{
    private int total; 

    public Gold(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.total = 0;
    }

    public int getGold() {
        return this.total;
    }
    
    public ItemType getItemType(){
        return ItemType.GOLD;
    }
    
}
