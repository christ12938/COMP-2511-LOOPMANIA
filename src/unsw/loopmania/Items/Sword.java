package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class Sword extends OffensiveItems {
    private int attack;
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = 5;
    }    

    public ItemType getItemType(){
        return ItemType.SWORD;
    }

    public int getAttack() {
        return this.attack;
    }
}
