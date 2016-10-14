import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sand94 on 24.09.2016.
 */
public class FastCollinearPoints {
    private List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (Point point : points)
            if (point == null) throw new NullPointerException();
        for (Point point : points)
            for (Point point1 : points)
                if (point != point1 && point.slopeTo(point1) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();
        lineSegments = new ArrayList<>();
        Point[] sortedPoints = new Point[points.length];
        System.arraycopy(points, 0, sortedPoints, 0, points.length);

        for (Point p : points) {
            Arrays.sort(sortedPoints, p.slopeOrder());
            for (int i = 1; i < sortedPoints.length;) {
                double slope = p.slopeTo(sortedPoints[i]);
                int number = 0;
                boolean put = true;
                Point largest = p;
                while (i < sortedPoints.length && slope == p.slopeTo(sortedPoints[i])) {
                    number++;
                    if (p.compareTo(sortedPoints[i]) > 0) put = false;
                    if (largest.compareTo(sortedPoints[i]) < 0) largest = sortedPoints[i];
                    i++;
                }
                if (number < 3 || !put) continue;
                lineSegments.add(new LineSegment(p, largest));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
