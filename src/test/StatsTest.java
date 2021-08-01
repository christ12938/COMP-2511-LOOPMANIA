package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Items.Anduril;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.TreeStump;
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
    }

    @Test
    public void TestEquippingSwordStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Sword sword = World.addUnequippedSword();
        World.getCharacter().equip(sword);
        assertEquals(10, World.getCharacterAttack());
    }

    @Test
    public void TestUnequippingSwordStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Sword sword = World.addUnequippedSword();
        World.getCharacter().equip(sword);
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
    }

    @Test
    public void TestEquippingStaffStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Staff staff = World.addUnequippedStaff();
        World.getCharacter().equip(staff);
        assertEquals(6, World.getCharacterAttack());
    }

    @Test
    public void TestUnequippingStaffStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Staff staff = World.addUnequippedStaff();
        World.getCharacter().equip(staff);
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
    }

    @Test
    public void TestEquippingStakeStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Stake stake = World.addUnequippedStake();
        World.getCharacter().equip(stake);
        assertEquals(8, World.getCharacterAttack());
    }

    @Test
    public void TestUnequippingStakeStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Stake stake = World.addUnequippedStake();
        World.getCharacter().equip(stake);
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
    }

    @Test
    public void TestEquippingArmourStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Armour armour = World.addUnequippedArmour();
        
        World.getCharacter().equip(armour);
        assertEquals(3, World.getCharacterDefense());
    }

    @Test
    public void TestUnequippingArmourStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Armour armour = World.addUnequippedArmour();
        World.getCharacter().equip(armour);
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
    }

    @Test
    public void TestEquippingHelmetStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Helmet helmet = World.addUnequippedHelmet();
        
        World.getCharacter().equip(helmet);
        assertEquals(3, World.getCharacterDefense());
    }

    @Test
    public void TestUnequippingHelmetStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Helmet helmet = World.addUnequippedHelmet();
        World.getCharacter().equip(helmet);
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
    
    }

    @Test
    public void TestEquippingShieldStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Shield shield = World.addUnequippedShield();

        World.getCharacter().equip(shield);
        assertEquals(3, World.getCharacterDefense());
    
    }

    @Test
    public void TestUnequippingShieldStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Shield shield = World.addUnequippedShield();
        World.getCharacter().equip(shield);
        World.getCharacter().unequip(shield);
        assertEquals(0, World.getCharacterDefense());
    
    }

    @Test
    public void TestAndurilStat(){
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
    }

    @Test
    public void TestEquippingAndurilStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Anduril anduril = World.addUnequippedAnduril();
        
        World.getCharacter().equip(anduril);
        assertEquals(20, World.getCharacterAttack());
    }

    @Test
    public void TestUnequippingAndurilStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        Anduril anduril = World.addUnequippedAnduril();
        
        World.getCharacter().equip(anduril);
        World.getCharacter().unequip(anduril);
        assertEquals(5, World.getCharacterAttack());
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

        TreeStump treeStump = World.addUnequippedTreeStump();
        assertEquals(15, treeStump.getDefense());
    
    }

    @Test
    public void TestEquippingTreeStuempStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        TreeStump treeStump = World.addUnequippedTreeStump();
        
        World.getCharacter().equip(treeStump);
        assertEquals(15, World.getCharacterDefense());
    }

    @Test
    public void TestUnequippingTreeStuempStat(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld World = new LoopManiaWorld(1, 2, orderedPath);
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        TreeStump treeStump = World.addUnequippedTreeStump();
        
        World.getCharacter().equip(treeStump);
        World.getCharacter().unequip(treeStump);
        assertEquals(0, World.getCharacterDefense());
    
    }

    @Test

    public void TestBaseStatHealthCharacter() {
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
    }

    @Test

    public void TestBaseStatAttackCharacter() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        assertEquals(5, World.getCharacterAttack());
    }

    @Test

    public void TestBaseStatDefenseCharacter() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);

        assertEquals(0, World.getCharacterDefense());
    }

    @Test
    public void TestCharacterStatsEquippingMultipleEquipmentAttack() {
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

        assertEquals(10, World.getCharacterAttack());
 
    }

    @Test
    public void TestCharacterStatsEquippingMultipleEquipmentDefense() {
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
        
        assertEquals(9, World.getCharacterDefense());
        
    }

    @Test
    public void TestCharacterStatsUnequippingEquipmentDefense() {
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
        World.getCharacter().equip(sword);
        


        World.getCharacter().unequip(armour);

        assertEquals(6, World.getCharacterDefense());
    }


}