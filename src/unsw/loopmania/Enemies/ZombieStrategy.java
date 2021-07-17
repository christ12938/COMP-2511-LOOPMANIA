package unsw.loopmania.Enemies;

import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Damageable;

public class ZombieStrategy implements CritStrategy {
    public ZombieStrategy() {

    }

    @Override
    public void applyCrit(Damageable damageable) {
        if (damageable instanceof AlliedSoldier) {
            AlliedSoldier soldier = (AlliedSoldier) damageable;
            soldier.setAllied(false);
        }
    }

    @Override
    public void processCrit() {
        return;
    }
}
