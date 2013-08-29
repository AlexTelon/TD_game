package main.Tower.shootingTowers;

import java.awt.*;
import java.util.ArrayList;

import main.action.GameAction;
import main.board.Board;
import main.board.IDesign;
import main.enemies.EnemyWave;
import main.board.Placeable;
import main.Tower.Tower;
import main.graphics.ColorHandler;

import static java.lang.Math.abs;

public class ShootableTower extends Tower {


    public ShootableTower(Board board, ArrayList<Placeable> allObjects, GameAction gameAction, int difficulty, double framerate, int x, int y,
                          Dimension dimension, ColorHandler.Colour colourOfTower, ColorHandler.Colour colourOfShoots,
                          IDesign.Shapes shape, int dmg, int range, int rOF, int enemiesTowerCanShootAtTheSameFrame,
                          int price) {

        super(board, allObjects, gameAction, x, y, dimension, colourOfTower, shape, price, difficulty);

    }

    /**
     * Tick for towers is like all other tick in charge of gamemechanics. It checks if the tower can shoot,
     * if it can then which enemies it can shoot and then informs the enemy that it has been shoot.
     */
    public void tick(EnemyWave allEnemies) { // TODO Move to shootingAction so that we have only data in this class!

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