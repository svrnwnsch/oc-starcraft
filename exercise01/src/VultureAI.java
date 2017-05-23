import bwapi.*;
import java.util.*;

import java.util.HashSet;

public class VultureAI  extends DefaultBWListener implements Runnable {

    private final Mirror bwapi;
    private Game game;
    private Player self;
    private Vulture vulture;
    private HashSet<Unit> enemyUnits;
    private int frame;
    private HashSet<Unit> alliedUnits = new HashSet<Unit>();

    public VultureAI() {
        System.out.println("This is the VultureAI! :)");
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

        // complete map information
        this.game.enableFlag(0);
        
        // user input
        this.game.enableFlag(1);
        this.game.setLocalSpeed(10);
    }

    @Override
    public void onFrame() {

        //System.out.println("blabla");
        vulture.step();
        if (frame % 100 == 0) {
            List<Unit> allUnits = game.getAllUnits();
            //System.out.println("size of all Units:" + allUnits.size());
            for(Unit unit : allUnits){
                //System.out.println("size of all Units:" + allUnits.size());
                //System.out.println(unit);

                //System.out.println(unit.getPlayer());
                if(unit.getPlayer() == this.self){
                    //System.out.println("Unit is the from player" + unit.getPlayer());
                    alliedUnits.add(unit);
                    Situation situation = new Situation(unit, game);
                    HashSet<Unit> enemyUnits = situation.getEnemiesOnMap();
                    System.out.println("Enemy Units:" + situation.getNumberSightedEnemiesOnMap());
                    System.out.println("Allied Units:" + situation.getNumberAlliesOnMap());

                }
            }

        }
        frame++;
    }

    @Override
    public void onUnitCreate(Unit unit) {
        System.out.println("New unit discovered " + unit.getType());
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
    	if(this.enemyUnits.contains(unit)){
            this.enemyUnits.remove(unit);
    	}
    }
    

    @Override
    public void onEnd(boolean winner) {
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
