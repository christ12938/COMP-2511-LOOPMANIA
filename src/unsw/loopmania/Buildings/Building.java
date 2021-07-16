package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Types.BuildingType;

public abstract class Building extends StaticEntity{

    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public boolean inRange(int x, int y){
        if(x == this.getX() && y == this.getY()){
            return true;
        }

        return false;
    }

    public abstract BuildingType getBuildingType();


}
