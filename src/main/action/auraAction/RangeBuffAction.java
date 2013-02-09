package main.action.auraAction;

import main.tower.shootingTower.ShootableTower;
import main.board.Placeable;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * Remember to write something useful here!!
 */
public class RangeBuffAction extends AuraAction {

    public RangeBuffAction(double extraRange) {
        super(0, extraRange);
      }


   /*
    public void tick(Placeable obj) {
        if (obj instanceof ShootableTower) {
            ((ShootableTower) obj).getAttack().addBuffers(this);
        }
        obj.addBuffers(this);
    }
}
 */

    /**
     * Tick here could be improved upon, a better implementation would be to do this:
     * obj.addBuffer(this)
     * where addBuffer keeps track of if the obj is a shootable tower or not.
     * @param obj
     */
    public void tick(Placeable obj) {
        super.tick(obj);
        if (obj instanceof ShootableTower) {
            ((ShootableTower) obj).getAttack().addBuffers(this);
        }
    }

}