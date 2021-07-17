package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Types.BuildingType;

public class TrapBuilding extends Building{

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getBuildingRadius(){
        return 1;
    }

    public BuildingType getBuildingType(){
        return BuildingType.TRAP_BUILDING;
    }

    public void applyDeBuffToEnemy(Enemy enemy){
        enemy.takeDamage(30);
        if(enemy.getHealth() <= 0) enemy.destroy();
        destroy();
    }

}
