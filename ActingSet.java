/* 
(casting office/trailer)
Attributes
-scene
-extraroles[]
-extras[]
-shotTokens

Methods
-getters and setters
-wrap()

Implemented by - 
Last Change mm/dd/yy, first

*/

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class ActingSet extends Set{
    Scene scene;
    HashMap<String, Role> extraroles;
    ArrayList<Player> extras;
    int shotTokens;
    //might need to track max shot tokens
    int maxShotTokens;

    public ActingSet(String name, int shotTokens) {
        super(name);
        this.shotTokens = shotTokens;
        this.maxShotTokens = shotTokens;
    }

    public Scene getScene(){
        return this.scene;
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public int getMaxShotTokens(){
        return this.maxShotTokens;
    }


    public void removeShotToken(Board board) {
        this.shotTokens--;
        if (this.shotTokens == 0){
            wrap(board);
        }
    }

    public void setShotTokens(){
        this.shotTokens = this.maxShotTokens;
    }

    public int getShotToken(){
        return this.shotTokens;
    }

    public void addExtraRoles(Role newExtraRole){
        this.extraroles.put(newExtraRole.getTitle(), newExtraRole);
    }
    public HashMap<String,Role> getExtraRoles(){
        return this.extraroles;
    }


    public void addExtras(Player newExtra){
        this.extras.add(newExtra);
    }
    public ArrayList<Player> getExtras(){
        return this.extras;
    }

    public void wrap(Board board){
        Die die = new Die();
        ArrayList<Integer> payments = new ArrayList<Integer>();
        for (int i=0; i<this.scene.getBudget(); i++){
            int num = die.roll();
            payments.add(num);
        }
        extraWrap();
        //remove from scene count
        board.removeScene();
        starWrap(payments);


        this.scene = null;
    }

    //helper for wrap
    private void extraWrap(){
        for (int i=0; i<this.extras.size();i++){
            Player player = this.extras.get(i);
            player.addDollars(player.role.rank);
        }
    }

    //loops through the payments
    //if overshoots the stars, then will loop back over them
    private void starWrap(ArrayList<Integer> payments){
        int index = 0;
        List<Player> stars = this.scene.getStars();
        for (int i=0; i<payments.size(); i++){
            if (index < stars.size()){
                stars.get(index).addDollars(payments.get(i));
            }
            else{
                index = 0;
            }
        }
    }
}
