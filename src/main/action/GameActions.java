package main.action;

import main.board.Placeable;

/**
 * User: alete471 Date: 2012-10-12 Time: 09:47
 * Main class for all actions made by placeable objects.
 */
public class  GameActions implements IGameActions {
    private double extraRange;
    private int extraDmg;
    private int decreaseSpeed; // finns här för att visa att man lätt kan lägga fler "Actions"

    public GameActions() {
        // all values are set to 0 by default.
    }

    public void tick(Placeable obj) {
   /*     if (obj instanceof ShootableTowers) {
            ((ShootableTowers) obj).getAttack().addBuffers(this); // DÅLIGT MEN NÅT SÅNTHÄR SKA DE VA
        } */
        obj.addBuffers(this);
    }

    public GameActions(int extraDmg, double extraRange) {
        this.extraDmg = extraDmg;
        this.extraRange = extraRange;
    }

    public int getExtraDmg() {
        return extraDmg;
    }

    public double getExtraRange() {
        return extraRange;
    }

    public int getDecreaseSpeed() {
        return decreaseSpeed;
    }

}
