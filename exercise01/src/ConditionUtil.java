import java.util.Random;

/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class ConditionUtil {
    private static final Random RANDOM = new Random();

    public static double parseValue(int actualValue, int maxValue) {
        return ((double) actualValue) / ((double) maxValue);
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
                    " But we only counted: " + i + " in crossover");
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

    private static double getMutationChange() {
        return RANDOM.nextDouble() * 0.2 - 0.1; // return value in [-0.1, 0.1]
    }

    private static double getMutatedValue(double oldValue) {
        double change = getMutationChange();
        if (oldValue + change <= 0) {
            // if either interval or center gets smaller 0 then just half it
            oldValue = oldValue / 2;
        } else {
            oldValue += change;
        }
        return oldValue;
    }

    public static void applyMutation(Condition condition) {
        int sizeCondtion = 2 * (Situation.LENGTH + (Situation.closestAlliesArraySize +
                Situation.closestEnemiesArraySize) * ReducedUnit.LENGTH);

        int i = 0;
        if (RANDOM.nextDouble() < XCS.mu) {
            condition.situation.setUnitHitPoints(getMutatedValue(condition.situation.getUnitHitpoints()));
        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {
            condition.unitHitPointsInterval = getMutatedValue(condition.unitHitPointsInterval);
        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {
            condition.situation.setUnitPosX(getMutatedValue(condition.situation.getUnitPosX()));

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.unitPosXInterval = getMutatedValue(condition.unitPosXInterval);

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.situation.setUnitPosY(getMutatedValue(condition.situation.getUnitPosY()));

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.unitPosYInterval = getMutatedValue(condition.unitPosYInterval);

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.situation.setUnitGroundCooldown(getMutatedValue(condition.situation.getUnitGroundCooldown()));

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.unitGroundCooldownInterval = getMutatedValue(condition.unitGroundCooldownInterval);

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.situation.setNumberAlliesOnMap(getMutatedValue(condition.situation.getNumberAlliesOnMap()));

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.numberAlliesOnMapInterval = getMutatedValue(condition.numberAlliesOnMapInterval);

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.situation.setNumberSightedEnemiesOnMap(getMutatedValue(condition.situation.getNumberSightedEnemiesOnMap()));

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.numberSightedEnemiesOnMapInterval = getMutatedValue(condition.numberSightedEnemiesOnMapInterval);

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.situation.setKillCountAllies(getMutatedValue(condition.situation.getKillCountAllies()));

        }
        i++;
        if (RANDOM.nextDouble() < XCS.mu) {

            condition.killCountAlliesInterval = getMutatedValue(condition.killCountAlliesInterval);

        }
        i++;
        for (int j = 0; j < condition.situation.getClosestAllies().size(); j++) {
            ReducedUnit rUnit1 = condition.situation.getClosestAllies().get(j);
            ReducedUnitInterval rUnitInterval1 = condition.closestAlliesInterval.get(j);
            if (RANDOM.nextDouble() < XCS.mu) {
                rUnit1.normedDistance = getMutatedValue(rUnit1.normedDistance);
            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.normedDistanceInterval = getMutatedValue(rUnitInterval1.normedDistanceInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.normedAngle = getMutatedValue(rUnit1.normedAngle);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.normedAngleInterval = getMutatedValue(rUnitInterval1.normedAngleInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.normedHitPoints = getMutatedValue(rUnit1.normedHitPoints);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.normedHitPointsInterval = getMutatedValue(rUnitInterval1.normedHitPointsInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.unitType = getMutatedValue(rUnit1.unitType);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.unitTypeInterval = getMutatedValue(rUnitInterval1.unitTypeInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.velocityX = getMutatedValue(rUnit1.velocityX);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.velocityXInterval = getMutatedValue(rUnitInterval1.velocityXInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.velocityY = getMutatedValue(rUnit1.velocityY);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.velocityYInterval = getMutatedValue(rUnitInterval1.velocityYInterval);

            }
            i++;
        }

        for (int j = 0; j < condition.situation.getClosestEnemies().size(); j++) {
            ReducedUnit rUnit1 = condition.situation.getClosestEnemies().get(j);
            ReducedUnitInterval rUnitInterval1 = condition.closestEnemiesInterval.get(j);
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.normedDistance = getMutatedValue(rUnit1.normedDistance);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.normedDistanceInterval = getMutatedValue(rUnitInterval1.normedDistanceInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.normedAngle = getMutatedValue(rUnit1.normedAngle);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.normedAngleInterval = getMutatedValue(rUnitInterval1.normedAngleInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.normedHitPoints = getMutatedValue(rUnit1.normedHitPoints);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.normedHitPointsInterval = getMutatedValue(rUnitInterval1.normedHitPointsInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.unitType = getMutatedValue(rUnit1.unitType);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.unitTypeInterval = getMutatedValue(rUnitInterval1.unitTypeInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.velocityX = getMutatedValue(rUnit1.velocityX);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.velocityXInterval = getMutatedValue(rUnitInterval1.velocityXInterval);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnit1.velocityY = getMutatedValue(rUnit1.velocityY);

            }
            i++;
            if (RANDOM.nextDouble() < XCS.mu) {

                rUnitInterval1.velocityYInterval = getMutatedValue(rUnitInterval1.velocityYInterval);

            }
            i++;
        }

        if (i != sizeCondtion) {
            System.out.println("The size of Conditions should be: " + sizeCondtion +
                    " But we only counted: " + i + " in mutation");
            System.exit(-3);
        }
    }

    //checks if the parent classifier is more general than the through GA generated child classifier
    public static boolean isMoreGeneral(Classifier parent, Classifier child) {
        //check if the conditions in the child are all in the conditions of the parent
        return matchCondition(parent.getCondition(), child.getCondition());
    }

    public static boolean matchCondition(Condition parent, Condition child) {
        Situation parentSituation = parent.getSituation();
        Situation childSituation = child.getSituation();
        boolean result = (matchConditionVariable(parentSituation.getUnitHitpoints(), parent.getUnitHitPointsInterval(), childSituation.getUnitHitpoints(), child.getUnitHitPointsInterval()) ||
                matchConditionVariable(parentSituation.getUnitPosX(), parent.getUnitPosXInterval(), childSituation.getUnitPosX(), child.getUnitPosXInterval()) ||
                matchConditionVariable(parentSituation.getUnitPosY(), parent.getUnitPosYInterval(), childSituation.getUnitPosY(), child.getUnitPosYInterval()) ||
                matchConditionVariable(parentSituation.getUnitGroundCooldown(), parent.getUnitGroundCooldownInterval(), childSituation.getUnitGroundCooldown(), child.getUnitGroundCooldownInterval()) ||
                matchConditionVariable(parentSituation.getNumberAlliesOnMap(), parent.getNumberAlliesOnMapInterval(), childSituation.getNumberAlliesOnMap(), child.getNumberAlliesOnMapInterval()) ||
                matchConditionVariable(parentSituation.getNumberSightedEnemiesOnMap(), parent.getNumberSightedEnemiesOnMapInterval(), childSituation.getNumberSightedEnemiesOnMap(), child.getNumberSightedEnemiesOnMapInterval()) ||
                matchConditionVariable(parentSituation.getKillCountAllies(), parent.getKillCountAlliesInterval(), childSituation.getKillCountAllies(), child.getKillCountAlliesInterval()));
        for (int i = 0; i < parentSituation.getClosestEnemies().size(); i++) {
            result = result || (matchConditionVariable(parentSituation.getClosestEnemies().get(i).normedDistance, parent.getClosestEnemiesInterval().get(i).normedDistanceInterval, childSituation.getClosestEnemies().get(i).normedDistance, child.getClosestEnemiesInterval().get(i).normedDistanceInterval) ||
                    matchConditionVariable(parentSituation.getClosestEnemies().get(i).normedAngle, parent.getClosestEnemiesInterval().get(i).normedAngleInterval, childSituation.getClosestEnemies().get(i).normedAngle, child.getClosestEnemiesInterval().get(i).normedAngleInterval) ||
                    matchConditionVariable(parentSituation.getClosestEnemies().get(i).normedHitPoints, parent.getClosestEnemiesInterval().get(i).normedHitPointsInterval, childSituation.getClosestEnemies().get(i).normedHitPoints, child.getClosestEnemiesInterval().get(i).normedHitPointsInterval) ||
                    matchConditionVariable(parentSituation.getClosestEnemies().get(i).unitType, parent.getClosestEnemiesInterval().get(i).unitTypeInterval, childSituation.getClosestEnemies().get(i).unitType, child.getClosestEnemiesInterval().get(i).unitTypeInterval) ||
                    matchConditionVariable(parentSituation.getClosestEnemies().get(i).velocityX, parent.getClosestEnemiesInterval().get(i).velocityXInterval, childSituation.getClosestEnemies().get(i).velocityX, child.getClosestEnemiesInterval().get(i).velocityXInterval) ||
                    matchConditionVariable(parentSituation.getClosestEnemies().get(i).velocityY, parent.getClosestEnemiesInterval().get(i).velocityYInterval, childSituation.getClosestEnemies().get(i).velocityY, child.getClosestEnemiesInterval().get(i).velocityYInterval));
        }
        for (int i = 0; i < parentSituation.getClosestAllies().size(); i++) {
            result = result || (matchConditionVariable(parentSituation.getClosestAllies().get(i).normedDistance, parent.getClosestAlliesInterval().get(i).normedDistanceInterval, childSituation.getClosestAllies().get(i).normedDistance, child.getClosestAlliesInterval().get(i).normedDistanceInterval) ||
                    matchConditionVariable(parentSituation.getClosestAllies().get(i).normedAngle, parent.getClosestAlliesInterval().get(i).normedAngleInterval, childSituation.getClosestAllies().get(i).normedAngle, child.getClosestAlliesInterval().get(i).normedAngleInterval) ||
                    matchConditionVariable(parentSituation.getClosestAllies().get(i).normedHitPoints, parent.getClosestAlliesInterval().get(i).normedHitPointsInterval, childSituation.getClosestAllies().get(i).normedHitPoints, child.getClosestAlliesInterval().get(i).normedHitPointsInterval) ||
                    matchConditionVariable(parentSituation.getClosestAllies().get(i).unitType, parent.getClosestAlliesInterval().get(i).unitTypeInterval, childSituation.getClosestAllies().get(i).unitType, child.getClosestAlliesInterval().get(i).unitTypeInterval) ||
                    matchConditionVariable(parentSituation.getClosestAllies().get(i).velocityX, parent.getClosestAlliesInterval().get(i).velocityXInterval, childSituation.getClosestAllies().get(i).velocityX, child.getClosestAlliesInterval().get(i).velocityXInterval) ||
                    matchConditionVariable(parentSituation.getClosestAllies().get(i).velocityY, parent.getClosestAlliesInterval().get(i).velocityYInterval, childSituation.getClosestAllies().get(i).velocityY, child.getClosestAlliesInterval().get(i).velocityYInterval));
        }
        return result;
    }

    //matches the conditions to each other. tests if the values are larger than 1 or smaller than -1. If this applies for both, the parent and the child and the other end of the interval fits, it returns a true. otherwise it is checked in detail if the child fits in the parent.
    public static boolean matchConditionVariable(double parentClassifierValue, double parentClassifierInterval, double childClassifierValue, double childClassifierInterval) {
        if ((((parentClassifierValue - parentClassifierInterval) <= 0) && ((childClassifierValue - childClassifierInterval) <= 0) && (parentClassifierValue + parentClassifierInterval >= childClassifierValue + childClassifierInterval)) || // beide unteren intervall grenzen <= 0
                (((parentClassifierValue + parentClassifierInterval) >= 1) && ((childClassifierValue + childClassifierInterval) >= 1) && (parentClassifierValue - parentClassifierInterval <= childClassifierValue - childClassifierInterval)) || // beide oberen Grenzen >= 1
                (((parentClassifierValue - parentClassifierInterval) <= 0) && ((childClassifierValue - childClassifierInterval) <= 0) && ((parentClassifierValue + parentClassifierInterval) >= 1) && ((childClassifierValue + childClassifierInterval) >= 1))) {
            return true;
        } else {
            return (parentClassifierValue - parentClassifierInterval <= childClassifierValue - childClassifierInterval && parentClassifierValue + parentClassifierInterval >= childClassifierValue + childClassifierInterval);
        }
    }

}