package main.tower;

import main.action.GameAction;
import main.board.Board;
import main.enemy.Enemy;
import main.enemy.EnemyWave;
import main.board.Placeable;
import main.graphics.ColorHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:23
 * The main class for towers, all towers extend this class. It holds the most basic things which a tower must have so
 * both shooting and nonshooting towers can use the methods defined in this class.
 */

public class Tower extends Placeable  {
    private final main.tower.attackHelpClass attackHelpClass = new main.tower.attackHelpClass();
    protected LevelOfTower levelOfTower;
    protected int hasGainedLevels = 0;
    private int price;
    private ArrayList<Placeable> lastTargets = new ArrayList<Placeable>();
    private ArrayList<Placeable> placablesWithinRangeOfThisTower = new ArrayList<Placeable>();
    private ArrayList<Placeable> allPlaceables;
    private double range = 200.0;
    private int kills = 0;
    private Board board;
    private Enemy lastTarget;
    private Enemy currentTarget;
    public enum TowerInformation {
        DMG, RANGE, RATEOFFIRE, ENEMIESCANSHOOTSAMETIME, DPS, extraDMG
    }

    public Tower(Board board, ArrayList<Placeable> allPlaceables, GameAction gameAction, int x, int y, Dimension dimension, ColorHandler.Colour color,
                 Shapes shape, int price, int difficulty) {
        super(x, y, dimension, color, shape, gameAction);
        this.board = board;
        this.price = price;
        this.allPlaceables = allPlaceables;
        this.levelOfTower = new LevelOfTower(difficulty);

    }

    /*
        Adds the towers GameAction to all placeables in range.
         */
    public void tick(EnemyWave allEnemies) {
        for (GameAction currentAction : super.getGameActions()) {
            currentAction.tick(this);
        }

        updateAllObjects();
        recalcLevel();

        placablesWithinRangeOfThisTower = attackHelpClass.findObjectsWithinRange(allPlaceables, this);

    }

    public void delete() {
        board.removeFromAllTowers(this);
    }


    private void updateAllObjects() {
        allPlaceables = new ArrayList<Placeable>(); // GC is going to have a fun time because of this... :(
        allPlaceables.addAll(board.getAllObjects());
        for (Enemy currentEnemy : board.getAllEnemiesInCurrentWave()) {
            allPlaceables.add(currentEnemy);
        }
    }

    private void recalcLevel() {
        hasGainedLevels = getLevelOfTower().recalculateLevel();
        if (hasGainedLevels != 0) {
            for ( GameAction action : getGameActions()) {
                if (action.getAttack() != null) { // getAttack() returns null if the gameAction does not have an attack.
                    action.getAttack().recalculateLevelMultiplier(hasGainedLevels);
                }
            }
        }
    }

    public ArrayList<Placeable> getLastTargets() {
        return lastTargets;
    }


    public int getPrice() {
        return price;
    }


    /*
    Things regarding placablesWithinRangeOfThisTower
     */
    public void addToCurrentPlacablesWithinRangeOfThisTower(Placeable obj) {
        attackHelpClass.addToCurrentPlacablesWithinRangeOfThisTower(obj);
    }


    public void setLastTarget(Enemy currentTarget) {
        this.lastTargets.add(currentTarget);
    }

    public void removeFromCurrentPlacablesWithinRangeOfThisTower(Enemy currentEnemy) {
        attackHelpClass.removeFromCurrentPlacablesWithinRangeOfThisTower(currentEnemy);
    }

    public ArrayList<Placeable> getPlacablesWithinRangeOfThisTower() {
        return attackHelpClass.getPlacablesWithinRangeOfThisTower();
    }

    public ArrayList<Placeable> getAllObjects() {
        return attackHelpClass.getAllObjects();
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

    public void setCurrentTarget(Enemy currentTarget) {
        this.currentTarget = currentTarget;
    }

    public boolean hasTarget() {
        if (attackHelpClass.getPlacablesWithinRangeOfThisTower().isEmpty()) {
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
     * returns the aggregated value of all the shooting actions of a tower. Doubles are cast to ints!
     * @param towerInformation
     * @return
     */
    public int getTowerInformation(TowerInformation towerInformation) {
        double counter = 0;
        for (GameAction currentGameAction : getGameActions()) {
            if (currentGameAction.hasAnAttack()) {
                switch (towerInformation) {
                    case DMG:
                        counter += currentGameAction.getAttack().getDmg();
                        break;
                    case RANGE:
                        counter += currentGameAction.getAttack().getRange();
                        break;
                    case RATEOFFIRE:
                        counter += currentGameAction.getAttack().getRateOfFire();
                        break;
                    case ENEMIESCANSHOOTSAMETIME:
                        counter += currentGameAction.getAttack().getEnemiesTowerCanShootAtTheSameFrame();
                        break;
                    case DPS:
                        counter += currentGameAction.getAttack().getDPS();
                        break;
                    case extraDMG:
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
     * @return
     */
    public Color getAttackColor() {
        Color attackColor = Color.cyan;
        for (GameAction currentGameAction : getGameActions()) {
            if (currentGameAction.hasAnAttack()) {
                return currentGameAction.getAttack().getColor();

            }
        }
        return attackColor;
    }


    @Override
    public void addBuffers(GameAction action) {
        super.addBuffers(action);
        for (GameAction currentGameAction : getGameActions()) {
            currentGameAction.addBuffers(currentGameAction);
        }
    }

    @Override
    public void removeBuffer(GameAction action) {
        super.removeBuffer(action);
    }

    @Override
    public boolean isImortal() {
        return true;
    }
}