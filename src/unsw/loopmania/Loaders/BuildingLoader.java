package unsw.loopmania.Loaders;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Types.*;

public class BuildingLoader {
    /**
     * Create and return a building object based on the type of the card (Factory Pattern)
     * @param cardType Type of the card
     * @param buildingNodeX Frontend params
     * @param buildingNodeY Frontend params
     * @return A building object
     */
    public static Building loadBuilding(CardType cardType, int buildingNodeX, int buildingNodeY){
        switch(cardType){
            case VAMPIRECASTLE_CARD:
                return new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            case ZOMBIEPIT_CARD:
                return new ZombiePitBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            case TOWER_CARD:
                return new TowerBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            case VILLAGE_CARD:
                return new VillageBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            case BARRACKS_CARD:
                return new BarracksBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            case TRAP_CARD:
                return new TrapBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            case CAMPFIRE_CARD:
                return new CampfireBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
            default:
                /* This should never happen */
                return null;
        }
    }
}
