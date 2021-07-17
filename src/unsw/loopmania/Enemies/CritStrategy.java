package unsw.loopmania.Enemies;

import unsw.loopmania.Damageable;

public interface CritStrategy {
    public void applyCrit(Damageable damageable);

    public void processCrit();
}
