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

<<<<<<< HEAD
    //use to see if a location is in rage of this tower
    //use in battle calculation
    @Override
    public boolean inRange(int x, int y){
        if(Math.sqrt((this.getX()-x)*(this.getX()-x) + (this.getY()-y)*(this.getY()-y))<=5){
            return true;
        }
        return false;
=======
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
>>>>>>> 8e91f70171228b4dccaf26e702571430705948f9
    }
    
}
