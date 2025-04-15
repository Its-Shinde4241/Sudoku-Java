# 🧩 Sudoku Game in Java

A fully-featured Java Sudoku game with an interactive GUI built using Swing. Challenge yourself with puzzles of varying difficulty levels and enhance your logical thinking skills!

## 🎯 Features

- ✅ Interactive 9x9 Sudoku board with visual grid separators
- 🎨 User-friendly GUI built with Java Swing
- 🧠 Solve puzzles automatically with backtracking algorithm
- 💡 Get hints when you're stuck on a difficult cell
- 🔄 Reset the puzzle to its initial state
- 💾 Save and load puzzle progress from `.sudoku` files
- 🎲 Generate new puzzles with three difficulty levels: Easy, Medium, Hard
- 🚫 Input validation ensures only digits 1-9 are allowed
- 🔍 Solution checking with visual error highlighting

  
## Snapshots

![image](https://github.com/user-attachments/assets/f155e571-bbdc-4f2c-b85e-f9f8e0b4dd1f)

## 🛠️ Technologies Used

- Java (Object-Oriented Programming structure)
- Java Swing (for the graphical user interface)
- MVC-like separation of concerns

## 📁 Project Structure

- **Main.java**: Main application with GUI components and event handlers
- **Sudoku_packages/**
  - **SudokuBoard.java**: Core board representation with validation logic
  - **SudokuGenerator.java**: Puzzle creation with different difficulties
  - **SudokuSolver.java**: Implementation of solving algorithm

## 🚀 How to Run

1. Clone the repository to your local machine:
   ```
   git clone https://github.com/Its-Shinde4241/Sudoku-Java.git
   cd Sudoku-Java
   ```

2. Ensure you have Java Development Kit (JDK) installed

3. Compile all Java files:
   ```
   javac Main.java
   javac Sudoku_packages/*.java
   ```

4. Run the main class:
   ```
   java Main
   ```

## 🎮 Controls

- **New Puzzle**: Generate a new puzzle with selected difficulty
- **Solve**: Automatically solve the current puzzle
- **Check**: Validate your current solution and highlight errors
- **Reset**: Return to the initial puzzle state (requires confirmation)
- **Hint**: Get help for your next move with a highlighted suggestion
- **Save/Load**: Store and retrieve puzzle progress to continue later
- **Exit**: Close the application (with confirmation dialog)

## 📱 User Interface

- Light gray cells: Preset values that cannot be modified
- White cells: Editable positions for player input
- Red cells: Indicate invalid entries after checking
- Light blue cells: Hint values provided by the hint feature

## 🧠 Game Logic

- Sudoku rules: Fill the 9×9 grid so that each column, row, and 3×3 box contains the digits 1-9
- The solver uses a recursive backtracking algorithm to find solutions
- The generator creates puzzles by solving an empty board and then removing numbers based on difficulty
