//gameplay loop

//COMPLETED until test
//need to implement a way to ensure turns end
public class Game{
    public static void main(String[] args){
        int max_day;
        int current_day;
        int scenes_left;
        Player[] players;

        //get player input
        //int n = Controller.getPlayers()

        Parser parser = new Parser();
        Board board = new Board();
        Checker checker = new Checker();
        Controller controller = new Controller();
        int player_num = controller.setup();
        players = new Player[player_num];
        //setup game in board class
        players = board.setup(player_num, checker);
        max_day = board.playerDependentSetup(player_num, players);

                

        int index = 0;
        current_day = 1;
        while(current_day <= max_day){
            controller.newDay(current_day);
            //set up board for new day
            board.setBoard(players);
            while(board.getScenesLeft()>1){
                if (index < players.length){
                    ///currently for some reason the players name is 2. this is not correct
                    players[0].takeTurn(board, controller, false);
                    index++;
                }
                else{
                    index = 0;
                }

            }
            current_day++;
        }
        //calc scores, display scores
        //board.results(players);
    }

    

    
}