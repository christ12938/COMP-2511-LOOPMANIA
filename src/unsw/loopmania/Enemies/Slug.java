package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.EnemyType;

public class Slug extends Enemy{

    public Slug(PathPosition position) {
        super(position);
    }
    
    public EnemyType getEnemyType(){
        return EnemyType.SLUG;
    }
}
