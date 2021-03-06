package main.action;

import main.graphics.ColorHandlerSingleton;
import main.graphics.ColorHandlerSingleton.Colour;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-26
 * Time: 19:34
 * This is the class that holds information about the attack of a tower (model), HOW it shoots and when is up to the
 * class ShootingAction
 */
public class AttackData
{
    private int dmg;
    private int extraDmg = 0;
    private double range;
    private double rateOfFire; // attacks per second
    private double rateOfFirePerFrame;
    private double rateOfFireCounter;
    private Color color = Color.ORANGE;
    private Collection<GameAction> buffers = new ArrayList<GameAction>();

    private int enemiesTowerCanShootAtTheSameFrame = 1;
    private int enemiesTowerHasShoot = 0;

    public AttackData(int dmg, double range, double rateOfFire, int enemiesTowerCanShootAtSameFrame, Colour colour,
                      double framerate) {
        this.dmg = dmg;
        this.range = range;
        this.rateOfFire = rateOfFire;
        this.color = ColorHandlerSingleton.getInstance().getGUIColour(colour);
        this.setRateOfFirePerFrame(framerate);
        this.enemiesTowerCanShootAtTheSameFrame = enemiesTowerCanShootAtSameFrame;
    }

    public int getDmg() {
        return dmg+getBufferDmg();
    }

    public void addDmg(int dmg) {
        this.dmg += dmg;
    }

    public double getRange() {
        double extraRange = 0;
        for (GameAction action: getBuffers()) {
            extraRange += action.getExtraRange();
        }
        return range+ extraRange;
    }

    public double getRateOfFire() {
        return rateOfFire;
    }

    public void setRateOfFirePerFrame(double framerate) {
        // rate of fire = shoots per second
        this.rateOfFirePerFrame = rateOfFire* framerate/1000;
    }

    public double getDPS() {
        return rateOfFire*(dmg+extraDmg);
    }

    public Color getColor() {
        return color;
    }

    public int getBufferDmg() {
        int dmgFromBuffers = 0;
        for (GameAction action: getBuffers()) {
            dmgFromBuffers += action.getExtraDmg();
        }
        extraDmg = dmgFromBuffers;
        return dmgFromBuffers;
    }

    // can not shoot more than 1 time per frame per enemy
    public boolean canShootAtThisFrame() {
        rateOfFireCounter += rateOfFirePerFrame;
        if (rateOfFireCounter >= 0) {
            rateOfFireCounter--;
            return true;
        }
        return false;
    }

    public void recalculateLevelMultiplier(int levels) {
        addDmg(levels);
    }

    public Iterable<GameAction> getBuffers() {
        return buffers;
    }

    public void addBuffers(GameAction action) {
        if (!buffers.contains(action)) {
            this.buffers.add(action);
        }
    }

    public void removeBuffers(GameAction action) {
        this.buffers.remove(action);
    }

    public int getEnemiesTowerHasShoot() {
        return enemiesTowerHasShoot;
    }

    public void addEnemiesTowerHasShoot() {
        this.enemiesTowerHasShoot++;
    }

    public void resetEnemiesTowerHasShoot() {
        this.enemiesTowerHasShoot = 0;
    }

    public int getEnemiesTowerCanShootAtTheSameFrame() {
        return enemiesTowerCanShootAtTheSameFrame;
    }

    public boolean isRememberOldTarget() {
        final boolean rememberOldTarget = false;
        return rememberOldTarget;
    }
}
