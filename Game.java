//gameplay loop
public class Game{
    int max_day;
    int current_day;
    int scenes_left;
    Player[] players;

    public static void main(String[] args){
        //get player input
        //int n = Controller.getPlayers()
        int player_num = 2;
        players = new Player[player_num];
        setup(player_num);
        Parser parser = new Parser();
        Board board = new Board();
        //setup
        
        int index = 0;

        while(current_day <= max_day){
            while(scenes_left>1){
                if (index < player.length){
                    players[0].takeTurn();
                    index++;
                }
                else{
                    index = 0;
                }

            }
            current_day++;
            board.resetBoard();
        }


    }

    private void setup(player_num){
        //need to set max day, initialize players with correct stats
        board.buildScenes();
        board.buildSets();
        board.buildActingSets();
        board.buildPlayers(player_num);
        board.asignScenes();
    }

    public void buildPlayers(int n){
        for (int i=0; i<=n; i++){
            Player player = new Player("player" + i)
            players[i] = player;
        }
    }
}