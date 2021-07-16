package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.javatuples.Pair;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Slug;

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
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        double health = character.getHealth();

        slug.dealDamage(character);
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
}
