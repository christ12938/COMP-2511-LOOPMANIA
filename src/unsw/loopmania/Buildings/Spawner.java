package unsw.loopmania.Buildings;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Spawner extends Building{

    /* Spawning tile for enemy */
    private List<Pair<Integer, Integer>> tileToSpawn;

    public Spawner(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        tileToSpawn = new ArrayList<Pair<Integer, Integer>>();
    }

    public void addSpawningTile(List<Pair<Integer, Integer>> tiles){
        for(Pair<Integer, Integer> tile : tiles){
            tileToSpawn.add(tile);
        }
    }

    public List<Pair<Integer, Integer>> getSpawningTiles(){
        return tileToSpawn;
    }

    public abstract int getSpawningCycle();
    public abstract void setHasSpawned(boolean hasSpawned);
    public abstract boolean getHasSpawned();

}
