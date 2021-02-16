/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private boolean[][] displayGrid;
    private final int virtualTop;
    private final int virtualBot;
    private int openSitesAmount;
    private final WeightedQuickUnionUF grid;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n;
        if (size < 1) {
            throw new IllegalArgumentException("'n' must be greater than 0.");
        }
        displayGrid = new boolean[size][size];
        grid = new WeightedQuickUnionUF(size * size + 2);
        virtualTop = size * size;
        virtualBot = size * size + 1;
        openSitesAmount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.validateRowCol(row, col);
        if (!this.isOpen(row, col)) {
            this.displayGrid[row - 1][col - 1] = true;
            this.openSitesAmount++;
            int siteIndex = this.determineArrayIndex(row, col);

            // If site is in top row, connect to virtual top
            if (row == 1) {
                this.grid.union(this.virtualTop, siteIndex);
            }
            // If site is in the bottom row, connect to virtual bottom
            if (row == this.size) {
                this.grid.union(this.virtualBot, siteIndex);
            }

            // check site to the left
            if (this.isOpen(row - 1, col)) {
                this.updateUnionFinds(row - 1, col, siteIndex);
            }
            // check site to the right
            if (this.isOpen(row + 1, col)) {
                this.updateUnionFinds(row + 1, col, siteIndex);
            }
            // check site above
            if (this.isOpen(row, col + 1)) {
                this.updateUnionFinds(row, col + 1, siteIndex);
            }
            // check site below
            if (this.isOpen(row, col - 1)) {
                this.updateUnionFinds(row, col - 1, siteIndex);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        try {
            return this.displayGrid[row - 1][col - 1] == true;
        }
        catch (ArrayIndexOutOfBoundsException x) {
            return false;
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        this.validateRowCol(row, col);
        int siteIndex = this.determineArrayIndex(row, col);
        return this.grid.find(this.virtualTop) == this.grid.find(siteIndex);
    }

    // returns the open number of sites
    public int numberOfOpenSites() {
        return this.openSitesAmount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.grid.find(this.virtualTop) == this.grid.find(this.virtualBot);
    }

    // Update UF data structure
    private void updateUnionFinds(int row, int col, int site) {
        try {
            this.validateRowCol(row, col);
            int neighborSite = this.determineArrayIndex(row, col);
            this.grid.union(neighborSite, site);
        }
        catch (IllegalArgumentException e) {
            // carry on citizen
        }
    }

    // returns single array index for two coordinates (row, col)
    private int determineArrayIndex(int row, int col) {
        return (row - 1) * this.size + col - 1;
    }

    // validate (row, col) arguments
    private void validateRowCol(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size) {
            throw new IllegalArgumentException(
                    "'row' and 'col' must be greater than 1 and less than " + this.size
            );
        }
    }
}
