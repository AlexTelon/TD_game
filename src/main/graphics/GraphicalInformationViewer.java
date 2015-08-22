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
    private static final int SMALL_SPACING = 20;
    private static final int PREFERRED_WITH = 300;
    private static final int TOP_MARGIN_X = 80; // margin to the top of the window for text information
    private static final int MARGIN_Y = 15;
    private static final int MARGIN_X = 10;
    private static final int WAVE_INFO_SPACE_TO_TOP = 190;

    public GraphicalInformationViewer( Board board) {
        this.board = board;
        enemyGroups = board.getEnemyWaves();
    }

    @SuppressWarnings("RefusedBequest") @Override // doing this on purpose as we want to redefine the preferred size.
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth(), preferredHeight());
    }

    private int preferredHeight() {
        return Board.getSquareHeight() * Board.getHeight();
    }
    private int preferredWidth() {  return PREFERRED_WITH; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        paintInfoForCurrentObj(g2, currentObject);
        paintInfo(g2);

    }

    /**
     * paintInfoForCurrentEnemy is only for debugging, it gives information about all kinds of things usefull while
     * working with the code.
     * @param  g2  a graphics2D objecv
     */
    private void paintInfoForCurrentEnemy(Graphics2D g2, Enemy currentEnemy) {
        int x = TOP_MARGIN_X;
        int y = MARGIN_Y;
        g2.setColor(Color.BLACK);
        g2.drawString("Information about current Enemy", 0, y);

        y += SMALL_SPACING;
        g2.drawString("Hitpoints", 0, y);

        g2.drawString(currentEnemy.getHealthText(), x, y);
        y += SMALL_SPACING;
        g2.drawString("Dmg To Base",0, y);

        g2.drawString(stringConverter(currentEnemy.getDmgToBase()), x, y);
        y += SMALL_SPACING;
        g2.drawString("Speed",0, y);

        g2.drawString(stringConverter(currentEnemy.getPixelSpeed()), x, y);
        y += SMALL_SPACING;
        g2.drawString("Gold", 0, y);

        g2.drawString(stringConverter(currentEnemy.getGold()), x, y);
    }

    private void paintInfoForCurrentObj(Graphics2D g2, Placeable obj) {
        if (obj != null && obj.isEnemy()) {
            paintInfoForCurrentEnemy(g2, (Enemy) obj);
        } else if (currentObject instanceof ShootableTower) {
            paintInfoForCurrentTower(g2, (ShootableTower) currentObject);
        }
    }

    private void paintInfo(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        int x = MARGIN_X;
        int y = WAVE_INFO_SPACE_TO_TOP;
        g2.drawString("Current WaveNr: " + enemyGroups.getCurrentGroupNr() + "    Time to next Wave " +
                stringConverter((int) board.getCountdownToNextWave()), x, y);
        y += SMALL_SPACING;
        g2.setColor(Color.BLACK);
        g2.drawString("main/tower", x, y);
        x += TOP_MARGIN_X;
        for (Tower currentTower : board.getAllTowers()) {
            g2.drawString(currentTower.getPrice() + ", ", x, y);
            x += SMALL_SPACING;
        }
        y += SMALL_SPACING;
        g2.drawString("Gold: "+ stringConverter(board.getPlayer().getGold()), MARGIN_X, y);
        y += SMALL_SPACING;
        g2.drawString("Lives: "+ stringConverter(board.getPlayer().getLives()), MARGIN_X, y);


    }

    @SuppressWarnings("ReuseOfLocalVariable") private void paintInfoForCurrentTower(Graphics2D g2, Tower tower) {
        int x = TOP_MARGIN_X;
        int y = MARGIN_Y;
        g2.setColor(Color.BLACK);
        g2.drawString("Information about current tower", 0, y);

        y += SMALL_SPACING;
        g2.drawString("Type", 0, y);
        g2.drawString(tower.getNameText(), x, y);

        y += SMALL_SPACING;
        g2.drawString("Level",0, y);
        g2.drawString(stringConverter(tower.getLevelOfTower().getLevel()), x, y);

        y += SMALL_SPACING;
        g2.drawString("Exp",0, y);
        g2.drawString(stringConverter(tower.getLevelOfTower().getExp()), x, y);

        y += SMALL_SPACING;
        g2.drawString("MultiShoot? " + stringConverter(tower.getTowerInformation(TowerInformation.ENEMIESCANSHOOTSAMETIME)), 0,
                y);

        y += SMALL_SPACING;
        g2.drawString("NrOfTargets:  " + stringConverter(tower.getPlacablesWithinRangeOfThisTower().size()), 0, y);

        y += SMALL_SPACING;
        g2.drawString("Rate of Fire: (rounded)  " +  stringConverter(tower.getTowerInformation(TowerInformation.RATEOFFIRE)), 0, y);

        y += SMALL_SPACING;

        /* This and all other cases of "Reuse of local variable" is OK for temporary variables in my mind. Several individual
         variables with good naming would be better thouogh, but still I think this is ok for placing stuff in a GUI like this*/
        x = SMALL_SPACING;
	if (!tower.getBuffers().isEmpty()) {
	    g2.drawString("Buffers:  ", 0, y);
            final int spaceBetweenBuffers = 55;
	    for (GameAction actions : tower.getBuffers()) {
		x += spaceBetweenBuffers;
		g2.drawString(String.valueOf(actions.getExtraDmg() + " " + actions.getExtraRange()), x, y);
	    }
	} else System.out.println("No targets");


	y += SMALL_SPACING;
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
     */
    public void currentObject(Placeable obj) {
        this.currentObject = obj;
    }

}
