package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.StaticEntity;

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
        d.addUnequippedConumsables();
        d.removeUnequippedInventoryItemByCoordinates(0, 0);
        d.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestAddAndRemoveGold() {
        // shift gold into a class
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        d.addGold(100);
        assertEquals(d.getGold, 100);
        d.removeGold(45)
        assertEquals(d.getGold, 55);
        d.removeGold(100)
        assertEquals(d.getGold, 55);
    }
}
