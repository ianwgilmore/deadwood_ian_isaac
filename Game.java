//gameplay loop
public class Game{
    public static void main(String[] args){
        int max_day;
        int current_day;
        int scenes_left;
        Player[] players;

        //get player input
        //int n = Controller.getPlayers()
        int player_num = 2;
        Parser parser = new Parser();
        Board board = new Board();
        Checker checker = new Checker();
        //setup game in board class
        players = board.setup(player_num, checker);
        max_day = board.playerDependentSetup(player_num);

        int index = 0;
        current_day = 0;
        while(current_day <= max_day){
            //set up board for new day
            board.setBoard();
            scenes_left = 10;
            while(scenes_left>1){
                if (index < players.length){
                    players[0].takeTurn();
                    index++;
                }
                else{
                    index = 0;
                }

            }
            current_day++;
        }


    }

    

    
}