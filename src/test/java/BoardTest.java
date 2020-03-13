import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class BoardTest {

    Board board;

    @Before
    public void setUp() throws Exception {
        board = generateBoard("puzzle01.txt");
    }

    private Board generateBoard(String filename) {
        // create initial board from file
        In in = new In("8puzzle-test-files/" + filename); // name of file is the min number of moves needed to do it
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
        return initial;
    }

    @Test
    public void testDimension() {
        //fail("Not yet implemented");
    }

    @Test
    public void testSolve() {

    }

    @Test
    public void testSegments() {
        //fail("Not yet implemented");
    }

}
