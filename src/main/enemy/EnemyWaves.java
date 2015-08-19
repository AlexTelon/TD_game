package main.enemy;

import main.board.Board;
import main.enemy.enemytype.*;
import main.position.Point;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that groups all enemy waves together to one large group
 */

public class EnemyWaves {
    private List<EnemyWave> allEnemyWaves = new ArrayList<EnemyWave>();
    private List<Point> path = new ArrayList<Point>();
    private static final int PRIORITY = 5;

    private int currentGroupNr = 0;
     // private ColorHandlerSingleton colorHandler = ColorHandlerSingleton.getInstance();


    public EnemyWaves(Board board,Point castlePos, Point enemyStartingPosition) {
        int nrOfWaves = 10;
        double groupActivationTime = 1000.0;
        int y = board.getCastlePos().getY();
        int x = 0;

        EnemyPath enemyPathing = new EnemyPath(enemyStartingPosition, castlePos);
        path = enemyPathing.getPath();


        for (int i = 0; i < nrOfWaves; i++) {
            Enemy enemyPrototype;
            int nrOfEnemiesInAWave = 10;
            double activationTime = 1000.0;

            switch (i) {
                case 0:
                    enemyPrototype = new FastEnemy(board, x, y);
                    nrOfEnemiesInAWave += 3;
                    break;

                case 2: enemyPrototype = new SlowEnemy(board, x, y);
                    nrOfEnemiesInAWave += 10;
                 //   activationTime += 100;
                    break;

                case 1: enemyPrototype = new StrongEnemy(board, x, y);
                    nrOfEnemiesInAWave -= 5;
                    activationTime *= 2;
                    break;

                default: enemyPrototype = new DefaultEnemy(board, 0, board.getCastlePos().getY());
                    break;
            }


            EnemyWave enemyWave = new EnemyWave(enemyPrototype, nrOfEnemiesInAWave, groupActivationTime, activationTime);
            addEnemyGroupToAllGroups(enemyWave);
        }
    }


    /**
     * OBS does not check if there is a next group to get!
     * @return ArrayList<main.enemy.Enemy>
     */
    public EnemyWave getNextEnemyWave() {
	int ret = currentGroupNr;
	currentGroupNr++;
        return allEnemyWaves.get(ret);
    }

    private void addEnemyGroupToAllGroups(EnemyWave newEnemyGroup) {
        allEnemyWaves.add(newEnemyGroup);
    }


    public boolean isThereMoreGroups() {
        if (allEnemyWaves.size() > currentGroupNr) {
            return true;
        }
        return false;
    }

    public double getNextGroupsActivationTime() {
        return allEnemyWaves.get(currentGroupNr).getGroupActivationTime();
    }

    public int getCurrentGroupNr() {
        return currentGroupNr;
    }

    public Iterable<Point> getEnemyPath() {
        return path;
    }

    public int getPriority() {
        return PRIORITY;
    }
}
