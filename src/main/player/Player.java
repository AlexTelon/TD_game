package main.player;

/**
 * @author Alex Telon
 * This is the player, all objects that the player can place must have variable for player,
 * this way things like gold and life is easy to change.
 */
public class Player {
    private final static int STARTING_GOLD = 40;
    private int gold = STARTING_GOLD;
    private int lives = 100;

    public int getGold() {
        return gold;
    }

    public int getLives() { return lives; }

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
