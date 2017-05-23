import bwapi.Game;
import bwapi.Unit;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Situation {

    public static final int closestAlliesArraySize = 5;
    public static final int closestEnemiesArraySize = 5;
    public static final int maxUnits = 50;
    public static final int maxPosX = 10000;
    public static final int maxPosY = 10000;
    public static final double maxDistance = Math.sqrt(maxPosX*maxPosX + maxPosY*maxPosY);


    private double unitHitPoints;
    private double unitPosX;
    private double unitPosY;

    private final double numberAlliesOnMap;
    private double numberEnemiesOnMap;
    private double numberSightedEnemiesOnMap;
    private double killCountEnemies;
    private double killCountAllies;

    private HashSet<Unit> enemiesOnMap = new HashSet<Unit>();
    private HashSet<Unit> alliesOnMap = new HashSet<Unit>();
    private ArrayList<Unit> closestEnemies;
    private ArrayList<Unit> closestAllies;





    public Situation(Unit unit, Game game){
        //Values are always parsed on [0.,1.]
        unitPosX = ConditionUtil.parseValue(unit.getX(), maxPosX);
        unitPosY = ConditionUtil.parseValue(unit.getY(), maxPosY);
        unitHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());
        List<Unit> allUnits = game.getAllUnits();
        //Separating enemy units from allied units
        for(Unit currentUnit : allUnits){
            if(currentUnit.getPlayer() != game.self()){
                enemiesOnMap.add(currentUnit);
            }
            else{
                alliesOnMap.add(currentUnit);
            }
        }
        //Determining the closest Enemies and Alies
        closestEnemies = getClosestUnits(closestEnemiesArraySize ,enemiesOnMap, unit);
        closestAllies = getClosestUnits(closestAlliesArraySize, alliesOnMap, unit);


        numberAlliesOnMap = ConditionUtil.parseValue(alliesOnMap.size(), maxUnits);
        numberSightedEnemiesOnMap = ConditionUtil.parseValue(enemiesOnMap.size(), maxUnits);


    }

    //Getting number many closest units to mainUnit sorted as an ArrayList. Sorting may be optimized
    public ArrayList<Unit> getClosestUnits(int number, HashSet<Unit> units, Unit mainUnit){
        //Length of the ArrayList is final. Empty entries possible
        ArrayList<Unit> closestUnits = new ArrayList<Unit>(number);
        //cloning source set
        HashSet<Unit> workSetUnits = (HashSet<Unit>) units.clone();
        //sorting algorithm
        for(int i=0; i < number; i++){
            //if original length of workset is smaller than i
            if(workSetUnits.isEmpty())
                return closestUnits;
            double minimalDistance = 1.;
            Unit closestUnit = null;
            for(Unit unit : workSetUnits){
                double distance = ((double) unit.getPosition().getDistance(mainUnit.getPosition()))/maxDistance;
                if(distance < minimalDistance){
                    minimalDistance = distance;
                    closestUnit = unit;
                }
            }
            closestUnits.add(i , closestUnit);
            workSetUnits.remove(closestUnit);
        }


        return closestUnits;
    }

    public HashSet<Unit> getAlliesOnMap() {
        return alliesOnMap;
    }

    public HashSet<Unit> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public double getNumberSightedEnemiesOnMap(){ return numberSightedEnemiesOnMap;}

    public double getKillCountAllies() {
        return killCountAllies;
    }

    public double getKillCountEnemies() {
        return killCountEnemies;
    }

    public double getNumberEnemiesOnMap() {
        return numberEnemiesOnMap;
    }

    public double getNumberAlliesOnMap() {
        return numberAlliesOnMap;
    }

    public double getUnitPosX(){return unitPosX;}

    public double getUnitPosY(){return unitPosY;}

    public double getUnitHitpoints(){return unitHitPoints;}


    //private Pattern conditionPattern;

    //public Condition(String regex) {
      //  conditionPattern = Pattern.compile(regex);
    //}

    //public boolean matches(String environment) {
    //    return conditionPattern.matcher(environment).matches();
    //}
}
