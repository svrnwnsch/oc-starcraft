import bwapi.Position;
import bwapi.Unit;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ReducedUnit {
    //public static String bla = "bla";

    public double distance;
    public double angle;
    public double health;
    public double unitType;

    public ReducedUnit(Unit unit, Position position){
        this.health = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());
        this.distance = ((double) position.getDistance(unit.getPosition()))/Situation.maxDistance;
        this.angle = calculateAngle(position , unit.getPosition());
    }

    public double calculateAngle(Position centerPosition, Position relativePosition){
        return Math.atan(((double)(relativePosition.getY() - centerPosition.getY()))/ (relativePosition.getX() - centerPosition.getX()));
    }

    /*public static double distance(Position position1, Position position2){
        int diffX = position1.getX() - position2.getX();
        int diffY = position1.getY() - position2.getY();
        return (double) diffX
    }*/

}
