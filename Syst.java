/* 
Attributes
-wrapped
-turnIndex
-turnOrder[]
-scenes[]
-days
-actingSets[]
-trailer


Methods
-buildScenes()
-buildSets()
-buildPlayers()
-assignScenes()
-setDay()
-endDay() checks if 1 scene remaining
-calcScore()


Implemented by - 
Last Change mm/dd/yy, first
*/

import java.util.HashMap;

public class Syst{
    boolean wrapped;
    int turnIndex;
    Player[] turnOrder;
    Scene[] scenes;
    int days;
    Set[] sets;
    Hashmap <Set, ActingSet> actingsets;
    Set trailer;
    int scenecount;

    public void getWrapped(){}
    public void setWrapped(){}
    public void getTurnIndex(){}
    public void getDays(){}
    public void buildScenes(){}
    public void setSceneCount(){}
    public void removeSceneCount(){}
    public void buildSets(){}
    public void buildPlayers(){}
    public void assignScenes(){}
    public void setDay(){}
    public void endDay(){}
    public void calcScore(){}
    public ActingSet getActingSet(Set set){
        ActingSet placeholder = new ActingSet("placeholder",1,1);
        return placeholder;
    }

}