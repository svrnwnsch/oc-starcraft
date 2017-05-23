import bwapi.Game;

/**
 * Created by OC2 on 23.05.2017.
 */
public class MoveRightAction extends MoveAction {
    public MoveRightAction(Game game) {
        super(game);
        this.setMoveAngle(Math.toRadians(0));
    }


}
