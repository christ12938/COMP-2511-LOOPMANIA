package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Shield extends DefensiveItems{
    private int defense;
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.defense = 3;
    }
    
    public ItemType getItemType(){
        return ItemType.SHIELD;
    }

    public int getDefense() {
        return this.defense; 
     }
}
