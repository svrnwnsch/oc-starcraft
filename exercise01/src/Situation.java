import bwapi.Game;
import bwapi.Unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Situation implements java.io.Serializable {

    public static final int closestAlliesArraySize = 0;
    public static final int closestEnemiesArraySize = 3;
    public static final int maxUnits = 10;
    //manually determined for current map(Karte-Aufgabe4_v1.scx)
    public static final int maxPosX = 5000;
    public static final int maxPosY = 4000;
    public static final int maxGroundCooldown = 100;
    public static final double maxDistance = Math.sqrt(maxPosX * maxPosX + maxPosY * maxPosY);
    public static final int maxPossibleHP = 1000;
    // TODO: update if we have buildings

    // Number of primitive Variables in this Class
    public static final int LENGTH = 7;


    private double unitHitPoints;
    private double unitPosX;
    private double unitPosY;
    private double unitGroundCooldown;

    private double numberAlliesOnMap;
    private double numberSightedEnemiesOnMap;
    private double killCountAllies;

    private ArrayList<ReducedUnit> closestEnemies;
    private ArrayList<ReducedUnit> closestAllies;

    public void printSituation() {
        System.out.print("\tunithitpoints" + unitHitPoints);
        System.out.print("\tunitposx" + unitPosX);
        System.out.print("\tunitposy" + unitPosY);
        System.out.print("\tunitgroundcooldown" + unitGroundCooldown);
        System.out.print("\tunmbersightedenemiesonmap" + numberSightedEnemiesOnMap);
        for (ReducedUnit reducedUnit : closestEnemies) {
            reducedUnit.printReducedUnit();
        }
        System.out.println();
    }


    public Situation(Unit unit, Game game) {
        //Values are always parsed on [0.,1.]
        unitPosX = ConditionUtil.parseValue(unit.getX(), maxPosX);
        unitPosY = ConditionUtil.parseValue(unit.getY(), maxPosY);
        unitHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), maxPossibleHP);
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

    // Constructor for Deepcopy

    public Situation(Situation parent) {
        unitPosX = parent.unitPosX;
        unitPosY = parent.unitPosY;
        unitHitPoints = parent.unitHitPoints;
        unitGroundCooldown = parent.unitGroundCooldown;
        killCountAllies = parent.killCountAllies;
        closestAllies = new ArrayList<>();
        for (ReducedUnit reducedUnit : parent.closestAllies) {
            closestAllies.add(new ReducedUnit(reducedUnit));
        }
        closestEnemies = new ArrayList<>();
        for (ReducedUnit reducedUnit : parent.closestEnemies) {
            closestEnemies.add(new ReducedUnit(reducedUnit));
        }
        numberAlliesOnMap = parent.getNumberAlliesOnMap();
        numberSightedEnemiesOnMap = parent.getNumberSightedEnemiesOnMap();
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

    public double getUnitHitPoints() {
        return unitHitPoints;
    }

    public void setUnitHitPoints(double unitHitPoints) {
        this.unitHitPoints = unitHitPoints;
    }

    public void setUnitPosX(double unitPosX) {
        this.unitPosX = unitPosX;
    }

    public void setUnitPosY(double unitPosY) {
        this.unitPosY = unitPosY;
    }

    public void setUnitGroundCooldown(double unitGroundCooldown) {
        this.unitGroundCooldown = unitGroundCooldown;
    }

    public void setNumberAlliesOnMap(double numberAlliesOnMap) {
        this.numberAlliesOnMap = numberAlliesOnMap;
    }

    public void setNumberSightedEnemiesOnMap(double numberSightedEnemiesOnMap) {
        this.numberSightedEnemiesOnMap = numberSightedEnemiesOnMap;
    }

    public void setKillCountAllies(double killCountAllies) {
        this.killCountAllies = killCountAllies;
    }
}
