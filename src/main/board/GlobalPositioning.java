package main.board;

/**
 * User: alete471 Date: 2012-10-28 Time: 15:57
 * A class for converting grid positions to pixel positions
 */
public final class GlobalPositioning {

    private GlobalPositioning() {
       }

    /**
     * getXPixel returns the pixelPosition from a grid position
     * @param x X-pos in the grid
     * @return a pixel position int
     */
    //Calculates the position of the top left corner of each box from its position in the array
    public static int getXPixel(int x) {
        return x*Board.getSquareWidth();
    }


    /**
     * getXPixel returns the pixelPosition from a grid position
     * @param y Y-pos in the grid
     * @return a pixel position int
     */
    public static int getYPixel(int y) {
        return y*Board.getSquareHeight();
    }

}
