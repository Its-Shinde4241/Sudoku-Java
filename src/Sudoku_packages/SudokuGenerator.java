package Sudoku_packages;
import java.util.Random;

public class SudokuGenerator {

    public static SudokuBoard generateBoard(String difficulty) {
        SudokuBoard board = new SudokuBoard();
        int[][] solvedBoard = new int[9][9];
        SudokuSolver.fillBoard(solvedBoard);
        int[][] puzzle = removeNumbers(solvedBoard, difficulty);
        board.loadBoard(puzzle);
        return board;
    }

    private static int[][] removeNumbers(int[][] board, String difficulty) {
        int[][] puzzle = new int[9][9];
        int blanks;

        switch (difficulty.toLowerCase()) {
            case "easy": blanks = 30; break;
            case "medium": blanks = 40; break;
            case "hard": blanks = 50; break;
            default: blanks = 30;
        }

        for (int i = 0; i < 9; i++)
            System.arraycopy(board[i], 0, puzzle[i], 0, 9);

        Random rand = new Random();
        while (blanks > 0) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0;
                blanks--;
            }
        }
        return puzzle;
    }
}
