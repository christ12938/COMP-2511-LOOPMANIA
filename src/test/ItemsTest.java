package test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.TheOneRing;
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
    public void TestRemoveAndAddTheOneRing() {
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedTheOneRing().getItemType(), ItemType.THE_ONE_RING);
        assertSame(World.addUnequippedTheOneRing().getItemType(), ItemType.THE_ONE_RING);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);
    }

    @Test
    public void TestRemoveAndAddAnduril() {
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedAnduril().getItemType(), ItemType.ANDURIL);
        assertSame(World.addUnequippedAnduril().getItemType(), ItemType.ANDURIL);

        World.removeUnequippedInventoryItemByCoordinates(0, 0);
        World.removeUnequippedInventoryItemByCoordinates(1, 0);

    }

    @Test
    public void TestRemoveAndAddTreeStump() {
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertSame(World.addUnequippedTreeStump().getItemType(), ItemType.TREE_STUMP);
        assertSame(World.addUnequippedTreeStump().getItemType(), ItemType.TREE_STUMP);

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
        
        assertTrue(testCharacter.addGold(100));
        assertEquals(testCharacter.getGold(), 100);
        assertTrue(testCharacter.minusGold(45));
        assertEquals(testCharacter.getGold(), 55);
        assertFalse(testCharacter.minusGold(100));
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
        assertTrue(testCharacter.addGold(Integer.MAX_VALUE - 5));
        assertEquals(testCharacter.getGold(), Integer.MAX_VALUE - 5);
        assertTrue(testCharacter.addGold(6));
        assertEquals(testCharacter.getGold(), Integer.MAX_VALUE);
        assertFalse(testCharacter.addGold(1231));
        
    }

    @Test
    public void TestTakeAwayTooMuchGold() {
        
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));

        assertTrue(testCharacter.addGold(50));
        assertEquals(testCharacter.getGold(), 50);
        assertTrue(testCharacter.minusGold(48));
        assertEquals(testCharacter.getGold(), 2);
        assertFalse(testCharacter.minusGold(6));
    }

    /*@Test
    public void TestAddRandomItems() {
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        
        Item item1 = World.loadRandomUnenquippedInventoryItem();
        Item item2 = World.loadRandomUnenquippedInventoryItem();
        Item item3 = World.loadRandomUnenquippedInventoryItem();
        
        if (item1.getItemType() != ItemType.GOLD) {
            World.removeUnequippedInventoryItem(item1);
        }

        if (item2.getItemType() != ItemType.GOLD) {
            World.removeUnequippedInventoryItem(item2);
        }

        if (item3.getItemType() != ItemType.GOLD) {
            World.removeUnequippedInventoryItem(item3);
        }
    }*/
    
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
        assertEquals(5, World.getGold());
        assertEquals(10, World.getExperience());

        World.addUnequippedArmour();
        assertEquals(10, World.getGold());
        assertEquals(20, World.getExperience());

        World.addUnequippedHealthPotion();
        assertEquals(15, World.getGold());
        assertEquals(30, World.getExperience());

        World.addUnequippedShield();
        assertEquals(20, World.getGold());
        assertEquals(40, World.getExperience());

        World.addUnequippedStaff();
        assertEquals(25, World.getGold());
        assertEquals(50, World.getExperience());

        World.addUnequippedStake();
        assertEquals(30, World.getGold());
        assertEquals(60, World.getExperience());

        World.addUnequippedSword();
        assertEquals(35, World.getGold());
        assertEquals(70, World.getExperience());

        World.addUnequippedTheOneRing();
        assertEquals(40, World.getGold());
        assertEquals(80, World.getExperience());
        
    }

    @Test
    public void TestHealthPotion() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        World.addUnequippedHealthPotion();
        World.decreaseCharacterHp(50);
        World.useHealthPotion();
        assertEquals(55,World.getCharacterCurrentHp(), "comparing two doubles");
    }

    @Test
    public void TestMultipleHealthPotion() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        World.addUnequippedHealthPotion();
        World.addUnequippedHealthPotion();
        World.addUnequippedHealthPotion();
        World.addUnequippedHealthPotion();
        World.addUnequippedHealthPotion();
        
        World.decreaseCharacterHp(90);
       
        World.useHealthPotion();
        World.useHealthPotion();
        World.useHealthPotion();
        World.useHealthPotion();
        World.useHealthPotion();
        
        assertEquals(35, World.getCharacterCurrentHp(), "comparing two doubles");
    }
    
    @Test
    public void TestNoneHealthPotion() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);
        
        World.decreaseCharacterHp(90);
        World.useHealthPotion();
        
        assertEquals(10,World.getCharacterCurrentHp(), "comparing two doubles");
    }

    // Test dying with the One Ring
    @Test
    public void TestTheOneRingEquipped() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld world = new LoopManiaWorld(4, 5, orderedPath);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        character.setWorld(world);
        Slug slug = new Slug(new PathPosition(1, orderedPath));

        TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1), null);

        character.equip(theOneRing);

        character.takeDamage(110, slug);

        assertEquals(character.getCurrentHealth(), character.getMaxHealth());
    }

    // Test dying with the One Ring
    @Test
    public void TestTheOneRingUnequipped() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld world = new LoopManiaWorld(4, 5, orderedPath);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        character.setWorld(world);
        Slug slug = new Slug(new PathPosition(1, orderedPath));

        world.addUnequippedTheOneRing();

        character.takeDamage(110, slug);

        assertEquals(character.getCurrentHealth(), character.getMaxHealth());
    }

    // Test offensive subtype behaviour
    @Test
    public void TestItemSubTypeEquipOffensive() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld world = new LoopManiaWorld(4, 5, orderedPath);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        character.setWorld(world);

        TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1), ItemType.SWORD);

        int attack = character.getAttack();        

        character.equip(theOneRing);

        assertEquals(character.getAttack(), attack + ItemType.SWORD.getAttack());

        character.unequip(theOneRing);

        assertEquals(character.getAttack(), attack);
    }

    // Test defensive subtype
    @Test
    public void TestItemSubTypeEquipDefensive() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld world = new LoopManiaWorld(4, 5, orderedPath);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        character.setWorld(world);

        TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1), ItemType.SHIELD);

        int defence = character.getDefense();

        character.equip(theOneRing);

        assertEquals(character.getDefense(), defence + ItemType.SHIELD.getDefense());

        character.unequip(theOneRing);

        assertEquals(character.getDefense(), defence);
    }

    // Test next attack trance
    @Test
    public void TestNextAttackTrance() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld world = new LoopManiaWorld(4, 5, orderedPath);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        character.setWorld(world);

        Staff staff = new Staff(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        character.equip(staff);

        for (int i = 0; i < 100; i++) {
            character.isNextAttackTrance();
        }
    }
}
