/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int experimentsCount;
    private final double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int size, int trials) {
        if (size <= 0)
            throw new IllegalArgumentException("'n' must b greater than or equal to 0");
        if (trials <= 0) throw new IllegalArgumentException(
                "'trials' must b greater than or equal to 0");

        experimentsCount = trials;
        thresholds = new double[experimentsCount];

        for (int i = 0; i < experimentsCount; i++) {
            int row, column;
            Percolation perc = new Percolation(size);
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, size + 1);
                column = StdRandom.uniform(1, size + 1);
                if (!perc.isOpen(row, column)) {
                    perc.open(row, column);
                }
            }
            thresholds[i] = perc.numberOfOpenSites() / (Math.pow(size, 2));
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    private double variance() {
        return 1.96 * stddev() / Math.sqrt(experimentsCount);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - variance();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + variance();
    }

    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, t);
        for (int i = 0; i < percStats.thresholds.length; i++) {
            System.out.println(percStats.thresholds[i]);
        }
        System.out.println("Mean                    = " + percStats.mean());
        System.out.println("StdDev                  = " + percStats.stddev());
        System.out.println(
                "95% confidence interval = " + percStats.confidenceLo() + ", " + percStats
                        .confidenceHi());
    }
}
