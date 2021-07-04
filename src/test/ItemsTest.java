package test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;


public class ItemsTest {

    @Test
    public void TestRemoveAndAddSword(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addUnequippedSword();
        d.addUnequippedSword();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
        
    }

    @Test
    public void TestRemoveAndAddStake(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addUnequippedStake();
        d.addUnequippedStake();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddStaff(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addUnequippedStaff();
        d.addUnequippedStaff();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddHelmet(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addUnequippedHelmet();
        d.addUnequippedHelmet();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddShield(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addUnequippedShield();
        d.addUnequippedShield();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddArmour(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addUnequippedArmour();
        d.addUnequippedArmour();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddConsumables(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addUnequippedConsumables();
        d.addUnequippedConsumables();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestAddAndRemoveGold() {
        
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(true,d.addGold(100));
        assertEquals(d.getGold(), 100);
        assertSame(true,d.minusGold(45));
        assertEquals(d.getGold(), 55);
        assertSame(false,d.minusGold(100));
        assertEquals(d.getGold(), 55);
    }

    @Test
    public void TestAddTooMuchGold() {
        
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(true,d.addGold(Integer.MAX_VALUE - 5));
        assertEquals(d.getGold(), Integer.MAX_VALUE - 5);
        assertSame(true,d.addGold(6));
        assertEquals(d.getGold(), Integer.MAX_VALUE);
        assertEquals(false, d.addGold(1231));
        
    }
}
