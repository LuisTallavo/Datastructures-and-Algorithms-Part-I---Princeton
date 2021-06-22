import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int segnum = 0;
    private LineSegment[] seg;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point a : points) {
            if (a == null) {
                throw new IllegalArgumentException();
            }
        }
        if (points.length < 4) {
            throw new IllegalArgumentException();
        }

        Point[] pointscopy = points.clone();

        Arrays.sort(pointscopy);
        for (int i = 0; i < pointscopy.length - 1; i++) {
            if (pointscopy[i].compareTo(pointscopy[i + 1]) == 0)
                throw new IllegalArgumentException();
        }


        double slopepq = 0;
        double slopepr = 0;
        double slopeps = 0;
        Point high;
        Point low;
        int counter = 0;
        int n = points.length;
        // Assuming that for n points, you cannot have more than n*n line segments
        LineSegment[] tempseg = new LineSegment[n * n];


        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    for (int s = r + 1; s < n; s++) {
                        slopepq = points[p].slopeTo(points[q]);
                        slopepr = points[p].slopeTo(points[r]);
                        slopeps = points[p].slopeTo(points[s]);


                        if ((slopepq == slopepr) && (slopepq == slopeps)) {

                            if (points[p].compareTo(points[q]) > 0) {
                                high = points[p];
                                low = points[q];
                            }
                            else {
                                high = points[q];
                                low = points[p];
                            }

                            if (points[s].compareTo(high) > 0) {
                                high = points[s];
                            }
                            else if (points[s].compareTo(low) < 0) {
                                low = points[s];
                            }
                            if (points[r].compareTo(high) > 0) {
                                high = points[r];
                            }
                            else if (points[r].compareTo(low) < 0) {
                                low = points[r];
                            }
                            tempseg[counter] = new LineSegment(low, high);
                            counter++;
                            segnum++;
                        }
                    }
                }
            }
        }
        seg = new LineSegment[counter];
        for (int i = 0; i < counter; i++) {
            seg[i] = tempseg[i];
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segnum;
    }

    // the line segments
    public LineSegment[] segments() {
        return seg;
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
        }
        StdDraw.show();
    }
}
