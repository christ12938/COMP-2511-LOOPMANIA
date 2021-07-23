package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents health potions in the backend world
 */
public class HealthPotion extends Consumables {

    public static final int healingHealth = 5;
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public ItemType getItemType(){
        return ItemType.HEALTH_POTION;
    }

    public Item copyItem(int x, int y) {
        return new HealthPotion(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}
