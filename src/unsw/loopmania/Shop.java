package unsw.loopmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Types.ItemType;
import static unsw.loopmania.Types.ItemType.*;


public class Shop {
    private List<ItemType> itemsForSale;
    private Character character;
    private List<Item> unequippedItems;
    
    public Shop(Character character, List<Item> unequippedItems) {
        this.character = character;
        this.itemsForSale = new ArrayList<ItemType>();
        this.unequippedItems = unequippedItems;

        while (itemsForSale.size() != 3) {
            int index = new Random().nextInt(ItemType.values().length);
            if (ItemType.values()[index] != GOLD && ItemType.values()[index] != THE_ONE_RING ) {
                this.itemsForSale.add(ItemType.values()[index]);
            }
        }
        
    }

    public int getShopBuyPrice(ItemType item) {
        switch(item){
            case SWORD:
                return 20;

            case STAKE:
                return 20;

            case STAFF:
                return 20;

            case SHIELD:
                return 20;

            case ARMOUR:
                return 20;

            case HELMET:
                return 20;
            
            case HEALTH_POTION:
                return 20;

            default:
                return -1;
        }
    }

    public int getShopSellPrice(ItemType item) {
        switch(item){
            case SWORD:
                return 5;

            case STAKE:
                return 5;
            case STAFF:
                return 5;

            case SHIELD:
                return 5;

            case ARMOUR:
                return 5;

            case HELMET:
                return 5;

            case HEALTH_POTION:
                return 5;

            case THE_ONE_RING:
                return 20;

            default:
                return -1;
        }
    }

    public ItemType getSaleItem(int index) {
        return this.itemsForSale.get(index - 1);
    }

    public boolean isItemBuyable(int index) {
        if (character.getGold() < getShopBuyPrice(this.itemsForSale.get(index - 1)) || 
            this.unequippedItems.size() >= 16) {
            return false;
        } else {
            return true;
        }
    }

}
