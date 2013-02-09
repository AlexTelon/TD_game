package main.tower.auraTower;

import main.action.GameActionFactory;
import main.action.GameActions;
import main.board.Placeable;
import main.tower.Tower;
import main.graphics.ColorHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: alete471 Date: 2012-10-07 Time: 19:05
 * Remember to write something useful here!!
 */
public class NonShootableTower extends Tower {
    private int range = 300;
    private int difficulty = 1;
    private STATE state = STATE.NonShootable;

    /**
     * For defensive towers
     * @param allObjects
     * @param difficulty
     * @param x
     * @param y
     * @param dimension
     * @param color
     * @param shape
     * @param range
     * @param price
     * @param extraDmg
     * @param extraRange
     */
    public NonShootableTower(ArrayList<Placeable> allObjects, int difficulty, int x, int y,
                             Dimension dimension, ColorHandler.Colour color, Shapes shape, int range, int price,
                             int extraDmg, double extraRange) {
        super(allObjects, STATE.NonShootable, x, y, dimension, color, shape, price, difficulty);
        this.range = range;
        this.difficulty = difficulty;
        super.addGameActions(new GameActionFactory().createGameAction(extraDmg, extraRange));

        /* KODEN HÄR FINNS KVAR FÖR ATT VISA HUR MAN SKULLE GÖRA OM MAN INTE HADE EN Factory! // TODO
        if (extraDmg != 0) {

            DmgBuffAction dmgBuffAction = new DmgBuffAction(extraDmg);
            super.addGameActions(dmgBuffAction);

        }
        if (extraRange != 0) {
            RangeBuffAction rangeBuffAction = new RangeBuffAction(extraRange);
            super.addGameActions(rangeBuffAction);

        }
        */
    }

    /**
     * for Offensive towers
     * @param allObjects
     * @param difficulty
     * @param x
     * @param y
     * @param dimension
     * @param colourOfTower
     * @param shape
     * @param range
     * @param price
     */
    public NonShootableTower(ArrayList<Placeable> allObjects, int difficulty, int x, int y, Dimension dimension, ColorHandler.Colour colourOfTower, Shapes shape, int range, int price) {
        super(allObjects, STATE.NonShootable, x, y, dimension, colourOfTower, shape, price, difficulty);
        this.range = range;
        this.difficulty = difficulty;
    }

    public void tick() {

        findObjectsWithinRange();

        //send action to all objects
        for (Placeable obj : super.getCurrentTargets()) {
            for (GameActions currentAction : super.getGameActions()) {
                currentAction.tick(obj);
            }
        }
    }

    /**
     * Fins all placebles within range and adds them to current targets
     */
    private void findObjectsWithinRange() {
        for( Placeable obj : super.getAllObjects()) {
            if (isObjectWithinRange(obj)) {
                if (obj != this) {
                    super.addToCurrentTargets(obj);
                }
            }
        }
    }

    private boolean isObjectWithinRange(Placeable obj) {
        if (obj.distanceTo(this) <= range) {
            return true;
        }
        return false;
    }

    public int getExtraDmg() {
        int extraDmg = 0;
        for (GameActions currentAction : super.getGameActions()) {
            extraDmg += currentAction.getExtraDmg();

        }
        return extraDmg;
    }

    public double getExtraRange() {
        double extraRange = 0;
        for(GameActions currentAction : super.getGameActions()) {
            extraRange += currentAction.getExtraRange();
        }
        return extraRange;
    }

}
