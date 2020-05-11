import org.junit.platform.commons.util.ToStringBuilder;
import sun.reflect.generics.tree.Tree;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
    // Provided grid data for main/testing
    // The instance variable strategy is up to you.

    // Provided easy 1 6 grid
    // (can paste this text into the GUI too)
    public static final int[][] easyGrid = Sudoku.stringsToGrid(
            "1 6 4 0 0 0 0 0 2",
            "2 0 0 4 0 3 9 1 0",
            "0 0 5 0 8 0 4 0 7",
            "0 9 0 0 0 6 5 0 0",
            "5 0 0 1 0 2 0 0 8",
            "0 0 8 9 0 0 0 3 0",
            "8 0 9 0 4 0 2 0 0",
            "0 7 3 5 0 9 0 0 1",
            "4 0 0 0 0 0 6 7 9");


    // Provided medium 5 3 grid
    public static final int[][] mediumGrid = Sudoku.stringsToGrid(
            "530070000",
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079");

    // Provided hard 3 7 grid
    // 1 solution this way, 6 solutions if the 7 is changed to 0
    public static final int[][] hardGrid = Sudoku.stringsToGrid(
            "3 7 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");


    public static final int SIZE = 9;  // size of the whole 9x9 puzzle
    public static final int PART = 3;  // size of each 3x3 part
    public static final int MAX_SOLUTIONS = 100;
    private static int[][] matrix;
    private static List<Spot> spots;
    private static String foundSolution;
    private static boolean foundFirst = false;
    private static long startOfProgram = 0;
    private static long endOfProgram = 0;

    // Provided various static utility methods to
    // convert data formats to int[][] grid.

    /**
     * Returns a 2-d grid parsed from strings, one string per row.
     * The "..." is a Java 5 feature that essentially
     * makes "rows" a String[] array.
     * (provided utility)
     *
     * @param rows array of row strings
     * @return grid
     */
    public static int[][] stringsToGrid(String... rows) {
        int[][] result = new int[rows.length][];
        for (int row = 0; row < rows.length; row++) {
            result[row] = stringToInts(rows[row]);
        }
        return result;
    }


    /**
     * Given a single string containing 81 numbers, returns a 9x9 grid.
     * Skips all the non-numbers in the text.
     * (provided utility)
     *
     * @param text string of 81 numbers
     * @return grid
     */
    public static int[][] textToGrid(String text) {
        int[] nums = stringToInts(text);
        if (nums.length != SIZE * SIZE) {
            throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
        }

        int[][] result = new int[SIZE][SIZE];
        int count = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result[row][col] = nums[count];
                count++;
            }
        }
        return result;
    }


    /**
     * Given a string containing digits, like "1 23 4",
     * returns an int[] of those digits {1 2 3 4}.
     * (provided utility)
     *
     * @param string string containing ints
     * @return array of ints
     */
    public static int[] stringToInts(String string) {
        int[] a = new int[string.length()];
        int found = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                a[found] = Integer.parseInt(string.substring(i, i + 1));
                found++;
            }
        }
        int[] result = new int[found];
        System.arraycopy(a, 0, result, 0, found);
        return result;
    }


    // Provided -- the deliverable main().
    // You can edit to do easier cases, but turn in
    // solving hardGrid.
    public static void main(String[] args) {
        Sudoku sudoku;
        sudoku = new Sudoku(hardGrid);
        System.out.println(sudoku); // print the raw problem
        int count = sudoku.solve();
        System.out.println("solutions:" + count);
        System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
        System.out.println(sudoku.getSolutionText());
    }

    /**
     * Sets up based on the given ints.
     */
    public Sudoku(int[][] ints) {
        matrix = ints;
    }

    public Sudoku(String gridInStrings) {
        matrix = textToGrid(gridInStrings);
    }

    private int solveNum = 0;

    /**
     * Solves the puzzle, invoking the underlying recursive search.
     */
    public int solve() {
        foundFirst = false;
        solveNum = 0;
        startOfProgram = 0;
        endOfProgram = 0;
        spots = getFreeSpots();
        if (spots.size() != 0) {
            startOfProgram = System.currentTimeMillis();
            solveSudoku(0);
            endOfProgram = System.currentTimeMillis();
            return solveNum;
        } else return checkFullSudokus();
    }

    public String getSolutionText() {
        if (foundFirst) return foundSolution;
        return "";
    }

    public long getElapsed() {
        return endOfProgram - startOfProgram;
    }

    @Override
    public String toString() {
        //make string which will add new line separator to our string
        String newLine = System.getProperty("line.separator");
        String str = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                str += Integer.toString(matrix[i][j]);
                str += " ";
            }
            str += newLine;
        }
        return str;
    }

    private int checkFullSudokus() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Spot spot = new Spot(i, j);
                int num = matrix[i][j];
                spot.setValue(0);
                if (!checkSudoku(i, j, num)) {
                    foundSolution = "";
                    //spot.setValue(num);
                    return 0;
                }
                spot.setValue(num);
            }
        }
        foundFirst = true;
        foundSolution = toString();
        return 1;
    }

    private boolean solveSudoku(int index) {
        if (solveNum == MAX_SOLUTIONS) return false;
        if (index == spots.size()) {
            if (!foundFirst) {
                foundFirst = true;
                foundSolution = toString();
            }
            solveNum++;
            return true;
        }
        int counter = 0;
        for (int i = 1; i <= SIZE; i++) {
            if (checkSudoku(spots.get(index).getX(), spots.get(index).getY(), i)) {
                spots.get(index).setValue(i);
                if (!solveSudoku(index + 1)) counter++;
            } else counter++;
            spots.get(index).setValue(0);
        }
        return counter != 9;
    }

    private List<Spot> getFreeSpots() {
        List<Spot> sortedSpots = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] == 0) {
                    sortedSpots.add(getSpot(i, j));
                }
            }
        }
        sortedSpots.sort(Comparator.comparingInt(Spot::getValidNumbersNum));
        return sortedSpots;
    }

    private Spot getSpot(int row, int coll) {
        Spot spot = new Spot(row, coll);
        for (int i = 1; i <= SIZE; i++) {
            if (checkSudoku(row, coll, i)) spot.setValidNumbersNum(spot.getValidNumbersNum() + 1);
        }
        spot.setValue(0);
        return spot;
    }

    private static boolean checkSudoku(int row, int coll, int num) {
        return checkSquare(row, coll, num) && checkCollumn(row, coll, num) && checkRow(row, coll, num);
    }

    private static boolean checkSquare(int row, int coll, int num) {
        int squareRowIndex = (row / 3) * 3;
        int squareCollIndex = (coll / 3) * 3;
        for (int i = squareRowIndex; i < squareRowIndex + 3; i++) {
            for (int j = squareCollIndex; j < squareCollIndex + 3; j++) {
                if (matrix[i][j] == num) return false;
            }
        }
        return true;
    }

    private static boolean checkCollumn(int row, int coll, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (matrix[i][coll] == num) return false;
        }
        return true;
    }

    private static boolean checkRow(int row, int coll, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (matrix[row][i] == num) return false;
        }
        return true;
    }

    public class Spot {
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        private int x;
        private int y;
        private int validNumbersNum;

        public Spot(int x, int y) {
            this.x = x;
            this.y = y;
            validNumbersNum = 0;
        }

        public int getValidNumbersNum() {
            return validNumbersNum;
        }

        public void setValidNumbersNum(int validNumbersNum) {
            this.validNumbersNum = validNumbersNum;
        }

        public void setValue(int value) {
            matrix[x][y] = value;
        }
    }

}
