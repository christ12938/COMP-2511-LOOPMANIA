package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Spawner;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Types.CardType;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Character;
import unsw.loopmania.HerosCastle;


/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class BuildingsTest{

    //checks to see if towers attack enemies within their radius during battle
    @Test
    public void TowerRadiusTrue() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);


        LoopManiaWorld d = new LoopManiaWorld(4, 5, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));

        d.setCharacter(testCharacter);

        d.spawnBuilding(CardType.TOWER_CARD,2,1);

        List<Enemy> enemies;
        do{
            d.nextCycle();
            d.runTickMoves();
            enemies = d.possiblySpawnEnemies();
        }while(enemies.isEmpty());
        d.runBattles();
        assertTrue(d.towerUsed());
    }


    //checks that towers don't attack enemies outside their radius during battle
    @Test
    public void TowerRadiusFalse() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);



        LoopManiaWorld d = new LoopManiaWorld(4, 5, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setCharacter(testCharacter);
        d.spawnBuilding(CardType.TOWER_CARD,100,1);

        List<Enemy> enemies;
        do{
            d.nextCycle();
            d.runTickMoves();
            enemies = d.possiblySpawnEnemies();
        }while(enemies.isEmpty());

        d.applyTowerDamageToEnemy(enemies.get(0));
        assertTrue(!d.towerUsed());
    }

    //Checks the chracters health is increased appropriatley when passing over a villiage
    @Test
    public void TestVillage(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setCharacter(testCharacter);
        d.decreaseCharacterHp(90);
        d.spawnBuilding(CardType.VILLAGE_CARD,1,1);
        d.runTickMoves();
        d.applyStaticBuildingBuffsToCharacter();
        //assumes player hp starts at 10 and vilage adds 10
        assertTrue(d.getCharacterHP() == 20);
    }

    //Checks that the characters HP does not increase over the max value when travelling over a villiage
    //when already at max HP
    @Test
    public void TestVillageMaxHP(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setCharacter(testCharacter);
        d.spawnBuilding(CardType.VILLAGE_CARD,1,1);
        d.runTickMoves();
        d.applyStaticBuildingBuffsToCharacter();
        //assumes player hp starts at 100 and vilage adds 2
        //Shouldn't add onto max hp
        assertTrue(d.getCharacterHP() == 100);
    }


    //Checks that the characters HP does not increase over the max value when travelling over a villiage
    //when close to max HP (close enough where healing from a villaige would push their HP over max)
    @Test
    public void TestVillageNearMaxHP(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setCharacter(testCharacter);
        d.spawnBuilding(CardType.VILLAGE_CARD,1,1);
        d.runTickMoves();
        d.decreaseCharacterHp(1);
        d.applyStaticBuildingBuffsToCharacter();
        //assumes player hp starts at 100 and vilage adds 2
        //Shouldn't add onto max hp
        assertTrue(d.getCharacterHP() == 100);
    }

    //Tests the the campfire provides and attack buff when the character is in range
    @Test
    public void CampfireRadiusTrue(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setCharacter(testCharacter);

        Card card;
        do{
            card = d.loadRandomCard();
        }while(card.getCardType() != CardType.CAMPFIRE_CARD);

        d.convertCardToBuildingByCoordinates(card.getX(), card.getY(), 2, 2);
        d.runTickMoves();
        d.applyCampfireBuffToCharacter();

        //assumes base attack is 5
        assertTrue(d.getCharacterAttack() == 10);
    }


    //Tests the the campfire does not provide an attack buff when the character is out of range
    @Test
    public void CampfireRadiusFalse(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(1, 3, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
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

    //Tests that a trap deals damage to an enemy when they pass over it
    @Test
    public void TestTrap(){
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);
        Pair<Integer, Integer> test3 = new Pair<Integer, Integer>(1,3);
        Pair<Integer, Integer> test4 = new Pair<Integer, Integer>(1,4);
        Pair<Integer, Integer> test5 = new Pair<Integer, Integer>(1,5);
        List<Pair<Integer, Integer>> spawnArea = new  ArrayList<Pair<Integer, Integer>>();
        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);
        orderedPath.add(test3);
        orderedPath.add(test4);
        orderedPath.add(test5);
        spawnArea.add(test2);

        LoopManiaWorld d = new LoopManiaWorld(4, 5, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        testCharacter.setAttack(0);
        LoopManiaWorld.herosCastle = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setCharacter(testCharacter);

        Spawner spawnB = (Spawner) d.spawnBuilding(CardType.VAMPIRECASTLE_CARD, 2, 1);
        spawnB.addSpawningTile(spawnArea);
        d.spawnBuilding(CardType.TRAP_CARD,1,3);
        d.spawnBuilding(CardType.TRAP_CARD,1,1);
        d.spawnBuilding(CardType.TRAP_CARD,1,2);
        d.spawnBuilding(CardType.TRAP_CARD,1,4);
        d.spawnBuilding(CardType.TRAP_CARD,1,5);



        d.runTickMoves();
        d.nextCycle();
        d.nextCycle();
        d.nextCycle();
        d.nextCycle();
        d.nextCycle();
        d.possiblySpawnEnemies();
        d.runTickMoves();
        d.applyTrapsToEnemies();
        assertTrue(d.getFirstEnemy().getCurrentHealth()== 10);
    }
}

