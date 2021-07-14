package unsw.loopmania.Enemies;

import java.util.Random;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;

/**
 * a basic form of enemy in the world
 */

 // TODO: RENAME BASICENEMY TO ENEMY and change to abstract
public class Enemy extends MovingEntity {
    private int health;
    private int battleRadius;
    private int supportRadius;

    // TODO = modify this, and add additional forms of enemy
    public Enemy(PathPosition position) {
        super(position);
        battleRadius = supportRadius = 4;
        health = 10;
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
        return Math.pow((character.getX()-super.getX()), 2) < battleRadius;
    }

    public boolean inSupportRadius(Character character) {
        return Math.pow((character.getX()-super.getX()), 2) < supportRadius;
    }

    public void dealDamage(Character character) {
        return;
    }

    public void takeDamage(Character character) {
        return;
    }
}
