package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class HealthPotion extends Consumables {

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        //TODO Auto-generated constructor stub
    }
    
    public ItemType getItemType(){
        return ItemType.HEALTH_POTION;
    }
}
