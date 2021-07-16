package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.EnemyType;

public class Vampire extends Enemy{

    public Vampire(PathPosition position) {
        super(position);
    }

    public EnemyType getEnemyType(){
        return EnemyType.VAMPIRE;
    }
    
}
