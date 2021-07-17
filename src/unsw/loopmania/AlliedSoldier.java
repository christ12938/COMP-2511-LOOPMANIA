package unsw.loopmania;

import org.javatuples.Pair;

public class AlliedSoldier extends MovingEntity implements Damageable{
    /**
     * The position of the soldier on the slot
     */
    private Pair<Integer, Integer> slotPosition;

    private double health;
    private int damage;
    private boolean allied;

    public AlliedSoldier (PathPosition position, Pair<Integer, Integer> slotPosition) {
        super(position);
        this.slotPosition = slotPosition;
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

    public int getSlotPosition(){
        return slotPosition.getValue0();
    }

    public void setSlotPosition(int slotPosition){
        this.slotPosition = new Pair<Integer, Integer>(slotPosition, 0);
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