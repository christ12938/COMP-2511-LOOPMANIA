package unsw.loopmania.Buildings;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Types.EnemyType;

public abstract class Spawner extends Building{

    /* Spawning tile for enemy */
    List<Pair<Integer, Integer>> tileToSpawn;
    /* Type of enemy that the spawner is spawning */
    EnemyType enemyType;

    public Spawner(SimpleIntegerProperty x, SimpleIntegerProperty y, EnemyType enemyType) {
        super(x, y);
        tileToSpawn = new ArrayList<Pair<Integer, Integer>>();
        this.enemyType = enemyType;
    }

    public void addSpawningTile(List<Pair<Integer, Integer>> tiles){
        for(Pair<Integer, Integer> tile : tiles){
            tileToSpawn.add(tile);
        }
    }
    
}
