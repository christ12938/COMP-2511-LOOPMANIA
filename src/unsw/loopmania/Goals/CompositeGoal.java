package unsw.loopmania.Goals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import unsw.loopmania.LoopManiaWorld;

public abstract class CompositeGoal implements Goal{

    private List<Goal> subgoals = new ArrayList<Goal>();

    public CompositeGoal(JSONArray subgoals){
        try{
            for(int i = 0; i < subgoals.length(); i++){
                switch(subgoals.getJSONObject(i).getString("goal")){
                    case "AND":
                        this.subgoals.add(new CompositeAndGoal(subgoals.getJSONObject(i).getJSONArray("subgoals")));
                        break;
                    case "OR":
                        this.subgoals.add(new CompositeOrGoal(subgoals.getJSONObject(i).getJSONArray("subgoals")));
                        break;
                    case "gold":
                        this.subgoals.add(new GoldGoal(subgoals.getJSONObject(i).getInt("quantity")));
                        break;
                    case "experience":
                        this.subgoals.add(new ExperienceGoal(subgoals.getJSONObject(i).getInt("quantity")));
                        break;
                    case "cycles":
                        this.subgoals.add(new CycleGoal(subgoals.getJSONObject(i).getInt("quantity")));
                        break;
                    default:
                        /* Should never happen */
                        throw new Exception("Goal " + subgoals.getJSONObject(i).getString("goal") + " is not defined. ");
                }
            }
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public List<Goal> getSubGoals(){
        return this.subgoals;
    }

    public abstract boolean isGoal(LoopManiaWorld world);
    
}
