package main.graphics;

import main.Towers.shootingTowers.ShootableTowers;
import main.Towers.Towers;
import main.action.GameActions;
import main.board.Board;
import main.board.IBoardListener;
import main.enemies.Enemies;
import main.enemies.EnemyWaves;
import main.board.Placeable;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-10-02
 * Time: 16:39
 * This class shows information about selected targets on the board.
 */

/**
 * This will be the right side of the game which holds information and all buttons for different things
 *
 * Later on this should NOT implement boardlistener since this only needs to be updated each time a user klicks
 * something, not each frame.
 */
public class GraphicalInformationViewer extends JComponent implements IBoardListener {
    private Board board;
    private Placeable currentObject;
    private EnemyWaves enemyGroups;

    public GraphicalInformationViewer( Board board) {
        this.board = board;
        enemyGroups = board.getEnemyWaves();
    }

    public Dimension getPreferredSize() {
        return new Dimension(PreferredWidth(),PreferredHeight());
    }

    private int PreferredHeight() {
        int height = Board.getSquareHeight() * Board.getHeight();
        return height;
    }
    private int PreferredWidth() {
        int width = 300;
        return width;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        paintInfoForCurrentObj(g2,currentObject);
        paintInfo(g2);

    }

    /**
     * paintInfoForCurrentEnemy is only for debugging, it gives information about all kinds of things usefull while
     * working with the code.
     * @param  g2  a graphics2D objecv
     */
    private void paintInfoForCurrentEnemy(Graphics2D g2, Enemies currentEnemy) {
        int tmpX = 80;
        int tmpY = 15;
        g2.setColor(Color.BLACK);
        g2.drawString("Information about current Enemy", 0, 15);

        tmpY += 20;
        g2.drawString("Hitpoints", 0, tmpY);

        g2.drawString(currentEnemy.getHealthText(), tmpX, tmpY);
        tmpY += 20;
        g2.drawString("Dmg To Base",0, tmpY);

        g2.drawString(stringConverter(currentEnemy.getDmgToBase()), tmpX, tmpY);
        tmpY += 20;
        g2.drawString("Speed",0, tmpY);

        g2.drawString(stringConverter(currentEnemy.getPixelSpeed()), tmpX, tmpY);
        tmpY += 20;
        g2.drawString("Gold", 0, tmpY);

        g2.drawString(stringConverter(currentEnemy.getGold()), tmpX, tmpY);
    }

    private void paintInfoForCurrentObj(Graphics2D g2, Placeable obj) {
        if (currentObject instanceof Enemies) {
            paintInfoForCurrentEnemy(g2, (Enemies) obj);
        } else if (currentObject instanceof ShootableTowers) {
            paintInfoForCurrentTower(g2, (ShootableTowers) currentObject);
        }
    }

    private void paintInfo(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        int tmpX = 10;
        int tmpY = 190;
        g2.drawString("Current WaveNr: " + enemyGroups.getCurrentGroupNr() + "    Time to next Wave " +
                stringConverter((int) board.getCountdownToNextWave()), tmpX, tmpY);
        tmpY += 30;
        g2.setColor(Color.BLACK);
        g2.drawString("main/Towers", tmpX, tmpY);
        tmpX += 85;
        for (Towers currentTower : board.getAllTowers()) {
            g2.drawString(currentTower.getPrice() + ", ", tmpX, tmpY);
            tmpX += 30;
        }
        tmpY += 30;
        g2.drawString("Gold: "+ stringConverter(board.getGold()), 10, tmpY);
        tmpY += 30;
        g2.drawString("Lives: "+ stringConverter(board.getLives()), 10, tmpY);


    }

    private void paintInfoForCurrentTower(Graphics2D g2, ShootableTowers tower) {
        int tmpX = 80;
        int tmpY = 15;
        g2.setColor(Color.BLACK);
        g2.drawString("Information about current tower", 0, 15);

        tmpY += 20;
        g2.drawString("Type", 0, tmpY);
        g2.drawString(tower.getNameText(), tmpX, tmpY);

        tmpY += 20;
        g2.drawString("Level",0, tmpY);
        g2.drawString(stringConverter(tower.getLevelOfTower().getLevel()), tmpX, tmpY);

        tmpY += 20;
        g2.drawString("Exp",0, tmpY);
        g2.drawString(stringConverter(tower.getLevelOfTower().getExp()), tmpX, tmpY);

        tmpY += 20;
        g2.drawString("MultiShoot? " + stringConverter(tower.getAttack().getEnemiesTowerCanShootAtTheSameFrame()), 0,
                tmpY);

        tmpY += 20;
        g2.drawString("NrOfTargets:  " + stringConverter(tower.getCurrentTargets().size()), 0, tmpY);

        tmpY += 20;
        g2.drawString("Rate of Fire:  " +  tower.getAttack().getRateOfFire(), 0, tmpY);

        tmpY += 20;
        tmpX = 30;
        if (tower.getBuffers().size() != 0 ) {
            if (tower.getBuffers().size() != 0 ) {
                g2.drawString("Buffers:  ", 0, tmpY);
                for (GameActions actions : tower.getBuffers()) {
                    tmpX += 55;
                    g2.drawString(String.valueOf(actions.getExtraDmg() + " " + actions.getExtraRange()), tmpX, tmpY);
                }
            } else System.out.println("No targets");
        }


        tmpY += 20;
        g2.drawString("DPS  " + String.valueOf(tower.getAttack().getDPS()) + "    Dmg  " + stringConverter(tower
                .getAttack().getDmg()) + " ( " +"+ " + stringConverter(tower.getAttack().getExtraDmg()) + ")" , 0, tmpY);

    }



    private String stringConverter(int info) {
        return String.valueOf(info);
    }

    @Override
    public void boardChanged() {
        repaint();
    }

    /**
     * Sets the current object to something so that information about this object can be shown on the screen.
     * @param obj
     */
    public void currentObject(Placeable obj) {
        this.currentObject = obj;
    }

}
