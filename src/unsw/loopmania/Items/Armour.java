package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Armour extends DefensiveItems{
    private int defense;

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.defense = 3;
    }

    public ItemType getItemType(){
        return ItemType.ARMOUR;
    }

    public int getDefense() {
        return this.defense; 
     }
    
}
