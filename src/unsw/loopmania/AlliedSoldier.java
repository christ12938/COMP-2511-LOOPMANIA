package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AlliedSoldier extends MovingEntity implements Damageable{
    /**
     * The position of the soldier on the slot
     */
    private IntegerProperty slotPosition;

    private double health;
    private int damage;
    private boolean allied;

    public AlliedSoldier (PathPosition position, int slotPosition) {
        super(position);
        this.slotPosition = new SimpleIntegerProperty(slotPosition);
        this.health = 30;
        this.damage = 5;
        this.allied = true;
    }

    public boolean isAllied() {
        return allied;
    }

    public void setAllied(boolean allied) {
        this.allied = allied;
    }

    public IntegerProperty getSlotPosition(){
        return slotPosition;
    }

    public void setSlotPosition(int slotPosition){
        this.slotPosition.set(slotPosition);
    }

    @Override
    public void takeDamage(double damage) {
        health -= damage;
    }

    @Override
    public void dealDamage(Damageable damageable) {
        damageable.takeDamage(damage);
    }

    public double getHealth() {
        return health;
    }
}