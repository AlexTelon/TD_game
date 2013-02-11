package main.board;

/**
 * User: alete471 Date: 2012-10-28 Time: 15:57
 * A class for converting grid positions to pixel positions
 */
public class GlobalPositioning {
    /**
     * getXPixel returns the pixelPosition from a grid position
     * @param x
     * @return a pixel position int
     */
    //Calculates the position of the top left corner of each box from its position in the array
    public static int getXPixel(int x) {
        return x*Board.getSquareWidth();
    }


    /**
     * getXPixel returns the pixelPosition from a grid position
     * @param y
     * @return a pixel position int
     */
    public static int getYPixel(int y) {
        return y*Board.getSquareHeight();
    }

}
