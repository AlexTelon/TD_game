package main.Towers;

import main.action.GameActions;
import main.enemies.EnemyWave;
import main.board.Placeable;
import main.enemies.Enemies;
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

public class Towers extends Placeable  {
    protected LevelOfTower levelOfTower;
    protected int hasGainedLevels = 0;
    private int price;
    private ArrayList<Placeable> lastTargets = new ArrayList<Placeable>();
    private ArrayList<Placeable> PlacablesWithinRangeOfThisTower = new ArrayList<Placeable>();
    private ArrayList<Placeable> allObjects;
    private double range = 200.0;
    private int kills = 0;
    private Enemies lastTarget;
    private Enemies currentTarget;

    public Towers(ArrayList<Placeable> allObjects, GameActions gameActions, int x, int y, Dimension dimension, ColorHandler.Colour color,
                  Shapes shape, int price, int difficulty) {
        super(x, y, dimension, color, shape);
        this.price = price;
        this.allObjects = allObjects;
        this.levelOfTower = new LevelOfTower(difficulty);
    }

    /*
    Adds the towers GameAction to all placeables in range.
     */
    public void tick(EnemyWave allEnemies) {

        recalcLevel();
        findObjectsWithinRange(allObjects);

            //send action to all objects
            for (Placeable obj : PlacablesWithinRangeOfThisTower) {
                for (GameActions currentAction : super.getGameActions()) {
                    obj.addGameActions(currentAction);
                }
            }
        }

    private void recalcLevel() {
        hasGainedLevels = getLevelOfTower().recalculateLevel();
        if (hasGainedLevels != 0) {
            for ( GameActions action : getGameActions()) {
                if (action.getAttack() != null) { // getAttack() returns null if the gameAction does not have an attack.
                    action.getAttack().recalculateLevelMultiplier(hasGainedLevels);
                }
            }
        }
    }

    /**
         * Fins all placebles within range and returns them
         */
        private void findObjectsWithinRange(ArrayList<Placeable> allObjects) {
            for( Placeable obj : allObjects) {
                if (isObjectWithinRange(obj)) {
                    if (obj != this) { // so we dont get an infinite recursion?
                        addToCurrentPlacablesWithinRangeOfThisTower(obj); // why do we do this?
                    }
                }
            }
        }

    /**
     * Calculates if a object is within range of the tower.
     * @param obj it is a placable
     * @return true if it is in range.
     */
    public boolean isObjectWithinRange(Placeable obj) {
        if (obj.distanceTo(this) <= range) {
            return true;
        }
        return false;
    }



    public ArrayList<Placeable> getLastTargets() {
        return lastTargets;
    }


    public int getPrice() {
        return price;
    }

    /*
    Things regarding PlacablesWithinRangeOfThisTower
     */
    public void addToCurrentPlacablesWithinRangeOfThisTower(Placeable obj) {
        if (!PlacablesWithinRangeOfThisTower.contains(obj)) {
            this.PlacablesWithinRangeOfThisTower.add(obj);
        }
    }

    public void setLastTarget(Enemies currentTarget) {
        this.lastTargets.add(currentTarget);
    }

    public void removeFromCurrentPlacablesWithinRangeOfThisTower(Enemies currentEnemy) {
        this.PlacablesWithinRangeOfThisTower.remove(currentEnemy);
    }

    public ArrayList<Placeable> getPlacablesWithinRangeOfThisTower() {
        return PlacablesWithinRangeOfThisTower;
    }

    public ArrayList<Placeable> getAllObjects() {
        return allObjects;
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

    public Enemies getLastTarget() {
        return lastTarget;
    }

    public void setCurrentTarget(Enemies currentTarget) {
        this.currentTarget = currentTarget;
    }

    public boolean hasTarget() {
        if (getPlacablesWithinRangeOfThisTower().isEmpty()) {
            return false;
        }
        return true;
    }
}