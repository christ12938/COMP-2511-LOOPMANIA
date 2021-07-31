package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.TheOneRing;
import unsw.loopmania.Items.TreeStump;
import unsw.loopmania.Types.EnemyType;
import unsw.loopmania.Types.ItemType;

public class CharacterTest {
    // Tests taking damage from vampire with shield
    @Test
    public void TestTakeDamageVampireWithShield() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        Vampire vampire = new Vampire(new PathPosition(1, orderedPath));

        Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        character.equip(shield);

        double health = character.getCurrentHealth();

        character.takeDamage(EnemyType.VAMPIRE.getAttack() + 1, vampire);

        assertTrue(health > character.getCurrentHealth());
    }

    // Tests taking damage from bosses with tree stump
    @Test
    public void TestTakeDamageBossesTreeStump() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        Doggie doggie = new Doggie(new PathPosition(1, orderedPath));
        ElanMuske elanMuske = new ElanMuske(new PathPosition(1, orderedPath));

        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), null);
        character.equip(treeStump);

        double health = character.getCurrentHealth();

        character.takeDamage(EnemyType.DOGGIE.getAttack(), doggie);

        assertTrue(health > character.getCurrentHealth());

        health = character.getCurrentHealth();

        character.takeDamage(EnemyType.ELAN_MUSKE.getAttack(), elanMuske);

        assertTrue(health > character.getCurrentHealth());
    }

    // Tests taking damage with armour on
    @Test
    public void TestTakeDamageWithArmour() {
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);

        LoopManiaWorld.herosCastle = new unsw.loopmania.HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        Character character = new Character(new PathPosition(0, orderedPath));
        Slug slug = new Slug(new PathPosition(1, orderedPath));

        double health = character.getCurrentHealth();

        Armour armour = new Armour(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        character.equip(armour);

        character.takeDamage(20, slug);

        assertEquals(health, character.getCurrentHealth() + (20 / 2 - character.getDefense()) );
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
}
