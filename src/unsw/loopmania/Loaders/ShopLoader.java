package unsw.loopmania.Loaders;

import java.util.EnumMap;

import org.javatuples.Pair;

import unsw.loopmania.Types.ItemType;

public class ShopLoader {

    /**
     * Define a map of item positions on shop
     */
    public static EnumMap<ItemType, Pair<Integer, Integer>> loadItemPositions(){
        EnumMap<ItemType, Pair<Integer, Integer>> itemPositions = new EnumMap<ItemType, Pair<Integer, Integer>>(ItemType.class);
        itemPositions.put(ItemType.SWORD, new Pair<Integer, Integer>(0, 0));
        itemPositions.put(ItemType.STAKE, new Pair<Integer, Integer>(1, 0));
        itemPositions.put(ItemType.STAFF, new Pair<Integer, Integer>(2, 0));
        itemPositions.put(ItemType.ARMOUR, new Pair<Integer, Integer>(0, 1));
        itemPositions.put(ItemType.SHIELD, new Pair<Integer, Integer>(1, 1));
        itemPositions.put(ItemType.HELMET, new Pair<Integer, Integer>(2, 1));
        itemPositions.put(ItemType.HEALTH_POTION, new Pair<Integer, Integer>(1, 2));
        return itemPositions;
    }

    /**
     * Set prices of items
     */
    public static EnumMap<ItemType, Integer> loadItemPrices(){
        EnumMap<ItemType, Integer> itemPrices = new EnumMap<ItemType, Integer>(ItemType.class);
        itemPrices.put(ItemType.SWORD, 10);
        itemPrices.put(ItemType.STAKE, 10);
        itemPrices.put(ItemType.STAFF, 10);
        itemPrices.put(ItemType.ARMOUR, 10);
        itemPrices.put(ItemType.SHIELD, 10);
        itemPrices.put(ItemType.HELMET, 10);
        itemPrices.put(ItemType.HEALTH_POTION, 10);
        return itemPrices;
    }
}
