package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.BuildingType;

public class CampfireBuilding extends Building{

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public BuildingType getBuildingType(){
        return BuildingType.CAMPFIRE_BUILDING;
    }


    //use to see if a location is in rage of this campfire
    //use in battle calculation (check at start of each battle)
    public boolean inRange(int x, int y){
        if(Math.sqrt((this.getX()-x)*(this.getX()-x) + (this.getY()-y)*(this.getY()-y))<=5){
            return true;
        }
        return false;
    }

}
