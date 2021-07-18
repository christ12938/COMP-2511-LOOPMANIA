package unsw.loopmania.Enemies;

import org.javatuples.Pair;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.EnemyType;

public class Zombie extends Enemy{

    public Zombie(PathPosition position, Pair<Integer, Integer> herosCastlePos) {
        super(position, herosCastlePos, 10, 3, 3);
        setCritStrategy(new ZombieStrategy());
    }

    public EnemyType getEnemyType(){
        return EnemyType.ZOMBIE;
    }
}
