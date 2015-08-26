package main.action;

import main.tower.Tower;
import main.board.Placeable;
import main.enemy.Enemy;

/**
 * User: alete471 Date: 2012-10-12 Time: 09:47
 * Main class for all actions made by placeable objects.
 */
public class GameAction {
    private double extraRange;
    private int extraDmg;

    public GameAction() {
        // all values are set to 0 by default.
    }

    public void tick(Tower tower) {
        tower.addBuffers(this);
    }

    public void tick(Enemy enemy) {
    }

    public GameAction(int extraDmg, double extraRange) {
        this.extraDmg = extraDmg;
        this.extraRange = extraRange;
    }

    public int getExtraDmg() {
        return extraDmg;
    }

    public double getExtraRange() {
        return extraRange;
    }

    /**
     * @return true if the action has an attack
     */
    public boolean hasAnAttack() {
        return false;
    }

    public AttackData getAttackData() {  return null; }


    public boolean canShoot(Placeable obj) {
        // this is overrided by shooting towers
        return false;
    }

    public void addBuffers(GameAction gameAction) {
    }

}
