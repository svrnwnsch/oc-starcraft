import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

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
    private final static Logger LOGGER = Logger.getLogger(VultureAI.class.getName());
    public static String fileDirectory = "data";
    public static String fileName = fileDirectory + "\\xcs3.ser";

    private HashSet<Classifier> population; // population of all classifiers in XCS
    private HashSet<Classifier> matchSet; // match set for current environment

    //TODO: replace two step reward with multistep reward an queue of fixed last k action sets
    private LinkedList<AbstractMap.SimpleEntry<HashSet, Double>> actionSets;
    private ArrayList<HashSet<Classifier>> allActionSets;

    private HashMap<Integer, Action> actionDic;

    private final Object actionSetsLock = new Object();
    private final Object allActionSetsLock = new Object();
    private final Object lastRewardLock = new Object();

    private double lastReward = 0;


    // XCS parameters taken from "An Algorithmic Description of XCS"

    private int N = 5000;  // population Size  untested
    private double beta = 0.1;  // learning rate
    private double alpha = 0.1;
    private double epsilon0 = 1; // should be 1% of maximum predicted reward
    private double nu = 5; // power parameter
    private double gamma = 0.8; // discount factor
    private int thetaGA = 30;
    private double chi = 0.7; // crossover probabilities
    private double mu = 0.02; // mutation probability
    public static final double thetaDel = 20; // deletion threshold
    public static final double delta = 0.1;
    private double thetaSub = 20; // subsumption threshold
    public static double pInit = 0; // Predicted reward init
    public static double epsilonInit = 0; // prediction error init
    public static double FInit = 0; // Fitness init
    private double pExplor = 0.2; // exploration probability
    private int thetaMNA; // number of all possible Action

    private int timestep = 0;
    private static int MULTI_STEP_REWARD_LENGTH = 30;

    public XCS() {
        // only used to print population
    }

    public XCS(Game game) {
        this.game = game;
        //LOGGER.setLevel(Level.CONFIG);
        LOGGER.info("Initialising XCS");
        actionSets = new LinkedList<>();
        matchSet = new HashSet<Classifier>();
        population = new HashSet<Classifier>();
        allActionSets = new ArrayList<>();
        LOGGER.info("Initialised HashSets");
        actionDic = new HashMap<>();
        actionDic.put(0, new HoldAction()); // Do Nothing
        LOGGER.info("Initialised HoldAction");
        actionDic.put(1, new MoveAction(this.game, 0)); // Move Right
        //actionDic.put(2, new MoveAction(this.game, 45)); // Move Right Down
        //actionDic.put(3, new MoveAction(this.game, 90)); // Move Down
        //actionDic.put(4, new MoveAction(this.game, 135)); // Move Left Down
        actionDic.put(5, new MoveAction(this.game, 180)); // Move Left
        //actionDic.put(6, new MoveAction(this.game, 225)); // Move Left Up
        //actionDic.put(7, new MoveAction(this.game, 270)); // Move Up
        //actionDic.put(8, new MoveAction(this.game, 315)); // Move Right Up
        LOGGER.info("Initialised MoveActions");

        // Generate Attack Closest Enemy for Each unit type listed in ReducedUnit.uniTypedMap
        for (UnitType unitType : ReducedUnit.unitTypeMap.keySet()) {
            actionDic.put(100 + ReducedUnit.unitTypeMap.get(unitType), new AttackClosestEnemyAction(unitType, this.game));
        }
        LOGGER.info("Initialised Attack Closest Enemy actions");

        thetaMNA = actionDic.size();
        LOGGER.info("Finished Initialising XCS");
    }

    public void step(Unit unit) {
        // Executes one Step of the XCS system for the submitted unit
        // TODO: Problem if we have more then one Unit the action set and last action set should be saved for each unit
        //       with respect their last actions

        // Shallow Copy of action set into last actions set
        timestep++;
        LOGGER.config("Do xcs step");
        //lastActionSet = (HashSet<Classifier>) actionSet.clone();
        // empty Matchset
        generateMatchSet(unit);
        LOGGER.config("Generated Match set");
        int selectedActionId = selectActionId();
        generateActionSet(selectedActionId);
        synchronized (actionSetsLock) {
            while (actionSets.size() > MULTI_STEP_REWARD_LENGTH) { //remove oldest action Sets form queue
                AbstractMap.SimpleEntry<HashSet, Double> actionSet = actionSets.pollFirst();
                updateClassifier(actionSet.getValue(), actionSet.getKey());
            }
        }
        LOGGER.config("Generated Action Set " + actionSets.size());

        // Reward last action sets based on the current predicted Reward
        if (actionSets.size() > 1) {
            // calculate maximum predicted reward from current actionset
            double maxPrediction = Double.NEGATIVE_INFINITY;
            LOGGER.config("Updated Classifiers 1");
            synchronized (actionSetsLock) {
                for (Classifier classifier : (HashSet<Classifier>) actionSets.peekLast().getKey()) {
                    if (classifier.getPrediction() > maxPrediction) {
                        maxPrediction = classifier.getPrediction();
                    }
                }

                LOGGER.config("Updated Classifiers 2");
                updateActionSets(maxPrediction);
            }
            LOGGER.config("Updated Classifiers");
        }

        double curReward = actionDic.get(selectedActionId).executeAction(unit);
        LOGGER.info("The action id was " + selectedActionId + " With current Reward: " + curReward);
        synchronized (lastRewardLock) {
            lastReward = curReward;
        }
    }

    public synchronized void updateActionSets(double maxPrediction) {
        synchronized (lastRewardLock) {
            for (int i = actionSets.size() - 2; i > 0; i--) {
                int j = actionSets.size() - i;
                double reward;
                // add maxPrediction only to the second news set and only update lastReward else
                // this stops problems
                if (i == actionSets.size() - 2) { // j = 2
                    reward = lastReward + gamma * maxPrediction;
                } else { // j < 2
                    reward = Math.pow(gamma, j - 2) * (lastReward);
                }
                if (reward > 2000 || reward < -2000) {
                    System.out.println("First: " + Math.pow(gamma, j - 2) + " LastReward: " + lastReward + " maxPred:" +
                            (gamma * maxPrediction));
                }
                if (reward > 1000000 || reward < -100000)
                    System.exit(-2);
                // update cumulated reward in actionSets
                actionSets.get(i).setValue(actionSets.get(i).getValue() + reward);
            }
        }

    }

    public void finnish() {
        // Method for last Evaluation when game finished
        synchronized (actionSetsLock) {
            actionSets.add(new AbstractMap.SimpleEntry<HashSet, Double>(new HashSet(), 0.)); // add empty HashSet to as there is no Action Set in this last Step
            updateActionSets(0);
            for (AbstractMap.SimpleEntry<HashSet, Double> actionSetMap : actionSets) {
                updateClassifier(actionSetMap.getValue(), actionSetMap.getKey()); // Update all remaining Action Sets
            }
            actionSets.clear(); // empty all saved action sets
        }
        synchronized (allActionSetsLock) {
            allActionSets.clear();
        }
        synchronized (lastRewardLock) {
            lastReward = 0;
        }
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
                    LOGGER.info(population.size() + ": Created New Classifier with Action Id: " + i);
                    newClassifier.setActionId(i);
                    population.add(newClassifier);
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

        predictionSet.values().removeIf(Objects::isNull); // remove all Actions where it is null
        int selectedActionId = -1;
        // reduce over time the expolration to 1% of original Expolration
        if (random.nextDouble() < (pExplor - 0.99 * pExplor * Math.exp(-100000. / timestep))) {
            // do exploration
            List<Integer> aIds = new ArrayList<Integer>(predictionSet.keySet());
            selectedActionId = aIds.get(random.nextInt(aIds.size()));
            System.out.println("Do expolration " + timestep);
        } else {
            double bestPrediction = Double.NEGATIVE_INFINITY;
            double sumPositvPrediction = 0;
            for (int aId : predictionSet.keySet()) {
                double pred = predictionSet.get(aId);
                System.out.println("aId: " + aId + " pred: " + pred);
                if (pred > 0) {
                    sumPositvPrediction += pred;
                }
                if (pred > bestPrediction) {
                    // select action id with best prediction
                    bestPrediction = pred;
                    selectedActionId = aId;
                }
            }
            if (sumPositvPrediction > 0) {
                System.out.println("Sum of Positv Predcition is positiv");
                // at least some of the conditions predict positive reward
                // choose action probable based on reward size
                double threshold = random.nextDouble() * sumPositvPrediction;
                double predSum = 0;
                for (int aId : predictionSet.keySet()) {
                    double pred = predictionSet.get(aId);
                    if (pred > 0) {
                        predSum += pred;
                    }
                    if (predSum >= threshold) {
                        return aId;
                    }
                }
            }
        }
        return selectedActionId;

    }

    // updates the actionSet based on the current match set and the selectedActionId
    private void generateActionSet(int selectedActionId) {
        HashSet<Classifier> actionHashSet = new HashSet<Classifier>();
        for (Classifier classifier : matchSet) {
            if (classifier.getActionId() == selectedActionId) {
                actionHashSet.add(classifier);
            }
        }
        actionSets.add(new AbstractMap.SimpleEntry<HashSet, Double>(actionHashSet, 0.));
        synchronized (allActionSetsLock) {
            allActionSets.add(actionHashSet);
        }
    }

    private void updateFitness(HashSet<Classifier> usedActionSet) {
        double accuracySum = 0;
        Map<Classifier, Double> kappa = new HashMap<Classifier, Double>();
        for (Classifier cl : usedActionSet) {
            if (cl.getPredictionError() < epsilon0) {
                kappa.put(cl, 1.);
            } else {
                kappa.put(cl, alpha * Math.pow(cl.getPredictionError() / epsilon0, -nu));
            }
            accuracySum = accuracySum + kappa.get(cl) * cl.getNumerosisty();
        }
        for (Classifier cl : usedActionSet) {
            cl.setFitness(cl.getFitness() + beta * (kappa.get(cl) * cl.getNumerosisty() / accuracySum - cl.getFitness()));
        }
    }

    private void updateClassifier(double reward, HashSet<Classifier> usedActionSet) {
        // This Method updates the last action classifiers based on the reward they got and the expected reward the will
        // get from the current Action set (this is already calculated in die variable reward)

        LOGGER.info("Updated set: " + usedActionSet.hashCode() + " with Reward: " + reward);
        int sumNumerosity = 0;
        for (Classifier cl : usedActionSet) {
            sumNumerosity += cl.getNumerosisty();
        }
        for (Classifier cl : usedActionSet) {
            cl.setExp(cl.getExp() + 1);
            // Update prediction error
            if (cl.getExp() < 1 / beta)
                cl.setPredictionError(cl.getPredictionError() +
                        (Math.abs(reward - cl.getPrediction()) - cl.getPredictionError()) / cl.getExp());
            else
                cl.setPredictionError(cl.getPredictionError() +
                        beta * (Math.abs(reward - cl.getPrediction()) - cl.getPredictionError()));
            // Update prediction for classifier
            if (cl.getExp() < 1 / beta) {
                cl.setPrediction(cl.getPrediction() + (reward - cl.getPrediction()) / cl.getExp());
            } else
                cl.setPrediction(cl.getPrediction() + beta * (reward - cl.getPrediction()));

            // update action set size estimates cl.as
            if (cl.getExp() < 1 / beta)
                cl.setActionSetSize(cl.getActionSetSize() + (sumNumerosity - cl.getActionSetSize()) / cl.getExp());
            else
                cl.setActionSetSize(cl.getActionSetSize() + beta * (sumNumerosity - cl.getActionSetSize()));
        }
        updateFitness(usedActionSet);
        // TODO: Implement action set subsumation (maybe GA is enough)
    }

    public synchronized void reward(double reward) {
        // Function Adds the given reward to the action and lastActionSet for every unit
        synchronized (lastRewardLock) {
            lastReward += reward; // add all Rewards to lastReward for next iteration
        }
    }

    public void reward(Unit unit, double reward) {
        // Function adds the given reward to the action and lastActionSet for the specific unit
        // TODO: Implement specific Reward method
        reward(reward);
    }

    // Rewards all Actionsets in the current game
    // The lastReward gets distributed between all Actionsets if fixed is false
    public void rewardAllActionSets(double reward, boolean fixed) {
        synchronized (allActionSetsLock) {
            if (!fixed)
                reward = reward / allActionSets.size();
            LOGGER.warning("Rewarded all actions with: " + reward);
            for (HashSet<Classifier> set : allActionSets) {
                updateClassifier(reward, set);
            }
        }
    }

    private void deleteFromPopulation() {
        // Removes Conditions from the population until the size of the population is N
        // TODO: Implement Method
        while (population.size() > N) {


            double totalFitness = 0;
            int totalNumerosity = 0;
            for (Classifier c : population) {
                totalFitness += c.getFitness();
                totalNumerosity += c.getNumerosisty();
            }
            double avFitnessInPopulation = totalFitness / totalNumerosity;
            LOGGER.warning("Population is full deleting a Classifier. Avg Fitness: " + avFitnessInPopulation);
            double voteSum = 0;
            for (Classifier c : population) {
                voteSum += c.vote(avFitnessInPopulation);
            }
            double choicepoint = random.nextDouble() * voteSum;
            voteSum = 0;
            for (Classifier c : population) {
                voteSum += c.vote(avFitnessInPopulation);
                if (voteSum > choicepoint) {
                    if (c.getNumerosisty() > 1)
                        c.setNumerosity(c.getNumerosisty() - 1);
                    else
                        population.remove(c);
                    break;
                }


            }

        }
    }

    public int getPopSize() {
        return population.size();
    }

    public void loadXCS(String filename) {
        population = new HashSet<Classifier>();
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Classifier cl = null;
            try {
                timestep = (Integer) objectIn.readObject();
                while ((cl = (Classifier) objectIn.readObject()) != null) {
                    population.add(cl);
                }
            } catch (EOFException e) {
            }
            objectIn.close();
            fileIn.close();
        } catch (Exception e) {
            LOGGER.severe("File Loading Error");
            e.printStackTrace();
        }
        LOGGER.info("Loading " + filename + "completed");
        LOGGER.info(population.size() + " Classifier loaded");
        LOGGER.fine("Current path: " + Paths.get("").toAbsolutePath().toString());
        // Loads a XCS from a given filename
        // TODO: Implement Loading functionality
    }

    public void saveXCS(String filename) {
        // Serializes the XCS into a file in filename
        try {
            if (!new File(fileDirectory).exists()) {
                new File(fileDirectory).mkdirs();
            }
            LOGGER.config("Saving XCS-file");
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(timestep); // always save timestep first
            for (Classifier cl : population) {
                objectOut.writeObject(cl);
            }
            objectOut.close();
            fileOut.close();
            LOGGER.info("Population has been saved under: " + filename);
        } catch (Exception e) {
            LOGGER.severe("File Saving Error!");

            e.printStackTrace();

        }


    }

    public void printClassifiers() {
        for (Classifier cl : population) {
            cl.printClassifier();
        }
    }
}
