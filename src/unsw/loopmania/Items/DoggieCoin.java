package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.ItemType;

public class DoggieCoin extends Item{

    public static int minValue = 1;
    public static int currentValue = 10;
    public static int maxValue = 20;

    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public ItemType getItemType() {
        return ItemType.DOGGIECOIN;
    }

    public Item copyItem(int x, int y) {
        return new DoggieCoin(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
    
}