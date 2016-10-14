import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sand94 on 24.09.2016.
 */
public class BruteCollinearPoints {
    private List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (Point p : points)
            if (p == null) throw new NullPointerException();
        for (Point p : points)
            for (Point q : points)
                if (p != q && p.slopeTo(q) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();

        lineSegments = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        double slope0 = p.slopeTo(q);
                        double slope1 = p.slopeTo(r);
                        double slope2 = p.slopeTo(s);
                        if (slope0 == slope1 && slope1 == slope2) {
                            Point[] points1 = new Point[] {p, q, r, s};
                            Arrays.sort(points1);
                            lineSegments.add(new LineSegment(points1[0], points1[3]));
                        }
                    }
                }
            }
        }
    }


    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
