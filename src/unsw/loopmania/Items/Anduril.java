package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents an equipped or unequipped Anudril in the backend world
 */
public class Anduril extends OffensiveItems{

    private ItemType subType;
    
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y, ItemType subType) {
        super(x, y);
        this.subType = subType;
    }    

    public ItemType getItemSubType(){
        return this.subType;
    }

    public ItemType getItemType(){
        return ItemType.ANDURIL;
    }

    public Item copyItem(int x, int y){
        return new Anduril(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y), subType);
    }
}
