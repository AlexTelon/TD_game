package main.enemy.enemyType;

import main.board.Board;
import main.enemy.Enemy;
import main.graphics.ColorHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-06
 * Time: 07:39
 * To change this template use File | Settings | File Templates.
 */
public class StrongEnemy extends Enemy {
    private static final int experienceForTowers = 20;
    private static final int dmgToBase = 2;
    private static final int gold = 20;
    private static final int hitPoints = 100;
    private static final int pixelSpeed = 1;
    private static final ColorHandler.Colour colour = ColorHandler.Colour.GREEN;

    /**
     * The first constructor for this class, this one requires all parameters to be set manually. <b>Another class
     * should be considered if the hitPoints is not rather high.</b>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     * @param colour color of the enemy
     * @param experienceToTowers exp towers gain for a kill of this enemy
     * @param pixelSpeed movement per tick (/frame) in pixels.
     * @param dmgToBase damage done to base (/castle) once it reaches it
     * @param gold gold towers gain for a kill
     * @param hitPoints Hitpoints of the enemy  <b>Should be relatively high</b>
     * @see StrongEnemy#StrongEnemy(main.board.Board, int, int)
     */
    public StrongEnemy(Board board, int x, int y, ColorHandler.Colour colour, int experienceToTowers, int pixelSpeed,
                       int dmgToBase, int gold, int hitPoints) {
        super(board, x, y, colour, experienceToTowers, pixelSpeed, dmgToBase, gold, hitPoints);
    }

    /**
     * Creates a default strong enemy <br></br>
     * Stats: <p></p>
     * experienceForTowers 5 <br></br>
     * dmgToBase = 1 <br></br>
     * gold = 5 <br></br>
     * hitPoints = 40  <br></br>
     * pixelSpeed = 1 <br></br>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     * @see StrongEnemy#StrongEnemy(main.board.Board, int, int, main.graphics.ColorHandler.Colour, int, int, int, int, int)
     *
     */
    public StrongEnemy(Board board, int x, int y) {
        super(board, x, y, colour, experienceForTowers, pixelSpeed,
                dmgToBase, gold, hitPoints);
    }

}
