package main.board;

import main.graphics.GameFrame;
import main.graphics.GraphicalViewer;

import java.util.TimerTask;
import java.util.Timer;


/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:33
 * This class in the main class and calls tick for Board which the runs the rest of the program.
 */
public final class GameTimer {
    private static final int REFRESH_RATE = 50;
    private static final Board BOARD = new Board(REFRESH_RATE);


    public static void runGame() {
        BOARD.setFramerate(REFRESH_RATE);
        BOARD.addBoardListener(new GameFrame(BOARD));
        BOARD.addBoardListener(new GraphicalViewer(BOARD));

        Timer timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                BOARD.tick();
            }
        }, 0, REFRESH_RATE);
    }

    public static void main(String[] args) {
        runGame();
    }
}


