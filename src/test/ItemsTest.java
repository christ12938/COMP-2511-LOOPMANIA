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


    /*
    Testing that a sword is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingSword(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedSword();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.SWORD);
    }

    /*
    Testing that a sword is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingSword(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Sword sword = World.addUnequippedSword();
        World.removeUnequippedInventoryItem(sword);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }



    /*
    Testing that a stake is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingStake(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedStake();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.STAKE);
    }

    /*
    Testing that a stake is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingStake(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Stake stake = World.addUnequippedStake();
        World.removeUnequippedInventoryItem(stake);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that a staff is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingStaff(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedStaff();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.STAFF);
    }

    /*
    Testing that a staff is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingStaff(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Staff staff = World.addUnequippedStaff();
        World.removeUnequippedInventoryItem(staff);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }


    /*
    Testing that a helmet is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingHelmet(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedHelmet();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.HELMET);
    }

    /*
    Testing that a helmet is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingHelmet(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Helmet helmet = World.addUnequippedHelmet();
        World.removeUnequippedInventoryItem(helmet);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that a shield is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingShield(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedShield();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.SHIELD);
    }

    /*
    Testing that a staff is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingShield(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Shield shield = World.addUnequippedShield();
        World.removeUnequippedInventoryItem(shield);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that an armour is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingArmour(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedArmour();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.ARMOUR);
    }

    /*
    Testing that an armour is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingArmour(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Armour armour = World.addUnequippedArmour();
        World.removeUnequippedInventoryItem(armour);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that a HealthPotion is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingHealthPotion(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedHealthPotion();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.HEALTH_POTION);
    }

    /*
    Testing that a HealthPotion is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingHealthPotion(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Consumables pot = World.addUnequippedHealthPotion();
        World.removeUnequippedInventoryItem(pot);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that a TheOneRing is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingTheOneRing(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedTheOneRing();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.THE_ONE_RING);
    }

    /*
    Testing that a TheOneRing is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingTheOneRing(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        TheOneRing ring = World.addUnequippedTheOneRing();
        World.removeUnequippedInventoryItem(ring);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that an Anduril is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingAnduril(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedAnduril();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.ANDURIL);
    }

    /*
    Testing that an Anduril is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingAnduril(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Anduril anduril = World.addUnequippedAnduril();
        World.removeUnequippedInventoryItem(anduril);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that a DoggieCoin is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingDoggieCoin(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedDoggieCoin();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.DOGGIE_COIN);
    }

    /*
    Testing that a DoggieCoin is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingDoggieCoin(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        DoggieCoin doggieCoin = World.addUnequippedDoggieCoin();
        World.removeUnequippedInventoryItem(doggieCoin);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /*
    Testing that a TreeStump is successfully added to the unequippeditem inventory.
    */
    @Test
    public void TestAddingTreeStump(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        World.addUnequippedTreeStump();
        assertSame(World.getUnequippedItemTypeByCoordinates(0, 0), ItemType.TREE_STUMP);
    }

    /*
    Test that a TreeStump is successfully removed from unequippeditem inventory.
    */
    @Test
    public void TestRemovingTreeStump(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        TreeStump treeStump = World.addUnequippedTreeStump();
        World.removeUnequippedInventoryItem(treeStump);
        assertEquals(World.getUnequippedInventoryItems().size(), 0);
    }

    /* 
    Testing gold is successfully added.
    */
    @Test
    public void TestAddGold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        
        assertTrue(testCharacter.addGold(100));
    }


    /* 
    Testing that the specific amount of gold is added correctly.
    */
    @Test
    public void TestAddGoldCorrectAmount() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        
        testCharacter.addGold(100);
        assertEquals(testCharacter.getGold(), 100);
    }

    /* 
    Testing gold is successfully removed.
    */
    @Test
    public void TestremoveGold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        
        testCharacter.addGold(100);
        assertTrue(testCharacter.minusGold(100));
    }


    /* 
    Testing that the specific amount of gold is removed correctly.
    */
    @Test
    public void TestRemoveGoldCorrectAmount() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        
        testCharacter.addGold(100);
        testCharacter.minusGold(12);
        assertEquals(testCharacter.getGold(), 88);
    }


    /*
    Testing that if a specific amount of gold is being add that cause the character's gold to 
    go over the limit, their gold is set to the limit.
    */
    @Test
    public void TestAddTooMuchGold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        testCharacter.addGold(Integer.MAX_VALUE - 5);
        
        testCharacter.addGold(6);
        assertEquals(testCharacter.getGold(), Integer.MAX_VALUE);
        
    }

    /*
    Test that when maxgold is obtained no more gold can be added.
    */
    @Test
    public void TestAddMaxGold() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        testCharacter.addGold(Integer.MAX_VALUE);
        
        assertFalse(testCharacter.addGold(6));
        
    }

    /*
    Test testing gold cannot be taken away from the user if the takeaway amount is greater than
    the amount of gold the character has.
    */
    @Test
    public void TestTakeAwayTooMuchGold() {
        
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));

        testCharacter.addGold(50);
        assertFalse(testCharacter.minusGold(54));
    }


    /*
    Test for correct amount of gold exp is added when item is obtained with a full inventory.
    */
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
    }

    /*
    Test that consuming a healthpotion restores 5 hp.
    */
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


    /*
    Test that consuming multiples healthpotion restores correct amount.
    */
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
    

    /*
    Test that no health is restored if user is trying to restore health with no health potions in 
    unquipped inventry.
    */
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
