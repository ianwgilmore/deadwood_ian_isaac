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
*/

import java.util.ArrayList;

public class Checker{
    //maybe add static hashmaps for payment costs
    int cost = 3;

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

    public boolean checkMove(ArrayList<Set> neighbors, Set target_loc) {
        //if target location is one of the current player location's neighbors --> True
        Boolean bool = neighbors.contains(target_loc);
        return bool;
    }

    public boolean checkRehearsal(Role role, int practiceTokens, int budget) {
        Boolean bool = false;
        if (checkRole(role) == true && practiceTokens<budget){
            bool = true;
        }
        return bool;
    }

    public boolean checkRankUp(String payment, int amount, int target, Set location) {
        return true;
    }

    public boolean checkTakeRole(Set location, Role role) {
        return true;
    }
}