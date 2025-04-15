import Sudoku_packages.SudokuBoard;
import Sudoku_packages.SudokuGenerator;
import Sudoku_packages.SudokuSolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Set up JFrame
        JFrame frame = new JFrame("Sudoku Game");
        frame.setSize(600, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the Sudoku board with initial empty state
        final SudokuBoard[] board = {new SudokuBoard()};

        // Create a preset puzzle for initial loading
        final int[][] initialPuzzle = {
                {0, 2, 0, 4, 0, 0, 7, 8, 9},
                {4, 5, 0, 7, 0, 9, 1, 0, 3},
                {7, 8, 0, 1, 2, 0, 4, 5, 6},
                {0, 1, 0, 0, 6, 0, 8, 0, 7},
                {3, 6, 0, 8, 9, 7, 2, 0, 4},
                {8, 9, 7, 0, 1, 4, 0, 0, 0},
                {5, 3, 1, 0, 4, 2, 9, 7, 8},
                {6, 4, 0, 0, 0, 8, 5, 3, 0},
                {0, 0, 0, 5, 3, 0, 6, 0, 2}
        };
        board[0].loadBoard(initialPuzzle);

        // Create the panel for the Sudoku grid
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Array of JTextFields for the Sudoku grid
        JTextField[][] textFields = new JTextField[9][9];

        // Populate the grid with JTextFields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField(2);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setFont(new Font("Arial", Font.PLAIN, 22));
                textField.setBackground(Color.WHITE);
                textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                textField.setOpaque(true);
                textFields[i][j] = textField;

                // Add input verification to only accept digits 1-9
                textField.setInputVerifier(new InputVerifier() {
                    @Override
                    public boolean verify(JComponent input) {
                        JTextField field = (JTextField) input;
                        String text = field.getText();

                        // Check if input is a number and between 1 and 9
                        if (text.isEmpty()) {
                            return true; // Empty is allowed for empty cells
                        }

                        try {
                            int value = Integer.parseInt(text);
                            return value >= 1 && value <= 9; // Only allow 1-9
                        } catch (NumberFormatException e) {
                            return false; // Reject non-numeric input
                        }
                    }
                });

                // Add double lines for 3x3 grid separation
                if (i % 3 == 0 && i != 0) {
                    textField.setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.BLACK)); // Double horizontal border
                }
                if (j % 3 == 0 && j != 0) {
                    textField.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.BLACK)); // Double vertical border
                }
                // Corner cells need special treatment
                if (i % 3 == 0 && i != 0 && j % 3 == 0 && j != 0) {
                    textField.setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.BLACK));
                }

                if (board[0].getValue(i, j) != 0) {
                    textField.setText(String.valueOf(board[0].getValue(i, j)));
                    textField.setEditable(false);
                    textField.setBackground(Color.LIGHT_GRAY);  // Highlight preset values
                }

                panel.add(textField);
            }
        }

        // Add the board panel to the JFrame
        frame.add(panel, BorderLayout.CENTER);

        // Create a control panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Create a new panel for difficulty selection
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout());
        difficultyPanel.setBackground(new Color(240, 240, 240));

        // Create label and dropdown for difficulty selection
        JLabel difficultyLabel = new JLabel("Difficulty: ");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 16));

        String[] difficulties = {"Easy", "Medium", "Hard"};
        JComboBox<String> difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setFont(new Font("Arial", Font.BOLD, 16));

        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyComboBox);

        // Create a button to generate new puzzle
        JButton newPuzzleButton = new JButton("New Puzzle");
        newPuzzleButton.setBackground(new Color(65, 105, 225));
        newPuzzleButton.setFont(new Font("Arial", Font.BOLD, 16));
        newPuzzleButton.setForeground(Color.WHITE);
        newPuzzleButton.setFocusPainted(false);

        difficultyPanel.add(newPuzzleButton);

        // Create buttons with improved styling
        JButton solveButton = new JButton("Solve");
        JButton checkButton = new JButton("Check");
        JButton resetButton = new JButton("Reset");
        JButton exitButton = new JButton("Exit");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton hintButton = new JButton("Hint");

        // Style buttons
        solveButton.setBackground(new Color(0, 204, 0));
        solveButton.setFont(new Font("Arial", Font.BOLD, 16));
        solveButton.setForeground(Color.WHITE);
        solveButton.setFocusPainted(false);

        checkButton.setBackground(new Color(255, 165, 0));
        checkButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);

        resetButton.setBackground(new Color(255, 69, 0));
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);

        exitButton.setBackground(new Color(255, 0, 0));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        saveButton.setBackground(new Color(25, 25, 112));
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        loadButton.setBackground(new Color(70, 130, 180));
        loadButton.setFont(new Font("Arial", Font.BOLD, 16));
        loadButton.setForeground(Color.WHITE);
        loadButton.setFocusPainted(false);

        hintButton.setBackground(new Color(148, 0, 211));
        hintButton.setFont(new Font("Arial", Font.BOLD, 16));
        hintButton.setForeground(Color.WHITE);
        hintButton.setFocusPainted(false);

        // Add buttons to button panel
        buttonPanel.add(solveButton);
        buttonPanel.add(checkButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(hintButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exitButton);

        // Create a panel to hold both the difficulty selection and button panels
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(difficultyPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add control panel to frame
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Action listener for the "New Puzzle" button
        newPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected difficulty
                String difficulty = (String) difficultyComboBox.getSelectedItem();

                // Generate a new board based on difficulty
                board[0] = SudokuGenerator.generateBoard(difficulty.toLowerCase());

                // Update UI with the new board
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (board[0].getValue(i, j) != 0) {
                            textFields[i][j].setText(String.valueOf(board[0].getValue(i, j)));
                            textFields[i][j].setEditable(false);
                            textFields[i][j].setBackground(Color.LIGHT_GRAY);
                        } else {
                            textFields[i][j].setText("");
                            textFields[i][j].setEditable(true);
                            textFields[i][j].setBackground(Color.WHITE);
                        }
                    }
                }

                JOptionPane.showMessageDialog(frame, "New " + difficulty + " puzzle generated!", "New Puzzle", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Action listener for the "Solve" button
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Extract values from the text fields into the SudokuBoard
                int[][] boardValues = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        String text = textFields[i][j].getText();
                        if (!text.isEmpty()) {
                            boardValues[i][j] = Integer.parseInt(text);
                        } else {
                            boardValues[i][j] = 0;
                        }
                    }
                }
                board[0].loadBoard(boardValues);

                // Solve the Sudoku
                if (SudokuSolver.solve(board[0])) {
                    JOptionPane.showMessageDialog(frame, "Sudoku Solved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Update the UI with the solved board
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            textFields[i][j].setText(String.valueOf(board[0].getValue(i, j)));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Unable to solve the Sudoku.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the "Check" button
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                boolean isComplete = true;

                // First, update the board with current values
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        String text = textFields[i][j].getText();
                        if (!text.isEmpty()) {
                            board[0].setValue(i, j, Integer.parseInt(text));
                        } else {
                            board[0].setValue(i, j, 0);
                            isComplete = false;
                        }
                    }
                }

                // Check each cell
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        String text = textFields[i][j].getText();
                        if (!text.isEmpty()) {
                            int value = Integer.parseInt(text);
                            // Temporarily remove the value to check if it's valid
                            int temp = board[0].getValue(i, j);
                            board[0].setValue(i, j, 0);
                            if (!board[0].isValid(i, j, value)) {
                                textFields[i][j].setBackground(Color.RED);  // Invalid value
                                isValid = false;
                            } else {
                                // Only change non-preset cells
                                if (textFields[i][j].isEditable()) {
                                    textFields[i][j].setBackground(Color.WHITE);  // Valid value
                                }
                            }
                            // Restore the value
                            board[0].setValue(i, j, temp);
                        }
                    }
                }

                if (isValid && isComplete) {
                    JOptionPane.showMessageDialog(frame, "Congratulations! The puzzle is solved correctly!", "Complete", JOptionPane.INFORMATION_MESSAGE);
                } else if (isValid) {
                    JOptionPane.showMessageDialog(frame, "So far so good! Keep going!", "Valid", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "The board has errors. Red cells contain invalid values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the "Reset" button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirm reset
                int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to reset the board?", "Confirm Reset", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // Get the current board's initial values (cells that are not editable)
                    int[][] resetBoard = new int[9][9];
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (!textFields[i][j].isEditable()) {
                                String text = textFields[i][j].getText();
                                if (!text.isEmpty()) {
                                    resetBoard[i][j] = Integer.parseInt(text);
                                }
                            } else {
                                resetBoard[i][j] = 0;
                            }
                        }
                    }

                    // Reset the board to only show preset values
                    board[0].loadBoard(resetBoard);
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (!textFields[i][j].isEditable()) {
                                // Keep preset cells as they are
                            } else {
                                textFields[i][j].setText("");
                                textFields[i][j].setBackground(Color.WHITE);
                            }
                        }
                    }
                }
            }
        });

        // Action listener for the "Hint" button
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a copy of the current board
                SudokuBoard solveBoard = new SudokuBoard();
                int[][] currentValues = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        String text = textFields[i][j].getText();
                        if (!text.isEmpty()) {
                            currentValues[i][j] = Integer.parseInt(text);
                        } else {
                            currentValues[i][j] = 0;
                        }
                    }
                }
                solveBoard.loadBoard(currentValues);

                // Solve the board copy
                if (SudokuSolver.solve(solveBoard)) {
                    // Find an empty cell to provide a hint
                    boolean hintProvided = false;
                    for (int i = 0; i < 9 && !hintProvided; i++) {
                        for (int j = 0; j < 9 && !hintProvided; j++) {
                            if (textFields[i][j].getText().isEmpty() && textFields[i][j].isEditable()) {
                                textFields[i][j].setText(String.valueOf(solveBoard.getValue(i, j)));
                                textFields[i][j].setBackground(new Color(173, 216, 230)); // Light blue for hint
                                hintProvided = true;
                            }
                        }
                    }

                    if (!hintProvided) {
                        JOptionPane.showMessageDialog(frame, "No empty cells to provide a hint for!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Current board configuration cannot be solved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the "Save" button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save Sudoku Game");
                    int userSelection = fileChooser.showSaveDialog(frame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        if (!fileToSave.getName().toLowerCase().endsWith(".sudoku")) {
                            fileToSave = new File(fileToSave.getAbsolutePath() + ".sudoku");
                        }

                        FileWriter writer = new FileWriter(fileToSave);

                        // First save which cells are preset (not editable)
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                writer.write(textFields[i][j].isEditable() ? "0 " : "1 ");
                            }
                            writer.write("\n");
                        }

                        writer.write("---\n"); // Separator

                        // Then save the current values
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                String text = textFields[i][j].getText();
                                if (!text.isEmpty()) {
                                    writer.write(text + " ");
                                } else {
                                    writer.write("0 ");
                                }
                            }
                            writer.write("\n");
                        }

                        writer.close();
                        JOptionPane.showMessageDialog(frame, "Game saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the "Load" button
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Load Sudoku Game");
                    int userSelection = fileChooser.showOpenDialog(frame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToLoad = fileChooser.getSelectedFile();
                        Scanner scanner = new Scanner(fileToLoad);

                        // Load which cells are preset
                        boolean[][] isPreset = new boolean[9][9];
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                isPreset[i][j] = scanner.nextInt() == 1;
                            }
                        }

                        // Skip the separator
                        scanner.nextLine(); // consume remaining of the line
                        scanner.nextLine(); // consume the separator line

                        // Load the current state
                        int[][] currentState = new int[9][9];
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                currentState[i][j] = scanner.nextInt();
                            }
                        }

                        scanner.close();

                        // Update the board and UI
                        board[0].loadBoard(currentState);

                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                if (currentState[i][j] != 0) {
                                    textFields[i][j].setText(String.valueOf(currentState[i][j]));
                                } else {
                                    textFields[i][j].setText("");
                                }

                                if (isPreset[i][j]) {
                                    textFields[i][j].setEditable(false);
                                    textFields[i][j].setBackground(Color.LIGHT_GRAY);
                                } else {
                                    textFields[i][j].setEditable(true);
                                    textFields[i][j].setBackground(Color.WHITE);
                                }
                            }
                        }

                        JOptionPane.showMessageDialog(frame, "Game loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the "Exit" button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirm exit
                int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Set the frame visible
        frame.setVisible(true);
    }
}