package main.graphics;

import main.tower.Tower;
import main.tower.TowerMaker;
import main.board.Board;
import main.board.IBoardListener;
import main.enemy.Enemy;
import main.position.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-27
 * Time: 18:07
 * Main class for GUI, handels what is shown and also placing and selling towers.
 */
public class GameFrame extends JFrame implements IBoardListener, MouseListener {
    private final JMenu menu = new JMenu("Menu");
    private Board board;
    private GraphicalViewer graphicalViewer;
    private GraphicalInformationViewer graphicalInformationViewer;
    private Point lastClickedPosition;
    private TowerMaker towerFactory = new TowerMaker();

    public GameFrame(Board board) throws HeadlessException {
        super("SmartTD 0.1");
	this.board = board;
        this.graphicalViewer = new GraphicalViewer(board);
        this.graphicalInformationViewer = new GraphicalInformationViewer(board);

        /* this is the left side information,
            not very interesting in a final verision but for someone examining the program we felt it could be left to
            show the priorityMap and how it works.*/
        GraphicalIDebugViewer graphicalIDebugViewer = new GraphicalIDebugViewer(board);
        this.graphicalViewer.addMouseListener(this);


        this.add(graphicalIDebugViewer, BorderLayout.WEST);
        this.add(graphicalViewer, BorderLayout.CENTER);
        this.add(graphicalInformationViewer, BorderLayout.EAST);



        menu.add(new JMenuItem(closeAction));
        final JMenuBar bar = new JMenuBar();
        bar.add(menu);

        final JPanel buttons = new JPanel();
        buttons.add(new JButton(buildTowerTypeAAction));
        buttons.add(new JButton(buildTowerTypeBAction));
        buttons.add(new JButton(sellTowerAction));
        this.add(buttons);

        this.setJMenuBar(bar);

        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);
    }

    /**
     * Gives information about the object that the player clicks on. The player must FIRST click on a grip position
     * and then choose what to do on/with that grid.
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point mouseGridPosition = board.getPosOnGrid(new Point(e.getPoint()));


        for (Enemy currentEnemy : board.getAllEnemiesInCurrentWave()) {
            if( currentEnemy.isWithinObject(new main.position.Point(e.getPoint())) && currentEnemy.isAlive()) {
                graphicalInformationViewer.currentObject(currentEnemy);
                graphicalViewer.higlight(currentEnemy);
                return;
            }
        }

        // this part handels where to build towers
        for (Tower currentTower : board.getAllTowers()) {
            if (currentTower.getPosition().getX() == mouseGridPosition.getX() &&
                    currentTower.getPosition().getY() == mouseGridPosition.getY() ) {
                graphicalInformationViewer.currentObject(currentTower);
                graphicalViewer.higlight(currentTower);
                lastClickedPosition = mouseGridPosition;
                return;
            }

        }
        graphicalViewer.higlightPoint(mouseGridPosition);
        lastClickedPosition = mouseGridPosition;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    Action buildTowerTypeAAction = new AbstractAction("Build tower A") {
        {
            putValue(SHORT_DESCRIPTION, "Put in how much Gold they cost later...");
        }
        public void actionPerformed(ActionEvent evt) {
            towerFactory.makeTower(board, 'A', lastClickedPosition);
        }
    };

    Action buildTowerTypeBAction = new AbstractAction("Build tower B") {
        {
            putValue(SHORT_DESCRIPTION, "Put in how much Gold they cost later...");
        }
        public void actionPerformed(ActionEvent evt) {
            towerFactory.makeTower(board, 'B', lastClickedPosition);
        }
    };

    Action sellTowerAction = new AbstractAction("Sell") {
        {
            putValue(SHORT_DESCRIPTION, "Put in how much Gold you get back later...");
        }
        public void actionPerformed(ActionEvent evt) {
            for (Tower currentTower : board.getAllTowers()) {
                if (currentTower.getPosition().getX() == lastClickedPosition.getX() &&
                        currentTower.getPosition().getY() == lastClickedPosition.getY() ) {
                    board.sellTower(currentTower);
                    return;
                }
            }
            System.out.println("No tower at that position");
        }
    };

    Action closeAction = new AbstractAction("Close !") {
        {
            putValue(SHORT_DESCRIPTION, "Closes the game");
        }
        public void actionPerformed(ActionEvent evt) {
            System.out.println("close down the game??");
            int answer = JOptionPane.showConfirmDialog
                    (menu, "Do you really want to hurt me, do you really want to make me cry?", "Quit?",
                            JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    };

    @Override
    public void boardChanged() {
        repaint();
    }

}
