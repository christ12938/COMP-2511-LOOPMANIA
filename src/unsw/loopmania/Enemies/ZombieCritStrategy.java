package unsw.loopmania.Enemies;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Types.EnemyType;

public class ZombieCritStrategy implements CritStrategy {

    private final double critChance = EnemyType.ZOMBIE.getCritChance();

    public double applyCritDamage(int damage) {
        return 0;
    }

    public boolean isNextAttackCritical() {
        return LoopManiaWorld.rand.nextDouble() < critChance ? true : false;
    }
}
