package main.tower;

import main.board.Placeable;
import main.enemy.Enemy;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Class encapsulating the range checks.
 */
public class RangeHandler {
    private Collection<Placeable> placablesInRange = new ArrayList<Placeable>();
    private static final double DEFAULT_RANGE = 200.0;
    private double range = DEFAULT_RANGE;

    /**
     * Finds all placebles within range and adds them to the current placables in range of the tower.
     * @param allObjects the group of placables amoung the search for new placables in range of the tower
     * @param referencePoint - a placeable from which we want to find which objects are within range of
     */
    public void updateObjectsWithinRange(Collection<Placeable> allObjects, Placeable referencePoint) {
        if (!allObjects.isEmpty()) {
            clearPlaceablesInRange();
            for (Placeable obj : allObjects) {
                if (!obj.equals(referencePoint)) {
                    if (isObjectWithinRange(obj, referencePoint)) {
                        addToCurrentPlaceablesInRange(obj);
                    }
                }
            }
        }
    }

    /**
     * Constructor.
     * @param range sets the maximum range of this RangeHandler.
     */
    public RangeHandler(double range) {
        this.range = range;
    }

    /**
     * Calculates if a object is within range of the tower.
     * @param obj it is a placable
     * @return true if it is in range.
     */
    public boolean isObjectWithinRange(Placeable obj, Placeable obj2) {
        if (obj.distanceTo(obj2) <= range) {
            return true;
        }
        return false;
    }

    /*
     * Things regarding placablesInRange
     */

    public void addToCurrentPlaceablesInRange(Placeable obj) {
        if (!placablesInRange.contains(obj)) {
            this.placablesInRange.add(obj);
        }
    }

    public void removePlaceableInRange(Enemy currentEnemy) {
        this.placablesInRange.remove(currentEnemy);
    }

    public void clearPlaceablesInRange() {
        this.placablesInRange.clear();
    }

    public Collection<Placeable> getPlacablesInRange() { return placablesInRange; }

}