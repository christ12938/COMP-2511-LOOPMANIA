package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Types.BuildingType;

public class CampfireBuilding extends Building{

    private int attackBuffed = 0;
    private boolean isBuffingCharacter = false;

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getBuildingRadius(){
        return 5;
    }

    public BuildingType getBuildingType(){
        return BuildingType.CAMPFIRE_BUILDING;
    }

    public void applyBuffToCharacter(Character character){
        attackBuffed = character.getAttack();
        character.setAttack(attackBuffed*2);
        isBuffingCharacter = true;
    }

<<<<<<< HEAD
    //use to see if a location is in rage of this campfire
    //use in battle calculation (check at start of each battle)
    @Override
    public boolean inRange(int x, int y){
        if(Math.sqrt((this.getX()-x)*(this.getX()-x) + (this.getY()-y)*(this.getY()-y))<=5){
            return true;
        }
        return false;
=======
    public void removeBuffFromCharacter(Character character){
        character.setAttack(character.getAttack() - attackBuffed);
        attackBuffed = 0;
        isBuffingCharacter = false;
>>>>>>> 8e91f70171228b4dccaf26e702571430705948f9
    }

    public boolean isBuffingCharacter(){
        return isBuffingCharacter;
    }
    
}
