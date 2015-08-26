package main.action.auraaction;

import main.enemy.Enemy;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * Remember to write something useful here!!
 */
public class RangeBuffAction extends AuraAction {

    public RangeBuffAction(double extraRange) {
        super(0, extraRange);
      }

    public void tick(Enemy enemy) {
        super.tick(enemy);
        enemy.addBuffers(this);
    }

}
