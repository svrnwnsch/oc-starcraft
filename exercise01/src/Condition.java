import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Condition implements java.io.Serializable {
    public static final double sigma = 0.25;
    // probability to set Interval to 1
    public static final double pIntervalOne = 0.2;

    public Situation situation;

    public double unitHitPointsInterval;
    public double unitPosXInterval;
    public double unitPosYInterval;
    public double unitGroundCooldownInterval;

    public double numberAlliesOnMapInterval;
    public double numberEnemiesOnMapInterval;
    public double numberSightedEnemiesOnMapInterval;
    public double killCountEnemiesInterval;
    public double killCountAlliesInterval;

    public ArrayList<ReducedUnitInterval> closestEnemiesInterval;
    public ArrayList<ReducedUnitInterval> closestAlliesInterval;

    private static Random random = new Random();

    public void printCondition() {
        System.out.print("\tunitHitInterval: " + unitHitPointsInterval);
        System.out.print("\tunitPosXInter: " + unitPosXInterval);
        System.out.print("\tUnitPosYInter: " + unitPosYInterval);
        System.out.print("\tunitGroundcoolInter" + unitGroundCooldownInterval);
        System.out.print("\tnumbersightedenemiesonmap" + numberSightedEnemiesOnMapInterval);
        for (ReducedUnitInterval reducedUnitInterval : closestEnemiesInterval) {
            reducedUnitInterval.printReducedUnitInterval();
        }
        System.out.println("");
        situation.printSituation();

    }

    //Covering Function
    public Condition(Situation situation){
        this.situation = situation;


        unitHitPointsInterval = Math.abs(getRandomInterval());
        unitPosXInterval = Math.abs(getRandomInterval());
        unitPosYInterval = Math.abs(getRandomInterval());
        unitGroundCooldownInterval = Math.abs(getRandomInterval());
        numberAlliesOnMapInterval = Math.abs(getRandomInterval());
        numberEnemiesOnMapInterval = Math.abs(getRandomInterval());
        numberSightedEnemiesOnMapInterval = Math.abs(getRandomInterval());
        killCountAlliesInterval = Math.abs(getRandomInterval());
        killCountEnemiesInterval = Math.abs(getRandomInterval());
        closestAlliesInterval = new ArrayList<ReducedUnitInterval>(Situation.closestAlliesArraySize);
        for(int i=0; i<Situation.closestAlliesArraySize; i++){
            closestAlliesInterval.add(i, new ReducedUnitInterval());
        }
        closestEnemiesInterval = new ArrayList<ReducedUnitInterval>(Situation.closestEnemiesArraySize);
        for(int i=0; i<Situation.closestEnemiesArraySize; i++){
            closestEnemiesInterval.add(i, new ReducedUnitInterval());
        }

    }

    // Deep Copy Constructor
    public Condition(Condition parentCond) {
        this.situation = new Situation(parentCond.situation);
        unitHitPointsInterval = parentCond.unitHitPointsInterval;
        unitPosXInterval = parentCond.unitPosXInterval;
        unitPosYInterval = parentCond.unitPosYInterval;
        unitGroundCooldownInterval = parentCond.unitGroundCooldownInterval;
        numberAlliesOnMapInterval = parentCond.numberAlliesOnMapInterval;
        numberEnemiesOnMapInterval = parentCond.numberEnemiesOnMapInterval;
        numberSightedEnemiesOnMapInterval = parentCond.numberSightedEnemiesOnMapInterval;
        killCountAlliesInterval = parentCond.killCountAlliesInterval;
        killCountEnemiesInterval = parentCond.killCountAlliesInterval;
        closestAlliesInterval = new ArrayList<ReducedUnitInterval>(parentCond.closestAlliesInterval.size());
        for (int i = 0; i < parentCond.closestAlliesInterval.size(); i++) {
            closestAlliesInterval.add(i, new ReducedUnitInterval(parentCond.closestAlliesInterval.get(i)));
        }
        closestEnemiesInterval = new ArrayList<ReducedUnitInterval>(parentCond.closestEnemiesInterval.size());
        for (int i = 0; i < parentCond.closestEnemiesInterval.size(); i++) {
            closestEnemiesInterval.add(i, new ReducedUnitInterval(parentCond.closestEnemiesInterval.get(i)));
        }
    }

    public static double getRandomInterval() {

        if (random.nextDouble() < pIntervalOne) {
            return 1.;
        } else {
            return Math.abs(random.nextGaussian() * sigma);
        }
    }

    public boolean matchSituation(Situation externSituation){
        boolean result = (matchVariable(externSituation.getUnitHitpoints(), situation.getUnitHitpoints(), unitHitPointsInterval)) &&
                (matchVariable(externSituation.getUnitPosX(), situation.getUnitPosX(), unitPosXInterval)) &&
                (matchVariable(externSituation.getUnitPosY(), situation.getUnitPosY(), unitPosYInterval)) &&
                (matchVariable(externSituation.getUnitGroundCooldown(), situation.getUnitGroundCooldown(), unitGroundCooldownInterval)) &&
                (matchVariable(externSituation.getNumberAlliesOnMap(), situation.getNumberAlliesOnMap(), numberAlliesOnMapInterval)) &&
                (matchVariable(externSituation.getNumberSightedEnemiesOnMap(), situation.getNumberSightedEnemiesOnMap(), numberSightedEnemiesOnMapInterval)) &&
                (matchVariable(externSituation.getKillCountAllies(), situation.getKillCountAllies(), killCountAlliesInterval));
        for(int i=0; i<Situation.closestAlliesArraySize; i++){
            result = result && (matchVariable(externSituation.getClosestAllies().get(i).normedDistance, situation.getClosestAllies().get(i).normedDistance, closestAlliesInterval.get(i).normedDistanceInterval)) &&
                    (matchVariable(externSituation.getClosestAllies().get(i).normedAngle, situation.getClosestAllies().get(i).normedAngle, closestAlliesInterval.get(i).normedAngleInterval)) &&
                    (matchVariable(externSituation.getClosestAllies().get(i).normedHitPoints, situation.getClosestAllies().get(i).normedHitPoints, closestAlliesInterval.get(i).normedHitPointsInterval)) &&
                    (matchVariable(externSituation.getClosestAllies().get(i).unitType, situation.getClosestAllies().get(i).unitType, closestAlliesInterval.get(i).unitTypeInterval)) &&
                    (matchVariable(externSituation.getClosestAllies().get(i).velocityX, situation.getClosestAllies().get(i).velocityX, closestAlliesInterval.get(i).velocityXInterval)) &&
                    (matchVariable(externSituation.getClosestAllies().get(i).velocityY, situation.getClosestAllies().get(i).velocityY, closestAlliesInterval.get(i).velocityYInterval));
        }
        for(int i=0; i<Situation.closestEnemiesArraySize; i++){
            result = result && (matchVariable(externSituation.getClosestEnemies().get(i).normedDistance, situation.getClosestEnemies().get(i).normedDistance, closestEnemiesInterval.get(i).normedDistanceInterval)) &&
                    (matchVariable(externSituation.getClosestEnemies().get(i).normedAngle, situation.getClosestEnemies().get(i).normedAngle, closestEnemiesInterval.get(i).normedAngleInterval)) &&
                    (matchVariable(externSituation.getClosestEnemies().get(i).normedHitPoints, situation.getClosestEnemies().get(i).normedHitPoints, closestEnemiesInterval.get(i).normedHitPointsInterval)) &&
                    (matchVariable(externSituation.getClosestEnemies().get(i).unitType, situation.getClosestEnemies().get(i).unitType, closestEnemiesInterval.get(i).unitTypeInterval)) &&
                    (matchVariable(externSituation.getClosestEnemies().get(i).velocityX, situation.getClosestEnemies().get(i).velocityX, closestEnemiesInterval.get(i).velocityXInterval)) &&
                    (matchVariable(externSituation.getClosestEnemies().get(i).velocityY, situation.getClosestEnemies().get(i).velocityY, closestEnemiesInterval.get(i).velocityYInterval));
        }
        return result;


    }

    public boolean matchVariable(double situationValue, double conditionValue, double conditionInterval){
        return (conditionValue - conditionInterval <= situationValue && conditionValue + conditionInterval >= situationValue);
    }

     public Situation getSituation(){return situation;}
     public double getUnitHitPointsInterval(){return unitHitPointsInterval;}
     public double getUnitPosXInterval(){return unitPosXInterval;}
     public double getUnitPosYInterval(){return unitPosYInterval;}
     public double getUnitGroundCooldownInterval(){return unitGroundCooldownInterval;}
     public double getNumberAlliesOnMapInterval(){return numberAlliesOnMapInterval;}
     public double getNumberEnemiesOnMapInterval(){return numberEnemiesOnMapInterval;}
     public double getNumberSightedEnemiesOnMapInterval(){return numberSightedEnemiesOnMapInterval;}
     public double getKillCountEnemiesInterval(){return killCountEnemiesInterval;}
     public double getKillCountAlliesInterval(){return killCountAlliesInterval;}
     public ArrayList<ReducedUnitInterval> getClosestEnemiesInterval(){return closestEnemiesInterval;}
     public ArrayList<ReducedUnitInterval> getClosestAlliesInterval(){return closestAlliesInterval;}
}
