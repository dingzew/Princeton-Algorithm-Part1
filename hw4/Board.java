import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;
import java.lang.Math;


public class Board {
    private final int dimen;
    // private final int ham;
    // private final int manha;
    private final char[] oneD;
    private int blankpos;
    
    
    /** convert 2D index into 1D index */
//    private int converter(int i, int j){
//        return i * this.dimen + j;
//    }
    
    /** convert 1D into 2D index */
    private int row(int index){
        int result = 1 + (index - 1) / this.dimen;
        return result; 
    }
    
    private int col(int index){
        int result = 1 + (index - 1) - (row(index) - 1) * this.dimen;
        return result;
    }
    
    
    private int[][] toTwoDarray(char[] oneD) {
        int k = 0;
        int[][] blocks = new int[dimen][dimen];
        for (int i = 0; i < dimen; i++)
            for (int j = 0; j < dimen; j++) {
            blocks[i][j] = oneD[k];
            k++;
        }
        return blocks;
    }
    
    /** four swap functions */
    private boolean exchAbove(char[] oneDArray, int k, int mode){
        // bound check
        if(k/dimen < 1) return false;
        // check if k or k-n is empty
        if((oneDArray[k] == 0 || oneDArray[k-dimen] == 0) && mode == 1) return false;
        // exchange k value and its above
        char temp = oneDArray[k];
        oneDArray[k] = oneDArray[k-dimen];
        oneDArray[k-dimen] = temp;
        return true;
    }
    
    private boolean exchRight(char[] oneDArray, int k, int mode){
        // bound check
        if(k%dimen == dimen - 1) return false;
        if((oneDArray[k] == 0 || oneDArray[k+1] == 0) && mode == 1) return false;
        // exchange k value and its above
        char temp = oneDArray[k];
        oneDArray[k] = oneDArray[k+1];
        oneDArray[k+1] = temp;
        return true;
    }
    
    private boolean exchBelow(char[] oneDArray, int k, int mode){
        // bound check
        if(k/dimen > dimen - 2) return false;
        // check if k or k-n is empty
        if((oneDArray[k] == 0 || oneDArray[k+dimen] == 0) && mode == 1) return false;
        // exchange k value and its above
        char temp = oneDArray[k];
        oneDArray[k] = oneDArray[k+dimen];
        oneDArray[k+dimen] = temp;
        return true;
    }
    
    private boolean exchLeft(char[] oneDArray, int k, int mode){
        // bound check
        if(k%dimen == 0) return false;
        if((oneDArray[k] == 0 || oneDArray[k-1] == 0) && mode == 1) return false;
        // exchange k value and its above
        char temp = oneDArray[k];
        oneDArray[k] = oneDArray[k-1];
        oneDArray[k-1] = temp;
        return true;
    }
    
    private void printAll(){
        for (int i = 0; i < this.oneD.length; i++){
            System.out.println((int)oneD[i]);
        }
    }
    
    /** construct a board from an n-by-n array of blocks 
      (where blocks[i][j] = block in row i, column j)*/
    public Board(int[][] blocks){
        this.dimen = blocks.length;
        this.oneD = new char[dimen*dimen];
        int k = 0; // index for the 1D char array
        for (int i = 0; i < this.dimen; i++){
            for (int j = 0; j< this.dimen; j++){
                if(blocks[i][j] == 0) blankpos = k;
                oneD[k] = (char)blocks[i][j];
                k++;
            }
        }
        // this.manha = this.manhattan();
        // this.ham = this.hamming();
    }
    

    
    /** board dimension n */
    public int dimension(){
        return this.dimen;
    }
    
    
    /** number of blocks out of place */
    public int hamming(){
        int count = 0;
        for (int i = 0; i < dimen * dimen; i++){
            if(oneD[i] != i + 1 && oneD[i] != 0) count++;
        }
        return count;
    }
    
    
    /** sum of Manhattan distances between blocks and goal */
    public int manhattan(){
        int count = 0;
        for (int i = 0; i < dimen*dimen; i++){
                if(oneD[i] == 0) continue;
                int x1 = row(i + 1);
                int y1 = col(i + 1);
                int x2 = row(oneD[i]);
                int y2 = col(oneD[i]);
                count = count + Math.abs(x2 - x1) + Math.abs(y2 - y1);
//                System.out.println("x1 = " + x1);
//                System.out.println("y1 = " + y1);
//                System.out.println("x2 = " + x2);
//                System.out.println("y2 = " + y2);
//                
//                System.out.println(count);
            }
        return count;
    }
    
    /** is this board the goal board? */
    public boolean isGoal(){
        return (this.hamming() == 0);
    }
    /** a board that is obtained by exchanging any pair of blocks */
    public Board twin(){
        boolean swapSuccess = false;
        char[] twin = oneD.clone();
        // choose a non-blank block
        int k = 0;
//        do {
//            k=StdRandom.uniform(dimen*dimen);
//        } while (oneD[k] == 0);
        while (swapSuccess == false) {
             swapSuccess = exchAbove(twin, k, 1);
             if(swapSuccess == true) break;

             swapSuccess = exchRight(twin, k, 1);
             if(swapSuccess == true) break;

             swapSuccess = exchBelow(twin, k, 1);
             if(swapSuccess == true) break;

             swapSuccess = exchLeft(twin, k, 1);
             if(swapSuccess == true) break;
             
             if(swapSuccess == false) k = dimen - 1;
        }
        Board twinBoard = new Board(toTwoDarray(twin));
        // if(twinBoard == null) System.out.println("hello");
        return twinBoard;
    }
    
    
    /** does this board equal y? */
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.equals(this.oneD, that.oneD)) return false;
        return true;
    }
    
    /** all neighboring boards */
    public Iterable<Board> neighbors(){
        Stack<Board> stackNeighbors = new Stack<Board>();
        char[] neighbor;
        if(row(blankpos+1) != 1) {
            neighbor = oneD.clone();
            exchAbove(neighbor,blankpos, 0);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        if(row(blankpos+1) != dimen) {
            neighbor = oneD.clone();
            exchBelow(neighbor,blankpos, 0);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        if(col(blankpos+1) != 1) {
            neighbor = oneD.clone();
            exchLeft(neighbor,blankpos, 0);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        if(col(blankpos+1) != dimen) {
            neighbor = oneD.clone();
            exchRight(neighbor,blankpos, 0);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        return stackNeighbors;
    }
    
    /** string representation of this board 
      * (in the output format specified below) */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(dimen + "\n");
        int k = 0;
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                s.append(String.format("%2d ", (int)oneD[k]));
                k++;
            }
            s.append("\n");
        }
        return s.toString();
    }

    /** unit tests (not graded) */
    public static void main(String[] args){
        int a[][] = {{2,1,6},{4,5,3},{7,8,0}};
        Board board = new Board(a);
        Board board1 = new Board(a);
        for (int i = 0; i < 40; i++){
            board1.twin();
            board1.twin();
            board.twin();
            board1.neighbors();
            board1.hamming();
            board1.manhattan();
            board1.toString();
        }
        
        Board twin = board.twin();
        System.out.println(board.manhattan());
        System.out.println(board.hamming());
        System.out.println(board.isGoal());
        System.out.println(twin.manhattan());
        System.out.println(twin.hamming());
        System.out.println(twin.isGoal());
        // twin.printAll();
//        Iterable<Board> k = board.neighbors();
//        for (Board element : k){
//            element.printAll();
//            System.out.println("");
//        }
        System.out.println(board.equals(board1));
    }
}