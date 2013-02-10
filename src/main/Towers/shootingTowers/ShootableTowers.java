package main.Towers.shootingTowers;

import java.awt.*;
import java.util.ArrayList;

import main.action.Attack;
import main.action.GameActions;
import main.board.IDesign;
import main.enemies.EnemyWave;
import main.board.Placeable;
import main.Towers.Towers;
import main.action.shootingAction.ShootingAction;
import main.enemies.Enemies;
import main.graphics.ColorHandler;

import static java.lang.Math.abs;

public class ShootableTowers extends Towers {


    public ShootableTowers(ArrayList<Placeable> allObjects, GameActions gameActions, int difficulty, double framerate, int x, int y,
                           Dimension dimension, ColorHandler.Colour colourOfTower, ColorHandler.Colour colourOfShoots,
                           IDesign.Shapes shape, int dmg, int range, int rOF, int enemiesTowerCanShootAtTheSameFrame,
                           int price) {

        super(allObjects, gameActions, x, y, dimension, colourOfTower, shape, price, difficulty);

    }

    /**
     * Tick for towers is like all other tick in charge of gamemechanics. It checks if the tower can shoot,
     * if it can then which enemies it can shoot and then informs the enemy that it has been shoot.
     */
    public void tick(EnemyWave allEnemies) { // TODO Move to shootingAction so that we have only data in this class!

        }


    @Override
    public void addBuffers(GameActions action) {
        if (super.getBuffers().contains(action)) {
            // do nothing if already there
        } else {
            super.getBuffers().add(action);
            for (GameActions currentGameAction : getGameActions()) {
                if (currentGameAction.hasAnAttack())
                    currentGameAction.getAttack().addBuffers(action);
            }
        }
    }

    @Override
        public void removeBuffer(GameActions action) {
            super.getBuffers().remove(action);
        for (GameActions currentGameAction : getGameActions()) {
            if (currentGameAction.hasAnAttack())
                currentGameAction.getAttack().removeBuffers(action);
        }
    }
}