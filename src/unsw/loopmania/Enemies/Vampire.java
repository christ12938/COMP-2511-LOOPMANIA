package unsw.loopmania.Enemies;

import unsw.loopmania.Damageable;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;

public class Vampire extends Enemy{
    
    private final double maxHealth = EnemyType.VAMPIRE.getMaxHealth();
    private double currentHealth;
    private final int attack = EnemyType.VAMPIRE.getAttack();
    private CritStrategy critStrategy;

    public Vampire(PathPosition position) {
        super(position);
        this.currentHealth = maxHealth;
        this.critStrategy = new VampireCritStrategy();
    }

    public EnemyType getEnemyType(){
        return EnemyType.VAMPIRE;
    }

    public DamageableType getDamageableType() {
        return DamageableType.VAMPIRE;
    }

    public double getCurrentHealth(){
        return this.currentHealth;
    }

    public void setCurrentHealth(double currentHealth){
        this.currentHealth = currentHealth;
    }

    public void dealDamage(Damageable damageable){
        if(critStrategy.isNextAttackCritical()){
            damageable.takeDamage(critStrategy.applyCritDamage(attack), this);
        }else{
            damageable.takeDamage(attack, this);
        }
    }
    
}
