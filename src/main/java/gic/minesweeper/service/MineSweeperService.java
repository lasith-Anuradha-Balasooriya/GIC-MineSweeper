package gic.minesweeper.service;

import gic.minesweeper.model.CellPosition;
import gic.minesweeper.model.GameGrid;
import gic.minesweeper.model.UserInput;

public class MineSweeperService {
    UserInputReader userInputReader = new UserInputReader();

    public void gameInitiate() {
        System.out.println("Welcome to Minesweeper!\n");

        try {
            UserInput userInput = userInputReader.getInitialUserInput();

            if (userInput.getGridSize()!=0 && userInput.getMinesCount()!=0){

                GameGrid gameGrid = new GameGrid(userInput.getGridSize(), userInput.getMinesCount());
                gameGrid.initialize();

                while (!gameGrid.isGameOver()) {
                    gameGrid.print();

                    CellPosition cellPosition = userInputReader.getCellInput(userInput.getGridSize());
                    if (cellPosition == null || !gameGrid.isValid(cellPosition)) {
                        System.out.println("Invalid input. Try again.");
                        continue;
                    }
                    gameGrid.reveal(cellPosition);
                    if (gameGrid.isGameLost()) {
                        gameGrid.printWithMines();
                        System.out.println("Oh no, you detonated a mine! Game over.");
                        return;
                    }
                    if (gameGrid.isGameWon()) {
                        gameGrid.print();
                        System.out.println("Congratulations, you have won the game!");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in gameInitiate "+e);
        }
    }
}
