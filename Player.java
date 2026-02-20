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

NEED TAKETURN ONCE VIEW AND CONTROLLER SET UP
*/

public class Player{
    String name;
    int rank;
    int dollars;
    int credits;
    Set location;
    int practice_tok;
    Role role;
    Checker checker;
    Die die;
    
    public Player(String name, Set location, Checker checker) {
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

    public Set getLocation() {
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

    public void setLocation(Set location) {
        this.location = location;
    }

    public void setPracticeTokens(int practice_tok) {
        this.practice_tok = practice_tok;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // actions

    public void move(Set new_location) {
        if (this.checker.checkMove(this.location.getNeighbors(), new_location, this.role)) {
            this.location = new_location;
        }
    }

    public void act(Board board) {
        int[] payout;
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
    }

    public void rehearse(Board board) {
        ActingSet actingset = board.getActingSet(this.location);
        int budget = actingset.getScene().getBudget();
        if (this.checker.checkRehearsal(this.role, this.practice_tok, budget)) {
            this.practice_tok += 1;
        }
    }

    public void takeRole(Role role, Board board) {
        if (this.checker.checkTakeRole(board, this.location, role)) {
            this.role = role;
        }
    }

    public void rankUp(String type, int target) {
        int amount = 0;
        if (type == "dollars"){
            amount = this.dollars;
        }
        else if (type == "credits"){
            amount = this.credits;
        }
        //else shut it down
        if (this.checker.checkRankUp(type, amount, target,this.location)) {
            this.rank += 1;
        }
    }

    public void takeTurn(){
        //call controller to prompt the view
    }
}
