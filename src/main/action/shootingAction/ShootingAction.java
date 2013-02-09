package main.action.shootingAction;

import main.Towers.shootingTowers.ShootableTowers;
import main.action.GameActions;
import main.enemies.Enemies;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * This ONLY and action - in other words this only handels HOW things are done and when. It should NOT contain any
 * data itself. The Data is in tower and in attack
 */
public class ShootingAction extends GameActions {
    private ShootableTowers tower;

    public ShootingAction(ShootableTowers tower) {
        this.tower = tower;
    }

    public void tick(Enemies currentEnemy) {
        if (currentEnemy.isActive() && currentEnemy.isAlive()) { // can only handle active and alive enemies
            if (canShoot() && inRange(currentEnemy) && correctTarget(currentEnemy)) {
                shoot(currentEnemy);
                tower.setLastTarget(currentEnemy);

                if (currentEnemy.isAlive()) { // ie enemy still is alive
                    tower.setCurrentTarget(currentEnemy);
                    tower.addToCurrentTargets(currentEnemy);
                } else {
                    tower.setCurrentTarget(null);
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
            if (tower.getAttack().isRememberOldTarget()) { //if it does not care about keeping track of old target
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
        currentEnemy.attacked(tower.getAttack().getDmg());
        tower.addKills(1);
        tower.getLevelOfTower().addExperience(currentEnemy.getExperienceToTowers());
        tower.getAttack().addEnemiesTowerHasShoot();
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
        if (tower.getAttack().getRange() >= rangeToEnemy) {
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

    public boolean canShoot(Enemies currentEnemy) {
        return (canShoot() && inRange(currentEnemy) && correctTarget(currentEnemy));
    }

    private boolean canShootMoreTargets() {
        if (tower.getAttack().getEnemiesTowerCanShootAtTheSameFrame() > tower.getAttack().getEnemiesTowerHasShoot()) {
            return true;
        }
        return false;
    }


}
