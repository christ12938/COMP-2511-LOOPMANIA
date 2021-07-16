package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Types.CardType;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.LoopManiaWorldController;
import unsw.loopmania.LoopManiaWorldLoader;
import unsw.loopmania.Character;
import unsw.loopmania.HerosCastle;


/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class BuildingsTest{
    @Test
    public void blahTest(){
        assertEquals("a", "a");
    }

    @Test
    public void blahTest2(){
        LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        assertEquals(d.getWidth(), 1);
    }

    @Test
    public void TowerRadiusTrue() {
        /*
        Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1,2);
        Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(1,3);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(pair1);
        orderedPath.add(pair2);
        orderedPath.add(pair3);

        Character character = new Character(new PathPosition(0, orderedPath));
        Slug slug = new Slug(new PathPosition(2, orderedPath));
        assertFalse(slug.inBattleRadius(character));*/
    }

    @Test
    public void TowerRadiusFalse() {

    }

    @Test
    public void TestBarracks(){}

    @Test
    public void TestVillage(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        HerosCastle hc = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setHerosCastle(hc);
        d.setCharacter(testCharacter);


        Card card;
        do{
            card = d.loadRandomCard();
        }while(card.getCardType() != CardType.VILLAGE_CARD);
        d.subtractCharacterHP(90);
        d.convertCardToBuildingByCoordinates(card.getX(), card.getY(), 1, 2);
        d.runTickMoves();

        //assumes player hp starts at 100 and vilage adds 2
        assertTrue(d.getCharacterHP() == 12);
    }

    @Test
    public void CampfireRadiusTrue(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        HerosCastle hc = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setHerosCastle(hc);
        d.setCharacter(testCharacter);

        Card card;
        do{
            card = d.loadRandomCard();
        }while(card.getCardType() != CardType.CAMPFIRE_CARD);
        d.convertCardToBuildingByCoordinates(card.getX(), card.getY(), 2, 2);
        d.runTickMoves();

        //assumes base attack is 5
        assertTrue(d.getCharacterAttack() == 10);
    }

    @Test
    public void CampfireRadiusFalse(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        HerosCastle hc = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setHerosCastle(hc);
        d.setCharacter(testCharacter);

        Card card;
        do{
            card = d.loadRandomCard();
        }while(card.getCardType() != CardType.CAMPFIRE_CARD);
        d.convertCardToBuildingByCoordinates(card.getX(), card.getY(), 7, 2);
        d.runTickMoves();

        //assumes base attack is 5
        assertTrue(d.getCharacterAttack() == 5);
    }

    @Test
    public void TestTrap(){}
}
