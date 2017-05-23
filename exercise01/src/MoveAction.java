import bwapi.*;


/**
 * Created by OC2 on 23.05.2017.
 */
public abstract class MoveAction implements Action {

    // moveAngle should be in Radians
    private double moveAngle;
    private int moveRadius = 20;
    Game game;

    public MoveAction(Game game, Double moveAngle) {
        this.game = game;
        this.moveAngle = moveAngle;
    }


    @Override
    public double executeAction(Unit executingUnit, Unitset friendlyUnits, Unitset enemyUnits) {
        if (!executingUnit.canMove()) {
            return Rewards.ILLEGAL_MOVE;
        }
        Position position = executingUnit.getPosition();
        System.out.println("current position: " + position);
        Position newPosition = new Position(position.getX() + (int) (moveRadius * Math.cos(moveAngle)),
                position.getY() + (int) (moveRadius * Math.sin(moveAngle)));
        System.out.println("move to: " + newPosition);

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
