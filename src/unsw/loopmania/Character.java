package unsw.loopmania;


/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {

    private LoopManiaWorldController observer;
    private double health = 0;
    private Money money;

    public Character(PathPosition position) {
        super(position);
        money = new Money();
    }

    public void setObserver(LoopManiaWorldController observer){
        this.observer = observer;
    }

    public boolean addMoney(int amount) {
        boolean result = money.addMoney(amount);
        observer.updateMoney();
        return result;
    }

    public boolean minusMoney(int amount) {
        boolean result = money.minusMoney(amount);
        observer.updateMoney();
        return result;
    }

    public int getMoney() {
        return money.getMoney();
    }
    
}
