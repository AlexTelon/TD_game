package main.Towers;

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
     * Fins all placebles within range and returns them
     */
    void findObjectsWithinRange(ArrayList<Placeable> allObjects) {
        for (Placeable obj : allObjects) {
            if (isObjectWithinRange(obj)) {
                if (obj != null) { // so we dont get an infinite recursion?
                    addToCurrentPlacablesWithinRangeOfThisTower(obj); // why do we do this?
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
    public boolean isObjectWithinRange(Placeable obj) {
        if (obj.distanceTo(null) <= range) {
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

    /**
     * Calculates if a object is within range of the tower.
     *
     * @param towers@return true if it is in range.
     */
    public boolean isObjectWithinRange(Towers towers) {
        return isObjectWithinRange(towers);
    }
}