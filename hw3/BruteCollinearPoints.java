import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] p;
    // private LineSegment[] ls;
    private ArrayList<LineSegment> ls= new ArrayList<>();
    private int count = 0;
    /** finds all line segments containing 4 points */
    public BruteCollinearPoints(Point[] points){
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
        
         if(len < 4){
            return;
        }
       
        for (int i = 0; i < len ; i++){
            for (int j = i+1; j < len ; j++){
                for (int k = j+1; k < len; k++){
                    for (int l = k+1; l < len; l++){
                        double temp = p[i].slopeTo(p[j]);
                        if(p[i].slopeTo(p[k]) == temp && p[i].slopeTo(p[l]) == temp){
                            count++;
                            Point[] tempArray= {p[i],p[j],p[k],p[l]};
                            Arrays.sort(tempArray);
                            LineSegment templs = new LineSegment(tempArray[0], tempArray[3]);
                            ls.add(templs);
                        }                                                                          
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.show();
        }
        
    }
}