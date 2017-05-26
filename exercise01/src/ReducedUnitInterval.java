/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ReducedUnitInterval implements java.io.Serializable {
    //public static String bla = "bla";

    public double normedDistanceInterval;
    public double normedAngleInterval;
    public double normedHitPointsInterval;
    public double unitTypeInterval;

    public void printReducedUnitInterval() {
        System.out.print("\tnormedhitpointsinverval" + normedHitPointsInterval);
        System.out.print("\tnormedangleinter" + normedAngleInterval);
        System.out.print("\tnormedhitpointsintver" + normedHitPointsInterval);
        System.out.print("\tunitTypeInterval" + unitTypeInterval);
    }

    public ReducedUnitInterval(double normedDistanceInterval, double normedAngleInterval, double normedHitPointsInterval, double unitTypeInterval){
        this.normedDistanceInterval = normedDistanceInterval;
        this.normedAngleInterval = normedAngleInterval;
        this.normedHitPointsInterval = normedHitPointsInterval;
        this.unitTypeInterval = unitTypeInterval;
    }

    public ReducedUnitInterval() {
        normedDistanceInterval = Math.abs(Condition.getRandomInterval());
        normedAngleInterval = Math.abs(Condition.getRandomInterval());
        normedHitPointsInterval = Math.abs(Condition.getRandomInterval());
        unitTypeInterval = Math.abs(Condition.getRandomInterval());

    }

    /*public static double distance(Position position1, Position position2){
        int diffX = position1.getX() - position2.getX();
        int diffY = position1.getY() - position2.getY();
        return (double) diffX
    }*/

}
