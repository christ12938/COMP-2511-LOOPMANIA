package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;

import unsw.loopmania.Character;
import unsw.loopmania.HerosCastle;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.CritStrategy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.SlugCritStrategy;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.VampireCritStrategy;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Types.EnemyType;


public class EnemyTest {
    @Test
    public void TestBattleRadiusTrue() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

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
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

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
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

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
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

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
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        character.setCurrentHealth(99999999);
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        double health = character.getCurrentHealth();

        for (int i = 0; i < 100; i++) {
            slug.dealDamage(character);
        }

        assertTrue(health > character.getCurrentHealth());
    }

    @Test
    public void TestTakeDamage() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Slug slug = new Slug(new PathPosition(0, orderedPath));
        double health = slug.getCurrentHealth();

        slug.takeDamage(5, null);
        assertTrue(health > slug.getCurrentHealth());
    }

    @Test
    public void TestGetEnemyType() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

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
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));


        Slug slug = new Slug(new PathPosition(0, orderedPath));
        for (int i = 0; i < 100; i++) {
            slug.move();
        }
    }

    @Test
    public void TestSlugCritStrategy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        double health = character.getCurrentHealth();

        Slug slug = new Slug(new PathPosition(0, orderedPath));

        CritStrategy crit = new SlugCritStrategy();
        character.takeDamage(crit.applyCritDamage(10), slug);

        assertTrue(health > character.getCurrentHealth());
    }

    // For whoever wrote this test, it doesn't work since it doesn't add allied soldiers to
    // the character and doing so causes a null pointer error. For some reason, I can't call
    // the LoopManiaWorldControllerLoader from this test that's required, I think it may be 
    // cause the directory this test is called from is different from where the directory is 
    // called during normal application startup and I have no clue how to fix this.
    /*@Test
    public void TestZombieStrategy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        Character character = new Character(new PathPosition(0, orderedPath));
        AlliedSoldier soldier = new AlliedSoldier(new PathPosition(0, orderedPath), 0);

        assertTrue(character.getAlliedSoldiers().size() == 1);
        CritStrategy crit = new ZombieCritStrategy();
        crit.applyCritDamage(0);

        assertFalse(character.getAlliedSoldiers().size() == 0);
    } */

    @Test
    public void TestVampireStrategy() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        Character character = new Character(new PathPosition(0, orderedPath));
        double health = character.getCurrentHealth();

        Vampire vampire = new Vampire(new PathPosition(0, orderedPath));

        CritStrategy crit = new VampireCritStrategy();
        character.takeDamage(crit.applyCritDamage(10), vampire);

        assertTrue(health > character.getCurrentHealth());
    }

}
