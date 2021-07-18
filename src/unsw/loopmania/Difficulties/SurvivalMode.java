package unsw.loopmania.Difficulties;

import java.util.List;

import unsw.loopmania.Items.Item;
import unsw.loopmania.Types.ItemType;

public class SurvivalMode implements DifficultyBehaviour{

    @Override
    public boolean canBuy(List<Item> boughtItems) {
        for(Item item : boughtItems){
            if(item.getItemType() == ItemType.HEALTH_POTION){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return new String("Buy failed.\nAlready Bought 1 Health Potion.");
    }
    
}
