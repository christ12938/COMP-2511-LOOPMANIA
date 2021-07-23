package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.HerosCastle;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Slug;

public class AlliedSoldierTest {
    @Test
    public void TestGetHealth() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        AlliedSoldier soldier = new AlliedSoldier(new PathPosition(0, orderedPath), 0);

        assertTrue(soldier.getCurrentHealth() == 30);
    }

    @Test
    public void TestDealDamage() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);        
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        AlliedSoldier soldier = new AlliedSoldier(new PathPosition(0, orderedPath), 0);
        //TODO: I changed the parameters so the second value is the position of heros castle
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        double health = slug.getCurrentHealth();

        soldier.dealDamage(slug);

        assertTrue(health > slug.getCurrentHealth());
    }

    @Test 
    public void TestTakeDamage() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        AlliedSoldier soldier = new AlliedSoldier(new PathPosition(0, orderedPath), 0);
        double health = soldier.getCurrentHealth();

        soldier.takeDamage(5, new Slug(new PathPosition(0, orderedPath)));

        assertTrue((health - 5) == soldier.getCurrentHealth());
    }

    @Test
    public void TestGetSlot() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        AlliedSoldier soldier = new AlliedSoldier(new PathPosition(0, orderedPath), 0);

        int pos = soldier.getSlotPosition().get();

        assertTrue(pos == 0);
    }

    @Test
    public void TestSetSlot() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);

        AlliedSoldier soldier = new AlliedSoldier(new PathPosition(0, orderedPath), 0);

        soldier.setSlotPosition(1);

        assertTrue(soldier.getSlotPosition().get() == 1);
    }
}
