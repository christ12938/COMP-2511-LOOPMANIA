package unsw.loopmania.Enemies;

import unsw.loopmania.Damageable;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;

public class Slug extends Enemy{

    private final double maxHealth = EnemyType.SLUG.getMaxHealth();
    private double currentHealth;
    private final int attack = EnemyType.SLUG.getAttack();

    public Slug(PathPosition position){
        super(position);
        this.currentHealth = maxHealth;
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
        this.currentHealth = currentHealth;
    }

    public void dealDamage(Damageable damageable){
        damageable.takeDamage(attack, this);
    }
}
