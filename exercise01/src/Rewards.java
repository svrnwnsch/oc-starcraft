/**
 * Created by Severin Wünsch on 23.05.2017.
 * <p>
 * Central Class to store all Reward values
 */
public class Rewards {
    // Positive Rewards
    public static int LEGAL_MOVE = 1;

    // Negative Rewards
    public static int ILLEGAL_MOVE = -1;
    public static int CANNOT_ATTACK = -10;

    // Multiplier used to config rewards
    public static int ATTACK_REWARD_MULTIPLIER = 2;
}
