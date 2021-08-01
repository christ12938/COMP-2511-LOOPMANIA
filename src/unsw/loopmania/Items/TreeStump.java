package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped TreeStump in the backend world
 */
public class TreeStump extends DefensiveItems{

    private ItemType subType;

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y, ItemType subType) {
        super(x, y);
        this.subType = subType;
    }
    
    public ItemType getItemSubType(){
        return this.subType;
    }

    public ItemType getItemType(){
        return ItemType.TREE_STUMP;
    }

    public Item copyItem(int x, int y) {
        return new TreeStump(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y), subType);
    }
}