import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double dev;
    private final int times;
    
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        
        double[] openSites = new double[trials];
     
        times = trials;
        
        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                if (!grid.isOpen(x, y))
        		    grid.open(x, y);
            }
            openSites[i] = (double)grid.numberOfOpenSites() / (n * n);
        }
        
        mean = StdStats.mean(openSites);
        dev = StdStats.stddev(openSites);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return dev;
    }
	
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(times);
    }
	
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(times);
    }
	
    public static void main(String[] args) { 
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = " + "[" + stats.confidenceLo() + ", "
        + stats.confidenceHi() + "]");
    }
}

