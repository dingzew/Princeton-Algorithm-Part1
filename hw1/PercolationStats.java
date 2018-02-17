import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double[] result;
    private int trialTimes;
    
    /* perform trials independent experiments on an n-by-n grid */
    public PercolationStats(int n, int trials){
        //initialize result array
        result = new double[trials];
        int count = 0;
        trialTimes = trials;
        int row;
        int col;
        Percolation P;
        while(count < trials){
            P = new Percolation(n);
            while(!P.percolates()){
                row = (int)((n)*(StdRandom.uniform())+1);
                col = (int)((n)*(StdRandom.uniform())+1);
                P.open(row, col);
//                System.out.println(row);
//                System.out.println(col);
//                System.out.println(P.percolates());
            }
            result[count] = 1.0 * P.numberOfOpenSites() /n / n;
            count++;
        }
    }
    
    /* sample mean of percolation threshold */
    public double mean(){
        return StdStats.mean(result);
        
    }
    /* sample standard deviation of percolation threshold */
    public double stddev(){
        return StdStats.stddev(result);
    }
    /* low  endpoint of 95% confidence interval */
    public double confidenceLo(){
        double testmean = this.mean();
        double testdev = this.stddev();
        return testmean - 1.96 * testdev / Math.sqrt(this.trialTimes);
    }
    /* high endpoint of 95% confidence interval */
    public double confidenceHi(){
        double testmean = this.mean();
        double testdev = this.stddev();
        return testmean + 1.96 * testdev / Math.sqrt(this.trialTimes);
    }
    /* test client (described below) */
   public static void main(String[] args){
       int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);
       PercolationStats P = new PercolationStats(n,trials);
       System.out.print("mean                    = ");
       System.out.println(P.mean());
       System.out.print("stddev                  = ");
       System.out.println(P.stddev());
       System.out.print("95% confidence interval = [");
       System.out.print(P.confidenceLo());
       System.out.print(", ");
       System.out.print(P.confidenceHi());
       System.out.println("]");
   }
}