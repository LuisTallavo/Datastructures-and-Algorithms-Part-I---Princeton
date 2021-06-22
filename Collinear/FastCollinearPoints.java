import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int segnum = 0;
    private LineSegment[] seg;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // Catching Errors in Input
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

        // Keep original set of points because sorting scrambles points
        Point[] fixedOrder = pointscopy.clone();


        for (int i = 0; i < pointscopy.length - 1; i++) {
            if (pointscopy[i].compareTo(pointscopy[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        // Declare necessary variables
        int n = pointscopy.length;
        LineSegment[] tempseg = new LineSegment[n * n];
        double slope = 0;
        Point origin;
        int count = 0;

        // total of n^2*logn runtime
        for (int i = 0; i < n; i++) {
            origin = fixedOrder[i];
            // StdOut.println("=== Origin: " + origin.toString() + " ===");

            // Sort by natural order and then by slope to find endpoints easily
            Arrays.sort(pointscopy);
            Arrays.sort(pointscopy, origin.slopeOrder()); // Mergesort used (stable)

            int j = 0;
            int additionalPoints = 0;

            while (j < n - 2) {

                // Find all points with the same slope we are currently working with
                slope = origin.slopeTo(pointscopy[j]);
                additionalPoints = 1;
                while (j + additionalPoints < n &&
                        slope == origin.slopeTo(pointscopy[j + additionalPoints])) {
                    additionalPoints++;
                }
                additionalPoints -= 1;

                // Check if the line segment has at least 4 collinear points
                if (additionalPoints >= 2) {

                    // Only create line segment if first and last additional
                    // points are in the "forward" direction
                    // (works because we sorted twice, so the points inbetween are sorted
                    // in an increasing order)
                    if (origin.compareTo(pointscopy[j]) < 0 &&
                            origin.compareTo(pointscopy[j + additionalPoints]) < 0) {
                        tempseg[segnum] = new LineSegment(origin, pointscopy[j + additionalPoints]);
                        segnum++;
                    }
                    j = j + additionalPoints;
                }
                j++;
            }
        }
        seg = new LineSegment[segnum];
        for (int i = 0; i < segnum; i++) {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
