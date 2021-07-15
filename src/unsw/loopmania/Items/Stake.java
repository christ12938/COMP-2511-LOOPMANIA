package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Stake extends OffensiveItems{
    private int attack;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = 3;
    }

    public ItemType getItemType(){
        return ItemType.STAKE;
    }

    public int getAttack() {
        return this.attack;
    }
}
