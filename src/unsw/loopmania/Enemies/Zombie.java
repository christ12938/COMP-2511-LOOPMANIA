package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.EnemyType;

public class Zombie extends Enemy{

    public Zombie(PathPosition position) {
        super(position, 10, 3, 3);
    }

    public EnemyType getEnemyType(){
        return EnemyType.ZOMBIE;
    }
    
}
