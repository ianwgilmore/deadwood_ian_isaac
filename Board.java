import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class Board{
    ArrayList<Scene> scenes;
    ArrayList<Set>  sets;
    Hashmap <Set, ActingSet> actingsets;
    Set trailer;

    public void buildScenes(ArrayList <Parser> scenedata){
        //import data from xml
        //logic needs to be updated once xml is finished!!!!!!!!!!!!!
        for(int i=0; i<30; i++){
            Parser data = scenedata.get(i);
            //Scene nextScene = new Scene(data)
            //Scenes.add()
        }
    }
    //get random scene
    public Scene getScene(){
        Random random = new Random();
        int index = random.nextInt(scenes.size());
        Scene getscene = scenes.get(index);
        scenes.remove(index);
        return getscene;
    }

    public void buildSets(){
        //needs to take parsed data as well
        return;
    }

    public void buildActingSets(){
        //needs also takes parsed data, need to differentiate the ones that are actingsets
        return;
    }


    public void assignScenes(){
        //iterate through hashmap of actingsets to assigne scenes randomly
        return;
    }


    public ActingSet getActingSet(Set set){
        //index into hashmap
        ActingSet placeholder = new ActingSet("placeholder", 1, 1);
        return placeholder;
    }

    public void resetBoard(){
        //needs to clear scenes, add new scenes, change player location to trailer, reset shot tokens, etc.
        return;
    }

}