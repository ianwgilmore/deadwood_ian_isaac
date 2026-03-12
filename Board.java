import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



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
        this.castingoffice = this.parser.buildCastingOffice();
    }

    public void buildTrailer(){
        this.trailer = this.parser.buildTrailer();
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
        if (name.equals("office")){
            placeholder = this.castingoffice;
        }
        return placeholder;
    }

    public void setBoard(Player[] players){
        //replaces current scenes with new scenes
        assignScenes();
        //move players to trailer
        for(int i = 0; i<players.length;i++){
            players[i].setLocation(this.trailer.getName());
        }
        //resets the scenes counter
        this.scenesLeft = 10;
        for (ActingSet set: actingsets.values()){
            set.setShotTokens();
        }
    }

    public Player[] buildPlayers(int n, Checker checker){
        Player[] players = new Player[n];
        String name;
        Player player;
        int playerNum;
        for (int i=0; i<n; i++){
            playerNum = i+1;
            name = "player"+playerNum;
            player = new Player(name, null, checker);
            players[i] = player;
        }
        return players;
    }

    public ArrayList<String> getNeighbors(String name){
        ArrayList<String> neighbors = null;
        if (actingsets.containsKey(name)){
            neighbors = actingsets.get(name).getNeighbors();
        }
        else if (name.equals(this.castingoffice.getName())){
            neighbors = this.castingoffice.getNeighbors();
        }
        else if (name.equals(this.trailer.getName())){
            neighbors = this.trailer.getNeighbors();
        }
        return neighbors;
    }

    public void results(Player[] players, Controller controller){
        HashMap<Integer, String> tempMap = new HashMap<Integer, String>();
        String[] results= new String[players.length];
        Integer[] sortingArray= new Integer[players.length];
        Player player;
        Integer score;
        for (int i=0; i<players.length; i++){
            player = players[i];
            score = calcScore(player);
            tempMap.put(score, player.getName());
            sortingArray[i] = score;
        }
        //sort by score
        Arrays.sort(sortingArray);
        String tempPlayer;
        //index into hashmap to build an array of names rather than scores
        for (int i=0; i<sortingArray.length; i++){
            tempPlayer = tempMap.get(sortingArray[i]);
            results[i] = tempPlayer;
        }
        //send results to view
        controller.displayResults(results);

    }

    public int calcScore(Player player){
        int rankScore = 5*player.getRank();
        int score = player.getDollars() + player.getCredits() + rankScore;
        return score;
    }

}

    