package main.enemy;

import main.action.GameAction;
import main.board.Placeable;
import main.tower.Tower;
import main.board.Board;
import main.graphics.ColorHandlerSingleton.Colour;
import main.position.Point;
import main.position.Vector;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:23
 * The main class for enemy, all types of enemy will extend this class. Has all the basic information which all
 * enemy have in common.
 */
@SuppressWarnings("CallToSimpleSetterFromWithinClass") // keeping this since simple setters might change and its a bit easier
// to refactor things we call setters internally too.
public class Enemy extends Placeable {
    private int hitPoints;
    private int pixelSpeed;
    private int dmgToBase;
    private int gold;
    private boolean alive;
    private boolean active;
    private Board board = null;
    private double activationTime;
    private int experienceToTowers;

    private EnemyPath enemyPathing = null;

    public Enemy(Board board, int x, int y, Colour colour,
                 int experienceToTowers, int pixelSpeed, int dmgToBase, int gold, int hitPoints) {
        super(x, y, hitPoints , colour, 9);
        this.board = board;
        this.hitPoints = hitPoints;
        this.pixelSpeed = pixelSpeed;
        if (pixelSpeed >= Board.getSquareHeight() || pixelSpeed >= Board.getSquareWidth()) { // there is a speed limit
            if (Board.getCastleHeight() < Board.getCastleWidth()) {
                this.pixelSpeed = Board.getCastleHeight() ;
            }
            this.pixelSpeed = Board.getCastleWidth();
        }
        this.dmgToBase = dmgToBase;
        alive = true;
        active = false;
        this.gold = gold;
        this.experienceToTowers = experienceToTowers;
        enemyPathing = new EnemyPath(getPosition(), board.getCastlePos());

    }

    /**
     * The idea is that an enemy can be sent in as a prototype for creating a new one. The fields alive and active
     * could later on be set to some default values if the questions of reusing object would come up. Reuse of
     * objects could be done by creating new objects using dead enemies as Prototypes but setting alive and active to
     * true for example.
     * The activationTime for the prototype is not used directly as we do not want all enemies to appear on the field
     * at the same time as the first, hence activationTime can be altered for the enemies.
     *
     * @param enemyPrototype an enemy to be copied
     * @param activationTime new activationTime for this enemy.
     */
    public Enemy(Enemy enemyPrototype, double activationTime) {
        super(enemyPrototype.getPosition().getX(), enemyPrototype.getPosition().getY(), enemyPrototype.getHitpoints() , enemyPrototype.getColour(), 9);
        this.board = enemyPrototype.board;
        this.hitPoints = enemyPrototype.getHitpoints();
        this.pixelSpeed = enemyPrototype.pixelSpeed;
        if (pixelSpeed >= Board.getSquareHeight() || pixelSpeed >= Board.getSquareWidth()) { // there is a speed limit
            if (Board.getCastleHeight() < Board.getCastleWidth()) {
                this.pixelSpeed = Board.getCastleHeight() ;
            }
            this.pixelSpeed = Board.getCastleWidth();
        }
        this.dmgToBase = enemyPrototype.dmgToBase;
        alive = enemyPrototype.alive;
        active = enemyPrototype.active;
        this.gold = enemyPrototype.gold;
        this.activationTime = activationTime;
        this.experienceToTowers = enemyPrototype.experienceToTowers;
        enemyPathing = new EnemyPath(getPosition(), board.getCastlePos());
    }


    /**
     * like all other tick methods this handels everything that gamemechanically could or should happen to an object.
     * tick for enemies: Check if enemy should be activated, if it should move and if it should die and depending on
     * how it dies it should either add/subtract gold to the player and if it reaches the castle which the player
     * must defend it subtracts life from the castle.
     * @param time current time
     */
    public void tick(double time) {
        if (activationTime <= time && alive) {
            setActive(true);

            for (GameAction currentAction : getGameActions()) {
                currentAction.tick(this);
            }

            moveEnemy(enemyPixelMovement(enemyPathing.getCurrentPixelGoal()));
            if (enemyPathing.getCurrentPixelGoal().equals(getPixelPosition())) {
                enemyPathing.updateGoal();
            }
        }

        if (getHitpoints() <= 0 && alive) {
            setToDead();
            return;
        }
        if (alive && board.isWithinCastlePixelPos(this)) { // if enemy is on castle
            board.getPlayer().subtractLives(dmgToBase);
            board.getPlayer().subtractGold(gold);
            setToDead();
        }
    }

    /**
     * calculates the vector which the enemy should move by
     * @return  a main.position.Vector that determines the change in position of every tick
     * @see
     */
    private Vector enemyPixelMovement(Point goal) {
        //   Point goal = board.getCastle().getCenterOfObject();

        Point enemyPos = this.getPixelPosition();
        int deltaX = goal.getX()-enemyPos.getX();
        int deltaY = goal.getY()-enemyPos.getY();
        Vector newPos;
        // kollar vilket håll som enemy ska gå
        int moveX, moveY;

        // check for how far it should move and if if the direction should be pos or neg.
        if (abs(deltaX) > pixelSpeed) {
            moveX = pixelSpeed;
            if (deltaX < 0) moveX = -moveX;
        } else {
            moveX = deltaX;
        }
        if (abs(deltaY) > pixelSpeed) {
            moveY = pixelSpeed;
            if (deltaY < 0) moveY = -moveY;
        } else {
            moveY = deltaY;
        }


        if (deltaX == 0 && deltaY == 0) {
            newPos = new Vector(0,0);
        } else {
            if (abs(deltaX) != 0) {
                newPos = new Vector(moveX, 0);
            } else {
                newPos = new Vector(0, moveY);
            }
        }

        return newPos;
    }

    public void setToDead() {
        setAlive(false);
        setImortality(true);
        //     makeDeadBody();

        for( Tower currentTower : board.getAllTowers()) {
            currentTower.removeFromCurrentPlaceablesInRangeOfThisTower(this);
        }

        setActive(false);
        //  setJustDied(false); // temporär tilldelning så fiender försvinner istället för att blinka
        //möjligt problem intill currentEnemy.setJustDied(false) i main.graphics.GraphicalViewer.paintAllEnemies?
    }

    /*
   Enkla Getters och Setter nedanför
    */

    public int getGold() {
        return gold;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public int getDmgToBase() {
        return dmgToBase;
    }

    @Override public int hashCode() {
	int result;
	long temp;
	result = hitPoints;
	result = 31 * result + pixelSpeed;
	result = 31 * result + dmgToBase;
	result = 31 * result + gold;
	result = 31 * result + (alive ? 1 : 0);
	result = 31 * result + (active ? 1 : 0);
	result = 31 * result + (board != null ? board.hashCode() : 0);
	temp = Double.doubleToLongBits(activationTime);
	result = 31 * result + (int) (temp ^ (temp >>> 32));
	result = 31 * result + experienceToTowers;
	result = 31 * result + (enemyPathing != null ? enemyPathing.hashCode() : 0);
	return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enemy)) return false;
        if (!super.equals(o)) return false;

        Enemy enemy = (Enemy) o;

        if (dmgToBase != enemy.dmgToBase) return false;
        if (hitPoints != enemy.hitPoints) return false;
        if (active != enemy.active) return false;
        if (alive != enemy.alive) return false;
        if (experienceToTowers != enemy.experienceToTowers) return false;
        if (gold != enemy.gold) return false;
  //      if (justDied != enemy.justDied) return false;
        if (pixelSpeed != enemy.pixelSpeed) return false;
    //    if (speed != enemy.speed) return false;
        if (board != null ? !board.equals(enemy.board) : enemy.board != null) return false;

        return true;
    }

    /**
     * remember to add a kill in the caller!
     * @param dmg
     */
    public void attacked(int dmg) {
        subtractHitpoints(dmg);
        if (getHitpoints() <= 0) {
            setToDead();
            // tower.addKills(1);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public boolean isActive() {
        return active;
    }

    /**
     * Changes the position of an placeable obj by a vector
     * @param point this vector contains the change in x and y.
     */
    public void moveEnemy(Vector point) {
        int currentX = this.getPixelPosition().getX();
        int currentY = this.getPixelPosition().getY();
        int deltaX = point.getX();
        int deltaY = point.getY();
        Point tmpPoint = new Point(currentX+deltaX, currentY+deltaY);
        this.setPixelPosition(tmpPoint);
    }


    public double getActivationTime() {
        return activationTime;
    }

    public int getPixelSpeed() {
        return pixelSpeed;
    }

    /*
   Getters for information about the enemy object
    */
    public String getHealthText() {
        return String.valueOf(getHitpoints());
    }

    /**
     * Sees if the given point is within the enemy. Could be moved to class Placable?
     * @param point
     * @return true if point is within object
     */
    public boolean isWithinObject(Point point) {
        int enemyX = getPixelPosition().getX();
        int enemyY = getPixelPosition().getY();
        int width = Board.getSquareWidth();
        int height = Board.getSquareHeight();
        int pointX = point.getX();
        int pointY = point.getY();

        // if point is between the leftmost and rightmost pixel of the object in X
        // and between the highest and lowest pixel in Y return true.
        if ((enemyX <= pointX && pointX <= (enemyX+width)) &&
                (enemyY <= pointY && pointY <= (enemyY+height))) {
            return true;
        }
        return false;
    }

    public int getExperienceToTowers() { return experienceToTowers; }

    @Override // "Method X ignores defined method in superclass errors are ok in my book if it is an override like this.
    public void addBuffers(GameAction action) {
      // as of now there are no buffers for enemies.
    }

    @Override
    public void removeBuffer(GameAction action) {
        // as of now there are no buffers for enemies.
    }
}
