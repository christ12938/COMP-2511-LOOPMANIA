package unsw.loopmania.Types;

/**
 * Type of items
 */
public enum ItemType{
    SWORD(true, true, false, 5, 0),
    STAKE(true, true, false, 3, 0),
    STAFF(true, true, false, 1, 0),
    ARMOUR(true, false, true, 0, 3),
    SHIELD(true, false, true, 0, 3),
    HELMET(true, false, true, 0, 3),
    GOLD(false, false, false, 0, 0),
    HEALTH_POTION(false, false, false, 0, 0),
    DOGGIECOIN(false, false, false, 0, 0),
    THE_ONE_RING(false, false, false, 0, 0),
    ANDURIL(true, true, false, 15, 0),
    TREE_STUMP(true, false, true, 0, 15);

    private boolean isEquipable;
    private boolean isOffensive;
    private boolean isDefensive;
    private int attack;
    private int defense;

    ItemType(boolean isEquipable, boolean isOffensive, boolean isDefensive, int attack, int defense){
        this.isEquipable = isEquipable;
        this.isOffensive = isOffensive;
        this.isDefensive = isDefensive;
        this.attack = attack;
        this.defense = defense;
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

    public int getAttack(){
        return attack;
    }

    public int getDefense(){
        return defense;
    }

}