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


    //payout =[dol, credit]
    public int[] getStarWin() {
        int[] i = {0, 2};
        return i;
    }

    public int[] getExtraWin() {
        int[] i = {1, 1};
        return i;
    }

    public int[] getStarLose() {
        int[] i = {0, 0};
        return i;
    }

    public int[] getExtraLose() {
        int[] i = {1, 0};
        return i;
    }

    public void addExtraRoles(Role newExtraRole){
        this.extraroles.add(newExtraRole);
    }
    public ArrayList<Role> getExtraRoles(){
        return this.extraroles;
    }
     public Player[] getExtras(Player newExtra){
        this.extras.add(newExtra);
    }
    public ArrayList<Player> getExtras(){
        return this.extras;
    }

    public void wrap(){
        Die die = new Die();
        ArrayList<Integer> payments;
        for (int i=0; i<this.budget; i++){
            int num = die.roll();
            payments.add(num);
        }
        extraWrap();

        starWrap(payments);

        //remove scene, increment scene count
        removeSceneCount();
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
        for (int i=0; i<payments.size(); i++){
            if (index < this.extras.size()){
                extras.get(index).addDollars(payments.get(i));
            }
            else{
                index = 0;
            }
        }
    }
}