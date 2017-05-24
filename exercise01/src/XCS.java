import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by Severin Wünsch on 22.05.17.
 * <p>
 * General class for extended classifier system (XCS)
 * <p>
 * In the future specialised XCS should be extended for each class
 */
public class XCS {
    private Game game;

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
    private double pInit = 0;
    private double epsilonInit = 0;
    private double FInit = 0;
    private double pExplor = 0.5; // exploration probability
    private int thetaMNA = 1; // number of all possible Action TODO: generate from code: number of all possible actions

    private int timestep = 0;

    public XCS(Game game) {
        this.game = game;
        actionSet = new HashSet<Classifier>(); // initialize set
        matchSet = new HashSet<Classifier>();
        population = new HashSet<>(N);
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
        generatePredictionSet();
        generateActionSet();


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

    private void generatePredictionSet() {

    }

    private void generateActionSet() {

    }

    public void updateFitness(HashSet<Classifier> actionSet) {
        double accuracySum = 0;
        Map<Classifier, Double> kappa = new HashMap<Classifier, Double>();
        for (Classifier cl : actionSet) {
            if (cl.getPredictionError() < epsilon0) {
                kappa.put(cl, 1.);
            } else {
                kappa.put(cl, alpha * Math.pow(cl.getPredictionError() / epsilon0, nu));
            }
            accuracySum = accuracySum + kappa.get(cl) * cl.getNumerosisty();
        }
        for (Classifier cl : actionSet) {
            cl.setFitness(cl.getFitness() + beta * (kappa.get(cl) * cl.getNumerosisty() / accuracySum - cl.getFitness()));
        }
    }

    public void reward(double reward) {
        // Function Adds the given reward to the action and lastActionSet
    }

    public void deleteFromPopulation() {
        // Removes Conditions from the population until the size of the population is N
        // TODO: Implement Method
    }

    public void loadXCS(String filename) {
        population = new HashSet<Classifier>();
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Classifier cl = null;
            while ((cl = (Classifier) objectIn.readObject()) != null) {
                population.add(cl);
            }
            objectIn.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("File Loading Error");
            e.printStackTrace();
        }
        // Loads a XCS from a given filename
        // TODO: Implement Loading functionality
    }

    public void saveXCS(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (Classifier cl : population) {
                objectOut.writeObject(cl);
            }
            objectOut.close();
            fileOut.close();
            System.out.println("Population has been saved under: " + filename);
        } catch (Exception e) {
            System.out.println("File Saving Error!");
            e.printStackTrace();
        }
        // Serializes the XCS into a file in filename
        // TODO: Implement Save functionality
    }
}
