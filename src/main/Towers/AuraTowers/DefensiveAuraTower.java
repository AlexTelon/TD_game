package main.Towers.AuraTowers;

import main.board.Placeable;
import main.graphics.ColorHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: alete471 Date: 2012-10-07 Time: 18:35
 * Remember to write something useful here!!
 */
public class DefensiveAuraTower extends NonShootableTower {
    private static final int price = 20;
    private static final int range = 200;
    private static final ColorHandler.Colour colourOfTower = ColorHandler.Colour.DARKBLUE;
    private static final Dimension dimension = new Dimension(1,1);
    private static final Shapes shape = Shapes.Rectangle;
    private static final int extraRange = 0;
    private static final int extraDmg = 0;

    /**
     * Creates a default offensive aura-tower tower.
     * Stats:<br></br>
     * price = 20 <br></br>
     * range = 200 <br></br>
     * Color of tower = darkblue <br></br>
     * Dimension(1,1)<br></br>
     * <b>STUFF BELOW IS WRONG</b>
     * @param allObjects all objects of type placable in board
     * @param difficulty higher integers mean more difficult
     * @param x position of the tower in x
     * @param y position of the tower in x
     */
    public DefensiveAuraTower(ArrayList<Placeable> allObjects, int difficulty, int x, int y,
                              double extraRange) {
        super(allObjects, difficulty, x, y, dimension, colourOfTower, shape, range, price,
                extraDmg, extraRange);
    }

    /**
     * Creates a default offensive aura-tower tower.
     * Stats:<br></br>
     * price = 20 <br></br>
     * range = 200 <br></br>
     * Color of tower = darkblue <br></br>
     * Dimension(1,1)<br></br>
     * @param allObjects all objects of type placable in board
     * @param difficulty
     * @param x
     * @param y
     * @param extraDmg
     */
    public DefensiveAuraTower(ArrayList<Placeable> allObjects, int difficulty, int x, int y,
                              int extraDmg) {
        super(allObjects, difficulty, x, y, dimension, colourOfTower, shape, range, price,
                extraDmg, extraRange );
    }
}
