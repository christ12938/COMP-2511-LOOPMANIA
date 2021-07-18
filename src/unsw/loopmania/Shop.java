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

        for (int index = 0; index <= 6; index++) {
            if (ItemType.values()[index] != GOLD && ItemType.values()[index] != THE_ONE_RING ) {
                this.itemsForSale.add(ItemType.values()[index]);
            }
        }
        
    }

    /**
    * returns the buy buy price of an item
    * @param item the item whose buy price is returned
    * @return the buy price of the item
    */
    public int getShopBuyPrice(ItemType item) {
        switch(item){
            case SWORD:
                return 10;

            case STAKE:
                return 10;

            case STAFF:
                return 10;

            case SHIELD:
                return 10;

            case ARMOUR:
                return 10;

            case HELMET:
                return 10;
            
            case HEALTH_POTION:
                return 10;

            default:
                return -1;
        }
    }

    /**
    * returns the sell price of an item
    * @param item the item whose sell price is returned
    * @return the sell price of the item
    */
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

    /**
    * determines if an item is able to be bought from the shop
    * @param item the item being bought
    * @return returns true if item can be otherwise return false
    */
    public boolean isItemBuyable(ItemType item) {
        if (character.getGold() < getShopBuyPrice(item) || 
            this.unequippedItems.size() >= 16) {
            return false;
        } else {
            return true;
        }
    }

}
