package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;
import unsw.loopmania.Types.EnemyType;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Spawner{

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, EnemyType.VAMPIRE);
    }

    public BuildingType getBuildingType(){
        return BuildingType.VAMPIRECASTLE_BUILDING;
    }
}
