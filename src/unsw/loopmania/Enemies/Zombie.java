package unsw.loopmania.Enemies;

import unsw.loopmania.Damageable;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;

public class Zombie extends Enemy{

    private final double maxHealth = EnemyType.ZOMBIE.getMaxHealth();
    private double currentHealth;
    private final int attack = EnemyType.ZOMBIE.getAttack();
    private CritStrategy critStrategy;

    public Zombie(PathPosition position) {
        super(position);
        this.currentHealth = maxHealth;
        this.critStrategy = new ZombieCritStrategy();
    }

    public EnemyType getEnemyType(){
        return EnemyType.ZOMBIE;
    }

    public DamageableType getDamageableType() {
        return DamageableType.ZOMBIE;
    }

    public double getCurrentHealth(){
        return this.currentHealth;
    }

    public void setCurrentHealth(double currentHealth){
        this.currentHealth = currentHealth;
    }

    public void dealDamage(Damageable damageable) {
        damageable.takeDamage(attack, this);
    }

    public boolean isNextAttackCritical(){
        return critStrategy.isNextAttackCritical();
    }
}
