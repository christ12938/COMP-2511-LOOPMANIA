package unsw.loopmania;


public class Experience {
    private int total;
    
    public Experience() {
        this.total = 0;
    }
    
    public boolean addExperience(int amount){
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

    public int getExperience() {
        return this.total;
    }

}
