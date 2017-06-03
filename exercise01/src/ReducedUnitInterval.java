/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ReducedUnitInterval implements java.io.Serializable {
    //public static String bla = "bla";

    public double normedDistanceInterval;
    public double normedAngleInterval;
    public double normedHitPointsInterval;
    public double unitTypeInterval;
    public double velocityXInterval;
    public double velocityYInterval;

    public void printReducedUnitInterval() {
        System.out.print("\tnormedhitpointsinverval " + normedHitPointsInterval);
        System.out.print("\tnormedangleinter " + normedAngleInterval);
        System.out.print("\tnormedhitpointsintver " + normedHitPointsInterval);
        System.out.print("\tunitTypeInterval " + unitTypeInterval);
        System.out.print("\tvxInterval " + velocityXInterval);
        System.out.print("\tvyInterval " + velocityYInterval);
    }

    public ReducedUnitInterval(double normedDistanceInterval, double normedAngleInterval, double normedHitPointsInterval,
                               double unitTypeInterval, double velocityXInterval, double velocityYInterval) {
        this.normedDistanceInterval = normedDistanceInterval;
        this.normedAngleInterval = normedAngleInterval;
        this.normedHitPointsInterval = normedHitPointsInterval;
        this.unitTypeInterval = unitTypeInterval;
        this.velocityXInterval = velocityXInterval;
        this.velocityYInterval = velocityYInterval;
    }

    public ReducedUnitInterval() {
        normedDistanceInterval = Math.abs(Condition.getRandomInterval());
        normedAngleInterval = Math.abs(Condition.getRandomInterval());
        normedHitPointsInterval = Math.abs(Condition.getRandomInterval());
        unitTypeInterval = Math.abs(Condition.getRandomInterval());
        velocityXInterval = Math.abs(Condition.getRandomInterval());
        velocityYInterval = Math.abs(Condition.getRandomInterval());

    }

    /*public static double distance(Position position1, Position position2){
        int diffX = position1.getX() - position2.getX();
        int diffY = position1.getY() - position2.getY();
        return (double) diffX
    }*/

}
