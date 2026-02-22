/* 

Attributes
-rank
-dollars
-credits
-location
-practice_tok
-role

Methods
-getters and setters
-move()
-act()
-rehearse()
-takeRole()
-rankUp()

Implemented by - Isaac Raven
Last Change 02/05/26, Isaac

//add a way to not manage turns ending
*/

import java.util.ArrayList;
import java.util.HashMap;
public class Player{
    String name;
    int rank;
    int dollars;
    int credits;
    String location;
    int practice_tok;
    Role role;
    Checker checker;
    Die die;
    
    public Player(String name, String location, Checker checker) {
        this.name = name;
        this.rank = 0;
        this.dollars = 0;
        this.credits = 0;
        this.location = location;
        this.practice_tok = 0;
        this.role = null;
        this.checker = checker;
        this.die = new Die(); // Unsure if we're creating a die class or not
    }

    // getters

    public int getRank() {
        return this.rank;
    }

    public int getDollars() {
        return this.dollars;
    }

    public int getCredits() {
        return this.credits;
    }

    public String getLocation() {
        return this.location;
    }

    public int getPracticeTokens() {
        return this.practice_tok;
    }

    public Role getRole() {
        return this.role;
    }

    // setters

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void addDollars(int dollars) {
        this.dollars = this.dollars+dollars;
    }

    public void addCredits(int credits) {
        this.credits = this.credits + credits;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPracticeTokens(int practice_tok) {
        this.practice_tok = practice_tok;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // actions

    private void move(String new_location, Board board, Controller controller) {
        //need to change this.location to something like getSet(location).getneighbors();
        if (this.checker.checkMove(board.getNeighbors(this.location), new_location, this.role)) {
            this.location = new_location;
        }
        else{
            //if moving is not valid, restart turn with an error indicator given to user
            takeTurn(board, controller, true);
        }
    }

    private void act(Board board, Controller controller) {
        int[] payout;
        //need to change to something like getSet(location)
        ActingSet actset = board.getActingSet(this.location);
        
        if (this.checker.checkRole(this.role)) {
            if (this.die.roll() + this.practice_tok >= actset.scene.getBudget()) {
                payout = this.role.getSuccess();

                actset.removeShotToken(board);
            } else {
                payout = this.role.getFailure();
            }

            int dollars = payout[0];
            int credits = payout[1];

            this.dollars += dollars;
            this.credits += credits;
        }
        else{
            //if acting is not valid, restart turn with an error indicator given to user
            takeTurn(board, controller, true);
        }
    }

    private void rehearse(Board board, Controller controller) {
        //same as above method
        
        if(checker.checkActingSet(this.location, board) == true){
            ActingSet actingset = board.getActingSet(this.location);
            int budget = actingset.getScene().getBudget();
            if (this.checker.checkRehearsal(this.role, this.practice_tok, budget)) {
                this.practice_tok += 1;
            }
        }
        else{
            //if rehearsing is not valid, restart turn with an error indicator given to user
            takeTurn(board, controller, true);
        }
    }

    private void takeStarRole(String role, Board board, Controller controller, ActingSet actingset) {
        //
        HashMap<String, Role> starRoles = actingset.getScene().getRoles();
        ArrayList<Player> stars = actingset.getScene().getStars();
        Role target = starRoles.get(role);
        if (this.checker.checkTakeRole(board, this.location, target, starRoles, stars)) {
            this.role = target;
        }
        else{
            //if taking a role is not valid, restart turn with an error indicator given to user
            takeTurn(board, controller, true);
        }
    }

    private void takeExtraRole(String role, Board board, Controller controller, ActingSet actingset) {
        //
        HashMap<String, Role> extraRoles = actingset.getExtraRoles();
        ArrayList<Player> extras = actingset.getExtras();
        Role target = extraRoles.get(role);
        if (this.checker.checkTakeRole(board, this.location, target, extraRoles, extras)) {
            this.role = target;
        }
        else{
            //if taking a role is not valid, restart turn with an error indicator given to user
            takeTurn(board, controller, true);
        }
    }

    private void rankUp(String type, int target, Board board, Controller controller) {
        int amount = 0;
        //pick what 
            if (type == "dollars"){
                amount = this.dollars;
            }
            else if (type == "credits"){
                amount = this.credits;
            }
        //if ranking up is a valid move, take payment and increase rank
        if (this.checker.checkRankUp(type, amount, target, this.location, board)) {
            if (type == "dollars"){
                this.dollars= this.dollars - amount;
            }
            else{
                this.credits = this.credits - credits;
            }
            this.rank = target;
        }
        else{
            //if ranking up is not valid, restart turn with an error indicator given to user
            takeTurn(board, controller, true);
        }

    }

    public void takeTurn(Board board, Controller controller, Boolean error){
        //if restarting turn let user know
        if (error == true){
            controller.error();
        }

        String action = controller.takeTurn();
        //do player action 
        if (action == "act"){
            act(board, controller);
        }

        else if (action == "rehearse"){
            rehearse(board, controller);
        }

        else if (action == "move"){
            ArrayList<String> neighbors = board.getNeighbors(this.location);
            String targetLoc = controller.move(neighbors);
            move(targetLoc, board, controller);
        }

        else if (action == "take role"){
            ActingSet set = board.getActingSet(this.location);
            String type;
            String target;
            if (set != null){
                //
                type = controller.typeRole();
                if (type == "star"){
                    target = controller.starRole(set.getScene().getRoles());
                    takeStarRole(target, board, controller, set);
                }
                else{
                    target = controller.extraRole(set.getExtraRoles());
                    takeExtraRole(target, board, controller, set);
                }

            }
            else{
                controller.error();
                controller.takeTurn();
            }
        }

        else if (action == "rank up"){
            String type; 
            int target;
            int[] balance = {this.dollars, this.credits};
            int[] rankInfo = controller.rankUp(balance);
            if (rankInfo[0] == 0){
                type = "dollars";
            }
            else{
                type = "credits";
            }
            target = rankInfo[1];
            rankUp(type, target, board, controller);
        }
        //no valid choice made restart turn 
        else{
            takeTurn(board, controller, true);
        }
    }
}
