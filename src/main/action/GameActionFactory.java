package main.action;

import main.action.auraaction.DmgBuffAction;
import main.action.auraaction.RangeBuffAction;

/**
 * User: alete471 Date: 2012-10-18 Time: 15:25
 * Factory for AuraActions.
 */
public class GameActionFactory implements IGameActionFactory {

    @Override
    public GameAction createGameAction(int extraDmg, double extraRange) {
        if (extraDmg != 0 && extraRange != 0) {
            return new GameAction(extraDmg, extraRange);
        }
        if (extraDmg != 0) {
            return new DmgBuffAction(extraDmg);
        }
        if (extraRange != 0) {
            return new RangeBuffAction(extraRange);
        }
        return null; // TODO this could cause errors, change to "return new GameAction(extraDmg, extraRange);" ?
    }

}
