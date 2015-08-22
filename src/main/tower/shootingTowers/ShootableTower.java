package main.tower.shootingtowers;

import java.awt.*;
import java.util.Collection;

import main.action.GameAction;
import main.action.shootingaction.ShootingAction;
import main.board.Board;
import main.enemy.EnemyWave;
import main.board.Placeable;
import main.tower.Tower;
import main.graphics.ColorHandlerSingleton.Colour;

/**
 * Towers that shoot.
 */
public class ShootableTower extends Tower {

    public ShootableTower(Board board, Collection<Placeable> allObjects, ShootingAction newShootingAction, int x, int y, Dimension dimension, Colour colourOfTower, Shapes rectangle, int price, int range) {
        super(board,allObjects,newShootingAction,x,y,dimension,colourOfTower,rectangle,price, range);
    }


    public void tick(EnemyWave allEnemies) { // TODO Move to shootingaction so that we have only data in this class(?)

        //send action to all objects
        for (Placeable obj : getPlacablesWithinRangeOfThisTower()) {
            if (!obj.equals(this)) {
                for (GameAction currentAction : getGameActions()) {
                    if (currentAction.hasAnAttack()) {
                        obj.addGameActions(currentAction);
                    }
                }
            }
        }
        super.tick(allEnemies);
    }



    @Override // Not calling superclass is intended, but not optimal. All buffers should in
    // my current oppinion be the same buffer Collection. Right now there are some actions that are both added to the AttackData
    // class and the placable itself which is weird. Better have them all in one place or?
    public void addBuffers(GameAction action) {
        if (!getBuffers().contains(action)) {
            getBuffers().add(action);
            for (GameAction currentGameAction : getGameActions()) {
                if (currentGameAction.hasAnAttack())
                    currentGameAction.getAttackData().addBuffers(action);
            }
        }
    }

    @Override
        public void removeBuffer(GameAction action) {
        getBuffers().remove(action);
        for (GameAction currentGameAction : getGameActions()) {
            if (currentGameAction.hasAnAttack())
                currentGameAction.getAttackData().removeBuffers(action);
        }
    }

}