import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean_value;
    private double stddev_value;
    private double confidenceLo_value;
    private double confidenceHi_value;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double[] results = new double[trials];
        int test_site_y;
        int test_site_x;

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                do {
                    test_site_x = (int) (StdRandom.uniform() * n) + 1;
                    test_site_y = (int) (StdRandom.uniform() * n) + 1;
                }
                while (p.isOpen(test_site_y, test_site_x));

                p.open(test_site_y, test_site_x);
            }
            results[i] = (double) (p.numberOfOpenSites()) / (n * n);
        }

        mean_value = StdStats.mean(results);
        stddev_value = StdStats.stddev(results);

        confidenceLo_value = mean_value - (1.96 * stddev_value) / Math.sqrt(trials);
        confidenceHi_value = mean_value + (1.96 * stddev_value) / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean_value;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev_value;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo_value;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi_value;
    }

    // test client (see below)
    public static void main(String[] args) {
        int a, b;
        a = Integer.parseInt(args[0]);
        b = Integer.parseInt(args[1]);

        try {
            PercolationStats pp = new PercolationStats(a, b);
            StdOut.println("mean                    = " + pp.mean());
            StdOut.println("stddev                  = " + pp.stddev());
            StdOut.println("95% confidence interval = [" + pp.confidenceLo() + ", " + pp.confidenceHi() + "]");
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
