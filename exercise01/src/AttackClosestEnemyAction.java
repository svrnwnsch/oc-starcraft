import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.logging.Logger;

/**
 * Created by Severin Wünsch on 24.05.2017.
 * <p>
 * Attack action to attack closest enemy of a given type
 */
public class AttackClosestEnemyAction implements Action {

    private final static Logger LOGGER = Logger.getLogger(VultureAI.class.getName());

    private UnitType enemyUnitType;
    private Game game;

    public AttackClosestEnemyAction(UnitType unitType, Game game) {
        enemyUnitType = unitType;
        this.game = game;

    }

    @Override
    public double executeAction(Unit executingUnit) {
        // get closes Unit with correct type
        Unit closestUnit = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Unit unit : game.getAllUnits()) {
            if (unit.getType() != enemyUnitType || unit.getPlayer() == executingUnit.getPlayer())
                continue;
            double distance = executingUnit.getPosition().getDistance(unit.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                closestUnit = unit;
            }
        }
        if (closestUnit == null)
            return Rewards.CANNOT_ATTACK;
        if (!executingUnit.canAttack(closestUnit)) {
            LOGGER.config(String.format("%s: Cannot attack closest Unit %s", executingUnit.toString(), closestUnit.toString()));
            return Rewards.CANNOT_ATTACK;
        }

        // Check Weaponcooldown
        if (closestUnit.isFlying()) {
            if (executingUnit.getAirWeaponCooldown() > 0) {
                LOGGER.config(String.format("%s: Cannot attack closest Unit %s because of Weaponcooldown %s",
                        executingUnit.toString(), closestUnit.toString(), executingUnit.getAirWeaponCooldown()));
                return Rewards.CANNOT_ATTACK;
            }
        } else {
            if (executingUnit.getGroundWeaponCooldown() > 0) {
                LOGGER.config(String.format("%s: Cannot attack closest Unit %s because of Weaponcooldown %s",
                        executingUnit.toString(), closestUnit.toString(), executingUnit.getGroundWeaponCooldown()));
                return Rewards.CANNOT_ATTACK;
            }
        }

        if (!executingUnit.isInWeaponRange(closestUnit)) {
            LOGGER.config(String.format("%s: Cannot attack closest Unit %s out of Range",
                    executingUnit.toString(), closestUnit.toString()));
            return Rewards.CANNOT_ATTACK;
        }

        executingUnit.attack(closestUnit);
        int expectedDamage = game.getDamageFrom(executingUnit.getType(), closestUnit.getType(), executingUnit.getPlayer(),
                closestUnit.getPlayer());
        // TODO: Change expected Damage if unites are not on the same ground height
        LOGGER.info(String.format("%s: Attacked closest Unit of Type %s: %s. Calculated Dmg: %s",
                executingUnit, enemyUnitType, closestUnit, expectedDamage));

        //TODO: scale Reward based on dealt Damage
        return Rewards.ATTACK_REWARD_MULTIPLIER * expectedDamage;

    }
}
