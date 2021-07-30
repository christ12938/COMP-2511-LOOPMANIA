package unsw.loopmania.Types;

/**
 * Type of items
 */
public enum ItemType{
    SWORD(true, true, false),
    STAKE(true, true, false),
    STAFF(true, true, false),
    ARMOUR(true, false, true),
    SHIELD(true, false, true),
    HELMET(true, false, true),
    GOLD(false, false, false),
    DOGGIE_COIN(false, false, false),
    HEALTH_POTION(false, false, false),
    THE_ONE_RING(false, false, false),
    ANDURIL(true, true, false),
    TREE_STUMP(true, false, true);

    private boolean isEquipable;
    private boolean isOffensive;
    private boolean isDefensive;

    ItemType(boolean isEquipable, boolean isOffensive, boolean isDefensive){
        this.isEquipable = isEquipable;
        this.isOffensive = isOffensive;
        this.isDefensive = isDefensive;
    }

    public boolean isEquipable(){
        return this.isEquipable;
    }

    public boolean isOffensive(){
        return this.isOffensive;
    }

    public boolean isDefensive(){
        return isDefensive;
    }

}