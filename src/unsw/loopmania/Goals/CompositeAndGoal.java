package unsw.loopmania.Goals;

import org.json.JSONArray;

import unsw.loopmania.LoopManiaWorld;

public class CompositeAndGoal extends CompositeGoal{

    public CompositeAndGoal(JSONArray subgoals){
        super(subgoals);
    }

    public boolean isGoal(LoopManiaWorld world) {
        boolean result = true;
        for(Goal subgoal : getSubGoals()){
            result = result && subgoal.isGoal(world);
        }
        return result;
    }
    
}
