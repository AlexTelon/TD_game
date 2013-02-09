package main.action;

import main.action.auraActions.DmgBuffAction;
import main.action.auraActions.RangeBuffAction;

/**
 * User: alete471 Date: 2012-10-18 Time: 15:25
 * Factory for AuraActions.
 */
public class GameActionFactory implements IGameActionFactory {

    @Override
    public GameActions createGameAction(int extraDmg, double extraRange) {
if (extraDmg != 0 && extraRange != 0) {
    return new GameActions(extraDmg, extraRange);

}
        if (extraDmg != 0) {

           DmgBuffAction dmgBuffAction = new DmgBuffAction(extraDmg);
           return dmgBuffAction;

        }
        if (extraRange != 0) {
            RangeBuffAction rangeBuffAction = new RangeBuffAction(extraRange);
            return rangeBuffAction;

        }
        return null; // TODO this could cause error, change to "return new GameActions(extraDmg, extraRange);" ?
    }

}
