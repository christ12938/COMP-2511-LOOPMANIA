package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Items.DefensiveItems;
import unsw.loopmania.Items.Equipable;
import unsw.loopmania.Items.OffensiveItems;

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

    // List of equipped items
    private List<Equipable> equippedItems;

    public Character(PathPosition position) {
        super(position);
        this.position = position;
        this.currentHealth = this.maxHealth;
        this.gold = 0;
        this.experience = 0;
        // just putting random health value for now for testing
        this.attack = 5;
        this.defense = 5;
        this.observer = null;
        
        battleBuildings = new ArrayList<>();
        alliedSoldiers = new ArrayList<>();
        equippedItems = new ArrayList<>();
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

    public void addHp(long amount) {
        this.currentHealth += amount;
        if (currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        }
        return;
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

    public void addAlliedSoldier(){
        AlliedSoldier alliedSoldier = observer.addAlliedSoldier();
        if(alliedSoldier != null){
            alliedSoldiers.add(alliedSoldier);
        }
    }

    public void removeAlliedSoldier(AlliedSoldier alliedSoldier){
        alliedSoldiers.remove(alliedSoldier);
        observer.removeAlliedSoldier(alliedSoldier);
    }

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

    @Override
    public void takeDamage(double damage) {
        currentHealth -= damage;
    }

    @Override
    public void dealDamage(Damageable damageable) {
        damageable.takeDamage(attack);
    }
    
    public void addHealth(double health){
        currentHealth = currentHealth + health;
        if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
        observer.updateHealth(this.currentHealth, this.maxHealth);
    }

    public void minusHealth(double health){
        currentHealth = currentHealth - health;
        if(currentHealth <= 0){
            currentHealth = 0;

            if (observer != null) {
                observer.updateHealth(this.currentHealth, this.maxHealth);
            }

            //DO STH ELSE
        }else{
            currentHealth = currentHealth - health;

            if (observer != null) {
                observer.updateHealth(this.currentHealth, this.maxHealth);
            }

            
        }
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    public void equip(Equipable item){
        if(item instanceof OffensiveItems){
            addAttack(((OffensiveItems)item).getAttack());
        }else if(item instanceof DefensiveItems){
            addDefense(((DefensiveItems)item).getDefense());
        }
        equippedItems.add(item);
    }

    public void unequip(Equipable item){
        if(item instanceof OffensiveItems){
            addAttack(((OffensiveItems)item).getAttack()*(-1));
        }else if(item instanceof DefensiveItems){
            addDefense(((DefensiveItems)item).getDefense()*(-1));
        }
        equippedItems.remove(item);
    }
    
}
