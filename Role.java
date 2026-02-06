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

Implemented by - 
Last Change mm/dd/yy, first
*/

public class Role{
    String title;
    int rank;
    boolean star;
    int success;//might need to think about how this is represented
    int failure;

    public Role(boolean star){
        this.star = star;
    }
    //everything void to compile for now implementing later
    public void getTitle(){}
    public void getRank(){}
    public void getSuccess(){}
    public void getFailure(){}
    public boolean isStar() {
        return this.star;
    }
}