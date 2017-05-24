/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Classifier {
    private Condition condition;
    private int actionId;
    private double prediction;


    private double predictionError;
    private double fitness;
    private int exp; // experience
    private int ts;  // time stamp
    private double actionSetSize = 1;
    private int numerosisty = 1;

    public Classifier(Situation situation, int timestep) {
        condition = new Condition(situation);
        this.ts = timestep;
    }

    public boolean matchesSituation(Situation situation) {
        condition.matchSituation(situation);
        return false;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getNumerosisty() {
        return numerosisty;
    }

    public double getPredictionError() {
        return predictionError;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

}
