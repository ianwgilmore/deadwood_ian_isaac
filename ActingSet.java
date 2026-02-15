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

public class ActingSet extends Set{
    Scene scene;
    ArrayList<Role> extraroles;
    ArrayList<Player> extras;
    int shotTokens;
    int budget;

    public ActingSet(String name, ArrayList<Set> neighbors, int budget, int shotTokens) {
        super(name, neighbors);
        this.budget = budget;
        this.shotTokens = shotTokens;
    }

    public int getBudget() {
        return this.budget;
    }

    public void removeShotToken() {
        this.shotTokens--;
    }
    public int getShotToken(){
        return this.shotTokens;
    }

    public void addExtraRoles(Role newExtraRole){
        this.extraroles.add(newExtraRole);
    }
    public ArrayList<Role> getExtraRoles(){
        return this.extraroles;
    }
     public void addExtras(Player newExtra){
        this.extras.add(newExtra);
    }
    public ArrayList<Player> getExtras(){
        return this.extras;
    }

    public void wrap(Syst system){
        Die die = new Die();
        ArrayList<Integer> payments = new ArrayList<Integer>();
        for (int i=0; i<this.budget; i++){
            int num = die.roll();
            payments.add(num);
        }
        extraWrap();

        starWrap(payments);

        //remove scene, increment scene count
        system.removeSceneCount();
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
