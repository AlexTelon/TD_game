package main.Towers;

import main.action.GameActions;
import main.enemies.EnemyWave;
import main.board.Placeable;
import main.Towers.AuraTowers.NonShootableTower;
import main.Towers.shootingTowers.ShootableTowers;
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
    private int price;
    private ArrayList<Placeable> lastTargets = new ArrayList<Placeable>();
    private ArrayList<Placeable> currentTargets = new ArrayList<Placeable>();
    private ArrayList<Placeable> allObjects;

    public Towers(ArrayList<Placeable> allObjects, int x, int y, Dimension dimension, ColorHandler.Colour color,
                  Shapes shape, int price) {
        super(x, y, dimension, color, shape);
        this.price = price;
        this.allObjects = allObjects;
    }

    public void tick(Towers currentTower, EnemyWave allEnemies ) {
        if (currentTower instanceof ShootableTowers) {
            ((ShootableTowers) currentTower).tick(allEnemies);
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
       if (this instanceof ShootableTowers) {
            ((ShootableTowers) this).getAttack().addBuffers(action);
        }

        super.addBuffers(action);
    }
}