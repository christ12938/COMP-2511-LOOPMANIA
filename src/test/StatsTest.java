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
        World.getCharacter().equip(sword);
        assertEquals(10, World.getCharacterAttack());

        World.getCharacter().unequip(sword);
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
        
        World.getCharacter().equip(staff);
        assertEquals(6, World.getCharacterAttack());

        World.getCharacter().unequip(staff);
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
        
        World.getCharacter().equip(stake);
        assertEquals(8, World.getCharacterAttack());

        World.getCharacter().unequip(stake);
        assertEquals(5, World.getCharacterAttack());
    }


    
    @Test
    public void TestArmourStat(){
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
        
        World.getCharacter().equip(armour);
        assertEquals(3, World.getCharacterDefense());

        World.getCharacter().unequip(armour);
        assertEquals(0, World.getCharacterDefense());
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
        
        World.getCharacter().equip(helmet);
        assertEquals(3, World.getCharacterDefense());

        World.getCharacter().unequip(helmet);
        assertEquals(0, World.getCharacterDefense());
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
        
        World.getCharacter().equip(shield);
        assertEquals(3, World.getCharacterDefense());

        World.getCharacter().unequip(shield);
        assertEquals(0, World.getCharacterDefense());
    
    }

    @Test
    public void TestAndruilStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Anduril anduril = World.addUnequippedAnduril();
        assertEquals(15, anduril.getAttack());
        
        World.getCharacter().equip(anduril);
        assertEquals(20, World.getCharacterDefense());

        World.getCharacter().unequip(anduril);
        assertEquals(5, World.getCharacterDefense());
    
    }

    @Test
    public void TestTreeStuempStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        TreeStump treeStump = World.addUnequippedStump();
        assertEquals(15, treeStump.getDefense());
        
        World.getCharacter().equip(treeStump);
        assertEquals(20, World.getCharacterDefense());

        World.getCharacter().unequip(treeStump);
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
        assertEquals(0, World.getCharacterDefense());
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
        
        World.getCharacter().equip(armour);
        World.getCharacter().equip(helmet);
        World.getCharacter().equip(sword);
        World.getCharacter().equip(shield);
        
        assertEquals(100, World.getCharacterCurrentHp(), "comparing two doubles");
        assertEquals(9, World.getCharacterDefense());
        assertEquals(10, World.getCharacterAttack());
        
        World.getCharacter().unequip(armour); 
        World.getCharacter().unequip(helmet); 
        World.getCharacter().unequip(sword); 
        World.getCharacter().unequip(shield); 

        assertEquals(100, World.getCharacterCurrentHp(), "comparing two doubles");
        assertEquals(0, World.getCharacterDefense());
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
        
        World.getCharacter().equip(armour);
        World.getCharacter().equip(helmet);
        World.getCharacter().equip(shield);
        
        assertEquals(9, World.getCharacterDefense());
        assertEquals(5, World.getCharacterAttack());

        World.getCharacter().unequip(armour);
        World.getCharacter().unequip(helmet);
        World.getCharacter().unequip(shield);
        World.getCharacter().equip(sword);

        assertEquals(10, World.getCharacterAttack());
        assertEquals(0, World.getCharacterDefense());

        World.getCharacter().equip(shield);

        assertEquals(10, World.getCharacterAttack());
        assertEquals(3, World.getCharacterDefense());
        
        World.getCharacter().equip(armour);
        
        assertEquals(10, World.getCharacterAttack());
        assertEquals(6, World.getCharacterDefense());
    }
}


@Test
public void TestCharacterStatsWithRareEquipment() {
    Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
    Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
    List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
    orderedPath.add(test1);
    orderedPath.add(test2);
    LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
    Character testCharacter = new Character(new PathPosition(1,orderedPath));
    World.setCharacter(testCharacter);

    TreeStump treeStump = World.addUnequippedTreeStump();
    Anduril anduril = World.addUnequippedAnduril();
    
    World.getCharacter().equip(treeStump);
    World.getCharacter().equip(anduril);

    
    assertEquals(100, World.getCharacterCurrentHp(), "comparing two doubles");
    assertEquals(15, World.getCharacterDefense());
    assertEquals(20, World.getCharacterAttack());
    
    World.getCharacter().unequip(treeStump);
    World.getCharacter().unequip(anduril);
    
    assertEquals(100, World.getCharacterCurrentHp(), "comparing two doubles");
    assertEquals(0, World.getCharacterDefense());
    assertEquals(5, World.getCharacterAttack());
}
