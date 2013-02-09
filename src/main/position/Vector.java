package main.position;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created with IntelliJ IDEA.
 * User: alete471
 * Date: 2012-09-25
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class Vector {
    private int x, y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;

        Vector point = (Vector) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    public double length() {
        double x = getX();
        double y = getY();
        double range = sqrt((pow(x,2)+pow(y,2)));
        return range;
    }

}
