package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.Types.BuildingType;

public class CampfireBuilding extends Building{

    private int attackBuffed = 0;

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
    }

    public void removeBuffFromCharacter(Character character){
        character.setAttack(character.getAttack() - attackBuffed);
        attackBuffed = 0;
    }
    
}
