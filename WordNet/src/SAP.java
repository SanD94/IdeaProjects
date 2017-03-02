import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by sand94 on 22.02.2017.
 */
public class SAP {

    private class ColoredNode {
        final int color;
        final int node;

        public ColoredNode(int color, int node) {
            this.color = color;
            this.node = node;
        }
    }

    private class ColoredLength {
        final int color;
        final int length;

        public ColoredLength(int color, int length) {
            this.color = color;
            this.length = length;
        }
    }

    private class CompositeNode {
        final int node;
        final ColoredLength v;
        final ColoredLength w;

        public CompositeNode(int node, ColoredLength v, ColoredLength w) {
            this.node = node;
            this.v = v;
            this.w = w;
        }
    }

    private Digraph digraph;
    private int[] lengths;
    private int[] colored;
    private int ancestor;
    private int color;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();
        this.digraph = new Digraph(G);
        this.lengths = new int[G.V()];
        this.colored = new int[G.V()];
        this.color = 1;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        inBound(v);
        inBound(w);

        Queue<ColoredNode> Q = new LinkedList<>();
        lengths[v] = lengths[w] = 0;
        colored[v] = color++;
        Q.add(new ColoredNode(colored[v], v));

        if (color - 1 == colored[w]) {
            ancestor = v;
            return 0;
        }

        colored[w] = color++;
        Q.add(new ColoredNode(colored[w], w));

        return BFS(Q);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        inBound(v);
        inBound(w);
        length(v,w);
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException();
        for (int i : v) inBound(i);
        for (int i : w) inBound(i);

        Queue<ColoredNode> Q = new LinkedList<>();
        for (int i : v) {
            lengths[i] = 0;
            colored[i] = color;
            Q.add(new ColoredNode(color, i));
        }
        color ++;
        for (int i : w) {
            lengths[i] = 0;
            if (colored[i] == color - 1) {
                ancestor = i;
                return 0;
            }
            colored[i] = color;
            Q.add(new ColoredNode(color, i));
        }
        color++;

        return BFS(Q);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException();
        for (int i : v) inBound(i);
        for (int i : w) inBound(i);
        length(v,w);
        return ancestor;
    }

    private void inBound(int v) {
        if (v < 0 || v >= digraph.V())
            throw new IndexOutOfBoundsException();
    }

    private int BFS(Queue<ColoredNode> Q) {
        int minLength = digraph.V();
        int currentAncestor = -1;
        CompositeNode compositeNode = null;
        Queue<CompositeNode> QQ = new LinkedList<>();
        HashMap<Integer, CompositeNode> compositeNodeHashMap = new HashMap<>();

        while(!Q.isEmpty()) {
            boolean isComposite = false;
            ColoredNode cur = Q.poll();
            int node = cur.node;
            // There is a composite node here.
            if (colored[node] != cur.color) continue;
            // check color if there is a composite node
            if (cur.color == color) {
                compositeNode = QQ.poll();
                node = compositeNode.node;
                isComposite = true;
            }
            if (minLength < lengths[node]) break;
            for (int neighbor : digraph.adj(node)) {
                // new neighbor detected
                if (colored[neighbor] < color-2) {
                    if (!isComposite) {
                        lengths[neighbor] = lengths[node] + 1;
                    } else {
                        lengths[neighbor] = Math.min(compositeNode.v.length, compositeNode.w.length);
                        CompositeNode nextNode = new CompositeNode(neighbor,
                                new ColoredLength(compositeNode.v.color, compositeNode.v.length+1),
                                new ColoredLength(compositeNode.w.color, compositeNode.w.length+1));
                        compositeNodeHashMap.put(neighbor, nextNode);
                        QQ.add(nextNode);
                    }
                    colored[neighbor] = colored[node];
                    Q.add(new ColoredNode(colored[neighbor], neighbor));
                } else if (colored[neighbor] != color || colored[neighbor] != colored[node]) {
                    CompositeNode nextNode = null;
                    int nextMin = 1_000_000_000;

                    colored[neighbor] = color;
                    if (minLength > nextMin) {
                        minLength = nextMin;
                        currentAncestor = neighbor;
                    }
                }
            }
        }
        color++;
        if (currentAncestor == -1) minLength = -1;
        ancestor = currentAncestor;
        return minLength;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            StdOut.println();
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

    }
}
