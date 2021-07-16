package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Spawner{

    private boolean hasSpawned = true;

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getBuildingRadius(){
        return 0;
    }

    public BuildingType getBuildingType(){
        return BuildingType.VAMPIRECASTLE_BUILDING;
    }

    public int getSpawningCycle(){
        return 5;
    }

    public void setHasSpawned(boolean hasSpawned){
        this.hasSpawned = hasSpawned;
    }

    public boolean getHasSpawned(){
        return hasSpawned;
    }

}
