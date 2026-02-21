/* 

Methods
-checkRole()
-checkAct()
-checkMove()
-checkRehearsal()
-checkUpgrade()
-checkTakeRole()

Implemented by - 
Last Change mm/dd/yy, first

NEED TO FINISH TAKEROLE
*/

import java.util.ArrayList;
public class Checker{
    //extra zeros allow desired rank to serve as an index

    //used for takeRole and Act
    public boolean checkRole(Role role) {
        //true if player has a role(not null)
        //false if player does not have a role
        Boolean bool = false;
        if (role != null){
            bool = true;
        }
        return bool;
    }

    public boolean checkActingSet(String name, Board board){
        Boolean bool = board.getActingSet(name) != null;
        return bool
    }

    public boolean checkCastingOffice(){
        Boolean bool = board.
    }

    public boolean checkMove(ArrayList<String> neighbors, String target_loc, Role role) {
        //if target location is one of the current player location's neighbors --> True
        //also must be false if the player is currently working on a role
        Boolean bool = false;
        if (role == null){
            bool = neighbors.contains(target_loc);
        }
        return bool;
    }

    public boolean checkRehearsal(Role role, int practiceTokens, int budget) {
        Boolean bool = false;
        if (checkRole(role) == true && practiceTokens<budget){
            bool = true;
        }
        return bool;
    }

    public boolean checkRankUp(String payment, int amount, int targetRank, String location) {
        //since list of cost is in array 0 indexed and rank 1 not included
        //check if sufficient funds
        //check if player is in casting office
        Boolean bool = false;
        if (location == "casting office"){
            if(payment == "dollars"){
                bool = amount >= this.dolCost[targetRank];
            }
            else if (payment == "credits"){
                bool = amount >= this.credCost[targetRank];
            }
        }
        else{
            bool = false;
        }
        return bool;
    }

    public boolean checkTakeRole(Board board, String location, Role role) {
        Boolean bool;
        if (checkRole(role) == false){
            bool = board.getActingSet(location).getScene().getRoles().contains(role);
            //make sure that role is not already taken 
        }
        else{
            bool = false;
        }
        return bool;
    }
}