package unsw.loopmania.Enemies;

import java.util.Random;

import unsw.loopmania.Damageable;

public class StandardCritStrategy implements CritStrategy {
    private double damage;
    public  StandardCritStrategy() {
        this.damage = 5;
    }

    @Override
    public void applyCrit(Damageable damageable) {
        damageable.takeDamage(damage);
    }

    @Override
    public void processCrit() {
        return;    
    }
}
