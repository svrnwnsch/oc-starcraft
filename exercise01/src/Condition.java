import java.util.regex.Pattern;
import bwapi.*;
import java.util.*;
/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Condition {

    public int alliesArraySize = 5;
    public int enemiesArraySize = 5;
    public int maxUnits = 50;
    public int maxPosX = 1000;
    public int maxPosY = 1000;


    private double posX;
    private double posY;

    

    public Condition(){
        int numberAlliesInSight = getNumberAlliesInSight();
        int numberEnemiesInSight = getNumberEnemiesInSight();
        int killCountEnemies = getKillCountEnemies();
        int killCountAllies = getKillCountAllies();

        ArrayList<Unit> enemiesInSight = new ArrayList<Unit>();
        enemiesInSight = getEnemiesInSight();
        ArrayList<Unit> alliesInSight = new ArrayList<Unit>();
        alliesInSight = getAlliesInSight();
    }

    private ArrayList<Unit> getAlliesInSight() {
        return null;
    }

    private ArrayList<Unit> getEnemiesInSight() {
        return null;
    }

    private int getKillCountAllies() {
        return 0;
    }

    private int getKillCountEnemies() {
        return 0;
    }

    private int getNumberEnemiesInSight() {
        return 0;
    }

    private int getNumberAlliesInSight() {
        return 0;
    }
    //private Pattern conditionPattern;

    //public Condition(String regex) {
      //  conditionPattern = Pattern.compile(regex);
    //}

    //public boolean matches(String environment) {
    //    return conditionPattern.matcher(environment).matches();
    //}
}
