package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Damageable;
import unsw.loopmania.Types.BuildingType;

public class TowerBuilding extends Building{

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
    }

    public void removeBuffFromCharacter(Character character){
        character.removeBattleBuildings(this);
    }
}
