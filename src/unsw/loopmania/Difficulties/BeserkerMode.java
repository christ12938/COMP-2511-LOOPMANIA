package unsw.loopmania.Difficulties;

import java.util.List;

import unsw.loopmania.Items.DefensiveItems;
import unsw.loopmania.Items.Item;

public class BeserkerMode implements DifficultyBehaviour{

    @Override
    public boolean canBuy(List<Item> boughtItems) {
        for(Item item : boughtItems){
            if(item instanceof DefensiveItems){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return new String("Buy failed.\nAlready Bought 1 Protective Gear.");
    }
    
}
