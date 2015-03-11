package main.tower;

import main.board.Placeable;
import main.enemy.Enemy;

import java.util.ArrayList;

public class attackHelpClass {
    java.util.List<Placeable> placablesWithinRangeOfThisTower = new ArrayList<Placeable>();
    ArrayList<Placeable> allObjects;
    double range = 200.0;

    /**
     * Finds all placebles within range and returns them
     * @param allObjects
     * @param referencePoint - a placeable from which we want to find which objects are within range of
     * @return
     */
    public java.util.List<Placeable> findObjectsWithinRange(java.util.List<Placeable> allObjects, Placeable referencePoint) {
        if (!allObjects.isEmpty()) {
            assert(allObjects != null);
            clearPlacablesWithinRangeOfThisTower();
            for (Placeable obj : allObjects) {
                if (obj != referencePoint) {
                    if (isObjectWithinRange(obj, referencePoint)) {
                        addToCurrentPlacablesWithinRangeOfThisTower(obj);
                    }
                }
            }
        }
        return placablesWithinRangeOfThisTower;
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
    }/*
    Things regarding placablesWithinRangeOfThisTower
     */

    public void addToCurrentPlacablesWithinRangeOfThisTower(Placeable obj) {
        if (!placablesWithinRangeOfThisTower.contains(obj)) {
            this.placablesWithinRangeOfThisTower.add(obj);
        }
    }

    public void removeFromCurrentPlacablesWithinRangeOfThisTower(Enemy currentEnemy) {
        this.placablesWithinRangeOfThisTower.remove(currentEnemy);
    }

    public void clearPlacablesWithinRangeOfThisTower() {
        this.placablesWithinRangeOfThisTower.clear();
    }

    public java.util.List<Placeable> getPlacablesWithinRangeOfThisTower() {
        return placablesWithinRangeOfThisTower;
    }

    public ArrayList<Placeable> getAllObjects() {
        return allObjects;
    }

    public void setAllObjects(ArrayList<Placeable> allObjects) {
        this.allObjects = allObjects;
    }


}