package main.action.auraActions;

import main.Towers.shootingTowers.ShootableTowers;
import main.board.Placeable;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * Remember to write something useful here!!
 */
public class DmgBuffAction extends AuraAction {

    public DmgBuffAction(int extraDmg) {
        super(extraDmg, 0);
     }


    public void tick(Placeable obj) {
        if (obj instanceof ShootableTowers) {
            ((ShootableTowers) obj).getAttack().addBuffers(this);
        }
        obj.addBuffers(this);
    }
}
