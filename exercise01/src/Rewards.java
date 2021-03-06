/**
 * Created by Severin Wünsch on 23.05.2017.
 * <p>
 * Central Class to store all Reward values
 */
public class Rewards {
    // Positive Rewards
    public static final double LEGAL_MOVE = -0.5;
    public static final int DESTROY_ENEMY = 500;
    public static final int FIND_UNIT = 10;
    public static final int WIN_GAME = 1000;


    // Negative Rewards
    public static final double HOLD = -1;
    public static final int ILLEGAL_MOVE = -2;
    public static final int HIDE_ENEMY = -5;
    public static final int CANNOT_ATTACK = -5;
    public static final int DESTROYED_ALLY = 0; //-10;
    public static final int LOSE_GAME = -20;
    public static final double LOSE_GAME_STEP = -0.1;



    // Multiplier used to config rewards
    public static final int ATTACK_REWARD_MULTIPLIER = 4;
    public static final double TAKE_DAMAGE_REWARD_MULTIPLIER = 1;


}
