
import java.util.ArrayList;

/**
 * Board class.
 */
public class Board {
    /**
     * Board 2D Array.
     */
    private int[][] board;
    /**
     * Length and width of array.
     */
    private int n;
    /**
     * Used to reuse hamming value.
     */
    private int hamming = -1;
    /**
     * Row where 0 is.
     */
    private int blankRow = -1;
    /**
     * Column where 0 is.
     */
    private int blankCol = -1;
    /**
     * Used to resuse manhattan value.
     */
    private int manhattan = -1;

    public Board(int[][] blocks) {
        board = blocks;
        n = board.length;
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        // number of blocks out of place
        int count = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (blankRow == -1 && board[r][c] == 0) {
                    blankRow = r;
                    blankCol = c;
                }
                int num = board[r][c];
                if (num != 0) {
                    int corcol;
                    int corrow;
                    if (num > n) {
                        if (num % n == 0) {
                            corcol = n - 1;
                        } else {
                            corcol = (num % n) - 1;
                        }

                    } else {
                        corcol = num - 1;
                    }
                    corrow = (num - 1) / n;
                    if (corrow != r || corcol != c) count++;
                }
            }
        }
        hamming = count;
        return count;
    }

    public int manhattan() {
        // row*n + col+1 = num it should be
        if (manhattan != -1) return manhattan;
        int man = 0;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (blankRow == -1 && board[r][c] == 0) {
                    blankRow = r;
                    blankCol = c;
                }
                int num = board[r][c];
                if (num != 0) {
                    int corcol;
                    int corrow;
                    if (num > n) {
                        if (num % n == 0) {
                            corcol = n - 1;
                        } else {
                            corcol = (num % n) - 1;
                        }

                    } else {
                        corcol = num - 1;
                    }
                    corrow = (num - 1) / n;
                    man += Math.abs((corrow - r)) + Math.abs(corcol - c);
                }
            }
        }
        // sum of Manhattan distances between blocks and goal
        manhattan = man;
        return man;
    }

    public boolean isGoal() {
        // is this board the goal board?
        if (hamming == -1) hamming = hamming();
        return hamming == 0;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] twin = new int[board.length][board.length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                twin[r][c] = board[r][c];
            }
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != 0 && inbounds(r, c + 1) && board[r][c + 1] != 0) {
                    int temp = twin[r][c];
                    twin[r][c] = twin[r][c + 1];
                    twin[r][c + 1] = temp;
                    return new Board(twin);
                }
                if (board[r][c] != 0 && inbounds(r + 1, c) && board[r + 1][c] != 0) {
                    int temp = twin[r][c];
                    twin[r][c] = twin[r + 1][c];
                    twin[r + 1][c] = temp;
                    return new Board(twin);
                }
            }
        }
        return null;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board compare = (Board) y;
        if (compare.n != n) return false;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != compare.board[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> ret = new ArrayList<>();
        if (blankRow == -1) {
            boolean leave = false;
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board.length; c++) {
                    if (board[r][c] == 0) {
                        blankRow = r;
                        blankCol = c;
                        leave = true;
                        break;
                    }
                }
                if (leave) break;
            }

        }
        int[] DX = {1, 0, -1, 0};
        int[] DY = {0, 1, 0, -1};
        for (int i = 0; i < 4; i++) {
            int[][] twin = new int[board.length][board.length];
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board.length; c++) {
                    twin[r][c] = board[r][c];
                }
            }
            int newRow = blankRow + DX[i];
            int newCol = blankCol + DY[i];
            if (inbounds(newRow, newCol)) {
                int temp = twin[blankRow][blankCol];
                twin[blankRow][blankCol] = twin[newRow][newCol];
                twin[newRow][newCol] = temp;
                ret.add(new Board(twin));
            }
        }

        // all neighboring boards

        return ret;
    }

    private boolean inbounds(int row, int col) {
        return (row >= 0 && row < n && col >= 0 && col < n);
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = {{0, 2, 3}, {4, 5, 6}};
        int[][] blocks1 = {{1, 2, 3}, {4, 5, 6}};
        Board b1 = new Board(blocks);
        b1.neighbors();


    }
}