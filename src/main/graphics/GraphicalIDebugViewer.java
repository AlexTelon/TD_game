package main.graphics;

import main.board.Board;
import main.board.GlobalPositioning;
import main.board.IBoardListener;

import javax.swing.*;
import java.awt.*;

/**
 * This will be the right side of the game which holds information and all buttons for different things
 *
 * A better verision would be for this to NOT implement boardlistener since this only needs to be
 * updated each time user klicks something, not each frame.
 */
public class GraphicalIDebugViewer extends JComponent implements IBoardListener {
    private final Board board;

    public GraphicalIDebugViewer(Board board) {
        this.board = board;
    }

    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth(), preferredHeight());
    }

    private int preferredHeight() {
        return Board.getSquareHeight() * Board.getHeight();
    }
    private int preferredWidth() {
        return Board.getSquareWidth() * Board.getWidth();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        paintInfoForAllEnemies(g2);
    }

    /**
     * paintInfoForAllEnemies is only for debugging, it gives information about all kinds of things usefull while
     * working with the code.
     * @param  g2  a graphics2D objecv
     */
    private void paintInfoForAllEnemies(Graphics2D g2) {

        for (int y = 0; y < Board.getHeight(); y++ ) {
            for (int x = 0; x < Board.getWidth(); x++) {
                if (!board.getPriorityMap().isEmpty(x,y)) {
                    g2.setColor(Color.BLACK);
                    g2.drawString(stringConverter(board.getPriority(x, y)), GlobalPositioning.getXPixel(x),
                            GlobalPositioning.getYPixel(y)+10);
                } else {
                    g2.setColor(Color.GREEN);
                    g2.drawString("0", GlobalPositioning.getXPixel(x), GlobalPositioning.getYPixel(y)+10);
                }

            }
        }
    }

    private String stringConverter(int info) {
        return String.valueOf(info);
    }

    @Override
    public void boardChanged() {
        repaint();
    }

}
