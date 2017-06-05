/**
 * Created by Severin Wünsch on 22.05.17.
 */
public class Classifier implements java.io.Serializable {
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

    // Constructor used to copy a Classifier in the GA
    public Classifier(Classifier parentClassifier) {
        this.condition = new Condition(parentClassifier.getCondition());
        this.ts = parentClassifier.getTs();
        this.actionId = parentClassifier.actionId;
        this.prediction = parentClassifier.getPrediction();
        this.predictionError = parentClassifier.getPredictionError();
        this.fitness = parentClassifier.getFitness();
        this.exp = 0;
        this.actionSetSize = parentClassifier.getActionSetSize();
        this.numerosity = 1;

    }


    public void printClassifier() {
        System.out.print("ActionId: " + actionId);
        System.out.print("\tprediction: " + prediction);
        System.out.print("\tpredictionError: " + predictionError);
        System.out.print("\tfitness: " + fitness);
        System.out.print("\texperience: " + exp);
        System.out.println("\tactionSetSize" + actionSetSize);
        condition.printCondition();
    }

    public boolean matchesSituation(Situation situation) {
        return condition.matchSituation(situation);
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public void setPredictionError(double predictionError) {
        this.predictionError = predictionError;
    }

    public double getActionSetSize() {
        return actionSetSize;
    }

    public void setActionSetSize(double actionSetSize) {
        this.actionSetSize = actionSetSize;
    }

    public void setNumerosity(int numerosity) {
        this.numerosity = numerosity;
    }

    public double vote(double avFitnessInPopulation) {
        double vote = this.actionSetSize * this.numerosity;
        if (exp > XCS.thetaDel && fitness / numerosity < XCS.delta * avFitnessInPopulation) {
            vote = vote * avFitnessInPopulation / (fitness / numerosity);
        }
        return vote;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public Condition getCondition() {
        return condition;
    }

}
