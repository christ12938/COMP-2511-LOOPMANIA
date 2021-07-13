package unsw.loopmania;


/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {

    private double health;
    private int gold;
    private int experience;

    public Character(PathPosition position) {
        super(position);
        this.gold = 0;
        this.experience = 0;
    }
    
    public int getGold() {
        return this.gold;
    }

    public int getExperience() {
        return this.experience;
    }

    public boolean addGold(int amount){
        if (this.gold == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.gold) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.gold = Integer.MAX_VALUE;
            return true;
        } else {
            this.gold += amount;
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
            return true;
        }
    }

    public boolean addExperience(int amount){
        if (this.experience == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.experience) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.experience = Integer.MAX_VALUE;
            return true;
        } else {
            this.experience += amount;
            return true;
        }
    }

    
}
