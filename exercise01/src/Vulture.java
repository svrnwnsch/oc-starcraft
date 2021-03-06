import bwapi.*;

import java.util.HashSet;

public class Vulture {

    private final Mirror bwapi;
    private final HashSet<Unit> enemyUnits;
    final private Unit unit;

    public Vulture(Unit unit, Mirror bwapi, HashSet<Unit> enemyUnits) {
        this.unit = unit;
        this.bwapi = bwapi;
        this.enemyUnits = enemyUnits;
    }

    public void step() {
        /**
         * TODO: XCS
         */
        Unit target = getClosestEnemy();
        move(target);
        attackClosestEnemy(target);
    }

    private void move(Unit target) {
        unit.move(new Position(target.getPosition().getX(), target.getPosition().getY()), false);
    }

    private Unit getClosestEnemy() {
        Unit result = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Unit enemy : enemyUnits) {
            double distance = getDistance(enemy);
            if (distance < minDistance) {
                minDistance = distance;
                result = enemy;
            }
        }

        return result;
    }

    private double getDistance(Unit enemy) {
        return this.unit.getPosition().getDistance(enemy.getPosition());
    }

    private void attackClosestEnemy(Unit closestEnemy) {
        if (unit.canAttack(closestEnemy) == true) {
            unit.attack(closestEnemy);
        }
    }
}
