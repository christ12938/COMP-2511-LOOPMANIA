package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped TreeStump in the backend world
 */
public class TreeStump extends DefensiveItems{

    private final int defense = 15;

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public ItemType getItemType(){
        return ItemType.TREE_STUMP;
    }

    public int getDefense() {
        return this.defense; 
     }

    public Item copyItem(int x, int y) {
        return new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
}