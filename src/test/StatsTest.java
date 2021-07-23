package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Character;


public class StatsTest {

    @Test
    public void TestSwordStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Sword sword = World.addUnequippedSword();
        assertEquals(5, sword.getAttack());
        World.equipItem(sword);
        assertEquals(10, World.getCharacterAttack());

        World.unequipEquippableItem(sword);
        assertEquals(5, World.getCharacterAttack());
        
    }

    @Test
    public void TestStaffStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Staff staff = World.addUnequippedStaff();
        assertEquals(1, staff.getAttack());
        
        World.equipEquippableItem(staff);
        assertEquals(6, World.getCharacterAttack());

        World.unequipEquippableItem(staff);
        assertEquals(5, World.getCharacterAttack());
    }

    @Test
    public void TestStakeStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Stake stake = World.addUnequippedStake();
        assertEquals(3, stake.getAttack());
        
        World.equipEquippableItem(stake);
        assertEquals(8, World.getCharacterAttack());

        World.unequipEquippableItem(stake);
        assertEquals(5, World.getCharacterAttack());
    }


    
    @Test
    public void TestArmourState(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Armour armour = World.addUnequippedArmour();
        assertEquals(3, armour.getDefense());
        
        World.equipEquippableItem(armour);
        assertEquals(8, World.getCharacterDefense());

        World.unequipEquippableItem(armour);
        assertEquals(5, World.getCharacterDefense());
    }
    
    @Test

    public void TestHelmetStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Helmet helmet = World.addUnequippedHelmet();
        assertEquals(3, helmet.getDefense());
        
        World.equipEquippableItem(helmet);
        assertEquals(8, World.getCharacterDefense());

        World.unequipEquippableItem(helmet);
        assertEquals(5, World.getCharacterDefense());
    }

    @Test
    public void TestShieldStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Shield shield = World.addUnequippedShield();
        assertEquals(3, shield.getDefense());
        
        World.equipEquippableItem(shield);
        assertEquals(8, World.getCharacterDefense());

        World.unequipEquippableItem(shield);
        assertEquals(5, World.getCharacterDefense());
    
    }

    @Test

    public void TestBaseStatesCharacter() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        assertEquals(100, World.getCharacterCurrentHp(), "comparing two doubles");
        assertEquals(5, World.getCharacterDefense());
        assertEquals(5, World.getCharacterAttack());
    }

    @Test
    public void TestCharacterStatsWithEquipment() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Armour armour = World.addUnequippedArmour();
        Helmet helmet = World.addUnequippedHelmet();
        Sword sword = World.addUnequippedSword();
        Shield shield = World.addUnequippedShield();
        
        World.equipEquippableItem(armour);
        World.equipEquippableItem(helmet);
        World.equipEquippableItem(sword);
        World.equipEquippableItem(shield);
        
        assertEquals(100, World.getCharacterCurrentHp(), "comparing two doubles");
        assertEquals(14, World.getCharacterDefense());
        assertEquals(10, World.getCharacterAttack());
        
        World.unequipEquippableItem(armour); 
        World.unequipEquippableItem(helmet); 
        World.unequipEquippableItem(sword); 
        World.unequipEquippableItem(shield); 

        assertEquals(100, World.getCharacterCurrentHp(), "comparing two doubles");
        assertEquals(5, World.getCharacterDefense());
        assertEquals(5, World.getCharacterAttack());
    }

    @Test
    public void TestCharacterStatsWithEquipmentv2() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Armour armour = World.addUnequippedArmour();
        Helmet helmet = World.addUnequippedHelmet();
        Sword sword = World.addUnequippedSword();
        Shield shield = World.addUnequippedShield();
        
        World.equipEquippableItem(armour);
        World.equipEquippableItem(helmet);
        World.equipEquippableItem(shield);
        
        assertEquals(14, World.getCharacterDefense());
        assertEquals(5, World.getCharacterAttack());

        World.unequipEquippableItem(armour);
        World.unequipEquippableItem(helmet);
        World.unequipEquippableItem(shield);
        World.equipEquippableItem(sword);

        assertEquals(10, World.getCharacterAttack());
        assertEquals(5, World.getCharacterDefense());

        World.equipEquippableItem(shield);

        assertEquals(10, World.getCharacterAttack());
        assertEquals(8, World.getCharacterDefense());
        
        World.equipEquippableItem(armour);
        
        assertEquals(10, World.getCharacterAttack());
        assertEquals(11, World.getCharacterDefense());
    }
}
