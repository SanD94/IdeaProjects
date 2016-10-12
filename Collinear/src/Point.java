import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Created by sand94 on 24.09.2016.
 */
public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point point) {
        StdDraw.line(this.x, this.y, point.x, point.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point point) {
        if (this.y == point.y)
            return this.x - point.x;
        return this.y - point.y;
    }

    public double slopeTo(Point point) {
        if (this.x == point.x) {
            if (this.y != point.y) return Double.POSITIVE_INFINITY;
            else return Double.NEGATIVE_INFINITY;
        } else if (this.y == point.y)
            return 0;
        return (this.y - point.y) * 1.0 / (this.x - point.x);
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                double slope0 = slopeTo(point);
                double slope1 = slopeTo(t1);
                if (slope0 < slope1) return -1;
                else if (slope0 > slope1) return 1;
                return 0;
            }
        };
    }
}
