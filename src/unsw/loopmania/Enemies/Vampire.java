package unsw.loopmania.Enemies;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Types.EnemyType;

public class Vampire extends Enemy{

    private List<CampfireBuilding> campfires;
     
    public Vampire(PathPosition position, Pair<Integer, Integer> herosCastlePos) {
        super(position, herosCastlePos, 15, 4, 5);
        campfires = new ArrayList<>();
        setCritStrategy(new VampireStrategy());
    }

    public EnemyType getEnemyType(){
        return EnemyType.VAMPIRE;
    }
    
}
