import java.util.Arrays;

public class Board {
    private int[][] board;

    public Board(int[][] blocks) {
        board = blocks;
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
                if (r == board.length - 1 && c == board.length - 1 && board[r][c] != 0) {
                    count++;
                } else if (board[r][c] != r * board.length + c + 1) {
                    count++;
                }
            }
        }
        return count; // one is out of place
    }

    public int manhattan() {
        int man = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                int num = board[r][c];
                if(num!=0){
                    man += Math.abs(r-((num-1)/board.length));
                    man += Math.abs(c-(num%board.length-1));
                }
            }
        }
        // sum of Manhattan distances between blocks and goal
        return man;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return hamming()==0;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        return null;
    }

    public boolean equals(Object y) {
        // does this board equal y?
        //if they have the same numbers in the same places
        //cast y to Board
        //arrays.deepEquals works with 1D probs
        Board comp = (Board) y;
        return false;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        return null;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        return null;
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = {{1,2,3},{4,5,6}};
        Board b1 = new Board(blocks);


    }
}