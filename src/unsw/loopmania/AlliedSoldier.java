package unsw.loopmania;

import org.javatuples.Pair;

public class AlliedSoldier extends MovingEntity{

    /**
     * The position of the soldier on the slot
     */
    private Pair<Integer, Integer> slotPosition;

    public AlliedSoldier(PathPosition position, Pair<Integer, Integer> slotPosition) {
        super(position);
        this.slotPosition = slotPosition;
    }

    public int getSlotPosition(){
        return slotPosition.getValue0();
    }

    public void setSlotPosition(int slotPosition){
        this.slotPosition = new Pair<Integer, Integer>(slotPosition, 0);
    }
    
}
