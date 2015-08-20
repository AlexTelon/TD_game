package main.tower;

import main.board.Placeable;
import main.enemy.Enemy;

import java.util.Collection;
import java.util.ArrayList;

public class AttackHelpClass {
    private Collection<Placeable> placablesInRangeOfThisTower = new ArrayList<Placeable>();
    private ArrayList<Placeable> allObjects = new ArrayList<Placeable>();
    private final double range = 200.0;

    /**
     * Finds all placebles within range and returns them
     * @param allObjects
     * @param referencePoint - a placeable from which we want to find which objects are within range of
     */
    public void findObjectsWithinRange(Collection<Placeable> allObjects, Placeable referencePoint) {
        if (!allObjects.isEmpty()) {
            clearPlaceablesWithinRangeOfThisTower();
            for (Placeable obj : allObjects) {
                if (!obj.equals(referencePoint)) {
                    if (isObjectWithinRange(obj, referencePoint)) {
                        addToCurrentPlaceablesInRangeOfThisTower(obj);
                    }
                }
            }
        }
    }

    /**
     * Calculates if a object is within range of the tower.
     *
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
     * Things regarding placablesInRangeOfThisTower
     */


    public void addToCurrentPlaceablesInRangeOfThisTower(Placeable obj) {
        if (!placablesInRangeOfThisTower.contains(obj)) {
            this.placablesInRangeOfThisTower.add(obj);
        }
    }

    public void removeFromCurrentPlaceablesInRangeOfThisTower(Enemy currentEnemy) {
        this.placablesInRangeOfThisTower.remove(currentEnemy);
    }

    public void clearPlaceablesWithinRangeOfThisTower() {
        this.placablesInRangeOfThisTower.clear();
    }

    public Collection<Placeable> getPlacablesInRangeOfThisTower() {
        return placablesInRangeOfThisTower;
    }

    public ArrayList<Placeable> getAllObjects() {
        return allObjects;
    }

    public void setAllObjects(ArrayList<Placeable> allObjects) {
        this.allObjects = allObjects;
    }


}