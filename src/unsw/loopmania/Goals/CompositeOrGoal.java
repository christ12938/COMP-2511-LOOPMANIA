package unsw.loopmania.Goals;

import org.json.JSONArray;

import unsw.loopmania.LoopManiaWorld;

public class CompositeOrGoal extends CompositeGoal{

    public CompositeOrGoal(JSONArray subgoals) {
        super(subgoals);
    }

    public boolean isGoal(LoopManiaWorld world) {
        boolean result = false;
        for(Goal subgoal : getSubGoals()){
            result = result || subgoal.isGoal(world);
        }
        return result;
    }
    
}
