package unsw.loopmania.Buildings;
import java.lang.Math;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Types.BuildingType;

public class TowerBuilding extends Building{

    private boolean isBuffingCharacter = false;

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getBuildingRadius(){
        return 5;
    }

    public BuildingType getBuildingType(){
        return BuildingType.TOWER_BUILDING;
    }

    public void applyBuffToCharacter(Character character){
        character.addBattleBuildings(this);
        isBuffingCharacter = true;
    }

    public void removeBuffFromCharacter(Character character){
        character.removeBattleBuildings(this);
        isBuffingCharacter = false;
    }

    public boolean isBuffingCharacter(){
        return isBuffingCharacter;
    }
    
}
