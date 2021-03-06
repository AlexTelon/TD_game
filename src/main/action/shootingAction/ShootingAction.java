package main.action.shootingaction;

import main.action.AttackData;
import main.tower.Tower;
import main.action.GameAction;
import main.board.Placeable;
import main.enemy.Enemy;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * This ONLY and action - in other words this only handels HOW things are done and when. It should NOT contain any
 * data itself. The Data is attackData.
 */
public class ShootingAction extends GameAction {
    private Tower tower = null;
    private AttackData attackData;

    /**
     * Constructor to make a ShootingAction that does not belong to a tower yet.
     * @param attackData the attackInfo class
     */
    public ShootingAction(AttackData attackData) {
        this.attackData = attackData;
    }

    /*
     * Makes its thing on one enemy
     */
    public void tick(Enemy enemy) {
        super.tick(enemy);
        attackData.resetEnemiesTowerHasShoot(); // reset enemies it has shot this frame.
        if (attackData.canShootAtThisFrame()) {

            if (enemy.isActive() && enemy.isAlive()) { // can only handle active and alive enemies
                if (canShoot() && isInRange(enemy) && isCorrectTarget(enemy)) {
                    shoot(enemy);

                    if (enemy.isAlive()) { // ie enemy still is alive
                        tower.addToCurrentTargets(enemy);
                        tower.addToCurrentPlaceablesInRangeOfThisTower(enemy);
                    } else {
                        tower.removeFromCurrentTargets(enemy);
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
            if (attackData.isRememberOldTarget()) { //if it does not care about keeping track of old target
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
        currentEnemy.attacked(attackData.getDmg());
        attackData.addEnemiesTowerHasShoot();

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
        if (attackData.getRange() >= rangeToTarget) {
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
    @SuppressWarnings("RefusedBequest") // intentional as super always returns false
    public boolean canShoot(Placeable obj) {
        if (!obj.isImortal()) {
            return (canShoot() && isInRange(obj) && isCorrectTarget(obj));
        }
        return false;
    }

    private boolean canShootMoreTargets() {
        if (attackData.getEnemiesTowerCanShootAtTheSameFrame() > attackData.getEnemiesTowerHasShoot()) {
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("RefusedBequest") // Parent class does not have an attackData and hence always returns null
    public AttackData getAttackData() {
        return attackData;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    @Override
    @SuppressWarnings("RefusedBequest") // intentional as super always returns false
    public boolean hasAnAttack() {
        return (this.attackData != null);
    }
    @Override
    public void addBuffers(GameAction gameAction) {
        super.addBuffers(gameAction);
        this.attackData.addBuffers(gameAction);
    }

}
