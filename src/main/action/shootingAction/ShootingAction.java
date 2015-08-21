package main.action.shootingaction;

import main.tower.Tower;
import main.action.Attack;
import main.action.GameAction;
import main.board.Placeable;
import main.enemy.Enemy;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * This ONLY and action - in other words this only handels HOW things are done and when. It should NOT contain any
 * data itself. The Data is in tower and in attack
 */
public class ShootingAction extends GameAction {
    private Tower tower = null;
    private Attack attack;

    /**
     * Constructor to make a ShootingAction that does not belong to a tower yet.
     * @param attack the attackInfo class
     */
    public ShootingAction(Attack attack) {
        this.attack = attack;
    }

    /*
     * Makes its thing on one enemy
     */
    public void tick(Enemy enemy) {
        attack.resetEnemiesTowerHasShoot(); // reset enemies it has shot this frame.
        if (attack.canShootAtThisFrame()) {

            if (enemy.isActive() && enemy.isAlive()) { // can only handle active and alive enemies
                if (canShoot() && isInRange(enemy) && isCorrectTarget(enemy)) {
                    shoot(enemy);
                    tower.setLastTarget(enemy); // why is lastTarget used? - does it only work as an iterator?

                    if (enemy.isAlive()) { // ie enemy still is alive
                        tower.setCurrentTarget( enemy);
                        tower.addToCurrentPlaceablesInRangeOfThisTower(enemy);
                    } else {
                        tower.setCurrentTarget(null);
                    }
                }
            }
        }
    }


    /**
     * Correct target checks if the tower should remember its last target. If it should it is only allowed to change
     * target once the last target is dead or out of range.
     * @param currentEnemy enemy target.
     * @return true if it is the correct target
     */
    private boolean isCorrectTarget(Placeable currentEnemy) {
        if (tower.getLastTarget() != null || !isInRange(currentEnemy)) { // if no last target skip below and return
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


    private void shoot(Enemy currentEnemy) {
        currentEnemy.attacked(attack.getDmg());
        attack.addEnemiesTowerHasShoot();

        if (!currentEnemy.isAlive()) { // enemy died
            tower.getPlayer().addGold(currentEnemy.getGold());
            tower.addKills(1);
            tower.getLevelOfTower().addExperience(currentEnemy.getExperienceToTowers());
        }
    }

    /**
     * Calculates the range between the tower and its current target and then sees if this is less than the towers
     * maximum range.
     * @return true if it is in range, false otherwise
     */
    private boolean isInRange(Placeable currentTarget) {
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
    public boolean canShoot(Placeable obj) {
        if (!obj.isImortal()) {
            return (canShoot() && isInRange(obj) && isCorrectTarget(obj));
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

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    @Override
    public boolean hasAnAttack() {
        return (this.attack != null);
    }
    @Override
    public void addBuffers(GameAction gameAction) {
        this.attack.addBuffers(gameAction);
    }

}
