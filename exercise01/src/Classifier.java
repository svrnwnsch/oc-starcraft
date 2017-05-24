/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Classifier {
    private Condition condition;
    private int actionId;
    private double prediction = XCS.pInit;
    private double predictionError = XCS.epsilonInit;
    private double fitness = XCS.FInit;
    private int exp = 0; // experience
    private int ts;  // time stamp
    private double actionSetSize = 1;
    private int numerosity = 1;

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
        return numerosity;
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


    public double getPrediction() {
        return prediction;
    }

}
