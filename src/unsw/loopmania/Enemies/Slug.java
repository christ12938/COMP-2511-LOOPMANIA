package unsw.loopmania.Enemies;

import unsw.loopmania.Damageable;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;

public class Slug extends Enemy{

    private final double maxHealth = EnemyType.SLUG.getMaxHealth();
    private double currentHealth;
    private final int attack = EnemyType.SLUG.getAttack();
    private CritStrategy critStrategy;

    public Slug(PathPosition position){
        super(position);
        this.currentHealth = maxHealth;
        this.critStrategy = new SlugCritStrategy();
    }
    
    public EnemyType getEnemyType(){
        return EnemyType.SLUG;
    }

    public DamageableType getDamageableType(){
        return DamageableType.SLUG;
    }

    public double getCurrentHealth(){
        return this.currentHealth;
    }

    public void setCurrentHealth(double currentHealth){
        if(currentHealth > maxHealth) currentHealth = maxHealth;
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
