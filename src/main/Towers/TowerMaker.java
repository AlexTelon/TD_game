package main.Towers;

import main.Towers.AuraTowers.DefensiveAuraTower;
import main.Towers.shootingTowers.ShootableTowers;
import main.action.Attack;
import main.action.GameActions;
import main.board.Board;
import main.board.IDesign;
import main.board.Placeable;
import main.graphics.ColorHandler;
import main.position.Point;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-03
 * Time: 17:17
 * This is the class that makes the towers that the user "ordered". It checks if the tower can be placed where the
 * user wants it to be placed and checks if the user has enough gold. If that is the case the proper tower is created.
 */
public class TowerMaker {


    public void makeTower(Board board, Character type, Point position) {
        switch(type){
            case 'A':
                int price = 10;
                int priority = 1;
                int dmg = 5;
                int range = 100;
                int rOF = 1;
                int enemiesTowerCanShootAtTheSameTime = 1;
                ColorHandler.Colour colour = ColorHandler.Colour.ORANGE;
                ColorHandler.Colour colourOfShoots = ColorHandler.Colour.BLACK;
                Dimension dimension = new Dimension(1,1);
                if (board.isValidPositions(position, priority, dimension) && board.getGold() >= price) {
                    createTower(board, position, colour, colourOfShoots, dmg, range, rOF,
                            enemiesTowerCanShootAtTheSameTime, price,
                            dimension);
                    board.subtractGold(price);
                } else
                    System.out.println("Not enough gold or invalid position");
                return;

            case 'B':
                price = 20;
                priority = 1;
                dimension = new Dimension(1,1);

                if (board.isValidPositions(position, priority, dimension) && board.getGold() >= price) {
                    board.addObject(new DefensiveAuraTower(board.getAllObjects(), board.getDifficulty(),
                            position.getX(), position.getY(), 5));

                    board.subtractGold(price);
                } else
                    System.out.println("Not enough gold or invalid position");
                return;


            default :
                System.out.println("Tower not available");

        }
    }

    private void createTower(Board board, main.position.Point position, ColorHandler.Colour colourOfTower, ColorHandler.Colour colourOfShoots, int dmg,
                             int range, int rOF,
                             int enemiesTowerCanShootAtTheSameTime, int price, Dimension dimension) {

        Attack newAttack = new Attack(dmg, range, rOF, enemiesTowerCanShootAtTheSameTime ,colourOfShoots, board.getFrameRate());
        GameActions newGameAction = new GameActions(newAttack); // made a lonley gameaction
        Placeable newTower = new Towers(board.getAllObjects(), newGameAction, position.getX(), position.getY(),
                dimension, colourOfTower, IDesign.Shapes.Rectangle, price, board.getDifficulty());
        newGameAction.setTower(newTower); // the gameaction is now attatched to the newTower.

        board.addObject(newTower);
    }

}
