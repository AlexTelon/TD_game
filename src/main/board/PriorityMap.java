package main.board;

import main.position.Point;

/**
 * priorityMap is 2D array containing integers. Each integer represents a priority on a certain grid position in the
 * board. Higher values have higher priority. A lower value cannot be placed on a higher.
 */
public class PriorityMap {
    // Initzialize by doing nothing which makes all integers 0 in this 'matrix'
    private int[][] priorityMap = new int[Board.getHeight()][Board.getWidth()];

    public int getPriorityMap(int x, int y) {
        return priorityMap[y][x];
    }

    public void setPriorityMap(int x, int y, int newValue) {
        priorityMap[y][x] = newValue;
    }

    public void resetPriorityMap(Point point) {
        int x = point.getX();
        int y = point.getY();
        priorityMap[y][x] = 0;
    }

    public boolean isEmpty(int x, int y) {
        return (priorityMap[y][x] == 0);
    }
}
