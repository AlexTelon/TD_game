package main.action;

import main.action.GameActions;
import main.graphics.ColorHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-26
 * Time: 19:34
 * This is the class that holds information about the attack of a tower (model), HOW it shoots and when is up to the
 * class ShootingAction
 */
public class Attack {
    private int dmg;
    private int extraDmg = 0;
    private double range;
    private double rateOfFire; // attacks per second
    private double rateOfFirePerFrame;
    private double rateOfFireCounter;
    private ColorHandler colorHandler = ColorHandler.getInstance();
    private Color color = Color.ORANGE;
   private ArrayList<GameActions> buffers = new ArrayList<GameActions>();

    private int enemiesTowerCanShootAtTheSameFrame = 1;
    private int enemiesTowerHasShoot = 0;
    private boolean rememberOldTarget = false;

    public Attack(ArrayList<GameActions> buffers) {
        this.dmg = 3;
        this.range = 200;
        this.rateOfFire = 2;
        this.buffers = buffers;
    }

    public Attack(int dmg, double range, double rateOfFire, int enemiesTowerCanShootAtTheSameFrame, ColorHandler.Colour colour, double framerate) {
        this.dmg = dmg;
        this.range = range;
        this.rateOfFire = rateOfFire;
        this.color = colorHandler.getGUIColour(colour);
        this.setRateOfFirePerFrame(framerate);
        this.enemiesTowerCanShootAtTheSameFrame = enemiesTowerCanShootAtTheSameFrame;
    }

    public int getDmg() {
        return dmg+getBufferDmg();
    }

    public void addDmg(int dmg) {
        this.dmg += dmg;
    }

    public double getRange() {
        double extraRange = 0;
        for (GameActions action: getBuffers()) {
            extraRange += action.getExtraRange();
        }
        return range+ extraRange;
    }

    public void addRange(double range) {
        this.range += range;
    }


    public double getRateOfFire() {
        return rateOfFire;
    }

    public void setRateOfFirePerFrame(double framerate) {
        // rate of fire = shoots per second
        this.rateOfFirePerFrame = rateOfFire* framerate/1000;
    }

    public double getRateOfFirePerFrame() {
        return rateOfFirePerFrame;

    }

    public double getDPS() {
        return rateOfFire*(dmg+extraDmg);
    }

    public Color getColor() {
        return color;
    }

    public int getBufferDmg() {
        int DmgFromBuffers = 0;
        for (GameActions action: getBuffers()) {
            DmgFromBuffers += action.getExtraDmg();
        }
        extraDmg = DmgFromBuffers;
        return DmgFromBuffers;
    }

    public int getExtraDmg() {
        return extraDmg;
    }

    public void addExtraDmg(int extraDmg) {
        this.extraDmg += extraDmg;
    }

    // can not shoot more than 1 time per frame per enemy
    public boolean canShootAtThisFrame() {
        rateOfFireCounter += getRateOfFirePerFrame();
        if (rateOfFireCounter >= 0) {
            rateOfFireCounter--;
            return true;
        }
        return false;
    }

    public void recalculateLevelMultiplier(int levels) {
        addDmg(levels);
    }

    public ArrayList<GameActions> getBuffers() {
        return buffers;
    }

    public void addBuffers(GameActions action) {
        if (buffers.contains(action)) {
            // do nothing if already there
        } else {
            this.buffers.add(action);
        }
    }

    public void removeBuffers(GameActions action) {
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
        return rememberOldTarget;
    }

    public void setEnemiesTowerCanShootAtTheSameFrame(int enemiesTowerCanShootAtTheSameFrame) {
        this.enemiesTowerCanShootAtTheSameFrame = enemiesTowerCanShootAtTheSameFrame;
    }

    public void setRememberOldTarget(boolean rememberOldTarget) {
        this.rememberOldTarget = rememberOldTarget;
    }
}