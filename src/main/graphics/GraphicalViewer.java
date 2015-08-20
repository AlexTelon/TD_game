package main.graphics;

import main.tower.Tower;
import main.board.*;
import main.enemy.Enemy;
import main.position.Point;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-27
 * Time: 18:09
 * The main component of GameFrame, this is what paints the game itself.
 */
@SuppressWarnings("ReuseOfLocalVariable")
/* This and all other cases of "Reuse of local variable" is OK for temporary variables in my mind. Several individual
variables with good naming would be better thouogh, but still I think this is ok for placing stuff in a GUI like this*/
public class GraphicalViewer extends JComponent implements IBoardListener {
    private Board board;
    private Placeable higlightedObj = null;
    private Point higlightedPoint = null;
    private boolean higlightisObj = false;
    private boolean nothingIsHighlighted = true;


    public GraphicalViewer( Board board) {
        this.board = board;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth(), preferredHeight());
    }

    private int preferredHeight() {
        return Board.getSquareHeight() * Board.getHeight();
    }
    private int preferredWidth() {
        return Board.getSquareWidth() * Board.getWidth();
    }

    /**
     * Paints the whole board
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int y = 0; y < Board.getHeight(); ++y ) {
            for (int x = 0; x < Board.getWidth(); ++x) {
                g2.setColor(board.getBackgroundColor());
                g2.fill(new Rectangle(GlobalPositioning.getXPixel(x), GlobalPositioning.getYPixel(y),
                        Board.getSquareWidth(), Board.getSquareHeight()));

            }
        }

        paintEnemyPath(g2, board.getEnemyWaves().getEnemyPath());
        Placeable obj = board.getCastle();
        paintPlaceable(obj, g2);

        paintAllTowers(g2);
        paintAllActiveEnemies(g2);
        paintAllShoots(g2);
        paintInfo(g2);

        if (nothingIsHighlighted) return;

        if (higlightisObj) {
            fillInHighlightedObj(g2);
        } else {
            fillInHighlightedPoint(g2);
        }
        paintInfo(g2);

    }

    /**
     * This method uses the precalculated path in EnemyPath. EnemyPath contains all the gridCoordinates that the
     * enemies will visit and one by one they are painted. This way the path is only calculated once and after that
     * its only a matter of getting it from a list.
     * @param g2
     */
    private void paintEnemyPath(Graphics2D g2, Iterable<Point> enemyPath) {

        g2.setColor(Color.gray);
        for (Point currentGridPoint : enemyPath) {
            g2.fill(new Rectangle(GlobalPositioning.getXPixel(currentGridPoint.getX()), GlobalPositioning.getYPixel(currentGridPoint.getY()),
                    Board.getSquareWidth(), Board.getSquareHeight()));
        }
    }

    /**
     * Draws a red rectangle around the current obj
     * @param g2
     */
    private void fillInHighlightedObj(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawRect(higlightedObj.getPixelPosition().getX(), higlightedObj.getPixelPosition().getY(),
                GlobalPositioning.getXPixel(higlightedObj.getDimension().width), GlobalPositioning.getYPixel(higlightedObj.getDimension().height));

    }

    /**
     * Draws a red rectangle around the current position
     * @param g2
     */
    private void fillInHighlightedPoint(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawRect(higlightedPoint.getPixelPos().getX(), higlightedPoint.getPixelPos().getY(),
                GlobalPositioning.getXPixel(1), GlobalPositioning.getYPixel(1));
    }

    /**
     * paintInfo is only for debugging, it gives information about all kinds of things usefull while
     * working with the code.
     * @param  g2  a graphics2D object
     */
    private void paintInfo(Graphics2D g2) {
        final int MARGIN = 10;
        final int STANDARD_SPACING = 30;

        int tmpX = GlobalPositioning.getXPixel(1);
        g2.setColor(Color.BLACK);
        g2.drawString("HP", 0, MARGIN);
        for ( Enemy currentEnemy :  board.getAllEnemiesInCurrentWave()) {
            g2.drawString(currentEnemy.getHealthText(), tmpX, MARGIN);
            tmpX += STANDARD_SPACING;
        }
        tmpX = STANDARD_SPACING;
        g2.drawString("Alive", 0, STANDARD_SPACING);
        for ( Enemy currentEnemy : board.getAllEnemiesInCurrentWave()) {
            if (currentEnemy.isAlive()) {
                g2.drawString("A", tmpX, STANDARD_SPACING);
            } else {
                g2.drawString("D", tmpX, STANDARD_SPACING);
            }
            tmpX += STANDARD_SPACING;
        }
        tmpX = STANDARD_SPACING;
        g2.drawString("Active", 0, STANDARD_SPACING *2);
        for ( Enemy currentEnemy : board.getAllEnemiesInCurrentWave()) {
            int localtime =((int) currentEnemy.getActivationTime() - (int) board.getCurrentTime())/1000;
            if (localtime < 0) localtime = 0;
            g2.drawString(stringConverter(localtime), tmpX, STANDARD_SPACING *2);
            tmpX += STANDARD_SPACING;
        }
    }

    /**
     * Paints all shoots from ALL towers to ALL of their current targets.
     * @param g2
     */
    private void paintAllShoots(Graphics2D g2) {
        for (Tower currentTower : board.getAllTowers()) {
            if (currentTower.hasTarget()) {
                for (Placeable currentObj : currentTower.getPlacablesWithinRangeOfThisTower()) {
                    if (currentTower.canShoot(currentObj)) {
                        g2.setColor(currentTower.getAttackColor());
                        g2.drawLine(currentTower.getCenterOfObject().getX(), currentTower.getCenterOfObject().getY(),
                                currentObj.getCenterOfObject().getX(), currentObj.getCenterOfObject().getY());
                    }
                }
            }
        }
    }
    /**
     * Paints all towers
     * @param g2
     *
     */
    private void paintAllTowers(Graphics2D g2) {
        for ( Tower currentTower : board.getAllTowers()) {
            paintPlaceable(currentTower, g2);
        }
    }

    /**
     * Paints all towers active enemies.
     * @param g2
     */
    private void paintAllActiveEnemies(Graphics2D g2) {
        for ( Enemy currentEnemy : board.getAllEnemiesInCurrentWave()) {
            if (currentEnemy.isActive()) {
                paintPlacablePixelPosition(currentEnemy, g2);

                printHealthAboveEnemy(currentEnemy, g2);
            }
        }
    }


    private void printHealthAboveEnemy(Enemy currentEnemy, Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawString(currentEnemy.getHealthText(), currentEnemy.getPixelPosition().getX(),
                currentEnemy.getPixelPosition().getY());
    }

    /**
     * paintPlaceable paints an object of type Placable by using its coordinates in the grid
     * <br></br>OBS ignores current pixelposition so it does not work on enemies which relies soley on their
     * pixelPosition.
     * @param obj of type Placable - to be painted
     * @param g2
     */
    private void paintPlaceable(Placeable obj, Graphics2D g2) {
        if (obj == null) {
            System.out.println("obj in paintPlaceable is null");
        } else {
            int yPos = obj.getPosition().getY();
            int xPos = obj.getPosition().getX();
            int ySide = obj.getDimension().height;
            int xSide = obj.getDimension().width;
            for (int y = 0; y < ySide; y++) {
                for (int x = 0; x < xSide; x++) {
                    Color color = obj.getGUIColor();
                    g2.setColor(color);
                    g2.fill(new Rectangle(GlobalPositioning.getXPixel(xPos + x), GlobalPositioning.getYPixel(yPos + y),
                            Board.getSquareWidth(), Board.getSquareHeight()));
                }
            }
        }
    }

    /**
     *  paintPlacablePixelPosition is like paintPlacable but can paints the object according to its pixelposition
     * @param obj
     * @param g2
     */
    private void paintPlacablePixelPosition(Placeable obj, Graphics2D g2) {
        if (obj == null) {
            System.out.println("enemy obj in paintPlacablePixelPosition is null");
        } else {
            int yPos = obj.getPixelPosition().getY();
            int xPos = obj.getPixelPosition().getX();
            int ySide = obj.getDimension().height;
            int xSide = obj.getDimension().width;
            for (int y = 0; y < ySide; y++) {
                for (int x = 0; x < xSide; x++) {
                    Color color = obj.getGUIColor();
                    g2.setColor(color);
                    g2.fill(new Rectangle((xPos + x), (yPos + y), Board.getSquareWidth(),
                            Board.getSquareHeight()));
                }
            }
        }
    }

    private String stringConverter(int info) {
        return String.valueOf(info);
    }

    @Override
    public void boardChanged() {
        repaint();
    }

    public void higlight(Placeable obj) {
        this.higlightedObj = obj;
        higlightisObj = true;
        nothingIsHighlighted = false;
    }

    public void higlightPoint(main.position.Point mouseGridPosition) {
        this.higlightedPoint = mouseGridPosition;
        higlightisObj = false;
        nothingIsHighlighted = false;
    }
}


