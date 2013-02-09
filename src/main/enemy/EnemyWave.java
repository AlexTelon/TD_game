package main.enemy;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: alete471 Date: 2012-10-16 Time: 17:40
 This class creates several enemy and puts them into this class as to represent a wave. All enemy in a wave have
 a groupActivationTime.
 */

public class EnemyWave implements Iterable<Enemies> {
    private ArrayList<Enemies> enemiesInCurrentWave = new ArrayList<Enemies>();
    private double groupActivationTime;

    /**
     * Send in an enemyPrototype, the nr of enemy and the activationtime of the group and a wave is initiazed
     * @param enemyPrototype
     * @param nrOfEnemies
     * @param activationTime the activationTime of the enemyPrototype relative to the groups activationtime
     * @param groupActivationTime the groupActivationTime determines after how many ms this group should appear on
     *                            the board after the previous group has died. Or if the group is the first, how long
     *                            before the first group of enemy start to come.
     */
    public EnemyWave(Enemies enemyPrototype, int nrOfEnemies, double groupActivationTime, double activationTime) {
        double totalActivationTime;
        for (int i = 0; i < nrOfEnemies; i++) {
            totalActivationTime = i*activationTime;
            enemiesInCurrentWave.add(i,new Enemies(enemyPrototype, totalActivationTime));
        }
        this.groupActivationTime = groupActivationTime;
            }

    public double getGroupActivationTime() {
        return groupActivationTime;
    }

    @Override
    public Iterator<Enemies> iterator() {
        return enemiesInCurrentWave.listIterator();
    }
}
