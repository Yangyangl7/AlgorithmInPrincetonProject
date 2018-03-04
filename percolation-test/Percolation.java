import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation { 
    private int numberOfOpenSites = 0;
    private byte[][] a;
    private final WeightedQuickUnionUF grid;
	
    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        grid = new WeightedQuickUnionUF((n + 1) * (n + 1));
        a = new byte[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                a[i][j] = 0;
            }	
        }
    }
    
    private void validation(int row, int col) {
        if (row  >= a.length || col >= a.length
            || row  < 1 || col  < 1 ) 
        	throw new java.lang.IllegalArgumentException();
    }
	
    private int xyTo1D(int row, int col) {
        return row * a.length + col;
    }
    
    private void updateAround(int row1, int col1, int row2, int col2) {
    	int posO = xyTo1D(row1, col1);
    	int posN = xyTo1D(row2, col2);
    	int p = grid.find(posO);
    	int q = grid.find(posN);
    	
    	grid.union(posO, posN);
    	if (a[p / a.length][p % a.length] == 2 || a[q / a.length][q % a.length] == 2) {
    		int root = grid.find(posO);
    		a[root / a.length][root % a.length] = 2;
    	}
    }

    public void open(int row, int col) {
        validation(row, col);
        if (isOpen(row, col)) return;
        
        numberOfOpenSites++;
        a[row][col] = 1;
        
        if (row == a.length - 1)
        	a[row][col] = 2;
        
        if (row == 1) {
        	grid.union(0, xyTo1D(row,  col));
        	if (a[row][col] == 2) a[0][0] = 2;
        }
        	
        if (row < a.length - 1 && isOpen(row + 1, col))
        	updateAround(row, col, row + 1, col);
        		
        if (row > 1 && isOpen(row - 1, col))
        	updateAround(row, col, row - 1, col);
        	
        if (col > 1 && isOpen(row, col - 1))
        	updateAround(row, col, row, col - 1);
        	
        if (col < a.length - 1 && isOpen(row, col + 1))
        	updateAround(row, col, row, col + 1);
    }
    
    public boolean isOpen(int row, int col) {
        validation(row, col);
        return a[row][col] > 0;
    }
	
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        int root = grid.find(0);
		return a[root / a.length][root % a.length] == 2;
    }
	
    public boolean isFull(int row, int col) {
        validation(row, col);
        int pos = xyTo1D(row, col);
      
        return grid.connected(0, pos) && a[row][col] >= 1;
    }
}