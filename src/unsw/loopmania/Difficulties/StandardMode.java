package unsw.loopmania.Difficulties;

import java.util.List;

import unsw.loopmania.Items.Item;

public class StandardMode implements DifficultyBehaviour{

    @Override
    public boolean canBuy(List<Item> boughtItems) {
        return true;
    }

    @Override
    public String getErrorMessage() {
        return new String("This Should Never Happen");
    }
    
}
