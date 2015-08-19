package main.position;

import main.board.GlobalPositioning;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(java.awt.Point mousePosition) {
        this.x = (int) mousePosition.getX();
        this.y = (int) mousePosition.getY();
    }

    /**
     * takes a normal point and transforms it to a pixel position
     * @return a Point in pixelPosition
     */
    public Point getPixelPos() {
        return new Point(GlobalPositioning.getXPixel(x), GlobalPositioning.getYPixel(y));
    }

    public void convertToPixelPos() {
        this.x = GlobalPositioning.getXPixel(x);
        this.y = GlobalPositioning.getYPixel(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override public int hashCode() {
	int result = x;
	result = 31 * result + y;
	return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    @Override
    public String toString() {
      return ("(" + x + ", " + y + " )" );
    }

    public void add(Point pixelposition) {
        this.x += pixelposition.x;
        this.y += pixelposition.y;
    }

    public void subtract(Point pixelposition) {
        this.x -= pixelposition.x;
        this.y -= pixelposition.y;
    }

    public void subtractX(int changeInX) {
        this.x -= changeInX;
    }

    public void subtractY(int changeInY) {
        this.y -= changeInY;
    }

    public boolean isEmpty() {
        if (x == 0 && y == 0) return true;
        return false;
    }
}

