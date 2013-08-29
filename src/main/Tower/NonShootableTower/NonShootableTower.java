package main.Tower.NonShootableTower;

import main.Tower.Tower;
import main.action.GameAction;
import main.action.GameActionFactory;
import main.board.Board;
import main.board.Placeable;
import main.graphics.ColorHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: alete471 Date: 2012-10-07 Time: 19:05
 * Remember to write something useful here!!
 */
public class NonShootableTower extends Tower {
    private int difficulty = 1;
    private final int price = 20;
    private int range = 200;
    private ColorHandler.Colour colourOfTower = ColorHandler.Colour.DARKBLUE;
    private Dimension dimension = new Dimension(1,1);
    private Shapes shape = Shapes.Rectangle;
    private int extraRange = 0;
    private int extraDmg = 0;

    /**
     * For defensive towers
     * @param allObjects
     * @param difficulty
     * @param x
     * @param y
     * @param dimension
     * @param color
     * @param shape
     * @param range
     * @param price
     * @param extraDmg
     * @param extraRange
     */
    public NonShootableTower(Board board, ArrayList<Placeable> allObjects, GameAction gameAction, int difficulty, int x, int y,
                             Dimension dimension, ColorHandler.Colour color, Shapes shape, int range, int price,
                             int extraDmg, double extraRange) {
        super(board, allObjects, gameAction, x, y, dimension, color, shape, price, difficulty);
        this.range = range;
        this.difficulty = difficulty;
        super.addGameActions(new GameActionFactory().createGameAction(extraDmg, extraRange));
    }

}
