package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;

public class TowerBuilding extends Building{

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public BuildingType getBuildingType(){
        return BuildingType.TOWER_BUILDING;
    }

    public boolean inRange(SimpleIntegerProperty x, SimpleIntegerProperty y){
        return false;
    }
}
