import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int[][] board;
    private int n;
    private int hamming = -1;
    private int blankRow = -1;
    private int blankCol = -1;

    public Board(int[][] blocks) {
        board = blocks;
        n = board.length;
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
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
                if (r == board.length - 1 && c == board.length - 1 && board[r][c] != 0) {
                    count++;
                } else if (board[r][c] != r * board.length + c + 1) {
                    count++;
                }
            }
        }
        hamming = count;
        return count; // one is out of place
    }

    public int manhattan() {
        int man = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (blankRow == -1 && board[r][c] == 0) {
                    blankRow = r;
                    blankCol = c;
                }
                int num = board[r][c];
                if (num != 0) {
                    man += Math.abs(r - ((num - 1) / board.length));
                    man += Math.abs(c - (num % board.length - 1));
                }
            }
        }
        // sum of Manhattan distances between blocks and goal
        return man;
    }

    public boolean isGoal() {
        // is this board the goal board?
        if (hamming == -1) hamming = hamming();
        return hamming == 0;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != 0 && inbounds(r, c + 1) && board[r][c + 1] != 0) {
                    int[][] twin = Arrays.copyOf(board, board.length);
                    int temp = twin[r][c];
                    twin[r][c] = twin[r][c + 1];
                    twin[r][c + 1] = temp;
                    return new Board(twin);
                }
                if (board[r][c] != 0 && inbounds(r + 1, c) && board[r + 1][c] != 0) {
                    int[][] twin = Arrays.copyOf(board, board.length);
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
        // does this board equal y?
        //if they have the same numbers in the same places
        //cast y to Board
        //arrays.deepEquals works with 1D probs
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board compare = (Board) y;
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

        int[][] twin = new int[board.length][board.length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                twin[r][c] = board[r][c];
            }
        }

        int newRow = blankRow + 1;
        int newCol = blankCol;
        if (inbounds(newRow, newCol)) {
            int temp = twin[blankRow][blankCol];
            twin[blankRow][blankCol] = twin[newRow][newCol];
            twin[newRow][newCol] = temp;
            ret.add(new Board(twin));
        }

        int[][] twin1 = new int[board.length][board.length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                twin1[r][c] = board[r][c];
            }
        }

        newRow = blankRow;
        newCol = blankCol + 1;
        if (inbounds(newRow, newCol)) {
            int temp = twin1[blankRow][blankCol];
            twin1[blankRow][blankCol] = twin1[newRow][newCol];
            twin1[newRow][newCol] = temp;
            ret.add(new Board(twin1));
        }

        int[][] twin2 = new int[board.length][board.length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                twin2[r][c] = board[r][c];
            }
        }
        newRow = blankRow - 1;
        newCol = blankCol;
        if (inbounds(newRow, newCol)) {
            int temp = twin2[blankRow][blankCol];
            twin2[blankRow][blankCol] = twin2[newRow][newCol];
            twin2[newRow][newCol] = temp;
            ret.add(new Board(twin2));
        }
        int[][] twin3 = new int[board.length][board.length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                twin3[r][c] = board[r][c];
            }
        }
        newRow = blankRow;
        newCol = blankCol - 1;
        if (inbounds(newRow, newCol)) {
            int temp = twin3[blankRow][blankCol];
            twin3[blankRow][blankCol] = twin3[newRow][newCol];
            twin3[newRow][newCol] = temp;
            ret.add(new Board(twin3));
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