package unsw.loopmania.Enemies;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Types.EnemyType;

public class DoggieCritStrategy implements CritStrategy{

    private final double critChance = EnemyType.DOGGIE.getCritChance();

    public double applyCritDamage(int damage) {
        return damage * (1 + LoopManiaWorld.rand.nextDouble()*(EnemyType.DOGGIE.getCritMultiplier() - 1));
    }

    public boolean isNextAttackCritical() {
        return LoopManiaWorld.rand.nextDouble() < critChance ? true : false;
    }
    
}
