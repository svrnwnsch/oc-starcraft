import bwapi.Unit;

/**
 * Created by OC2 on 24.05.2017.
 */
public class HoldAction implements Action {
    // Action which orders the player to do nothing

    @Override
    public double executeAction(Unit executingUnit) {
        executingUnit.stop();
        return Rewards.HOLD;
    }
}
