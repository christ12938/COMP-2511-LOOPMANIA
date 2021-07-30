package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped Anudril in the backend world
 */
public class Anduril extends OffensiveItems{

    private final int attack = 15;
    
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    public ItemType getItemType(){
        return ItemType.ANDURIL;
    }

    public int getAttack() {
        return this.attack;
    }

    public Item copyItem(int x, int y){
        return new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}
