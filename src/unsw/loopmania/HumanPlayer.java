package unsw.loopmania;

import unsw.loopmania.Goals.*;


public class HumanPlayer {

    private Goal goal = null;
    private LoopManiaWorldController controller = null;
    private LoopManiaWorld world = null;
    private boolean hasWon = false;

    public HumanPlayer(String goal, int quantity, LoopManiaWorld world) {
        try{
            switch(goal){
                case "gold":
                    this.goal = new GoldGoal(quantity);
                    break;
                case "experience":
                    this.goal = new ExperienceGoal(quantity);
                    break;
                case "cycle":
                    this.goal = new CycleGoal(quantity);
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
    
}
