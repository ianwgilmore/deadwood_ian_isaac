/* 
Attributes
-title
-rank
-bool star
success failure not in constructor, init based on bool star
-success
-failure

Methods
-getters and setters
-payout()

Implemented by - Isaac Raven
Last Change 02/14/26, Isaac

COMPLETED
*/

public class Role{
    String title;
    int rank;
    Boolean star;
    int[] success; // Idea: int[dollars, credits]
    int[] failure;
    Boolean taken = false; 

    public Role(String title, int rank, boolean star){
        this.title = title;
        this.rank = rank;
        this.star = star;

        int[] success = new int[2];
        int[] failure = new int[2];
        // set success and failure payments
        // in format: int[dollars, credits]
        if (this.isStar()) {
            success[0] = 0;
            success[1] = 2;
            failure[0] = 0;
            failure[1] = 0;
        } else {
            success[0] = 1;
            success[1] = 1;
            failure[0] = 1;
            failure[1] = 0;
        }

        this.success = success;
        this.failure = failure;
    }

    public String getTitle(){
        return this.title;
    }

    public Boolean isTaken(){
        return this.taken;
    }

    public void take(){
        this.taken = true;
    }

    public void untake(){
        this.taken = false;
    }

    public int getRank(){
        return this.rank;
    }

    public int[] getSuccess(){
        return this.success;
    }

    public int[] getFailure(){
        return this.failure;
    }

    public Boolean isStar() {
        return this.star;
    }
}