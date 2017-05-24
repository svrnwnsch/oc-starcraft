import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ReducedUnitInterval {
    //public static String bla = "bla";

    public double normedDistanceInterval;
    public double normedAngleInterval;
    public double normedHitPointsInterval;
    public double unitTypeInterval;

    public ReducedUnitInterval(double normedDistanceInterval, double normedAngleInterval, double normedHitPointsInterval, double unitTypeInterval){
        this.normedDistanceInterval = normedDistanceInterval;
        this.normedAngleInterval = normedAngleInterval;
        this.normedHitPointsInterval = normedHitPointsInterval;
        this.unitTypeInterval = unitTypeInterval;
    }

    public ReducedUnitInterval(Random random){
        normedDistanceInterval = Math.abs(random.nextGaussian()*Condition.sigma);
        normedAngleInterval = Math.abs(random.nextGaussian()*Condition.sigma);
        normedHitPointsInterval = Math.abs(random.nextGaussian()*Condition.sigma);
        unitTypeInterval = Math.abs(random.nextGaussian()*Condition.sigma);

    }

    /*public static double distance(Position position1, Position position2){
        int diffX = position1.getX() - position2.getX();
        int diffY = position1.getY() - position2.getY();
        return (double) diffX
    }*/

}
