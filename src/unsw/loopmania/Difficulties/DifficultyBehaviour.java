package unsw.loopmania.Difficulties;

import java.util.List;

import unsw.loopmania.Items.Item;

public interface DifficultyBehaviour {
    public boolean canBuy(List<Item> boughtItems);
    public String getErrorMessage();
}
