package gic.minesweeper.util;

public class MineSweeperUtil {

    public int inputValidate(String userInput){
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
}
