package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Types.ItemType;

/**
 * represents an item in the backend world
 */
public abstract class Item extends StaticEntity{

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public boolean isEquipable(){
        return getItemType().isEquipable();
    }

    public boolean isOffensive(){
        return getItemType().isOffensive();
    }

    public boolean isDefensive(){
        return getItemType().isDefensive();
    }

    public ItemType getItemSubType(){
        return null;
    }
    
    public abstract ItemType getItemType();
    public abstract Item copyItem(int x, int y);
    
}
