package main.enemy.enemyType;

import main.board.Board;
import main.enemy.Enemy;
import main.graphics.ColorHandlerSingleton.Colour;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-06
 * Time: 07:39
 * To change this template use File | Settings | File Templates.
 */
public class DefaultEnemy extends Enemy {
    private static final int EXPERIENCE_FOR_TOWERS = 5;
    private static final int DMG_TO_BASE = 1;
    private static final int GOLD = 5;
    private static final int HIT_POINTS = 10;
    private static final int PIXEL_SPEED = 8;
    private static final Colour COLOUR = Colour.LIGHTBLUE;


    /**
     * The only constructor for this class, this one requires no parameters for setting values for the enemy other
     * than position and such. (besides difficulty). <p></p>
     * experienceForTower =5 <br></br>
     * DMG_TO_BASE = 1 <br></br>
     * GOLD = 5 <br></br>
     * HIT_POINTS = 10
     * PIXEL_SPEED = 1 <br></br>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     */
    public DefaultEnemy(Board board, int x, int y) {
        super(board, x, y, COLOUR, EXPERIENCE_FOR_TOWERS, PIXEL_SPEED, DMG_TO_BASE, GOLD, HIT_POINTS);
    }

}
