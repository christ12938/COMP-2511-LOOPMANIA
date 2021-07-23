package unsw.loopmania.Types;

/**
 * Type of buildings
 */
public enum BuildingType{
    VAMPIRECASTLE_BUILDING(true),
    ZOMBIEPIT_BUILDING(true),
    TOWER_BUILDING(false),
    VILLAGE_BUILDING(false),
    BARRACKS_BUILDING(false),
    TRAP_BUILDING(false),
    CAMPFIRE_BUILDING(false);

    private boolean isSpawnable;

    BuildingType(boolean isSpawnable){
        this.isSpawnable = isSpawnable;
    }

    public boolean isSpawnable(){
        return this.isSpawnable;
    }
}