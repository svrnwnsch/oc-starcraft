import bwapi.Game;
import bwapi.Position;
import bwapi.Region;
import bwapi.Unit;

import java.util.logging.Logger;


/**
 * Created by OC2 on 23.05.2017.
 */
public class MoveAction implements Action {
    private final static Logger LOGGER = Logger.getLogger(VultureAI.class.getName());

    // moveAngle should be in Radians
    private double moveAngle;
    private int moveRadius = 100;
    Game game;

    public MoveAction(Game game, double moveAngle) {
        this.game = game;
        this.moveAngle = Math.toRadians(moveAngle);
    }


    @Override
    public double executeAction(Unit executingUnit) {
        if (!executingUnit.canMove()) {
            LOGGER.info("Unit cannot Move");
            return Rewards.ILLEGAL_MOVE;
        }
        Position position = executingUnit.getPosition();
        Position newPosition = new Position(position.getX() + (int) (moveRadius * Math.cos(moveAngle)),
                position.getY() + (int) (moveRadius * Math.sin(moveAngle)));

        if (executingUnit.move(newPosition)) {

            Region region = game.getRegionAt(newPosition);
            if (region == null || !region.isAccessible())
                return Rewards.ILLEGAL_MOVE; // Cannot Move to this Place
            return Rewards.LEGAL_MOVE;
        } else {
            return Rewards.ILLEGAL_MOVE;
        }
    }

    public void setMoveAngle(double moveAngle) {
        this.moveAngle = moveAngle;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof MoveAction)) return false;
        MoveAction otherMoveAction = (MoveAction) other;
        return moveAngle == otherMoveAction.getMoveAngle() && moveRadius == otherMoveAction.getMoveRadius();
    }

    public int getMoveRadius() {
        return moveRadius;
    }

    public double getMoveAngle() {
        return moveAngle;
    }
}
