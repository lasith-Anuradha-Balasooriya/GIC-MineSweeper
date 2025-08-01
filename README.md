# GIC-MineSweeper
Welcome to Minesweeper

This is a command-line version of Minesweeper game which is built using Java 17 with Maven. This game can be played in the terminal.

Objectives
----------
This project was developed with the following objectives:
- Clean Object-Oriented Design
- Adherence to SOLID Principles
- Test-Driven Development (TDD) Approach

Technologies Used
-----------------
- Java 17
- Maven 4.0
- JUnit 5.10
- Mockito 5.11

Prerequisites
-------------
- Java 17 or later installed
- Maven 4.0 or later installed
- Terminal or Command Prompt for running the game

Project Structure
-----------------

```text
GIC-MineSweeper [minesweeper]
├── .idea/                         # IntelliJ project settings
├── pom.xml                        # Maven build configuration
├── README.md                      # Project documentation
├── src/
│   └── main/
│       └── java/
│           └── gic/
│               └── minesweeper/
│                   ├── model/
│                   │   ├── CellPosition.java
│                   │   ├── GameGrid.java
│                   │   ├── GridCell.java
│                   │   └── UserInput.java
│                   ├── service/
│                   │   ├── MineSweeperService.java
│                   │   └── UserInputReader.java
│                   ├── util/
│                   │   └── MineSweeperUtil.java   
│                   └── MineSweeperGameApplication.java
├── test/                          # Unit test source folder
│   └── (test classes here)
└── target/                        # Maven build output
``` 

Build and Run
-------------
- Compile the Project

	mvn clean install
 
- Run the Game

	java -cp target/minesweeper-game-1.0.jar gic.minesweeper.MineSweeperGameApplication
 
Running Tests
-------------
- Run all unit tests using

	mvn test


How to Play Minesweeper (CLI Version)
-------------------------------------

1. Choose the Grid Size

	When the game starts, you’ll be asked to enter the size of the grid — for example, 4 for a 4×4 grid. You can choose any reasonable size based on how easy or hard you want the game to be.

2. Set the Number of Mines

	Next, you’ll enter how many mines you want to place on the grid. The game will suggest a safe maximum number based on the grid size. You must enter a number within that limit to continue.

3. View the Game Board

	The grid will be displayed with labeled rows (A, B, C...) and numbered columns (1, 2, 3...). All cells are hidden at first.

4. Select a Cell to Reveal

	Type the label of the cell you want to reveal, like A1 or C3.
	- If the cell is safe, it will be revealed.

	- If it's empty, nearby empty cells may automatically be uncovered too.

5. Game Rules in Action

	The game follows standard Minesweeper rules: reveal safe cells, avoid mines.

	- Revealing a mine ends the game — 💥 Game Over!

	- Reveal all non-mine cells to win — 🏆 You Win!

6. Win or Lose

	- You win by uncovering all safe cells (without hitting any mines).

	- You lose if you reveal a cell that contains a mine.
	
7. Playing Again

	After each game — whether you win or lose — you’ll be asked if you want to play again.

Author
------
Lasith Anuradha Balasooriya