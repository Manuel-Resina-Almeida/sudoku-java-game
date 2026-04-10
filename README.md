# Java Sudoku Game Engine 🧩

## Project Overview
This project was developed for the Introduction to Programming course at ISCTE-IUL (2023/24)[cite: 1]. The objective was to build a complete Sudoku game engine in Java, focusing on logic validation, matrix manipulation, visual rendering, and file I/O[cite: 1]. The project runs within the PandionJ environment, utilizing custom classes for image generation[cite: 1].

## Key Technical Features
* **OOP Architecture:** Divided into distinct classes handling utilities (`SudokuAux`), game state (`SudokuBoard`), and overall game management (`Sudoku`)[cite: 1].
* **Logic & Validation:** Validates 9x9 grids to ensure compliance with Sudoku rules, checking rows, columns, and 3x3 segments for duplicates[cite: 1].
* **Dynamic Board Generation:** Automatically generates a playable board by omitting a specific percentage of numbers from a complete solution[cite: 1].
* **Visual Rendering:** Generates a graphical color representation of the board, highlighting invalid lines or segments with red borders dynamically[cite: 1].
* **State Persistence:** Implements File I/O to save (`*.sudgame`) and load game states and initial board layouts (`*.sud`)[cite: 1].
* **Undo Mechanism:** Allows players to revert moves using an implemented memory system tracking empty starting positions[cite: 1].

## Repository Structure
* **/src:** Contains the Java source code (`SudokuAux.java`, `SudokuBoard.java`, `Sudoku.java`)[cite: 1].
* **/*.sud & *.sudgame files:** Custom file formats used to store initial board solutions and in-progress game states[cite: 1].

## How to Run
This project was designed to be executed within the **PandionJ** environment using the provided `Color` and `ColorImage` classes[cite: 1]. 
To test the game, compile the source files and run the main method in a test class, loading a valid `.sud` file:

```java
public class SudokuTest {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku("sudoku1.sud", 0.5);
    }
}
