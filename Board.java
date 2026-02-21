import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;


//needs XML data to do constructors for various objects
//needs to implement get actingset logic

public class Board{
    ArrayList<Scene> scenes;
    NonActingSet trailer;
    HashMap <String, ActingSet> actingsets;
    CastingOffice castingoffice;
    Parser parser;
    int scenesLeft;

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
            cred = 2;
        }
        else if (player_num==6){
            cred = 4;
        }
        else if (player_num>=7 && player_num<=8){
            rank = 2;
        }
        //set all player stats to proper vals
        for (int i=0; i<players.length; i++){
            players[i].addDollars(dol);
            players[i].setRank(rank);
            players[i].addCredits(cred);
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

    public void removeScene(){
        this.scenesLeft--;
    }

    public int getScenesLeft(){
        return this.scenesLeft;
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


    public ActingSet getActingSet(String name){
        //index into hashmap
        //will placeholder will be null if name is not in actingset
        ActingSet placeholder = this.actingsets.get(name)
        return placeholder;
    }

    public CastingOffice getCastingOffice(String name){
        //index into hashmap
        //will placeholder will be null if name is not in actingset
        CastingOffice placeholder = null;
        if (name == "casting office"){
        placeholder = this.castingoffice;
        }
        return placeholder;
    }

    public void setBoard(){
        //needs to clear scenes, add new scenes, change player location to trailer, reset shot tokens, etc.
        //also set scenesLeft to 10
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