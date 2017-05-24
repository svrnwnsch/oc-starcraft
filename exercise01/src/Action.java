import bwapi.Unit;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public interface Action {
    double executeAction(Unit executingUnit); // should maybe return reward of action
}
