package main.action.auraActions;

import main.Tower.Tower;
import main.board.Placeable;

/**
 * User: alete471 Date: 2012-10-08 Time: 14:27
 * Remember to write something useful here!!
 */
public class DmgBuffAction extends AuraAction {

    public DmgBuffAction(int extraDmg) {
        super(extraDmg, 0);
     }

    @Override
    public void tick(Placeable obj) {
        super.tick(obj);
    }

    @Override
    public void tick(Tower tower) {
        tower.addBuffers(this);
    }


}
