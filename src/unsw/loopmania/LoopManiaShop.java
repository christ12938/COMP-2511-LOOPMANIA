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
    
    private EnumMap<ItemType, Integer> itemPrices;

    private DifficultyBehaviour difficultyBehaviour;

    private List<Item> boughtItems;

    private final String successMessage = new String("Buy Success");

    public LoopManiaShop(LoopManiaWorld world, LoopManiaWorldController parentController, LoopManiaShopController controller){
        this.world = world;
        this.parentController = parentController;
        this.controller = controller;
        boughtItems = new ArrayList<>();
        itemPrices = ShopLoader.loadItemPrices();
        switch(parentController.getDifficulty()){
            case STANDARD:
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
        String errorMessage = successMessage;
        if(world.getCharacter().getGold() < itemPrices.get(type)){
            errorMessage = new String("Insufficient Gold");
        }else if(world.isUnequippedInventoryFull()){
            errorMessage = new String("Inventory is Full");
        }else{
            if(difficultyBehaviour.canBuy(boughtItems)){
                world.getCharacter().minusGold(itemPrices.get(type));
                controller.updateShopGold();
                Item boughtItem = ItemLoader.loadBoughtItem(type, world.getFirstAvailableSlotForItem().getValue0(), world.getFirstAvailableSlotForItem().getValue1());
                boughtItems.add(boughtItem);
                world.addUnequippedItem(boughtItem);
                parentController.onLoadUnequippedItem(boughtItem);
            }else{
                errorMessage = difficultyBehaviour.getErrorMessage();
            }
        }
        return errorMessage;
    }

    public String getItemPrice(ItemType type){
        return Integer.toString(itemPrices.get(type));
    }

    public String getSuccessMessage(){
        return successMessage;
    }
}
