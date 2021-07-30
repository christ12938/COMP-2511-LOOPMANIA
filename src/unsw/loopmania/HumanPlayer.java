package unsw.loopmania;

import org.json.JSONObject;

import unsw.loopmania.Goals.*;


public class HumanPlayer {

    private Goal goal = null;
    private LoopManiaWorldController controller = null;
    private LoopManiaWorld world = null;
    private boolean hasWon = false;
    private boolean hasLost = false;

    public HumanPlayer(JSONObject goal , LoopManiaWorld world) {
        try{
            switch(goal.getString("goal")){
                case "gold":
                    this.goal = new GoldGoal(goal.getInt("quantity"));
                    break;
                case "experience":
                    this.goal = new ExperienceGoal(goal.getInt("quantity"));
                    break;
                case "cycles":
                    this.goal = new CycleGoal(goal.getInt("quantity"));
                    break;
                case "bosses":
                    this.goal = new BossGoal();
                    break;
                case "AND":
                    this.goal = new CompositeAndGoal(goal.getJSONArray("subgoals"));
                    break;
                case "OR":
                    this.goal = new CompositeOrGoal(goal.getJSONArray("subgoals"));
                    break;
                default:
                    /* Should never happen */
                    throw new Exception("Goal " + goal + " is not defined. ");
            }
        }catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        this.world = world;
    }

    public void setController(LoopManiaWorldController controller){
        this.controller = controller;
    }

    public void updateGoalState(){
        if(!hasWon && goal.isGoal(this.world)){
            this.hasWon = true;
            controller.displayVictoryMessage();
        }
    }

    public boolean hasWon(){
        return this.hasWon;
    }

    public boolean hasLost(){
        return this.hasLost;
    }

    public void setHasLost(boolean hasLost){
        this.hasLost = hasLost;
    }
   
}
