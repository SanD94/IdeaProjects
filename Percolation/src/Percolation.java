import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by sand94 on 13.09.2016.
 */


public class Percolation {
    private WeightedQuickUnionUF system;
    private int n;
    private boolean [] hole;
    private boolean [] up;
    private boolean [] down;
    private boolean done;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.system = new WeightedQuickUnionUF(n*n);
        this.hole = new boolean[n*n];
        this.up = new boolean[n*n];
        this.down = new boolean[n*n];
        this.n = n;
        this.done = false;
    }

    public void open(int i, int j) {
        boundaryCheck(i);
        boundaryCheck(j);
        int component = oneDim(i, j);
        hole[component] = true;
        if (component < n) up[component] = true;
        if (component >= n*(n-1)) down[component] = true;

        int [][] dir = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        for (int k = 0; k < 4; k++) {
            int nexti = i + dir[k][0];
            int nextj = j + dir[k][1];
            if (isGood(nexti) && isGood(nextj) && isOpen(nexti, nextj)) {
                int nearComponent = oneDim(nexti, nextj);
                int p = system.find(component);
                int q = system.find(nearComponent);
                system.union(component, nearComponent);
                int root = system.find(component);
                up[root] |= up[p] | up[q];
                down[root] |= down[p] | down[q];
            }
        }
        int root = system.find(component);
        if (up[root] && down[root]) done = true;

    }

    public boolean isOpen(int i, int j) {
        boundaryCheck(i);
        boundaryCheck(j);
        return hole[oneDim(i, j)];
    }

    public boolean isFull(int i, int j) {
        int root = system.find(oneDim(i, j));
        return isOpen(i, j) && up[root];
    }

    public boolean percolates() {
        return done;
    }

    private void boundaryCheck(int i) {
        if (i <= 0 || i > n) throw new IndexOutOfBoundsException();
    }

    private boolean isGood(int i) {
        return i > 0 && i <= n;
    }

    private int oneDim(int i, int j) {
        return (i-1)*n + j-1;
    }
}
