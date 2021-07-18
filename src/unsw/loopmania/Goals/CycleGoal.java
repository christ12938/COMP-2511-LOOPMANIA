package unsw.loopmania.Goals;

import unsw.loopmania.LoopManiaWorld;

public class CycleGoal implements Goal{

    private int cycleGoal;

    public CycleGoal(int cycleGoal){
        this.cycleGoal = cycleGoal;
    }

    public boolean isGoal(LoopManiaWorld world){
        if(world.getCycle() >= cycleGoal) return true;
        return false;
    }

}
