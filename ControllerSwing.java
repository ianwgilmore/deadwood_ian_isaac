//potentially completed
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerSwing implements Controller{
    ViewSwing view = new ViewSwing();

    public int setup(){
        view.startWindow();
        int playerNum = this.view.getPlayerNum();
        return playerNum;
    }


    public String move(ArrayList<String> neighbors){
        String targetLoc = this.view.getTargetLoc(neighbors);
        return targetLoc;
    }

    public int[] rankUp(int[] balance){
        //array [type, target]
        //where type is 0 or 1 (dollars or credits)
        //and target is int for desired rank
        int[] rankInfo = this.view.getRankInfo(balance);
        return rankInfo;
    }

    public String takeTurn(ArrayList<String> actions,String name){
        String action = this.view.getPlayerAction(name);
        return action;
    }

    //prompt user for whether star or 
    public String typeRole(){
        String action = this.view.getTypeRole();
        return action;
    }

    public String starRole(HashMap<String, Role> roles){
        String action = this.view.getRole(roles);
        return action;
    }

    public String extraRole(HashMap<String, Role> roles){
        String action = this.view.getRole(roles);
        return action;
    }

    //public void endTurn(){}

    public void error(){
        this.view.sendErrorMessage();
    }

    public void newDay(int day){
        this.view.sendNewDay(day);
    }

    public void displayResult(String[] results){
        this.view.displayResults(results);
    }

    public void showRoll(int roll){
        this.view.showRoll(roll);
    }

    public void showTokens(int tokens){
        this.view.showTokens(tokens);
    }

    public void displayWrap(){
        this.view.displayWrap();
    }

    public void displayResults(String[] results){
        this.view.displayResults(results);
    }
}