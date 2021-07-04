package unsw.loopmania.Loaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.*;

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
}