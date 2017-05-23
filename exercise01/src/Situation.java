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
    private double numberSightedEnemiesOnMap;
    private double killCountEnemies;
    private double killCountAllies;

    private double unitHitPoints;
    private double unitPosX;
    private double unitPosY;
    private HashSet<Unit> enemiesOnMap = new HashSet<Unit>();
    private HashSet<Unit> alliesOnMap = new HashSet<Unit>();



    public Situation(Unit unit, Game game){
        //System.out.println("new Situation created");
        unitPosX = ConditionUtil.parseValue(unit.getX(), maxPosX);
        unitPosY = ConditionUtil.parseValue(unit.getY(), maxPosY);
        unitHitPoints = ConditionUtil.parseValue(unit.getHitPoints(), unit.getType().maxHitPoints());
        List<Unit> allUnits = game.getAllUnits();
        for(Unit currentUnit : allUnits){
            if(currentUnit.getPlayer() != game.self()){
                enemiesOnMap.add(currentUnit);
                //System.out.println("Added Eneemy Unit in Situation Constr");
            }
            else{
                alliesOnMap.add(currentUnit);
            }
        }
        numberAlliesOnMap = ConditionUtil.parseValue(alliesOnMap.size(), maxUnits);
        numberSightedEnemiesOnMap = ConditionUtil.parseValue(enemiesOnMap.size(), maxUnits);

        //numberAlliesOnMap = getNumberAlliesInSight();
        //numberEnemiesOnMap = getNumberEnemiesInSight();
        //killCountEnemies = getKillCountEnemies();
        //killCountAllies = getKillCountAllies();

        //enemiesInSight = new ArrayList<Unit>();
        //enemiesInSight = getEnemiesInSight();
        //alliesInSight = new ArrayList<Unit>();
        //alliesInSight = getAlliesInSight();
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
