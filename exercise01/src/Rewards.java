/**
 * Created by Severin Wünsch on 23.05.2017.
 * <p>
 * Central Class to store all Reward values
 */
public class Rewards {
    // Positive Rewards
    public static final double LEGAL_MOVE = -0.1;
    public static final int DESTROY_ENEMY = 100;
    public static final int FIND_UNIT = 10;
    public static final int WIN_GAME = 1000;


    // Negative Rewards
    public static final int ILLEGAL_MOVE = -1;
    public static final int CANNOT_ATTACK = -10;
    public static final int DESTROYED_ALLY = -100;
    public static final int LOSE_GAME = -1000;



    // Multiplier used to config rewards
    public static final int ATTACK_REWARD_MULTIPLIER = 2;
    public static final double TAKE_DAMAGE_REWARD_MULTIPLIER = 1;


}
