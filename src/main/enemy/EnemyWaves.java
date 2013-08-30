package main.enemy;

import main.board.Board;
import main.enemy.enemyType.DefaultEnemy;
import main.enemy.enemyType.FastEnemy;
import main.enemy.enemyType.SlowEnemy;
import main.enemy.enemyType.StrongEnemy;
import main.position.Point;

import java.util.ArrayList;

/**
 * Class that groups all enemy waves together to one large group
 */

public class EnemyWaves {
    private ArrayList<EnemyWave> allEnemyWaves = new ArrayList<EnemyWave>();
    private ArrayList<Point> path = new ArrayList<Point>();
    private EnemyPath enemyPathing;
    private final int priority = 5;

    private int currentGroupIndex = 0;
     // private ColorHandler colorHandler = ColorHandler.getInstance();


    public EnemyWaves(Board board, int difficulty, Point castlePos, Point enemyStartingPosition) {
        int nrOfEnemiesInAWave;
        int nrOfWaves = 10;
        double activationTime;
        double groupActivationTime = 1000.0;
        int y = board.getCastlePos().getY();
        int x = 0;

        enemyPathing = new EnemyPath(enemyStartingPosition, castlePos);
        path = enemyPathing.getPath();


        for (int i = 0; i < nrOfWaves; i++) {
            Enemy enemyPrototype;
            nrOfEnemiesInAWave = 10;
            activationTime = 1000.0;

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
        return allEnemyWaves.get(currentGroupIndex++);
    }

    private void addEnemyGroupToAllGroups(EnemyWave newEnemyGroup) {
        allEnemyWaves.add(newEnemyGroup);
    }


    public boolean isThereMoreGroups() {
        if (allEnemyWaves.size() > currentGroupIndex) {
            return true;
        }
        return false;
    }

    public double getNextGroupsActivationTime() {
        return allEnemyWaves.get(currentGroupIndex).getGroupActivationTime();
    }

    public int getCurrentGroupNr() {
        return currentGroupIndex;
    }

    public ArrayList<Point> getEnemyPath() {
        return path;
    }

    public int getPriority() {
        return priority;
    }
}
