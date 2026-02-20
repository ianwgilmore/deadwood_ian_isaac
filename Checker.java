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
    int[] dolCost = {0, 0,  4, 10, 18, 28, 40};
    int[] credCost = {0, 0, 5, 10, 15, 20, 25};

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

    public boolean checkMove(ArrayList<Set> neighbors, Set target_loc, Role role) {
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

    public boolean checkRankUp(String payment, int amount, int targetRank, Set location) {
        //since list of cost is in array 0 indexed and rank 1 not included
        //check if sufficient funds
        //check if player is in casting office
        Boolean bool = false;
        if (location.getName() == "casting office"){
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

    public boolean checkTakeRole(Board board, Set location, Role role) {
        Boolean bool;
        if (checkRole(role) == false){
            //need a way to check if the desired role is already taken
            //checks if current player location has desired role
            bool = board.getActingSet(location).getScene().getRoles().contains(role);
        }
        else{
            bool = false;
        }
        return bool;
    }
}