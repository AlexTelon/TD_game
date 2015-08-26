package main.action.auraaction;

import main.action.GameAction;
import main.enemy.Enemy;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * Remember to write something useful here!!
 */
public class AuraAction extends GameAction {

    public AuraAction(int extraDmg, double extraRange) {
        super(extraDmg, extraRange);
    }

    public void tick(Enemy enemy) {
        super.tick(enemy);
        enemy.addBuffers(this);
    }

}


