package main.graphics;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-05
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public final class ColorHandlerSingleton
{
    private static ColorHandlerSingleton instance = new ColorHandlerSingleton();

    /**
     * Our our colourscheme.
     */
    public enum Colour {
        /**
         * White
         */
        WHITE,

        /**
         * Gray
         */
        GRAY,

        /**
         * Black
         */
        BLACK,

        /**
         * Yellow
         */
        YELLOW,

        /**
         * Orange
         */
        ORANGE,

        /**
         * Green
         */
        GREEN,

        /**
         * Normal Blue
         */
        DARKBLUE,

        /**
         * Cyan, which means light blue.
         */
        LIGHTBLUE,

        /**
         * Red
         */
        RED,

        /**
         * Blue
         */
        BLUE
    }

    public static ColorHandlerSingleton getInstance() {
        return instance;
    }

    private ColorHandlerSingleton() {
    }

    /**
     * Gives the GUI color
     * TODO make the colors more interesting, no game really wants to have pure green, blue and so on but a certain hue to them.
     * @param colour the Colour object to be converted to an Color object.
     * @return a color that the GUI can use
     */
    public Color getGUIColour(Colour colour) {
        switch (colour) {
            case WHITE: return Color.WHITE;
            case GRAY: return Color.GRAY;
            case BLACK: return Color.BLACK;
            case YELLOW: return Color.YELLOW;
            case ORANGE: return Color.ORANGE;
            case GREEN: return Color.GREEN;
            case DARKBLUE: return Color.BLUE;
            case LIGHTBLUE: return Color.CYAN;
            case RED: return Color.RED;
            case BLUE: return Color.BLUE;
            default: return Color.magenta;
        }
    }
}
