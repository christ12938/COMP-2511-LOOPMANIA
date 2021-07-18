package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

import static unsw.loopmania.Types.ItemType.*;
import unsw.loopmania.Character;
import unsw.loopmania.Items.Sword;



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

        assertEquals(10, World.getBuyPrice(SWORD));
        assertEquals(10, World.getBuyPrice(STAKE));
        assertEquals(10, World.getBuyPrice(STAFF));
        assertEquals(10, World.getBuyPrice(ARMOUR));
        assertEquals(10, World.getBuyPrice(HELMET));
        assertEquals(10, World.getBuyPrice(SHIELD));
        assertEquals(10, World.getBuyPrice(HEALTH_POTION));

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
    public void TestShopBuyingAndSellingSword(){
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
       
        World.buySword();
        amount -= World.getBuyPrice(SWORD);
        assertEquals(World.getGold(), amount); 
        assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), SWORD);
        
        World.sellItem(SWORD);
        amount += World.getSellPrice(SWORD);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
    }

    @Test
    public void TestShopBuyingAndSellingStake(){
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
       
        World.buyStake();
        amount -= World.getBuyPrice(STAKE);
        assertEquals(World.getGold(), amount); 
        assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), STAKE);
        
        World.sellItem(STAKE);
        amount += World.getSellPrice(STAKE);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
    }

    @Test
    public void TestShopBuyingAndSellingStaff(){
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
       
        World.buyStaff();
        amount -= World.getBuyPrice(STAFF);
        assertEquals(World.getGold(), amount); 
        assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), STAFF);
        
        World.sellItem(STAFF);
        amount += World.getSellPrice(STAFF);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
    }

    @Test
    public void TestShopBuyingAndSellingArmour(){
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
       
        World.buyArmour();
        amount -= World.getBuyPrice(ARMOUR);
        assertEquals(World.getGold(), amount); 
        assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), ARMOUR);
        
        World.sellItem(ARMOUR);
        amount += World.getSellPrice(ARMOUR);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
    }

    @Test
    public void TestShopBuyingAndSellingHelmet(){
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
       
        World.buyHelmet();
        amount -= World.getBuyPrice(HELMET);
        assertEquals(World.getGold(), amount); 
        assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), HELMET);
        
        World.sellItem(HELMET);
        amount += World.getSellPrice(HELMET);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
    }

    @Test
    public void TestShopBuyingAndSellingShield(){
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
       
        World.buyShield();
        amount -= World.getBuyPrice(SHIELD);
        assertEquals(World.getGold(), amount); 
        assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), SHIELD);
        
        World.sellItem(SHIELD);
        amount += World.getSellPrice(SHIELD);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
    }

    @Test
    public void TestShopBuyingAndSellingHealthPotion(){
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
       
        World.buyHealthPotion();
        amount -= World.getBuyPrice(HEALTH_POTION);
        assertEquals(World.getGold(), amount); 
        
        
        World.sellItem(HEALTH_POTION);
        amount += World.getSellPrice(HEALTH_POTION);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
    }

    @Test
    public void TestShopSellingRing(){
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
       
        World.addUnequippedTheOneRing();
        
        World.sellItem(THE_ONE_RING);
        amount += World.getSellPrice(THE_ONE_RING);
        assertEquals(World.getGold(), amount);
        assertNull(World.getUnequippedItemByCoordinates(0, 0));
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

        World.buySword();
        amount -= World.getBuyPrice(SWORD);
        assertEquals(World.getGold(), amount); 
        assertSame(World.getUnequippedItemByCoordinates(0, 0).getItemType(), SWORD);
        

        World.buyShield();
        amount -= World.getBuyPrice(SHIELD);
        assertEquals(World.getGold(), amount);
        assertSame(World.getUnequippedItemByCoordinates(1, 0).getItemType(), SHIELD);
        
        
        World.buyHelmet();
        amount -= World.getBuyPrice(HELMET);
        assertEquals(World.getGold(), amount);
        assertSame(World.getUnequippedItemByCoordinates(2, 0).getItemType(), HELMET);

       

        assertEquals(amount, 100 - World.getBuyPrice(SWORD) - 
                                World.getBuyPrice(SHIELD) - 
                                World.getBuyPrice(HELMET));
    }

    @Test
    public void TestShopBuyingMultipleSameItem(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);
        World.addGold(1000);

        World.loadShop();
        
        assertTrue(World.buyArmour());
        assertTrue(World.buyArmour());
        assertTrue(World.buyShield());
        assertTrue(World.buyShield());
        assertTrue(World.buyHelmet());
        assertTrue(World.buyHelmet());
        assertTrue(World.buyStaff());
        assertTrue(World.buyStaff());
        assertTrue(World.buySword());
        assertTrue(World.buySword());
        assertTrue(World.buyStake());
        assertTrue(World.buyStake());
        assertTrue(World.buyHealthPotion());
        assertTrue(World.buyHealthPotion());
        
        assertEquals(World.getGold(), 860);
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

        World.addUnequippedHelmet();
        World.addUnequippedArmour();
        World.addUnequippedHealthPotion();
        World.addUnequippedShield();
        World.addUnequippedStaff();
        World.addUnequippedStake();
        World.addUnequippedSword();
        World.addUnequippedTheOneRing();
        
        int amount = 0;

        assertTrue(World.sellItem(HELMET));
        amount += World.getSellPrice(HELMET);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(ARMOUR));
        amount += World.getSellPrice(ARMOUR);
        assertEquals(World.getGold(), amount);


        assertTrue(World.sellItem(SHIELD));
        amount += World.getSellPrice(SHIELD);
        assertEquals(World.getGold(), amount);


        assertTrue(World.sellItem(SWORD));
        amount += World.getSellPrice(SWORD);
        assertEquals(World.getGold(), amount);

        assertTrue(World.sellItem(STAKE));
        amount += World.getSellPrice(STAKE);
        assertEquals(World.getGold(), amount);


        assertTrue(World.sellItem(STAFF));
        amount += World.getSellPrice(STAFF);
        assertEquals(World.getGold(), amount);


        assertTrue(World.sellItem(THE_ONE_RING));
        amount += World.getSellPrice(THE_ONE_RING);
        assertEquals(World.getGold(), amount);


        assertTrue(World.sellItem(HEALTH_POTION));
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
        World.addUnequippedSword();
        World.removeUnequippedInventoryItemByCoordinates(0, 0);

        assertFalse(World.sellItem(SWORD));
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
        assertFalse(World.buySword());
        assertFalse(World.buyStaff());
        assertFalse(World.buyShield());    

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

        assertFalse(World.buyHealthPotion());
        assertFalse(World.buyHelmet());
        assertFalse(World.buyStake());   
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
        World.addUnequippedSword();
        World.addUnequippedSword();
        World.addUnequippedShield();
        World.addUnequippedHelmet();
        World.addUnequippedTheOneRing();

        assertTrue(World.sellItem(SWORD));
        assertTrue(World.sellItem(SWORD));
        assertTrue(World.sellItem(SHIELD));
        assertTrue(World.sellItem(HELMET));
        assertTrue(World.sellItem(THE_ONE_RING));
        assertEquals(40, World.getGold());  
        

        assertTrue(World.buySword());
        assertTrue(World.buyShield());
        
        assertEquals(World.getGold(), 20);
        assertSame(SWORD, World.getUnequippedItemByCoordinates(0, 0).getItemType());

        assertSame(SHIELD, World.getUnequippedItemByCoordinates(1, 0).getItemType());

    }
}