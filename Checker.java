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
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
public class Checker{
    //extra zeros allow desired rank to serve as an index

    //used for takeRole and Act
    public boolean checkRole(Role role) {
        //true if player has a role(not null)
        //false if player does not have a role
        Boolean bool = false;
        if (!Objects.isNull(role)){
            bool = true;
        }
        return bool;
    }

    public boolean checkActingSet(String name, Board board){
        Boolean bool = board.getActingSet(name) != null;
        return bool;
    }

    public boolean checkCastingOffice(String name, Board board){
        Boolean bool = board.getCastingOffice(name) != null;
        return bool;
    }

    public boolean checkMove(ArrayList<String> neighbors, String target_loc, Role role) {
        //if target location is one of the current player location's neighbors --> True
        //also must be false if the player is currently working on a role
        Boolean bool = false;
        if (Objects.isNull(role)){
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

    public boolean checkRankUp(String payment, int amount, int targetRank, String location, Board board) {
        //since list of cost is in array 0 indexed and rank 1 not included
        //check if sufficient funds
        //check if player is in casting office
        Boolean bool = false;
        if (checkCastingOffice(location, board) == true){
            CastingOffice castingoffice = board.getCastingOffice(location);
            if(payment == "dollars"){
                bool = amount >= castingoffice.getDolCost()[targetRank];
            }
            else if (payment == "credits"){
                bool = amount >= castingoffice.getCredCost()[targetRank];
            }
        }
        return bool;
    }

    public boolean checkTakeRole(Board board, String location, Role role, HashMap<String, Role> roleList, ArrayList<Player> players, Role target) {
        Boolean bool;
        ActingSet actingset = board.getActingSet(location);
        //if does not have role
        if (!checkRole(role) && !target.isTaken()){
            bool = actingset.getScene().getRoles().containsValue(target);
        }
        else{
            bool = false;
        }
        return bool;
    }
}