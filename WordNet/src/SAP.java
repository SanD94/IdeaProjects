import edu.princeton.cs.algs4.Digraph;

/**
 * Created by sand94 on 22.02.2017.
 */
public class SAP {

    private Digraph digraph;
    private int[] lengths;
    private int ancestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();
        this.digraph = new Digraph(G);
        this.lengths = new int[G.V()];
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        inBound(v);
        inBound(w);

        return -1;
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
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException();
        for (int i : v) inBound(i);
        for (int i : w) inBound(i);
        return 0;
    }

    private void inBound(int v) {
        if (v < 0 || v >= digraph.V())
            throw new IndexOutOfBoundsException();
    }

    public static void main(String[] args) {

    }
}
