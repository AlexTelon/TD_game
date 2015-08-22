package main.tower.nonshootabletower;

import main.tower.Tower;
import main.action.GameAction;
import main.action.GameActionFactory;
import main.board.Board;
import main.board.Placeable;
import main.enemy.EnemyWave;
import main.graphics.ColorHandlerSingleton.Colour;

import java.awt.*;
import java.util.Collection;

/**
 * User: alete471 Date: 2012-10-07 Time: 19:05
 * Remember to write something useful here!!
 */
public class NonShootableTower extends Tower {

    /**
     * Create an aura tower.
     */
    public NonShootableTower(Board board, Collection<Placeable> allObjects, GameAction gameAction,
                             int x, int y, Dimension dimension, Colour color, Shapes shape, int price,
                             int extraDmg, double extraRange, int range) {
        super(board, allObjects, gameAction, x, y, dimension, color, shape, price, range);
        super.addGameActions(new GameActionFactory().createGameAction(extraDmg, extraRange));
    }

    @Override
    public void tick(EnemyWave allEnemies) {
        super.tick(allEnemies);

        //send action to all objects
        for (Placeable obj : getPlacablesWithinRangeOfThisTower()) {
            if (!obj.equals(this)) {
                for (GameAction currentAction : getGameActions()) {
                    if (!currentAction.hasAnAttack()) {
                        obj.addGameActions(currentAction);
                    }
                }
            }
        }
    }

    @Override
    public void addBuffers(GameAction action) {
        if (!action.hasAnAttack()) { // do not add attacking actions to the tower
            super.addBuffers(action);
        }
    }


    /**
     * Non shootable towers can have no actions.
     */
    @SuppressWarnings("RefusedBequest") @Override // Not calling the method in the superclass is intended.
    public void addGameActions(GameAction action) {
    }
}
