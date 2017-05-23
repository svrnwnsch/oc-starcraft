import bwapi.Game;
import bwapi.Unit;

import java.util.HashSet;
import java.util.List;
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
    private HashSet<Unit> enemiesInSight = new HashSet<Unit>();
    private HashSet<Unit> alliesInSight = new HashSet<Unit>();



    public Situation(Unit unit, Game game){
        //System.out.println("new Situation created");
        unitPosX = ConditionUtil.parseValue(unit.getX(), maxPosX);
        unitPosY = ConditionUtil.parseValue(unit.getY(), maxPosY);
        unitHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());
        List<Unit> allUnits = game.getAllUnits();
        for(Unit objectUnit : allUnits){
            if(objectUnit.getPlayer() != game.self()){
                enemiesInSight.add(objectUnit);
                System.out.println("Added Eneemy Unit in Situation Constr");
            }
        }

        //numberAlliesOnMap = getNumberAlliesInSight();
        //numberEnemiesOnMap = getNumberEnemiesInSight();
        //killCountEnemies = getKillCountEnemies();
        //killCountAllies = getKillCountAllies();

        //enemiesInSight = new ArrayList<Unit>();
        //enemiesInSight = getEnemiesInSight();
        //alliesInSight = new ArrayList<Unit>();
        //alliesInSight = getAlliesInSight();
    }

    public HashSet<Unit> getAlliesInSight() {
        return alliesInSight;
    }

    public HashSet<Unit> getEnemiesInSight() {
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
