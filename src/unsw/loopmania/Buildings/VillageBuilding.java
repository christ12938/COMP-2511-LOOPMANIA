package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;

public class VillageBuilding extends Building{

    public static final double healValue = 10.0;

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getBuildingRadius(){
        return 1;
    }

    public BuildingType getBuildingType(){
        return BuildingType.VILLAGE_BUILDING;
    }

}
