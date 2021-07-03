package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;

public class TrapBuilding extends Building{

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public BuildingType getBuildingType(){
        return BuildingType.TRAP_BUILDING;
    }
    
}
