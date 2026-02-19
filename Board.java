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

    public int getMaxDay(int player_num){
        ///IMPLEMENT !!!!!!!!!!!!!!
        return 3;
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