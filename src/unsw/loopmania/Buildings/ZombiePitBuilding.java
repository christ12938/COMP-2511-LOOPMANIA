package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;
import unsw.loopmania.Types.EnemyType;

public class ZombiePitBuilding extends Spawner{

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, EnemyType.ZOMBIE);
    }

    public BuildingType getBuildingType(){
        return BuildingType.ZOMBIEPIT_BUILDING;
    }
    
}
