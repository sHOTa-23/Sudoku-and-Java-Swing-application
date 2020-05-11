import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {

    @Test
    void testSimple() {
        Sudoku sudo = new Sudoku(Sudoku.hardGrid);
        assertEquals(sudo.solve(), 1);
        assertEquals(sudo.solve(), 1);
        int[][] grid = Sudoku.hardGrid;
        grid[0][1] = 0;
        sudo = new Sudoku(grid);
        assertEquals(sudo.solve(), 6);
        sudo = new Sudoku(Sudoku.easyGrid);
        assertEquals(sudo.solve(), 1);
        sudo = new Sudoku(Sudoku.mediumGrid);
        assertEquals(sudo.solve(), 1);
        String s = "";
        for (int i = 0; i < 81; i++) {
            s += "0";
        }
        sudo = new Sudoku(s);
        assertEquals(sudo.solve(), 100);
        s = "";
        for (int i = 0; i < 80; i++) {
            s += "0";
        }
        s += "1";
        sudo = new Sudoku(s);
        assertEquals(sudo.solve(), 100);
        s = "5 3 4 6 7 8 9 1 2 " +
                "6 7 2 1 9 5 3 4 8 " +
                "1 9 8 3 4 2 5 6 7 " +
                "8 5 9 7 6 1 4 2 3 " +
                "4 2 6 8 5 3 7 9 1 " +
                "7 1 3 9 2 4 8 5 6 " +
                "9 6 1 5 3 7 2 8 4 " +
                "2 8 7 4 1 9 6 3 5 " +
                "3 4 5 2 8 6 1 7 9";
        sudo = new Sudoku(s);
        assertEquals(sudo.solve(), 1);
        s = "5 3 4 6 7 8 9 1 2 " +
                "6 7 2 1 9 5 3 4 8 " +
                "1 9 8 3 4 2 5 6 7 " +
                "8 5 9 7 6 1 4 2 3 " +
                "4 2 6 8 5 3 7 9 1 " +
                "7 1 3 9 2 4 8 5 6 " +
                "9 6 1 5 3 7 2 8 4 " +
                "2 8 7 4 1 9 6 3 5 " +
                "3 4 5 2 8 6 1 7 0";
        sudo = new Sudoku(s);
        assertEquals(sudo.solve(), 1);
        s = "5 3 4 6 7 8 9 1 2 " +
                "6 7 2 1 9 5 3 4 8 " +
                "1 9 8 3 4 2 5 6 7 " +
                "8 5 9 7 6 1 4 2 3 " +
                "4 2 6 8 5 3 7 9 1 " +
                "7 1 3 9 2 4 8 5 6 " +
                "9 6 1 5 3 7 2 8 4 " +
                "2 8 7 4 1 9 6 3 5 " +
                "3 4 5 2 8 6 1 7 5";
        sudo = new Sudoku(s);
        assertEquals(sudo.solve(), 0);

    }

    @Test
    void testComplex() {
        String s = "5 3 4 6 7 8 9 1 2 " +
                "6 7 2 1 9 5 3 4 8 " +
                "1 9 8 3 4 2 5 6 7 " +
                "8 5 9 7 6 1 4 2 3 " +
                "4 2 6 8 5 3 7 9 1 " +
                "7 1 3 9 2 4 8 5 6 " +
                "9 6 1 5 3 7 2 8 4 " +
                "2 8 7 4 1 9 6 3 5 " +
                "3 4 5 2 8 6 1 7 0";
        String s1 = "5 3 4 6 7 8 9 1 2 " +
                "6 7 2 1 9 5 3 4 8 " +
                "1 9 8 3 4 2 5 6 7 " +
                "8 5 9 7 6 1 4 2 3 " +
                "4 2 6 8 5 3 7 9 1 " +
                "7 1 3 9 2 4 8 5 6 " +
                "9 6 1 5 3 7 2 8 4 " +
                "2 8 7 4 1 9 6 3 5 " +
                "3 4 5 2 8 6 1 7 9 ";
        String s2 = "5 3 4 6 7 8 9 1 2 " +
                "6 7 2 1 9 5 3 4 8 " +
                "1 9 8 3 4 2 5 6 7 " +
                "8 5 9 7 6 1 4 2 3 " +
                "4 2 6 8 5 3 7 9 1 " +
                "7 1 3 9 2 4 8 5 6 " +
                "9 6 1 5 3 7 2 8 4 " +
                "2 8 7 4 1 9 6 3 5 " +
                "3 4 5 2 8 6 1 7 5 ";
        String s3 = "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 ";
        String s4 = "1 2 3 4 5 6 7 8 9 " +
                "4 5 6 7 8 9 1 2 3 " +
                "7 8 9 1 2 3 4 5 6 " +
                "2 1 4 3 6 5 8 9 7 " +
                "3 6 5 8 9 7 2 1 4 " +
                "8 9 7 2 1 4 3 6 5 " +
                "5 3 1 6 4 2 9 7 8 " +
                "6 4 2 9 7 8 5 3 1 " +
                "9 7 8 5 3 1 6 4 2";
        String s5 = "23 34 5";
        assertThrows(RuntimeException.class,()->{ new Sudoku(s5); });
        Sudoku sudoo = new Sudoku(s);
        assertEquals("", sudoo.getSolutionText());
        assertEquals(0,sudoo.getElapsed());
        sudoo.solve();
        Sudoku sudo1 = new Sudoku(s1);
        assertEquals(sudo1.toString(), sudoo.getSolutionText());
        sudo1.solve();
        assertEquals(sudo1.getSolutionText(), sudo1.toString());
        sudoo = new Sudoku(s2);
        sudoo.solve();
        assertEquals("", sudoo.getSolutionText());
        sudoo = new Sudoku(s3);
        sudoo.solve();
        assertEquals(sudoo.getSolutionText(),new Sudoku(s4).toString());
        String [] args = { "one", "two", "three" };
        Sudoku.main(args);
        String st = "5 3 3 6 7 8 9 1 2 " +
                "6 7 2 1 9 5 3 4 8 " +
                "1 9 8 3 4 2 5 6 7 " +
                "8 5 9 7 6 1 4 2 3 " +
                "4 2 6 8 5 3 7 9 1 " +
                "7 1 3 9 2 4 8 5 6 " +
                "9 6 1 5 3 7 2 8 4 " +
                "2 8 7 4 1 9 6 3 5 " +
                "3 4 5 2 8 6 1 7 9 ";
        Sudoku sud = new Sudoku(st);
        Sudoku.Spot spot = sud.new Spot(0,2);
        assertEquals(0,spot.getValidNumbersNum());
        assertEquals(0,spot.getX());
        assertEquals(2,spot.getY());
        assertEquals(0,sud.solve());
        spot.setValue(0);
        assertEquals(1,sud.solve());
        spot.setValue(3);
        assertEquals(0,sud.solve());
    }

}