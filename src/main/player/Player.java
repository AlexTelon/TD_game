package main.player;

/**
 * @author Alex Telon
 * This is the player, all objects that the player can place must have variable for player,
 * this way things like gold and life is easy to change.
 */
public class Player {
    private int gold = 40;
    private int lives = 100;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void subtractLives(int dmg) {
        lives -= dmg;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public void subtractGold(int goldloss) {
        gold -= goldloss;
        if ( gold < 0)
            gold = 0;
    }


}
