package Sudoku_packages;

public class SudokuSolver {

    public static boolean solve(SudokuBoard board) {
        int[][] b = board.getBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (b[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (board.isValid(row, col, num)) {
                            b[row][col] = num;
                            if (solve(board))
                                return true;
                            b[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean fillBoard(int[][] board) {
        SudokuBoard temp = new SudokuBoard();
        temp.loadBoard(board);
        return fill(temp);
    }

    private static boolean fill(SudokuBoard board) {
        int[][] b = board.getBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (b[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (board.isValid(row, col, num)) {
                            b[row][col] = num;
                            if (fill(board))
                                return true;
                            b[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
