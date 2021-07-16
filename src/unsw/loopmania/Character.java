package unsw.loopmania;

import unsw.loopmania.Enemies.Damageable;

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
    private int damage;
    LoopManiaWorldController observer;

    public Character(PathPosition position) {
        super(position);
        this.currentHealth = this.maxHealth;
        this.gold = 0;
        this.experience = 0;
        // just putting random health value for now for testing
        this.attack = 5;
        this.defense = 5;
    }

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

    public void setHealth(double health) {
        this.currentHealth = health;

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public void addAttack(int attack) {
        this.attack += attack;
    }

    public void addDefense(int defense) {
        this.defense += defense;
    }

    public boolean addGold(int amount){
        if (this.gold == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.gold) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.gold = Integer.MAX_VALUE;
            observer.updateGold();
            return true;
        } else {
            this.gold += amount;
            observer.updateGold();
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
            observer.updateGold();
            return true;
        }
    }

    public boolean addExperience(int amount){
        if (this.experience == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.experience) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.experience = Integer.MAX_VALUE;
            observer.updateExperience();
            return true;
        } else {
            this.experience += amount;
            observer.updateExperience();
            return true;
        }
    }

    public void minusHealth(double health){
        if(currentHealth - health == 0){
            currentHealth = 0;
            observer.updateHealth(this.currentHealth, this.maxHealth);
            //DO STH ELSE
        }else{
            currentHealth = currentHealth - health;
            observer.updateHealth(this.currentHealth, this.maxHealth);
        }
    }

    
}
