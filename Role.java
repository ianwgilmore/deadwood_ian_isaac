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
*/

public class Role{
    String title;
    int rank;
    boolean star;
    int[] success; // Idea: int[dollars, credits]
    int[] failure;

    public Role(int rank, boolean star){
        this.rank = rank;
        this.star = star;

        // set success and failure payments
        // in format: int[dollars, credits]
        if (this.isStar()) {
            int[] success = {0, 2};
            int[] failure = {0, 0};
        } else {
            int[] success = {1, 1};
            int[] failure = {1, 0};
        }

        this.success = success;
        this.failure = failure;
    }

    public String getTitle(){
        return this.title;
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

    public boolean isStar() {
        return this.star;
    }
}