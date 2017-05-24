import java.util.regex.Pattern;
import bwapi.*;
import java.util.*;
/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Condition {
    public static final double sigma =  1./2.2;

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

    
    //Covering Function
    public Condition(Situation situation){
        this.situation = situation;

        Random random = new Random();
        unitHitPointsInterval = Math.abs(random.nextGaussian()*sigma);
        unitPosXInterval = Math.abs(random.nextGaussian()*sigma);
        unitPosYInterval = Math.abs(random.nextGaussian()*sigma);
        unitGroundCooldownInterval = Math.abs(random.nextGaussian()*sigma);
        numberAlliesOnMapInterval = Math.abs(random.nextGaussian()*sigma);
        numberEnemiesOnMapInterval = Math.abs(random.nextGaussian()*sigma);
        numberSightedEnemiesOnMapInterval = Math.abs(random.nextGaussian()*sigma);
        killCountAlliesInterval = Math.abs(random.nextGaussian()*sigma);
        killCountEnemiesInterval = Math.abs(random.nextGaussian()*sigma);
        closestAlliesInterval = new ArrayList<ReducedUnitInterval>(Situation.closestAlliesArraySize);
        for(int i=0; i<Situation.closestAlliesArraySize; i++){
            closestAlliesInterval.add(i, new ReducedUnitInterval(random));
        }
        closestEnemiesInterval = new ArrayList<ReducedUnitInterval>(Situation.closestEnemiesArraySize);
        for(int i=0; i<Situation.closestEnemiesArraySize; i++){
            closestEnemiesInterval.add(i, new ReducedUnitInterval(random));
        }

    }


    //private Pattern conditionPattern;

    //public Condition(String regex) {
      //  conditionPattern = Pattern.compile(regex);
    //}

    //public boolean matches(String environment) {
    //    return conditionPattern.matcher(environment).matches();
    //}
}
