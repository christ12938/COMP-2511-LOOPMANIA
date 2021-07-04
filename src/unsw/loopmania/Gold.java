package unsw.loopmania;

/**
 * represents an equipped or unequipped Helmet in the backend world
 */
public class Gold {

    private int total;
    
    public Gold() {
        this.total = 0;
    }
    
    public boolean addGold(int amount){
        if (this.total == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.total) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.total = Integer.MAX_VALUE;
            return true;
        } else {
            this.total += amount;
            return true;
        }
    }

    public boolean minusGold(int amount){
        if (this.total == 0) {
            return false;
        } else if (this.total - amount < 0) {
            return false;
        } else {
            this.total -= amount;
            return true;
        }
    }

    public int getGold() {
        return this.total;
    }

}