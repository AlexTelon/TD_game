package main.Tower;

import main.board.Placeable;
import main.enemies.Enemies;

import java.util.ArrayList;

public class attackHelpClass {
    ArrayList<Placeable> PlacablesWithinRangeOfThisTower = new ArrayList<Placeable>();
    ArrayList<Placeable> allObjects;
    double range = 200.0;

    public attackHelpClass() {
    }

    /**
     * Finds all placebles within range and returns them
     * @param allObjects
     * @param referencePoint - a placeable from which we want to find which objects are within range of
     * @return
     */
    public ArrayList<Placeable> findObjectsWithinRange(ArrayList<Placeable> allObjects, Placeable referencePoint) {
        if (!allObjects.isEmpty()) {
            assert(allObjects != null);
            for (Placeable obj : allObjects) {
                if (isObjectWithinRange(obj, referencePoint)) {
                    addToCurrentPlacablesWithinRangeOfThisTower(obj);
                }
            }
        }
        return PlacablesWithinRangeOfThisTower;
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
    Things regarding PlacablesWithinRangeOfThisTower
     */

    public void addToCurrentPlacablesWithinRangeOfThisTower(Placeable obj) {
        if (!PlacablesWithinRangeOfThisTower.contains(obj)) {
            this.PlacablesWithinRangeOfThisTower.add(obj);
        }
    }

    public void removeFromCurrentPlacablesWithinRangeOfThisTower(Enemies currentEnemy) {
        this.PlacablesWithinRangeOfThisTower.remove(currentEnemy);
    }

    public ArrayList<Placeable> getPlacablesWithinRangeOfThisTower() {
        return PlacablesWithinRangeOfThisTower;
    }

    public ArrayList<Placeable> getAllObjects() {
        return allObjects;
    }

    public void setAllObjects(ArrayList<Placeable> allObjects) {
        this.allObjects = allObjects;
    }


}