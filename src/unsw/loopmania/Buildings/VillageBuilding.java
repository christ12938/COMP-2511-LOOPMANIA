package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Types.BuildingType;

public class VillageBuilding extends Building{

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getBuildingRadius(){
        return 1;
    }

    public BuildingType getBuildingType(){
        return BuildingType.VILLAGE_BUILDING;
    }

    public void applyBuffToCharacter(Character character){
        character.addHealth(20.0);
    }
    
    public boolean isBuffingCharacter(){
        return false;
    }
}
