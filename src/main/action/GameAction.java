package main.action;

import main.board.Placeable;
import main.enemies.Enemies;

/**
 * User: alete471 Date: 2012-10-12 Time: 09:47
 * Main class for all actions made by placeable objects.
 */
public class GameAction implements IGameActions {
    private double extraRange;
    private int extraDmg;
    private int decreaseSpeed; // finns här för att visa att man lätt kan lägga fler "Actions"

    public GameAction() {
        // all values are set to 0 by default.
    }

    /**
     * overrided by ShootingAction
     * @param newAttack
     */
    public GameAction(Attack newAttack) {
    //funkardethär? - jag hoppas att ShootingAction overidear denna varje gång den anropas
        // TODO Should this be something?
    }

    public void tick(Placeable obj) {
   /*     if (obj instanceof ShootableTower) {
            ((ShootableTower) obj).getAttack().addBuffers(this); // DÅLIGT MEN NÅT SÅNTHÄR SKA DE VA
        } */
        obj.addBuffers(this);
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

    public int getDecreaseSpeed() {
        return decreaseSpeed;
    }

    /**
     * returns true if the action has an attack
     * @return
     */
    public boolean hasAnAttack() {
        return false;
    }

    public Attack getAttack() {
        return null;
    }

    public void setTower(Placeable tower) {
        //funkardethär?
    }

    public boolean canShoot(Placeable currentObj) {
        return false;
    }
}
