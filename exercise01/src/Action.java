import bwapi.Unit;
import bwapi.Unitset;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public interface Action {
    double executeAction(Unit executingUnit, Unitset friendlyUnits, Unitset enemyUnits); // should maybe return reward of action
}
