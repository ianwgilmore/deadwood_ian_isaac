import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;



public class Board{
    List<Scene> scenes;
    NonActingSet trailer;
    HashMap <String, ActingSet> actingsets;
    CastingOffice castingoffice;
    Parser parser;
    int scenesLeft;

    public Board() {
        this.parser = new Parser();
    }

    public void buildCastingOffice(){

    }

    public void buildTrailer(){

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
            players[i] = new Player(String.valueOf(player_num), "trailer", new Checker());
            players[i].addDollars(dol);
            players[i].setRank(rank);
            players[i].addCredits(cred);
        }
        return max_days;

    }

    public Player[] setup(int player_num, Checker checker){
        //need to set max day, initialize players with correct stats!!!!!!!!!
        buildScenes();
        buildActingSets();
        buildCastingOffice();
        buildTrailer();
        Player[] players = buildPlayers(player_num, checker);
        return players;
    }

    private void buildScenes(){
        //import data from xml
        this.scenes = this.parser.buildScenes();
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


    private void buildActingSets(){
        //needs also takes parsed data, need to differentiate the ones that are actingsets
        List<ActingSet> acting_sets_list = this.parser.buildActingSets();
        this.actingsets = new HashMap <String, ActingSet>();

        for (int i = 0; i < acting_sets_list.size(); i++) {
            ActingSet acting_set = acting_sets_list.get(i);
            String name = acting_set.getName();
            this.actingsets.put(name, acting_set);
        }
    }

    //needs to reset shot tokens, might need a max shot token attr somewhere
    private void assignScenes(){
        //iterate through hashmap of actingsets to assign scenes randomly
        actingsets.forEach((s,a) ->
            {

                a.setScene(getScene());
            });
    }


    public ActingSet getActingSet(String name){
        //index into hashmap
        //will placeholder will be null if name is not in actingset
        ActingSet placeholder = this.actingsets.get(name);
        return placeholder;
    }

    public CastingOffice getCastingOffice(String name){
        //index into hashmap
        //will placeholder will be null if name is not in actingset
        CastingOffice placeholder = null;
        if (name.equals("casting office")){
        placeholder = this.castingoffice;
        }
        return placeholder;
    }

    public void setBoard(Player[] players){
        //reset shot tokens, etc.
        //also set scenesLeft to 10
        //replaces current scenes with new scenes
        assignScenes();
        //move players to trailer
        for(int i = 0; i<players.length;i++){
            players[i].setLocation("trailer");
        }
        this.scenesLeft = 10;

    }

    public Player[] buildPlayers(int n, Checker checker){
        Player[] players = new Player[n+1];
        String name;
        for (int i=1; i<n+1; i++){
            name = "player"+i;
            Player player = new Player(name, null, checker);
            players[i] = player;
        }
        return players;
    }

    public ArrayList<String> getNeighbors(String name){
        ArrayList<String> neighbors = null;
        if (actingsets.containsKey(name)){
            neighbors = actingsets.get(name).getNeighbors();
        }
        else if (name == this.castingoffice.getName()){
            neighbors = this.castingoffice.getNeighbors();
        }
        else if (name == this.trailer.getName()){
            neighbors = this.trailer.getNeighbors();
        }
        return neighbors;
    }

    // public void results(Player[] players){

    //     String[] results;
    //     for (int i =0; i<player.size;i++){
    //         calcScore(players[i]);
    //     }
    //     results.sort();
    //     //send results to view
    // }

}