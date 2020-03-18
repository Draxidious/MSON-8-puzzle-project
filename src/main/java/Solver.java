
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Solver class to solve boards.
 */
public class Solver {
    /**
     * Node class to store boards in q.
     */
    private class BoardNode implements Comparable<BoardNode> {
        /**
         * Gvalue = Moves +manhattan.
         */
        private int gvalue;
        /**
         * Number of moves relative to this node.
         */
        private int numOfmoves;
        /**
         * Current board.
         */
        private Board current;
        /**
         * Previous Board.
         */
        private BoardNode prev;
        /**
         * Boolean to check if Twin.
         */
        private boolean isTwin;

        public BoardNode(Board c, BoardNode p, boolean iT, int moves) {
            if (c == null) throw new NullPointerException("Tried to pass null to BoardNode class");

            current = c;

            prev = p;
            isTwin = iT;
            numOfmoves = moves;
            gvalue = c.manhattan() + moves;
        }

        @Override
        public int compareTo(BoardNode boardNode) {
            if (gvalue > boardNode.gvalue) {
                return 1;
            } else if (gvalue < boardNode.gvalue) {
                return -1;
            } else {
                return 0;
            }

        }

        public String toString() {
            return current.toString();
        }
    }

    /**
     * Solution Deque.
     */
    private Deque<Board> solution;
    /**
     * Queue for solving.
     */
    private MinPQ<BoardNode> queue;
    /**
     * End Goal node.
     */
    private BoardNode goalNode;
    /**
     * Number of moves taken to get to solution.
     */
    private int nummoves;

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        // initialize the queues with correct comparator(start with hamming)(final should be manhattan)
        if (initial == null) throw new IllegalArgumentException();
        queue = new MinPQ<>();
        solution = new ArrayDeque<>();
        queue.insert(new BoardNode(initial, null, false, 0));
        queue.insert(new BoardNode(initial.twin(), null, true, 0));
        nummoves = getGoalNode();
    }

    private int getGoalNode() {
        while (true) {

            BoardNode deqnode = queue.delMin();
            if (deqnode.current.isGoal()) {
                if (deqnode.isTwin) {
                    goalNode = null;
                    return -1;
                } else {
                    goalNode = deqnode;
                    return deqnode.numOfmoves;
                }
            }
            if (deqnode.isTwin) {

                for (Board board : deqnode.current.neighbors()) {
                    if (deqnode.prev == null || !board.equals(deqnode.prev.current)) {
                        queue.insert(new BoardNode(board, deqnode, true, deqnode.numOfmoves + 1));
                    }
                }
            } else {
                for (Board board : deqnode.current.neighbors()) {
                    if (deqnode.prev == null || !board.equals(deqnode.prev.current)) {
                        queue.insert(new BoardNode(board, deqnode, false, deqnode.numOfmoves + 1));
                    }
                }
            }
        }
    }

    public boolean isSolvable() {
        return goalNode != null;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return nummoves;
    }

    public Iterable<Board> solution() {
        if (goalNode != null) {
            BoardNode solNode = goalNode;
            while (solNode != null) { // set a next value in BoardNode class
                solution.addFirst(solNode.current);
                solNode = solNode.prev;
            }
        } else {
            return null;
        }
        // sequence of boards in a shortest solution; null if unsolvable

        return solution;
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
    }
}