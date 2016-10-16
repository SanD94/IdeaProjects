import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sand94 on 15.10.2016.
 */
public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }


    public KdTree() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        nullCheck(p);
        if (!contains(p)) {
            size++;
            root = verticalInsert(root, new RectHV(0, 0, 1, 1), p);
        }
    }

    public boolean contains(Point2D p) {
        nullCheck(p);
        return verticalGet(root, p) != null;
    }

    public void draw() {
        verticalDraw(root);
    }

    public Iterable<Point2D> range(RectHV rect) {
        nullCheck(rect);
        List<Point2D> l = new ArrayList<>();
        search(root, rect, l);
        return l;
    }

    public Point2D nearest(Point2D p) {
        nullCheck(p);
        return nearestSearch(root, p, null, 0);
    }


    private Point2D nearestSearch(Node x, Point2D p, Point2D nearest, double distSq) {
        if (x == null) return nearest;
        if (nearest == null) {
            nearest = x.p;
            distSq = p.distanceSquaredTo(nearest);
        }
        if (x.rect.distanceSquaredTo(p) > distSq)
            return nearest;
        if (p.distanceSquaredTo(x.p) < distSq) {
            nearest = x.p;
            distSq = p.distanceSquaredTo(nearest);
        }
        nearest = nearestSearch(x.lb, p, nearest, distSq);
        distSq = p.distanceSquaredTo(nearest);
        nearest = nearestSearch(x.rt, p, nearest, distSq);

        return nearest;
    }

    private void search(Node x, RectHV rect, List<Point2D> l) {
        if (x == null) return;
        if (!rect.intersects(x.rect)) return;
        if (rect.contains(x.p)) l.add(x.p);
        search(x.lb, rect, l);
        search(x.rt, rect, l);
    }

    private void verticalDraw(Node x) {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(x.p.x(), x.p.y());
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        horizontalDraw(x.lb);
        horizontalDraw(x.rt);
    }

    private void horizontalDraw(Node x) {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(x.p.x(), x.p.y());
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        verticalDraw(x.lb);
        verticalDraw(x.rt);
    }

    private Point2D verticalGet(Node x, Point2D p) {
        if (x == null) return null;
        int cmp = Point2D.X_ORDER.compare(p, x.p);
        if (cmp < 0) return horizontalGet(x.lb, p);
        else if (p.compareTo(x.p) != 0)
            return horizontalGet(x.rt, p);
        return x.p;
    }

    private Point2D horizontalGet(Node x, Point2D p) {
        if (x == null) return null;
        int cmp = Point2D.Y_ORDER.compare(p, x.p);
        if (cmp < 0) return verticalGet(x.lb, p);
        else if (p.compareTo(x.p) != 0)
            return verticalGet(x.rt, p);
        return x.p;
    }


    private Node verticalInsert(Node x, RectHV rect, Point2D p) {
        if (x == null) return new Node(p, rect);
        int cmp = Point2D.X_ORDER.compare(p, x.p);
        RectHV newRect;
        if (cmp < 0) {
            newRect = new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax());
            x.lb = horizontalInsert(x.lb, newRect, p);
        }
        else if (p.compareTo(x.p) != 0) {
            newRect = new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            x.rt = horizontalInsert(x.rt, newRect, p);
        }
        return x;
    }

    private Node horizontalInsert(Node x, RectHV rect, Point2D p) {
        if (x == null) return new Node(p, rect);
        int cmp = Point2D.Y_ORDER.compare(p, x.p);
        RectHV newRect;
        if (cmp < 0) {
            newRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y());
            x.lb = verticalInsert(x.lb, newRect, p);
        }
        else if (p.compareTo(x.p) != 0) {
            newRect = new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax());
            x.rt = verticalInsert(x.rt, newRect, p);
        }
        return x;
    }

    private void nullCheck(Object o) {
        if (o == null)
            throw new NullPointerException();
    }


    public static void main(String[] args) {

    }
}
