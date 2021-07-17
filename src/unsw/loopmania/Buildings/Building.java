package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Types.BuildingType;
import unsw.loopmania.Character;

public abstract class Building extends StaticEntity{

    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public void applyBuffToCharacter(Character character){
        return;
    }

    public void removeBuffFromCharacter(Character character){
        return;
    }

    public void applyDeBuffToEnemy(Enemy enemy){
        return;
    }

    public void removeDeBuffFromEnemy(Enemy enemy){
        return;
    }

    public abstract int getBuildingRadius();
    public abstract BuildingType getBuildingType();
}
