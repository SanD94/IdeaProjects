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
            root = verticalInsert(root, null, p, Side.TOP);
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
        return nearestSearch(root, p, null, true);
    }


    private Point2D nearestSearch(Node x, Point2D p, Point2D nearest, boolean vert) {
        if (x == null) return nearest;
        if (nearest == null) nearest = x.p;
        if (x.rect.distanceSquaredTo(p) > p.distanceSquaredTo(nearest))
            return nearest;
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearest))
            nearest = x.p;
        int cmp;
        if (vert) cmp = Point2D.X_ORDER.compare(p, x.p);
        else cmp = Point2D.Y_ORDER.compare(p, x.p);
        if (cmp < 0) {
            nearest = nearestSearch(x.lb, p, nearest, !vert);
            nearest = nearestSearch(x.rt, p, nearest, !vert);
        } else {
            nearest = nearestSearch(x.rt, p, nearest, !vert);
            nearest = nearestSearch(x.lb, p, nearest, !vert);
        }
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


    private enum Side {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    private Node verticalInsert(Node x, Node parent, Point2D p, Side side) {
        if (x == null) {
            if (parent == null)
                return new Node(p, new RectHV(0, 0, 1, 1));
            RectHV newRect;
            if (side == Side.BOTTOM)
                newRect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                        parent.rect.xmax(), parent.p.y());
            else
                newRect = new RectHV(parent.rect.xmin(), parent.p.y(),
                        parent.rect.xmax(), parent.rect.ymax());

            return new Node(p, newRect);
        }
        int cmp = Point2D.X_ORDER.compare(p, x.p);
        if (cmp < 0)
            x.lb = horizontalInsert(x.lb, x, p, Side.LEFT);
        else if (p.compareTo(x.p) != 0)
            x.rt = horizontalInsert(x.rt, x, p, Side.RIGHT);

        return x;
    }

    private Node horizontalInsert(Node x, Node parent, Point2D p, Side side) {
        if (x == null) {
            RectHV newRect;
            if (side == Side.LEFT)
                newRect = new RectHV(parent.rect.xmin(),
                        parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
            else
                newRect = new RectHV(parent.p.x(), parent.rect.ymin(),
                        parent.rect.xmax(), parent.rect.ymax());

            return new Node(p, newRect);
        }
        int cmp = Point2D.Y_ORDER.compare(p, x.p);
        if (cmp < 0)
            x.lb = verticalInsert(x.lb, x, p, Side.BOTTOM);
        else if (p.compareTo(x.p) != 0)
            x.rt = verticalInsert(x.rt, x, p, Side.TOP);
        return x;
    }

    private void nullCheck(Object o) {
        if (o == null)
            throw new NullPointerException();
    }


    public static void main(String[] args) {

    }
}
