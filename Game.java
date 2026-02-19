//gameplay loop
public class Game{
    int max_day;
    int current_day;
    int scenes_left;

    public static void main(String[] args){
        //get player input
        //int n = Controller.getPlayers()
        int player_num = 2;
        setup(player_num);
        Parser parser = new Parser();
        Board board = new Board();
        //setup
        

        while()


    }

    private void setup(player_num){
        //need to set max day, initialize players with correct stats
        board.buildScenes();
        board.buildSets();
        board.buildActingSets();
        board.buildPlayers(player_num);
        board.asignScenes();

    }
}