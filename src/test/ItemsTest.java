package test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;


import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.ItemType;
import unsw.loopmania.Character;


public class ItemsTest {

    @Test
    public void TestRemoveAndAddSword(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedSword().getItemType(), ItemType.SWORD);
        assertSame(World.addUnequippedSword().getItemType(), ItemType.SWORD);
        
        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
        
    }

    @Test
    public void TestRemoveAndAddStake(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedStake().getItemType(), ItemType.STAKE);
        assertSame(World.addUnequippedStake().getItemType(), ItemType.STAKE);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddStaff(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedStaff().getItemType(), ItemType.STAFF);
        assertSame(World.addUnequippedStaff().getItemType(), ItemType.STAFF);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddHelmet(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedHelmet().getItemType(), ItemType.HELMET);
        assertSame(World.addUnequippedHelmet().getItemType(), ItemType.HELMET);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddShield(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedShield().getItemType(), ItemType.SHIELD);
        assertSame(World.addUnequippedShield().getItemType(), ItemType.SHIELD);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddArmour(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedArmour().getItemType(), ItemType.ARMOUR);
        assertSame(World.addUnequippedArmour().getItemType(), ItemType.ARMOUR);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddHealthPotion(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedHealthPotion().getItemType(), ItemType.HEALTH_POTION);
        assertSame(World.addUnequippedHealthPotion().getItemType(), ItemType.HEALTH_POTION);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestAddAndRemoveGold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));

        assertSame(true,testCharacter.addGold(100));
        assertEquals(testCharacter.getGold(), 100);
        assertSame(true,testCharacter.minusGold(45));
        assertEquals(testCharacter.getGold(), 55);
        assertSame(false,testCharacter.minusGold(100));
        assertEquals(testCharacter.getGold(), 55);
    }

    @Test
    public void TestAddTooMuchGold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        assertSame(true, testCharacter.addGold(Integer.MAX_VALUE - 5));
        assertEquals(testCharacter.getGold(), Integer.MAX_VALUE - 5);
        assertSame(true, testCharacter.addGold(6));
        assertEquals(testCharacter.getGold(), Integer.MAX_VALUE);
        assertEquals(false, testCharacter.addGold(1231));
        
    }

    @Test
    public void TestTakeAwayTooMuchGold() {
        
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));

        assertSame(true, testCharacter.addGold(50));
        assertEquals(testCharacter.getGold(), 50);
        assertSame(true, testCharacter.minusGold(48));
        assertEquals(testCharacter.getGold(), 2);
        assertEquals(false, testCharacter.minusGold(6));
    }
    
    @Test
    public void TestAddItemToFullInvetory() {
        
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);
        
        for (int x = 0; x < 16; x++) {
            World.addUnequippedHelmet();
        }

        World.addUnequippedHelmet();
        assertSame(5, World.getGold());
        assertSame(10, World.getExperience());
    }



    
}
