package unsw.loopmania.Enemies;

import org.javatuples.Pair;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.EnemyType;

public class Slug extends Enemy{

    public Slug(PathPosition position, Pair<Integer, Integer> herosCastlePos) {
        super(position, herosCastlePos, 5, 2, 2);
        setCritStrategy(new StandardCritStrategy());
    }
    
    public EnemyType getEnemyType(){
        return EnemyType.SLUG;
    }
}
