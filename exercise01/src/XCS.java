import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.*;

/**
 * Created by Severin Wünsch on 22.05.17.
 * <p>
 * General class for extended classifier system (XCS)
 * <p>
 * In the future specialised XCS should be extended for each class
 */
public class XCS {
    private Game game;
    private Random random = new Random();

    private HashSet<Classifier> population; // population of all classifiers in XCS
    private HashSet<Classifier> matchSet; // match set for current environment

    //TODO: replace two step reward with multistep reward an queue of fixed last k action sets
    private HashSet<Classifier> actionSet; // action set of all classifiers
    private HashSet<Classifier> lastActionSet; // action set of last action for delayed reward

    private HashMap<Integer, Action> actionDic;


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
    public static double pInit = 0; // Predicted reward init
    public static double epsilonInit = 0; // prediction error init
    public static double FInit = 0; // Fitness init
    private double pExplor = 0.5; // exploration probability
    private int thetaMNA; // number of all possible Action

    private int timestep = 0;

    public XCS(Game game) {
        this.game = game;
        actionSet = new HashSet<Classifier>(); // initialize set
        matchSet = new HashSet<Classifier>();
        population = new HashSet<>(N);
        actionDic.put(0, new HoldAction()); // Do Nothing
        actionDic.put(1, new MoveAction(this.game, 0)); // Move Right
        actionDic.put(2, new MoveAction(this.game, 45)); // Move Right Down
        actionDic.put(3, new MoveAction(this.game, 90)); // Move Down
        actionDic.put(4, new MoveAction(this.game, 135)); // Move Left Down
        actionDic.put(5, new MoveAction(this.game, 180)); // Move Left
        actionDic.put(6, new MoveAction(this.game, 225)); // Move Left Up
        actionDic.put(7, new MoveAction(this.game, 270)); // Move Up
        actionDic.put(8, new MoveAction(this.game, 315)); // Move Right Up

        // Generate Attack Closest Enemy for Each unit type listed in ReducedUnit.uniTypedMap
        for (UnitType unitType : ReducedUnit.unitTypeMap.keySet()) {
            actionDic.put(100 + ReducedUnit.unitTypeMap.get(unitType), new AttackClosestEnemyAction(unitType, this.game));
        }

        thetaMNA = actionDic.size();
    }

    public void step(Unit unit) {
        // Executes one Step of the XCS system for the submitted unit
        // TODO: Problem if we have more then one Unit the action set and last action set should be saved for each unit
        //       with respect their last actions

        // Shallow Copy of action set into last actions set
        timestep++;
        lastActionSet = (HashSet<Classifier>) actionSet.clone();
        // empty Matchset
        generateMatchSet(unit);
        int selectedActionId = selectActionId();
        generateActionSet(selectedActionId);

        reward(unit, actionDic.get(selectedActionId).executeAction(unit));

    }

    private void generateMatchSet(Unit unit) {
        matchSet.clear();
        Situation currentSituation = new Situation(unit, game);
        while (matchSet.isEmpty()) {
            for (Classifier classifier : population) {
                if (classifier.matchesSituation(currentSituation))
                    matchSet.add(classifier);
            }
            HashSet<Integer> actionsId = new HashSet<Integer>();
            for (Classifier matchClass : matchSet) {
                actionsId.add(matchClass.getActionId());
            }
            if (actionsId.size() < thetaMNA) {
                TreeSet<Integer> missingActionIds = new TreeSet<>(actionDic.keySet());
                missingActionIds.removeAll(actionsId);
                for (int i : missingActionIds) {
                    Classifier newClassifier = new Classifier(currentSituation, timestep);
                    newClassifier.setActionId(i);
                }
                deleteFromPopulation();
                matchSet.clear();

            }
        }
    }

    private int selectActionId() {
        // initialize predictionSet and fitnessSumSet
        HashMap<Integer, Double> predictionSet = new HashMap<Integer, Double>(actionDic.size());
        HashMap<Integer, Double> fitnessSumSet = new HashMap<Integer, Double>(actionDic.size());
        for (int i : actionDic.keySet()) {
            predictionSet.put(i, null);
            fitnessSumSet.put(i, 0.0);
        }

        for (Classifier classifier : matchSet) {
            int aId = classifier.getActionId();
            if (predictionSet.get(aId) == null) {
                predictionSet.put(aId, classifier.getPrediction() * classifier.getFitness());
            } else {
                predictionSet.put(aId, predictionSet.get(aId) + classifier.getPrediction() * classifier.getFitness());
            }
            fitnessSumSet.put(aId, fitnessSumSet.get(aId) + classifier.getFitness());
        }
        for (int aId : fitnessSumSet.keySet()) {
            if (fitnessSumSet.get(aId) != 0) {
                predictionSet.put(aId, predictionSet.get(aId) / fitnessSumSet.get(aId));
            }
        }

        // TODO: Select action probability based on prediction array and not only best
        predictionSet.values().removeIf(Objects::isNull); // remove all Actions where it is null
        int selectedActionId = -1;
        if (random.nextDouble() < pExplor) {
            List<Integer> aIds = new ArrayList<Integer>(predictionSet.keySet());
            selectedActionId = aIds.get(random.nextInt(aIds.size()));
        } else {
            double bestPrediction = Double.NEGATIVE_INFINITY;
            for (int aId : predictionSet.keySet()) {
                if (predictionSet.get(aId) > bestPrediction) {
                    bestPrediction = predictionSet.get(aId);
                    selectedActionId = aId;
                }
            }
        }
        return selectedActionId;

    }

    // updates the actionSet based on the current match set and the selectedActionId
    private void generateActionSet(int selectedActionId) {
        actionSet.clear();
        for (Classifier classifier : matchSet) {
            if (classifier.getActionId() == selectedActionId) {
                actionSet.add(classifier);
            }
        }
    }

    public void reward(double reward) {
        // Function Adds the given reward to the action and lastActionSet for every unit
    }

    public void reward(Unit unit, double reward) {
        // Function adds the given reward to the action and lastActionSet for the specific unit
        // TODO: Implement specific Reward method
        reward(reward);
    }

    public void deleteFromPopulation() {
        // Removes Conditions from the population until the size of the population is N
        // TODO: Implement Method
    }

    public void loadXCS(String filename) {
        // Loads a XCS from a given filename
        // TODO: Implement Loading functionality
    }

    public void saveXCS(String filename) {
        // Serializes the XCS into a file in filename
        // TODO: Implement Save functionality
    }
}
