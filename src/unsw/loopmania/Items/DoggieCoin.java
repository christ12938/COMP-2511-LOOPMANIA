package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

/**
 * represents gold in the backend world
 */
public class DoggieCoin extends Item{

    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public ItemType getItemType(){
        return ItemType.DOGGIE_COIN;
    }

    public Item copyItem(int x, int y){
        return new DoggieCoin(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }  
}