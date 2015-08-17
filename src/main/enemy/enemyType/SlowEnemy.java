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
public class SlowEnemy extends Enemy {
    private static final int EXPERIENCE_FOR_TOWERS = 5;
    private static final int DMG_TO_BASE = 1;
    private static final int GOLD = 5;
    private static final int HIT_POINTS = 20;
    private static final int PIXEL_SPEED = 1;
    private static final Colour COLOUR = Colour.YELLOW;

    /**
     * The first constructor for this class, this one requires all parameters to be set manually. <b>Another class
     * should be considered if the PIXEL_SPEED is not rather slow.</b>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     * @param colour color of the enemy
     * @param experienceToTowers exp towers gain for a kill of this enemy
     * @param pixelSpeed movement per tick (/frame) in pixels. <b>Should be relatively slow</b>
     * @param dmgToBase damage done to base (/castle) once it reaches it
     * @param gold GOLD towers gain for a kill
     * @param hitPoints Hitpoints of the enemy
     * @see SlowEnemy#SlowEnemy(Board, int, int)
     */
    public SlowEnemy(Board board, int x, int y, Colour colour, int experienceToTowers, int pixelSpeed,
                     int dmgToBase, int gold, int hitPoints) {
        super(board, x, y, colour, experienceToTowers, pixelSpeed, dmgToBase, gold, hitPoints);
    }

    /**
     * Creates a default slow enemy <br></br>
     * Stats: <br></br>
     * EXPERIENCE_FOR_TOWERS 5 <br></br>
     * DMG_TO_BASE = 1 <br></br>
     * GOLD = 5 <br></br>
     * HIT_POINTS = 20  <br></br>
     * PIXEL_SPEED = 1 <br></br>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     * @see SlowEnemy#SlowEnemy(Board, int, int, Colour, int, int, int, int, int)
     *
     */
    public SlowEnemy(Board board, int x, int y) {
        super(board, x, y, COLOUR, EXPERIENCE_FOR_TOWERS, PIXEL_SPEED, DMG_TO_BASE, GOLD, HIT_POINTS);
    }

}
