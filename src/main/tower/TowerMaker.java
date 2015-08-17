package main.tower;

import main.tower.nonShootableTower.NonShootableTower;
import main.tower.shootingTowers.ShootableTower;
import main.action.Attack;
import main.action.auraAction.DmgBuffAction;
import main.action.shootingAction.ShootingAction;
import main.board.Board;
import main.board.IDesign.Shapes;
import main.graphics.ColorHandlerSingleton.Colour;
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


    public void makeTower(Board board, char type, Point position) {
        int price, priority, dmg, range, rOF, enemiesTowerCanShootAtTheSameTime;
        Dimension dimension;
        switch(type){
            case 'A':
                price = 10;
                priority = 1;
                dmg = 5;
                range = 100;
                rOF = 1;
                enemiesTowerCanShootAtTheSameTime = 3;
                dimension = new Dimension(1,1);
                if (board.isValidPositions(position, priority, dimension) && board.getPlayer().getGold() >= price) {
                    Colour colour = Colour.ORANGE;
                    Colour colourOfShoots = Colour.BLACK;
                    createTower(board, position, colour, colourOfShoots, dmg, range, rOF,
                            enemiesTowerCanShootAtTheSameTime, price,
                            dimension);
                    board.getPlayer().subtractGold(price);
                } else
                    System.out.println("Not enough gold or invalid position");
                return;

            case 'B':
                price = 20;
                range = 200;
                priority = 1;
                dimension = new Dimension(1,1);

                if (board.isValidPositions(position, priority, dimension) && board.getPlayer().getGold() >= price) {
                    int extraDMG = 5;
                    int extraRange = 0;

                    // TODO fixa här, Gör en gameAction som du skickar in nedan. Sedan kolla upp vad gameActionFactoryn
                    // gör egentligen och om inte EXTRA_DMG och extraRange blir dubbel info då de kmr finnas
                    // i gameAction också.
                    DmgBuffAction dmgBuffAction = new DmgBuffAction(extraDMG);
                    NonShootableTower newTower = new NonShootableTower(board, board.getAllObjects(), dmgBuffAction,
                            position.getX(), position.getY(), dimension, Colour.BLUE, Shapes.RECTANGLE, range , price, extraDMG, extraRange);

                    board.addObject(newTower);
                    board.getPlayer().subtractGold(price);
                } else
                    System.out.println("Not enough gold or invalid position");
                return;


            default :
                System.out.println("Tower not available");

        }
    }

    private void createTower(Board board, main.position.Point position, Colour colourOfTower, Colour colourOfShoots, int dmg,
                             int range, int rOF,
                             int enemiesTowerCanShootAtTheSameTime, int price, Dimension dimension) {

        Attack newAttack = new Attack(dmg, range, rOF, enemiesTowerCanShootAtTheSameTime ,colourOfShoots, board.getFrameRate());
        ShootingAction newShootingAction = new ShootingAction(newAttack);
        ShootableTower newTower = new ShootableTower(board, board.getAllObjects(), newShootingAction, position.getX(),
                                                     position.getY(), dimension, colourOfTower, Shapes.RECTANGLE, price);
        newShootingAction.setTower(newTower); // the gameaction is now attatched to the newTower.

        board.addObject(newTower);
    }

}
