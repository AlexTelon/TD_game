package main.enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: alete471 Date: 2012-10-16 Time: 17:40
 This class creates several enemies and puts them into this class as to represent a wave. All enemies in a wave have
 a groupActivationTime.
 */

public class EnemyWave implements Iterable<Enemy> {
    private List<Enemy> enemiesInCurrentWave = new ArrayList<Enemy>();
    private double groupActivationTime;

    /**
     * Send in an enemyPrototype, the nr of enemies and the activationtime of the group and a wave is initiazed
     * @param enemyPrototype
     * @param nrOfEnemies
     * @param activationTime the activationTime of the enemyPrototype relative to the groups activationtime
     * @param groupActivationTime the groupActivationTime determines after how many ms this group should appear on
     *                            the board after the previous group has died. Or if the group is the first, how long
     *                            before the first group of enemies start to come.
     */
    public EnemyWave(Enemy enemyPrototype, int nrOfEnemies, double groupActivationTime, double activationTime) {
         for (int i = 0; i < nrOfEnemies; i++) {
            double totalActivationTime = i*activationTime;
            enemiesInCurrentWave.add(i,new Enemy(enemyPrototype, totalActivationTime));
        }
        this.groupActivationTime = groupActivationTime;
            }

    public double getGroupActivationTime() {
        return groupActivationTime;
    }

    @Override
    public Iterator<Enemy> iterator() {
        return enemiesInCurrentWave.listIterator();
    }
}
