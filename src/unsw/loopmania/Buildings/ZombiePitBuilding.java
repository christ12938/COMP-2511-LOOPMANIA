package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;

public class ZombiePitBuilding extends Spawner{

    private boolean hasSpawned = true;

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public BuildingType getBuildingType(){
        return BuildingType.ZOMBIEPIT_BUILDING;
    }

    public int getSpawningCycle(){
        return 1;
    }

    public void setHasSpawned(boolean hasSpawned){
        this.hasSpawned = hasSpawned;
    }

    public boolean getHasSpawned(){
        return hasSpawned;
    }

}
