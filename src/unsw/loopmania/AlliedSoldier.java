package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Types.DamageableType;

public class AlliedSoldier extends MovingEntity implements Damageable{
    /**
     * The position of the soldier on the slot
     */
    private IntegerProperty slotPosition;

    private final double maxHealth = 30.0;
    private double currentHealth;
    private final int attack = 5;
    private int currentTranceTurn;

    public AlliedSoldier (PathPosition position, int slotPosition) {
        super(position);
        this.slotPosition = new SimpleIntegerProperty(slotPosition);
        this.currentHealth = maxHealth;
        this.currentTranceTurn = Staff.tranceTurn;
    }

    public IntegerProperty getSlotPosition(){
        return slotPosition;
    }

    public void setSlotPosition(int slotPosition){
        this.slotPosition.set(slotPosition);
    }

    @Override
    public void takeDamage(double damage, Damageable damageable) {
        this.currentHealth = (this.currentHealth - damage) < 0 ? 0 : this.currentHealth - damage;
    }

    @Override
    public void dealDamage(Damageable damageable) {
        damageable.takeDamage(attack, this);
    }

    public boolean isDefeated(){
        return this.currentHealth <= 0 ? true : false;
    }

    public DamageableType getDamageableType(){
        return DamageableType.ALLIED_SOLDIER;
    }

    public Zombie transformToZombie(){
        return new Zombie(getPathPosition());
    }

    public void startTranceTurn(){
        currentTranceTurn = Staff.tranceTurn;
    }

    public void nextTranceTurn(){
        currentTranceTurn--;
    }

    public int getTranceTurn(){
        return currentTranceTurn;
    }

    public double getCurrentHealth(){
        return currentHealth;
    }

}