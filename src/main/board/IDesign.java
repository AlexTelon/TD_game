package main.board;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:55
 * A interface for the design things to be included for all placeables, not used fully yet
 */
public interface IDesign {

    /**
     * Enum class used to have a form for each object
     * <br></br> OBS not implemented well yet!
     */
    enum Shapes {
        /**
         * A rectangle shape
         */
	RECTANGLE,
        /**
         * A circular shape
         */
        CIRCLE,

        /**
         * A triangular shape
         */
        TRIANGLE
    }

    /**
     * Gives dimensions of the object in terms of the grid.
     */
    Dimension getSize();

     /**
     * This returns the colour of an Placable object
     * @return the color of the object in java form
     */
    Color getGUIColor();

    /**
     * @return the enum shape
     */
    Shapes getShape();

}
