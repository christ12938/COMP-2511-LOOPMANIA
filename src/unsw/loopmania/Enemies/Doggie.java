package unsw.loopmania.Enemies;

import unsw.loopmania.Damageable;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;

public class Doggie extends Enemy{

    private final double maxHealth = EnemyType.DOGGIE.getMaxHealth();
    private double currentHealth;
    private final int attack = EnemyType.DOGGIE.getAttack();
    private CritStrategy critStrategy;
    public static boolean hasSpawned = false;

    public Doggie(PathPosition position) {
        super(position);
        this.currentHealth = maxHealth;
        this.critStrategy = new DoggieCritStrategy();
    }

    public EnemyType getEnemyType(){
        return EnemyType.DOGGIE;
    }

    public DamageableType getDamageableType(){
        return DamageableType.DOGGIE;
    }

    public double getCurrentHealth(){
        return this.currentHealth;
    }

    @Override
    public void setCurrentHealth(double currentHealth) {
        if(currentHealth > maxHealth) currentHealth = maxHealth;
        this.currentHealth = currentHealth;
    }

    @Override
    public void dealDamage(Damageable damageable) {
        if(critStrategy.isNextAttackCritical()){
            damageable.takeDamage(critStrategy.applyCritDamage(attack), this);
        }else{
            damageable.takeDamage(attack, this);
        }
    }
}
