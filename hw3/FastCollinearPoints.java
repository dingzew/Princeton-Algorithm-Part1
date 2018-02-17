import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] p;
    // private LineSegment[] ls;
    private ArrayList<LineSegment> ls= new ArrayList<>();
    private int count = 0;
    
    /** finds all line segments containing 4 or more points */
    public FastCollinearPoints(Point[] points){
        int len = points.length;
        if(points == null){
            throw new java.lang.IllegalArgumentException();
        }
        /* copy the array O(n) */
        p = new Point[len];
        for (int i = 0 ; i < len; i++){
            if(points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            
            p[i] = points[i];
        }
        
        for(int i = 0 ; i < len; i++){
            for (int j = i+1; j < len; j++){
                if(p[i].compareTo(p[j]) == 0){
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }
        for (int i = 0; i < len - 3; i++){
            int tplen = len - i - 1;
            Point[] tp = new Point[tplen];
            for (int j = i+1; j < len; j++){
                tp[j-i-1] = p[j];
            }
            Arrays.sort(tp,p[i].slopeOrder());
            for(int j = 0; j < tplen - 2; j++){
                double temp = p[i].slopeTo(tp[j]);
                if(temp == p[i].slopeTo(tp[j+1]) && temp == p[i].slopeTo(tp[j+2])){
                    count++;
                    Point[] tempArray= {p[i],tp[j],tp[j+1],tp[j+2]};
                    Arrays.sort(tempArray);
                    LineSegment templs = new LineSegment(tempArray[0], tempArray[3]);
                    ls.add(templs);
                }
            }
        }
        
        
        
        
    }
    /** the number of line segments */
    public           int numberOfSegments(){
        return count;
    }
    /** the line segments */
    public LineSegment[] segments(){
        int size = ls.size();
        LineSegment[] templs = new LineSegment[size];
        int n = 0;
        for (LineSegment element : ls) {
            templs[n] = element;
            n++;
        }  
        return templs;
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.show();
        }
        
    }
    
    
}
