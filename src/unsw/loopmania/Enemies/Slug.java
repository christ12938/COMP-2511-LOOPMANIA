package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Types.EnemyType;

public class Slug extends Enemy{

    public Slug(PathPosition position) {
        super(position, 5, 2, 2);
        setCritStrategy(new StandardCritStrategy());
    }
    
    public EnemyType getEnemyType(){
        return EnemyType.SLUG;
    }
}
