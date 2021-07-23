package unsw.loopmania.Enemies;

import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Damageable;
import unsw.loopmania.Entity;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;

/**
 * a basic form of enemy in the world
 */

public abstract class Enemy extends MovingEntity implements Damageable{

    private Pair<Integer, Integer> herosCastlePos;

    public Enemy(PathPosition position) {
        super(position);
        this.herosCastlePos = new Pair<Integer, Integer>(LoopManiaWorld.herosCastle.getX(), LoopManiaWorld.herosCastle.getY());
    }

    /**
     * move the enemy
     */
    public void move(){
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...

        //New: if the selected path contains hero castle, select the oppsite path

        int directionChoice = (new Random()).nextInt(3);
        if (directionChoice == 0){
            if(getUpPath().equals(herosCastlePos)){
                if(!getDownPath().equals(herosCastlePos)){
                    moveDownPath();
                }
            }else{
                moveUpPath();
            }
        }
        else if (directionChoice == 1){
            if(getDownPath().equals(herosCastlePos)){
                if(!getUpPath().equals(herosCastlePos)){
                    moveUpPath();
                }
            }else{
                moveDownPath();
            }
        }
    }

    public boolean inBattleRadius(Entity e) {
        return Math.pow((e.getX()-super.getX()), 2) +  Math.pow((e.getY()-super.getY()), 2) < Math.pow(getBattleRadius(), 2);
    }

    public boolean inSupportRadius(Entity e) {
        return Math.pow((e.getX()-super.getX()), 2) +  Math.pow((e.getY()-super.getY()), 2) < Math.pow(getSupportRadius(), 2);
    }

    public int getBattleRadius(){
        return getEnemyType().getBattleRadius();
    }

    public int getSupportRadius(){
        return getEnemyType().getSupportRadius();
    }

    public boolean isDefeated() {
        return getCurrentHealth() <= 0 ? true : false;
    }

    public void takeDamage(double damage, Damageable from) {
        double currentHealth = (getCurrentHealth() - damage) < 0 ? 0 : getCurrentHealth() - damage;
        setCurrentHealth(currentHealth);
    }
    
    public abstract EnemyType getEnemyType();
    public abstract DamageableType getDamageableType();
    public abstract double getCurrentHealth();
    public abstract void setCurrentHealth(double currentHealth);
    public abstract void dealDamage(Damageable damageable);
}
