import java.util.HashSet;

/**
 * Created by Severin Wünsch on 22.05.17.
 * <p>
 * General class for extended classifier system (XCS)
 * <p>
 * In the future specialised XCS should be extended for each class
 */
public class XCS {
    private HashSet<Condition> population; // population of all classifiers in XCS
    private HashSet<Condition> matchSet; // match set for current environment
    private HashSet<Condition> actionSet; // action set of all classifiers

    // XCS parameters taken from "An Algorithmic Description of XCS"

    private int N = 1000;  // population Size  untested
    private double beta = 0.1;  // learning rate
    private double alpha = 0.1;
    private double epsilon0 = 1; // should be 1% of maximum predicted reward
    private double nu = 5; // power parameter
    private double gamma = 0.71; // discount factor
    private int thetaGA = 30;
    private double chi = 0.7; // crossover probabilities
    private double mu = 0.02; // mutation probability
    private double thetaDel = 20; // deletion threshold
    private double delta = 0.1;
    private double thetaSub = 20; // subsumption threshold
    private double pInit = 0;
    private double epsilonInit = 0;
    private double FInit = 0;
    private double pExplor = 0.5; // exploration probability
    private int thetaMNA = 1; // number of all possible Action TODO: generate from code: number of all possible actions

    public void loadXCS(String filename) {

    }

    public void saveXCS(String filename) {

    }
}
