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
    boolean actionTaken;
    
    
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
    public String getName(){
        return this.name;
    }
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
        //Boolean moveCheck = this.checker.checkMove(board.getNeighbors(this.location), new_location, this.role);
        //checks should be reduntant now
        this.location = new_location;
        this.actionTaken = true;
        board.flipCardAt(this.location, controller);

        takeTurn(board, controller, false);
    }

    private void act(Board board, Controller controller) {
        int[] payout;
        //need to change to something like getSet(location)
        System.out.println("tokens: " + this.practice_tok);
        ActingSet actset = board.getActingSet(this.location);
        int roll = this.die.roll();
        controller.showRoll(roll);
        if (roll + this.practice_tok >= actset.scene.getBudget()) {
            payout = this.role.getSuccess();
            actset.removeShotToken(board, controller);
            controller.showTokens(actset.getShotToken(), actset.getName());
            } 
        else {
            payout = this.role.getFailure();
        }

        int dollars = payout[0];
        int credits = payout[1];

        this.dollars += dollars;
        this.credits += credits;
        this.actionTaken = true;
        controller.updateScore(this.name, this.dollars, this.credits, this.rank);
        takeTurn(board, controller, false);
    }

    private void rehearse(Board board, Controller controller) {
        //same as above method
        
        ActingSet actingset = board.getActingSet(this.location);
        int budget = actingset.getScene().getBudget();
        this.practice_tok += 1;
        this.actionTaken = true;
        takeTurn(board, controller, false);
    }


    private ArrayList<String> getActions(Board board){
        ArrayList<String>  actions = new ArrayList<>();  
        if (this.actionTaken == false){
            if (checker.checkRole(this.role) == false){
                actions.add("move");
            }

            if (checker.checkActingSet(this.location, board) == true && checker.checkScene(board.getActingSet(this.location))){
            //check if on actingset, check if has role
                if (checker.checkRole(this.role) == false){
                    actions.add("take role");
                }
            //check player is in castingOffice
            //if player has a role and the current actingset has a scene
                if(checker.checkRole(this.role) == true){
                    actions.add("act");

                    if(checker.checkRehearse(this.role, this.practice_tok, board.getActingSet(this.location).getScene().getBudget())){
                        actions.add("rehearse");
            }
            }
            }
            //if player has role and rehearse tokens is less than budget
        }
        if(checker.checkCastingOffice(this.location,  board)== true){
                actions.add("rank up");
        }
        actions.add("end turn");
        actions.add("end day");
        return actions;
    }

    private void takeStarRole(String role, Board board, Controller controller, ActingSet actingset) {
        //roles player can take should be handled earlier
        HashMap<String, Role> starRoles = actingset.getScene().getRoles();
        ArrayList<Player> stars = actingset.getScene().getStars();
        Role target = starRoles.get(role);
        this.role = target;
        target.take();
        actingset.getScene().addStar(this);
        this.actionTaken = true;
        takeTurn(board, controller, false);
    }

    private void takeExtraRole(String role, Board board, Controller controller, ActingSet actingset) {
        //roles player can take should be handled earlier
        HashMap<String, Role> extraRoles = actingset.getExtraRoles();
        ArrayList<Player> extras = actingset.getExtras();
        Role target = extraRoles.get(role);
        this.role = target;
        target.take();
        actingset.addExtras(this);
        this.actionTaken = true;
        takeTurn(board, controller, false);
    }


    ////NEED TO ADD A WAY OF TRACKING RANK COSTS
    private void rankUp(String type, int target, Board board, Controller controller) {
        int amount = 0;
        //pick what 
            if (type.equals("dollars")){
                amount = this.dollars;
            }
            else if (type.equals("credits")){
                amount = this.credits;
            }
        //if ranking up is a valid move, take payment and increase rank
        if (this.checker.checkRankUp(type, amount, target, this.location, board)) {
            if (type.equals("dollars")){
                this.dollars= this.dollars - amount;
            }
            else{
                this.credits = this.credits - credits;
            }
            this.rank = target;
        }
        controller.updateScore(this.name, this.dollars, this.credits, this.rank);
        takeTurn(board, controller, false);
    }

    public void takeTurn(Board board, Controller controller, Boolean endTurn){
        if(endTurn == false){

            ArrayList<String> actions = getActions(board);
            String action = controller.takeTurn(actions, this.name);


            //String action = controller.takeTurn(this.name, this.location);
            //do player action 
            if (action.equals("act")){
                act(board, controller);
            }

            else if (action.equals("rehearse")){
                rehearse(board, controller);
            }

            else if (action.equals("move")){
                ArrayList<String> neighbors = board.getNeighbors(this.location);
                String targetLoc = controller.move(neighbors);
                move(targetLoc, board, controller);
            }

            else if (action.equals("take role")){
                //precond, does not have role, is on actingset
                ActingSet set = board.getActingSet(this.location);
                String type;
                String target;
                ArrayList<String> starRoles = checker.getValidRoles(set.getScene().getRoles(), this.rank);
                ArrayList<String> extraRoles = checker.getValidRoles(set.getExtraRoles(), this.rank);
                if (set != null){
                    type = controller.typeRole();
                    if (type.equals("star")&&starRoles.size()>0){
                        //send user valid roles instead of all
                        target = controller.starRole(starRoles);
                        takeStarRole(target, board, controller, set);
                        
                    }
                    else if(type.equals("extra")&&extraRoles.size()>0){
                        //send user valid roles instead of all
                        target = controller.extraRole(extraRoles);
                        takeExtraRole(target, board, controller, set);
                    }
                    else{
                        takeTurn(board, controller, false);
                    }

                }
            }

            else if (action.equals("rank up")){
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

                System.out.println("type " +type+ "rank " + target);
                rankUp(type, target, board, controller);
            }

            else if(action.equals("end turn")){
                this.actionTaken = false;
                takeTurn(board, controller, true);
            }
            else if(action.equals("end day")){
                this.actionTaken = false;
            }
        }
    }
}
