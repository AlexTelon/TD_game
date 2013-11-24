package main.tower;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-26
 * Time: 19:43
 * A class that handels the level of a tower.
 */
public class LevelOfTower {
    private int level = 1;
    private int exp = 0;
    private int difficulty;

    public LevelOfTower(int difficulty) {
        this.difficulty = difficulty;
        this.level = 1;
        this.exp = 0;
    }

    // calculates new level for given tower, if there is enough exp the tower could gain several levels
    public int recalculateLevel() {
        int addedlevels = 0;
        int requiredExp = getExpRequirement(level);
        while (exp >= requiredExp) {
            exp = exp-requiredExp;
            if (exp < 0) { exp = 0; } // so that experience is never below 0
            addedlevels++;
            addLevel();
            requiredExp = getExpRequirement(level);
        }
        return addedlevels;
    }


    // A formula for the exp requirement for each tower level.
    private int getExpRequirement(int level) {
        return level*100*difficulty;
    }

    public int getExp() {
        return exp;
    }

    public void addExperience(int experience) {
        this.exp += experience;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        this.level++;
    }

}
