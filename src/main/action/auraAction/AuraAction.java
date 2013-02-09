package main.action.auraAction;

import com.sun.xml.internal.bind.v2.TODO;
import main.action.GameActions;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * Remember to write something useful here!!
 */
public class AuraAction extends GameActions {

    public AuraAction(int extraDmg, double extraRange) {
        super(extraDmg, extraRange);
    }

    public void tick() {
    }


/*
    public void tick(GameActions currentAction, Placeable obj) {
        if (currentAction instanceof DmgBuffAction) {
            ((DmgBuffAction) currentAction).tick(obj);
        } else if (currentAction instanceof RangeBuffAction) {
            ((RangeBuffAction) currentAction).tick(obj);
        }
    }
    */
}


