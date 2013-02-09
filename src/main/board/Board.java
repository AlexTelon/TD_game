package main.board;

import main.Towers.AuraTowers.NonShootableTower;
import main.Towers.shootingTowers.ShootableTowers;
import main.Towers.Towers;
import main.action.GameActions;
import main.enemies.Enemies;
import main.enemies.EnemyWaves;
import main.enemies.EnemyWave;
import main.graphics.ColorHandler;
import main.position.Point;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:22
 * This is the class that puts all the peices together so to speak. It is the board which all objects are placed upon
 * and it keeps track of all objects in the game.
 */

public class Board {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int SQUARE_WIDTH = 20; // in pixels
    private static final int SQUARE_HEIGHT = 20; // in pixels
    private static final int PIXELWIDTH = WIDTH*SQUARE_WIDTH;
    private static final int PIXELHEIGHT = HEIGHT*SQUARE_HEIGHT;
    private static final int CASTLE_SIDE = 2;
    private int gold = 40;
    private int lives = 100;
    private static boolean gameover = false;
    private final int difficulty = 1;
    private double currentTime = 0;
    private double countdownToNextWave = 0;
    private final ArrayList<IBoardListener> boardListener  = new ArrayList<IBoardListener>();
    private EnemyWaves enemyWaves;

    //All enemies in current wave
    private EnemyWave allEnemiesInCurrentWave;

    // All main.Towers.Towers
    private final ArrayList<Towers> allTowers = new ArrayList<Towers>();

    // All main.Towers.shootingTowers.ShootableTowers
    private final ArrayList<ShootableTowers> allShootableTowers = new ArrayList<ShootableTowers>();

    // All main.Towers.AuraTowers.NonShootableTower
    private ArrayList<NonShootableTower> allNonShootableTowers = new ArrayList<NonShootableTower>();

    // All Objects
    private final ArrayList<Placeable> allObjects = new ArrayList<Placeable>();

    // A datatype containing all the priority for all positions.
    private final PriorityMap priorityMap = new PriorityMap();

    // Things that are constant and placed on the grid from the getgo.
    private final Placeable castle = new Placeable(WIDTH-CASTLE_SIDE, getHeight()/2,
            new Dimension(CASTLE_SIDE,
                    CASTLE_SIDE), ColorHandler.Colour.RED, IDesign.Shapes.Rectangle, 8); //only enemies are above this

    private final Color backgroundColor = Color.GREEN;
    private double frameRate = 50;



    public Board(double framerate) {
        setFramerate(framerate);
        enemyWaves = new EnemyWaves(this, difficulty, castle.getPosition(), new Point(0, getHeight()/2));
        allEnemiesInCurrentWave = enemyWaves.getNextEnemyWave();

        PlaceEnemyPathOnBoard();
        placeObjectOnBoard(castle);
    }

    private void PlaceEnemyPathOnBoard() {
        int priority = enemyWaves.getPriority();
        int x, y;
        for (Point currentPoint : enemyWaves.getEnemyPath()) {
            x = currentPoint.getX();
            y = currentPoint.getY();
            priorityMap.setPriorityMap(x,y, priority);
        }
    }

    /**
     * Keeps track of all gamemechanical rules by calling tick for ALL enemies and towers. Also keeps track of things
     * like player win/lose conditions, prints stats and inciments the global currentTime.
     */
    public void tick() {
        currentTime += getFrameRate();
        subtractCountdownToNextWave(getFrameRate());

        if (!gameover) {
            notifyListeners();

            if (getCountdownToNextWave() == 0) {

                // asume all enemies are dead and if this is not the case it is set to false below.
                boolean allEnemiesAreDead = true;
                for (Enemies currentEnemy : allEnemiesInCurrentWave) {
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
                if (lives <= 0) {
                    gameOver();
                }
                for ( Towers currentTower : allTowers) {
                    currentTower.tick(allEnemiesInCurrentWave);
                }
            }
        }
    }

    private static void playerWins() {
        System.out.println();
        System.out.println("You won!!!");
        System.out.println();
        gameover = true;
    }

    /**
     * Placeable objects are placed in their proper lists.
     * @param obj
     * @return true if added successfully, false otherwise
     */
    public void addObject(Placeable obj) {

        if (isValidPositions(obj)) {
            allObjects.add(0, obj);
            if (obj instanceof Enemies) {
                System.out.println("Why would you add enemies to a wave here? - in addObject");
            } else if ( obj instanceof Towers) {
                allTowers.add(0, (Towers) obj);
                setPriority(obj);

                if (obj instanceof ShootableTowers) {
                    allShootableTowers.add(0, (ShootableTowers) obj);
                }
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

        if (heightOfObject+yPosOfObject > Board.getHeight() || widthOfObject+xPosOfObject > Board.getWidth()) {
            System.out.println("out of bounds -- setPriority");
        } else {
            for (int y = 0; y < heightOfObject; y++) {
                for (int x = 0; x < widthOfObject; x++) {
                    setPriority(x + xPosOfObject, y + yPosOfObject, object.getPriority());
                }
            }
        }
    }

    public boolean isWithinCastlePixelPos(Enemies currentEnemy) {
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
        if (boardListener.isEmpty()) {
        }
        else {
            for (int i = 0; i < boardListener.size(); i++) {
                boardListener.get(i).boardChanged();
            }
        }
    }

    public static void gameOver() {
        System.out.println("Game over!");
        gameover = true;
    }

    public int getLives() {
        return lives;
    }

    public void subtractLives(int dmg) {
        lives -= dmg;
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

    public int getGold() {
        return gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
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

    public static int getWidth() {
        return WIDTH;
    }

    public void subtractGold(int goldloss) {
        gold -= goldloss;
        if ( gold < 0)
            gold = 0;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public EnemyWave getAllEnemiesInCurrentWave() {
        return allEnemiesInCurrentWave;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public ArrayList<Towers> getAllTowers() {
        return allTowers;
    }

    public ArrayList<ShootableTowers> getAllShootableTowers() {
        return allShootableTowers;
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

    /**
     * sets priorioty on the priorityMap to 0 again, player gains some gold, tower is removed from allTowers
     * @param currentTower
     */
    public void sellTower(Towers currentTower) {
        addGold(currentTower.getPrice());
        resetBoard(currentTower.getPosition());
        allTowers.remove(currentTower);

        for (Placeable currentPlaceable : currentTower.getPlacablesWithinRangeOfThisTower()) {
            for (GameActions action : currentTower.getGameActions()) {
                if (currentPlaceable instanceof ShootableTowers) {
                    ((ShootableTowers) currentPlaceable).getAttack().removeBuffers(action);
                }
                currentPlaceable.removeBuffer(action);
            }
        }
    }

}
