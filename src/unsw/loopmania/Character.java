package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Items.DefensiveItems;
import unsw.loopmania.Items.Equipable;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.OffensiveItems;
import unsw.loopmania.Items.RareItem;
import unsw.loopmania.Types.ItemType;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity implements Damageable{

    private final double maxHealth = 100;
    private double currentHealth;
    private int attack;
    private int defense;
    private int gold;
    private int experience;
    private LoopManiaWorldController observer;
    private LoopManiaWorld world;
    private PathPosition position;

    /**
     * List of buildings that could help in battle
     * Set type to Building just in case in milestone 3 
     * different types of buildings can help in battle
     */
    private List<Building> battleBuildings;

    /**
     * List of allied soldiers
     */
    private List<AlliedSoldier> alliedSoldiers;

    /**
     * // List of equipped items
     */
    private List<Equipable> equippedItems;

    public Character(PathPosition position) {
        super(position);
        this.position = position;
        this.currentHealth = this.maxHealth;
        this.gold = 0;
        this.experience = 0;
        this.attack = 5;
        this.defense = 5;
        battleBuildings = new ArrayList<>();
        alliedSoldiers = new ArrayList<>();
        equippedItems = new ArrayList<>();
        this.observer = null;
        this.world = null;
    }

    /**
     * Set the observer of the character
     * @param observer
     */
    public void setObserver(LoopManiaWorldController observer){
        this.observer = observer;
    }

    public double getCurrentHealth(){
        return this.currentHealth;
    }

    public double getMaxHealth(){
        return this.maxHealth;
    }
    
    public int getGold() {
        return this.gold;
    }

    public int getExperience() {
        return this.experience;
    }

    public double getHealth() {
        return currentHealth;
    }

    public List<AlliedSoldier> getAlliedSoldiers(){
        return this.alliedSoldiers;
    }

    public void setWorld(LoopManiaWorld world){
        this.world = world;
    }

    public void setHealth(double health) {
        this.currentHealth = health;

    }
    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public PathPosition getPathPosition(){
        return this.position;
    }
    
    public List<Equipable> getEquippedItems(){
        return this.equippedItems;
    }

    public void addAttack(int attack) {
        this.attack += attack;
    }

    public void addDefense(int defense) {
        this.defense += defense;
    }

    /**
     * Add buildings that could assist the character in battle
     * @param building
     */
    public void addBattleBuildings(Building building){
        this.battleBuildings.add(building);
    }

    /**
     * Remove buildings that could assist the character in battle
     * @param building
     */
    public void removeBattleBuildings(Building building){
        this.battleBuildings.remove(building);
    }

    /**
     * add allied soldier
     */
    public void addAlliedSoldier(){
        AlliedSoldier alliedSoldier = observer.addAlliedSoldier();
        if(alliedSoldier != null){
            alliedSoldiers.add(alliedSoldier);
        }
    }

    /**
     * remove allied soldier
     */
    public void removeAlliedSoldier(AlliedSoldier alliedSoldier){
        alliedSoldiers.remove(alliedSoldier);
        alliedSoldier.destroy();
        observer.removeAlliedSoldier();
    }

    /**
     * add gold to character
     * @param amount amount too be added
     */
    public boolean addGold(int amount){
        if (this.gold == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.gold) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.gold = Integer.MAX_VALUE;
            if (observer != null) {
                observer.updateGold();
            }
            return true;
        } else {
            this.gold += amount;
            if (observer != null) {
                observer.updateGold();
            }
            return true;
        }
    }

    /**
     * decrease gold to character
     * @param amount amount too be decrease
     */
    public boolean minusGold(int amount){
        if (this.gold == 0) {
            return false;
        } else if (this.gold - amount < 0) {
            return false;
        } else {
            this.gold -= amount;
            if (observer != null) {
                observer.updateGold();
            }
            return true;
        }
    }

    /**
     * add experience to character
     * @param amount amount too be added
     */
    public boolean addExperience(int amount){
        if (this.experience == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.experience) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.experience = Integer.MAX_VALUE;
            if (observer != null) {
                observer.updateExperience();
            }
            return true;
        } else {
            this.experience += amount;
            if (observer != null) {
                observer.updateExperience();
            }
            return true;
        }
    }

    /**
     * decrease hp based on damage taken by character
     * @param damage amount of hp too be decreased
     */
    @Override
    public void takeDamage(double damage) {
        damage = (damage - defense <= 0 ? 0 : damage - defense);
        minusHealth(damage);
    }

    /**
     * deal damage too an damageable entity
     * @param damageable entity to be damaged
     */
    @Override
    public void dealDamage(Damageable damageable) {
        damageable.takeDamage(attack);
    }
    
    /**
     * add health to current health of character
     * @param health amount of health to be added
     */
    public void addHealth(double health){
        currentHealth = currentHealth + health;
        if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
        if (observer != null) {
            observer.updateHealth(this.currentHealth, this.maxHealth);
        }
    }

    /**
     * decrease health of current health of character
     * @param health amount of health to be deacreased
     */
    public void minusHealth(double health){
        currentHealth = currentHealth - health;
        if(currentHealth <= 0){
            currentHealth = 0;
            /* Apply effect of the one ring */
            Item destroyedRareItem = null;
            for(Item i : world.getUnequippedInventoryItems()){
                if(i.getItemType() == ItemType.THE_ONE_RING){
                    currentHealth = maxHealth;
                    destroyedRareItem = i;
                    break;
                }
            }
            if(destroyedRareItem != null){
                world.removeUnequippedInventoryItem(destroyedRareItem);
            }

            if (observer != null) {
                observer.updateHealth(this.currentHealth, this.maxHealth);
            }

            //DO STH ELSE
        }else{
            if (observer != null) {
                observer.updateHealth(this.currentHealth, this.maxHealth);
            }
        }
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    /**
     * equip the character with an equipable item
     * @param item equipable item to be equipped
     */
    public void equip(Equipable item){
        if(item instanceof OffensiveItems){
            addAttack(((OffensiveItems)item).getAttack());
        }else if(item instanceof DefensiveItems){
            addDefense(((DefensiveItems)item).getDefense());
        }
        equippedItems.add(item);
    }

    /**
     * unequip item from character
     * @param item item to be unequipped    
     */
    public void unequip(Equipable item){
        if(item instanceof OffensiveItems){
            addAttack(((OffensiveItems)item).getAttack()*(-1));
        }else if(item instanceof DefensiveItems){
            addDefense(((DefensiveItems)item).getDefense()*(-1));
        }
        equippedItems.remove(item);
    }
    
}
