package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Types.BuildingType;

public class BarracksBuilding extends Building{

    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getBuildingRadius(){
        return 1;
    }

    public BuildingType getBuildingType(){
        return BuildingType.BARRACKS_BUILDING;
    }

    public void applyBuffToCharacter(Character character){
        character.addAlliedSoldier();
    }
    
}
