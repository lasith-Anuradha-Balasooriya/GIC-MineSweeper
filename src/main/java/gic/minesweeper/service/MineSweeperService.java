package gic.minesweeper.service;

import gic.minesweeper.model.UserInput;

public class MineSweeperService {
    UserInputReader userInputReader = new UserInputReader();

    public void gameInitiate() {
        System.out.println("Welcome to Minesweeper!");
        UserInput userInput = userInputReader.getUserInputReader();
    }


}
