import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ReducedUnit implements java.io.Serializable {
    //public static String bla = "bla";
    public static final Map<UnitType, Integer> unitTypeMap = createMap();

    public static Map<UnitType, Integer> createMap() {
        Map<UnitType, Integer> typeMap = new HashMap<UnitType, Integer>();
        typeMap.put(UnitType.Terran_Command_Center, 0);
        typeMap.put(UnitType.Protoss_Zealot, 1);
        typeMap.put(UnitType.Terran_Vulture, 2);
        return typeMap;
    }

    public double normedDistance = 1.;
    public double normedAngle = 1.;
    public double normedHitPoints = 1.;
    public double unitType = 1.;

    public void printReducedUnit() {
        System.out.print("\tnormeddistance " + normedDistance);
        System.out.print("\tnormedangle " + normedAngle);
        System.out.print("\tnormedHitpoints " + normedHitPoints);
        System.out.print("\tunittype " + unitType);
    }

    public ReducedUnit(){

    }

    public ReducedUnit(Unit unit, Position position){
        this.normedHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());
        this.normedDistance = Math.exp(-position.getDistance(unit.getPosition()) / 100);
        this.normedAngle = calculateAngle(position, unit.getPosition());
        this.unitType = ((double) unitTypeMap.get(unit.getType())) / 100;
    }

    public double calculateAngle(Position centerPosition, Position relativePosition){
        return (Math.atan2((double) (relativePosition.getY() - centerPosition.getY()), (double) (relativePosition.getX() - centerPosition.getX())) + Math.PI) / (20 * Math.PI);
    }

    /*public static double distance(Position position1, Position position2){
        int diffX = position1.getX() - position2.getX();
        int diffY = position1.getY() - position2.getY();
        return (double) diffX
    }*/

}
