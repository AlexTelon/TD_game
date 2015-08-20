package main.board;

import main.player.Player;
import main.tower.nonshootabletower.NonShootableTower;
import main.tower.Tower;
import main.board.IDesign.Shapes;
import main.enemy.Enemy;
import main.enemy.EnemyWaves;
import main.enemy.EnemyWave;
import main.graphics.ColorHandlerSingleton.Colour;
import main.position.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:22
 * This is the class that puts all the peices together so to speak. It is the board which all objects are placed upon
 * and it keeps track of all objects in the game.
 */

@SuppressWarnings({ "CallToSimpleSetterFromWithinClass", "SuspiciousGetterSetter", "CallToSimpleGetterFromWithinClass" })
// keeping this since simple setters might change and its a bit easier
// to refactor things we call setters internally too. All getters/setters are good so the suspiciouns are wrong imo.
public class Board {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int SQUARE_WIDTH = 20; // in pixels
    private static final int SQUARE_HEIGHT = 20; // in pixels
    private static final int PIXELWIDTH = WIDTH*SQUARE_WIDTH;
    private static final int PIXELHEIGHT = HEIGHT*SQUARE_HEIGHT;
    private static final int CASTLE_SIDE = 2;
    private static final int FRAMERATE = 50;
    private static final double DELTA = 0.001; // used for comparisons between float values

    private Player player = new Player();
    private boolean gameover = false;
    private double currentTime = 0;
    private double countdownToNextWave = 0;
    private final List<IBoardListener> boardListener  = new ArrayList<IBoardListener>();


    private final List<Tower> allTowers = new ArrayList<Tower>();
    private List<NonShootableTower> allNonShootableTowers = new ArrayList<NonShootableTower>();
    private final ArrayList<Placeable> allObjects = new ArrayList<Placeable>();

    // A datatype containing all the priority for all positions.
    private final PriorityMap priorityMap = new PriorityMap();

    // Things that are constant and placed on the grid from the getgo.
    private final Placeable castle = new Placeable(WIDTH-CASTLE_SIDE, HEIGHT/2,
            new Dimension(CASTLE_SIDE,
                    CASTLE_SIDE), Colour.RED, Shapes.RECTANGLE, 8); //only enemies are above this

    private static final Color BACKGROUND_COLOR = Color.GREEN;
    private double frameRate = FRAMERATE;
    private EnemyWaves enemyWaves = new EnemyWaves(this, castle.getPosition(), new Point(0, HEIGHT/2));
    private EnemyWave allEnemiesInCurrentWave = enemyWaves.getNextEnemyWave();



    public Board(double framerate) {
        setFramerate(framerate);

        placeEnemyPathOnBoard();
        placeObjectOnBoard(castle);
    }

    private void placeEnemyPathOnBoard() {
        int priority = enemyWaves.getPriority();
	for (Point currentPoint : enemyWaves.getEnemyPath()) {
	    int x = currentPoint.getX();
	    int y = currentPoint.getY();
	    priorityMap.setPriorityMap(x,y, priority);
        }
    }

    /**
     * Keeps track of all gamemechanical rules by calling tick for ALL enemies and towers. Also keeps track of things
     * like player win/lose conditions, prints stats and inciments the global currentTime.
     */
    public void tick() {
        currentTime += frameRate;
        subtractCountdownToNextWave(frameRate);

        if (!gameover) {
            notifyListeners();

            if (countdownToNextWave <= DELTA) {

                // asume all enemies are dead and if this is not the case it is set to false below.
                boolean allEnemiesAreDead = true;
                for (Enemy currentEnemy : allEnemiesInCurrentWave) {
                    currentEnemy.tick(currentTime);
                    if (currentEnemy.isAlive()) {
                        allEnemiesAreDead = false;
                    }
                }
                if (allEnemiesAreDead) {
                    if(enemyWaves.isThereMoreGroups()) {
                        setCountdownToNextWave(enemyWaves.getNextGroupsActivationTime());
                        allEnemiesInCurrentWave = enemyWaves.getNextEnemyWave();
                        currentTime = 0;
                    } else {
                        playerWins();
                        notifyListeners();
                    }

                }
                if (player.getLives() <= 0) {
                    gameOver();
                }
                for ( Tower currentTower : allTowers) {
                    currentTower.tick(allEnemiesInCurrentWave);
                }
            }
        }
    }

    private void playerWins() {
        System.out.println();
        System.out.println("You won!!!");
        System.out.println();
        this.gameover = true;
    }

    /**
     * Placeable objects are placed in their proper lists.
     * @param obj
     */
    public void addObject(Placeable obj) {

        if (isValidPositions(obj)) {
            allObjects.add(0, obj);
	    if ( obj instanceof Tower) {
		allTowers.add(0, (Tower) obj);
		setPriority(obj);

		if (obj instanceof NonShootableTower) {
		    allNonShootableTowers.add(0, (NonShootableTower) obj);
		}

	    }
	}
    }

    public Board(Placeable obj) {
        setPriority(obj);
    }

    public PriorityMap getPriorityMap() {
        return priorityMap;
    }

    public void placeObjectOnBoard(Placeable object) {
        if (isValidPositions(object)) {
            setPriority(object);
        }
    }

    //checks if the area under an object is empty - if yes return true.
    public boolean isValidPositions(Point position, int priority, Dimension dimension) {
        int heightOfObject = dimension.height;
        int widthOfObject = dimension.width;
        int yPos = position.getY();
        int xPos = position.getX();

        for (int y = yPos; y < yPos+heightOfObject; y++) {
            for (int x = xPos; x < xPos+widthOfObject; x++) {
                if (priority <= getPriority(x, y)) {
                    System.out.println("Cannot be placed there");
                    return false;
                }
            }
        }
        return true;
    }

    //checks if the area under an object is empty - if yes return true.
    public boolean isValidPositions(Placeable object) {
        int heightOfObject = (int) object.getSize().getHeight();
        int widthOfObject = (int) object.getSize().getWidth();
        int yPosOfObject = object.getPosition().getY();
        int xPosOfObject = object.getPosition().getX();

        for (int y = yPosOfObject; y < heightOfObject; y++) {
            for (int x = xPosOfObject; x < widthOfObject; x++) {
                if (object.getPriority() <= getPriority(x, y)) {
                    System.out.println("Cannot be placed there");
                    return false;
                }
            }
        }
        return true;
    }

    public void setPriority(int x, int y, int newValue) {
        priorityMap.setPriorityMap(x, y, newValue);
    }

    /**
     * Sets the priority on a priorityMap
     *
     * @param object
     */
    public void setPriority(Placeable object) {
        int heightOfObject = (int) object.getSize().getHeight();
        int widthOfObject = (int) object.getSize().getWidth();
        int yPosOfObject = object.getPosition().getY();
        int xPosOfObject = object.getPosition().getX();

        if (heightOfObject+yPosOfObject > Board.HEIGHT || widthOfObject+xPosOfObject > Board.getWidth()) {
            System.out.println("out of bounds -- setPriority");
        } else {
            for (int y = 0; y < heightOfObject; y++) {
                for (int x = 0; x < widthOfObject; x++) {
                    setPriority(x + xPosOfObject, y + yPosOfObject, object.getPriority());
                }
            }
        }
    }

    public boolean isWithinCastlePixelPos(Enemy currentEnemy) {
        if ( isWithinCastle(getPosOnGrid(currentEnemy.getCenterOfObject()))) {
            return true;
        }
        return false;
    }

    //returns over which pos on the grid that a main.position.Point in pixels is
    public Point getPosOnGrid(main.position.Point pixelPosition) {
        int xPixel = pixelPosition.getX();
        int yPixel = pixelPosition.getY();
        int xtmp = Board.getSquareWidth();
        int ytmp = Board.getSquareHeight();
        int xGrid = 0;
        int yGrid = 0;

        while (xPixel >= xtmp) {
            xtmp += Board.getSquareWidth();
            xGrid++;
        }
        while (yPixel >= ytmp) {
            ytmp += Board.getSquareHeight();
            yGrid++;
        }

        return new Point(xGrid,yGrid);
    }


    public boolean isWithinCastle(Point point) {
        int castleY = castle.getPosition().getY();
        int castleX = castle.getPosition().getX();
        for (int y = castleY; y < castleY + getCastleHeight(); y++ ) {
            for (int x = castleX; x < castleX + getCastleWidth(); x++ ) {
                if (point.getX() == x && point.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }


    //Listener methods
    public void addBoardListener(IBoardListener bl) {
        boardListener.add(bl);
    }

    // Loops through all listners in the arrayList and calls boardChanged() for them all
    public void notifyListeners() {
        if (!boardListener.isEmpty()) {
            for (int i = 0; i < boardListener.size(); i++) {
                boardListener.get(i).boardChanged();
            }
        }
    }

    public void gameOver() {
        System.out.println("Game over!");
        this.gameover = true;
    }

    public static int getCastleWidth() {
        return CASTLE_SIDE;
    }

    public static int getCastleHeight() {
        return CASTLE_SIDE;
    }

    public Point getCastlePos() {
        return castle.getPosition();
    }

    public void resetBoard(Point point) {
        priorityMap.resetPriorityMap(point);
    }

    public int getPriority(int x, int y) {
        return priorityMap.getPriorityMap(x,y);
    }


    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() { return WIDTH; }


    @SuppressWarnings("TypeMayBeWeakened")
    /*
    I dont think that is a needed here. Below is sample code of a case where I would need to
    be explicit of what i get back from this getter which just seems unnecessary right now. Sure being general is good
    if I would change the type of allEnemiesInCurrentWave, this function would not need to change, but the below code
    would need to change anyway to cast to that something else and I would argue that then we have gained nothing as it
    would result in equally many places to do refactoring on. Anyways here is the code example:
       for ( Enemy currentEnemy : (EnemyWave) board.getAllEnemiesInCurrentWave()) { ... }

    Without the suggested change it it:
       for ( Enemy currentEnemy : board.getAllEnemiesInCurrentWave()) { ... }
    */
    public EnemyWave getAllEnemiesInCurrentWave() {
        return allEnemiesInCurrentWave;
    }

    public Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }

    public Iterable<Tower> getAllTowers() {
        return allTowers;
    }

    public Placeable getCastle() {
        return castle;
    }

    public static int getPixelheight() {
        return PIXELHEIGHT;
    }

    public static int getPixelwidth() {
        return PIXELWIDTH;
    }

    public static int getSquareHeight() {
        return SQUARE_HEIGHT;
    }

    public static int getSquareWidth() {
        return SQUARE_WIDTH;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getFrameRate() {
        return frameRate;
    }

    public void setFramerate(double refreshrate) {
        this.frameRate = refreshrate;
    }

    public ArrayList<Placeable> getAllObjects() {
        return allObjects;
    }

    public double getCountdownToNextWave() {
        return countdownToNextWave;
    }

    public void setCountdownToNextWave(double countdownToNextWave) {
        this.countdownToNextWave = countdownToNextWave;
    }

    public void subtractCountdownToNextWave(double refreshrate) {
        countdownToNextWave -= refreshrate;
        if (countdownToNextWave < 0)
            countdownToNextWave = 0;
    }

    public EnemyWaves getEnemyWaves() {
        return enemyWaves;
    }

    public void removeFromAllTowers(Tower tower) {
        allTowers.remove(tower);
    }

    public void removeFromNonShootableTower(Tower tower) {
        allNonShootableTowers.remove(tower);
    }


    /**
     * sets priorioty on the priorityMap to 0 again, player gains some gold, tower is removed from allTowers
     * @param currentTower
     */
    public void sellTower(Tower currentTower) {
        player.addGold(currentTower.getPrice());
        resetBoard(currentTower.getPosition());
        currentTower.delete();


    }

    public Player getPlayer() {
        return player;
    }
}
