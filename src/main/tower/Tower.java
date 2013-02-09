package main.tower;

import main.action.GameActions;
import main.enemy.EnemyWave;
import main.board.Placeable;
import main.tower.auraTower.NonShootableTower;
import main.tower.shootingTower.ShootableTower;
import main.enemy.Enemies;
import main.graphics.ColorHandler;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:23
 * All kinds of Towers are of this class. Different towers types are differed by a enum STATE. Non-shootable and Shootable
 * for example.
 */

public class Tower extends Placeable  {
    protected LevelOfTower levelOfTower;
    protected int hasGainedLevels = 0;
    protected GameActions towerAction;
    private int price;
    private ArrayList<Placeable> lastTargets = new ArrayList<Placeable>();
    private ArrayList<Placeable> currentTargets = new ArrayList<Placeable>();
    private ArrayList<Placeable> allObjects;


    public LevelOfTower getLevelOfTower() {
        return levelOfTower;
    }

    public enum STATE {
        Shootable, NonShootable
    }
    private STATE state;


    public Tower(ArrayList<Placeable> allObjects, STATE state, GameActions gameAction , int x, int y, Dimension dimension, ColorHandler.Colour color,
                 Shapes shape, int price, int difficulty) {
        super(x, y, dimension, color, shape);
        this.price = price;
        this.allObjects = allObjects;
        this.state = state;
        this.levelOfTower = new LevelOfTower(difficulty);
        this.towerAction = gameAction;
    }

    public void tick(EnemyWave allEnemies) {
        /*
        The only thing that a tower does is to update its own variables like its level. toweraction.tick(this) does the rest.
         */
        hasGainedLevels = getLevelOfTower().recalculateLevel();
        towerAction.tick(this);
        }


    public void tick(Tower currentTower, EnemyWave allEnemies ) {
        if (currentTower instanceof ShootableTower) {
            ((ShootableTower) currentTower).tick(allEnemies);
        } else if (currentTower instanceof NonShootableTower) {
           ((NonShootableTower) currentTower).tick();
        }
    }

    public int getPrice() {
        return price;
    }


    public void addToCurrentTargets(Placeable obj) {
        if (!currentTargets.contains(obj)) {
            this.currentTargets.add(obj);
        }
    }

    public void setLastTarget(Enemies currentTarget) {
        this.lastTargets.add(currentTarget);
    }

    public ArrayList<Placeable> getLastTargets() {
        return lastTargets;
    }

    public void removeFromCurrentTargets(Enemies currentEnemy) {
        this.currentTargets.remove(currentEnemy);
    }

    public ArrayList<Placeable> getCurrentTargets() {
        return currentTargets;
    }

    public ArrayList<Placeable> getAllObjects() {
        return allObjects;
    }

    @Override
    public void addBuffers(GameActions action) {
       if (this instanceof ShootableTower) {
            ((ShootableTower) this).getAttack().addBuffers(action);
        }

        super.addBuffers(action);
    }
}