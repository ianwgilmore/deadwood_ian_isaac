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

Implemented by - 
Last Change mm/dd/yy, first
*/

public class Player{
    int rank;
    int dollars;
    int credits;
    Set location;
    int practice_tok;
    Role role;
    Checker checker;
    Die die;
    
    public Player(Set location, Checker checker) {
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

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public void setCredits(int credits) {
        this.credits = credits;
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
        if (this.checker.checkMove(this.location)) {
            this.location = new_location;
        }
    }

    public void act() {
        int[] payout;
        
        if (this.checker.checkAct(this.scene)) {
            if (this.die.roll() + this.practice_tok >= this.location.scene.getBudget()) {
                if (this.role.isStar()) { // Debating whether .isStar() or .getStar() is better
                    payout = this.location.getStarWin();
                } else {
                    payout = this.location.getExtraWin();
                }

                this.location.removeShotToken();
            } else {
                if (this.role.isStar()) {
                    payout = this.location.getStarLose();
                } else {
                    payout = this.location.getExtraLose();
                }
            }

            int dollars = payout[0];
            int credits = payout[1];

            this.dollars += dollars;
            this.credits += credits;
        }
    }

    public void rehearse() {
        if (this.checker.checkRehearsal(this.location, this.practice_tok)) {
            this.practice_tok += 1;
        }
    }

    public void takeRole(Role role) {
        if (this.checker.checkTakeRole(this.location, role)) {
            this.role = role;
        }
    }

    public void rankUp() {
        if (this.checker.checkRankUp(this.location)) {
            this.rank += 1;
        }
    }
}