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

    public Role(boolean star){
        this.star = star;
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