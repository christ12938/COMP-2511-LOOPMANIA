package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Types.BuildingType;

public class VillageBuilding extends Building{

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public BuildingType getBuildingType(){
        return BuildingType.VILLAGE_BUILDING;
    }

    public void characterEffect(Character c){
        c.addHealth(2);
    }

}
