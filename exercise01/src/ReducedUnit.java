import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ReducedUnit {
    //public static String bla = "bla";
    public static final Map<UnitType, Double> unitTypeMap = createMap();

    public static Map<UnitType, Double> createMap() {
        Map<UnitType, Double> typeMap = new HashMap<UnitType, Double>();
        typeMap.put(UnitType.Terran_Command_Center, 0.0);
        typeMap.put(UnitType.Protoss_Zealot, 0.1);
        typeMap.put(UnitType.Terran_Vulture, 0.2);
        return typeMap;
    }

    public double normedDistance;
    public double normedAngle;
    public double normedHitPoints;
    public double unitType;

    public ReducedUnit(Unit unit, Position position){
        this.normedHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());
        this.normedDistance = ((double) position.getDistance(unit.getPosition())) / Situation.maxDistance;
        this.normedAngle = calculateAngle(position, unit.getPosition());
        this.unitType = unitTypeMap.get(unit.getType());
    }

    public double calculateAngle(Position centerPosition, Position relativePosition){
        return Math.atan2((double) (relativePosition.getY() - centerPosition.getY()), (double) (relativePosition.getX() - centerPosition.getX())) + Math.PI;
    }

    /*public static double distance(Position position1, Position position2){
        int diffX = position1.getX() - position2.getX();
        int diffY = position1.getY() - position2.getY();
        return (double) diffX
    }*/

}
