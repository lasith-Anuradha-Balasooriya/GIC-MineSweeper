import gic.minesweeper.service.MineSweeperService;

public class MineSweeperGameApplication {

    public static void main(String[] args){

        MineSweeperService mineSweeperService = new MineSweeperService();
        while (true) {
            try{
                mineSweeperService.gameInitiate();
                System.out.println("Press Enter to play again...");
                new java.util.Scanner(System.in).nextLine();
            }catch(Exception e){
                System.out.println("Exception in game initiator "+e);
                break;
            }

        }
    }
}
