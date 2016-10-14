import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
/**
 * Created by sand94 on 13.09.2016.
 */
public class PercolationStats {
    private double [] thresholds;
    private int trials;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.thresholds = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int opened = 0;
            while (!p.percolates()) {
                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    opened++;
                }
            }
            thresholds[i] = 1.0 * opened/(n*n);
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    public double mean() { return mean; }

    public double stddev() { return stddev; }

    public double confidenceLo() { return mean - 1.96 * stddev / Math.sqrt(trials); }

    public double confidenceHi() { return mean + 1.96 * stddev / Math.sqrt(trials); }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats PS = new PercolationStats(n, trials);

        StdOut.printf("%-25s = %f\n", "mean", PS.mean());
        StdOut.printf("%-25s = %f\n", "stddev", PS.stddev());
        StdOut.printf("%-25s = %f, %f\n", "95% confidence interval",
                PS.confidenceLo(), PS.confidenceHi());
    }
}
