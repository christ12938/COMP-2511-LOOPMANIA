package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.javatuples.Pair;

import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.CritStrategy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.StandardCritStrategy;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.VampireStrategy;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Enemies.ZombieStrategy;
import unsw.loopmania.Types.EnemyType;


public class EnemyTest {
    @Test
    public void TestBattleRadiusTrue() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        
        Character character = new Character(new PathPosition(0, orderedPath));
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        assertTrue(slug.inBattleRadius(character));
    }

    @Test
    public void TestBattleRadiusFalse() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(1,3);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        
        Character character = new Character(new PathPosition(0, orderedPath));
        Slug slug = new Slug(new PathPosition(2, orderedPath));
        assertFalse(slug.inBattleRadius(character));
    }

    @Test
    public void TestSupportRadiusTrue() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        
        Character character = new Character(new PathPosition(0, orderedPath));
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        assertTrue(slug.inSupportRadius(character));
    }

    @Test
    public void TestBattleSupportFalse() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(1,3);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);
        
        Character character = new Character(new PathPosition(0, orderedPath));
        Slug slug = new Slug(new PathPosition(2, orderedPath));
        assertFalse(slug.inSupportRadius(character));
    }

    @Test
    public void TestDealDamage() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        Character character = new Character(new PathPosition(0, orderedPath));
        character.setHealth(99999999);
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        double health = character.getHealth();

        for (int i = 0; i < 100; i++) {
            slug.dealDamage(character);
        }

        assertTrue(health > character.getHealth());
    }

    @Test
    public void TestTakeDamage() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        double health = slug.getHealth();

        slug.takeDamage(5);
        assertTrue(health > slug.getHealth());
    }

    @Test
    public void TestGetEnemyType() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        Slug slug = new Slug(new PathPosition(0, orderedPath));
        Vampire vampire = new Vampire(new PathPosition(0, orderedPath));
        Zombie zombie = new Zombie(new PathPosition(0, orderedPath));

        assertTrue(slug.getEnemyType().equals(EnemyType.SLUG));
        assertTrue(vampire.getEnemyType().equals(EnemyType.VAMPIRE));
        assertTrue(zombie.getEnemyType().equals(EnemyType.ZOMBIE));
    }

    @Test
    public void TestMove() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(1,3);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);

        Slug slug = new Slug(new PathPosition(0, orderedPath));
        for (int i = 0; i < 100; i++) {
            slug.move();
        }
    }

    @Test
    public void TestStandardCritStrategy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        Character character = new Character(new PathPosition(0, orderedPath));
        double health = character.getHealth();

        CritStrategy crit = new StandardCritStrategy();
        crit.applyCrit(character);
        crit.processCrit();

        assertTrue(health > character.getHealth());
    }

    @Test
    public void TestZombieStrategy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        Character character = new Character(new PathPosition(0, orderedPath));
        AlliedSoldier soldier = new AlliedSoldier(new PathPosition(0, orderedPath), new Pair<Integer, Integer>(0, 0));

        assertTrue(soldier.isAllied());
        CritStrategy crit = new ZombieStrategy();
        crit.applyCrit(soldier);
        crit.applyCrit(character);
        crit.processCrit();

        assertFalse(soldier.isAllied());
    }

    @Test
    public void TestVampireStrategy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        Character character = new Character(new PathPosition(0, orderedPath));
        double health = character.getHealth();

        CritStrategy crit = new VampireStrategy();
        crit.applyCrit(character);

        for (int i = 0; i < 6; i++) {
            crit.processCrit();
        }

        assertTrue(health > character.getHealth());
    }

}
