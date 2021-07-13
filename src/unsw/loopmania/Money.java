package unsw.loopmania;

/**
 * represents an equipped or unequipped Helmet in the backend world
 */
public class Money {

    private int total;
    
    public Money() {
        this.total = 0;
    }
    
    public boolean addMoney(int amount){
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

    public boolean minusMoney(int amount){
        if (this.total == 0) {
            return false;
        } else if (this.total - amount < 0) {
            return false;
        } else {
            this.total -= amount;
            return true;
        }
    }

    public int getMoney() {
        return this.total;
    }

}