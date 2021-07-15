package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Helmet extends DefensiveItems{
    private int defense;

    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.defense = 3;
    }
    
    public ItemType getItemType(){
        return ItemType.HELMET;
    }

    public int getDefense() {
       return this.defense; 
    }
}
