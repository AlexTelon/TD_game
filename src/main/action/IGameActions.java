package main.action;

import main.board.Placeable;

/**
 * User: alete471 Date: 2012-10-18 Time: 15:14
 * Interface for all GameAction
 */
public interface IGameActions {

    public void tick(Placeable obj);

    public int getExtraDmg();

    public double getExtraRange();

    public int getDecreaseSpeed();

}

