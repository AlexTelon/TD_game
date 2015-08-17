package main.graphics;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-05
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class ColorHandler {
    private static ColorHandler ourInstance = new ColorHandler();

    public enum Colour {
        WHITE, GRAY, BLACK, YELLOW, ORANGE, GREEN, PURPLE, DARKBLUE, LIGHTBLUE, RED, BLUE
    }

    public static ColorHandler getInstance() {
        return ourInstance;
    }

    private ColorHandler() {
    }

    /**
     * Gives the GUI color
     *
     * LATER ON: this should maybe use something else instead of a switch case.
     * @param colour
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
            case PURPLE: return Color.PINK;
            case DARKBLUE: return Color.BLUE;
            case LIGHTBLUE: return Color.CYAN;
            case RED: return Color.RED;
            case BLUE: return Color.BLUE;
            default: return Color.magenta;
        }
    }
}
