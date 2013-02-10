package main.action.shootingAction;

import main.Towers.Towers;
import main.Towers.shootingTowers.ShootableTowers;
import main.action.Attack;
import main.action.GameActions;
import main.enemies.Enemies;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * This ONLY and action - in other words this only handels HOW things are done and when. It should NOT contain any
 * data itself. The Data is in tower and in attack
 */
public class ShootingAction extends GameActions {
    private Towers tower;
    private Attack attack;


    public ShootingAction(Towers tower, Attack attack) {
        this.tower = tower;
        this.attack = attack;
    }

    /**
     * constructor to make a ShootingAction that does not belong to a tower yet.
     * @param newAttack
     */
    public ShootingAction(Attack newAttack) {
        this.attack = newAttack;
    }

    /*
        Makes its thing on one enemy
         */
    public void tick(Enemies currentEnemy) {

        getAttack().resetEnemiesTowerHasShoot(); // reset enemies it has shot this frame.
        if (attack.canShootAtThisFrame()) {

            if (currentEnemy.isActive() && currentEnemy.isAlive()) { // can only handle active and alive enemies
                if (canShoot() && inRange(currentEnemy) && correctTarget(currentEnemy)) {
                    shoot(currentEnemy);
                    tower.setLastTarget(currentEnemy); // why is lastTarget used? - does it only work as an iterator?

                    if (currentEnemy.isAlive()) { // ie enemy still is alive
                        tower.setCurrentTarget(currentEnemy);
                        tower.addToCurrentPlacablesWithinRangeOfThisTower(currentEnemy);
                    } else {
                        tower.setCurrentTarget(null);
                    }
                }
            }
        }
    }


    /**
     * correct target checks if the tower should remember its last target. If it should it is only allowed to change
     * target once the last target is dead or out of range.
     * @param currentEnemy
     * @return
     */
    private boolean correctTarget(Enemies currentEnemy) {
        if (tower.getLastTarget() != null || !inRange(currentEnemy)) { // if no last target skip below and return
            // true
            if (attack.isRememberOldTarget()) { //if it does not care about keeping track of old target
            // skip below
                // and return true
                if (tower.getLastTarget().isActive() || tower.getLastTarget().isAlive() ) { // if enemy is dead skip
                    // below
                    // and
                    // return true
                    if (currentEnemy.equals(tower.getLastTarget())) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }


    private void shoot(Enemies currentEnemy) {
        currentEnemy.attacked(attack.getDmg());
        tower.addKills(1);
        tower.getLevelOfTower().addExperience(currentEnemy.getExperienceToTowers());
        attack.addEnemiesTowerHasShoot();
    }

    /**
     * calculates the range between the tower and its current enemy and then sees if this is less than the towers
     * maximum range.
     * @param currentEnemy
     * @return true if it is in range, false otherwise
     */
    private boolean inRange(Enemies currentEnemy) {
        double rangeToEnemy;
        rangeToEnemy = currentEnemy.distanceTo(tower);
        if (attack.getRange() >= rangeToEnemy) {
            return true;
        } return false;
    }


    /**
     * Checks if tower canShoot = true and if it can shoot any more targets
     * @return true if it can shoot
     */
    private boolean canShoot() {
        if (canShootMoreTargets()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canShoot(Enemies currentEnemy) {
        return (canShoot() && inRange(currentEnemy) && correctTarget(currentEnemy));
    }

    private boolean canShootMoreTargets() {
        if (attack.getEnemiesTowerCanShootAtTheSameFrame() > attack.getEnemiesTowerHasShoot()) {
            return true;
        }
        return false;
    }

    @Override
    public Attack getAttack() {
        return attack;
    }

    public void setTower(Towers tower) {
        this.tower = tower;
    }

    @Override
    public boolean hasAnAttack() {
        if (this.attack != null) {
            return true;
        }
        return false;
    }
}
