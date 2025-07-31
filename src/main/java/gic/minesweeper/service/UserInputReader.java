package gic.minesweeper.service;

import gic.minesweeper.model.CellPosition;
import gic.minesweeper.model.UserInput;
import gic.minesweeper.util.MineSweeperUtil;

import java.util.Scanner;

public class UserInputReader {

    private final Scanner scanner = new Scanner(System.in);

    protected UserInput getInitialUserInput() {

        //reading user input to start game.
        UserInput userInput = new UserInput();
        try {
            while (true) {
                int allowMineCount;
                while (true) {
                    System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
                    String gridSize = scanner.nextLine();
                    int grid = MineSweeperUtil.inputValidate(gridSize);
                    if (grid != 0) {
                        userInput.setGridSize(grid);
                        break;
                    }
                }
                while (true) {
                    allowMineCount = (int) Math.floor(userInput.getGridSize() * userInput.getGridSize() * 0.35);
                    System.out.printf("Enter the number of mines to place on the grid (maximum is %d): ", allowMineCount);
                    String minesNum = scanner.nextLine();
                    int mine = MineSweeperUtil.inputValidate(minesNum);
                    if (mine != 0) {
                        userInput.setMinesCount(mine);
                        break;
                    }
                }
                if (userInput.getMinesCount() <= allowMineCount) {
                    break;
                } else {
                    System.out.println("Too many mines. Try again.");
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in Uer input reader method " + e);
        }
        return userInput;
    }

    protected CellPosition getCellInput(int gridSize) {
        CellPosition cellPosition = null;
        try {
            // reading user input for cell selection
            System.out.print("Select a square to reveal (e.g. A1): ");
            String inputCell = scanner.nextLine();
            cellPosition = MineSweeperUtil.converter(inputCell, gridSize);
        } catch (Exception e) {
            System.out.println("Exception in getCellInput " + e);
        }
        return cellPosition;
    }
}

