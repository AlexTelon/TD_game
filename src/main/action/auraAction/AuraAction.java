package main.action.auraaction;

import main.action.GameAction;
import main.board.Placeable;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * Remember to write something useful here!!
 */
public class AuraAction extends GameAction {

    public AuraAction(int extraDmg, double extraRange) {
        super(extraDmg, extraRange);
    }

    public void tick(Placeable obj) {
        obj.addBuffers(this);
    }

/*
    public void tick(GameAction currentAction, Placeable obj) {
        if (currentAction instanceof DmgBuffAction) {
            ((DmgBuffAction) currentAction).tick(obj);
        } else if (currentAction instanceof RangeBuffAction) {
            ((RangeBuffAction) currentAction).tick(obj);
        }
    }
    */
}


