package unsw.loopmania;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import unsw.loopmania.Difficulties.BeserkerMode;
import unsw.loopmania.Difficulties.DifficultyBehaviour;
import unsw.loopmania.Difficulties.StandardMode;
import unsw.loopmania.Difficulties.SurvivalMode;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Loaders.ItemLoader;
import unsw.loopmania.Loaders.ShopLoader;
import unsw.loopmania.Types.ItemType;

public class LoopManiaShop {
    
    private LoopManiaWorld world;
    private LoopManiaShopController controller;
    private LoopManiaWorldController parentController;
    
    private EnumMap<ItemType, Integer> itemBuyPrices;
    private EnumMap<ItemType, Integer> itemSellPrices;

    private DifficultyBehaviour difficultyBehaviour;

    private List<Item> boughtItems;

    private final String buySuccessMessage = new String("Buy Success");
    private final String sellSuccessMessage = new String("Sell Success");

    public LoopManiaShop(LoopManiaWorld world, LoopManiaWorldController parentController, LoopManiaShopController controller){
        this.world = world;
        this.parentController = parentController;
        this.controller = controller;
        boughtItems = new ArrayList<>();
        itemBuyPrices = ShopLoader.loadItemBuyPrices();
        itemSellPrices = ShopLoader.loadItemSellPrices();
        switch(parentController.getDifficulty()){
            case STANDARD:
            case CONFUSING:
                difficultyBehaviour = new StandardMode();
                break;
            case SURVIVAL:
                difficultyBehaviour = new SurvivalMode();
                break;
            case BESERKER:
                difficultyBehaviour = new BeserkerMode();
                break;
        }
    }

    public String buyItem(ItemType type){
        String errorMessage = buySuccessMessage;
        if(world.getCharacter().getGold() < itemBuyPrices.get(type)){
            errorMessage = new String("Insufficient Gold");
        }else if(world.isUnequippedInventoryFull()){
            errorMessage = new String("Inventory is Full");
        }else{
            if(difficultyBehaviour.canBuy(boughtItems)){
                world.getCharacter().minusGold(itemBuyPrices.get(type));
                controller.updateShopGold();
                Item boughtItem = ItemLoader.loadBoughtItem(type, world.getFirstAvailableSlotForItem().getValue0(), world.getFirstAvailableSlotForItem().getValue1());
                boughtItems.add(boughtItem);
                world.addUnequippedItem(boughtItem);
                controller.addInventoryItem(boughtItem);
                parentController.onLoadUnequippedItem(boughtItem);
            }else{
                errorMessage = difficultyBehaviour.getErrorMessage();
            }
        }
        return errorMessage;
    }

    public String sellItem(ItemType type){
        String errorMessage = sellSuccessMessage;
        if(hasItme(type)){
            world.getCharacter().addGold(itemSellPrices.get(type));
            controller.updateShopGold();
            Item removedItem = removeUnequippedItemByType(type);
            if(removedItem != null) removedItem.destroy();
        }else{
            errorMessage = new String("No Such Item");
        }
        return errorMessage;
    }

    private Item removeUnequippedItemByType(ItemType type){
        Item temp = null;
        for(Item item : world.getUnequippedInventoryItems()){
            if(item.getItemType() == type){
                temp = item;
                break;
            }
        }
        if(temp != null){
            world.getUnequippedInventoryItems().remove(temp);
        }
        return temp;
    }

    public String getItemBuyPrice(ItemType type){
        return Integer.toString(itemBuyPrices.get(type));
    }

    public String getItemSellPrice(ItemType type){
        return Integer.toString(itemSellPrices.get(type));
    }

    private boolean hasItme(ItemType type){
        for(Item item : world.getUnequippedInventoryItems()){
            if(item.getItemType() == type) return true;
        }
        return false;
    }

    public String getBuySuccessMessage(){
        return buySuccessMessage;
    }

    public String getSellSuccessMessage(){
        return sellSuccessMessage;
    }
}
