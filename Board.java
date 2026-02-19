import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class Board{
    ArrayList<Scene> scenes;
    ArrayList<Set>  sets;
    HashMap <Set, ActingSet> actingsets;
    Set trailer;
    Parser parser;

    public Board() {
        this.parser = new Parser();
    }

    public int playerDependentSetup(int player_num, Player[] players){
        int cred = 0;
        int dol = 0;
        int rank = 1;
        int max_days = 4;
        //all player number related game states
        if(player_num>1 && player_num<=3){
            max_days = 3;
        }
        else if (player_num==5){
            credits = 2;
        }
        else if (player_num==6){
            credits = 4;
        }
        else if (player_num>=7 && player_num<=8){
            rank = 2;
        }
        //set all player stats to proper vals
        for (int i=0; i<players.length; i++){
            players[i].addDollars(dol);
            players[i].setRank(rank);
            players[i].addCredits(credits);
        }
        return max_days;

    }

    public Player[] setup(int player_num, Checker checker){
        //need to set max day, initialize players with correct stats!!!!!!!!!
        buildScenes();
        buildSets();
        buildActingSets();
        Player[] players = buildPlayers(player_num, checker);
        return players;
    }

    private void buildScenes(){
        //import data from xml
    }
    //get random scene
    public Scene getScene(){
        Random random = new Random();
        int index = random.nextInt(scenes.size());
        Scene getscene = scenes.get(index);
        scenes.remove(index);
        return getscene;
    }

    private void buildSets(){
        //needs to take parsed data as well
    }

    private void buildActingSets(){
        //needs also takes parsed data, need to differentiate the ones that are actingsets
    }


    private void assignScenes(){
        //iterate through hashmap of actingsets to assign scenes randomly
        actingsets.forEach( (s,a) ->
            {
                a.setScene(getScene());
            });
            
    }


    public ActingSet getActingSet(Set set){
        //index into hashmap
        ActingSet placeholder = new ActingSet("placeholder", 1, 1);
        return placeholder;
    }

    public void setBoard(){
        //needs to clear scenes, add new scenes, change player location to trailer, reset shot tokens, etc.
        assignScenes();
    }

    public Player[] buildPlayers(int n, Checker checker){
        Player[] players = new Player[n];
        String name;
        for (int i=0; i<=n; i++){
            name = "player"+i;
            Player player = new Player(name, null,checker);
            players[i] = player;
        }
        return players;
    }

}