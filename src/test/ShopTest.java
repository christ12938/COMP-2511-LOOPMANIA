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
import unsw.loopmania.Items.HealthPotion;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.TheOneRing;


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

        Shop shop = World.loadShop()
        // when shop is initialised, 3 items are automatiicaly generated to 
        // for user to buy, however for this test case 
        assertEquals(20, shop.getShopBuyPrice(SWORD));
        assertEquals(20, shop.getShopBuyPrice(STAKE));
        assertEquals(20, shop.getShopBuyPrice(STAFF));
        assertEquals(20, shop.getShopBuyPrice(ARMOUR));
        assertEquals(20, shop.getShopBuyPrice(HELMET));
        assertEquals(20, shop.getShopBuyPrice(SHIELD));
        assertEquals(20, shop.getShopBuyPrice(HEALTH_POTION));

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

        Shop shop = World.loadShop()
        // when shop is initialised, 3 items are automatiicaly generated to 
        // for user to buy, however for this test case 
        assertEquals(5, shop.getShopSellPrice(SWORD));
        assertEquals(5, shop.getShopSellPrice(STAKE));
        assertEquals(5, shop.getShopSellPrice(STAFF));
        assertEquals(5, shop.getShopSellPrice(ARMOUR));
        assertEquals(5, shop.getShopSellPrice(HELMET));
        assertEquals(5, shop.getShopSellPrice(SHIELD));
        assertEquals(5, shop.getShopSellPrice(HEALTH_POTION));
        assertEquals(20, shop.getShopSellPrice(THE_ONE_RING));

        

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

        Shop shop = World.openShop();

        Item item1 = shop.getSaleItem(1);
        Item item2 = shop.getSaleItem(2);
        Item item3 = shop.getSaleItem(3);

        shop.buyItem(1);
        amount -= shop.getShopBuyPrice(item1.getItemType());
        assertEquals(World.getGold(), amount);  
        assertSame(World.getUnequippedItemByCoordinates(1, 0), item1);
        

        shop.buyItem(2);
        amount -= shop.getShopBuyPrice(item2.getItemType());
        assertEquals(World.getGold(), amount);
        assertSame(World.getUnequippedItemByCoordinates(2, 0), item2);
        
        shop.buyItem(3);
        amount -= shop.getShopBuyPrice(item3.getItemType());
        assertEquals(World.getGold(), amount);
        assertSame(World.getUnequippedItemByCoordinates(3, 0), item3);
       

        assertEquals(amount, 100 - shop.getShopBuyPrice(item1.getItemType()) - 
                                    shop.getShopBuyPrice(item1.getItemType()) - 
                                    shop.getShopBuyPrice(item1.getItemType()));
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


        Shop shop = World.openShop();

        Helmet helmet = World.addUnequippedHelmet();
        Armour armour = World.addUnequippedArmour();
        Consumables pot = World.addUnequippedHealthPotion();
        Shield shield = World.addUnequippedShield();
        Staff staff = World.addUnequippedStaff();
        Stake stake = World.addUnequippedStake();
        Sword sword = World.addUnequippedSword();
        TheOneRing ring = World.addUnequippedTheOneRing();
        
        int amount = 0;

        shop.sellItem(helmet);
        amount += shop.getShopSellPrice(HELMET);
        assertEquals(World.getGold(), amount);

        shop.sellItem(armour);
        amount += shop.getShopSellPrice(ARMOUR);
        assertEquals(World.getGold(), amount);

        shop.sellItem(shield);
        amount += shop.getShopSellPrice(SHIELD);
        assertEquals(World.getGold(), amount);

        shop.sellItem(sword);
        amount += shop.getShopSellPrice(SWORD);
        assertEquals(World.getGold(), amount);

        shop.sellItem(stake);
        amount += shop.getShopSellPrice(STAKE);
        assertEquals(World.getGold(), amount);

        shop.sellItem(staff);
        amount += shop.getShopSellPrice(STAFF);
        assertEquals(World.getGold(), amount);

        shop.sellItem(ring);
        amount += shop.getShopSellPrice(THE_ONE_RING);
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


        Shop shop = World.openShop();
        Sword sword = World.addUnequippedSword();
        World.removeUnequippedInventoryItemByCoordinates(1, 0);

        assertFalse(shop.sell(sword));
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


        Shop shop = World.openShop();
        assertFalse(shop.buyItem(1));
        assertFalse(shop.buyItem(2));
        assertFalse(shop.buyItem(3));    

        assertEquals(0, World.getGold());
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


        Shop shop = World.openShop();
        Sword sword = World.addUnequippedSword();
        Sword sword2 = World.addUnequippedSword();
        Shield shield = World.addUnequippedShield();
        Helmet helmet = World.addUnequippedHelmet();
        TheOneRing ring = World.addUnequippedTheOneRing();

        assertTrue(shop.sellItem(sword));
        assertTrue(shop.sellItem(sword2));
        assertTrue(shop.sellItem(shield));
        assertTrue(shop.sellItem(helmet));
        assertTrue(shop.sellItem(ring));
        assertEquals(40, World.getGold());  
        
        Item item1 = shop.getSaleItem(1);
        Item item2 = shop.getSaleItem(2);
        Item item3 = shop.getSaleItem(3);

        assertTrue(shop.buyItem(1));
        assertTrue(shop.buyItem(2));
        
        assertEqual(World.getGold(), shop.getShopBuyPrice(item1.getItemType()) + shop.getShopBuyPrice(item2.getItemType()))
        assertSame(item1, World.getUnequippedItemByCoordinates(1, 0));
        assertSame(item2, World.getUnequippedItemByCoordinates(2, 0));
    }
}