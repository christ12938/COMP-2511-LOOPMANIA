package unsw.loopmania.Enemies;

import java.util.Random;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;
import unsw.loopmania.Damageable;
import unsw.loopmania.Types.EnemyType;

/**
 * a basic form of enemy in the world
 */

 // TODO: RENAME BASICENEMY TO ENEMY and change to abstract
public abstract class Enemy extends MovingEntity implements Damageable{
    private double health;
    private int battleRadius;
    private int supportRadius;
    private double critRate;
    private int damage;
    private CritStrategy critStrategy;

    // TODO = modify this, and add additional forms of enemy
    public Enemy(PathPosition position, int health, int battleRadius, int supportRadius) {
        super(position);
        this.health = health;
        this.battleRadius = battleRadius;
        this.supportRadius = supportRadius;
        this.critRate = 0.05;
        this.damage = 5;
    }

    public double getHealth() {
        return health;
    }

    public void setCritStrategy(CritStrategy critStrategy) {
        this.critStrategy = critStrategy;
    }

    /**
     * move the enemy
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    public boolean inBattleRadius(Character character) {
        return Math.pow((character.getX()-super.getX()), 2) +  Math.pow((character.getY()-super.getY()), 2) < battleRadius;
    }

    public boolean inSupportRadius(Character character) {
        return Math.pow((character.getX()-super.getX()), 2) +  Math.pow((character.getY()-super.getY()), 2) < supportRadius;
    }

    @Override
    public void takeDamage(double damage) {
        health -= damage;
    }

    @Override
    public void dealDamage(Damageable damageable) {
        if ((new Random()).nextDouble() < 0.05) {
            critStrategy.applyCrit(damageable);
        }
        
        critStrategy.processCrit();
        damageable.takeDamage(damage);
    }

    public abstract EnemyType getEnemyType();
}
