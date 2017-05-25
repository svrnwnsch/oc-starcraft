import bwapi.Game;
import bwapi.Unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Situation {

    public static final int closestAlliesArraySize = 3;
    public static final int closestEnemiesArraySize = 3;
    public static final int maxUnits = 50;
    //manually determined for current map(Karte-Aufgabe4_v1.scx)
    public static final int maxPosX = 5000;
    public static final int maxPosY = 4000;
    public static final int maxGroundCooldown = 100;
    public static final double maxDistance = Math.sqrt(maxPosX * maxPosX + maxPosY * maxPosY);


    private double unitHitPoints;
    private double unitPosX;
    private double unitPosY;
    private double unitGroundCooldown;

    private double numberAlliesOnMap;
    private double numberSightedEnemiesOnMap;
    private double numberOfAllies;
    private double killCountAllies;

    private ArrayList<ReducedUnit> closestEnemies;
    private ArrayList<ReducedUnit> closestAllies;


    public Situation(Unit unit, Game game) {
        //Values are always parsed on [0.,1.]
        unitPosX = ConditionUtil.parseValue(unit.getX(), maxPosX);
        unitPosY = ConditionUtil.parseValue(unit.getY(), maxPosY);
        unitHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());
        unitGroundCooldown = ConditionUtil.parseValue(unit.getGroundWeaponCooldown(), maxGroundCooldown);

        int killCount = 0;
        for (Unit u : game.getAllUnits()) {
            if (u.getPlayer() == unit.getPlayer()) { // is unit of same player
                killCount += u.getKillCount();
            }
        }
        killCountAllies = ConditionUtil.parseValue(killCount, maxUnits);

        List<Unit> allUnits = game.getAllUnits();
        //Separating enemy units from allied units
        HashSet<Unit> enemiesOnMap = new HashSet<Unit>();
        HashSet<Unit> alliesOnMap = new HashSet<Unit>();
        for (Unit currentUnit : allUnits) {
            if (currentUnit.getPlayer() != game.self()) {
                enemiesOnMap.add(currentUnit);
            } else if (!currentUnit.equals(unit)) {
                alliesOnMap.add(currentUnit);
            }
        }
        //Determining the closest Enemies and Alies
        closestEnemies = getClosestUnits(closestEnemiesArraySize, enemiesOnMap, unit);
        closestAllies = getClosestUnits(closestAlliesArraySize, alliesOnMap, unit);


        numberAlliesOnMap = ConditionUtil.parseValue(alliesOnMap.size(), maxUnits);
        numberSightedEnemiesOnMap = ConditionUtil.parseValue(enemiesOnMap.size(), maxUnits);


    }

    //Getting number many closest units to mainUnit sorted as an ArrayList. Sorting may be optimized
    public ArrayList<ReducedUnit> getClosestUnits(int number, HashSet<Unit> units, Unit mainUnit) {
        //Length of the ArrayList is final. Empty entries possible
        ArrayList<ReducedUnit> closestUnits = new ArrayList<ReducedUnit>(number);
        //cloning source set
        HashSet<Unit> workSetUnits = (HashSet<Unit>) units.clone();
        //sorting algorithm
        for (int i = 0; i < number; i++) {
            //if original length of workset is smaller than i
            if (workSetUnits.isEmpty())
                closestUnits.add(i, new ReducedUnit());
            else {
                double minimalDistance = 1.;
                Unit closestUnit = null;
                for (Unit unit : workSetUnits) {
                    double distance = ((double) unit.getPosition().getDistance(mainUnit.getPosition())) / maxDistance;
                    if (distance < minimalDistance) {
                        minimalDistance = distance;
                        closestUnit = unit;
                    }
                }
                closestUnits.add(i, new ReducedUnit(closestUnit, mainUnit.getPosition()));
                workSetUnits.remove(closestUnit);
            }


        }

        return closestUnits;
    }

    public double getUnitGroundCooldown() {
        return unitGroundCooldown;
    }

    public ArrayList<ReducedUnit> getClosestAllies() {
        return closestAllies;
    }

    public ArrayList<ReducedUnit> getClosestEnemies() {
        return closestEnemies;
    }


    public double getNumberSightedEnemiesOnMap() {
        return numberSightedEnemiesOnMap;
    }

    public double getKillCountAllies() {
        return killCountAllies;
    }

    public double getNumberOfAllies() {
        return numberOfAllies;
    }

    public double getNumberAlliesOnMap() {
        return numberAlliesOnMap;
    }

    public double getUnitPosX() {
        return unitPosX;
    }

    public double getUnitPosY() {
        return unitPosY;
    }

    public double getUnitHitpoints() {
        return unitHitPoints;
    }


    //private Pattern conditionPattern;

    //public Condition(String regex) {
    //  conditionPattern = Pattern.compile(regex);
    //}

    //public boolean matches(String environment) {
    //    return conditionPattern.matcher(environment).matches();
    //}
}
