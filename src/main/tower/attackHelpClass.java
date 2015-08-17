package main.tower;

import main.board.Placeable;
import main.enemy.Enemy;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class AttackHelpClass
{
    private List<Placeable> placablesWithinRangeOfThisTower = new ArrayList<Placeable>();
    private ArrayList<Placeable> allObjects = new ArrayList<Placeable>();
    private double range = 200.0;

    /**
     * Finds all placebles within range and returns them
     * @param allObjects
     * @param referencePoint - a placeable from which we want to find which objects are within range of
     * @return
     */
    public void findObjectsWithinRange(Collection<Placeable> allObjects, Placeable referencePoint) {
        if (!allObjects.isEmpty()) {
            clearPlaceablesWithinRangeOfThisTower();
            for (Placeable obj : allObjects) {
                if (obj != referencePoint) {
                    if (isObjectWithinRange(obj, referencePoint)) {
                        addToCurrentPlaceablesWithinRangeOfThisTower(obj);
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
    }/*
    Things regarding placablesWithinRangeOfThisTower
     */

    public void addToCurrentPlaceablesWithinRangeOfThisTower(Placeable obj) {
        if (!placablesWithinRangeOfThisTower.contains(obj)) {
            this.placablesWithinRangeOfThisTower.add(obj);
        }
    }

    public void removeFromCurrentPlaceablesWithinRangeOfThisTower(Enemy currentEnemy) {
        this.placablesWithinRangeOfThisTower.remove(currentEnemy);
    }

    public void clearPlaceablesWithinRangeOfThisTower() {
        this.placablesWithinRangeOfThisTower.clear();
    }

    public Collection<Placeable> getPlaceablesWithinRangeOfThisTower() {
        return placablesWithinRangeOfThisTower;
    }

    public ArrayList<Placeable> getAllObjects() {
        return allObjects;
    }

    public void setAllObjects(ArrayList<Placeable> allObjects) {
        this.allObjects = allObjects;
    }


}