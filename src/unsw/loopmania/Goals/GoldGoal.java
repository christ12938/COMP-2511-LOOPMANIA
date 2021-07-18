package unsw.loopmania.Goals;

import unsw.loopmania.LoopManiaWorld;

public class GoldGoal implements Goal{

    private int goldGoal;

    public GoldGoal(int goldGoal){
        this.goldGoal = goldGoal;
    }

    public boolean isGoal(LoopManiaWorld world){
        if(world.getCharacter().getGold() >= goldGoal) return true;
        return false;
    }
}
