package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

import static unsw.loopmania.Types.ItemType.*;
import unsw.loopmania.Character;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Consumables;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.TheOneRing;
import unsw.loopmania.Types.ItemType;


public class ShopTest {

    @Test
    public void TestShopItemCostPrices(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);
        
        World.loadShop();

        // when shop is initialised, 3 items are automatiicaly generated to 
        // for user to buy, however for this test case 

        assertEquals(20, World.getBuyPrice(SWORD));
        assertEquals(20, World.getBuyPrice(STAKE));
        assertEquals(20, World.getBuyPrice(STAFF));
        assertEquals(20, World.getBuyPrice(ARMOUR));
        assertEquals(20, World.getBuyPrice(HELMET));
        assertEquals(20, World.getBuyPrice(SHIELD));
        assertEquals(20, World.getBuyPrice(HEALTH_POTION));

    }

    @Test
    public void TestShopItemSellPrices(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        World.loadShop();
        // when shop is initialised, 3 items are automatiicaly generated to 
        // for user to buy, however for this test case 
        assertEquals(5, World.getSellPrice(SWORD));
        assertEquals(5, World.getSellPrice(STAKE));
        assertEquals(5, World.getSellPrice(STAFF));
        assertEquals(5, World.getSellPrice(ARMOUR));
        assertEquals(5, World.getSellPrice(HELMET));
        assertEquals(5, World.getSellPrice(SHIELD));
        assertEquals(5, World.getSellPrice(HEALTH_POTION));
        assertEquals(20, World.getSellPrice(THE_ONE_RING));

        

    }


    @Test
    public void TestShopBuyingItems(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        World.addGold(100);
        int amount = 100;

        World.loadShop();

        ItemType item1 = World.getSaleItem(1);
        ItemType item2 = World.getSaleItem(2);
        ItemType item3 = World.getSaleItem(3);

        
        World.buyItem(1);
        amount -= World.getBuyPrice(item1);
        assertEquals(World.getGold(), amount); 
        // note for these test this if statment is needed as getUnequippedItemByCoordinates
        // doesn't return equipables
        if (item1 != HEALTH_POTION) {
            assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), item1);
        }

        World.buyItem(2);
        amount -= World.getBuyPrice(item2);
        assertEquals(World.getGold(), amount);
        if (item2 != HEALTH_POTION) {
            assertSame(World.getUnequippedItemByCoordinates(1, 0).getItemType(), item2);
        }
        
        World.buyItem(3);
        amount -= World.getBuyPrice(item3);
        assertEquals(World.getGold(), amount);
        if (item3 != HEALTH_POTION) {
            assertSame(World.getUnequippedItemByCoordinates(2, 0).getItemType(), item3);
        }

       

        assertEquals(amount, 100 - World.getBuyPrice(item1) - 
                                World.getBuyPrice(item1) - 
                                World.getBuyPrice(item1));
    }

    @Test
    public void TestShopSellingItems(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);


        World.loadShop();

        Helmet helmet = World.addUnequippedHelmet();
        Armour armour = World.addUnequippedArmour();
        Consumables pot = World.addUnequippedHealthPotion();
        Shield shield = World.addUnequippedShield();
        Staff staff = World.addUnequippedStaff();
        Stake stake = World.addUnequippedStake();
        Sword sword = World.addUnequippedSword();
        TheOneRing ring = World.addUnequippedTheOneRing();
        
        int amount = 0;

        assertTrue(World.sellItem(helmet));
        amount += World.getSellPrice(HELMET);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(armour));
        amount += World.getSellPrice(ARMOUR);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(shield));
        amount += World.getSellPrice(SHIELD);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(sword));
        amount += World.getSellPrice(SWORD);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(stake));
        amount += World.getSellPrice(STAKE);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(staff));
        amount += World.getSellPrice(STAFF);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(ring));
        amount += World.getSellPrice(THE_ONE_RING);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(pot));
        amount += World.getSellPrice(HEALTH_POTION);
        assertEquals(World.getGold(), amount);
        

        
    }

    @Test
    public void TestShopSellingItemsEmptyInvetory(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);


        World.loadShop();
        Sword sword = World.addUnequippedSword();
        World.removeUnequippedInventoryItemByCoordinates(0, 0);

        assertFalse(World.sellItem(sword));
        assertEquals(0, World.getGold());        
    }

    @Test
    public void TestShopBuyingWithNoMoney(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);


        World.loadShop();
        assertFalse(World.buyItem(1));
        assertFalse(World.buyItem(2));
        assertFalse(World.buyItem(3));    

        assertEquals(0, World.getGold());
    }

    @Test
    public void TestShopBuyingWithFullInvetory(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);
        World.addGold(100);

        World.loadShop();
        for (int x = 0; x < 16; x++) {
            World.addUnequippedHelmet();
        }

        assertFalse(World.buyItem(1));
        assertFalse(World.buyItem(2));
        assertFalse(World.buyItem(3));   
    }

    @Test
    public void TestShopSellingandBuyingItems(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);


        World.loadShop();
        Sword sword = World.addUnequippedSword();
        Sword sword2 = World.addUnequippedSword();
        Shield shield = World.addUnequippedShield();
        Helmet helmet = World.addUnequippedHelmet();
        TheOneRing ring = World.addUnequippedTheOneRing();

        assertTrue(World.sellItem(sword));
        assertTrue(World.sellItem(sword2));
        assertTrue(World.sellItem(shield));
        assertTrue(World.sellItem(helmet));
        assertTrue(World.sellItem(ring));
        assertEquals(40, World.getGold());  
        
        ItemType item1 = World.getSaleItem(1);
        ItemType item2 = World.getSaleItem(2);
        

        assertTrue(World.buyItem(1));
        assertTrue(World.buyItem(2));
        
        assertEquals(World.getGold(), 0);
        if (item1 != HEALTH_POTION) {
            assertSame(item1, World.getUnequippedItemByCoordinates(0, 0).getItemType());
        }
        
        if (item2 != HEALTH_POTION) {
            assertSame(item2, World.getUnequippedItemByCoordinates(1, 0).getItemType());
        }
    }
}