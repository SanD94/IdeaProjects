import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sand94 on 15.10.2016.
 */
public class PointSET {

    private SET<Point2D> s;

    public PointSET() {
        s = new SET<>();
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }

    public int size() {
        return s.size();
    }

    public void insert(Point2D p) {
        nullCheck(p);
        if (s.contains(p)) return;
        s.add(p);
    }

    public boolean contains(Point2D p) {
        nullCheck(p);
        return s.contains(p);
    }

    public void draw() {
        for (Point2D p : s)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        nullCheck(rect);
        List<Point2D> l = new ArrayList<>();
        for (Point2D p : s) {
            if (rect.contains(p))
                l.add(p);
        }
        return l;
    }

    public Point2D nearest(Point2D p) {
        double mn = 3;
        Point2D nearest = null;
        for (Point2D q : s) {
            double dist = q.distanceSquaredTo(p);
            if (dist < mn) {
                mn = dist;
                nearest = q;
            }
        }
        return nearest;
    }


    private void nullCheck(Object o) {
        if (o == null)
            throw new NullPointerException();
    }



}
