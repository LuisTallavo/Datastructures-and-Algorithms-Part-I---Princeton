import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private boolean[] sites;
    private int open;
    private int n;
    private int numelements;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        numelements = (n * n) + 2;

        uf = new WeightedQuickUnionUF(numelements);

        sites = new boolean[numelements];

        this.n = n;
        open = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkSpot(row, col);
        int indx = index(row, col);

        if (!sites[indx]) {
            sites[indx] = true;
            open++;

            if (row == 1) {
                uf.union(indx, 0);
            }
            if (row == n) {
                uf.union(indx, numelements - 1);
            }
            if (row != 1 && isOpen(row - 1, col)) {
                uf.union(indx, indx - n);
            }
            if (row != n && isOpen(row + 1, col)) {
                uf.union(indx, indx + n);
            }
            if (col != 1 && isOpen(row, col - 1)) {
                uf.union(indx, indx - 1);
            }
            if (col != n && isOpen(row, col + 1)) {
                uf.union(indx, indx + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkSpot(row, col);
        return sites[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkSpot(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return uf.connected(index(row, col), 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, numelements - 1);
    }

    // Returns the index of the cell
    private int index(int row, int col) {
        return ((row - 1) * n + col - 1);
    }

    private void checkSpot(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }

}
