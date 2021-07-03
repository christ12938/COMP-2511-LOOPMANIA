package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public abstract class Item extends StaticEntity{

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
}
