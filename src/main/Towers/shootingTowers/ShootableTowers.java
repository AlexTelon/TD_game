package main.Towers.shootingTowers;

import java.awt.*;
import java.util.ArrayList;

import main.action.GameActions;
import main.board.IDesign;
import main.enemies.EnemyWave;
import main.board.Placeable;
import main.Towers.Attack;
import main.Towers.LevelOfTower;
import main.Towers.Towers;
import main.action.shootingAction.ShootingAction;
import main.enemies.Enemies;
import main.graphics.ColorHandler;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:23
 * Class for shooting towers
 */
// TODO this and nonShootable should be merged to one type with a STATE instead of two different classes. They both
// do the same thing anyways, just different actions basically. LevelOfTower is a bit different and attack,
// but if attack is included in the shootingAction then LevelOfTower implemented to all tower types then the
// problem is solved.

/**
 * there have been some recent changes that we did not have time to finish. The idea was to do what
 * the " TO-DO" above says. But atm we have only moved away stuff from here to Attack which makes this class weird as
 * it is in the middle of a change. Everything works as it should but it is not finished.
 */

public class ShootableTowers extends Towers {
    private Attack attack = new Attack(super.getBuffers());
    private ShootingAction towerAction;
    private LevelOfTower levelOfTower;
   private Enemies lastTarget;
    private Enemies currentTarget;
     private int kills = 0;
    private int hasGainedLevels = 0;

    public ShootableTowers(ArrayList<Placeable> allObjects, int difficulty, double framerate, int x, int y,
                           Dimension dimension, ColorHandler.Colour colourOfTower, ColorHandler.Colour colourOfShoots,
                           IDesign.Shapes shape, int dmg, int range, int rOF, int enemiesTowerCanShootAtTheSameFrame,
                           int price) {

        super(allObjects, x, y, dimension, colourOfTower, shape, price);
        this.levelOfTower = new LevelOfTower(difficulty);
        this.attack = new Attack(dmg, range, rOF, colourOfShoots, framerate);
        attack.setEnemiesTowerCanShootAtTheSameFrame(enemiesTowerCanShootAtTheSameFrame);
        this.towerAction = new ShootingAction(this);
    }

    /**
     * Tick for towers is like all other tick in charge of gamemechanics. It checks if the tower can shoot,
     * if it can then which enemies it can shoot and then informs the enemy that it has been shoot.
     */
    public void tick(EnemyWave allEnemies) { // TODO Move to shootingAction so that we have only data in this class!
        getAttack().resetEnemiesTowerHasShoot();
        hasGainedLevels = getLevelOfTower().recalculateLevel();
        if (hasGainedLevels != 0) {
            attack.recalculateLevelMultiplier(hasGainedLevels);
        }

        if (attack.canShootAtThisFrame()) {


            for (Enemies currentEnemy : allEnemies ) { // loop through all enemies
                towerAction.tick(currentEnemy);
            }
        }
    }

   public Enemies getLastTarget() {
        return lastTarget;
    }

    public boolean canShoot(Enemies currentEnemy) {
        return towerAction.canShoot(currentEnemy);
    }

    public Attack getAttack() {
        return attack;
    }

    public int getKills() {
        return kills;
    }

    public LevelOfTower getLevelOfTower() {
        return levelOfTower;
    }

    public void setCurrentTarget(Enemies currentTarget) {
        this.currentTarget = currentTarget;
    }

    public boolean hasTarget() {
        if (super.getCurrentTargets().isEmpty()) {
            return false;
        }
        return true;
    }

    public void addKills(int newKills) { //TODO to levelOfTower
        this.kills += newKills;
    }

    @Override
    public void addBuffers(GameActions action) {
        //  attack.addBuffers(obj);
        super.addBuffers(action);
    }

    /**
     * CAUTION this replaces the current shootingAction with a new one in ShootableTowers!
     * @param action
     */
    @Override
    public void addGameActions(GameActions action) {
        towerAction = (ShootingAction) action;
    }

}