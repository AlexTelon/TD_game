package main.tower.shootingTowers;

import java.awt.*;
import java.util.ArrayList;

import main.action.GameAction;
import main.action.shootingAction.ShootingAction;
import main.board.Board;
import main.board.IDesign;
import main.enemy.EnemyWave;
import main.board.Placeable;
import main.tower.Tower;
import main.graphics.ColorHandler;

import static java.lang.Math.abs;

public class ShootableTower extends Tower {


    public ShootableTower(Board board, ArrayList<Placeable> allObjects, GameAction gameAction, int difficulty, double framerate, int x, int y,
                          Dimension dimension, ColorHandler.Colour colourOfTower, ColorHandler.Colour colourOfShoots,
                          IDesign.Shapes shape, int dmg, int range, int rOF, int enemiesTowerCanShootAtTheSameFrame,
                          int price) {

        super(board, allObjects, gameAction, x, y, dimension, colourOfTower, shape, price, difficulty);

    }

    public ShootableTower(Board board, ArrayList<Placeable> allObjects, ShootingAction newShootingAction, int x, int y, Dimension dimension, ColorHandler.Colour colourOfTower, Shapes rectangle, int price, int difficulty) {
        super(board,allObjects,newShootingAction,x,y,dimension,colourOfTower,rectangle,price,difficulty);
    }


    public void tick(EnemyWave allEnemies) { // TODO Move to shootingAction so that we have only data in this class(?)

        //send action to all objects
        for (Placeable obj : super.getPlacablesWithinRangeOfThisTower()) {
            if (obj != this) {
                for (GameAction currentAction : super.getGameActions()) {
                    if (currentAction.hasAnAttack()) {
                        obj.addGameActions(currentAction);
                    }
                }
            }
        }
        super.tick(allEnemies);
    }


    @Override
    public void addBuffers(GameAction action) {
        if (super.getBuffers().contains(action)) {
            // do nothing if already there
        } else {
            super.getBuffers().add(action);
            for (GameAction currentGameAction : getGameActions()) {
                if (currentGameAction.hasAnAttack())
                    currentGameAction.getAttack().addBuffers(action);
            }
        }
    }

    @Override
        public void removeBuffer(GameAction action) {
            super.getBuffers().remove(action);
        for (GameAction currentGameAction : getGameActions()) {
            if (currentGameAction.hasAnAttack())
                currentGameAction.getAttack().removeBuffers(action);
        }
    }

}