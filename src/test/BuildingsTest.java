package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.Spawner;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Types.BuildingType;
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
    public void TowerRadiusTrue() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);


        LoopManiaWorld d = new LoopManiaWorld(4, 5, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        HerosCastle hc = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setHerosCastle(hc);
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

    @Test
    public void TowerRadiusFalse() {
        Pair<Integer, Integer> test1 = new Pair<Integer, Integer>(1,1);
        Pair<Integer, Integer> test2 = new Pair<Integer, Integer>(1,2);

        List<Pair<Integer, Integer>> orderedPath = new  ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(test1);
        orderedPath.add(test2);



        LoopManiaWorld d = new LoopManiaWorld(4, 5, orderedPath);

        Character testCharacter = new Character(new PathPosition(1,orderedPath));
        HerosCastle hc = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setHerosCastle(hc);
        d.setCharacter(testCharacter);
        d.spawnBuilding(CardType.TOWER_CARD,100,1);

        List<Enemy> enemies;
        do{
            d.nextCycle();
            d.runTickMoves();
            enemies = d.possiblySpawnEnemies();
        }while(enemies.isEmpty());

        d.applyBuildingDebuffsToEnemies();
        assertTrue(!d.towerUsed());
    }

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
        d.decreaseCharacterHp(90);
        d.spawnBuilding(CardType.VILLAGE_CARD,1,1);
        d.runTickMoves();
        d.applyBuildingBuffsToCharacter();
        //assumes player hp starts at 100 and vilage adds 2
        assertTrue(d.getCharacterHP() == 12);
    }

    @Test
    public void TestVillageMaxHP(){
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
        d.spawnBuilding(CardType.VILLAGE_CARD,1,1);
        d.runTickMoves();
        d.applyBuildingBuffsToCharacter();
        //assumes player hp starts at 100 and vilage adds 2
        //Shouldn't add onto max hp
        assertTrue(d.getCharacterHP() == 100);
    }

    @Test
    public void TestVillageNearMaxHP(){
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
        d.spawnBuilding(CardType.VILLAGE_CARD,1,1);
        d.runTickMoves();
        d.decreaseCharacterHp(1);
        d.applyBuildingBuffsToCharacter();
        //assumes player hp starts at 100 and vilage adds 2
        //Shouldn't add onto max hp
        assertTrue(d.getCharacterHP() == 100);
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
        d.applyBuildingBuffsToCharacter();

        //assumes base attack is 5
        System.err.println(d.getCharacterAttack());
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
        HerosCastle hc = new HerosCastle(new SimpleIntegerProperty(1),new SimpleIntegerProperty(1));
        d.setHerosCastle(hc);
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
        d.applyBuildingDebuffsToEnemies();
        assertTrue(d.getFirstEnemy().getHealth()== 10);
    }
}
