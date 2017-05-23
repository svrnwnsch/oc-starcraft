import bwapi.Game;
import bwapi.Unit;

import java.util.ArrayList;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Situation {

    public static final int alliesArraySize = 5;
    public static final int enemiesArraySize = 5;
    public static final int maxUnits = 50;
    public static final int maxPosX = 10000;
    public static final int maxPosY = 10000;


    private double numberAlliesOnMap;
    private double numberEnemiesOnMap;
    private double killCountEnemies;
    private double killCountAllies;

    private double unitHitPoints;
    private double unitPosX;
    private double unitPosY;
    private ArrayList<Unit> enemiesInSight;
    private ArrayList<Unit> alliesInSight;



    public Situation(Unit unit, Game game){
        unitPosX = ConditionUtil.parseValue(unit.getX(), maxPosX);
        unitPosY = ConditionUtil.parseValue(unit.getY(), maxPosY);
        unitHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());

        //numberAlliesOnMap = getNumberAlliesInSight();
        //numberEnemiesOnMap = getNumberEnemiesInSight();
        //killCountEnemies = getKillCountEnemies();
        //killCountAllies = getKillCountAllies();

        //enemiesInSight = new ArrayList<Unit>();
        //enemiesInSight = getEnemiesInSight();
        //alliesInSight = new ArrayList<Unit>();
        //alliesInSight = getAlliesInSight();
    }

    public ArrayList<Unit> getAlliesInSight() {
        return alliesInSight;
    }

    public ArrayList<Unit> getEnemiesInSight() {
        return enemiesInSight;
    }

    public double getKillCountAllies() {
        return killCountAllies;
    }

    public double getKillCountEnemies() {
        return killCountEnemies;
    }

    public double getNumberEnemiesInSight() {
        return 0;
    }

    public double getNumberAlliesInSight() {
        return 0;
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
