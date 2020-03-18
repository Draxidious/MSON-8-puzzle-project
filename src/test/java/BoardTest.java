import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class BoardTest {

    Board board;


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
        int[][] arr = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board b = new Board(arr);
        Solver solver = new Solver(b);

    }

    @Test
    public void testSelectFile() {
        generateBoard("puzzle4x4-32.txt");
    }

    @Test
    public void test2x2()//handles bulk
    {
        String s = "puzzle2x2-0";
        for (int i = 0; i <= 6; i++) {
            generateBoard(s + i + ".txt");
        }
    }

    @Test
    public void test3x3()//handles bulk
    {
        String s1 = "puzzle3x3-0";
        String s2 = "puzzle3x3-";
        for (int i = 0; i <= 31; i++) {
            if (i > 9) {
                generateBoard(s2 + i + ".txt");
            } else {
                generateBoard(s1 + i + ".txt");
            }

        }
    }

    @Test
    public void test4x4()//handles bulk
    {
        String s1 = "puzzle4x4-0";
        String s2 = "puzzle4x4-";
        for (int i = 0; i <= 50; i++) {
            if (i > 9) {
                generateBoard(s2 + i + ".txt");
            } else {
                generateBoard(s1 + i + ".txt");
            }

        }
    }

    @Test
    public void testMan_Ham() {
        int[][] arr = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(arr);
        System.out.println("ham:" + b.hamming());
        System.out.println("man:" + b.manhattan());

    }

    @Test
    public void hamming() {

    }

    @Test
    public void testSegments() {
        //fail("Not yet implemented");
    }

}
