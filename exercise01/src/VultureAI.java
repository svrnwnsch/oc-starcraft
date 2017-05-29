import bwapi.*;

import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VultureAI  extends DefaultBWListener implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(VultureAI.class.getName());

    private final Mirror bwapi;
    private Game game;
    private Player self;
    private Vulture vulture;
    // private HashSet<Unit> enemyUnits;
    private int frame;
    //private HashSet<Unit> alliedUnits = new HashSet<Unit>();
    private XCS xcs;
    private HashMap<Unit, Integer> friendlyUnitHealth = new HashMap<>();
    private int wonGames = 0;
    private int lostGames = 0;
    private boolean loadGame = false;
    private boolean saveGame = true;


    public VultureAI() {
        LOGGER.setLevel(Level.INFO);

        // Code so that console shows all logger events down to finer
        ConsoleHandler handler = new ConsoleHandler();
        // PUBLISH this level
        handler.setLevel(Level.FINER);
        //LOGGER.addHandler(handler);

        LOGGER.info("This is the VultureAI! :)");
        this.bwapi = new Mirror();
    }

    public static void main(String[] args) {
        new VultureAI().run();
    }

    @Override
    public void onStart() {
        //enemyUnits = new HashSet<Unit>();
        this.game = this.bwapi.getGame();
        this.self = game.self();
        this.frame = 0;
        friendlyUnitHealth.clear();
        LOGGER.config("Clearing FriendlyUnitHealth");
        if (xcs == null)
            xcs = new XCS(game);
        if (loadGame)
            xcs.loadXCS(XCS.fileName);
        // complete map information
        this.game.enableFlag(0);
        
        // user input
        this.game.enableFlag(1);
        this.game.setLocalSpeed(10);
    }

    @Override
    public void onFrame() {

        //LOGGER.info("blabla");
        //vulture.step();
        List<Unit> allUnits = game.getAllUnits();
        //LOGGER.info("size of all Units:" + allUnits.size());
        if (frame % 3 == 0) {
            // execute xcs only every second frame

            for (Unit unit : allUnits) {
                //LOGGER.info("size of all Units:" + allUnits.size());
                //LOGGER.info(unit);

                //LOGGER.info(unit.getPlayer());
                if (unit.getPlayer() == this.self) {
                    if (!friendlyUnitHealth.containsKey(unit)) {
                        friendlyUnitHealth.put(unit, unit.getHitPoints());
                    } else { // check if unit is in Map is necessary to avoid NPE at start
                        int dmgTaken = friendlyUnitHealth.get(unit) - unit.getHitPoints();
                        LOGGER.fine("Unit selected to calculate Healthchange " + unit);
                        if (dmgTaken > 0) {
                            LOGGER.config("Unit took dmg " + dmgTaken);
                            if (!unit.getType().isBuilding())
                                xcs.reward(unit, Rewards.TAKE_DAMAGE_REWARD_MULTIPLIER * (-dmgTaken));
                            else // If building apply penalty to all units
                                xcs.reward(Rewards.TAKE_DAMAGE_REWARD_MULTIPLIER * (-dmgTaken));
                            friendlyUnitHealth.replace(unit, unit.getHitPoints()); // update hitpoints
                        }
                    }
                    if (unit.getType() == UnitType.Terran_Vulture) {
                        LOGGER.fine("Unit " + unit + " is the from player" + unit.getPlayer());
                        xcs.step(unit);
                        /*
                        Situation situation = new Situation(unit, game);
                        LOGGER.config("Enemy Units:" + situation.getNumberSightedEnemiesOnMap());
                        LOGGER.config("Allied Units:" + situation.getNumberAlliesOnMap());
                        */

                    }
                }
            }


        }
        frame++;
    }

    @Override
    public void onUnitCreate(Unit unit) {
        // WARNING: the unit given here is not the same as in game.getAllUnits()
        LOGGER.info("New unit discovered " + unit.getType());
        /*
        UnitType type = unit.getType();

        if (unit.getPlayer() == this.self) {
            friendlyUnitHealth.put(unit, unit.getHitPoints());
            LOGGER.warning("Add Unit to Friendly units " + unit);
        }
        /*
        if (type == UnitType.Terran_Vulture) {
            if (unit.getPlayer() == this.self) {
                this.vulture = new Vulture(unit, bwapi, enemyUnits);
            }
        } else if (type == UnitType.Protoss_Zealot) {
            if (unit.getPlayer() != this.self) {
                enemyUnits.add(unit);
            }
        }
        */
    }
    
    @Override
    public void onUnitDestroy(Unit unit) {
        if (unit.getPlayer() == this.self) {
            xcs.reward(Rewards.DESTROYED_ALLY);
            LOGGER.warning("Allied Unit was Destroyed " + unit.getType() + ": " + unit);
        } else {
            xcs.reward(Rewards.DESTROY_ENEMY);
            LOGGER.warning("Destroyed Enemy Unit " + unit.getType());
        }
    }
    

    @Override
    public void onEnd(boolean winner) {
        if (winner) {
            xcs.reward(Rewards.WIN_GAME);
            //xcs.rewardAllActionSets(Rewards.WIN_GAME, false);
            wonGames++;
        } else {
            xcs.reward(Rewards.LOSE_GAME);
            //xcs.rewardAllActionSets(Rewards.LOSE_GAME_STEP, true);
            lostGames++;
        }
        LOGGER.warning("Game Ended did we win? " + winner + " Number of Classifiers: " + xcs.getPopSize()
                + " Number of games won: " + wonGames + " Number of games lost: " + lostGames);
        xcs.finnish();
        if (saveGame) {
            xcs.saveXCS(XCS.fileName);
        }
    }

    @Override
    public void onSendText(String text) {
    }

    @Override
    public void onReceiveText(Player player, String text) {
    }

    @Override
    public void onPlayerLeft(Player player) {
    }

    @Override
    public void onNukeDetect(Position position) {
    }

    @Override
    public void onUnitEvade(Unit unit) {
        // is also called when the unit get destroyed

    }

    @Override
    public void onUnitShow(Unit unit) {
        if (unit.getPlayer() != this.self) {
            xcs.reward(Rewards.FIND_UNIT);
            LOGGER.config("See a enemy Unit " + unit);
        }
    }

    @Override
    public void onUnitHide(Unit unit) {
        if (unit.getPlayer() != this.self) {
            xcs.reward(Rewards.HIDE_ENEMY);
            LOGGER.warning("Loosing sight to unit");
        }
    }

    @Override
    public void onUnitMorph(Unit unit) {

    }

    @Override
    public void onUnitRenegade(Unit unit) {

    }

    @Override
    public void onSaveGame(String gameName) {
    }

    @Override
    public void onUnitComplete(Unit unit) {
    }

    @Override
    public void onPlayerDropped(Player player) {
    }

    @Override
    public void run() {
        this.bwapi.getModule().setEventListener(this);
        this.bwapi.startGame();
    }
}
