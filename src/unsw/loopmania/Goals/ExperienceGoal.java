package unsw.loopmania.Goals;

import unsw.loopmania.LoopManiaWorld;

public class ExperienceGoal implements Goal{

    private int experienceGoal;

    public ExperienceGoal(int experienceGoal){
        this.experienceGoal = experienceGoal;
    }

    public boolean isGoal(LoopManiaWorld world){
        if(world.getCharacter().getExperience() >= experienceGoal) return true;
        return false;
    }
}
