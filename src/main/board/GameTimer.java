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
public class GameTimer {
    private static final int REFRESH_RATE = 50;
    private final Board board = new Board(REFRESH_RATE);


    public void runGame() {
        board.setFramerate(REFRESH_RATE);
        board.addBoardListener(new GameFrame(board));
        board.addBoardListener(new GraphicalViewer(board));

        Timer timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                board.tick();
            }
        }, 0, REFRESH_RATE);
    }

    public static void main(String[] args) {
        GameTimer game = new GameTimer();
        game.runGame();
    }
}


