package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Types.EnemyType;

public class CharacterTest {
    @Test
    public void TestTakeDamage() {
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
}
