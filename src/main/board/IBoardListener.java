package main.board;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-27
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public interface IBoardListener {
    /**
     * Notifies all listners that something has happened - normal usage is to repaint with the use of this
     */
    public void boardChanged();
}
