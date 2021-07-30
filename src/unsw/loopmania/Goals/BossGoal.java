package unsw.loopmania.Goals;

import unsw.loopmania.LoopManiaWorld;

public class BossGoal implements Goal{

    @Override
    public boolean isGoal(LoopManiaWorld world) {
        if(world.getBossesDefeated() == 2) return true;
        return false;
    }
    
}
