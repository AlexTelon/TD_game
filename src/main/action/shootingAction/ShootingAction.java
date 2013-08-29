package main.action.shootingAction;

import main.Tower.Tower;
import main.action.Attack;
import main.action.GameAction;
import main.board.Placeable;
import main.enemies.Enemies;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * This ONLY and action - in other words this only handels HOW things are done and when. It should NOT contain any
 * data itself. The Data is in tower and in attack
 */
public class ShootingAction extends GameAction {
    private Tower tower;
    private Attack attack;


    public ShootingAction(Tower tower, Attack attack) {
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
    @Override
    public void tick(Placeable currentEnemy) {

        getAttack().resetEnemiesTowerHasShoot(); // reset enemies it has shot this frame.
        if (attack.canShootAtThisFrame()) {

            if (((Enemies) currentEnemy).isActive() && ((Enemies) currentEnemy).isAlive()) { // can only handle active and alive enemies
                if (canShoot() && inRange(currentEnemy) && correctTarget(currentEnemy)) {
                    shoot(((Enemies) currentEnemy));
                    tower.setLastTarget(((Enemies) currentEnemy)); // why is lastTarget used? - does it only work as an iterator?

                    if (((Enemies) currentEnemy).isAlive()) { // ie enemy still is alive
                        tower.setCurrentTarget(((Enemies) currentEnemy));
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
    private boolean correctTarget(Placeable currentEnemy) {
        if (tower.getLastTarget() != null || !inRange(currentEnemy)) { // if no last target skip below and return
            if (attack.isRememberOldTarget()) { //if it does not care about keeping track of old target
                if (tower.getLastTarget().isActive() || tower.getLastTarget().isAlive() ) { // if enemy is dead, skip
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
     * Calculates the range between the tower and its current target and then sees if this is less than the towers
     * maximum range.
     * @param currentTarget
     * @return true if it is in range, false otherwise
     */
    private boolean inRange(Placeable currentTarget) {
        double rangeToTarget;
        rangeToTarget = currentTarget.distanceTo(tower);
        if (attack.getRange() >= rangeToTarget) {
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
    public boolean canShoot(Placeable currentEnemy) {
        if (!currentEnemy.isImortal()) {
            return (canShoot() && inRange(currentEnemy) && correctTarget(currentEnemy));
        }
        return false;
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

    @Override
    public void setTower(Tower tower) {
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
