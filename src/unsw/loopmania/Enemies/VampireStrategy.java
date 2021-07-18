package unsw.loopmania.Enemies;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import unsw.loopmania.Damageable;

public class VampireStrategy implements CritStrategy {
    private Hashtable<Damageable, Integer> critEnemies;
    private double damage;
    
    public VampireStrategy() {
        critEnemies = new Hashtable<Damageable, Integer>();
        damage = 6;
    }

    @Override
    public void applyCrit(Damageable damageable) {
        critEnemies.put(damageable, Integer.valueOf(2 + new Random().nextInt(3)));
    }

    @Override
    public void processCrit() {
        List<Damageable> rem = new ArrayList<Damageable>();
        critEnemies.forEach((k, v) -> {
            k.takeDamage(damage);
            v = Integer.valueOf(v.intValue() - 1);
            if (v <= 0) {
                rem.add(k);
            }
        });

        for (Damageable r : rem) {
            critEnemies.remove(r);
        }
    }
}
