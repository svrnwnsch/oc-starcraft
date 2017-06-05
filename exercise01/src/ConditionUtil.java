import java.util.Random;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ConditionUtil {
    private static final Random RANDOM = new Random();

    public static double parseValue(int actualValue, int maxValue){
        return ((double) actualValue)/((double) maxValue);
    }

    // Method to apply crossover on two conditions
    public static void applyCrossover(Condition cond1, Condition cond2) {
        // Number of all Values AND their Interval size
        int sizeCondtion = 2 * (Situation.LENGTH + (Situation.closestAlliesArraySize +
                Situation.closestEnemiesArraySize) * ReducedUnit.LENGTH);
        double startCrossover = RANDOM.nextDouble() * sizeCondtion;
        double endCrossover = RANDOM.nextDouble() * sizeCondtion;
        double tmp;
        if (startCrossover > endCrossover) {
            tmp = startCrossover;
            startCrossover = endCrossover;
            endCrossover = tmp;
        }
        int i = 0;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.situation.getUnitHitpoints();
            cond1.situation.setUnitHitPoints(cond2.situation.getUnitHitpoints());
            cond2.situation.setUnitHitPoints(tmp);
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.unitHitPointsInterval;
            cond1.unitHitPointsInterval = cond2.unitHitPointsInterval;
            cond2.unitHitPointsInterval = tmp;
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.situation.getUnitPosX();
            cond1.situation.setUnitPosX(cond2.situation.getUnitPosX());
            cond2.situation.setUnitPosX(tmp);
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.unitPosXInterval;
            cond1.unitPosXInterval = cond2.unitPosXInterval;
            cond2.unitPosXInterval = tmp;
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.situation.getUnitPosY();
            cond1.situation.setUnitPosY(cond2.situation.getUnitPosY());
            cond2.situation.setUnitPosY(tmp);
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.unitPosYInterval;
            cond1.unitPosYInterval = cond2.unitPosYInterval;
            cond2.unitPosYInterval = tmp;
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.situation.getUnitGroundCooldown();
            cond1.situation.setUnitGroundCooldown(cond2.situation.getUnitGroundCooldown());
            cond2.situation.setUnitGroundCooldown(tmp);
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.unitGroundCooldownInterval;
            cond1.unitGroundCooldownInterval = cond2.unitGroundCooldownInterval;
            cond2.unitGroundCooldownInterval = tmp;
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.situation.getNumberAlliesOnMap();
            cond1.situation.setNumberAlliesOnMap(cond2.situation.getNumberAlliesOnMap());
            cond2.situation.setNumberAlliesOnMap(tmp);
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.numberAlliesOnMapInterval;
            cond1.numberAlliesOnMapInterval = cond2.numberAlliesOnMapInterval;
            cond2.numberAlliesOnMapInterval = tmp;
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.situation.getNumberSightedEnemiesOnMap();
            cond1.situation.setNumberSightedEnemiesOnMap(cond2.situation.getNumberSightedEnemiesOnMap());
            cond2.situation.setNumberSightedEnemiesOnMap(tmp);
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.numberSightedEnemiesOnMapInterval;
            cond1.numberSightedEnemiesOnMapInterval = cond2.numberSightedEnemiesOnMapInterval;
            cond2.numberSightedEnemiesOnMapInterval = tmp;
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.situation.getKillCountAllies();
            cond1.situation.setKillCountAllies(cond2.situation.getKillCountAllies());
            cond2.situation.setKillCountAllies(tmp);
        }
        i++;
        if (startCrossover <= i && i < endCrossover) {
            tmp = cond1.killCountAlliesInterval;
            cond1.killCountAlliesInterval = cond2.killCountAlliesInterval;
            cond2.killCountAlliesInterval = tmp;
        }
        i++;
        for (int j = 0; j < cond1.situation.getClosestAllies().size(); j++) {
            ReducedUnit rUnit1 = cond1.situation.getClosestAllies().get(j);
            ReducedUnit rUnit2 = cond2.situation.getClosestAllies().get(j);
            ReducedUnitInterval rUnitInterval1 = cond1.closestAlliesInterval.get(j);
            ReducedUnitInterval rUnitInterval2 = cond2.closestAlliesInterval.get(j);
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.normedDistance;
                rUnit1.normedDistance = rUnit2.normedDistance;
                rUnit2.normedDistance = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.normedDistanceInterval;
                rUnitInterval1.normedDistanceInterval = rUnitInterval2.normedDistanceInterval;
                rUnitInterval2.normedDistanceInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.normedAngle;
                rUnit1.normedAngle = rUnit2.normedAngle;
                rUnit2.normedAngle = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.normedAngleInterval;
                rUnitInterval1.normedAngleInterval = rUnitInterval2.normedAngleInterval;
                rUnitInterval2.normedAngleInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.normedHitPoints;
                rUnit1.normedHitPoints = rUnit2.normedHitPoints;
                rUnit2.normedHitPoints = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.normedHitPointsInterval;
                rUnitInterval1.normedHitPointsInterval = rUnitInterval2.normedHitPointsInterval;
                rUnitInterval2.normedHitPointsInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.unitType;
                rUnit1.unitType = rUnit2.unitType;
                rUnit2.unitType = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.unitTypeInterval;
                rUnitInterval1.unitTypeInterval = rUnitInterval2.unitTypeInterval;
                rUnitInterval2.unitTypeInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.velocityX;
                rUnit1.velocityX = rUnit2.velocityX;
                rUnit2.velocityX = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.velocityXInterval;
                rUnitInterval1.velocityXInterval = rUnitInterval2.velocityXInterval;
                rUnitInterval2.velocityXInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.velocityY;
                rUnit1.velocityY = rUnit2.velocityY;
                rUnit2.velocityY = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.velocityYInterval;
                rUnitInterval1.velocityYInterval = rUnitInterval2.velocityYInterval;
                rUnitInterval2.velocityYInterval = tmp;
            }
            i++;
        }

        for (int j = 0; j < cond1.situation.getClosestEnemies().size(); j++) {
            ReducedUnit rUnit1 = cond1.situation.getClosestEnemies().get(j);
            ReducedUnit rUnit2 = cond2.situation.getClosestEnemies().get(j);
            ReducedUnitInterval rUnitInterval1 = cond1.closestEnemiesInterval.get(j);
            ReducedUnitInterval rUnitInterval2 = cond2.closestEnemiesInterval.get(j);
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.normedDistance;
                rUnit1.normedDistance = rUnit2.normedDistance;
                rUnit2.normedDistance = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.normedDistanceInterval;
                rUnitInterval1.normedDistanceInterval = rUnitInterval2.normedDistanceInterval;
                rUnitInterval2.normedDistanceInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.normedAngle;
                rUnit1.normedAngle = rUnit2.normedAngle;
                rUnit2.normedAngle = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.normedAngleInterval;
                rUnitInterval1.normedAngleInterval = rUnitInterval2.normedAngleInterval;
                rUnitInterval2.normedAngleInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.normedHitPoints;
                rUnit1.normedHitPoints = rUnit2.normedHitPoints;
                rUnit2.normedHitPoints = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.normedHitPointsInterval;
                rUnitInterval1.normedHitPointsInterval = rUnitInterval2.normedHitPointsInterval;
                rUnitInterval2.normedHitPointsInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.unitType;
                rUnit1.unitType = rUnit2.unitType;
                rUnit2.unitType = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.unitTypeInterval;
                rUnitInterval1.unitTypeInterval = rUnitInterval2.unitTypeInterval;
                rUnitInterval2.unitTypeInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.velocityX;
                rUnit1.velocityX = rUnit2.velocityX;
                rUnit2.velocityX = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.velocityXInterval;
                rUnitInterval1.velocityXInterval = rUnitInterval2.velocityXInterval;
                rUnitInterval2.velocityXInterval = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnit1.velocityY;
                rUnit1.velocityY = rUnit2.velocityY;
                rUnit2.velocityY = tmp;
            }
            i++;
            if (startCrossover <= i && i < endCrossover) {
                tmp = rUnitInterval1.velocityYInterval;
                rUnitInterval1.velocityYInterval = rUnitInterval2.velocityYInterval;
                rUnitInterval2.velocityYInterval = tmp;
            }
            i++;
        }

        if (i != sizeCondtion) {
            System.out.println("The size of Conditions should be: " + sizeCondtion +
                    " But we only counted: " + i);
            System.exit(-2);
        }

        /*
        if(startCrossover <= i && i < endCrossover){
            tmp = cond1.situation.;
            cond1.situation.(cond2.situation.);
            cond2.situation.;
        }
        i++;
        if(startCrossover <= i && i < endCrossover){
            tmp = cond1.;
            cond1. = cond2.;
            cond2. = tmp;
        }
        i++;
        */

    }

    public static void applyMutation(Condition condition) {
        int sizeCondtion = 2 * (Situation.LENGTH + (Situation.closestAlliesArraySize +
                Situation.closestEnemiesArraySize) * ReducedUnit.LENGTH);
    }

}