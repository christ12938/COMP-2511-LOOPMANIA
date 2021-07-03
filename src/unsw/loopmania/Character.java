package unsw.loopmania;


/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {

    private double health = 0;

    public Character(PathPosition position) {
        super(position);
    }
    
}
