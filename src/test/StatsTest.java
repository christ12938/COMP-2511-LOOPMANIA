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
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Types.ItemType;
import unsw.loopmania.Character;


public class StatsTest {

    @Test
    public void TestSwordStat(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Sword sword = World.addUnequippedSword();
        assertEqual(5, sword.getAttack());
    }

    @Test
    public void TestStaffStat(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Staff staff = World.addUnequippedStaff();
        assertEqual(1, staff.getAttack());
    }

    @Test
    public void TestStakeStat(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Stake stake = World.addUnequippedStake();
        assertEqual(3, stake.getAttack());
    }


    /* Armour doesn't ahve stats as it halves the enemies attack?
    @Test
    public void TestArmourState(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Armour armour = World.addUnequippedArmour();
        assertEqual(3, armour.getDefense());
    }
    */
    @Test

    public void TestHelmetStat(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Helmet helmet = World.addUnequippedHelmet();
        assertEqual(3, helmet.getDefense());
    }

    @Test
    public void TestShieldStat(){
        LoopManiaWorld World = new LoopManiaWorld(1, 2, new ArrayList<>());
        Shield shield = World.addUnequippedShield();
        assertEqual(3, shield.getDefense());
    
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

        assertEqual(10, World.getCharacterHp());
        assertEqual(0, World.getCharacterDefense());
        assertEqual(1, World.getCharacterAttack());
    }

    @Test

    public void TestCharacterStatsWithEquipment() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        LoopManiaWorld World = d;
        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        World.setCharacter(testCharacter);
        World.addUnequippedArmour();
        World.addUnequippedHelmet();
        World.addUnequippedSword();
        World.addUnequippedShield();
        World.EquipArmour();
        World.EquipHelmet();
        World.EquipSword();
        World.EquipShield();

        assertEqual(10, World.getCharacterHp());
        assertEqual(6, World.getCharacterDefense());
        assertEqual(6, World.getCharacterAttack());
    }
}
