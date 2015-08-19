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
    private final AttackHelpClass attackHelpClass = new AttackHelpClass();
    protected LevelOfTower levelOfTower;
    protected int hasGainedLevels = 0;
    private int price;
    private ArrayList<Placeable> lastTargets = new ArrayList<Placeable>();
    private Collection<Placeable> allPlaceables = new ArrayList<Placeable>();
    private int kills = 0;
    private Board board;
    private Enemy lastTarget = null;
    private Enemy currentTarget = null;
    public enum TowerInformation {
        DMG, RANGE, RATEOFFIRE, ENEMIESCANSHOOTSAMETIME, DPS, EXTRA_DMG
    }

    public Tower(Board board, Collection<Placeable> allPlaceables, GameAction gameAction, int x, int y, Dimension dimension, Colour color,
                 Shapes shape, int price) {
        super(x, y, dimension, color, shape, gameAction);
        this.board = board;
        this.price = price;
        this.allPlaceables = allPlaceables;
        this.levelOfTower = new LevelOfTower();
    }

    /*

     */
    public void tick(EnemyWave allEnemies) {
        for (GameAction currentAction : getGameActions()) {
            currentAction.tick(this);
        }

        updateAllObjects();
        recalcLevel();

        attackHelpClass.findObjectsWithinRange(allPlaceables, this);

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
    public void addToCurrentPlaceablesInRangeOfThisTower(Placeable obj) {
        attackHelpClass.addToCurrentPlaceablesInRangeOfThisTower(obj);
    }


    public void setLastTarget(Enemy currentTarget) {
        this.lastTargets.add(currentTarget);
    }

    public void removeFromCurrentPlaceablesInRangeOfThisTower(Enemy currentEnemy) {
        attackHelpClass.removeFromCurrentPlaceablesInRangeOfThisTower(currentEnemy);
    }

    public Collection<Placeable> getPlacablesWithinRangeOfThisTower() {
        return attackHelpClass.getPlaceablesWithinRangeOfThisTower();
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
        if (attackHelpClass.getPlaceablesWithinRangeOfThisTower().isEmpty()) {
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
    public boolean isImortal() {
        return true;
    }

    public Player getPlayer() {
        return this.board.getPlayer();
    }


}