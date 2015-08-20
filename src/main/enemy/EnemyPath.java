package main.enemy;

import main.board.Board;
import main.position.Point;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
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
    private List<Point> path = new ArrayList<Point>();
    private static int subGoal1x = new Random().nextInt(Board.getWidth()-Board.getCastleWidth());
    private static int subGoal1y = new Random().nextInt(Board.getHeight());
    private static int subGoal2x = new Random().nextInt(Board.getWidth()-Board.getCastleWidth());
    private static int subGoal2y = new Random().nextInt(Board.getHeight());
    private int pathLenght = 0;
    /*
    private static int x3 = new Random().nextInt(Board.getWidth());
    private static int y3 = new Random().nextInt(Board.getHeight());
*/
    public EnemyPath(Point start, Point endGoal) {

        this.mainPath.add(0,new Point(subGoal1x, subGoal1y));
        this.mainPath.add(1,new Point(subGoal2x, subGoal2y));
     //   this.mainPath.add(2,new Point(x3,y3));
        this.mainPath.add(2,endGoal);

        setPath(start);
    }

    public ArrayList<Point> getMainPath() {
        return mainPath;
    }

    /**
     * Updates the position for the next goalPoint for enemies
     */
    public void updateGoal() {
        currentGoal++;
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
     * @param start The startpoint
     */
    private void setPath(Point start) {
        int counter = 0;
	path.add(start);
        Point goal = mainPath.get(counter);
        counter++;

        int currentXPos = start.getX();
        int currentYPos = start.getY();


        while(true) {
            while (goal.getX() != currentXPos || goal.getY() != currentYPos) {
		int deltaX = goal.getX() - currentXPos;
		int deltaY = goal.getY()-currentYPos;

                if (deltaX != 0) {
                    int changeInX = deltaX/abs(deltaX); // +/- 1
                    currentXPos += changeInX; // update current XPos
                } else if (deltaY != 0) {
                    int changeInY = deltaY/abs(deltaY); // +/- 1
                    currentYPos += changeInY; // update current YPos
                }

                Point pathPoint = new Point(currentXPos, currentYPos);
		path.add(pathPoint);

            }
            assert (goal.isEmpty());
            if (counter >= mainPath.size()) break;
            goal = mainPath.get(counter);
            counter++;
        }
    }

    public List<Point> getPath() {
        return path;
    }
}
