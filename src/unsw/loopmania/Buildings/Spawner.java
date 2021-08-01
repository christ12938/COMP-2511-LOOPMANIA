package unsw.loopmania.Buildings;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Spawner extends Building{

    /* Spawning tile for enemy */
    private List<Pair<Integer, Integer>> tileToSpawn;

    private BooleanProperty isCursed;

    public static final double cursedChance = 0.2;

    public Spawner(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        tileToSpawn = new ArrayList<Pair<Integer, Integer>>();
        isCursed = new SimpleBooleanProperty(false);
    }

    public void addSpawningTile(List<Pair<Integer, Integer>> tiles){
        for(Pair<Integer, Integer> tile : tiles){
            tileToSpawn.add(tile);
        }
    }

    public List<Pair<Integer, Integer>> getSpawningTiles(){
        return tileToSpawn;
    }

    public int getBuildingRadius(){
        return 0;
    }

    public void setIsCursed(boolean isCursed){
        this.isCursed.set(isCursed);
    }

    public BooleanProperty getIsCursed(){
        return isCursed;
    }

    public abstract int getSpawningCycle();
    public abstract void setHasSpawned(boolean hasSpawned);
    public abstract boolean getHasSpawned();

}
