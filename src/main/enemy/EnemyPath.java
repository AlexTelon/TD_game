package main.enemy;

import main.board.Board;
import main.position.Point;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 2012-10-04
 * Time: 23:50
 * TThis is the path that all enemy will take in their way to the castle.
 */
public class EnemyPath {
    private final ArrayList<Point> mainPath = new ArrayList<Point>();
    private int currentGoal = 0;
    private ArrayList<Point> path = new ArrayList<Point>();
    private static int x = new Random().nextInt(Board.getWidth()-Board.getCastleWidth());
    private static int y = new Random().nextInt(Board.getHeight());
    private static int x2 = new Random().nextInt(Board.getWidth()-Board.getCastleWidth());
    private static int y2 = new Random().nextInt(Board.getHeight());
    private int pathLenght = 0;
    /*
    private static int x3 = new Random().nextInt(Board.getWidth());
    private static int y3 = new Random().nextInt(Board.getHeight());
*/
    public EnemyPath(Point start, Point endGoal) {

        this.mainPath.add(0,new Point(x,y));
        this.mainPath.add(1,new Point(x2,y2));
     //   this.mainPath.add(2,new Point(x3,y3));
        this.mainPath.add(2,endGoal);

        setPath(start);
    }

    public ArrayList<Point> getMainPath() {
        return mainPath;
    }

    /**
     * Gets the position for the next goalPoint for enemies
     * @return
     */
    public Point getNextGoal() {
        currentGoal++;
        return mainPath.get(currentGoal-1);
    }

    public Point getCurrentPixelGoal() {
        return mainPath.get(currentGoal).getPixelPos();
    }

    public Point getCurrentGridGoal() {
        return mainPath.get(currentGoal);
    }

    /**
     * Method that calculates the path that the enemies will take. Is now hardcoded to follow a certain pattern to
     * the goals, but in the future this should be made to use the exact same method that enemies use for determining
     * their path so that everything uses the same piece of code.
     * @param start
     */
    private void setPath(Point start) {
        int counter = 0;
        Point PathPoint;
        path.add(start);
        Point goal = mainPath.get(counter);
        counter++;

        int currentXPos = start.getX();
        int currentYPos = start.getY();
        int deltaX;
        int deltaY;

        while(true) {
            while (goal.getX() != currentXPos || goal.getY() != currentYPos) {
                deltaX = goal.getX()-currentXPos;
                deltaY = goal.getY()-currentYPos;

                if (deltaX != 0) {
                    int changeInX = deltaX/abs(deltaX); // +/- 1
                    currentXPos += changeInX; // update current XPos
                } else if (deltaY != 0) {
                    int changeInY = deltaY/abs(deltaY); // +/- 1
                    currentYPos += changeInY; // update current YPos
                }

                PathPoint = new Point(currentXPos, currentYPos);
                path.add(PathPoint);

            }
            assert (goal.isEmpty());
            if (counter >= mainPath.size()) break;
            goal = mainPath.get(counter);
            counter++;
        }
    }

    public ArrayList<Point> getPath() {
        return path;
    }
}
