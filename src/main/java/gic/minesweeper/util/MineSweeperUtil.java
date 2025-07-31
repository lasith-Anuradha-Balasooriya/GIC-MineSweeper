package gic.minesweeper.util;

import gic.minesweeper.model.CellPosition;

public class MineSweeperUtil {

    private MineSweeperUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static int inputValidate(String userInput){
        //validate user input as a non-empty int
        int value = 0;
        try{
            if(userInput == null || userInput.trim().isEmpty()){
                System.out.println("Input is Empty ");
            }else{
                try{
                    value = Integer.parseInt(userInput.trim());
                }catch (Exception e){
                    System.out.println("Invalid Input ");
                }
            }
        }catch (Exception e){
            System.out.println("Exception in inputValidate "+e);
        }
        return value;
    }

    public static CellPosition converter(String input, int size) {
        // calculate the cell position based on input
        if (input == null || input.length() < 2) {
            return null;
        }
        char rowChar = input.charAt(0);
        int row = rowChar - 'A';
        try {
            int col = Integer.parseInt(input.substring(1)) - 1;
            if (row < 0 || row >= size || col < 0 || col >= size){
                return null;
            }
            return new CellPosition(row, col);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
