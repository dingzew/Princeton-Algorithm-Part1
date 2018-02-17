import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> pqTwin;
    private int n;
    private Board initial;
    private SearchNode end;
    private final int MAXTREESIZE = 500000;
    private final int CUTTOSIZE = 100000;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int priority;
        private SearchNode previousNode;

        public SearchNode(Board board, int moves, SearchNode previousNode) {
            this.board = board;
            this.moves = moves;
            priority = moves + board.manhattan();
            this.previousNode = previousNode;
        }

        public int compareTo(SearchNode that) {
            return (this.priority - that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        //varify input
        if(initial == null) throw new  NullPointerException();

        this.initial = initial;
        n = initial.dimension();
        pq = new MinPQ<SearchNode>();
        pqTwin = new MinPQ<SearchNode>();


        SearchNode minNode;
        SearchNode minNodeTwin;
        pq.insert(new SearchNode(initial, 0, null));
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));
        while(!(pq.min().board.isGoal()) && !(pqTwin.min().board.isGoal())) {
            minNode = pq.min();
            minNodeTwin = pqTwin.min();
            pq.delMin();
            pqTwin.delMin();
            for (Board neighbor: minNode.board.neighbors()) {
                if (minNode.moves == 0) {
                    pq.insert(new SearchNode(neighbor, minNode.moves+1, minNode));
                }
                else if (!neighbor.equals(minNode.previousNode.board)) {
                    pq.insert(new SearchNode(neighbor, minNode.moves+1, minNode));
                }
            }
            // Twin
            for (Board neighbor: minNodeTwin.board.neighbors()) {
                if (minNodeTwin.moves == 0) {
                    pqTwin.insert(new SearchNode(neighbor, minNodeTwin.moves+1, minNodeTwin));
                }
                else if (!neighbor.equals(minNodeTwin.previousNode.board)) {
                    pqTwin.insert(new SearchNode(neighbor, minNodeTwin.moves+1, minNodeTwin));
                }
            }
            // cut game tree
            if(pq.size() > MAXTREESIZE){
                MinPQ<SearchNode> newpq = new MinPQ<SearchNode>();
                for (int i = 0; i < CUTTOSIZE; i++){
                    newpq.insert(pq.min());
                    pq.delMin();
                }
                pq = newpq;
            }
            
            if(pqTwin.size() > MAXTREESIZE){
                MinPQ<SearchNode> newpqTwin = new MinPQ<SearchNode>();
                for (int i = 0; i < CUTTOSIZE; i++){
                    newpqTwin.insert(pqTwin.min());
                    pqTwin.delMin();
                }
                pqTwin = newpqTwin;
            }
        }
        
        
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        if (pq.min().board.isGoal()) {
            return true;
        }
        if (pqTwin.min().board.isGoal()) {
            return false;
        }
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if(!isSolvable()) return -1;
        //return end.moves;
        return pq.min().moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    // -FILO???
    public Iterable<Board> solution() {
        if(!isSolvable()) return null;
        Stack<Board> stackSolution = new Stack<Board>();
        SearchNode current = pq.min();
        while (current.previousNode!=null) {
            stackSolution.push(current.board);
            current = current.previousNode;
        }
        stackSolution.push(initial);
        return stackSolution;
    }

    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++){
            blocks[i][j] = in.readInt();
            //System.out.println(blocks[i][j]);
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}