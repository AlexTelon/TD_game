package main.enemies.enemyTypes;

import main.board.Board;
import main.enemies.Enemies;
import main.graphics.ColorHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-06
 * Time: 07:39
 * To change this template use File | Settings | File Templates.
 */
public class FastEnemy extends Enemies {
    private static final int experienceForTowers = 5;
    private static final int dmgToBase = 1;
    private static final int gold = 5;
    private static final int hitPoints = 10;
    private static final int pixelSpeed = 4;
    private static final ColorHandler.Colour colour = ColorHandler.Colour.LIGHTBLUE;


    /**
     * The first constructor for this class, this one requires all parameters to be set manually. <b>Another class
     * should be considered if the pixelSpeed is not rather high.</b>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     * @param colour color of the enemy
     * @param experienceToTowers exp towers gain for a kill of this enemy
     * @param pixelSpeed movement per tick (/frame) in pixels. <b>Should be relatively high</b>
     * @param dmgToBase damage done to base (/castle) once it reaches it
     * @param gold gold towers gain for a kill
     * @param hitPoints Hitpoints of the enemy
     * @see FastEnemy#FastEnemy(main.board.Board, int, int)
     */
    public FastEnemy(Board board, int x, int y, ColorHandler.Colour colour, int experienceToTowers, int pixelSpeed,
                     int dmgToBase, int gold, int hitPoints) {
        super(board, x, y, colour, experienceToTowers, pixelSpeed, dmgToBase,
                gold, hitPoints);
    }

    /**
     * Creates a default fast enemy. <br></br>
     * Stats: <br></br>
     * experienceForTowers = 5 <br></br>
     * dmgToBase = 1 <br></br>
     * gold = 5 <br></br>
     * hitPoints = 10
     * pixelSpeed = 4 <br></br>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     * @see FastEnemy#FastEnemy(main.board.Board, int, int, main.graphics.ColorHandler.Colour, int, int, int, int, int)
     *
     */
    public FastEnemy(Board board, int x, int y) {
        super(board, x, y, colour, experienceForTowers, pixelSpeed,
                dmgToBase, gold, hitPoints);
    }

}
