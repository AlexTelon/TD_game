package main.tower;

import main.action.GameAction;
import main.board.Board;
import main.enemy.Enemy;
import main.enemy.EnemyWave;
import main.board.Placeable;
import main.graphics.ColorHandlerSingleton.Colour;
import main.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:23
 * The main class for towers, all towers extend this class. It holds the most basic things which a tower must have so
 * both shooting and nonshooting towers can use the methods defined in this class.
 */
public class Tower extends Placeable  {
    private RangeHandler rangeHandler;
    protected LevelOfTower levelOfTower;
    protected int hasGainedLevels = 0;
    private int price;
    private Collection<Placeable> allPlaceables = new ArrayList<Placeable>();
    private int kills = 0;
    private Board board;
    private Enemy lastTarget = null;
    private Collection<Enemy> currentTargets = new ArrayList<Enemy>();


    public enum TowerInformation {
        /**
         * The damage of the Tower.
         */
        DMG,

        /**
         * The range of the Tower.
         */
        RANGE,

        /**
         * The rate of fire of the Tower.
         */
        RATEOFFIRE,

        /**
         * The nr of enemies the tower can shoot at the same time.
         */
        ENEMIESCANSHOOTSAMETIME,

        /**
         * The damage per second of the Tower
         */
        DPS,

        /**
         * Any extra damage per shot that the Tower has, from buffing towers for example.
         */
        EXTRA_DMG
    }

    public Tower(Board board, Collection<Placeable> allPlaceables, GameAction gameAction, int x, int y, Dimension dimension, Colour color,
                 Shapes shape, int price, int range) {
        super(x, y, dimension, color, shape, gameAction);
        this.board = board;
        this.price = price;
        this.allPlaceables = allPlaceables;
        this.levelOfTower = new LevelOfTower();
        this.rangeHandler = new RangeHandler(range);
    }

    /*

     */
    public void tick(EnemyWave allEnemies) {
        for (GameAction currentAction : getGameActions()) {
            currentAction.tick(this);
        }

        updateAllObjects();
        recalcLevel();

        rangeHandler.updateObjectsWithinRange(allPlaceables, this);
    }

    /**
     * Delete itself and remove all its effects.
     */
    public void delete() {
        board.removeFromAllTowers(this);
        board.removeFromAllTowers(this);
        for (Placeable currentPlaceable : this.getPlacablesWithinRangeOfThisTower()) {
            for (GameAction action : this.getGameActions()) {
                currentPlaceable.removeBuffer(action);
                currentPlaceable.removeGameAction(action);
            }
        }
    }


    private void updateAllObjects() {
        allPlaceables = new ArrayList<Placeable>(); // GC is going to have a fun time because of this... :(
        allPlaceables.addAll(board.getAllObjects());
        for (Enemy currentEnemy : board.getAllEnemiesInCurrentWave()) {
            allPlaceables.add(currentEnemy);
        }
    }


    private void recalcLevel() {
        hasGainedLevels = levelOfTower.recalculateLevel();
        if (hasGainedLevels != 0) {
            for ( GameAction action : getGameActions()) {
                if (action.getAttackData() != null) { // getAttackData() returns null if the gameAction does not have an attack.
                    action.getAttackData().recalculateLevelMultiplier(hasGainedLevels);
                }
            }
        }
    }


    public int getPrice() {
        return price;
    }


    /*
    Things regarding placablesWithinRangeOfThisTower
     */
    public void addToCurrentPlaceablesInRangeOfThisTower(Placeable obj) {
        rangeHandler.addToCurrentPlaceablesInRange(obj);
    }


    public void removePlaceableInRange(Enemy currentEnemy) {
        rangeHandler.removePlaceableInRange(currentEnemy);
    }

    public Collection<Placeable> getPlacablesWithinRangeOfThisTower() {
        return rangeHandler.getPlacablesInRange();
    }

    public int getKills() {
        return kills;
    }

    public LevelOfTower getLevelOfTower() {
        return levelOfTower;
    }

    public void addKills(int newKills) { //TODO to levelOfTower
        this.kills += newKills;
    }

    public Enemy getLastTarget() {
        return lastTarget;
    }

    public void addToCurrentTargets(Enemy enemy) {
        this.currentTargets.add(enemy);
    }

    public void removeFromCurrentTargets(Enemy enemy) {
        this.currentTargets.remove(enemy);
    }

    public Iterable<Enemy> getCurrentTargets() {
        return currentTargets;
    }

    public boolean hasTarget() {
        if (rangeHandler.getPlacablesInRange().isEmpty()) {
            return false;
        }
        return true;
    }



    public boolean canShoot(Placeable currentObj) {
        for (GameAction currentGameAction : getGameActions()) {
            if (currentGameAction.canShoot(currentObj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is this a tower check.
     * @return true
     */
    @SuppressWarnings("RefusedBequest") // clearly intentionally, no need to call super.isTower()
    public boolean isTower() {
        return true;
    }

    /*
    A few getters
     */

    /**
     * Returns the aggregated value of all the shooting actions of a tower. Doubles are cast to ints!
     */
    public int getTowerInformation(TowerInformation towerInformation) {
        double counter = 0;
        for (GameAction currentGameAction : getGameActions()) {
            if (currentGameAction.hasAnAttack()) {
                switch (towerInformation) {
                    case DMG:
                        counter += currentGameAction.getAttackData().getDmg();
                        break;
                    case RANGE:
                        counter += currentGameAction.getAttackData().getRange();
                        break;
                    case RATEOFFIRE:
                        counter += currentGameAction.getAttackData().getRateOfFire();
                        break;
                    case ENEMIESCANSHOOTSAMETIME:
                        counter += currentGameAction.getAttackData().getEnemiesTowerCanShootAtTheSameFrame();
                        break;
                    case DPS:
                        counter += currentGameAction.getAttackData().getDPS();
                        break;
                    case EXTRA_DMG:
                        counter += currentGameAction.getExtraDmg();
                        break;

                    default:
                        System.out.println("Something went wrong in getTowerInformation");
                        return -1;
                }
            }
        }
        return (int)counter;
    }

    /**
     * Returns no cyan if the tower has no attack. If the tower has several shooting actions the color
     * of the first one is returned.
     * @return he color of the attack.
     */
    public Color getAttackColor() {
        Color attackColor = Color.cyan;
        for (GameAction currentGameAction : getGameActions()) {
            if (currentGameAction.hasAnAttack()) {
                return currentGameAction.getAttackData().getColor();

            }
        }
        return attackColor;
    }


    public Player getPlayer() {
        return this.board.getPlayer();
    }


    /*
    Misc
     */


    @Override
    public void addBuffers(GameAction action) {
        super.addBuffers(action);
        for (GameAction currentGameAction : getGameActions()) {
            currentGameAction.addBuffers(currentGameAction);
        }
    }


    @Override
    @SuppressWarnings("RefusedBequest") // clearly intentional
    public boolean isImortal() {
        return true;
    }
}