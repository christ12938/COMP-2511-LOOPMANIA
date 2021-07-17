package unsw.loopmania.Loaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.*;
import unsw.loopmania.Types.ItemType;

public class ItemLoader{

    /**
     * Creating and returning a random item object (Factory pattern)
     * @return A random item object
     */
    public static Item loadRandomItem(Pair<Integer, Integer> firstAvailableSlot){
        /* Initialize a list of items */
        List<Item> items = new ArrayList<>();
        SimpleIntegerProperty x = new SimpleIntegerProperty(firstAvailableSlot.getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(firstAvailableSlot.getValue1());
        /* Now add all types of items into the list */
        /* TODO: Destroy all non used objects? */
        items.add(new Sword(x, y));
        items.add(new Stake(x, y));
        items.add(new Staff(x, y));
        items.add(new Shield(x, y));
        items.add(new Armour(x, y));
        items.add(new Helmet(x, y));
        items.add(new Gold(x, y));  //??????
        items.add(new HealthPotion(x, y));
        /* Now randomly choose a item */
        return items.get(new Random().nextInt(items.size()));
    }

    public static Equipable loadEquipableItem(ItemType type, int nodeX, int nodeY){
        SimpleIntegerProperty x = new SimpleIntegerProperty(nodeX);
        SimpleIntegerProperty y = new SimpleIntegerProperty(nodeY);
        switch(type){
            case SWORD:
                return new Sword(x, y);
            case STAKE:
                return new Stake(x, y);
            case STAFF:
                return new Staff(x, y);
            case SHIELD:
                return new Shield(x, y);
            case ARMOUR:
                return new Armour(x, y);
            case HELMET:
                return new Helmet(x, y);
            default:
                return null;
        }
    }


}