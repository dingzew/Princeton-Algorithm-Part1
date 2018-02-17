import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] grid;
    private WeightedQuickUnionUF unionTree;
    private WeightedQuickUnionUF unionTreeFull;
    private int side;
    private int size;
    private int openNumber;
    
    /*transfer 2D into 1D */
    private int xyTo1D(int row, int col){
        return (row-1) * side + (col-1);
    }
    
    
    /* create n-by-n grid, with all sites blocked */
    /* index: size is the top vitual position while size + 1 is lower one*/
    public Percolation(int n){
        size = n*n;
        side = n;
        grid = new int[size];
        unionTree = new WeightedQuickUnionUF(size + 2);
        unionTreeFull = new WeightedQuickUnionUF(size + 1);
        openNumber = 0;
        
        /* initialize grid to be all blocked */
        for (int i = 0; i < size; i++){
            grid[i] = 1; // 1 means blocked
        }
        
        /* initialize two vitual sites */
        for (int i = 0 ; i < side; i++){
            unionTree.union(size, i);
            unionTree.union(size + 1, size - i - 1);
            unionTreeFull.union(size,i);
        }
        
    }
   /* open site (row, col) if it is not open already */
    public    void open(int row, int col){
        int index = xyTo1D(row, col);
        if(grid[index] == 0) return;
        grid[index] = 0;
        if(row != 1){
            if(grid[index - side] ==  0) {
                unionTree.union(index, index - side);
                unionTreeFull.union(index, index - side);
            }
        }
        if(row != side){
            if(grid[index + side] ==  0) {
                unionTree.union(index, index + side);
                unionTreeFull.union(index, index + side);
            }
        }
        if(col != 1){
            if(grid[index - 1] ==  0) {
                unionTree.union(index, index - 1);
                unionTreeFull.union(index, index - 1);
            }
        }
        if(col != side){
            if(grid[index + 1] ==  0) {
                unionTree.union(index, index + 1);
                unionTreeFull.union(index, index + 1);
            }
        }
        openNumber++;
    }
   /* is site (row, col) open? */
    public boolean isOpen(int row, int col){
        return grid[xyTo1D(row, col)] == 0;
    }
    /* is site (row, col) full? */
    public boolean isFull(int row, int col){
        int index = xyTo1D(row, col); 
        return (isOpen(row, col) && (unionTreeFull.connected(index,size)));
    }
    
   /* number of open sites */
    public     int numberOfOpenSites(){
        return openNumber;
    }
    
   /* does the system percolate? */
    public boolean percolates(){
        return unionTree.connected(size, size + 1);
    }
    
   // test client (optional) 
    public static void main(String[] args){
        Percolation P = new Percolation(2);
        P.open(1,1);
        P.open(2,1);
        P.open(2,2);
        //P.open(3,2);
        //P.open(4,2);
        //P.open(5,2);
        // System.out.println(P.percolates());
        //P.open(5,3);
        // System.out.println(P.isFull(5,3));
        System.out.println(P.percolates());
        //P.open(5,5);
        //System.out.println(P.isFull(5,5));
    }
}