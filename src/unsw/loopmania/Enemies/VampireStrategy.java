package unsw.loopmania.Enemies;

import java.util.Hashtable;
import java.util.Random;

import unsw.loopmania.Damageable;

public class VampireStrategy implements CritStrategy {
    private Hashtable<Damageable, Integer> critEnemies;
    private double damage;
    
    public VampireStrategy() {
        critEnemies = new Hashtable<Damageable, Integer>();
        damage = 5;
    }

    @Override
    public void applyCrit(Damageable damageable) {
        critEnemies.put(damageable, Integer.valueOf(new Random().nextInt(5)));
    }

    @Override
    public void processCrit() {
        critEnemies.forEach((k, v) -> {
            k.takeDamage(damage);
            v--;
            if (v <= 0) {
                critEnemies.remove(k);
            }
        });
    }
}
