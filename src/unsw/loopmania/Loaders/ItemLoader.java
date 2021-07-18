package unsw.loopmania.Loaders;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.*;
import unsw.loopmania.Types.ItemType;

public class ItemLoader{

    /* Single thread, so no need to worry */
    public static Random random = new Random();
    /**
     * Creating and returning a random item object (Factory pattern)
     * @return A random item object
     */
    public static Item loadRandomItem(Pair<Integer, Integer> firstAvailableSlot, List<ItemType> rareItemsAvailable){
        /* Initialize a list of items */
        List<Item> normalItems = new ArrayList<>();
        List<Item> rareItems = new ArrayList<>();
        SimpleIntegerProperty x = new SimpleIntegerProperty(firstAvailableSlot.getValue0());
        SimpleIntegerProperty y = new SimpleIntegerProperty(firstAvailableSlot.getValue1());
        /* Add rare items */
        for(ItemType type : rareItemsAvailable){
            switch(type){
                case THE_ONE_RING:
                    rareItems.add(new TheOneRing(x, y));
                    break;
                default:
                    continue;
            }
        }
        /* Now add all types of items into the list */
        /* TODO: Destroy all non used objects? */
        normalItems.add(new Sword(x, y));
        normalItems.add(new Stake(x, y));
        normalItems.add(new Staff(x, y));
        normalItems.add(new Shield(x, y));
        normalItems.add(new Armour(x, y));
        normalItems.add(new Helmet(x, y));
        normalItems.add(new Gold(x, y));
        normalItems.add(new HealthPotion(x, y));
        /* Now randomly choose a item */
        /* If chance < 5% get random item */
        if(rareItems.size() == 0 || random.nextDouble() >= 0.05) return normalItems.get(random.nextInt(normalItems.size()));
        return rareItems.get(random.nextInt(rareItems.size()));
    }

    public static Item loadBoughtItem(ItemType type, int nodeX, int nodeY){
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
            case HEALTH_POTION:
                return new HealthPotion(x, y);
            default:
                return null;
        }
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

    public static List<Item> loadShopItems(EnumMap<ItemType, Pair<Integer, Integer>> itemPositions){
        /**
         * Define shop items position manually ???
         */
        List<Item> result = new ArrayList<>();

        SimpleIntegerProperty swordPosX = new SimpleIntegerProperty(itemPositions.get(ItemType.SWORD).getValue0());
        SimpleIntegerProperty swordPosY = new SimpleIntegerProperty(itemPositions.get(ItemType.SWORD).getValue1());
        result.add(new Sword(swordPosX, swordPosY));

        SimpleIntegerProperty stakePosX = new SimpleIntegerProperty(itemPositions.get(ItemType.STAKE).getValue0());
        SimpleIntegerProperty stakePosY = new SimpleIntegerProperty(itemPositions.get(ItemType.STAKE).getValue1());
        result.add(new Stake(stakePosX, stakePosY));

        SimpleIntegerProperty staffPosX = new SimpleIntegerProperty(itemPositions.get(ItemType.STAFF).getValue0());
        SimpleIntegerProperty staffPosY = new SimpleIntegerProperty(itemPositions.get(ItemType.STAFF).getValue1());
        result.add(new Staff(staffPosX, staffPosY));

        SimpleIntegerProperty armourPosX = new SimpleIntegerProperty(itemPositions.get(ItemType.ARMOUR).getValue0());
        SimpleIntegerProperty armourPosY = new SimpleIntegerProperty(itemPositions.get(ItemType.ARMOUR).getValue1());
        result.add(new Armour(armourPosX, armourPosY));

        SimpleIntegerProperty shieldPosX = new SimpleIntegerProperty(itemPositions.get(ItemType.SHIELD).getValue0());
        SimpleIntegerProperty shieldPosY = new SimpleIntegerProperty(itemPositions.get(ItemType.SHIELD).getValue1());
        result.add(new Shield(shieldPosX, shieldPosY));

        SimpleIntegerProperty helmetPosX = new SimpleIntegerProperty(itemPositions.get(ItemType.HELMET).getValue0());
        SimpleIntegerProperty helmetPosY = new SimpleIntegerProperty(itemPositions.get(ItemType.HELMET).getValue1());
        result.add(new Helmet(helmetPosX, helmetPosY));

        SimpleIntegerProperty healthPotionPosX = new SimpleIntegerProperty(itemPositions.get(ItemType.HEALTH_POTION).getValue0());
        SimpleIntegerProperty healthPotionPosY = new SimpleIntegerProperty(itemPositions.get(ItemType.HEALTH_POTION).getValue1());
        result.add(new HealthPotion(healthPotionPosX, healthPotionPosY));

        return result;
    }

}