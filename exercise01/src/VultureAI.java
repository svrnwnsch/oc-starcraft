import bwapi.*;

import java.util.HashSet;
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
    private HashSet<Unit> enemyUnits;
    private int frame;
    private HashSet<Unit> alliedUnits = new HashSet<Unit>();
    private XCS xcs;


    public VultureAI() {
        LOGGER.setLevel(Level.WARNING);

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
        enemyUnits = new HashSet<Unit>();
        this.game = this.bwapi.getGame();
        this.self = game.self();
        this.frame = 0;
        if (xcs == null)
            xcs = new XCS(game);
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
        if (frame % 1 == 0) {
            // execute xcs only every second frame

            for (Unit unit : allUnits) {
                //LOGGER.info("size of all Units:" + allUnits.size());
                //LOGGER.info(unit);

                //LOGGER.info(unit.getPlayer());
                if (unit.getPlayer() == this.self && unit.getType() == UnitType.Terran_Vulture) {
                    LOGGER.fine("Unit is the from player" + unit.getPlayer());
                    xcs.step(unit);
                    /*
                    Situation situation = new Situation(unit, game);
                    LOGGER.config("Enemy Units:" + situation.getNumberSightedEnemiesOnMap());
                    LOGGER.config("Allied Units:" + situation.getNumberAlliesOnMap());
                    */

                }
            }


        }
        frame++;
    }

    @Override
    public void onUnitCreate(Unit unit) {
        LOGGER.info("New unit discovered " + unit.getType());
        UnitType type = unit.getType();

        if (type == UnitType.Terran_Vulture) {
            if (unit.getPlayer() == this.self) {
                this.vulture = new Vulture(unit, bwapi, enemyUnits);
            }
        } else if (type == UnitType.Protoss_Zealot) {
            if (unit.getPlayer() != this.self) {
                enemyUnits.add(unit);
            }
        }
    }
    
    @Override
    public void onUnitDestroy(Unit unit) {
        if (unit.getPlayer() == this.self)
            xcs.reward(Rewards.DESTROYED_ALLY);
        else
            xcs.reward(Rewards.DESTROY_ENEMY);
    }
    

    @Override
    public void onEnd(boolean winner) {
        if (winner)
            xcs.reward(Rewards.WIN_GAME);
        else
            xcs.reward(Rewards.LOSE_GAME);
        LOGGER.warning("Game Ended did we win? " + winner + " Number of Classifiers: " + xcs.getPopSize());
        xcs.finnish();
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
    }

    @Override
    public void onUnitShow(Unit unit) {
        if (unit.getPlayer() != this.self)
            xcs.reward(Rewards.FIND_UNIT);
    }

    @Override
    public void onUnitHide(Unit unit) {
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
