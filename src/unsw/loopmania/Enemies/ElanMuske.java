package unsw.loopmania.Enemies;

import java.util.List;

import unsw.loopmania.Damageable;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;

public class ElanMuske extends Enemy{

    private final double maxHealth = EnemyType.ELAN_MUSKE.getMaxHealth();
    private double currentHealth;
    private final int attack = EnemyType.ELAN_MUSKE.getAttack();
    private CritStrategy critStrategy;

    public static double jumpChance = 0.8;
    public static boolean hasSpawned = false;
    
    public ElanMuske(PathPosition position) {
        super(position);
        this.currentHealth = maxHealth;
        this.critStrategy = new ElanMuskeCritStrategy();
    }

    public EnemyType getEnemyType(){
        return EnemyType.ELAN_MUSKE;
    }

    public DamageableType getDamageableType(){
        return DamageableType.ELAN_MUSKE;
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

    public void healEnemies(List<Enemy> battleEnemies){
        for(Enemy e : battleEnemies){
            if(LoopManiaWorld.rand.nextDouble() < 0.2){
                e.setCurrentHealth(e.getCurrentHealth() + 10);
            }
        }
    }
}
