package main.enemy.enemyType;

import main.board.Board;
import main.enemy.Enemy;
import main.graphics.ColorHandler.Colour;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-06
 * Time: 07:39
 * To change this template use File | Settings | File Templates.
 */
public class DefaultEnemy extends Enemy {
    private static final int experienceForTowers = 5;
    private static final int dmgToBase = 1;
    private static final int gold = 5;
    private static final int hitPoints = 10;
    private static final int pixelSpeed = 8;
    private static final Colour colour = Colour.LIGHTBLUE;


    /**
     * The only constructor for this class, this one requires no parameters for setting values for the enemy other
     * than position and such. (besides difficulty). <p></p>
     * experienceForTower =5 <br></br>
     * dmgToBase = 1 <br></br>
     * gold = 5 <br></br>
     * hitPoints = 10
     * pixelSpeed = 1 <br></br>
     * @param board the board (/map) that the enemy will be placed on
     * @param x starting position of the enemy
     * @param y starting position of the enemy
     */
    public DefaultEnemy(Board board, int x, int y) {
        super(board, x, y, colour, experienceForTowers, pixelSpeed,
                dmgToBase, gold, hitPoints);
    }

}
