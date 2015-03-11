package main.board;

import main.tower.Tower;
import main.action.GameAction;
import main.graphics.ColorHandler;
import main.graphics.ColorHandler.Colour;
import main.position.*;

import java.awt.*;
import java.util.ArrayList;
import main.position.Point;
import java.util.List;
import java.lang.Iterable;

import static java.lang.StrictMath.round;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:23
 * This is the class which all objects that can be placed on the board will extend.
 */

public class Placeable implements IDesign{
    private Point position;
    private Dimension dimension = new Dimension(1,1);
    private ColorHandler colorHandler = ColorHandler.getInstance();
    private Colour color = Colour.WHITE;
    private Shapes shapes = Shapes.Rectangle;
    private int priority = 1;
    private Point pixelPosition;
    private String nameText = "Placeholder";
    private int hitpoints = 99999;
    private Boolean isImortal = false;
    private List<GameAction> buffers = new ArrayList<GameAction>(); // TODO change to sets?
    private List<GameAction> actions = new ArrayList<GameAction>();
    //   private GameAction gameActions = new GameAction();

    public Placeable(int x, int y, Dimension dimension, Colour color, Shapes shape, int priority) {
        this.position = new Point(x,y);
        this.pixelPosition = position.getPixelPos();
        this.dimension = dimension;
        this.color = color;
        this.shapes = shape;
        this.priority = priority;
    }

    public Placeable(int x, int y, Dimension dimension, Colour color, Shapes shape) {
        this(x, y, dimension, color, shape, 1);
    }


    public Placeable(int x, int y, Dimension dimension, Colour color, Shapes shape, GameAction gameAction) {
        this(x, y, dimension,color,shape,1);
        this.addGameActions(gameAction);
    }

    public Placeable(int x, int y, int hitpoints, Colour color, int priority) {
        this(x, y, new Dimension(1,1), color, Shapes.Rectangle, priority);
        this.hitpoints = hitpoints;
    }

    /**
     * calculates the center of an object
     * <p></p>
     * - maybe this should be added into the datatype later on instead?
     * @return a point to the center of the object
     */
    public Point getCenterOfObject() {
        double width = GlobalPositioning.getXPixel(getDimension().width);
        double height = GlobalPositioning.getYPixel(getDimension().height);
        double xPos = getPixelPosition().getX();
        double yPos = getPixelPosition().getY();
        int midX = (int) round(xPos + width / 2);
        int midY = (int) round(yPos + height / 2);
        return new Point(midX, midY);
    }


    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Placeable)) return false;

        Placeable placeable = (Placeable) o;

        if (hitpoints != placeable.hitpoints) return false;
        if (priority != placeable.priority) return false;
        if (color != placeable.color) return false;
        if (dimension != null ? !dimension.equals(placeable.dimension) : placeable.dimension != null) return false;
        if (nameText != null ? !nameText.equals(placeable.nameText) : placeable.nameText != null) return false;
        if (pixelPosition != null ? !pixelPosition.equals(placeable.pixelPosition) : placeable.pixelPosition != null)
            return false;
        if (position != null ? !position.equals(placeable.position) : placeable.position != null) return false;
        if (shapes != placeable.shapes) return false;

        return true;
    }


    @Override
    public Dimension getSize() {
        return dimension;
    }

    public Color getGUIColor() {
        return colorHandler.getGUIColour(color);
    }

    public Colour getColour() {
        return color;
    }

    @Override
    public Shapes getShape() {
        return shapes;
    }

    /**
     * Calculates the distance between obj a and b.
     * @param a
     * @return a double
     */
    public double distanceTo(Placeable a) {
        Point aPos = a.getPixelPosition();
        Point bPos = this.getPixelPosition();
        int deltaX = aPos.getX()-bPos.getX();
        int deltaY = aPos.getY()-bPos.getY();

        Vector vector = new Vector(deltaX, deltaY);
        return vector.length();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Point getPosition() {
        return position;
    }

    public void subtractHitpoints(int hitpoints) {
        this.hitpoints -= hitpoints;
    }

    public Point getPixelPosition() {
        return pixelPosition;
    }

    public void setPixelPosition(Point pixelPosition) {
        this.pixelPosition = pixelPosition;
    }

    public String getNameText() {
        return nameText;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setColour(Colour color) {
        this.color = color;
    }


    public List<GameAction> getBuffers() {
        return buffers;
    }

    public void addBuffers(GameAction action) {
        if (buffers.contains(action)) {
            // do nothing if already there
        } else {
            this.buffers.add(action);
        }
    }

    public void removeBuffer(GameAction action) {
        this.buffers.remove(action);
    }

    public void addGameActions(GameAction action) {
        if (!actions.contains(action))
        this.actions.add(action); // only adds new actions
    }

    public Iterable<GameAction> getGameActions() {
        return actions;
    }

    /**
     * Calculates if a object is within range of the tower.
     *
     * @param tower@return true if it is in range.
     */
    public boolean isObjectWithinRange(Tower tower) {
        return tower.isObjectWithinRange(tower);
    }

    /**
     * As default placables are NOT imortal, towers for example might be.
     * @return
     */
    public boolean isImortal() {
        return isImortal;
    }

    public void setImortality(Boolean imortal) {
        isImortal = imortal;
    }

    public void removeGameAction(GameAction action) {
        this.actions.remove(action);
    }
}

