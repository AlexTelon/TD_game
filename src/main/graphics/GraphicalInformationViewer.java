package main.graphics;

import main.tower.shootingtowers.ShootableTower;
import main.tower.Tower;
import main.tower.Tower.TowerInformation;
import main.action.GameAction;
import main.board.Board;
import main.board.IBoardListener;
import main.enemy.Enemy;
import main.enemy.EnemyWaves;
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
@SuppressWarnings("ReuseOfLocalVariable")
public class GraphicalInformationViewer extends JComponent implements IBoardListener {
    private Board board;
    private Placeable currentObject = null;
    private EnemyWaves enemyGroups;
    private int smallSpacing = 20;


    public GraphicalInformationViewer( Board board) {
        this.board = board;
        enemyGroups = board.getEnemyWaves();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth(), preferredHeight());
    }

    private int preferredHeight() {
        return Board.getSquareHeight() * Board.getHeight();
    }
    private int preferredWidth() {  return 300; }

    @Override
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
    private void paintInfoForCurrentEnemy(Graphics2D g2, Enemy currentEnemy) {
        int x = 80;
        int y = 15;
        g2.setColor(Color.BLACK);
        g2.drawString("Information about current Enemy", 0, 15);

        y += smallSpacing;
        g2.drawString("Hitpoints", 0, y);

        g2.drawString(currentEnemy.getHealthText(), x, y);
        y += smallSpacing;
        g2.drawString("Dmg To Base",0, y);

        g2.drawString(stringConverter(currentEnemy.getDmgToBase()), x, y);
        y += smallSpacing;
        g2.drawString("Speed",0, y);

        g2.drawString(stringConverter(currentEnemy.getPixelSpeed()), x, y);
        y += smallSpacing;
        g2.drawString("Gold", 0, y);

        g2.drawString(stringConverter(currentEnemy.getGold()), x, y);
    }

    private void paintInfoForCurrentObj(Graphics2D g2, Placeable obj) {
        if (currentObject instanceof Enemy) {
            paintInfoForCurrentEnemy(g2, (Enemy) obj);
        } else if (currentObject instanceof ShootableTower) {
            paintInfoForCurrentTower(g2, (ShootableTower) currentObject);
        }
    }

    private void paintInfo(Graphics2D g2) {
        int spacingBeforeTowerText = 85;
        g2.setColor(Color.BLACK);
        int x = 10;
        int y = 190;
        g2.drawString("Current WaveNr: " + enemyGroups.getCurrentGroupNr() + "    Time to next Wave " +
                stringConverter((int) board.getCountdownToNextWave()), x, y);
        y += smallSpacing;
        g2.setColor(Color.BLACK);
        g2.drawString("main/tower", x, y);
        x += spacingBeforeTowerText;
        for (Tower currentTower : board.getAllTowers()) {
            g2.drawString(currentTower.getPrice() + ", ", x, y);
            x += smallSpacing;
        }
        y += smallSpacing;
        g2.drawString("Gold: "+ stringConverter(board.getPlayer().getGold()), 10, y);
        y += smallSpacing;
        g2.drawString("Lives: "+ stringConverter(board.getPlayer().getLives()), 10, y);


    }

    @SuppressWarnings("ReuseOfLocalVariable") private void paintInfoForCurrentTower(Graphics2D g2, Tower tower) {
        int x = 80;
        int y = 15;
        g2.setColor(Color.BLACK);
        g2.drawString("Information about current tower", 0, 15);

        y += smallSpacing;
        g2.drawString("Type", 0, y);
        g2.drawString(tower.getNameText(), x, y);

        y += smallSpacing;
        g2.drawString("Level",0, y);
        g2.drawString(stringConverter(tower.getLevelOfTower().getLevel()), x, y);

        y += smallSpacing;
        g2.drawString("Exp",0, y);
        g2.drawString(stringConverter(tower.getLevelOfTower().getExp()), x, y);

        y += smallSpacing;
        g2.drawString("MultiShoot? " + stringConverter(tower.getTowerInformation(TowerInformation.ENEMIESCANSHOOTSAMETIME)), 0,
                y);

        y += smallSpacing;
        g2.drawString("NrOfTargets:  " + stringConverter(tower.getPlacablesWithinRangeOfThisTower().size()), 0, y);

        y += smallSpacing;
        g2.drawString("Rate of Fire: (rounded)  " +  stringConverter(tower.getTowerInformation(TowerInformation.RATEOFFIRE)), 0, y);

        y += smallSpacing;

        /* This and all other cases of "Reuse of local variable" is OK for temporary variables in my mind. Several individual
         variables with good naming would be better thouogh, but still I think this is ok for placing stuff in a GUI like this*/
        x = smallSpacing;
	if (!tower.getBuffers().isEmpty()) {
	    g2.drawString("Buffers:  ", 0, y);
            int spacingBetweenBuffers = 55;
	    for (GameAction actions : tower.getBuffers()) {
		x += spacingBetweenBuffers;
		g2.drawString(String.valueOf(actions.getExtraDmg() + " " + actions.getExtraRange()), x, y);
	    }
	} else System.out.println("No targets");


	y += smallSpacing;
        g2.drawString("DPS  " + tower.getTowerInformation(TowerInformation.DPS) + "    Dmg  " + stringConverter(tower
                .getTowerInformation(TowerInformation.DMG)) + " ( " +"+ " + stringConverter(tower.getTowerInformation(TowerInformation.EXTRA_DMG)) + ")" , 0, y);

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
